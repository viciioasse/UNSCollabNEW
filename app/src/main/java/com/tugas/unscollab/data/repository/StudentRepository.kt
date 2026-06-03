package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.Student

class StudentRepository {
    fun getStudents(): List<Student> {
        return listOf(
            Student(
                idStudent = 1,
                idUser = 101,
                fullName = "Budi Santoso",
                nim = "M0521001",
                major = "Informatika",
                bio = "Mahasiswa aktif yang tertarik pada backend development dan cloud computing.",
                portfolio = "github.com/budi-santoso",
                experience = "Backend Developer Intern di startup lokal",
                skill = "Kotlin, Java, Spring Boot",
                profilePicture = null
            ),
            Student(
                idStudent = 2,
                idUser = 102,
                fullName = "Siti Aisyah",
                nim = "M0521002",
                major = "Sistem Informasi",
                bio = "Fokus pada UI/UX design dan pengembangan aplikasi mobile.",
                portfolio = "behance.net/sitiaisyah",
                experience = "UI/UX Designer freelance",
                skill = "Figma, Adobe XD, UI Design",
                profilePicture = null
            ),
            Student(
                idStudent = 3,
                idUser = 103,
                fullName = "Andi Pratama",
                nim = "M0521003",
                major = "Informatika",
                bio = "Pengembang Android yang suka membuat aplikasi produktivitas.",
                portfolio = "github.com/andi-pratama",
                experience = "Android Developer intern",
                skill = "Kotlin, Android Studio, Jetpack Compose",
                profilePicture = null
            ),
            Student(
                idStudent = 4,
                idUser = 104,
                fullName = "Rina Kartika",
                nim = "M0521004",
                major = "Teknik Komputer",
                bio = "Tertarik pada jaringan komputer dan keamanan sistem.",
                portfolio = "linkedin.com/in/rinakartika",
                experience = "Network assistant lab kampus",
                skill = "Networking, Cyber Security Basics",
                profilePicture = null
            ),
            Student(
                idStudent = 5,
                idUser = 105,
                fullName = "Dewi Lestari",
                nim = "M0521005",
                major = "Informatika",
                bio = "Mobile developer yang fokus pada aplikasi e-commerce.",
                portfolio = "github.com/dewilestari",
                experience = "Freelance mobile developer",
                skill = "Flutter, Kotlin, REST API",
                profilePicture = null
            ),
            Student(
                idStudent = 6,
                idUser = 106,
                fullName = "Fajar Nugroho",
                nim = "M0521006",
                major = "Sistem Informasi",
                bio = "Data enthusiast yang tertarik pada data analysis dan machine learning.",
                portfolio = "kaggle.com/fajarnugroho",
                experience = "Data analyst intern",
                skill = "Python, SQL, Machine Learning",
                profilePicture = null
            ),
            Student(
                idStudent = 7,
                idUser = 107,
                fullName = "Maya Sari",
                nim = "M0521007",
                major = "Informatika",
                bio = "Frontend developer yang fokus pada web modern.",
                portfolio = "github.com/mayasari",
                experience = "Frontend intern",
                skill = "HTML, CSS, JavaScript, React",
                profilePicture = null
            ),
            Student(
                idStudent = 8,
                idUser = 108,
                fullName = "Rizky Maulana",
                nim = "M0521008",
                major = "Teknik Komputer",
                bio = "Suka eksplorasi IoT dan embedded system.",
                portfolio = "github.com/rizkymaulana",
                experience = "IoT project assistant",
                skill = "C++, Arduino, IoT",
                profilePicture = null
            ),
            Student(
                idStudent = 9,
                idUser = 109,
                fullName = "Nadia Putri",
                nim = "M0521009",
                major = "Sistem Informasi",
                bio = "UI/UX enthusiast dengan minat besar pada desain produk digital.",
                portfolio = "dribbble.com/nadiaputri",
                experience = "UI/UX intern",
                skill = "Figma, User Research, Prototyping",
                profilePicture = null
            ),
            Student(
                idStudent = 10,
                idUser = 110,
                fullName = "Ilham Ramadhan",
                nim = "M0521010",
                major = "Informatika",
                bio = "Backend developer yang fokus pada scalable system.",
                portfolio = "github.com/ilhamramadhan",
                experience = "Backend intern di startup",
                skill = "Go, Kotlin, Microservices",
                profilePicture = null
            )
        )
    }
}