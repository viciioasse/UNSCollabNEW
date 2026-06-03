package com.tugas.unscollab.ui.screens.notification

import android.app.Notification
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun NotificationScreen() {
    Text(
        text = "Hello!",
    )
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    UNSCollabTheme {
        NotificationScreen()
    }
}