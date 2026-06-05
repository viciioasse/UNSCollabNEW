package com.tugas.unscollab.data.model

data class UserNotification(
    val idUserNotification: Int,
    val idUser: Int,
    val idNotification: Int,
    val isRead: Boolean,
    val sentAt: String
)
