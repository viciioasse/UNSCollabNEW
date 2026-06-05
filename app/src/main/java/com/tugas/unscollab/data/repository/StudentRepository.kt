package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.Student

object StudentRepository {

    fun getStudents(): List<Student> {
        return listOf(

            Student(
                idStudent = 1,
                idUser = 1,
                fullName = "Budi Santoso",
                nim = "M0521001",
                major = "Informatika",
                bio = "Mahasiswa yang tertarik pada pengembangan aplikasi Android.",
                portfolio = "github.com/budisantoso",
                experience = "Asisten Praktikum Pemrograman Mobile",
                skill = "Kotlin, Jetpack Compose, Laravel"
            ),

            Student(
                idStudent = 2,
                idUser = 2,
                fullName = "Siti Aisyah",
                nim = "M0521002",
                major = "Informatika",
                bio = "UI/UX Designer dengan pengalaman desain aplikasi mobile.",
                portfolio = "behance.net/sitiaisyah",
                experience = "UI Designer Intern",
                skill = "Figma, Canva, Adobe XD"
            ),

            Student(
                idStudent = 3,
                idUser = 3,
                fullName = "Ahmad Fauzan",
                nim = "M0521003",
                major = "Informatika",
                bio = "Backend Developer yang fokus pada REST API.",
                portfolio = "github.com/ahmadfauzan",
                experience = "Backend Developer Freelance",
                skill = "Laravel, MySQL, PHP"
            ),

            Student(
                idStudent = 4,
                idUser = 4,
                fullName = "Dinda Permata",
                nim = "M0521004",
                major = "Sistem Informasi",
                bio = "Data Analyst dan Business Intelligence Enthusiast.",
                portfolio = "linkedin.com/in/dindapermata",
                experience = "Data Analyst Intern",
                skill = "Python, Power BI, SQL"
            ),

            Student(
                idStudent = 5,
                idUser = 5,
                fullName = "Rizky Pratama",
                nim = "M0521005",
                major = "Informatika",
                bio = "Frontend Developer yang menyukai pengembangan web modern.",
                portfolio = "github.com/rizkypratama",
                experience = "Frontend Developer Freelance",
                skill = "ReactJS, TailwindCSS, JavaScript"
            ),

            Student(
                idStudent = 6,
                idUser = 6,
                fullName = "Putri Maharani",
                nim = "M0521006",
                major = "Sistem Informasi",
                bio = "Project Manager untuk berbagai proyek kampus.",
                portfolio = "linkedin.com/in/putrimaharani",
                experience = "Project Manager Organisasi",
                skill = "Leadership, Scrum, Jira"
            ),

            Student(
                idStudent = 7,
                idUser = 7,
                fullName = "Bagas Nugroho",
                nim = "M0521007",
                major = "Informatika",
                bio = "Machine Learning Enthusiast.",
                portfolio = "github.com/bagasnugroho",
                experience = "AI Research Assistant",
                skill = "Python, TensorFlow, Scikit-Learn"
            ),

            Student(
                idStudent = 8,
                idUser = 8,
                fullName = "Nabila Zahra",
                nim = "M0521008",
                major = "Informatika",
                bio = "Mobile Developer yang fokus pada Android.",
                portfolio = "github.com/nabilazahra",
                experience = "Android Developer Intern",
                skill = "Kotlin, Firebase, Compose"
            ),

            Student(
                idStudent = 9,
                idUser = 9,
                fullName = "Farhan Akbar",
                nim = "M0521009",
                major = "Sistem Informasi",
                bio = "Cyber Security Enthusiast.",
                portfolio = "github.com/farhanakbar",
                experience = "Security Analyst Intern",
                skill = "Network Security, Linux, OWASP"
            ),

            Student(
                idStudent = 10,
                idUser = 10,
                fullName = "Alya Safitri",
                nim = "M0521010",
                major = "Informatika",
                bio = "Fullstack Developer yang menyukai pengembangan aplikasi web dan mobile.",
                portfolio = "github.com/alyasafitri",
                experience = "Fullstack Developer Freelance",
                skill = "Laravel, Kotlin, ReactJS"
            )
        )
    }
}