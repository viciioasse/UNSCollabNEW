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
import kotlinx.coroutines.flow.first
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

    private val _dismissedIds = MutableStateFlow<Set<String>>(emptySet())

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
                val session = sessionManager.getSession().first()
                val idUser = session.first

                if(!idUser.isNullOrEmpty()) {
                    try {
                        val created = teamRepository.getMyCreatedTeams(idUser)
                        _myCreatedTeams.value = created
                    } catch (e: Exception) {
                        if(e.message?.contains("User not found") == true) {
                            _myCreatedTeams.value = emptyList()
                        }
                    }

                    try {
                        val applied = internshipRepository.getAppliedInternships(idUser)
                        _appliedInternships.value = applied
                    } catch (e: Exception) {
                        if(e.message?.contains("User not found") == true) {
                            _appliedInternships.value = emptyList()
                        }
                    }

                    try {
                        val requested = teamRepository.getJoinedTeams(idUser)
                        _requestedTeams.value = requested
                    } catch (e: Exception) {
                        if(e.message?.contains("User not found") == true) {
                            _requestedTeams.value = emptyList()
                        }
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun removeInternshipFromUI(item: ApplicationResponse) {
        // Memperbarui StateFlow dengan list baru yang sudah membuang item tersebut
        val id = item.internshipResponse.internship.id_internship
        _dismissedIds.value += id
        _appliedInternships.value = _appliedInternships.value.filter { it.internshipResponse.internship.id_internship != id }
    }

    fun removeRequestedTeamFromUI(item: JoinTeamResponse) {
        val id = item.teamResponse.team.id_team
        _dismissedIds.value += id
        _requestedTeams.value = _requestedTeams.value.filter { it.teamResponse.team.id_team != id }
    }

    fun removeMyTeamFromUI(item: TeamResponse) {
        _myCreatedTeams.value = _myCreatedTeams.value.filter { it != item }
    }

    fun handleMemberRequest(idTeam: String, idStudent: String, isAccepted: Boolean) {
        viewModelScope.launch {
            try {
                val status = if (isAccepted) "accepted" else "rejected"
                teamRepository.updateMemberStatus(idTeam, idStudent, status)

                fetchAllActivity()
            }catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            // Menghapus session (id_user dan email) dari local storage
            sessionManager.clearSession()

            // Opsional: Bersihkan state lokal jika perlu
            _myCreatedTeams.value = emptyList()
            _appliedInternships.value = emptyList()
            _requestedTeams.value = emptyList()
            _session.value = Pair(null, null)
        }
    }
}