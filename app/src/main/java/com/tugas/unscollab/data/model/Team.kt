package com.tugas.unscollab.data.model

data class Team(
    val id_team: String,
    val id_creator: String,
    val team_name: String,
    val category: String,
    val description: String,
    val requirement: String,
    val max_member: Int,
    val deadline: String,
    val tag: String,
    val team_logo: String? = null,
    val created_at: String
)
