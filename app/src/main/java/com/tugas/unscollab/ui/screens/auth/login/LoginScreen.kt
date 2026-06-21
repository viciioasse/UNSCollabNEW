package com.tugas.unscollab.ui.screens.auth.login

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tugas.unscollab.R
import com.tugas.unscollab.ui.components.textField.CustomTextField
import com.tugas.unscollab.ui.components.LogoUNSCollab
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.button.SecondaryButton
import com.tugas.unscollab.ui.components.text.WelcomeText
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.auth.login.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val backStack = LocalBackStack.current
    val context = LocalContext.current

    val currentUser by loginViewModel.currentUser.collectAsState()
    val currentStudent by loginViewModel.currentStudent.collectAsState()
    val errorMessage by loginViewModel.errorMessage.collectAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(Unit) {
        loginViewModel.checkSession()
    }

    LaunchedEffect(currentUser, currentStudent) {
        if (currentUser != null) {
            backStack.clear()
            backStack.add(Routes.HomeRoute)
        }
    }

    LoginScreenContent(
        email = email,
        password = password,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        errorMessage = errorMessage,
        onLogin = { email, password ->
            loginViewModel.loginStudent(
                email,
                password
            )
        },
        onSSOClick = {
            val url = loginViewModel.onGoogleLoginClick()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    )
}

@Composable
private fun LoginScreenContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    errorMessage: String?,
    onLogin: (String, String) -> Unit,
    onSSOClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Image(
            painter = painterResource(R.drawable.bg_auth),
            contentDescription = null,
            modifier = Modifier.matchParentSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                48.dp,
                Alignment.CenterVertically
            ),
            modifier = Modifier.fillMaxSize()
        ) {

            LogoUNSCollab()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                WelcomeText(
                    "Welcome Back",
                    "Sign in to continue"
                )

                FormLogin(
                    email = email,
                    password = password,
                    errorMessage = errorMessage,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange
                )

                PrimaryButton(
                    text = "Login",
                    onButtonClick = {
                        if(email.isEmpty() || password.isEmpty()) {return@PrimaryButton}
                        onLogin(
                            email,
                            password
                        )
                    }
                )

                LoginSSO(onButtonClick = onSSOClick)
            }
        }
    }
}

@Composable
private fun FormLogin(
    email: String,
    password: String,
    errorMessage: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        CustomTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = "Email",
            icon = Icons.Default.Email
        )

        CustomTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "Password",
            icon = Icons.Default.Lock,
            isPassword = true
        )

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun LoginSSO(
    onButtonClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Or login with SSO",
            color = Color(0xFF1FABE1),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )

        SecondaryButton(
            text = "Continue with SSO",
            onButtonClick = onButtonClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    UNSCollabTheme {
        // Use LoginContent directly in Preview to avoid ViewModel instantiation issues.
        // Previews cannot automatically create ViewModels with dependencies (HiltViewModels).
        LoginScreenContent(
            email = "",
            password = "",
            onEmailChange = {},
            onPasswordChange = {},
            errorMessage = null,
            onLogin = { _, _ -> },
            onSSOClick = {}
        )
    }
}