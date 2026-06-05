package com.tugas.unscollab.ui.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ButtonDefaults
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
import com.tugas.unscollab.data.model.Internship
import com.tugas.unscollab.data.repository.CompanyRepository
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun InternshipCard(
    internship: Internship,

    isApplied: Boolean = false,
    dateApply: String? = null,
    statusInternship: String? = null,

    onClickApply: () -> Unit,
    onClickDelete: () -> Unit,

    modifier: Modifier = Modifier
) {
    val backStack = LocalBackStack.current

    val companyName = CompanyRepository.getCompanies().find {
        it.idCompany == internship.idCompany
    }?.companyName ?: "Company Not Found"

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
                                id = internship.idInternship
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
                    workMode = internship.workMode,
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
                onClickApply = onClickApply,
                onClickDelete = onClickDelete
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
                "Accepted" -> Color(0xFF2E7D32)
                "Pending" -> Color(0xFFF8E05D)
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
    onClickApply: () -> Unit,
    onClickDelete: () -> Unit
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

                OutlinedButton(
                    onClick = onClickDelete,
                    shape = RoundedCornerShape(8.dp),
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
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
            } else {
                Text(
                    text = "Closed: $deadline",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )

                OutlinedButton(
                    onClick = onClickApply,
                    shape = RoundedCornerShape(8.dp),
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
                ) {
                    Text(
                        text = "Apply",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInternshipCard() {
    UNSCollabTheme {

        val backStack = rememberNavBackStack(Routes.HomeRoute)

        CompositionLocalProvider(
            LocalBackStack provides backStack
        ) {

            InternshipCard(
                internship = Internship(
                    idInternship = 1,
                    idCompany = 1,
                    title = "Android Developer Intern",
                    description = "Membantu pengembangan aplikasi Android Tokopedia.",
                    requirement = "Menguasai Kotlin.",
                    benefit = "Sertifikat, mentoring, uang saku.",
                    quota = 3,
                    location = "Jawa Tengah",
                    workMode = "Hybrid",
                    duration = "4 Bulan",
                    paymentStatus = "Paid",
                    deadline = "2026-07-01",
                    image = null
                ),

                isApplied = true,
                dateApply = "2026-06-30",
                statusInternship = "Accepted",

                onClickApply = {},
                onClickDelete = {}
            )
        }
    }
}