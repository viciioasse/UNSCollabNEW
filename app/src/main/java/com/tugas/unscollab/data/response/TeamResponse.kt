package com.tugas.unscollab.data.response

import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.Team

data class TeamResponse(
    val team: Team,
    val creatorName: String,
    val currentMember: Int,
    val members: List<Student>
)