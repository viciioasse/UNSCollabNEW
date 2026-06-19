package com.tugas.unscollab.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.local.SessionManager
import com.tugas.unscollab.data.repository.InternshipRepository
import com.tugas.unscollab.data.repository.TeamRepository
import com.tugas.unscollab.data.response.InternshipResponse
import com.tugas.unscollab.data.response.TeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val internshipRepository: InternshipRepository,
    private val teamRepository: TeamRepository
) : ViewModel() {
    // Internship state
    private val _internships = MutableStateFlow<List<InternshipResponse>>(emptyList())
    val internships: StateFlow<List<InternshipResponse>> = _internships

    private val _isLoadingInternship = MutableStateFlow(false)
    val isLoadingInternship: StateFlow<Boolean> = _isLoadingInternship

    private val _errorInternship = MutableStateFlow<String?>(null)
    val errorInternship: StateFlow<String?> = _errorInternship

    fun fetchInternships() {
        viewModelScope.launch {
            _isLoadingInternship.value = true
            try {
                _internships.value = internshipRepository.getInternships()
                _errorInternship.value = null
            } catch (e: Exception) {
                _errorInternship.value = e.message
            } finally {
                _isLoadingInternship.value = false
            }
        }
    }

    // Team state
    private val _teams = MutableStateFlow<List<TeamResponse>>(emptyList())
    val teams: StateFlow<List<TeamResponse>> = _teams

    private val _isLoadingTeam = MutableStateFlow(false)
    val isLoadingTeam: StateFlow<Boolean> = _isLoadingTeam

    private val _errorTeam = MutableStateFlow<String?>(null)
    val errorTeam: StateFlow<String?> = _errorTeam

    fun fetchTeams() {
        viewModelScope.launch {
            _isLoadingTeam.value = true
            try {
                _teams.value = teamRepository.getTeams()
                _errorTeam.value = null
            } catch (e: Exception) {
                _errorTeam.value = e.message
            } finally {
                _isLoadingTeam.value = false
            }
        }
    }
}