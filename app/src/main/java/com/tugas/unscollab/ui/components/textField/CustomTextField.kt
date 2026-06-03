package com.tugas.unscollab.ui.components.textField

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector? = null,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.LightGray,
                fontWeight = FontWeight.SemiBold
            )
        },

        leadingIcon = if (icon != null && value.isEmpty()) {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
        } else null,

        visualTransformation = if (isPassword)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,

        shape = RoundedCornerShape(8.dp),

        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFFFFFFF),
            unfocusedContainerColor = Color(0xFFFFFFFF),
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color


                .LightGray,
            cursorColor = Color(0xFF1FABE1)
        ),

        modifier = modifier
            .fillMaxWidth()
    )
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(
        value = "",
        onValueChange = {},
        placeholder = "Email",
        icon = Icons.Default.Email
    )
}