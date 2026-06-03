package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.Team

class TeamRepository {

    private val students = StudentRepository().getStudents()
    fun getTeam(): List<Team> {

        return listOf(

            Team(
                idTeam = 1,
                nameTeam = "Code Warriors",
                category = "Web Development",
                description = "Code Warriors adalah tim pengembangan website yang berfokus pada pembuatan aplikasi web modern dan kompetitif menggunakan teknologi terbaru.",
                requirement = "Mahasiswa diharapkan memahami HTML, CSS, JavaScript, dan framework seperti Laravel atau React.",
                maxMember = 6,
                currentMember = 4,
                deadline = "15 Juli 2026",
                tag = listOf("web", "backend", "frontend"),
                createAt = "2026-06-01",
                leaderTeam = students[0],
                member = listOf(
                    students[0],
                    students[1],
                    students[2],
                    students[3]
                )
            ),

            Team(
                idTeam = 2,
                nameTeam = "Mobile Legends Dev",
                category = "Mobile Development",
                description = "Tim pengembangan aplikasi Android menggunakan Kotlin dan Jetpack Compose untuk project modern.",
                requirement = "Menguasai Kotlin dan Android Studio.",
                maxMember = 5,
                currentMember = 3,
                deadline = "20 Juli 2026",
                tag = listOf("android", "kotlin", "mobile"),
                createAt = "2026-06-02",
                leaderTeam = students[1],
                member = listOf(
                    students[1],
                    students[4],
                    students[5]
                )
            ),

            Team(
                idTeam = 3,
                nameTeam = "AI Innovators",
                category = "Artificial Intelligence",
                description = "Tim riset machine learning dan data science untuk penelitian serta kompetisi AI.",
                requirement = "Menguasai Python dan dasar machine learning.",
                maxMember = 7,
                currentMember = 5,
                deadline = "1 Agustus 2026",
                tag = listOf("ai", "ml", "python"),
                createAt = "2026-06-03",
                leaderTeam = students[2],
                member = listOf(
                    students[2],
                    students[0],
                    students[6],
                    students[7],
                    students[8]
                )
            ),

            Team(
                idTeam = 4,
                nameTeam = "Creative Design Squad",
                category = "Design",
                description = "Tim desain UI/UX dan branding untuk project digital kreatif.",
                requirement = "Menguasai Figma atau Adobe XD.",
                maxMember = 5,
                currentMember = 2,
                deadline = "18 Juli 2026",
                tag = listOf("design", "uiux", "graphic"),
                createAt = "2026-06-04",
                leaderTeam = students[3],
                member = listOf(
                    students[3],
                    students[4]
                )
            ),

            Team(
                idTeam = 5,
                nameTeam = "Fullstack Heroes",
                category = "Software Engineering",
                description = "Tim fullstack development untuk membangun aplikasi web modern dan scalable.",
                requirement = "Memahami frontend, backend, dan REST API.",
                maxMember = 8,
                currentMember = 6,
                deadline = "30 Juli 2026",
                tag = listOf("fullstack", "web", "api"),
                createAt = "2026-06-05",
                leaderTeam = students[4],
                member = listOf(
                    students[4],
                    students[0],
                    students[1],
                    students[2],
                    students[5],
                    students[6]
                )
            ),

            Team(
                idTeam = 6,
                nameTeam = "IoT Future Team",
                category = "Internet of Things",
                description = "Tim pengembangan project IoT berbasis sensor dan mikrokontroler.",
                requirement = "Menguasai Arduino atau ESP32.",
                maxMember = 6,
                currentMember = 4,
                deadline = "5 Agustus 2026",
                tag = listOf("iot", "hardware", "embedded"),
                createAt = "2026-06-06",
                leaderTeam = students[5],
                member = listOf(
                    students[5],
                    students[2],
                    students[7],
                    students[8]
                )
            ),

            Team(
                idTeam = 7,
                nameTeam = "Cyber Security Squad",
                category = "Cyber Security",
                description = "Tim yang fokus pada penetration testing dan keamanan jaringan.",
                requirement = "Memahami networking dan Linux.",
                maxMember = 5,
                currentMember = 3,
                deadline = "10 Agustus 2026",
                tag = listOf("security", "network", "linux"),
                createAt = "2026-06-07",
                leaderTeam = students[6],
                member = listOf(
                    students[6],
                    students[0],
                    students[9]
                )
            ),

            Team(
                idTeam = 8,
                nameTeam = "Data Science Team",
                category = "Data Science",
                description = "Tim analisis data dan visualisasi untuk kebutuhan penelitian dan bisnis.",
                requirement = "Menguasai Python, Pandas, dan statistik dasar.",
                maxMember = 7,
                currentMember = 4,
                deadline = "12 Agustus 2026",
                tag = listOf("data", "python", "analytics"),
                createAt = "2026-06-08",
                leaderTeam = students[7],
                member = listOf(
                    students[7],
                    students[2],
                    students[4],
                    students[8]
                )
            ),

            Team(
                idTeam = 9,
                nameTeam = "Game Dev Studio",
                category = "Game Development",
                description = "Tim pengembangan game multiplayer berbasis Unity.",
                requirement = "Menguasai Unity dan C#.",
                maxMember = 6,
                currentMember = 5,
                deadline = "15 Agustus 2026",
                tag = listOf("game", "unity", "csharp"),
                createAt = "2026-06-09",
                leaderTeam = students[8],
                member = listOf(
                    students[8],
                    students[1],
                    students[2],
                    students[3],
                    students[5]
                )
            ),

            Team(
                idTeam = 10,
                nameTeam = "Cloud Computing Team",
                category = "Cloud Computing",
                description = "Tim deployment cloud dan implementasi DevOps modern.",
                requirement = "Memahami Docker dan Linux server.",
                maxMember = 5,
                currentMember = 3,
                deadline = "17 Agustus 2026",
                tag = listOf("cloud", "docker", "devops"),
                createAt = "2026-06-10",
                leaderTeam = students[9],
                member = listOf(
                    students[9],
                    students[4],
                    students[6]
                )
            ),

            Team(
                idTeam = 11,
                nameTeam = "Startup Builder",
                category = "Business Innovation",
                description = "Tim startup digital yang fokus pada inovasi dan pengembangan bisnis teknologi.",
                requirement = "Komunikatif dan tertarik pada dunia startup.",
                maxMember = 8,
                currentMember = 6,
                deadline = "20 Agustus 2026",
                tag = listOf("startup", "business", "innovation"),
                createAt = "2026-06-11",
                leaderTeam = students[3],
                member = listOf(
                    students[3],
                    students[0],
                    students[1],
                    students[4],
                    students[7],
                    students[9]
                )
            ),

            Team(
                idTeam = 12,
                nameTeam = "Robotics Team",
                category = "Robotics",
                description = "Tim pengembangan robot pintar untuk kebutuhan otomatisasi dan kompetisi robotik.",
                requirement = "Menguasai mikrokontroler dan sensor elektronik.",
                maxMember = 6,
                currentMember = 4,
                deadline = "25 Agustus 2026",
                tag = listOf("robot", "arduino", "automation"),
                createAt = "2026-06-12",
                leaderTeam = students[5],
                member = listOf(
                    students[5],
                    students[2],
                    students[6],
                    students[8]
                )
            )
        )
    }
}