package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.TeamMember

object TeamMemberRepository {

    fun getTeamMembers(): List<TeamMember> {
        return listOf(

            TeamMember(
                idTeamMember = 1,
                idTeam = 1,
                idStudent = 1,
                joinDate = "2026-05-01",
                joinStatus = "Accepted"
            ),

            TeamMember(
                idTeamMember = 2,
                idTeam = 1,
                idStudent = 2,
                joinDate = "2026-05-02",
                joinStatus = "Accepted"
            ),

            TeamMember(
                idTeamMember = 3,
                idTeam = 2,
                idStudent = 3,
                joinDate = "2026-05-03",
                joinStatus = "Accepted"
            ),

            TeamMember(
                idTeamMember = 4,
                idTeam = 2,
                idStudent = 4,
                joinDate = "2026-05-03",
                joinStatus = "Pending"
            ),

            TeamMember(
                idTeamMember = 5,
                idTeam = 3,
                idStudent = 5,
                joinDate = "2026-05-04",
                joinStatus = "Accepted"
            ),

            TeamMember(
                idTeamMember = 6,
                idTeam = 3,
                idStudent = 6,
                joinDate = "2026-05-05",
                joinStatus = "Accepted"
            ),

            TeamMember(
                idTeamMember = 7,
                idTeam = 4,
                idStudent = 7,
                joinDate = "2026-05-05",
                joinStatus = "Pending"
            ),

            TeamMember(
                idTeamMember = 8,
                idTeam = 5,
                idStudent = 8,
                joinDate = "2026-05-06",
                joinStatus = "Accepted"
            ),

            TeamMember(
                idTeamMember = 9,
                idTeam = 6,
                idStudent = 9,
                joinDate = "2026-05-06",
                joinStatus = "Accepted"
            ),

            TeamMember(
                idTeamMember = 10,
                idTeam = 7,
                idStudent = 10,
                joinDate = "2026-05-07",
                joinStatus = "Pending"
            ),

            TeamMember(
                idTeamMember = 11,
                idTeam = 8,
                idStudent = 1,
                joinDate = "2026-05-08",
                joinStatus = "Accepted"
            ),

            TeamMember(
                idTeamMember = 12,
                idTeam = 9,
                idStudent = 2,
                joinDate = "2026-05-09",
                joinStatus = "Accepted"
            ),

            TeamMember(
                idTeamMember = 13,
                idTeam = 10,
                idStudent = 3,
                joinDate = "2026-05-10",
                joinStatus = "Accepted"
            ),

            TeamMember(
                idTeamMember = 14,
                idTeam = 11,
                idStudent = 4,
                joinDate = "2026-05-11",
                joinStatus = "Pending"
            ),

            TeamMember(
                idTeamMember = 15,
                idTeam = 12,
                idStudent = 5,
                joinDate = "2026-05-12",
                joinStatus = "Accepted"
            )
        )
    }
}