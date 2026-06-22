package com.tugas.unscollab.ui.screens.team

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.rememberNavBackStack
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tugas.unscollab.ui.components.textField.CustomTextField
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.dialog.CustomAlertDialog
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.team.CreateTeamViewModel

@Composable
fun EditTeamScreen(
    route: Routes.EditTeamRoute,
    editTeamViewModel: EditTeamViewModel = viewModel()
) {
    val backStack = LocalBackStack.current

    val teamData by editTeamViewModel.teamData.collectAsState()
    val errorMessage by editTeamViewModel.errorMessage.collectAsState()
    val isLoading by editTeamViewModel.isLoading.collectAsState()
    val isSuccess by editTeamViewModel.isSuccess.collectAsState()

    var showSuccessDialog by remember { mutableStateOf(false) }

    var teamName by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var maxMembers by rememberSaveable { mutableStateOf("") }
    var deadline by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var requirement by rememberSaveable { mutableStateOf("") }
    var tag by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    LaunchedEffect(route.id) {
        editTeamViewModel.getTeamData(route.id)
    }

    LaunchedEffect(teamData) {
        teamData?.let { data ->
            teamName = data.team.team_name
            category = data.team.category
            maxMembers = data.team.max_member.toString()
            deadline = data.team.deadline
            description = data.team.description ?: ""
            requirement = data.team.requirement ?: ""
            tag = data.team.tag ?: ""
        }
    }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            showSuccessDialog = true
        }
    }

    Scaffold(
        topBar = {
            HeaderEditTeam()
        }
    ) { innerPadding ->
        EditTeamScreenContent(
            isLoading = isLoading,
            errorMessage = errorMessage,

            teamName = teamName,
            onTeamNameChange = { teamName = it },
            category = category,
            onCategoryChange = { category = it },
            maxMembers = maxMembers,
            onMaxMembersChange = { maxMembers = it },
            deadline = deadline,
            onDeadlineChange = { deadline = it },
            description = description,
            onDescriptionChange = { description = it },
            requirement = requirement,
            onRequirementChange = { requirement = it },
            tag = tag,
            onTagChange = { tag = it },
            selectedImageUri = selectedImageUri,
            onSelectedImage = { selectedImageUri = it },
            innerPadding = innerPadding,

            onUpdateClick = {
                editTeamViewModel.updateTeam(
                    idTeam = route.id,
                    teamName = teamName,
                    category = category,
                    maxMember = maxMembers.toIntOrNull() ?: 0,
                    deadline = deadline,
                    description = description,
                    requirement = requirement,
                    tag = tag,
                    imageUri = selectedImageUri
                )
            }
        )

        if (showSuccessDialog) {
            CustomAlertDialog(
                title = "Success",
                message = "Your edit is successful",
                confirmText = "OK",
                dismissText = "Yes",
                onConfirm = { showSuccessDialog = false },
                onDismiss = { showSuccessDialog = false }
            )
        }
    }
}

@Composable
private fun EditTeamScreenContent(
    isLoading: Boolean,
    errorMessage: String?,
    teamName: String,
    onTeamNameChange: (String) -> Unit,

    category: String,
    onCategoryChange: (String) -> Unit,

    maxMembers: String,
    onMaxMembersChange: (String) -> Unit,

    deadline: String,
    onDeadlineChange: (String) -> Unit,

    description: String,
    onDescriptionChange: (String) -> Unit,

    requirement: String,
    onRequirementChange: (String) -> Unit,

    tag: String,
    onTagChange: (String) -> Unit,

    selectedImageUri: Uri?,
    onSelectedImage: (Uri?) -> Unit,

    innerPadding: PaddingValues,

    onUpdateClick: () -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.White)
    ) {

        item {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                UploadImageField(
                    selectedImageUri = selectedImageUri,
                    onSelectedImage = onSelectedImage
                )

                FormEditTeam(
                    title = "Team Name",
                    placeholder = "Enter Team Name",
                    value = teamName,
                    onValueChange = onTeamNameChange
                )

                FormEditTeam(
                    title = "Category",
                    placeholder = "Enter Category",
                    value = category,
                    onValueChange = onCategoryChange
                )

                FormEditTeam(
                    title = "Maximum Members",
                    placeholder = "Enter Max Members",
                    value = maxMembers,
                    onValueChange = onMaxMembersChange
                )

                FormEditTeam(
                    title = "Deadline",
                    placeholder = "yyyy-MM-dd",
                    value = deadline,
                    onValueChange = onDeadlineChange
                )

                FormEditTeam(
                    title = "Description",
                    placeholder = "Enter Description",
                    value = description,
                    onValueChange = onDescriptionChange,
                    modifier = Modifier.height(150.dp)
                )

                FormEditTeam(
                    title = "Requirement",
                    placeholder = "Enter Requirement",
                    value = requirement,
                    onValueChange = onRequirementChange,
                    modifier = Modifier.height(150.dp)
                )

                FormEditTeam(
                    title = "Tag",
                    placeholder = "Enter Tag",
                    value = tag,
                    onValueChange = onTagChange
                )

                errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red
                    )
                }

                PrimaryButton(
                    text = if (isLoading) {
                        "Updating..."
                    } else {
                        "Save Change"
                    },
                    onButtonClick = onUpdateClick,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Composable
private fun HeaderEditTeam() {
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
                    backStack.removeLastOrNull()
                }
        )

        Text(
            text = "Edit Team",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun UploadImageField(
    selectedImageUri: Uri?,
    onSelectedImage: (Uri?) -> Unit
) {
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        onSelectedImage(uri)
    }

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
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                imagePickerLauncher.launch(arrayOf("image/jpeg", "image/png"))
            }
    ) {
        if(selectedImageUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(selectedImageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
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
}

@Composable
private fun FormEditTeam(
    title: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
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
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditTeamScreenPreview() {
    UNSCollabTheme {
        // Sediakan id dummy agar preview bisa merender
        val mockRoute = Routes.EditTeamRoute(id = "123")
        val backStack = rememberNavBackStack(mockRoute)

        CompositionLocalProvider(LocalBackStack provides backStack) {
            // Berikan parameter route yang dibutuhkan
            EditTeamScreen(route = mockRoute)
        }
    }
}

