package com.tugas.unscollab.data.model

data class Application(
    val id_application: String,
    val id_student: String,
    val id_internship: String,
    val apply_date: String,
    val cv: String? = null,
    val cover_letter: String? = null,
    val application_status: String
)