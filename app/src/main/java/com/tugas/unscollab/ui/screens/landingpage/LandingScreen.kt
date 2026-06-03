package com.tugas.unscollab.ui.screens.landingpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.R
import com.tugas.unscollab.ui.components.LogoUNSCollab
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.button.SecondaryButton
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun LandingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.bg_auth),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxSize()
        ) {
            LogoUNSCollab()



            Buttons()
        }
    }
}


@Composable
private fun Buttons() {
    val backStack = LocalBackStack.current

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PrimaryButton(
                text = "Login",
                onButtonClick = {
                    backStack.add(Routes.LoginRoute)
                }
            )

            SecondaryButton(
                text = "Sign Up",
                onButtonClick = {
                    backStack.add(Routes.SignupRoute)
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.LandingRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            LandingScreen()
        }
    }
}