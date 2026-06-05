package com.tugas.unscollab.data.repository

import com.tugas.unscollab.data.model.UserNotification

object UserNotificationRepository {

    fun getUserNotifications(): List<UserNotification> {
        return listOf(

            UserNotification(1, 1, 1, false, "2026-06-01"),
            UserNotification(2, 1, 2, true, "2026-06-02"),
            UserNotification(3, 2, 3, false, "2026-06-02"),
            UserNotification(4, 2, 4, true, "2026-06-03"),
            UserNotification(5, 3, 5, false, "2026-06-03"),

            UserNotification(6, 4, 1, false, "2026-06-04"),
            UserNotification(7, 5, 2, true, "2026-06-04"),
            UserNotification(8, 6, 3, false, "2026-06-05"),
            UserNotification(9, 7, 4, false, "2026-06-05"),
            UserNotification(10, 8, 5, true, "2026-06-06"),

            UserNotification(11, 9, 1, false, "2026-06-06"),
            UserNotification(12, 10, 2, true, "2026-06-07"),
            UserNotification(13, 1, 3, false, "2026-06-07"),
            UserNotification(14, 2, 4, false, "2026-06-08"),
            UserNotification(15, 3, 5, true, "2026-06-08")
        )
    }
}