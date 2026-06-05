package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.User

object UserRepository {

    fun getUsers(): List<User> {
        return listOf(

            // Student (1-10)

            User(
                idUser = 1,
                email = "budi@student.uns.ac.id",
                password = "123456",
                role = "student",
                createdAt = "2026-01-01"
            ),

            User(
                idUser = 2,
                email = "siti@student.uns.ac.id",
                password = "123456",
                role = "student",
                createdAt = "2026-01-02"
            ),

            User(
                idUser = 3,
                email = "ahmad@student.uns.ac.id",
                password = "123456",
                role = "student",
                createdAt = "2026-01-03"
            ),

            User(
                idUser = 4,
                email = "dinda@student.uns.ac.id",
                password = "123456",
                role = "student",
                createdAt = "2026-01-04"
            ),

            User(
                idUser = 5,
                email = "rizky@student.uns.ac.id",
                password = "123456",
                role = "student",
                createdAt = "2026-01-05"
            ),

            User(
                idUser = 6,
                email = "putri@student.uns.ac.id",
                password = "123456",
                role = "student",
                createdAt = "2026-01-06"
            ),

            User(
                idUser = 7,
                email = "bagas@student.uns.ac.id",
                password = "123456",
                role = "student",
                createdAt = "2026-01-07"
            ),

            User(
                idUser = 8,
                email = "nabila@student.uns.ac.id",
                password = "123456",
                role = "student",
                createdAt = "2026-01-08"
            ),

            User(
                idUser = 9,
                email = "farhan@student.uns.ac.id",
                password = "123456",
                role = "student",
                createdAt = "2026-01-09"
            ),

            User(
                idUser = 10,
                email = "alya@student.uns.ac.id",
                password = "123456",
                role = "student",
                createdAt = "2026-01-10"
            ),

            // Company (11-17)

            User(
                idUser = 11,
                email = "recruitment@tokopedia.com",
                password = "123456",
                role = "company",
                createdAt = "2026-01-11"
            ),

            User(
                idUser = 12,
                email = "career@goto.com",
                password = "123456",
                role = "company",
                createdAt = "2026-01-12"
            ),

            User(
                idUser = 13,
                email = "hr@traveloka.com",
                password = "123456",
                role = "company",
                createdAt = "2026-01-13"
            ),

            User(
                idUser = 14,
                email = "career@blibli.com",
                password = "123456",
                role = "company",
                createdAt = "2026-01-14"
            ),

            User(
                idUser = 15,
                email = "hrd@telkom.co.id",
                password = "123456",
                role = "company",
                createdAt = "2026-01-15"
            ),

            User(
                idUser = 16,
                email = "career@bankmandiri.co.id",
                password = "123456",
                role = "company",
                createdAt = "2026-01-16"
            ),

            User(
                idUser = 17,
                email = "recruitment@pertamina.com",
                password = "123456",
                role = "company",
                createdAt = "2026-01-17"
            ),

            // Admin (18-20)

            User(
                idUser = 18,
                email = "admin1@unscollab.com",
                password = "123456",
                role = "admin",
                createdAt = "2026-01-18"
            ),

            User(
                idUser = 19,
                email = "admin2@unscollab.com",
                password = "123456",
                role = "admin",
                createdAt = "2026-01-19"
            ),

            User(
                idUser = 20,
                email = "admin3@unscollab.com",
                password = "123456",
                role = "admin",
                createdAt = "2026-01-20"
            )
        )
    }
}