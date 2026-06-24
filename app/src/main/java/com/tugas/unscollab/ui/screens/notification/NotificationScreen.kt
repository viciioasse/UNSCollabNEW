package com.tugas.unscollab.ui.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.data.model.Notification
import com.tugas.unscollab.data.response.NotificationResponse
import com.tugas.unscollab.ui.components.bottomBar.BottomBar
import com.tugas.unscollab.ui.components.card.NotificationCard
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.notification.NotificationViewModel

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel()
) {
    val notifications by viewModel.notifications.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.markAllRead()
    }

    NotificationScreenContent(
        notificationResponse = notifications,
        isLoading = isLoading,
        errorMessage = errorMessage
    )
}

@Composable
private fun NotificationScreenContent(
    notificationResponse: List<NotificationResponse>,
    isLoading: Boolean = false,
    errorMessage: String? = null
) {
    val backStack = LocalBackStack.current

    Scaffold(
        topBar = {
            HeaderNotif()
        },
        bottomBar = {
            BottomBar(
                currentRoute = "notification",
                onNavigate = { route ->
                    when (route) {
                        "home" -> backStack.add(Routes.HomeRoute)
                        "activity" -> backStack.add(Routes.ActivityRoute)
                        "notification" -> backStack.add(Routes.NotificationRoute)
                        "profile" -> backStack.add(Routes.ProfileRoute)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F6FA))
        ) {
            when {
                // State 1: Loading
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // State 2: Error
                errorMessage != null -> {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // State 3: Kosong
                notificationResponse.isEmpty() -> {
                    Text(
                        text = "No notifications available",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // State 4: Ada data
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(notificationResponse) { notification ->
                            NotificationCard(notificationResponse = notification)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderNotif() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 64.dp, bottom = 32.dp, start = 32.dp, end = 32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Notification",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Get your up to date notification",
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationScreen() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.NotificationRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            NotificationScreenContent(
                notificationResponse = listOf(
                    NotificationResponse(
                        notification = Notification(
                            id_notification = "A",
                            content = "Your request to join Team A has been accepted"
                        ),
                        isRead = false,
                        receiveAt = "2 minutes ago"
                    ),
                    NotificationResponse(
                        notification = Notification(
                            id_notification = "B",
                            content = "Internship deadline reminder: PT. Paragon"
                        ),
                        isRead = true,
                        receiveAt = "1 day ago"
                    )
                )
            )
        }
    }
}