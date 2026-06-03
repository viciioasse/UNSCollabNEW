package com.tugas.unscollab.ui.components.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WelcomeText(
    textWelcome: String,
    textSign: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            textWelcome,
            color = Color(0xFF1FABE1),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            textSign,
            color = Color(0xFF1FABE1),
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
private fun WelcomeTextPreview() {
    WelcomeText(
        textWelcome = "Welcome Back",
        textSign = "Sign In to your account"
    )
}