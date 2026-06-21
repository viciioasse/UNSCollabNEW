package com.tugas.unscollab.ui.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.ui.components.textField.SearchBar
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes

@Composable
fun HeaderScreen(
    title: String,
    placeholder: String,
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    showFilterIcon: Boolean = true
) {
    val backStack = LocalBackStack.current

    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(Color.White)
            .padding(
                top = 48.dp,
                bottom = 12.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {

        TitleBar(
            title = title,
            onBackClick = {
                backStack.removeLastOrNull()
            }
        )

        SearchSection(
            value = searchQuery,
            placeholder = placeholder,
            onValueChange = { searchQuery = it },
            onSearchClick = onSearchClick,
            onFilterClick = onFilterClick,
            showFilterIcon = showFilterIcon
        )
    }
}

@Composable
private fun TitleBar(
    title: String,
    onBackClick: () -> Unit = {},
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onBackClick()
                }
        )

        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
private fun SearchSection(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    showFilterIcon: Boolean = true
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                top = 20.dp
            )
    ) {
        SearchBar(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            modifier = Modifier
                .weight(1f)
        )

        if(showFilterIcon) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .border(
                        1.dp,
                        Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onFilterClick()
                    }
            ) {
                Icon(
                    imageVector = Icons.Outlined.FilterAlt,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .clickable{
                            onFilterClick()
                        }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.HomeRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            HeaderScreen(
                "Internship",
                "Search internship",
                {}
            )
        }
    }
}