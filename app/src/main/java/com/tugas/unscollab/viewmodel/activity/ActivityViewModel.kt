package com.tugas.unscollab.viewmodel.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.local.SessionManager
import com.tugas.unscollab.data.repository.InternshipRepository
import com.tugas.unscollab.data.repository.TeamRepository
import com.tugas.unscollab.data.response.ApplicationResponse
import com.tugas.unscollab.data.response.JoinTeamResponse
import com.tugas.unscollab.data.response.TeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val internshipRepository: InternshipRepository,
    private val teamRepository: TeamRepository,
    private val sessionManager: SessionManager
): ViewModel() {
    private val _myCreatedTeams = MutableStateFlow<List<TeamResponse>>(emptyList())
    val myCreatedTeams: StateFlow<List<TeamResponse>> = _myCreatedTeams

    private val _appliedInternships = MutableStateFlow<List<ApplicationResponse>>(emptyList())
    val appliedInternships: StateFlow<List<ApplicationResponse>> = _appliedInternships

    private val _requestedTeams = MutableStateFlow<List<JoinTeamResponse>>(emptyList())
    val requestedTeams: StateFlow<List<JoinTeamResponse>> = _requestedTeams

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _session = MutableStateFlow<Pair<String?, String?>>(null to null)
    val session: StateFlow<Pair<String?, String?>> = _session

    init {
        viewModelScope.launch {
            sessionManager.getSession().collect { pair ->
                _session.value = pair
            }
        }

        fetchAllActivity()
    }

    fun fetchAllActivity() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                sessionManager.getSession().collect { session ->
                    val idUser = session.first

                    if(idUser != null) {
                        val created = teamRepository.getMyCreatedTeams(idUser)
                        _myCreatedTeams.value = created

                        val applied = internshipRepository.getAppliedInternships(idUser)
                        _appliedInternships.value = applied

                        val requested = teamRepository.getJoinedTeams(idUser)
                        _requestedTeams.value = requested
                    } else {
                        _errorMessage.value = "User not logged in"
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}