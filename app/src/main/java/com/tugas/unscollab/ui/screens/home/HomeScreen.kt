package com.tugas.unscollab.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.ui.components.bottomBar.BottomBar
import com.tugas.unscollab.ui.components.card.InternshipCard
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.R
import com.tugas.unscollab.data.model.Internship
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.data.response.InternshipResponse
import com.tugas.unscollab.data.response.TeamResponse
import com.tugas.unscollab.ui.components.textField.SearchBar
import com.tugas.unscollab.ui.components.text.SectionHeader
import com.tugas.unscollab.ui.components.card.TeamCard
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.viewmodel.home.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val internships by homeViewModel.internships.collectAsState()
    val isLoadingInternship by homeViewModel.isLoadingInternship.collectAsState()
    val errorInternship by homeViewModel.errorInternship.collectAsState()

    val teams by homeViewModel.teams.collectAsState()
    val isLoadingTeam by homeViewModel.isLoadingTeam.collectAsState()
    val errorTeam by homeViewModel.errorTeam.collectAsState()

    // State pencarian di-hoist (ditaruh) di sini
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val backStack = LocalBackStack.current

    LaunchedEffect(Unit) {
        homeViewModel.fetchInternships()
        homeViewModel.fetchTeams()
    }

    // Filter List berdasarkan searchQuery
    val filteredInternships = internships.filter {
        it.internship.title.contains(searchQuery, ignoreCase = true) ||
                it.companyName.contains(searchQuery, ignoreCase = true)
    }.takeLast(5).reversed()

    val filteredTeams = teams.filter {
        it.team.team_name.contains(searchQuery, ignoreCase = true) ||
                it.team.category.contains(searchQuery, ignoreCase = true)
    }.takeLast(6).reversed()

    HomeScreenContent(
        internships = filteredInternships,
        isLoadingInternship = isLoadingInternship,
        errorInternship = errorInternship,
        teams = filteredTeams,
        isLoadingTeam = isLoadingTeam,
        errorTeam = errorTeam,
        backStack = backStack,
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it }
    )
}

@Composable
fun HomeScreenContent(
    internships: List<InternshipResponse>,
    isLoadingInternship: Boolean,
    errorInternship: String?,
    teams: List<TeamResponse>,
    isLoadingTeam: Boolean,
    errorTeam: String?,
    backStack: NavBackStack<NavKey>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
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
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F6FA))
        ) {
            item {
                HeaderSection(
                    searchQuery = searchQuery,
                    onSearchQueryChange = onSearchQueryChange
                )
            }

            item {
                SectionHeader(
                    title = "See All Internships",
                    onAllClick = { backStack.add(Routes.AllInternshipRoute) }
                )
                when {
                    isLoadingInternship ->
                        CircularProgressIndicator()

                    errorInternship != null ->
                        Text(
                            "Error: $errorInternship",
                            color = Color.Red
                        )

                    internships.isEmpty() -> Text("No internship available")
                    else -> Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        internships.forEach { internshipResponse ->
                            InternshipCard(
                                internshipResponse = internshipResponse,
                                actionButton = { }
                            )
                        }
                    }
                }
            }

            item {
                SectionHeader(
                    title = "See All Teams",
                    onAllClick = { backStack.add(Routes.AllTeamRoute) }
                )
                when {
                    isLoadingTeam -> CircularProgressIndicator()
                    errorTeam != null -> Text("Error: $errorTeam", color = Color.Red)
                    teams.isEmpty() -> Text("No team available")
                    else -> LazyVerticalGrid(
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
                                teamResponse = team,
                                actionButton = { }
                            )
                        }
                    }
                }
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
private fun HeaderSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.logo_unscollab),
            contentDescription = null
        )

        SearchBar(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = "Search internship or team"
        )
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
        ) {
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
            HomeScreenContent(
                internships = listOf(
                    InternshipResponse(
                        internship = Internship(
                            id_internship = "1",
                            id_company = "1",
                            id_admin = "1",
                            title = "Software Engineer Intern",
                            description = "Description",
                            requirement = "Requirement",
                            benefit = "Benefit",
                            approval_status = "Approved",
                            quota = 5,
                            location = "Jakarta",
                            work_mode = "Remote",
                            duration = "3 Months",
                            payment_status = "Paid",
                            deadline = "2023-12-31",
                            posted_at = "2023-01-01"
                        ),
                        companyName = "Tech Corp"
                    )
                ),
                isLoadingInternship = false,
                errorInternship = null,
                teams = listOf(
                    TeamResponse(
                        team = Team(
                            id_team = "1",
                            id_creator = "1",
                            team_name = "Tech Innovators",
                            category = "Competition",
                            description = "Description",
                            requirement = "Requirement",
                            max_member = 5,
                            deadline = "2023-12-31",
                            tag = "Android, Kotlin",
                            created_at = "2023-01-01"
                        ),
                        creatorName = "John Doe",
                        currentMember = 2,
                        members = emptyList()
                    )
                ),
                isLoadingTeam = false,
                errorTeam = null,
                backStack = backStack,
                searchQuery = "",
                onSearchQueryChange = {}
            )
        }
    }
}