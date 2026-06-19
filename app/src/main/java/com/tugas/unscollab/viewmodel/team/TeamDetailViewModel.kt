package com.tugas.unscollab.viewmodel.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.local.SessionManager
import com.tugas.unscollab.data.repository.TeamRepository
import com.tugas.unscollab.data.response.TeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val repository: TeamRepository,
    private val sessionManager: SessionManager
): ViewModel() {

    //Session State
    private val _session = MutableStateFlow<String?>(null)
    val session: StateFlow<String?> = _session

    init {
        viewModelScope.launch {
            sessionManager.getSession().collect { pair ->
                _session.value = pair.first
            }
        }
    }

    private val _selectedTeam = MutableStateFlow<TeamResponse?>(null)
    val selectedTeam: StateFlow<TeamResponse?> = _selectedTeam

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getTeamById(idTeam: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _selectedTeam.value = repository.getTeamById(idTeam)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun joinTeam(idTeam: String) {
        viewModelScope.launch {
            val idUser = session.value?.trim()
            if(idUser == null) {
                _errorMessage.value = "User not logged in"
                return@launch
            }

            try {
                repository.joinTeam(idUser, idTeam)
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}