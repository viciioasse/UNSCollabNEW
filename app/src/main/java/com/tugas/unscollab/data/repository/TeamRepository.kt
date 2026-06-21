package com.tugas.unscollab.data.repository

import android.content.Context
import android.net.Uri
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.data.model.TeamMember
import com.tugas.unscollab.data.remote.SupabaseApi
import com.tugas.unscollab.data.response.JoinTeamResponse
import com.tugas.unscollab.data.response.TeamResponse
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
class TeamRepository @Inject constructor(
    val api: SupabaseApi,
    @ApplicationContext private val context: Context
) {

    //mengambil semua teams (home & all teams screen)
    suspend fun getTeams(): List<TeamResponse> {
        val teams = api.getAllTeams()
        return teams.map { team ->
            val creator = api.getStudentByStudentId("eq.${team.id_creator}").firstOrNull()
            val teamMembers = api.getTeamMembersByTeamId("eq.${team.id_team}")
                .filter { it.join_status.equals("Accepted", ignoreCase = true) }
                .mapNotNull { member ->
                    api.getStudentByStudentId("eq.${member.id_student}").firstOrNull()
                }
            
            TeamResponse(
                team = team,
                creatorName = creator?.full_name ?: "Creator Not Found",
                currentMember = teamMembers.size,
                members = teamMembers
            )
        }
    }

    //mengambil team berdasarkan id_team (team detail screen)
    suspend fun getTeamById(idTeam: String): TeamResponse? {
        val team = api.getTeamById("eq.$idTeam").firstOrNull() ?: return null

        val creator = api.getStudentByStudentId("eq.${team.id_creator}").firstOrNull()
        val creatorName = creator?.full_name ?: "Creator Not Found"

        val teamMembers = api.getTeamMembersByTeamId("eq.${team.id_team}")
            .filter { it.join_status.equals("Accepted", ignoreCase = true) }
            .mapNotNull { member ->
                api.getStudentByStudentId("eq.${member.id_student}").firstOrNull()
            }

        return TeamResponse(
            team = team,
            creatorName = creatorName,
            currentMember = teamMembers.size,
            members = teamMembers
        )
    }

    suspend fun joinTeam(idUser: String, idTeam: String) {
        val student = api.getStudentByUserId("eq.$idUser").firstOrNull()
            ?: throw Exception("User not found")

        val idStudent = student.id_student

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val joinAt = sdf.format(System.currentTimeMillis())

        val teamMember = TeamMember(
            id_student = idStudent,
            id_team = idTeam,
            join_status = "Pending",
            join_at = joinAt
        )
        api.joinTeam(teamMember)
    }

    suspend fun createTeam(
        idUser: String,
        teamName: String,
        category: String,
        maxMember: Int,
        deadline: String,
        description: String,
        requirement: String,
        tag: String,
        imageUri: Uri?
    ): Boolean {
        val idTeam = UUID.randomUUID().toString()

        val student = api.getStudentByUserId("eq.$idUser").firstOrNull()
            ?: throw Exception("Student not found")
        val idCreator = student.id_student

        val imageUrl = imageUri?.let { uri ->
            val inputStream = context.contentResolver.openInputStream(uri)
            val imageBytes = inputStream?.readBytes()
            inputStream?.close()
            imageBytes?.let {
                val requestBody = imageBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())
                val fileName = "team_logo_${UUID.randomUUID()}.jpg"
                val imagePart = MultipartBody.Part.createFormData("image", fileName, requestBody)

                api.uploadFileToBucket("team_logos", fileName, imagePart)

                "https://qdcjgonjjrxhghlbdarz.supabase.co/storage/v1/object/public/team_logo/$fileName"
            }
        }

        val dateCreate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        dateCreate.timeZone = TimeZone.getTimeZone("UTC")
        val createdAt = dateCreate.format(System.currentTimeMillis())

        val team = Team(
            id_team = idTeam,
            id_creator = idCreator,
            team_name = teamName,
            category = category,
            description = description,
            requirement = requirement,
            max_member = maxMember,
            deadline = deadline,
            tag = tag,
            team_logo = imageUrl,
            created_at = createdAt
        )

        api.createTeam(team)

        val dateJoin = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        dateJoin.timeZone = TimeZone.getTimeZone("UTC")
        val joinAt = dateJoin.format(System.currentTimeMillis())

        val teamMember = TeamMember(
            id_student = student.id_student,
            id_team = team.id_team,
            join_status = "Accepted",
            join_at = joinAt
        )
        api.joinTeam(teamMember)
        return true
    }

    suspend fun getMyCreatedTeams(idUser: String): List<TeamResponse> {
        val student = api.getStudentByUserId("eq.$idUser").firstOrNull() ?:
            throw Exception("User not found")

        val teams = api.getTeamsByCreator("eq.${student.id_student}")

        return teams.map {team ->
            val teamMembers = api.getTeamMembersByTeamId("eq.${team.id_team}")
                .filter { it.join_status.equals("Pending", ignoreCase = true) }
                .mapNotNull { member ->
                    api.getStudentByStudentId("eq.${member.id_student}").firstOrNull()
                }

            TeamResponse(
                team = team,
                creatorName = student.full_name,
                currentMember = teamMembers.size,
                members = teamMembers
            )
        }
    }

    suspend fun getJoinedTeams(idUser: String): List<JoinTeamResponse> {
        val student = api.getStudentByUserId("eq.$idUser").firstOrNull()
            ?: throw Exception("User not found")

        val memberships = api.getTeamMemberByStudentId("eq.${student.id_student}")

        return memberships.map { membership ->
            val team = api.getTeamById("eq.${membership.id_team}").firstOrNull()
                ?: throw Exception("Team not found")

            val creator = api.getStudentByStudentId("eq.${team.id_creator}").firstOrNull()
                ?: throw Exception("Creator not found")

            val teamMembers = api.getTeamMembersByTeamId("eq.${team.id_team}")
                .filter { it.join_status.equals("Accepted", ignoreCase = true) }
                .mapNotNull { member ->
                    api.getStudentByStudentId("eq.${member.id_student}").firstOrNull()
                }

            JoinTeamResponse(
                TeamResponse(
                    team = team,
                    creatorName = creator.full_name,
                    currentMember = teamMembers.size,
                    members = teamMembers
                ),
                dateJoin = membership.join_at,
                statusJoin = membership.join_status
            )
        }
    }
}