package com.tugas.unscollab.ui.screens.internship

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LaptopWindows
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.data.repository.InternshipRepository
import com.tugas.unscollab.ui.components.header.HeaderDetail
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun InternshipDetailScreen(route: Routes.InternshipDetailRoute = Routes.InternshipDetailRoute(id = 1)) {
    val internship = InternshipRepository().getInternship().find { it.idInternship == route.id } ?: return

    Scaffold(
        topBar = {
            HeaderDetail(
                title = "Detail Internship"
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
                InternshipDetailContent(
                    image = internship.image,
                    title = internship.title,
                    companyName = internship.companyName,
                    workMode = internship.workMode,
                    location = internship.location,
                    duration = internship.duration ?: "",
                    description = internship.description ?: "",
                    deadline = internship.deadline,
                    status = internship.vacancyStatus ?: "",
                    paymentStatus = internship.paymentStatus ?: "",
                    internshipQuota = internship.internshipQuota,
                    requirement = internship.requirement ?: "",
                    benefit = internship.benefit ?: ""
                )
            }
        }
    }
}

@Composable
private fun InternshipDetailContent(
    image: Int,
    title: String,
    companyName: String,
    workMode: String,
    location: String,
    duration: String,
    description: String,
    deadline: String,
    status: String,
    paymentStatus: String,
    internshipQuota: Int,
    requirement: String,
    benefit: String

) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitleSection(
            image = image,
            title = title,
            companyName = companyName,
            workMode = workMode,
            location = location,
            duration = duration
        )

        ContentSection(
            icon = Icons.Default.Description,
            title = "Description",
            value = description
        )

        InformationSection(
            deadline = deadline,
            status = status,
            paymentStatus = paymentStatus,
            internshipQuota = internshipQuota,
        )

        ContentSection(
            icon = Icons.AutoMirrored.Filled.ViewList,
            title = "Requirement",
            value = requirement
        )

        ContentSection(
            icon = Icons.Default.Work,
            title = "Benefit",
            value = benefit
        )

        PrimaryButton(
            text = "Apply Now",
            onButtonClick = {},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun TitleSection(
    image: Int,
    title: String,
    companyName: String,
    workMode: String,
    location: String,
    duration: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = companyName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        Color(0xFFC6F9BF),
                        shape = RoundedCornerShape(50))
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LaptopWindows,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = workMode,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        Color(0xFFE1D7FF),
                        shape = RoundedCornerShape(50))
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = location,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        Color(0xFFCCE9FF),
                        shape = RoundedCornerShape(50))
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = duration,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
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
    status: String,
    paymentStatus: String,
    internshipQuota: Int
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
                icon = Icons.Default.Lock,
                title = "Status",
                value = status,
                value2 = paymentStatus
            )

            InfoDetail(
                icon = Icons.Default.Person,
                title = "Quota",
                value = "$internshipQuota people"
            )
        }
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
    Column() {
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
                        "$value - $value2",
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInternshipDetailScreen() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.InternshipDetailRoute(id = 1))
        CompositionLocalProvider(LocalBackStack provides backStack) {
            InternshipDetailScreen()
        }
    }
}