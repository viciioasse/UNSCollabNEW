package com.tugas.unscollab.data.repository

import android.util.Log
import com.tugas.unscollab.data.remote.SupabaseApi
import com.tugas.unscollab.data.response.NotificationResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val api: SupabaseApi
) {
    suspend fun getNotificationByUserId(idUser: String): List<NotificationResponse> {
        Log.d("NotifRepo", "Fetching for user: $idUser")

        // 1 request saja — JOIN langsung via Supabase select
        val recipients = api.getNotificationsWithDetails("eq.$idUser")
        Log.d("NotifRepo", "Recipients: ${recipients.size}")

        recipients.forEach {
            Log.d("NotifRepo", "id_notif: ${it.id_notification} | notifications: ${it.notifications}")
        }

        val result = recipients.mapNotNull { recipient ->
            val notification = recipient.notifications
            if (notification == null) {
                Log.w("NotifRepo", "notifications null untuk: ${recipient.id_notification}")
                return@mapNotNull null
            }
            NotificationResponse(
                notification = notification,
                isRead = recipient.is_read,
                receiveAt = recipient.sent_at
            )
        }

        Log.d("NotifRepo", "Final result: ${result.size} notifikasi")
        return result
    }
}