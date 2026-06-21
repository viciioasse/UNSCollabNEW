package com.tugas.unscollab.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomAlertDialog(
    title: String,
    message: String,
    confirmText: String = "Ya",
    dismissText: String = "Batal",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    isVisible: Boolean = true,
    confirmButtonColor: Color = Color(0xFF1FABE1),
    dismissButtonColor: Color = Color(0xFFEEEEEE)
) {
    if (!isVisible) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Surface(
            modifier = Modifier.Companion.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.Companion.White,
            tonalElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.Companion.Start
            ) {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    color = Color(0xFF1A1A2E)
                )
                Spacer(modifier = Modifier.Companion.height(10.dp))
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.Companion.height(24.dp))
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.Companion.weight(1f).height(42.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = dismissButtonColor,
                            contentColor = Color(0xFF333333)
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            dismissText,
                            fontWeight = FontWeight.Companion.Medium,
                            fontSize = 14.sp
                        )
                    }
                    Button(
                        onClick = {
                            onConfirm()
                            onDismiss()
                        },
                        modifier = Modifier.Companion.weight(1f).height(42.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = confirmButtonColor,
                            contentColor = Color.Companion.White
                        ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            confirmText,
                            fontWeight = FontWeight.Companion.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(
    itemName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    isVisible: Boolean = true
) {
    CustomAlertDialog(
        title = "Hapus $itemName?",
        message = "Tindakan ini tidak dapat dibatalkan. Item akan dihapus secara permanen.",
        confirmText = "Hapus",
        dismissText = "Batal",
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        isVisible = isVisible,
        confirmButtonColor = Color(0xFFD32F2F)
    )
}

@Composable
fun SuccessDialog(
    title: String = "Berhasil",
    message: String,
    onDismiss: () -> Unit,
    buttonText: String = "OK",
    isVisible: Boolean = true
) {
    if (!isVisible) return

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface(
            modifier = Modifier.Companion.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            color = Color.Companion.White,
            tonalElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.Companion.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.Companion
                        .size(64.dp)
                        .background(Color(0xFF4CAF50), CircleShape),
                    contentAlignment = Alignment.Companion.Center
                ) {
                    Text(
                        text = "✓",
                        fontSize = 32.sp,
                        color = Color.Companion.White,
                        fontWeight = FontWeight.Companion.Bold
                    )
                }

                Spacer(modifier = Modifier.Companion.height(16.dp))

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Companion.Bold,
                    color = Color(0xFF1A1A2E),
                    textAlign = TextAlign.Companion.Center
                )

                Spacer(modifier = Modifier.Companion.height(8.dp))

                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Companion.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.Companion.height(24.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.Companion.fillMaxWidth().height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                ) {
                    Text(buttonText, fontWeight = FontWeight.Companion.SemiBold, fontSize = 14.sp)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCustomAlertDialog() {
    CustomAlertDialog(
        title = "Hapus Item",
        message = "Apakah Anda yakin ingin menghapus item ini?",
        onConfirm = {},
        onDismiss = {}
    )
}