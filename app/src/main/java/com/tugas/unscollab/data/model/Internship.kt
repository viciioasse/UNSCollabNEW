package com.tugas.unscollab.data.model

data class Internship(
    val id_internship: String,
    val id_company: String,
    val id_admin: String,
    val title: String,
    val description:  String,
    val requirement: String,
    val benefit: String,
    val approval_status: String?,
    val quota: Int,
    val location: String,
    val work_mode: String,
    val duration: String,
    val payment_status: String,
    val deadline: String,
    val image: String? = null,
    val supporting_document: String? = null,
    val posted_at: String
)