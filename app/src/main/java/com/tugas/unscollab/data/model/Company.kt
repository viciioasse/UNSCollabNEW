package com.tugas.unscollab.data.model

data class Company(
    val idCompany: Int,
    val idUser: Int,
    val companyName: String,
    val industryField: String? = null,
    val description: String? = null,
    val contact: String? = null,
    val companyLogo: String? = null
)
