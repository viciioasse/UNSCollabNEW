package com.tugas.unscollab.ui.screens.internship

import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LaptopWindows
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.rememberNavBackStack
import coil.compose.AsyncImage
import com.tugas.unscollab.R
import com.tugas.unscollab.data.response.InternshipResponse
import com.tugas.unscollab.ui.components.bottomSheet.CustomBottomSheet
import com.tugas.unscollab.ui.components.header.HeaderDetail
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.internship.InternshipDetailViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InternshipDetailScreen(
    route: Routes.InternshipDetailRoute,
    internshipDetailViewModel: InternshipDetailViewModel = viewModel()
) {
    val selectedInternship by internshipDetailViewModel.selectedInternship.collectAsState()
    val isLoading by internshipDetailViewModel.isLoading.collectAsState()
    val errorMessage by internshipDetailViewModel.errorMessage.collectAsState()

    var isBottomSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(route.id) {
        internshipDetailViewModel.getInternshipById(route.id)
    }

    Scaffold(
        topBar = {
            HeaderDetail(
                title = "Detail Internship",
                onBookmarkClick = {}
            )
        }
    ) {innerPadding ->
        when {
            isLoading -> Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            errorMessage != null -> Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text(text = errorMessage ?: "Unknown Error", color = Color.Red)
            }
            selectedInternship != null -> LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(all = 16.dp),
                modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color(0xFFF5F6FA))
            ) {
                item {
                    InternshipDetailScreenContent(
                        internshipResponse = selectedInternship!!,
                        onClickApply = { isBottomSheetOpen = true }
                    )
                }
            }
        }
    }

    if(isBottomSheetOpen) {
        CustomBottomSheet(
            isBottomSheetOpen = isBottomSheetOpen,
            onDismiss = { isBottomSheetOpen = false },
            sheetState = sheetState,
            customFunction = {
                uploadFunction(
                    idInternship = route.id,
                    internshipDetailViewModel = internshipDetailViewModel
                )
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun InternshipDetailScreenContent(
    internshipResponse: InternshipResponse,
    onClickApply: () -> Unit
) {
    val internship = internshipResponse.internship
    val companyName = internshipResponse.companyName

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val deadline = LocalDate.parse(internship.deadline, formatter)

        val status = if (LocalDate.now().isBefore(deadline) || LocalDate.now().isEqual(deadline)) {
            "Open"
        } else {
            "Closed"
        }

        TitleSection(
            imageUrl = internship.image,
            title = internship.title,
            companyName = companyName,
            workMode = internship.work_mode,
            location = internship.location,
            duration = internship.duration
        )

        ContentSection(
            icon = Icons.Default.Description,
            title = "Description",
            value = internship.description
        )

        InformationSection(
            deadline = internship.deadline,
            status = status,
            paymentStatus = internship.payment_status,
            quota = internship.quota,
        )

        ContentSection(
            icon = Icons.AutoMirrored.Filled.ViewList,
            title = "Requirement",
            value = internship.requirement
        )

        ContentSection(
            icon = Icons.Default.Work,
            title = "Benefit",
            value = internship.benefit
        )

        PrimaryButton(
            text = "Apply Now",
            onButtonClick = onClickApply,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun TitleSection(
    imageUrl: String? = null,
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
    quota: Int
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
                value = "$quota people"
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

@Composable
private fun uploadFunction(
    idInternship: String,
    internshipDetailViewModel: InternshipDetailViewModel = viewModel()
) {
    var CVuri by remember { mutableStateOf<Uri?>(null)}
    var CoverLetterUri by remember { mutableStateOf<Uri?>(null)}

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        Text(
            text = "Apply Internship",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        UploadDocumentField(
            label = "Upload CV",
            selectedFileUri = CVuri,
            onSelectedFile = { CVuri = it }
        )

        UploadDocumentField(
            label = "Upload Cover Letter",
            selectedFileUri = CoverLetterUri,
            onSelectedFile = { CoverLetterUri = it }
        )

        PrimaryButton(
            text = "Submit Application",
            onButtonClick = {
                internshipDetailViewModel.applyInternship(
                    idInternship = idInternship,
                    cvUri = CVuri,
                    coverLetterUri = CoverLetterUri
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun UploadDocumentField(
    label: String,
    selectedFileUri: Uri?,
    onSelectedFile: (Uri?) -> Unit
){
    val context = LocalContext.current

    val documentPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) {
        uri: Uri? ->
        onSelectedFile(uri)
    }

    val fileName = remember(selectedFileUri) {
        selectedFileUri?.let { uri ->
            var name = "Unknown File"

            context.contentResolver.query(
                uri,
                null,
                null,
                null,
                null
            )?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)

                if(cursor.moveToFirst() && nameIndex >= 0) {
                    name = cursor.getString(nameIndex)
                }
            }

            name
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    documentPickerLauncher.launch(
                        arrayOf(
                            "application/pdf",
                            "application/msword",
                            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                        )
                    )
                }
                .padding(16.dp)
        ) {

            if (selectedFileUri == null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = null,
                        tint = Color(0xFF1FABE1)
                    )

                    Text(
                        text = "Upload $label",
                        color = Color.Gray
                    )

                    Text(
                        text = "PDF, DOC, DOCX",
                        fontSize = 12.sp,
                        color = Color.LightGray
                    )
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = null,
                        tint = Color(0xFF1FABE1)
                    )

                    Text(
                        text = fileName ?: "Document Selected",
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewInternshipDetailScreen() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.InternshipDetailRoute(id = ""))
        CompositionLocalProvider(LocalBackStack provides backStack) {
            InternshipDetailScreen(route = Routes.InternshipDetailRoute(id = ""))
        }
    }
}