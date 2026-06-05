package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.Team

object TeamRepository {

    fun getTeams(): List<Team> {
        return listOf(

            Team(
                idTeam = 1,
                creatorId = 1,
                teamName = "UNS Mobile Developer",
                category = "Competition",
                description = "Tim untuk mengikuti kompetisi pengembangan aplikasi mobile tingkat nasional.",
                requirement = "Menguasai Kotlin atau Flutter.",
                maxMember = 5,
                deadline = "2026-06-30",
                tag = listOf("Android", "Kotlin", "Mobile"),
                createAt = "2026-05-01"
            ),

            Team(
                idTeam = 2,
                creatorId = 2,
                teamName = "UI UX Squad",
                category = "Project",
                description = "Tim pengembangan desain aplikasi kampus.",
                requirement = "Menguasai Figma.",
                maxMember = 4,
                deadline = "2026-06-25",
                tag = listOf("UI/UX", "Figma", "Design"),
                createAt = "2026-05-02"
            ),

            Team(
                idTeam = 3,
                creatorId = 3,
                teamName = "AI Research Team",
                category = "Research",
                description = "Tim penelitian kecerdasan buatan dan machine learning.",
                requirement = "Menguasai Python dan ML.",
                maxMember = 6,
                deadline = "2026-07-10",
                tag = listOf("AI", "Machine Learning", "Python"),
                createAt = "2026-05-03"
            ),

            Team(
                idTeam = 4,
                creatorId = 4,
                teamName = "Data Science Club",
                category = "Research",
                description = "Tim analisis data dan visualisasi data.",
                requirement = "Menguasai SQL atau Power BI.",
                maxMember = 5,
                deadline = "2026-07-05",
                tag = listOf("Data", "SQL", "Power BI"),
                createAt = "2026-05-04"
            ),

            Team(
                idTeam = 5,
                creatorId = 5,
                teamName = "Web Development Team",
                category = "Project",
                description = "Pengembangan website untuk UMKM.",
                requirement = "Menguasai React atau Laravel.",
                maxMember = 5,
                deadline = "2026-06-28",
                tag = listOf("React", "Laravel", "Web"),
                createAt = "2026-05-05"
            ),

            Team(
                idTeam = 6,
                creatorId = 6,
                teamName = "Cyber Security Team",
                category = "Competition",
                description = "Persiapan kompetisi CTF nasional.",
                requirement = "Memahami dasar keamanan jaringan.",
                maxMember = 4,
                deadline = "2026-07-15",
                tag = listOf("Security", "CTF", "Network"),
                createAt = "2026-05-06"
            ),

            Team(
                idTeam = 7,
                creatorId = 7,
                teamName = "Startup Innovation",
                category = "Business",
                description = "Membangun startup berbasis teknologi.",
                requirement = "Memiliki ide bisnis inovatif.",
                maxMember = 6,
                deadline = "2026-07-20",
                tag = listOf("Startup", "Business", "Innovation"),
                createAt = "2026-05-07"
            ),

            Team(
                idTeam = 8,
                creatorId = 8,
                teamName = "Game Development Team",
                category = "Project",
                description = "Pengembangan game edukasi untuk pelajar.",
                requirement = "Menguasai Unity atau Godot.",
                maxMember = 5,
                deadline = "2026-07-01",
                tag = listOf("Game", "Unity", "C#"),
                createAt = "2026-05-08"
            ),

            Team(
                idTeam = 9,
                creatorId = 9,
                teamName = "Cloud Computing Team",
                category = "Research",
                description = "Eksplorasi teknologi cloud dan DevOps.",
                requirement = "Memahami Linux dan Docker.",
                maxMember = 5,
                deadline = "2026-07-12",
                tag = listOf("Cloud", "Docker", "DevOps"),
                createAt = "2026-05-09"
            ),

            Team(
                idTeam = 10,
                creatorId = 10,
                teamName = "IoT Innovation Team",
                category = "Competition",
                description = "Pengembangan solusi IoT untuk smart campus.",
                requirement = "Menguasai Arduino atau ESP32.",
                maxMember = 5,
                deadline = "2026-07-08",
                tag = listOf("IoT", "Arduino", "ESP32"),
                createAt = "2026-05-10"
            ),

            Team(
                idTeam = 11,
                creatorId = 1,
                teamName = "Digital Marketing Team",
                category = "Business",
                description = "Membantu promosi produk UMKM digital.",
                requirement = "Tertarik pada digital marketing.",
                maxMember = 5,
                deadline = "2026-06-27",
                tag = listOf("Marketing", "Business", "Content"),
                createAt = "2026-05-11"
            ),

            Team(
                idTeam = 12,
                creatorId = 2,
                teamName = "Open Source Community",
                category = "Project",
                description = "Kontribusi pada proyek open source.",
                requirement = "Memiliki pengalaman Git dan GitHub.",
                maxMember = 8,
                deadline = "2026-07-18",
                tag = listOf("Open Source", "Git", "GitHub"),
                createAt = "2026-05-12"
            )
        )
    }
}