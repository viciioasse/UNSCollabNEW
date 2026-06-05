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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LaptopWindows
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.data.model.Internship
import com.tugas.unscollab.ui.components.bottomSheet.CustomBottomSheet
import com.tugas.unscollab.ui.components.header.HeaderScreen
import com.tugas.unscollab.ui.components.card.InternshipCard
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.InternshipViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllInternshipScreen(
    modifier: Modifier = Modifier,
    internshipViewModel: InternshipViewModel = viewModel()
) {
    val internships by internshipViewModel.internships.collectAsState()
    val newestInternship = internships.reversed()
    
    var isBottomSheetOpen by remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            HeaderScreen(
                title = "Internship",
                value = "",
                onValueChange = {},
                placeholder = "Search internship",
                onFilterClick = {
                    isBottomSheetOpen = true
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(all = 16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F6FA))
        ) {
            item {
                InternshipContent(
                    internships = newestInternship
                )
            }
        }
    }

    if(isBottomSheetOpen) {
        CustomBottomSheet(
            isBottomSheetOpen = isBottomSheetOpen,
            onDismiss = { isBottomSheetOpen = false },
            sheetState = sheetState,
            CustomFilter = { CustomFilter() }
        )
    }
}

@Composable
private fun CustomFilter() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProvincesFilter()
        WorkModeFilter()
        StatusFilter()
    }
}

@Composable
private fun InternshipContent(
    internships: List<Internship>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if(internships.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(text = "No internship available")
            }
        } else {
            internships.forEach { internship ->
                InternshipCard(
                    internship = internship,

                    isApplied = false,
                    dateApply = null,
                    statusInternship = null,

                    onClickApply = {},
                    onClickDelete = {}
                )
            }
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
        if (title == "Onsite")
            Icons.Outlined.Business
        else if (title == "Remote")
            Icons.Outlined.Home
        else
            Icons.Outlined.LaptopWindows

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
            AllInternshipScreen()
        }
    }
}
