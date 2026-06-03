package com.tugas.unscollab.ui.screens.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.rememberNavBackStack
import com.tugas.unscollab.R
import com.tugas.unscollab.ui.components.textField.CustomTextField
import com.tugas.unscollab.ui.components.LogoUNSCollab
import com.tugas.unscollab.ui.components.button.PrimaryButton
import com.tugas.unscollab.ui.components.text.WelcomeText
import com.tugas.unscollab.ui.navigation.LocalBackStack
import com.tugas.unscollab.ui.navigation.Routes
import com.tugas.unscollab.ui.theme.UNSCollabTheme

@Composable
fun SignupScreen() {
    var name by remember { mutableStateOf("") }
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

            TextFormButtonSignup(
                name = name,
                email = email,
                password = password,
                showError = showError,
                onNameChange = {
                    name = it; showError = false
                },
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
private fun TextFormButtonSignup(
    name: String,
    email: String,
    password: String,
    showError: Boolean,
    onNameChange: (String) -> Unit,
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
            "Welcome",
            "Create an account to continue"
        )

        FormButtonSignUp(
            name = name,
            email = email,
            password = password,
            showError = showError,
            onNameChange = onNameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onShowError = onShowError
        )
    }
}

@Composable
private fun FormButtonSignUp(
    name: String,
    email: String,
    password: String,
    showError: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onShowError: (Boolean) -> Unit
) {
    val backStack = LocalBackStack.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        FormSignup(
            name = name,
            email = email,
            password = password,
            showError = showError,
            onNameChange = onNameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onShowError = onShowError
        )

        PrimaryButton(
            "Sign Up",
            onButtonClick = {
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    backStack.clear()
                    backStack.add(Routes.LoginRoute)
                } else {
                    onShowError(true)
                }
            }
        )
    }
}

@Composable
private fun FormSignup(
    name: String,
    email: String,
    password: String,
    showError: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onShowError: (Boolean) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomTextField(
            value = name,
            onValueChange = { onNameChange(it); onShowError(false) },
            placeholder = "Name",
            icon = Icons.Default.Person
        )

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
                text = "All fields must be filled",
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
        val backStack = rememberNavBackStack(Routes.SignupRoute)
        CompositionLocalProvider(LocalBackStack provides backStack) {
            SignupScreen()
        }
    }
}