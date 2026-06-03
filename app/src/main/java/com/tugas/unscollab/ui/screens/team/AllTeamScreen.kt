package com.tugas.unscollab.ui.screens.team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.ui.components.bottomSheet.CustomBottomSheet
import com.tugas.unscollab.ui.components.header.HeaderScreen
import com.tugas.unscollab.ui.components.card.TeamCard
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.TeamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTeamScreen(
    modifier: Modifier = Modifier,
    teamViewModel: TeamViewModel = viewModel()
) {
    val teams by teamViewModel.teams.collectAsState()
    val newestTeams = teams.reversed()

    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            HeaderScreen(
                title = "Team",
                value = "",
                onValueChange = {},
                placeholder = "Search team",
                onFilterClick = {
                    isBottomSheetOpen = true
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F6FA)),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(all = 16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(newestTeams) { team ->
                    TeamCard(
                        team = team,
                        onClickJoin = {
                            // Handle join logic
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    CustomBottomSheet(
        isBottomSheetOpen = isBottomSheetOpen,
        onDismiss = { isBottomSheetOpen = false },
        sheetState = sheetState,
        CustomFilter = { }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAllTeamScreen() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.AllTeamRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            AllTeamScreen()
        }
    }
}
