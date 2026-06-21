package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.remote.SupabaseApi
import com.tugas.unscollab.data.response.NotificationResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val api: SupabaseApi
) {
    suspend fun getNotificationByUserId(idUser: String): List<NotificationResponse> {
        val recipients = api.getNotificationRecipientsByUserId("eq.$idUser")

        return recipients.map { recipients ->
            val notification = api.getNotificationById("eq.${recipients.id_notification}").firstOrNull()
                ?: throw Exception("Notification not found")

            NotificationResponse(
                notification = notification,
                isRead = recipients.is_read,
                receiveAt = recipients.sent_at
            )
        }
    }
}