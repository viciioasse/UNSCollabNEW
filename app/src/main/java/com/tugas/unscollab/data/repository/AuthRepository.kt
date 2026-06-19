package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.remote.SupabaseApi
import com.tugas.unscollab.data.local.SessionManager
import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: SupabaseApi,
    private val sessionManager: SessionManager
) {
    // LOGIN
    suspend fun login(email: String, password: String): Pair<User, Student?> {
        val cleanEmail = email.trim()
        val cleanPassword = password.trim()

        // Supabase memerlukan operator "eq." untuk filter
        val queryEmail = "eq.$cleanEmail"
        val users = api.getUserByEmail(queryEmail)
        
        if(users.isEmpty()) throw Exception("User not found")

        val user = users.first()
        if(user.password.trim() != cleanPassword) throw Exception("Wrong password")

        // Gunakan "eq." juga untuk mencari student berdasarkan id_user
        val students = api.getStudentByUserId("eq.${user.id_user}")
        val student = students.firstOrNull()

        sessionManager.saveSession(user.id_user, user.email)
        return Pair(user, student)
    }

    // REGISTER
    suspend fun register(fullName: String, email: String, password: String): Pair<User, Student> {
        val userId = UUID.randomUUID().toString()

        // Membuat format tanggal ISO 8601 (Contoh: 2024-06-17T14:00:00.000Z)
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val createdAt = sdf.format(Date())

        val user = User(
            id_user = userId,
            email = email,
            password = password,
            role = "student",
            created_at = createdAt
        )
        api.insertUser(user)

        val student = Student(
            id_student = UUID.randomUUID().toString(),
            id_user = userId,
            full_name = fullName
        )
        api.insertStudent(student)

        return Pair(user, student)
    }

    fun getSession() = sessionManager.getSession()

    suspend fun logout() = sessionManager.clearSession()
}
