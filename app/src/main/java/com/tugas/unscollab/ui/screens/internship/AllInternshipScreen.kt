package com.tugas.unscollab.ui.screens.internship

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LaptopWindows
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.data.response.InternshipResponse
import com.tugas.unscollab.ui.components.bottomSheet.CustomBottomSheet
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.button.SecondaryButton
import com.tugas.unscollab.ui.components.header.HeaderScreen
import com.tugas.unscollab.ui.components.card.InternshipCard
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.internship.AllInternshipViewModel

@Composable
fun AllInternshipScreen(
    allInternshipViewModel: AllInternshipViewModel = viewModel()
) {
    val internships by allInternshipViewModel.internships.collectAsState()
    val isLoading by allInternshipViewModel.isLoading.collectAsState()
    val errorMessage by allInternshipViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        allInternshipViewModel.fetchInternships()
    }

    AllInternshipScreenContent(
        internships = internships,
        isLoading = isLoading,
        errorMessage = errorMessage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllInternshipScreenContent(
    internships: List<InternshipResponse>,
    isLoading: Boolean,
    errorMessage: String?
) {
    val newestInternship = internships.reversed()
    var isFilterSheetOpen by remember {
        mutableStateOf(false)
    }
    val filterSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            HeaderScreen(
                title = "Internship",
                placeholder = "Search internship",
                onSearchClick = {},
                onFilterClick = {
                    isFilterSheetOpen = true
                }
            )
        }
    ) { innerPadding ->
        AllInternshipScreenContent(
            internships = newestInternship,
            isLoading = isLoading,
            errorMessage = errorMessage,
            innerPadding = innerPadding
        )
    }

    if (isFilterSheetOpen) {
        CustomBottomSheet(
            isBottomSheetOpen = isFilterSheetOpen,
            onDismiss = { isFilterSheetOpen = false },
            sheetState = filterSheetState,
            customFunction = { FilterInternship() }
        )
    }
}

@Composable
private fun AllInternshipScreenContent(
    internships: List<InternshipResponse>,
    isLoading: Boolean,
    errorMessage: String?,
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color(0xFFF5F6FA)),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> Text(
                text = "Error: $errorMessage",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            internships.isEmpty() -> Text(
                text = "No internship available",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            else -> LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(all = 16.dp),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(internships) { internship ->
                    InternshipCard(
                        internshipResponse = internship,
                        actionButton = {}
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterInternship() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Filter"
        )

        ProvincesFilter()

        WorkModeFilter()

        StatusFilter()

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SecondaryButton(
                text = "Reset",
                onButtonClick = {},
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
            )

            PrimaryButton(
                text = "Apply",
                onButtonClick = {},
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProvincesFilter() {
    val provincesList = listOf(
        "Aceh", "Bali", "Bangka Belitung", "Banten", "Bengkulu", "DI Yogyakarta", "DKI Jakarta",
        "Gorontalo", "Jambi", "Jawa Barat", "Jawa Tengah", "Jawa Timur", "Kalimantan Barat",
        "Kalimantan Selatan", "Kalimantan Tengah", "Kalimantan Timur", "Kalimantan Utara",
        "Kepulauan Riau", "Lampung", "Maluku", "Maluku Utara", "Nusa Tenggara Barat",
        "Nusa Tenggara Timur", "Papua", "Papua Barat", "Papua Barat Daya", "Papua Pegunungan",
        "Papua Selatan", "Papua Tengah", "Riau", "Sulawesi Barat", "Sulawesi Selatan",
        "Sulawesi Tengah", "Sulawesi Tenggara", "Sulawesi Utara", "Sumatera Barat",
        "Sumatera Selatan", "Sumatera Utara"
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedProvince by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Provinces",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = selectedProvince,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Province")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                provincesList.forEach { province ->
                    DropdownMenuItem(
                        text = { Text(province) },
                        onClick = {
                            selectedProvince = province
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun WorkModeFilter() {
    val workModeList = listOf("Onsite", "Remote", "Hybrid")
    var selectedWorkMode by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Work Mode",
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            workModeList.forEach { workMode ->
                WorkModeChip(
                    title = workMode,
                    selected = selectedWorkMode == workMode,
                    onClickSelected = {
                        selectedWorkMode = workMode
                    }
                )
            }
        }
    }
}

@Composable
private fun StatusFilter() {
    val statusList = listOf("Open", "Closed")
    var selectedStatus by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Status",
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            statusList.forEach { status ->
                StatusChip(
                    title = status,
                    selected = selectedStatus == status,
                    onClickSelected = {
                        selectedStatus = status
                    }
                )
            }
        }
    }
}

@Composable
private fun StatusChip(
    title: String,
    selected: Boolean,
    onClickSelected: () -> Unit
) {
    val icon =
        if (title == "Open")
            Icons.Outlined.LockOpen
        else
            Icons.Outlined.Lock

    val iconColor =
        if (title == "Open")
            Color(0xFF34C759)
        else
            Color(0xFFFF3B30)

    val background =
        if (title == "Open")
            Color(0x1434C759)
        else
            Color(0x14FF3B30)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .border(
                width = 1.dp,
                color =
                    if (selected)
                        Color.Black
                    else
                        Color(0xFFD9D9D9),
                shape = RoundedCornerShape(50)
            )
            .padding(all = 8.dp)
            .clickable {
                onClickSelected()
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(background)
                .padding(all = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor
            )
        }

        Text(
            text = title,
            color = Color.Black,
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}

@Composable
private fun WorkModeChip(
    title: String,
    selected: Boolean,
    onClickSelected: () ->  Unit
) {
    val icon =
        when (title) {
            "Onsite" -> Icons.Outlined.Business
            "Remote" -> Icons.Outlined.Home
            else -> Icons.Outlined.LaptopWindows
        }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .border(
                width = 1.dp,
                color = if (selected)
                            Color.Black
                        else
                            Color(0xFFD9D9D9),
                shape = RoundedCornerShape(50)
            )
            .padding(all = 8.dp)
            .clickable {
                onClickSelected()
            }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )

        Text(
            text = title,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAllInternshipScreen() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.AllInternshipRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            AllInternshipScreenContent(
                internships = emptyList(),
                isLoading = false,
                errorMessage = null
            )
        }
    }
}
