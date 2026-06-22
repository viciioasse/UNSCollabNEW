package com.tugas.unscollab.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


// Mendefinisikan rute navigasi (semua halaman yang dapat dikunjungi di aplikasi)
object Routes {
    //AUTH
    @Serializable
    data object LandingRoute : NavKey

    @Serializable
    data object LoginRoute : NavKey

    @Serializable
    data object SignupRoute : NavKey

    @Serializable
    data object HomeRoute : NavKey


    @Serializable
    data object AllInternshipRoute : NavKey

    @Serializable
    data object AllTeamRoute : NavKey

    @Serializable
    data object CreateTeamRoute : NavKey

    @Serializable
    data object ActivityRoute : NavKey

    @Serializable
    data object NotificationRoute : NavKey

    @Serializable
    data object ProfileRoute : NavKey

    @Serializable
    data class InternshipDetailRoute(val id: String) : NavKey

    @Serializable
    data class TeamDetailRoute(val id: String) : NavKey

    @Serializable
    data class EditTeamRoute(val id: String) : NavKey
}
