package com.tugas.unscollab.ui.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.rememberNavBackStack
import coil.compose.AsyncImage
import com.tugas.unscollab.R
import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.data.response.TeamResponse
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes

@Composable
fun MyTeamCard(
    teamResponse: TeamResponse,
    onClickAccept: (Student) -> Unit,
    onClickDecline: (Student) -> Unit,
    onClickDelete: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(8.dp)
        ) {
            HeaderTeam(
                idTeam = teamResponse.team.id_team,
                imageUrl = teamResponse.team.team_logo,
                teamName = teamResponse.team.team_name,
                category = teamResponse.team.category,
                currentmember = teamResponse.currentMember,
                maxMember = teamResponse.team.max_member,
                onClickDelete = onClickDelete
            )

            if(teamResponse.members.isNotEmpty()) {
                HorizontalDivider(
                    color = Color.LightGray
                )

                RequestJoin(
                    requests = teamResponse.members,
                    onClickAccept = onClickAccept,
                    onClickDecline = onClickDecline
                )
            }
        }
    }
}

@Composable
private fun HeaderTeam(
    idTeam: String,
    imageUrl: String? = null,
    teamName: String,
    category: String,
    currentmember: Int,
    maxMember: Int,
    onClickDelete: () -> Unit
) {
    val backStack = LocalBackStack.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                backStack.add(
                    Routes.TeamDetailRoute(id = idTeam)
                )
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.logo_unscollab),
                error = painterResource(R.drawable.logo_unscollab),
                modifier = Modifier
                    .size(64.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = teamName,
                    fontSize = 16.sp,
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

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Member",
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Group,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                )

                Text(
                    text = "$currentmember/$maxMember",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun RequestJoin(
    requests: List<Student>,
    onClickAccept: (Student) -> Unit,
    onClickDecline: (Student) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Request Join",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        requests.forEach {request ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = request.profile_picture,
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.logo_unscollab),
                        error = painterResource(R.drawable.logo_unscollab),
                        modifier = Modifier
                            .size(48.dp)
                    )

                    Column() {
                        Text(
                            text = request.full_name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Text(
                            text = request.nim.toString(),
                            fontSize = 10.sp,
                            color = Color.Gray
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    OutlinedButton(
                        onClick = { onClickAccept(request) },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFF2E7D32))
                    ) {
                        Text(
                            text = "Accept",
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }

                    OutlinedButton(
                        onClick = { onClickDecline(request) },
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFFFF3B30))
                    ) {
                        Text(
                            text = "Decline",
                            color = Color(0xFFFF3B30),
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyTeamCardPreview() {
    val team = Team(
        id_team = "1",
        id_creator = "1",
        team_name = "UNSCollab Mobile Team",
        category = "Mobile Development",
        description = "Membuat aplikasi kolaborasi mahasiswa",
        requirement = "Kotlin & Compose",
        max_member = 10,
        deadline = "2026-06-10",
        tag = "Android, Kotlin, Java",
        created_at = "2026-06-01",
        team_logo = null
    )

    val requests = listOf(
        Student(
            id_student = "1",
            id_user = "1",
            full_name = "Budi Santoso",
            nim = "M0522001",
            major = "Informatika"
        ),
        Student(
            id_student = "2",
            id_user = "2",
            full_name = "Andi Wijaya",
            nim = "M0522002",
            major = "Informatika"
        )
    )

    val teamResponse = TeamResponse(
        team = team,
        creatorName = "Creator Preview",
        currentMember = 4,
        members = requests
    )

    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.HomeRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            MyTeamCard(
                teamResponse = teamResponse,
                onClickAccept = {},
                onClickDecline = {}
            )
        }
    }
}