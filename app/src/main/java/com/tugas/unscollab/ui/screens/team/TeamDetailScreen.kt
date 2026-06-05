package com.tugas.unscollab.ui.screens.team


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.rememberNavBackStack
import coil.compose.AsyncImage
import com.tugas.unscollab.R
import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.data.repository.StudentRepository
import com.tugas.unscollab.data.repository.TeamMemberRepository
import com.tugas.unscollab.data.repository.TeamRepository
import com.tugas.unscollab.ui.components.CustomAlertDialog
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.header.HeaderDetail
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.TeamViewModel

@Composable
fun TeamDetailScreen(route: Routes.TeamDetailRoute) {
    val team = TeamRepository.getTeams().find { it.idTeam == route.id }

    Scaffold(
        topBar = {
            HeaderDetail(
                title = "Detail Team"
            )
        }
    ) {innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(all = 16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F6FA))
        ) {
            item {
                TeamDetailContent(
                    team = team!!,
                    onClickJoin = {}
                )
            }
        }
    }
}

@Composable
private fun TeamDetailContent(
    team: Team,
    onClickJoin: () -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    val leaderTeam = StudentRepository.getStudents().find {
        it.idStudent == team.creatorId
    }?.fullName ?: "Leader Team Not Found"

    val currentMember = TeamMemberRepository.getTeamMembers().count {
        it.idTeam == team.idTeam && it.joinStatus == "Accepted"
    }

    val member = TeamMemberRepository.getTeamMembers().filter{
        it.idTeam == team.idTeam && it.joinStatus == "Accepted"
    }
        .mapNotNull { teamMember ->
            StudentRepository.getStudents().find {
                it.idStudent == teamMember.idStudent
            }
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleSection(
            imageUrl = team.teamLogo,
            nameTeam = team.teamName,
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
            leaderTeam = leaderTeam,
            currentMember = currentMember,
            maxMember = team.maxMember
        )

        ContentSection(
            icon = Icons.AutoMirrored.Default.ViewList,
            title = "Requirement",
            value = team.requirement
        )

        MemberSection(
            member = member
        )

        PrimaryButton(
            text = "Join Team",
            onButtonClick = {
                showDialog = true
            },
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
    tag: List<String>
) {
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

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            tag.forEach { item ->
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
                    model = member.profilePicture,
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
                        text = member.fullName,
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
        val backStack = rememberNavBackStack(Routes.TeamDetailRoute(id = 1))
        CompositionLocalProvider(LocalBackStack provides backStack) {
            TeamDetailScreen(route = Routes.TeamDetailRoute(id = 1))
        }
    }
}
