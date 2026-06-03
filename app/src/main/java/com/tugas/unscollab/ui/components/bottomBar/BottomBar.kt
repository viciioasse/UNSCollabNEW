package com.tugas.unscollab.ui.components.bottomBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun BottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
){
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { onNavigate("home") },
            icon = {
                Icon(
                    imageVector =
                        if (currentRoute == "home") Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home",
                    tint =
                        if (currentRoute == "home") Color(0xFF1FABE1) else Color.Gray
                )
            },
            label = {
                Text(
                    "Home",
                    color =
                        if (currentRoute == "home") Color(0xFF1FABE1) else Color.Gray,
                    fontSize =
                        if (currentRoute == "home") 12.sp else 10.sp,
                    fontWeight =
                        if (currentRoute == "home") FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == "activity",
            onClick = { onNavigate("activity") },
            icon = {
                Icon(
                    imageVector =
                        if (currentRoute == "activity") Icons.Filled.ConfirmationNumber else Icons.Outlined.ConfirmationNumber,
                    contentDescription = "Activity",
                    tint =
                        if (currentRoute == "activity") Color(0xFF1FABE1) else Color.Gray
                )
            },
            label = {
                Text(
                    "Activity",
                    color =
                        if (currentRoute == "activity") Color(0xFF1FABE1) else Color.Gray,
                    fontSize =
                        if (currentRoute == "activity") 12.sp else 10.sp,
                    fontWeight =
                        if (currentRoute == "activity") FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == "notification",
            onClick = { onNavigate("notification") },
            icon = {
                Icon(
                    imageVector =
                        if (currentRoute == "notification") Icons.Filled.Notifications else Icons.Outlined.Notifications,
                    contentDescription = "Notification",
                    tint =
                        if (currentRoute == "notification") Color(0xFF1FABE1) else Color.Gray
                )
            },
            label = {
                Text(
                    "Notification",
                    color =
                        if (currentRoute == "notification") Color(0xFF1FABE1) else Color.Gray,
                    fontSize =
                        if (currentRoute == "notification") 12.sp else 10.sp,
                    fontWeight =
                        if (currentRoute == "notification") FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = { onNavigate("profile") },
            icon = {
                Icon(
                    imageVector =
                        if (currentRoute == "profile") Icons.Filled.Person else Icons.Outlined.Person,
                    contentDescription = "Profile",
                    tint =
                        if (currentRoute == "profile") Color(0xFF1FABE1) else Color.Gray
                )
            },
            label = {
                Text(
                    "Profile",
                    color =
                        if (currentRoute == "profile") Color(0xFF1FABE1) else Color.Gray,
                    fontSize =
                        if (currentRoute == "profile") 12.sp else 10.sp,
                    fontWeight =
                        if (currentRoute == "profile") FontWeight.Bold else FontWeight.Normal
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomBarPreview(){
    BottomBar(
        currentRoute = "home",
        onNavigate = {}
    )
}