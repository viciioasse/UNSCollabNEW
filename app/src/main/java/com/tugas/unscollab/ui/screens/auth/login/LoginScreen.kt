package com.tugas.unscollab.ui.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.R
import com.tugas.unscollab.ui.components.textField.CustomTextField
import com.tugas.unscollab.ui.components.LogoUNSCollab
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.button.SecondaryButton
import com.tugas.unscollab.ui.components.text.WelcomeText
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

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
            verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxSize()
        ) {
            LogoUNSCollab()

            TextFormButtonLogin(
                email = email,
                password = password,
                showError = showError,
                onEmailChange = {
                  email = it; showError = false
                },
                onPasswordChange = {
                    password = it; showError = false
                },
                onShowError = { showError = it }
            )
        }
    }
}

@Composable
private fun TextFormButtonLogin(
    email: String,
    password: String,
    showError: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onShowError: (Boolean) -> Unit

) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .padding(16.dp)
    ) {
        WelcomeText(
            "Welcome Back",
            "Sign in to continue"
        )

        FormButtonLogin(
            email = email,
            password = password,
            showError = showError,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onShowError = onShowError
        )
    }
}

@Composable
private fun FormButtonLogin(
    email: String,
    password: String,
    showError: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onShowError: (Boolean) -> Unit
) {
    val backStack = LocalBackStack.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ) {
        FormLogin(
            email = email,
            password = password,
            showError = showError,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onShowError = onShowError
        )

        PrimaryButton(
            "Login",
            onButtonClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    backStack.add(Routes.HomeRoute)
                } else {
                    onShowError(true)
                }
            }
        )

        LoginSSO()
    }
}

@Composable
private fun FormLogin(
    email: String,
    password: String,
    showError: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onShowError: (Boolean) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomTextField(
            value = email,
            onValueChange = { onEmailChange(it); onShowError(false) },
            placeholder = "Email",
            icon = Icons.Default.Email
        )

        CustomTextField(
            value = password,
            onValueChange = { onPasswordChange(it); onShowError(false) },
            placeholder = "Password",
            icon = Icons.Default.Lock,
            isPassword = true
        )

        if (showError) {
         Text(
             text = "Invalid email or password",
             color = Color.Red,
             fontSize = 12.sp,
             modifier = Modifier
                 .align(Alignment.Start)
                 .padding(top = 4.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Forgot Password?",
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable { }
            )
        }
    }
}

@Composable
private fun LoginSSO() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Or login with SSO",
            color = Color(0xFF1FABE1),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )

        SecondaryButton(
            text = "Continue with SSO",
            onButtonClick = { },

        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    UNSCollabTheme {
        val backStack = rememberNavBackStack(Routes.LoginRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            LoginScreen()
        }
    }
}