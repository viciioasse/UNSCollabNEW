package com.tugas.unscollab.data.remote

import com.tugas.unscollab.data.model.Application
import com.tugas.unscollab.data.model.Company
import com.tugas.unscollab.data.model.Internship
import com.tugas.unscollab.data.model.Notification
import com.tugas.unscollab.data.model.NotificationRecipients
import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.data.model.TeamMember
import com.tugas.unscollab.data.model.User
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface SupabaseApi {
    //insert user
    @POST("rest/v1/users")
    suspend fun insertUser(@Body user: User): User

    //insert student
    @POST("rest/v1/students")
    suspend fun insertStudent(@Body student: Student): Student

    //get user by email
    @GET("rest/v1/users")
    suspend fun getUserByEmail(@Query("email") email: String): List<User>

    //get student by id_user
    @GET("rest/v1/students")
    suspend fun getStudentByUserId(@Query("id_user") idUser: String): List<Student>

    //get all internship
    @GET("rest/v1/internships")
    suspend fun getAllInternship(): List<Internship>

    //get internship by id_internship
    @GET("rest/v1/internships")
    suspend fun getInternshipById(@Query("id_internship") idInternship: String): List<Internship>

    //get company by id_company
    @GET("rest/v1/companies")
    suspend fun getCompanyById(@Query("id_company") idUser: String): List<Company>

    //get all team
    @GET("rest/v1/teams")
    suspend fun getAllTeams(): List<Team>

    //check application
    @GET("rest/v1/applications")
    suspend fun checkApplication(
        @Query("id_student") idStudent: String,
        @Query("id_internship") idInternship: String
    ): List<Application>

    //apply internship
    @POST("rest/v1/applications")
    suspend fun applyInternship(@Body application: Application): Unit

    @GET("rest/v1/teams")
    suspend fun getTeamById(@Query("id_team") idTeam: String): List<Team>

    @GET("rest/v1/students")
    suspend fun getStudentByStudentId(@Query("id_student") idStudent: String): List<Student>

    @GET("rest/v1/team_members")
    suspend fun getTeamMembersByTeamId(@Query("id_team") idTeam: String): List<TeamMember>

    //create team
    @POST("rest/v1/teams")
    suspend fun createTeam(@Body team: Team)

    //check team_member
    @GET("rest/v1/team_members")
    suspend fun checkTeamMembers(
        @Query("id_student") idStudent: String,
        @Query("id_team") idTeam: String
    ): List<TeamMember>

    @POST("rest/v1/team_members")
    suspend fun joinTeam(@Body teamMember: TeamMember)

    @PATCH("rest/v1/teams")
    suspend fun updateTeam(
        @Query("id_team") id: String,
        @Body teamUpdates: Map<String, @JvmSuppressWildcards Any?>
    ): retrofit2.Response<ResponseBody>

    //storage upload
    @Multipart
    @POST("storage/v1/object/{bucket}/{path}")
    suspend fun uploadFileToBucket(
        @Path("bucket") bucket: String,
        @Path("path") path: String,
        @Part file: MultipartBody.Part
    ): ResponseBody

    //get teams by id_creator
    @GET("rest/v1/teams")
    suspend fun getTeamsByCreator(@Query("id_creator") idCreator: String): List<Team>

    @GET("rest/v1/team_members")
    suspend fun getTeamMemberByStudentId(@Query("id_student") idStudent: String): List<TeamMember>

    //get application by id_student
    @GET("rest/v1/applications")
    suspend fun getApplicationByStudentId(@Query("id_student") idTeam: String): List<Application>

    //get notification by id_notification
    @GET("rest/v1/notifications")
    suspend fun getNotificationById(@Query("id_notification") idNotification: String): List<Notification>

    @GET("rest/v1/notification_recepients")
    suspend fun getNotificationRecipientsByUserId(@Query("id_user") idUser: String): List<NotificationRecipients>

    @PATCH("rest/v1/team_members")
    suspend fun updateTeamMemberStatus(
        @Query("id_team") id: String,
        @Query("id_student") idStudent: String,
        @Body body: Map<String, String>
    )
}
