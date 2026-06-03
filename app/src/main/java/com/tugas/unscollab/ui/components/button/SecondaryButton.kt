package com.tugas.unscollab.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SecondaryButton(
    text: String,
    onButtonClick: () -> Unit,
    shape: RoundedCornerShape = RoundedCornerShape(50),
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onButtonClick,
        border = BorderStroke(2.dp, Color(0xFF1FABE1)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White
        ),
        shape = shape,
        modifier = modifier
            .width(200.dp)
            .height(40.dp)
    ) {
        Text(
            text = text,
            color = Color(0xFF1FABE1),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun SecondaryButtonPreview() {
    SecondaryButton(
        text = "Sign Up",
        onButtonClick = { }
    )
}