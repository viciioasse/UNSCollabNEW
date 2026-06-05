package com.tugas.unscollab.data.repository

import com.tugas.unscollab.R
import com.tugas.unscollab.data.model.Internship

object InternshipRepository {

    fun getInternships(): List<Internship> {
        return listOf(

            Internship(
                idInternship = 1,
                idCompany = 1,
                title = "Android Developer Intern",
                description = "Membantu pengembangan aplikasi Android Tokopedia.",
                requirement = "Menguasai Kotlin.",
                benefit = "Sertifikat, mentoring, uang saku.",
                quota = 3,
                location = "Jawa Tengah",
                workMode = "Hybrid",
                duration = "4 Bulan",
                paymentStatus = "Paid",
                deadline = "2026-07-01",
                image = null
            ),

            Internship(
                idInternship = 2,
                idCompany = 2,
                title = "Backend Developer Intern",
                description = "Pengembangan REST API dan microservices.",
                requirement = "Menguasai Laravel atau Spring Boot.",
                benefit = "Mentoring dan sertifikat.",
                quota = 4,
                location = "Jakarta",
                workMode = "Hybrid",
                duration = "6 Bulan",
                paymentStatus = "UnPaid",
                deadline = "2026-07-05",
                image = null
            ),

            Internship(
                idInternship = 3,
                idCompany = 3,
                title = "UI UX Designer Intern",
                description = "Mendesain fitur aplikasi Traveloka.",
                requirement = "Menguasai Figma.",
                benefit = "Portofolio dan mentoring.",
                quota = 2,
                location = "Jawa Barat",
                workMode = "Remote",
                duration = "3 Bulan",
                paymentStatus = "UnPaid",
                deadline = "2026-07-08",
                image = null
            ),

            Internship(
                idInternship = 4,
                idCompany = 4,
                title = "Frontend Developer Intern",
                description = "Membangun tampilan website modern.",
                requirement = "ReactJS dan TailwindCSS.",
                benefit = "Sertifikat.",
                quota = 3,
                location = "Jawa Timur",
                workMode = "On-Site",
                duration = "4 Bulan",
                paymentStatus = "Paid",
                deadline = "2026-07-10",
                image = null
            ),

            Internship(
                idInternship = 5,
                idCompany = 5,
                title = "Data Analyst Intern",
                description = "Analisis data pelanggan dan dashboard.",
                requirement = "SQL dan Power BI.",
                benefit = "Mentoring profesional.",
                quota = 3,
                location = "Jakarta",
                workMode = "Hybrid",
                duration = "5 Bulan",
                paymentStatus = "Paid",
                deadline = "2026-07-12",
                image = null
            ),

            Internship(
                idInternship = 6,
                idCompany = 6,
                title = "Mobile Developer Intern",
                description = "Pengembangan aplikasi mobile banking.",
                requirement = "Kotlin atau Flutter.",
                benefit = "Uang saku dan sertifikat.",
                quota = 4,
                location = "Jakarta",
                workMode = "On-Site",
                duration = "6 Bulan",
                paymentStatus = "Paid",
                deadline = "2026-07-15",
                image = null
            ),

            Internship(
                idInternship = 7,
                idCompany = 7,
                title = "Cyber Security Intern",
                description = "Monitoring keamanan sistem perusahaan.",
                requirement = "Network Security.",
                benefit = "Pengalaman industri.",
                quota = 2,
                location = "Balikpapan",
                workMode = "On-Site",
                duration = "4 Bulan",
                paymentStatus = "UnPaid",
                deadline = "2026-07-18",
                image = null
            ),

            Internship(
                idInternship = 8,
                idCompany = 1,
                title = "Machine Learning Intern",
                description = "Pengembangan model rekomendasi produk.",
                requirement = "Python dan Machine Learning.",
                benefit = "Mentoring AI Engineer.",
                quota = 3,
                location = "Jakarta",
                workMode = "Hybrid",
                duration = "5 Bulan",
                paymentStatus = "Paid",
                deadline = "2026-07-20",
                image = null
            ),

            Internship(
                idInternship = 9,
                idCompany = 3,
                title = "QA Engineer Intern",
                description = "Melakukan testing aplikasi.",
                requirement = "Memahami software testing.",
                benefit = "Sertifikat.",
                quota = 2,
                location = "Remote",
                workMode = "Remote",
                duration = "3 Bulan",
                paymentStatus = "UnPaid",
                deadline = "2026-07-22",
                image = null
            ),

            Internship(
                idInternship = 10,
                idCompany = 5,
                title = "DevOps Intern",
                description = "Membantu deployment dan monitoring sistem.",
                requirement = "Linux dan Docker.",
                benefit = "Pengalaman DevOps.",
                quota = 2,
                location = "Jakarta",
                workMode = "Hybrid",
                duration = "4 Bulan",
                paymentStatus = "Paid",
                deadline = "2026-07-25",
                image = null
            )
        )
    }
}