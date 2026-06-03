package com.tugas.unscollab.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tugas.unscollab.R

@Composable
fun LogoUNSCollab(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.logo_unscollab),
        contentDescription = null,
        modifier = modifier
            .size(300.dp)
    )
}

@Preview
@Composable
fun PreviewLogoUNSCollab() {
    LogoUNSCollab()
}