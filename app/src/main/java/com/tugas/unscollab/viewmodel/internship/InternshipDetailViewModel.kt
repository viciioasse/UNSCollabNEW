package com.tugas.unscollab.viewmodel.internship

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.local.SessionManager
import com.tugas.unscollab.data.repository.InternshipRepository
import com.tugas.unscollab.data.response.InternshipResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternshipDetailViewModel @Inject constructor(
    private val repository: InternshipRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    //Session State
    private val _session = MutableStateFlow<Pair<String?, String?>>(null to null)
    val session: StateFlow<Pair<String?, String?>> = _session

    init {
        viewModelScope.launch {
            sessionManager.getSession().collect { pair ->
                _session.value = pair
            }
        }
    }

    private val _selectedInternship = MutableStateFlow<InternshipResponse?>(null)
    val selectedInternship: StateFlow<InternshipResponse?> = _selectedInternship

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getInternshipById(idInternship: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val internship = repository.getInternshipById(idInternship)
                _selectedInternship.value = internship
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun applyInternship(idInternship: String, cvUri: Uri?, coverLetterUri: Uri?) {
        viewModelScope.launch {
            val(idUser, _) = session.value
            if(idUser == null) {
                _errorMessage.value = "User not logged in"
                return@launch
            }

            try {
                repository.applyInternship(
                    idInternship,
                    idUser,
                    cvUri,
                    coverLetterUri
                )
            } catch (e: Exception) {
             _errorMessage.value = e.message
            }
        }
    }
}