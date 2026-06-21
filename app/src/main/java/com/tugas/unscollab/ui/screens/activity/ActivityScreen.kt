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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.data.response.ApplicationResponse
import com.tugas.unscollab.data.response.JoinTeamResponse
import com.tugas.unscollab.data.response.TeamResponse
import com.tugas.unscollab.ui.components.bottomBar.BottomBar
import com.tugas.unscollab.ui.components.button.deleteButton
import com.tugas.unscollab.ui.components.card.InternshipCard
import com.tugas.unscollab.ui.components.card.MyTeamCard
import com.tugas.unscollab.ui.components.card.TeamCard
import com.tugas.unscollab.ui.components.header.HeaderScreen
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.activity.ActivityViewModel

@Composable
fun ActivityScreen(
    activityViewModel: ActivityViewModel = hiltViewModel()
) {

    var selected by remember { mutableStateOf("My Team") }

    val isLoading by activityViewModel.isLoading.collectAsState()
    val errorMessage by activityViewModel.errorMessage.collectAsState()

    val myCreatedTeams by activityViewModel.myCreatedTeams.collectAsState()
    val appliedInternships by activityViewModel.appliedInternships.collectAsState()
    val requestedTeams by activityViewModel.requestedTeams.collectAsState()

    ActivityScreenContent(
        selected = selected,
        onSelected = { selected = it },
        isLoading = isLoading,
        errorMessage = errorMessage,
        myCreatedTeams = myCreatedTeams,
        appliedInternships = appliedInternships,
        requestedTeams = requestedTeams,
        activityViewModel = activityViewModel
    )
}

@Composable
private fun ActivityScreenContent(
    selected: String,
    onSelected: (String) -> Unit = {},
    isLoading: Boolean,
    errorMessage: String?,
    myCreatedTeams: List<TeamResponse>,
    appliedInternships: List<ApplicationResponse>,
    requestedTeams: List<JoinTeamResponse>,
    activityViewModel: ActivityViewModel = hiltViewModel()
) {
    val backStack = LocalBackStack.current

    Scaffold(
        topBar = {
            HeaderScreen(
                title = "Activity",
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
        when {
            isLoading -> {
                Box(Modifier.fillMaxSize().padding(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Text(text = errorMessage)
                }
            }
            else ->
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
                        onSelected = onSelected
                    )
                    when(selected) {
                        "My Team" -> {
                            MyTeamContent(
                                teams = myCreatedTeams.reversed(),
                                onAccept = { idTeam, idStudent ->
                                    activityViewModel.handleMemberRequest(idTeam, idStudent, true)
                                },
                                onDecline = { idTeam, idStudent ->
                                    activityViewModel.handleMemberRequest(idTeam, idStudent, false)
                                }
                            )
                        }

                        "Internship" -> {
                            InternshipContent(
                                internships = appliedInternships.reversed(),
                                onDelete = { activityViewModel.removeInternshipFromUI(it) }
                            )
                        }

                        "Team" -> {
                            TeamContent(
                                teams = requestedTeams.reversed(),
                                onDelete = { activityViewModel.removeRequestedTeamFromUI(it) },
                            )
                        }
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
private fun MyTeamContent(
    teams: List<TeamResponse> = emptyList(),
    onAccept: (idTeam: String, idStudent: String) -> Unit,
    onDecline: (idTeam: String, idStudent: String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(all = 16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(teams) { teamResponse ->
            MyTeamCard(
                teamResponse = teamResponse,
                onClickAccept = { student ->
                    onAccept(teamResponse.team.id_team, student.id_student)
                },
                onClickDecline = {student ->
                    onDecline(teamResponse.team.id_team, student.id_student)
                }
            )
        }
    }
}

@Composable
private fun InternshipContent(
    internships: List<ApplicationResponse> = emptyList(),
    onDelete: (ApplicationResponse) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(all = 16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(internships) { internship ->
            val formattedDate = internship.dateApply.split("T").firstOrNull() ?: internship.dateApply
            InternshipCard(
                applicationResponse = internship.copy(dateApply = formattedDate),
                actionButton = {
                    deleteButton {
                        onDelete(internship)
                    }
                }
            )

        }
    }
}

@Composable
private fun TeamContent(
    teams: List<JoinTeamResponse> = emptyList(),
    onDelete: (JoinTeamResponse) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(all = 16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(teams) { team ->
            val formattedDate = team.dateJoin.split("T").firstOrNull() ?: team.dateJoin
            TeamCard(
                joinTeamResponse = team.copy(dateJoin = formattedDate),
                actionButton = {
                    deleteButton {
                        onDelete(team)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityScreenPreview() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.ActivityRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            ActivityScreenContent(
                selected = "My Team",
                onSelected = {},
                isLoading = false,
                errorMessage = null,
                myCreatedTeams = listOf(),
                appliedInternships = listOf(),
                requestedTeams = listOf()
            )
        }
    }
}
