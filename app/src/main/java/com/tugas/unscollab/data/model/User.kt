package com.tugas.unscollab.data.model

data class User(
    val idUser: Int,
    val email: String,
    val role: String, // 'mahasiswa', 'perusahaan', 'admin'
    val createdAt: String? = null
)
