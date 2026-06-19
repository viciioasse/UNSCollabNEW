package com.tugas.unscollab.ui.components.header

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun HeaderDetail(
    title: String,

    onBookmarkClick: (Boolean) -> Unit = {},

    modifier: Modifier = Modifier
) {
    var isBookmark by rememberSaveable {
        mutableStateOf(false)
    }

    val backStack = LocalBackStack.current
    val context = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                top = 48.dp,
                bottom = 12.dp,
                start = 8.dp,
                end = 8.dp
            )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    backStack.removeLastOrNull()
                }
        )

        Text(
            text = title,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector =
                    if (isBookmark)
                        Icons.Filled.Bookmark
                    else
                        Icons.Outlined.BookmarkBorder,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        isBookmark = !isBookmark
                        onBookmarkClick(isBookmark)
                    }
            )

            Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "This is my text to send."
                            )
                            type = "text/plain"
                        }

                        context.startActivity(
                            Intent.createChooser(
                                sendIntent,
                                "Share Via"
                            )
                        )
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderDetailPreview() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.HomeRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            HeaderDetail(
                title = "Internship Detail"
            )
        }
    }
}