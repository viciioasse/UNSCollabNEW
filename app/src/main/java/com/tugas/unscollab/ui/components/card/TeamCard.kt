package com.tugas.unscollab.ui.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.R
import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.data.model.User
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun TeamCard(
    team: Team,
    onClickJoin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backStack = LocalBackStack.current

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .width(175.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .clickable {
                        backStack.add(Routes.TeamDetailRoute(id = team.idTeam))
                    }
            ) {
                ImageTeam()

                ContentTeam(team)
            }

            HorizontalDivider(
                color = Color.LightGray
            )

            FooterTeam(
                currentmember = team.currentMember,
                maxMember = team.maxMember,
                onClickJoin = onClickJoin
            )
        }
    }
}

@Composable
private fun ImageTeam() {
    Image(
        painter = painterResource(R.drawable.logo_uns),
        contentDescription = null,
        modifier = Modifier
            .size(32.dp)
    )
}

@Composable
private fun ContentTeam(
    team: Team
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = team.nameTeam,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = team.category,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = if (team.description.length > 40) {
                    team.description.substring(0, 40) + "..."
                } else {
                    team.description
                },
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun FooterTeam(
    currentmember: Int,
    maxMember: Int,
    onClickJoin: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "$currentmember/$maxMember Members",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray
        )

        OutlinedButton(
            onClick = onClickJoin,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
            border = BorderStroke(
                1.dp,
                Color.LightGray
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(32.dp)
                .width(48.dp)
        ) {
            Text(
                text = "Join",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TeamCardPreview() {
    UNSCollabTheme {

        val backStack = rememberNavBackStack(Routes.AllTeamRoute)

        CompositionLocalProvider(LocalBackStack provides backStack) {

            TeamCard(
                team = Team(
                    idTeam = 1,
                    nameTeam = "UNSCollab Mobile Team",
                    category = "Mobile Development",
                    description = "Membuat aplikasi kolaborasi mahasiswa menggunakan Jetpack Compose dan Firebase.",
                    requirement = "Menguasai Kotlin dan Jetpack Compose",
                    maxMember = 10,
                    currentMember = 5,
                    deadline = "10 Juni 2026",
                    tag = listOf(
                        "Kotlin",
                        "Compose",
                        "Firebase"
                    ),
                    createAt = "2 Juni 2026",
                    leaderTeam = Student(
                        idStudent = 1,
                        idUser = 1,
                        nim = "H123456789",
                        fullName = "John Doe",
                        major = "Informatika",
                        bio = "Android Developer",
                        portfolio = "github.com/johndoe",
                        experience = "2 Tahun Android Development",
                        skill = "Kotlin, Compose, Firebase",
                        profilePicture = "",
                    ),
                    member = listOf()
                ),
                onClickJoin = {}
            )
        }
    }
}
