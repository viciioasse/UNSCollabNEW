package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.Admin

object AdminRepository {

    fun getAdmins(): List<Admin> {
        return listOf(

            Admin(
                idAdmin = 1,
                idUser = 18,
                accessLevel = "Super Admin"
            ),

            Admin(
                idAdmin = 2,
                idUser = 19,
                accessLevel = "Content Moderator"
            ),

            Admin(
                idAdmin = 3,
                idUser = 20,
                accessLevel = "System Administrator"
            )
        )
    }
}