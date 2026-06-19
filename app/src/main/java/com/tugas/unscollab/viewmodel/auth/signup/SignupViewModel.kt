package com.tugas.unscollab.viewmodel.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun register(fullName: String, email: String, password: String, onRegisterSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.register(fullName, email, password)
                _errorMessage.value = null
                onRegisterSuccess()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}