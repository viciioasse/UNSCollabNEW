package com.tugas.unscollab.data.model

data class InternshipApplication(
    val idApplication: Int,
    val idInternship: Int,
    val idStudent: Int,
    val cv: String? = null,
    val coverLetter: String? = null,
    val applyDate: String,
    val applicationStatus: String
)