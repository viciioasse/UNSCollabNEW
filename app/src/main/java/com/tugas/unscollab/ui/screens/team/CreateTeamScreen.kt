package com.tugas.unscollab.ui.screens.team

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.ui.components.textField.CustomTextField
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun CreateTeamScreen() {
    Scaffold(
        topBar = {
            HeaderCreateTeam()
        }
    ) {innerPadding ->

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(all = 16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            item {
                CreateTeamContent()
            }
        }
    }
}

@Composable
private fun HeaderCreateTeam() {
    val backStack = LocalBackStack.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                top = 48.dp,
                bottom = 12.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    backStack.add(Routes.HomeRoute)
                }
        )

        Text(
            text = "Create Team",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun CreateTeamContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        UploadImageField()

        FormCreateTeam(
            title = "Team Name",
            placeholder = "Enter Name"
        )

        FormCreateTeam(
            title = "Category",
            placeholder = "Enter Category"
        )

        FormCreateTeam(
            title = "Maximum Members",
            placeholder = "Enter Max Members"
        )

        FormCreateTeam(
            title = "Deadline",
            placeholder = "Enter Deadline"
        )

        FormCreateTeam(
            title = "Description",
            placeholder = "Enter Description",
            modifier = Modifier
                .height(150.dp)
        )

        FormCreateTeam(
            title = "Requirement",
            placeholder = "Enter Requirement",
            modifier = Modifier
                .height(150.dp)
        )

        PrimaryButton(
            text = "Create Team",
            onButtonClick = {},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun UploadImageField() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(200.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {

            }
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CloudUpload,
                contentDescription = null,
                tint = Color(0xFF1FABE1),
                modifier = Modifier
                    .size(32.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Upload Team Logo",
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "PNG, JPG (Max 2MB)",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun FormCreateTeam(
    title: String,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        CustomTextField(
            value = "",
            onValueChange = {},
            placeholder = placeholder,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateTeamScreenPreview() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.CreateTeamRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            CreateTeamScreen()
        }
    }
}