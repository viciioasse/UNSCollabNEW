package com.tugas.unscollab.data.model

data class Internship(
    val idInternship: Int,
    val title: String,
    val companyName: String,
    val workMode: String,
    val location: String,
    val internshipQuota: Int,
    val duration: String? = null,
    val paymentStatus: String? = null,
    val vacancyStatus: String? = "Open",
    val deadline: String,
    val description:  String? = null,
    val requirement: String? = null,
    val benefit: String? = null,
    val image: Int
)