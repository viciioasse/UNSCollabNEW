package com.tugas.unscollab.data.remote

import com.tugas.unscollab.data.model.Application
import com.tugas.unscollab.data.model.Company
import com.tugas.unscollab.data.model.Internship
import com.tugas.unscollab.data.model.NotificationRecipients
import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.data.model.TeamMember
import com.tugas.unscollab.data.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface SupabaseApi {

    // ------------------------------------------------------------------ AUTH / USER
    @POST("rest/v1/users")
    suspend fun insertUser(@Body user: User): User

    @GET("rest/v1/users")
    suspend fun getUserByEmail(
        @Query("email") email: String
    ): List<User>

    // ------------------------------------------------------------------ STUDENT
    @POST("rest/v1/students")
    suspend fun insertStudent(@Body student: Student): Student

    @GET("rest/v1/students")
    suspend fun getStudentByUserId(
        @Query("id_user") idUser: String
    ): List<Student>

    @GET("rest/v1/students")
    suspend fun getStudentByStudentId(
        @Query("id_student") idStudent: String
    ): List<Student>

    @PATCH("rest/v1/students")
    suspend fun updateStudentData(
        @Query("id_student") idStudent: String,
        @Body updates: Map<String, @JvmSuppressWildcards Any?>,
        @Header("Prefer") prefer: String = "return=representation"
    ): Response<ResponseBody>

    // ------------------------------------------------------------------ INTERNSHIP
    @GET("rest/v1/internships")
    suspend fun getAllInternship(): List<Internship>

    @GET("rest/v1/internships")
    suspend fun getInternshipById(
        @Query("id_internship") idInternship: String
    ): List<Internship>

    // ------------------------------------------------------------------ COMPANY
    @GET("rest/v1/companies")
    suspend fun getCompanyById(
        @Query("id_company") idCompany: String
    ): List<Company>

    // ------------------------------------------------------------------ TEAM
    @GET("rest/v1/teams")
    suspend fun getAllTeams(): List<Team>

    @GET("rest/v1/teams")
    suspend fun getTeamById(
        @Query("id_team") idTeam: String
    ): List<Team>

    @GET("rest/v1/teams")
    suspend fun getTeamsByCreator(
        @Query("id_creator") idCreator: String
    ): List<Team>

    @POST("rest/v1/teams")
    suspend fun createTeam(@Body team: Team)

    @PATCH("rest/v1/teams")
    suspend fun updateTeam(
        @Query("id_team") id: String,
        @Body teamUpdates: Map<String, @JvmSuppressWildcards Any?>
    ): Response<ResponseBody>

    // ------------------------------------------------------------------ TEAM MEMBER
    @GET("rest/v1/team_members")
    suspend fun getTeamMembersByTeamId(
        @Query("id_team") idTeam: String
    ): List<TeamMember>

    @GET("rest/v1/team_members")
    suspend fun getTeamMemberByStudentId(
        @Query("id_student") idStudent: String
    ): List<TeamMember>

    @GET("rest/v1/team_members")
    suspend fun checkTeamMembers(
        @Query("id_student") idStudent: String,
        @Query("id_team") idTeam: String
    ): List<TeamMember>

    @POST("rest/v1/team_members")
    suspend fun joinTeam(@Body teamMember: TeamMember)

    @PATCH("rest/v1/team_members")
    suspend fun updateTeamMemberStatus(
        @Query("id_team") idTeam: String,
        @Query("id_student") idStudent: String,
        @Body body: Map<String, String>
    ): Response<ResponseBody>

    @DELETE("rest/v1/team_members")
    suspend fun deleteTeamMember(
        @Query("id_team") idTeam: String,
        @Query("id_student") idStudent: String
    ): Response<Unit>

    // ------------------------------------------------------------------ APPLICATION
    @GET("rest/v1/applications")
    suspend fun checkApplication(
        @Query("id_student") idStudent: String,
        @Query("id_internship") idInternship: String
    ): List<Application>

    @POST("rest/v1/applications")
    suspend fun applyInternship(@Body application: Application)

    @GET("rest/v1/applications")
    suspend fun getApplicationByStudentId(
        @Query("id_student") idStudent: String
    ): List<Application>

    @DELETE("rest/v1/applications")
    suspend fun deleteApplication(
        @Query("id_internship") idInternship: String,
        @Query("id_student") idStudent: String
    ): Response<Unit>

    // ------------------------------------------------------------------ NOTIFICATION
    @GET("rest/v1/notification_recipients")
    suspend fun getNotificationsWithDetails(
        @Query("id_user") idUser: String,
        @Query("select") select: String = "*,notifications(*)"
    ): List<NotificationRecipients>

    // ------------------------------------------------------------------ STORAGE

    // [FUNGSI LAMA] Biar InternshipRepository & TeamRepository nggak error
    @Multipart
    @POST("storage/v1/object/{bucket}/{path}")
    suspend fun uploadFileToBucket(
        @Path("bucket") bucket: String,
        @Path("path") path: String,
        @Part file: MultipartBody.Part
    ): ResponseBody

    // [FUNGSI BARU] Khusus buat ProfileRepository
    @POST("storage/v1/object/{bucket}/{path}")
    suspend fun uploadRawFileToBucket(
        @Path("bucket") bucket: String,
        @Path("path", encoded = true) path: String,
        @Body file: RequestBody
    ): ResponseBody

    @DELETE("storage/v1/object/{bucket}/{path}")
    suspend fun deleteFileFromBucket(
        @Path("bucket") bucket: String,
        @Path("path", encoded = true) path: String
    ): ResponseBody
}