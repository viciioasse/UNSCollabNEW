package com.tugas.unscollab.data.model

data class TeamMember(
    val idTeamMember: Int,
    val idTeam: Int,
    val idStudent: Int,
    val joinDate: String,
    val joinStatus: String
)
