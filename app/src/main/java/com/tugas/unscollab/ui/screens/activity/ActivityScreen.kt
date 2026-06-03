package com.tugas.unscollab.ui.screens.activity

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.ui.components.bottomBar.BottomBar
import com.tugas.unscollab.ui.components.header.HeaderScreen
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun ActivityScreen() {

    var selected by remember { mutableStateOf("My Team") }

    val backStack = LocalBackStack.current
    Scaffold(
        topBar = {
            HeaderScreen(
                title = "Activity",
                value = "",
                onValueChange = {},
                placeholder = "Search activity",
                onFilterClick = {},
                showFilterIcon = false
            )
        },
        bottomBar = {
            BottomBar(
                currentRoute = "activity",
                onNavigate = { route ->
                    when(route) {
                        "home" -> backStack.add(Routes.HomeRoute)
                        "activity" -> backStack.add(Routes.ActivityRoute)
                        "notification" -> backStack.add(Routes.NotificationRoute)
                        "profile" -> backStack.add(Routes.ProfileRoute)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            ActivityTabSelected(
                selected = selected,
                onSelected = { selected = it }
            )
            when(selected) {
                "My Team" -> {
                    MyTeamContent()
                }

                "Internship" -> {
                    InternshipContent()
                }

                "Team" -> {
                    TeamContent()
                }
            }
        }
    }
}

@Composable
private fun ActivityTabSelected(
    selected: String,
    onSelected: (String) -> Unit
) {
    val tabs = listOf(
        "My Team",
        "Internship",
        "Team"
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color.White)
            .padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        tabs.forEach { tab ->
            val bgColor by animateColorAsState(
                targetValue =
                    if (selected == tab)
                        Color(0xFF1FABE1)
                    else
                        Color.Transparent,
                animationSpec = tween(durationMillis = 250),
                label = ""
            )

            val textColor by animateColorAsState(
                targetValue =
                    if (selected == tab)
                        Color.White
                    else
                        Color(0xFF6B7280),
                animationSpec = tween(durationMillis = 250),
                label = ""
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(bgColor)
                    .clickable {
                        onSelected(tab)
                    }
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = tab,
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight =
                        if (selected == tab)
                            FontWeight.Bold
                        else
                            FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun MyTeamContent() {

}

@Composable
private fun InternshipContent() {

}

@Composable
private fun TeamContent() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(all = 16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun ActivityScreenPreview() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.ActivityRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            ActivityScreen()
        }
    }
}