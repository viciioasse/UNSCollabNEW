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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loginStudent(email: String, password: String) {
        viewModelScope.launch {
            try {
                val (user, student) = repository.login(email, password)
                _currentUser.value = user
                _currentStudent.value = student
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun checkSession() {
        viewModelScope.launch {
            repository.getSession().collect { (userId, email) ->
                if (userId != null && email != null) {
                    _currentUser.value = User(userId, email, "", "","")

                    try {
                        val student = repository.getStudentByUserId(userId)
                        _currentStudent.value = student
                    } catch (e: Exception) {
                        _errorMessage.value = e.message
                    }
                }
            }
        }
    }

    fun onGoogleLoginClick(): String {
        return repository.getSSOLoginUrl("google")
    }

    fun handleSSOSuccess(id_user: String, email: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.saveSSOSession(id_user, email)
                var student = repository.getStudentByUserId(id_user)
                if(student == null) {
                    val tempName = email.split("@")[0].replaceFirstChar { it.uppercase() }

                    val newStudent = Student(
                        id_student = java.util.UUID.randomUUID().toString(),
                        id_user = id_user,
                        full_name = tempName
                    )
                    student = repository.createStudent(newStudent)
                }
                _currentUser.value = User(id_user, email, "", "", "")
                _currentStudent.value = student
                _errorMessage.value = null
                checkSession()
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
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