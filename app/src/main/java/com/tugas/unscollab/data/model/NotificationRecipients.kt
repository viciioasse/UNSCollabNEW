package com.tugas.unscollab.data.model

data class NotificationRecipients(
    val id_user: String,
    val id_notification: String,
    val is_read: Boolean,
    val sent_at: String
)
