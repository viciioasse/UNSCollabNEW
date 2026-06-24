package com.tugas.unscollab.data.model

data class Student(
    val id_student: String,
    val id_user: String,
    val full_name: String = "",  // Tambahkan = ""
    val nim: String = "",        // Tambahkan = ""
    val major: String = "",      // Tambahkan = ""
    val bio: String? = null,
    val profile_picture: String? = null,
    val portofolio: String? = null,
    val experience: String? = null,
    val skill: String? = null
)