package com.tugas.unscollab.ui.screens.team

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.rememberNavBackStack
import coil.compose.AsyncImage
import com.tugas.unscollab.R
import com.tugas.unscollab.data.local.SessionManager
import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.data.response.TeamResponse
import com.tugas.unscollab.ui.components.dialog.CustomAlertDialog
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.header.HeaderDetail
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.team.TeamDetailViewModel

@Composable
fun TeamDetailScreen(
    route: Routes.TeamDetailRoute,
    teamDetailViewModel: TeamDetailViewModel = viewModel ()
) {
    val context = LocalContext.current
    val backStack = LocalBackStack.current
    val sessionManager = remember { SessionManager(context) }

    val currentUserId by sessionManager.idUser.collectAsState(initial = null)
    val currentStudentId by teamDetailViewModel.currentStudentId.collectAsState()

    val selectedTeam by teamDetailViewModel.selectedTeam.collectAsState()
    val isLoading by teamDetailViewModel.isLoading.collectAsState()
    val errorMessage by teamDetailViewModel.errorMessage.collectAsState()

    val isJoined by teamDetailViewModel.isSuccess.collectAsState()

    var showSuccessDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isJoined) {
        teamDetailViewModel.joinSuccessEvent.collect {
            showSuccessDialog = true
        }
    }

    LaunchedEffect(route.id) {
        teamDetailViewModel.getTeamById(route.id)
    }

    Scaffold(
        topBar = {
            HeaderDetail(
                title = "Detail Team",
                onBookmarkClick = {}
            )
        }
    ) {innerPadding ->
        when {
            isLoading -> Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            errorMessage != null -> Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center) {
                Text(text = errorMessage ?: "Unknown Error", color = Color.Red)
            }
            selectedTeam != null -> LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF5F6FA))
            ) {
                item {
                    TeamDetailScreenContent(
                        teamResponse = selectedTeam!!,
                        isJoined = isJoined,
                        currentUserId = currentStudentId,
                        onClickJoin = {
                            teamDetailViewModel.joinTeam(selectedTeam!!.team.id_team)
                        },
                        onClickEdit = {
                            backStack.add(Routes.EditTeamRoute(id = selectedTeam!!.team.id_team))
                        }
                    )
                }
            }
        }
    }

    if (showSuccessDialog) {
        CustomAlertDialog(
            title = "Success",
            message = "Your request to join this team is sent.",
            confirmText = "OK",
            dismissText = "Yes",
            onConfirm = { showSuccessDialog = false },
            onDismiss = { showSuccessDialog = false }
        )
    }
}


@Composable
private fun TeamDetailScreenContent(
    teamResponse: TeamResponse,
    isJoined: Boolean,
    currentUserId: String?,
    onClickJoin: () -> Unit,
    onClickEdit: () -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    val team = teamResponse.team
    val creatorName = teamResponse.creatorName
    val currentMember = teamResponse.currentMember
    val members = teamResponse.members

    val isCreator = currentUserId == team.id_creator

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleSection(
            imageUrl = team.team_logo,
            nameTeam = team.team_name,
            category = team.category,
            tag = team.tag
        )

        ContentSection(
            icon = Icons.Default.Description,
            title = "Description",
            value = team.description
        )

        InformationSection(
            deadline = team.deadline,
            leaderTeam = creatorName,
            currentMember = currentMember,
            maxMember = team.max_member
        )

        ContentSection(
            icon = Icons.AutoMirrored.Default.ViewList,
            title = "Requirement",
            value = team.requirement
        )

        MemberSection(
            member = members
        )

        PrimaryButton(
            text = when {
                isCreator -> "Edit Team"
                isJoined -> "Joined"
                else -> "Join Team"
            },
            onButtonClick = {
                if (isCreator) {
                    onClickEdit()
                } else {
                    showDialog = true
                }
            },
            enabled = if (isCreator) true else !isJoined,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    if (showDialog) {
        CustomAlertDialog(
            title = "Request to join team",
            message = "Are you sure to join this team?",
            confirmText = "Yes",
            dismissText = "No",
            onConfirm = {
                onClickJoin()
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}

@Composable
private fun TitleSection(
    imageUrl: String? = null,
    nameTeam: String,
    category: String,
    tag: String // Diubah ke String untuk menerima data mentah
) {
    // Memproses string tag (misal: "Android, Kotlin") menjadi List
    val tagList = remember(tag) {
        if (tag.isBlank()) emptyList() 
        else tag.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.logo_unscollab),
                error = painterResource(R.drawable.logo_unscollab),
                fallback = painterResource(R.drawable.logo_unscollab),
                modifier = Modifier
                    .size(128.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "TEAM",
                    color = Color(0xFF2E7D32),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            color = Color(0xFFEEF7ED),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 8.dp)
                )

                Text(
                    text = nameTeam,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Text(
                    text = category,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
            }
        }

        if (tagList.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                tagList.forEach { item ->
                    Text(
                        text = item,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(
                                Color(0xFF1FABE1),
                                RoundedCornerShape(50)
                            )
                            .padding(start = 8.dp, end = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ContentSection(
    icon: ImageVector,
    title: String,
    value: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(all = 16.dp)
    ) {
        NameSection(
            icon = icon,
            title = title,
        )

        HorizontalDivider(
            color = Color.LightGray
        )

        Text(
            text = value,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun InformationSection(
    deadline: String,
    leaderTeam: String,
    maxMember: Int,
    currentMember: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(all = 16.dp)
            .fillMaxWidth()
    ) {
        NameSection(
            icon = Icons.Default.Info,
            title = "Information"
        )

        HorizontalDivider(
            color = Color.LightGray
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            InfoDetail(
                icon = Icons.Default.CalendarMonth,
                title = "Deadline",
                value = deadline
            )

            InfoDetail(
                icon = Icons.Default.Groups,
                title = "Member",
                value = "$currentMember/$maxMember"
            )

            InfoDetail(
                icon = Icons.Default.Person,
                title = "Leader",
                value = leaderTeam
            )
        }
    }
}

@Composable
private fun MemberSection(
    member: List<Student>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(all = 16.dp)
            .fillMaxWidth()
    ) {
        NameSection(
            icon = Icons.Default.PeopleAlt,
            title = "Member"
        )

        HorizontalDivider(
            color = Color.LightGray
        )

        DetailMember(
            members = member
        )
    }
}

@Composable
private fun NameSection(
    icon: ImageVector,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF1FABE1),
            modifier = Modifier
                .size(20.dp)
        )

        Text(
            text = title,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun InfoDetail(
    icon: ImageVector,
    title: String,
    value: String,
    value2: String? = null
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF1FABE1),
                modifier = Modifier
                    .size(16.dp)
            )

            Text(
                text = title,
                color = Color.Gray,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = if (value2 == null)
                        value
                    else
                        "$value/$value2",
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun DetailMember(
    members: List<Student>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        members.forEach { member ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {}
                    )
            ) {
                AsyncImage(
                    model = member.profile_picture,
                    contentDescription = null,
                    placeholder = painterResource(R.drawable.logo_unscollab),
                    error = painterResource(R.drawable.logo_unscollab),
                    fallback = painterResource(R.drawable.logo_unscollab),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(50))
                )

                Column() {
                    Text(
                        text = member.full_name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = member.nim.toString(),
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTeamDetailScreen() {
    UNSCollabTheme {
        // Mock data untuk navigasi agar tidak error
        val mockRoute = Routes.TeamDetailRoute(id = "1")
        val backStack = rememberNavBackStack(mockRoute)

        CompositionLocalProvider(LocalBackStack provides backStack) {
            // Kita bungkus dengan Scaffold agar HeaderDetail muncul di preview
            Scaffold(
                topBar = {
                    HeaderDetail(
                        title = "Detail Team",
                        onBookmarkClick = {}
                    )
                }
            ) { innerPadding ->
                // Box sebagai container utama dengan background abu-abu (sesuai Internship style)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F6FA))
                        .padding(innerPadding)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            // Langsung panggil Content dengan data dummy agar preview terisi
                            TeamDetailScreenContent(
                                teamResponse = TeamResponse(
                                    team = Team(
                                        id_team = "1",
                                        team_name = "Mobile Development Team",
                                        category = "Mobile Application",
                                        deadline = "2024-12-31",
                                        max_member = 5,
                                        description = "This is a team dedicated to building high-quality Android applications using Jetpack Compose and Kotlin.",
                                        requirement = "1. Understand Kotlin fundamentals\n2. Familiar with Jetpack Compose\n3. Able to work in a team",
                                        id_creator = "user1",
                                        tag = "Android, Kotlin, Compose, Firebase",
                                        team_logo = "https://example.com/logo.png",
                                        created_at = "2026-11-12"
                                    ),
                                    creatorName = "Budi Santoso",
                                    currentMember = 2,
                                    members = listOf(
                                        Student(
                                            id_student = "s1",
                                            full_name = "Budi Santoso",
                                            nim = "M0521001",
                                            profile_picture = "",
                                            id_user = "user1",
                                            major = "Informatika"
                                        ),
                                        Student(
                                            id_student = "s2",
                                            full_name = "Siti Aminah",
                                            nim = "M0521045",
                                            profile_picture = "",
                                            id_user = "user2",
                                            major = "Informatika"
                                        )
                                    )
                                ),
                                isJoined = false,
                                currentUserId = "user3", // Bukan creator agar tombol "Join Team" muncul
                                onClickJoin = {},
                                onClickEdit = {}
                            )
                        }
                    }
                }
            }
        }
    }
}
