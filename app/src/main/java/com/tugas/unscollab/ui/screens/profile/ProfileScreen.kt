package com.tugas.unscollab.ui.screens.profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tugas.unscollab.viewmodel.profile.ProfileUiState
import com.tugas.unscollab.viewmodel.profile.ProfileViewModel
import com.tugas.unscollab.ui.components.bottomBar.BottomBar
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import java.io.File
import java.io.FileOutputStream

val PrimaryBlue = Color(0xFF65C3ED)
val TextDark = Color(0xFF333333)
val TextGray = Color(0xFF828282)
val BgGray = Color(0xFFF8F9FA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val backStack = LocalBackStack.current

    LaunchedEffect(Unit) {
        viewModel.sessionManager.getSession().collect { session ->
            if (session.first == null) {
                backStack.clear()
                backStack.add(Routes.LandingRoute)
            }
        }
    }

    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showPhotoActionDialog by remember { mutableStateOf(false) }

    var showTextEditorDialog by remember { mutableStateOf(false) }
    var editorTitle by remember { mutableStateOf("") }
    var editorColumn by remember { mutableStateOf("") }
    var editorContent by remember { mutableStateOf("") }

    val context = LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                val localPath = saveImageToInternalStorage(context, uri)
                if (localPath.isNotEmpty()) {
                    viewModel.updateProfilePhoto(localPath)
                }
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Profile", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextDark) },
                actions = { IconButton(onClick = { viewModel.logout() }) { Icon(Icons.AutoMirrored.Filled.Logout, "Logout", tint = TextDark) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgGray)
            )
        },
        bottomBar = {
            BottomBar(
                currentRoute = "profile",
                onNavigate = { route ->
                    when(route) {
                        "home" -> backStack.add(Routes.HomeRoute)
                        "activity" -> backStack.add(Routes.ActivityRoute)
                        "notification" -> backStack.add(Routes.NotificationRoute)
                        "profile" -> backStack.add(Routes.ProfileRoute)
                    }
                }
            )
        },
        containerColor = BgGray
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is ProfileUiState.Loading -> CircularProgressIndicator(color = PrimaryBlue)
                is ProfileUiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadProfile() }, colors = ButtonDefaults.buttonColors(PrimaryBlue)) {
                            Text("Try again")
                        }
                    }
                }
                is ProfileUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        ProfileHeader(
                            name = viewModel.userName,
                            nim = viewModel.userId,
                            role = viewModel.userRole,
                            prodi = viewModel.userProdi,
                            photoUri = viewModel.userPhotoUri,
                            onEditProfile = { showEditProfileDialog = true },
                            onChangePhoto = {
                                if (viewModel.userPhotoUri.isEmpty()) photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                else showPhotoActionDialog = true
                            }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            TextSectionCard(
                                title = "About", icon = Icons.Default.Person, content = viewModel.userBio,
                                onEditClick = { showEditProfileDialog = true }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            TextSectionCard(
                                title = "Experince", icon = Icons.Default.Work, content = viewModel.userExperience,
                                onEditClick = { editorTitle = "Edit Experience"; editorColumn = "experience"; editorContent = viewModel.userExperience; showTextEditorDialog = true }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            TextSectionCard(
                                title = "Portofolio", icon = Icons.Default.FolderSpecial, content = viewModel.userPortofolio,
                                onEditClick = { editorTitle = "Edit Portofolio"; editorColumn = "portofolio"; editorContent = viewModel.userPortofolio; showTextEditorDialog = true }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            SkillSectionCard(
                                title = "Skill", icon = Icons.Default.Star, content = viewModel.userSkill,
                                onEditClick = { editorTitle = "Edit Skill (Use comma (,) to separate skills)"; editorColumn = "skill"; editorContent = viewModel.userSkill; showTextEditorDialog = true }
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }
        }

        if (showPhotoActionDialog) {
            AlertDialog(
                onDismissRequest = { showPhotoActionDialog = false },
                title = { Text("Profile Picture", fontWeight = FontWeight.Bold) },
                text = { Text("Chose an action") },
                dismissButton = {
                    TextButton(onClick = { viewModel.deleteProfilePhoto(); showPhotoActionDialog = false }) {
                        Text("Delete Picture", color = Color.Red, fontWeight = FontWeight.SemiBold)
                    }
                },
                confirmButton = {
                    Row {
                        TextButton(onClick = { showPhotoActionDialog = false }) { Text("Batal", color = Color.Gray) }
                        Button(onClick = { photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)); showPhotoActionDialog = false }, colors = ButtonDefaults.buttonColors(PrimaryBlue)) { Text("Ubah Foto") }
                    }
                }
            )
        }

        if (showEditProfileDialog) {
            EditBasicProfileDialog(viewModel.userName, viewModel.userId, viewModel.userProdi, viewModel.userBio, onDismiss = { showEditProfileDialog = false }) { n, i, p, b ->
                viewModel.updateBasicProfile(n, i, p, b)
                showEditProfileDialog = false
            }
        }

        if (showTextEditorDialog) {
            EditTextSectionDialog(
                title = editorTitle,
                initialText = editorContent,
                onDismiss = { showTextEditorDialog = false },
                onSave = { newText ->
                    viewModel.updateSection(editorColumn, newText)
                    showTextEditorDialog = false
                }
            )
        }
    }
}

@Composable
fun ProfileHeader(name: String, nim: String, role: String, prodi: String, photoUri: String, onEditProfile: () -> Unit, onChangePhoto: () -> Unit) {
    Row(modifier = Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(72.dp).clickable { onChangePhoto() }) {
            Box(modifier = Modifier.size(72.dp).clip(CircleShape).background(Color(0xFFE0E0E0)), contentAlignment = Alignment.Center) {
                if (photoUri.isNotEmpty()) {
                    AsyncImage(model = photoUri, contentDescription = "Profile Picture", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                } else {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.DarkGray)
                }
            }
            Box(modifier = Modifier.align(Alignment.BottomEnd).size(24.dp).clip(CircleShape).background(PrimaryBlue).border(2.dp, Color.White, CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Picture", tint = Color.White, modifier = Modifier.size(12.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onEditProfile() }) {
                Text(name.ifEmpty { "Name not set" }, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextDark)
                Spacer(Modifier.width(8.dp))
                Icon(Icons.Default.Edit, contentDescription = "Edit Profile", modifier = Modifier.size(16.dp), tint = TextGray)
            }
            if (nim.isNotEmpty()) {
                Text(nim, color = TextDark, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
            if (role.isNotEmpty()) {
                Text(role, color = PrimaryBlue, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
            if (prodi.isNotEmpty()) {
                Text(prodi, color = TextGray, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun TextSectionCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, content: String, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = PrimaryBlue)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
                }
                Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(18.dp).clickable { onEditClick() }, tint = TextGray)
            }
            if (content.isEmpty()) {
                Text("No data available.", fontSize = 13.sp, color = TextGray, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
            } else {
                Text(content, fontSize = 14.sp, color = TextDark, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
fun SkillSectionCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, content: String, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = PrimaryBlue)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
                }
                Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(18.dp).clickable { onEditClick() }, tint = TextGray)
            }
            if (content.isEmpty()) {
                Text("No data available. Use comma (,) to separate skill", fontSize = 13.sp, color = TextGray, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
            } else {
                val skillList = content.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    skillList.chunked(3).forEach { rowSkills ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            rowSkills.forEach { SkillBadge(it) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SkillBadge(skill: String) {
    Surface(shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, Color(0xFFE0E0E0)), color = Color.White) {
        Text(skill, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = TextDark)
    }
}

@Composable
fun EditBasicProfileDialog(currentName: String, currentId: String, currentProdi: String, currentBio: String, onDismiss: () -> Unit, onSave: (String, String, String, String) -> Unit) {
    var tempName by remember { mutableStateOf(currentName) }
    var tempId by remember { mutableStateOf(currentId) }
    var tempProdi by remember { mutableStateOf(currentProdi) }
    var tempBio by remember { mutableStateOf(currentBio) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(16.dp), color = Color.White, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp).fillMaxWidth()) {
                Text("Edit Profile", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF1A1A2E))
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(value = tempName, onValueChange = { tempName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = tempId, onValueChange = { tempId = it }, label = { Text("NIM") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = tempProdi, onValueChange = { tempProdi = it }, label = { Text("Major") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = tempBio, onValueChange = { tempBio = it }, label = { Text("About (Bio)") }, modifier = Modifier.fillMaxWidth(), minLines = 3, maxLines = 5)
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Cancel", color = Color.Gray) }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onSave(tempName, tempId, tempProdi, tempBio) }, colors = ButtonDefaults.buttonColors(PrimaryBlue)) { Text("Save") }
                }
            }
        }
    }
}

@Composable
fun EditTextSectionDialog(title: String, initialText: String, onDismiss: () -> Unit, onSave: (String) -> Unit) {
    var text by remember { mutableStateOf(initialText) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(16.dp), color = Color.White, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Enter data") },
                    placeholder = { Text("Enter text here (Multiline)…") },
                    modifier = Modifier.fillMaxWidth().height(150.dp),
                    maxLines = 10
                )
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Cancel", color = Color.Gray) }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { onSave(text) }, colors = ButtonDefaults.buttonColors(PrimaryBlue)) { Text("Save") }
                }
            }
        }
    }
}

fun saveImageToInternalStorage(context: Context, uri: Uri): String {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return ""
        val file = File(context.filesDir, "profile_picture_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        Uri.fromFile(file).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}