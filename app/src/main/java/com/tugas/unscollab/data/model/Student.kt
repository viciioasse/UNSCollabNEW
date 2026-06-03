package com.tugas.unscollab.data.model

import com.google.gson.annotations.SerializedName

data class Student(
    val idStudent: Int,
    val idUser: Int,
    val fullName: String,
    val nim: String,
    val major: String,
    val bio: String,
    val portfolio: String,
    val experience: String,
    val skill: String,
    val profilePicture: String? = null
)
