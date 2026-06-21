package com.tugas.unscollab.data.response

import com.tugas.unscollab.data.model.Notification

data class NotificationResponse(
    val notification: Notification,
    val isRead: Boolean,
    val receiveAt: String
)
