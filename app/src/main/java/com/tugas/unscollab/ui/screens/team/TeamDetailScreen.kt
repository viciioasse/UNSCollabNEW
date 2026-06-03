package com.tugas.unscollab.ui.screens.team


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.tugas.unscollab.R
import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.repository.TeamRepository
import com.tugas.unscollab.ui.components.CustomAlertDialog
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.header.HeaderDetail
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.TeamViewModel

@Composable
fun TeamDetailScreen(
    route: Routes.TeamDetailRoute = Routes.TeamDetailRoute(id = 1)
) {
    val team = TeamRepository().getTeam().find { it.idTeam == route.id } ?: return

    Scaffold(
        topBar = {
            HeaderDetail(
                title = "Detail Team"
            )
        }
    ) {innerPadding ->
        if (team != null) {
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
                        nameTeam = team.nameTeam,
                        leaderTeam = team.leaderTeam.fullName,
                        category = team.category,
                        description = team.description,
                        maxMember = team.maxMember,
                        currentMember = team.currentMember,
                        deadline = team.deadline,
                        requirement = team.requirement,
                        tag = team.tag,
                        member = team.member
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Loading team details...")
            }
        }
    }
}

@Composable
private fun TeamDetailContent(
    nameTeam: String,
    leaderTeam: String,
    category: String,
    description: String,
    maxMember: Int,
    currentMember: Int,
    deadline: String,
    requirement: String,
    tag: List<String>,
    member: List<Student>
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleSection(
            nameTeam = nameTeam,
            category = category,
            tag = tag
        )

        ContentSection(
            icon = Icons.Default.Description,
            title = "Description",
            value = description
        )

        InformationSection(
            deadline = deadline,
            leaderTeam = leaderTeam,
            maxMember = maxMember,
            currentMember = currentMember
        )

        ContentSection(
            icon = Icons.AutoMirrored.Default.ViewList,
            title = "Requirement",
            value = requirement
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
            Image(
                painter = painterResource(R.drawable.logo_uns),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(8.dp))
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
            member = member
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
    member: List<Student>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        member.forEach { item ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_uns),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Column() {
                    Text(
                        text = item.fullName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = item.nim,
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
            TeamDetailScreen()
        }
    }
}
