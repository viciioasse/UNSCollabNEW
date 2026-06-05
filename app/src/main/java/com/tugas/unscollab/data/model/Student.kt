package com.tugas.unscollab.data.model

data class Student(
    val idStudent: Int,
    val idUser: Int,
    val fullName: String,
    val nim: String? = null,
    val major: String? = null,
    val bio: String? = null,
    val portfolio: String? = null,
    val experience: String? = null,
    val skill: String? = null,
    val profilePicture: String? = null
)
