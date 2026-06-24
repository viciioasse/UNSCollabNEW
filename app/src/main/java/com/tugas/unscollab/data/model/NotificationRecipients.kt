package com.tugas.unscollab.data.model

data class NotificationRecipients(
    val id_user: String,
    val id_notification: String,
    val is_read: Boolean,
    val sent_at: String,

    // Hasil JOIN dari Supabase — nama field harus sama persis dengan nama tabel
    val notifications: Notification? = null
)