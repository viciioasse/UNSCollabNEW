package com.tugas.unscollab.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.ui.components.bottomBar.BottomBar
import com.tugas.unscollab.ui.components.card.InternshipCard
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.R
import com.tugas.unscollab.data.model.Internship
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.ui.components.textField.SearchBar
import com.tugas.unscollab.ui.components.text.SectionHeader
import com.tugas.unscollab.ui.components.card.TeamCard
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.viewmodel.InternshipViewModel
import com.tugas.unscollab.viewmodel.TeamViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    internshipViewModel: InternshipViewModel = viewModel(),
    teamViewModel: TeamViewModel = viewModel()
) {
    val internships by internshipViewModel.internships.collectAsState()
    val teams by teamViewModel.teams.collectAsState()

    val newestInternship = internships.takeLast(5).reversed()
    val newestTeams = teams.takeLast(6).reversed()

    val backStack = LocalBackStack.current

    Scaffold(
        bottomBar = {
            BottomBar(
                currentRoute = "home",
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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(all = 16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F6FA))
        ) {
            item {
                HeaderSection()
            }
            item {
                InternshipSection(
                    internships = newestInternship,
                    backStack = backStack
                )
            }
            item {
                TeamSection(
                    teams = newestTeams,
                    backStack = backStack
                )
            }
            item {
                ButtonCreateTeam(
                    onClick = { backStack.add(Routes.CreateTeamRoute) }
                )
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    var search by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.logo_unscollab),
            contentDescription = null
        )

        SearchBar(
            value = search,
            onValueChange = { search = it },
            placeholder = "Search internship or team"
        )
    }
}

@Composable
private fun InternshipSection(
    internships: List<Internship>,
    backStack: NavBackStack<NavKey>
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionHeader(
            title = "See All Internships",
            onAllClick = { backStack.add(Routes.AllInternshipRoute) }
        )
        if(internships.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(text = "No internship available")
            }
        } else {
            internships.forEach { internship ->
                InternshipCard(
                    internship = internship,

                    isApplied = false,
                    dateApply = null,
                    statusInternship = null,

                    onClickApply = {},
                    onClickDelete = {}
                )
            }
        }
    }
}

@Composable
private fun TeamSection(
    teams: List<Team>,
    backStack: NavBackStack<NavKey>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionHeader(
            title = "See All Teams",
            onAllClick = { backStack.add(Routes.AllTeamRoute) }
        )

        if(teams.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(text = "No team available")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                userScrollEnabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(720.dp)
            ) {
                items(teams) { team ->
                    TeamCard(
                        team = team,

                        isJoin = false,
                        dateJoin = null,
                        statusJoin = null,

                        onClickJoin = {},
                        onClickDelete = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun ButtonCreateTeam(
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = onClick,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                1.dp,
                Color(0xFF1FABE1)
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color(0xFF1FABE1)
                )

                Text(
                    text = "Create Team",
                    color = Color(0xFF1FABE1),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.HomeRoute)
            CompositionLocalProvider(LocalBackStack provides backStack) {
                HomeScreen()
            }
    }
}
