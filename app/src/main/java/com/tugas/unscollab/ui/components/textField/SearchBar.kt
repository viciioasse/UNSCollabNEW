package com.tugas.unscollab.ui.components.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier.Companion
) {
    Row(
        verticalAlignment = Alignment.Companion.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(
                color = Color.Companion.White,
                shape = RoundedCornerShape(50)
            )
            .border(
                1.dp,
                Color.Companion.LightGray,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(50)
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )

    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 10.sp,
                color = Color.Companion.Black
            ),
            modifier = modifier
                .weight(1f)
                .align(Alignment.Companion.CenterVertically),
            decorationBox = { innerTextField ->

                Box(
                    contentAlignment = Alignment.Companion.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 10.sp,
                            color = Color.Companion.LightGray
                        )
                    }
                    innerTextField()
                }
            }
        )

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.Companion.LightGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBar() {
    UNSCollabTheme {
        SearchBar(
            value = "",
            onValueChange = {},
            placeholder = "Search internship or team"
        )
    }
}