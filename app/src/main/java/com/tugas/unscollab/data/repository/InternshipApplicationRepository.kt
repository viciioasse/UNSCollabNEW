package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.InternshipApplication

object InternshipApplicationRepository {

    fun getInternshipApplications(): List<InternshipApplication> {
        return listOf(

            InternshipApplication(
                idApplication = 1,
                idInternship = 1,
                idStudent = 1,
                applyDate = "2026-06-01",
                applicationStatus = "Pending"
            ),

            InternshipApplication(
                idApplication = 2,
                idInternship = 2,
                idStudent = 2,
                applyDate = "2026-06-01",
                applicationStatus = "Accepted"
            ),

            InternshipApplication(
                idApplication = 3,
                idInternship = 3,
                idStudent = 3,
                applyDate = "2026-06-02",
                applicationStatus = "Pending"
            ),

            InternshipApplication(
                idApplication = 4,
                idInternship = 4,
                idStudent = 4,
                applyDate = "2026-06-02",
                applicationStatus = "Rejected"
            ),

            InternshipApplication(
                idApplication = 5,
                idInternship = 5,
                idStudent = 5,
                applyDate = "2026-06-03",
                applicationStatus = "Pending"
            ),

            InternshipApplication(
                idApplication = 6,
                idInternship = 6,
                idStudent = 6,
                applyDate = "2026-06-03",
                applicationStatus = "Accepted"
            ),

            InternshipApplication(
                idApplication = 7,
                idInternship = 7,
                idStudent = 7,
                applyDate = "2026-06-04",
                applicationStatus = "Pending"
            ),

            InternshipApplication(
                idApplication = 8,
                idInternship = 8,
                idStudent = 8,
                applyDate = "2026-06-04",
                applicationStatus = "Pending"
            ),

            InternshipApplication(
                idApplication = 9,
                idInternship = 9,
                idStudent = 9,
                applyDate = "2026-06-05",
                applicationStatus = "Accepted"
            ),

            InternshipApplication(
                idApplication = 10,
                idInternship = 10,
                idStudent = 10,
                applyDate = "2026-06-05",
                applicationStatus = "Pending"
            ),

            InternshipApplication(
                idApplication = 11,
                idInternship = 1,
                idStudent = 3,
                applyDate = "2026-06-06",
                applicationStatus = "Pending"
            ),

            InternshipApplication(
                idApplication = 12,
                idInternship = 2,
                idStudent = 5,
                applyDate = "2026-06-06",
                applicationStatus = "Rejected"
            ),

            InternshipApplication(
                idApplication = 13,
                idInternship = 4,
                idStudent = 7,
                applyDate = "2026-06-07",
                applicationStatus = "Pending"
            ),

            InternshipApplication(
                idApplication = 14,
                idInternship = 6,
                idStudent = 9,
                applyDate = "2026-06-07",
                applicationStatus = "Accepted"
            ),

            InternshipApplication(
                idApplication = 15,
                idInternship = 8,
                idStudent = 2,
                applyDate = "2026-06-08",
                applicationStatus = "Pending"
            )
        )
    }
}