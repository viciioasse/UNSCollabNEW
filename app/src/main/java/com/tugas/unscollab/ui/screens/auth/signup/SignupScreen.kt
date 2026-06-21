package com.tugas.unscollab.ui.screens.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tugas.unscollab.R
import com.tugas.unscollab.ui.components.textField.CustomTextField
import com.tugas.unscollab.ui.components.LogoUNSCollab
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.text.WelcomeText
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme
import com.tugas.unscollab.viewmodel.auth.signup.SignupViewModel

@Composable
fun SignupScreen(
    authViewModel: SignupViewModel = hiltViewModel()
) {
    val backStack = LocalBackStack.current

    val errorMessage by authViewModel.errorMessage.collectAsState()

    SignupScreenContent(
        errorMessage = errorMessage,
        onRegister = { name, email, password ->
            authViewModel.register(
                fullName = name,
                email = email,
                password = password,
                onRegisterSuccess = {
                    backStack.clear()
                    backStack.add(Routes.LoginRoute)
                }
            )
        }
    )
}

@Composable
private fun SignupScreenContent(
    errorMessage: String?,
    onRegister: (
        name: String,
        email: String,
        password: String
    ) -> Unit
) {

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
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
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(16.dp)
            ) {

                WelcomeText(
                    "Welcome",
                    "Create an account to continue"
                )

                SignupForm(
                    name = name,
                    email = email,
                    password = password,
                    errorMessage = errorMessage,
                    onNameChange = {name = it },
                    onEmailChange = { email = it },
                    onPasswordChange = { password = it }
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    PrimaryButton(
                        text = "Sign Up",
                        onButtonClick = {

                            if (name.isBlank() || email.isBlank() || password.isBlank()) return@PrimaryButton

                            onRegister(name, email, password)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SignupForm(
    name: String,
    email: String,
    password: String,
    errorMessage: String?,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        CustomTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = "Name",
            icon = Icons.Default.Person
        )

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


@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    UNSCollabTheme {
        // Use SignupContent directly in the preview to avoid instantiating the ViewModel,
        // which requires a factory that is not available in the preview environment.
        SignupScreenContent(
            errorMessage = null,
            onRegister = { _, _, _ -> }
        )
    }
}