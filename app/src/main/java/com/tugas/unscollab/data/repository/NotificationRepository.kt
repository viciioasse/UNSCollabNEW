package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.Notification

object NotificationRepository {
    fun getNotifications(): List<Notification> {
        return listOf(

            Notification(
                idNotification = 1,
                content = "Lamaran magang Anda sedang ditinjau oleh perusahaan."
            ),

            Notification(
                idNotification = 2,
                content = "Selamat! Anda diterima pada lowongan magang yang dilamar."
            ),

            Notification(
                idNotification = 3,
                content = "Permintaan bergabung tim telah disetujui."
            ),

            Notification(
                idNotification = 4,
                content = "Deadline pendaftaran tim akan berakhir dalam 3 hari."
            ),

            Notification(
                idNotification = 5,
                content = "Profil Anda telah berhasil diperbarui."
            )
        )
    }
}