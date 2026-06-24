    package com.tugas.unscollab.data.repository

    import com.tugas.unscollab.data.model.Student
    import com.tugas.unscollab.data.model.User
    import com.tugas.unscollab.data.remote.SupabaseApi
    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.flow.flow
    import javax.inject.Inject
    import javax.inject.Singleton

    @Singleton
    class ProfileRepository @Inject constructor(
        private val api: SupabaseApi
    ) {
        fun getFullProfile(email: String): Flow<Pair<User, Student>> = flow {
            val users = api.getUserByEmail("eq.$email")
            if (users.isEmpty()) throw Exception("User tidak ditemukan")
            val user = users.first()

            val students = api.getStudentByUserId("eq.${user.id_user}")
            if (students.isEmpty()) throw Exception("Detail profil mahasiswa belum dikonfigurasi")
            val student = students.first()

            emit(Pair(user, student))
        }

        // Menggunakan String biasa untuk kolom tunggal
        suspend fun updateStudentInfo(idStudent: String, column: String, value: String) {
            val updateMap = mapOf(column to value)
            val response = api.updateStudentData("eq.$idStudent", updateMap)
            if (!response.isSuccessful) {
                throw Exception("Gagal menyimpan data ke database: ${response.code()}")
            }
        }

        suspend fun updateBasicProfile(idStudent: String, fullName: String, nim: String, major: String, bio: String) {
            val updateMap = mapOf(
                "full_name" to fullName,
                "nim" to nim,
                "major" to major,
                "bio" to bio
            )
            val response = api.updateStudentData("eq.$idStudent", updateMap)
            if (!response.isSuccessful) throw Exception("Gagal menyimpan profil dasar")
        }
    }