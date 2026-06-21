package com.tugas.unscollab.data.repository

import android.content.Context
import android.net.Uri
import com.tugas.unscollab.data.model.Application
import com.tugas.unscollab.data.remote.SupabaseApi
import com.tugas.unscollab.data.response.ApplicationResponse
import com.tugas.unscollab.data.response.InternshipResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternshipRepository @Inject constructor(
    private val api: SupabaseApi,
    @ApplicationContext private val context: Context
){
    //mengambil semua internship
    suspend fun getInternships(): List<InternshipResponse> {
        val internships = api.getAllInternship()
            .filter { it.approval_status.equals("Approved", ignoreCase = true) }
        return internships.map { internship ->
            val company = api.getCompanyById("eq.${internship.id_company}").firstOrNull()
            InternshipResponse(
                internship = internship,
                companyName = company?.company_name ?: "Unknown Company"
            )
        }
    }

    //mengambil internship berdasarkan idInternship
    suspend fun getInternshipById(idInternship: String): InternshipResponse? {
        val internship = api.getInternshipById("eq.$idInternship").firstOrNull()
        return internship?.let {
            val company = api.getCompanyById("eq.${it.id_company}").firstOrNull()
            InternshipResponse(
                internship = it,
                companyName = company?.company_name ?: "Unknown Company"
            )
        }
    }

    suspend fun checkIfApplied(idUser: String, idInternship: String): Boolean {
        // 1. Dapatkan id_student dari id_user (session)
        val student = api.getStudentByUserId("eq.$idUser").firstOrNull() ?: return false

        // 2. Cek apakah ada record di tabel applications
        val applications = api.checkApplication(
            idStudent = "eq.${student.id_student}",
            idInternship = "eq.$idInternship"
        )

        // 3. Jika list tidak kosong, berarti sudah pernah mendaftar
        return applications.isNotEmpty()
    }

    //apply Internship
    suspend fun applyInternship(
        idInternship: String,
        idUser: String,
        cvUri: Uri?,
        coverLetterUri: Uri?
    ): Boolean {
        val idApplication = UUID.randomUUID().toString()

        val student = api.getStudentByUserId("eq.$idUser").firstOrNull()
            ?: throw Exception("User not found")
        val idStudent = student.id_student

        //konversi file ke cv
        val cvPart = cvUri?.let { uri ->
            val inputStream = context.contentResolver.openInputStream(uri)
            val cvBytes = inputStream?.readBytes()
            inputStream?.close()
            cvBytes?.let {
                val requestBody = cvBytes.toRequestBody("application/pdf".toMediaTypeOrNull())
                val cvName = "cv_${UUID.randomUUID()}.pdf"
                val cvPart = MultipartBody.Part.createFormData("cv", cvName, requestBody)

                api.uploadFileToBucket("cv", cvName, cvPart)

                "https://qdcjgonjjrxhghlbdarz.supabase.co/storage/v1/object/public/cv/$cvName"
            }
        }

        //konversi file ke cover letter
        val coverLetterPart = coverLetterUri?.let { uri ->
            val inputStream = context.contentResolver.openInputStream(uri)
            val coverLetterBytes = inputStream?.readBytes()
            inputStream?.close()
            coverLetterBytes?.let {
                val requestBody = coverLetterBytes.toRequestBody("application/pdf".toMediaTypeOrNull())
                val coverLetterName = "cover_letter_${UUID.randomUUID()}.pdf"
                val coverLetterPart = MultipartBody.Part.createFormData("cover_letter", "cover_letter.pdf", requestBody)

                api.uploadFileToBucket("cover_letter", coverLetterName, coverLetterPart)

                "https://qdcjgonjjrxhghlbdarz.supabase.co/storage/v1/object/public/cover_letter/$coverLetterName"
            }
        }

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val appliedDate = sdf.format(System.currentTimeMillis())

        val application = Application(
            id_application = idApplication,
            id_student = idStudent,
            id_internship = idInternship,
            apply_date = appliedDate,
            cv = cvPart,
            cover_letter = coverLetterPart,
            application_status = "pending"
        )

        api.applyInternship(application)
        return true
    }

    suspend fun getAppliedInternships(idUser: String): List<ApplicationResponse>{
        val student = api.getStudentByUserId("eq.$idUser").firstOrNull()
            ?: throw Exception("User not found")

        val applications = api.getApplicationByStudentId("eq.${student.id_student}")

        return applications.map { application ->
            val internship = api.getInternshipById("eq.${application.id_internship}").firstOrNull()
                ?: throw Exception("Internship not found")

            val company = api.getCompanyById("eq.${internship.id_company}").firstOrNull()

            ApplicationResponse(
                internshipResponse = InternshipResponse(
                    internship = internship,
                    companyName = company?.company_name ?: "Unknown Company"
                ),
                dateApply = application.apply_date,
                statusInternship = application.application_status
            )
        }
    }
}