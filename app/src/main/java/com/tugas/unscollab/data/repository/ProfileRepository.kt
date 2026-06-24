package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.User
import com.tugas.unscollab.data.remote.SupabaseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val api: SupabaseApi
) {

    fun getFullProfile(email: String): Flow<Pair<User, Student>> = flow {
        val users = api.getUserByEmail("eq.$email")
        if (users.isEmpty()) throw Exception("User dengan email $email tidak ditemukan.")

        val user = users.first()
        val students = api.getStudentByUserId("eq.${user.id_user}")
        if (students.isEmpty()) throw Exception("Detail profil mahasiswa belum dikonfigurasi.")

        val student = students.first()
        emit(Pair(user, student))
    }

    suspend fun updateStudentInfo(idStudent: String, column: String, value: String) {
        require(idStudent.isNotBlank()) { "idStudent tidak boleh kosong" }
        require(column.isNotBlank()) { "Nama kolom tidak boleh kosong" }

        val filterParam = "eq.$idStudent"
        val updateMap: Map<String, Any?> = mapOf(column to value)

        println("DEBUG REPO -> updateStudentInfo()")

        val response = api.updateStudentData(
            idStudent = filterParam,
            updates   = updateMap
        )

        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: "tidak ada error body"
            println("ERROR: $errorBody")
            throw Exception("Gagal menyimpan '$column': HTTP ${response.code()} - $errorBody")
        }
    }

    suspend fun updateBasicProfile(
        idStudent: String,
        fullName : String,
        nim      : String,
        major    : String,
        bio      : String
    ) {
        require(idStudent.isNotBlank()) { "idStudent tidak boleh kosong" }

        val filterParam = "eq.$idStudent"
        val updateMap: Map<String, Any?> = mapOf(
            "full_name" to fullName,
            "nim"       to nim,
            "major"     to major,
            "bio"       to bio
        )

        println("DEBUG REPO -> updateBasicProfile()")

        val response = api.updateStudentData(
            idStudent = filterParam,
            updates   = updateMap
        )

        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: "tidak ada error body"
            println("ERROR: $errorBody")
            throw Exception("Gagal menyimpan profil dasar: HTTP ${response.code()} - $errorBody")
        }
    }

    suspend fun uploadProfilePhoto(idStudent: String, file: File, currentPhotoUrl: String): String {
        val bucket = "profile-mahasiswa"
        val fileName = "${idStudent}_${System.currentTimeMillis()}.jpg"

        if (currentPhotoUrl.isNotBlank()) {
            try {
                val oldFileName = currentPhotoUrl.substringAfterLast("/")
                api.deleteFileFromBucket(bucket, oldFileName)
            } catch (e: Exception) {
                println("DEBUG REPO -> Gagal hapus foto lama: ${e.message}")
            }
        }

        val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        api.uploadRawFileToBucket(bucket, fileName, requestBody)

        val projectUrl = "https://qdcjgonjjrxhghlbdarz.supabase.co"
        val publicUrl = "$projectUrl/storage/v1/object/public/$bucket/$fileName"

        updateStudentInfo(idStudent, "profile_picture", publicUrl)

        return publicUrl
    }

    suspend fun deleteProfilePhoto(idStudent: String, currentPhotoUrl: String) {
        val bucket = "profile-mahasiswa"

        if (currentPhotoUrl.isNotBlank()) {
            try {
                val fileName = currentPhotoUrl.substringAfterLast("/")
                api.deleteFileFromBucket(bucket, fileName)
            } catch (e: Exception) {
                println("DEBUG REPO -> Gagal hapus foto dari storage: ${e.message}")
            }
        }

        updateStudentInfo(idStudent, "profile_picture", "")
    }
}