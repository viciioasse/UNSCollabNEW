package com.tugas.unscollab.data.repository

import com.tugas.unscollab.R
import com.tugas.unscollab.data.model.Internship

class InternshipRepository {
    fun getInternship(): List<Internship> {
        return listOf(
            Internship(
                idInternship = 1,
                title = "Frontend Developer Intern",
                companyName = "PT Tokopedia",
                workMode = "Remote",
                location = "Jakarta",
                internshipQuota = 4,
                duration = "3 months",
                paymentStatus = "Paid",
                vacancyStatus = "Open",
                deadline = "12 June",
                description = "Kami mencari Frontend Developer Intern yang antusias untuk bergabung bersama tim Tokopedia dalam membangun pengalaman belanja terbaik.",
                requirement = "Mahasiswa/i semester 6/7 Informatika, Teknik Industri, Sains Data, atau jurusan terkait.\n" +
                        "Minimal 1 tahun pengalaman sebagai Android Developer.\n" +
                        "Menguasai Kotlin dan Jetpack Compose.\n" +
                        "Pemahaman tentang REST API dan database lokal.\n" +
                        "Komunikasi yang baik dan mampu bekerja dalam tim.\n" +
                        "Bersedia bekerja dari kantor (Jakarta).",
                benefit = "Gaji kompetitif sesuai pengalaman.\n" +
                        "Jaminan kesehatan BPJS Kesehatan & Ketenagakerjaan.\n" +
                        "Training rutin & pengembangan skill\n" +
                        "Peluang menjadi karyawan tetap\n" +
                        "Fasilitas kantor lengkap & snack gratis",
                image = R.drawable.logo_uns
            ),
            Internship(
                idInternship = 2,
                title = "Backend Developer Intern",
                companyName = "Gojek",
                workMode = "Hybrid",
                location = "Jakarta",
                internshipQuota = 1,
                duration = "4 months",
                paymentStatus = "Paid",
                vacancyStatus = "Open",
                deadline = "20 June",
                description = "Bergabunglah dengan tim engineering Gojek untuk membangun sistem backend yang scalable dan reliable.",
                requirement = "Mahasiswa/i semester 6/7 Ilmu Komputer atau jurusan terkait.\n" +
                        "Menguasai minimal satu bahasa backend (Golang, Java, atau Python).\n" +
                        "Memahami konsep REST API dan database.\n" +
                        "Mampu bekerja dalam tim agile.",
                benefit = "Gaji kompetitif.\n" +
                        "Mentoring langsung dari senior engineer.\n" +
                        "Sertifikat internship.\n" +
                        "Peluang full-time.",
                image = R.drawable.logo_uns
            ),
            Internship(
                idInternship = 3,
                title = "UI/UX Designer Intern",
                companyName = "Traveloka",
                workMode = "Remote",
                location = "Bandung",
                internshipQuota = 2,
                duration = "3 months",
                paymentStatus = "Unpaid",
                vacancyStatus = "Open",
                deadline = "15 July",
                description = "Bantu tim desain Traveloka menciptakan pengalaman pengguna yang intuitif dan menyenangkan.",
                requirement = "Mahasiswa/i semester 6/7 Desain atau jurusan terkait.\n" +
                        "Menguasai Figma atau tools desain sejenis.\n" +
                        "Memiliki portofolio desain yang kuat.\n" +
                        "Memahami prinsip user-centered design.",
                benefit = "Sertifikat internship.\n" +
                        "Mentoring dari desainer senior.\n" +
                        "Akses ke tools desain premium.",
                image = R.drawable.logo_uns
            ),
            Internship(
                idInternship = 4,
                title = "Mobile Developer Intern",
                companyName = "Shopee",
                workMode = "Onsite",
                location = "Jakarta",
                internshipQuota = 4,
                duration = "6 months",
                paymentStatus = "Paid",
                vacancyStatus = "Open",
                deadline = "1 August",
                description = "Kembangkan fitur-fitur baru aplikasi Shopee bersama tim mobile developer kami.",
                requirement = "Mahasiswa/i semester 6/7 Ilmu Komputer atau jurusan terkait.\n" +
                        "Menguasai Android (Kotlin/Java) atau iOS (Swift).\n" +
                        "Familiar dengan Jetpack Compose menjadi nilai plus.\n" +
                        "Bersedia onsite di Jakarta.",
                benefit = "Gaji kompetitif.\n" +
                        "BPJS Kesehatan & Ketenagakerjaan.\n" +
                        "Peluang menjadi karyawan tetap.\n" +
                        "Makan siang gratis.",
                image = R.drawable.logo_uns
            ),
            Internship(
                idInternship = 5,
                title = "Data Analyst Intern",
                companyName = "Bukalapak",
                workMode = "Hybrid",
                location = "Jakarta",
                internshipQuota = 3,
                duration = "3 months",
                paymentStatus = "Paid",
                vacancyStatus = "Open",
                deadline = "10 June",
                description = "Analisis data untuk membantu pengambilan keputusan bisnis di Bukalapak.",
                requirement = "Mahasiswa/i semester 6/7 Statistika, Matematika, atau Ilmu Komputer.\n" +
                        "Menguasai Python atau R untuk analisis data.\n" +
                        "Familiar dengan SQL dan tools visualisasi data.\n" +
                        "Kemampuan komunikasi data yang baik.",
                benefit = "Gaji kompetitif.\n" +
                        "Akses ke dataset skala besar.\n" +
                        "Sertifikat internship.\n" +
                        "Mentoring dari data scientist senior.",
                image = R.drawable.logo_uns
            ),
            Internship(
                idInternship = 6,
                title = "Software Engineer Intern",
                companyName = "Microsoft Indonesia",
                workMode = "Remote",
                location = "Jakarta",
                internshipQuota = 2,
                duration = "5 months",
                paymentStatus = "Paid",
                vacancyStatus = "Open",
                deadline = "25 July",
                description = "Berkontribusi dalam pengembangan produk Microsoft yang digunakan jutaan pengguna di seluruh dunia.",
                requirement = "Mahasiswa/i semester 6/7 Ilmu Komputer atau jurusan terkait.\n" +
                        "Menguasai minimal satu bahasa pemrograman (C#, Python, atau Java).\n" +
                        "Memahami struktur data dan algoritma.\n" +
                        "Kemampuan bahasa Inggris yang baik.",
                benefit = "Gaji sangat kompetitif.\n" +
                        "Akses ke Microsoft 365 & Azure.\n" +
                        "Networking dengan profesional global.\n" +
                        "Sertifikat dari Microsoft.",
                image = R.drawable.logo_uns
            ),
            Internship(
                idInternship = 7,
                title = "Cloud Engineer Intern",
                companyName = "AWS Indonesia",
                workMode = "Hybrid",
                location = "Jakarta",
                internshipQuota = 3,
                duration = "4 months",
                paymentStatus = "Paid",
                vacancyStatus = "Open",
                deadline = "30 June",
                description = "Pelajari dan kembangkan solusi cloud bersama tim AWS Indonesia.",
                requirement = "Mahasiswa/i semester 6/7 Ilmu Komputer atau jurusan terkait.\n" +
                        "Familiar dengan konsep cloud computing.\n" +
                        "Menguasai Linux dan scripting dasar.\n" +
                        "Kemampuan bahasa Inggris yang baik.",
                benefit = "Gaji kompetitif.\n" +
                        "AWS Certification voucher.\n" +
                        "Akses ke AWS training resources.\n" +
                        "Peluang full-time.",
                image = R.drawable.logo_uns
            ),
            Internship(
                idInternship = 8,
                title = "Cybersecurity Intern",
                companyName = "Telkom Indonesia",
                workMode = "Onsite",
                location = "Bandung",
                internshipQuota = 2,
                duration = "3 months",
                paymentStatus = "Unpaid",
                vacancyStatus = "Open",
                deadline = "12 July",
                description = "Bantu tim keamanan Telkom Indonesia dalam menjaga infrastruktur digital yang aman.",
                requirement = "Mahasiswa/i semester 6/7 Ilmu Komputer atau Teknik Informatika.\n" +
                        "Memahami konsep dasar keamanan jaringan.\n" +
                        "Familiar dengan tools seperti Wireshark atau Metasploit.\n" +
                        "Bersedia onsite di Bandung.",
                benefit = "Sertifikat internship.\n" +
                        "Mentoring dari security engineer senior.\n" +
                        "Akses ke lab cybersecurity.",
                image = R.drawable.logo_uns
            ),
            Internship(
                idInternship = 9,
                title = "AI Research Intern",
                companyName = "Huawei Indonesia",
                workMode = "Remote",
                location = "Jakarta",
                internshipQuota = 1,
                duration = "6 months",
                paymentStatus = "Paid",
                vacancyStatus = "Open",
                deadline = "5 August",
                description = "Bergabung dengan tim riset AI Huawei untuk mengembangkan teknologi kecerdasan buatan masa depan.",
                requirement = "Mahasiswa/i semester 6/7 Ilmu Komputer, Matematika, atau Sains Data.\n" +
                        "Menguasai Python dan framework ML (TensorFlow/PyTorch).\n" +
                        "Memahami konsep machine learning and deep learning.\n" +
                        "Kemampuan bahasa Inggris atau Mandarin menjadi nilai plus.",
                benefit = "Gaji sangat kompetitif.\n" +
                        "Akses ke GPU cluster untuk training model.\n" +
                        "Publikasi penelitian bersama.\n" +
                        "Peluang beasiswa S2.",
                image = R.drawable.logo_uns
            )
        )
    }
}