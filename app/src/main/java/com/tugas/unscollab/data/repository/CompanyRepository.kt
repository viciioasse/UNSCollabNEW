package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.Company

object CompanyRepository {

    fun getCompanies(): List<Company> {
        return listOf(

            Company(
                idCompany = 1,
                idUser = 11,
                companyName = "Tokopedia",
                industryField = "Technology",
                description = "Perusahaan teknologi yang bergerak di bidang marketplace dan e-commerce terbesar di Indonesia.",
                contact = "recruitment@tokopedia.com"
            ),

            Company(
                idCompany = 2,
                idUser = 12,
                companyName = "GoTo",
                industryField = "Technology",
                description = "Ekosistem digital Indonesia yang menaungi layanan transportasi, pembayaran, dan e-commerce.",
                contact = "career@goto.com"
            ),

            Company(
                idCompany = 3,
                idUser = 13,
                companyName = "Traveloka",
                industryField = "Travel Technology",
                description = "Platform perjalanan dan gaya hidup yang menyediakan layanan tiket, hotel, dan aktivitas wisata.",
                contact = "hr@traveloka.com"
            ),

            Company(
                idCompany = 4,
                idUser = 14,
                companyName = "Blibli",
                industryField = "E-Commerce",
                description = "Platform perdagangan digital yang menyediakan berbagai produk dan layanan untuk masyarakat Indonesia.",
                contact = "career@blibli.com"
            ),

            Company(
                idCompany = 5,
                idUser = 15,
                companyName = "Telkom Indonesia",
                industryField = "Telecommunication",
                description = "Perusahaan telekomunikasi milik negara yang menyediakan layanan komunikasi dan digital.",
                contact = "hrd@telkom.co.id"
            ),

            Company(
                idCompany = 6,
                idUser = 16,
                companyName = "Bank Mandiri",
                industryField = "Banking & Finance",
                description = "Salah satu bank terbesar di Indonesia yang menyediakan layanan keuangan dan perbankan.",
                contact = "career@bankmandiri.co.id"
            ),

            Company(
                idCompany = 7,
                idUser = 17,
                companyName = "Pertamina",
                industryField = "Energy",
                description = "Perusahaan energi nasional yang bergerak di bidang minyak, gas, dan energi terbarukan.",
                contact = "recruitment@pertamina.com"
            )
        )
    }
}