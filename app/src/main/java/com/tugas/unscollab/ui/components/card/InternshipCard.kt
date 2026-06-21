package com.tugas.unscollab.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.tugas.unscollab.data.model.Internship
import com.tugas.unscollab.data.response.ApplicationResponse
import com.tugas.unscollab.data.response.InternshipResponse
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun InternshipCard(
    internshipResponse: InternshipResponse,
    actionButton: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    InternshipCardContent(
        internshipResponse = internshipResponse,
        actionButton = actionButton,
        modifier = modifier
    )
}

@Composable
fun InternshipCard(
    applicationResponse: ApplicationResponse,
    actionButton: @Composable () -> Unit = {},

    modifier: Modifier = Modifier
) {
    InternshipCardContent(
        internshipResponse = applicationResponse.internshipResponse,
        isApplied = true,
        dateApply = applicationResponse.dateApply,
        statusInternship = applicationResponse.statusInternship,
        actionButton = actionButton,
        modifier = modifier
    )
}

@Composable
private fun InternshipCardContent(
    internshipResponse: InternshipResponse,
    isApplied: Boolean = false,
    dateApply: String? = null,
    statusInternship: String? = null,
    actionButton: @Composable () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val backStack = LocalBackStack.current

    val internship = internshipResponse.internship
    val companyName = internshipResponse.companyName

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
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .clickable {
                        backStack.add(
                            Routes.InternshipDetailRoute(
                                id = internship.id_internship
                            )
                        )
                    }
            ) {
                HeaderInternship(
                    imageUrl = internship.image,
                    statusInternship = statusInternship
                )

                ContentInternship(
                    title = internship.title,
                    companyName = companyName,
                    workMode = internship.work_mode,
                    location = internship.location,
                    duration = internship.duration
                )
            }

            HorizontalDivider(
                color = Color.LightGray
            )

            FooterInternship(
                deadline = internship.deadline,
                isApplied = isApplied,
                dateApply = dateApply,
                actionButton = actionButton
            )
        }
    }
}

@Composable
private fun HeaderInternship(
    imageUrl: String? = null,
    statusInternship: String? = null
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
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
                .size(64.dp)
        )

        if(statusInternship != null) {
            val backgroundColor = when (statusInternship) {
                "accepted" -> Color(0xFF2E7D32)
                "pending" -> Color(0xFFF8E05D)
                else -> Color.Red
            }

            Text(
                text = statusInternship,
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 8.dp)
            )
        } else {
            Text(
                text = "INTERNSHIP",
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
        }
    }
}

@Composable
private fun ContentInternship(
    title: String,
    companyName: String,
    workMode: String,
    location: String,
    duration: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = "$companyName - $workMode",
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(12.dp)
                )

                Text(
                    text = location,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(12.dp)
                )

                Text(
                    text = duration,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun FooterInternship(
    deadline: String,
    isApplied: Boolean,
    dateApply: String? = null,
    actionButton: @Composable () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if(isApplied) {
                Text(
                    text = "Applied: $dateApply",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            } else {
                Text(
                    text = "Closed: $deadline",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            }

            actionButton()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInternshipCard() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.HomeRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            InternshipCard(
                InternshipResponse(
                    internship = Internship(
                        id_internship = "",
                        id_company = "",
                        id_admin = "",
                        title = "Internship",
                        description = "Description",
                        requirement = "Requirement",
                        benefit = "Benefit",
                        approval_status = null,
                        quota = 10,
                        location = "Location",
                        work_mode = "Work Mode",
                        duration = "Duration",
                        payment_status = "Payment Status",
                        deadline = "Deadline",
                        image = null,
                        supporting_document = null,
                        posted_at = "Posted At"
                    ),
                    companyName = "UNSCollab"
                )
            )
        }
    }
}