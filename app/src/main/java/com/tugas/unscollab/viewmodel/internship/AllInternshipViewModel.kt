package com.tugas.unscollab.viewmodel.internship

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
class AllInternshipViewModel @Inject constructor(
    private val internshipRepository: InternshipRepository
): ViewModel() {

    private val _internships = MutableStateFlow<List<InternshipResponse>>(emptyList())
    val internships: StateFlow<List<InternshipResponse>> = _internships

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchInternships() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _internships.value = internshipRepository.getInternships()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}