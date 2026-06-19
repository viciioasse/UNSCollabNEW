package com.tugas.unscollab.viewmodel.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.local.SessionManager
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.data.repository.TeamRepository
import com.tugas.unscollab.data.response.TeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTeamViewModel @Inject constructor(
    private val teamRepository: TeamRepository
): ViewModel() {

    private val _teams = MutableStateFlow<List<TeamResponse>>(emptyList())
    val teams: StateFlow<List<TeamResponse>> = _teams

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchTeams() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _teams.value = teamRepository.getTeams()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}