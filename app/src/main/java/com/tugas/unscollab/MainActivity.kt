package com.tugas.unscollab

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.tugas.unscollab.ui.navigation.ComposeApp
import com.tugas.unscollab.viewmodel.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        handleSSORedirect(intent)
        setContent {
            ComposeApp()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleSSORedirect(intent)
    }

    private fun handleSSORedirect(intent: Intent?) {
        val uri: Uri? = intent?.data

        if(uri != null && uri.scheme == "unscollab" && uri.host == "login-callback") {
            val fragment = uri.fragment
            if(fragment != null) {
                val params = fragment.split("&").associate {
                    val pair = it.split("=")
                    if(pair.size == 2) pair[0] to pair[1] else "" to ""
                }

                val accessToken = params["access_token"]
                if(accessToken != null) {
                    try {
                        val parts = accessToken.split(".")
                        if(parts.size >= 2) {
                            val payload = String(Base64.decode(parts[1], Base64.URL_SAFE))
                            val jsonObject = JSONObject(payload)

                            val idUser = jsonObject.getString("sub")
                            val email = jsonObject.getString("email")

                            loginViewModel.handleSSOSuccess(idUser, email)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
