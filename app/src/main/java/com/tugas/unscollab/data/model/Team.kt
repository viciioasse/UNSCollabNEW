package com.tugas.unscollab.data.model

data class Team(
    val idTeam: Int,
    val creatorId: Int,
    val teamName: String,
    val category: String,
    val description: String,
    val requirement: String,
    val maxMember: Int,
    val deadline: String,
    val tag: List<String>,
    val teamLogo: String? = null,
    val createAt: String
)