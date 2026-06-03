package com.tugas.unscollab.data.model

data class Team(
    val idTeam: Int,
    val nameTeam: String,
    val category: String,
    val description: String,
    val requirement: String,
    val maxMember: Int,
    val currentMember: Int,
    val deadline: String,
    val tag: List<String>,
    val createAt: String,
    val leaderTeam: Student,
    val member: List<Student>
)