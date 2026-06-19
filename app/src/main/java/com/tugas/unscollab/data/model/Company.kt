package com.tugas.unscollab.data.model

data class Company(
    val id_company: String,
    val id_user: String,
    val company_name: String,
    val industry_field: String? = null,
    val description: String? = null,
    val contact: String? = null,
    val company_logo: String? = null
)
