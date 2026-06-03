package com.tugas.unscollab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.tugas.unscollab.ui.screens.activity.ActivityScreen
import com.tugas.unscollab.ui.screens.auth.login.LoginScreen
import com.tugas.unscollab.ui.screens.auth.signup.SignupScreen
import com.tugas.unscollab.ui.screens.home.HomeScreen
import com.tugas.unscollab.ui.screens.internship.AllInternshipScreen
import com.tugas.unscollab.ui.screens.internship.InternshipDetailScreen
import com.tugas.unscollab.ui.screens.landingpage.LandingScreen
import com.tugas.unscollab.ui.screens.notification.NotificationScreen
import com.tugas.unscollab.ui.screens.profile.ProfileScreen
import com.tugas.unscollab.ui.screens.team.AllTeamScreen
import com.tugas.unscollab.ui.screens.team.CreateTeamScreen
import com.tugas.unscollab.ui.screens.team.TeamDetailScreen
import com.tugas.unscollab.ui.theme.UNSCollabTheme


@Composable
fun ComposeApp() {
    val backStack = rememberNavBackStack(Routes.LandingRoute)

    UNSCollabTheme {
        CompositionLocalProvider(LocalBackStack provides backStack) {
            NavDisplay(
                backStack = backStack,
                entryDecorators = listOf(
                    // Menyimpan state composable saat navigasi
                    rememberSaveableStateHolderNavEntryDecorator(),
                    // Mengelola ViewModel per layar
                    rememberViewModelStoreNavEntryDecorator()
                ),
                entryProvider = entryProvider {
                    entry<Routes.LandingRoute> { LandingScreen() }
                    entry<Routes.LoginRoute> { LoginScreen() }
                    entry<Routes.SignupRoute> { SignupScreen() }
                    entry<Routes.HomeRoute> { HomeScreen() }
                    entry<Routes.AllInternshipRoute> { AllInternshipScreen() }
                    entry<Routes.AllTeamRoute> { AllTeamScreen() }
                    entry<Routes.CreateTeamRoute> { CreateTeamScreen() }
                    entry<Routes.ActivityRoute> { ActivityScreen() }
                    entry<Routes.NotificationRoute> { NotificationScreen() }
                    entry<Routes.ProfileRoute> { ProfileScreen() }
                    entry<Routes.InternshipDetailRoute> { route ->
                        InternshipDetailScreen(route = route)
                    }
                    entry<Routes.TeamDetailRoute> { route ->
                        TeamDetailScreen(route = route)
                    }
                }
            )
        }
    }
}
