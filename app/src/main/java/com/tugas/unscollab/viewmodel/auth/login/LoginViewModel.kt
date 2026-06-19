package com.tugas.unscollab.viewmodel.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.User
import com.tugas.unscollab.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _currentStudent = MutableStateFlow<Student?>(null)
    val currentStudent: StateFlow<Student?> = _currentStudent

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loginStudent(email: String, password: String) {
        viewModelScope.launch {
            try {
                val (user, student) = repository.login(email, password)
                _currentUser.value = user
                _currentStudent.value = student
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun checkSession() {
        viewModelScope.launch {
            repository.getSession().collect { (userId, email) ->
                if (userId != null && email != null) {
                    _currentUser.value = User(userId, email, "", "","")
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _currentUser.value = null
            _currentStudent.value = null
        }
    }
}