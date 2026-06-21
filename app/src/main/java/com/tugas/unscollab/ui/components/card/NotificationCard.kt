package com.tugas.unscollab.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tugas.unscollab.data.model.Notification
import com.tugas.unscollab.data.response.NotificationResponse
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun NotificationCard(
    notificationResponse: NotificationResponse
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(16.dp)
        ) {
            ContentNotification(
                content = notificationResponse.notification.content,
                isRead = notificationResponse.isRead
            )

            HorizontalDivider(
                color = Color.LightGray
            )

            FooterNotification(
                receiveAt = notificationResponse.receiveAt,
                isRead = notificationResponse.isRead
            )
        }
    }
}

@Composable
private fun ContentNotification(
    content: String,
    isRead: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = if (isRead) Color.LightGray else Color(0xFFCBEAF6),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = if (isRead) Color.White else Color(0xFF1FABE1)
            )
        }

        Text(
            text = content,
            color = if (isRead) Color.Gray else Color(0xFF1FABE1),
            fontSize = 12.sp,
            fontWeight = if (isRead) FontWeight.Normal else FontWeight.Bold,
            modifier = Modifier
                .weight(.3f)
        )

        if (!isRead) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = Color(0xFF1FABE1),
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
private fun FooterNotification(
    receiveAt: String,
    isRead: Boolean
) {
    Text(
        text = receiveAt,
        color = if (isRead) Color.Gray else Color(0xFF1FABE1),
        fontSize = 10.sp,
        fontWeight = if (isRead) FontWeight.Normal else FontWeight.Bold
    )
}

@Preview
@Composable
private fun PreviewNotificationCard() {
    UNSCollabTheme {
        NotificationCard(
            notificationResponse = NotificationResponse(
                Notification(
                    id_notification = "A",
                    content = "Your request for join team a have been accepted"
                ),
                isRead = false,
                receiveAt = "2 minute ago"
            )
        )
    }
}