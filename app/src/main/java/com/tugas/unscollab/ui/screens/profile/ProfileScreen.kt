package com.tugas.unscollab.ui.screens.profile

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
fun ProfileScreen() {
    Text(
        text = "Hello!",
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    UNSCollabTheme {
        ProfileScreen()
    }
}