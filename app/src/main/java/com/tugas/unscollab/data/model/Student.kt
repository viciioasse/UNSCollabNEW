package com.tugas.unscollab.data.model

data class Student(
    val id_student: String,
    val id_user: String,
    val full_name: String,
    val nim: String? = null,
    val major: String? = null,
    val bio: String? = null,
    val profile_picture: String? = null,
    val portfolio: String? = null,
    val experience: String? = null,
    val skill: String? = null
)
