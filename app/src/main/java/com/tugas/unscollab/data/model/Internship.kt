package com.tugas.unscollab.data.model

data class Internship(
    val idInternship: Int,
    val idCompany: Int,
    val title: String,
    val description:  String,
    val requirement: String,
    val benefit: String,
    val quota: Int,
    val location: String,
    val workMode: String,
    val duration: String,
    val paymentStatus: String,
    val deadline: String,
    val image: String? = null,
    val supportingDocument: String? = null
)