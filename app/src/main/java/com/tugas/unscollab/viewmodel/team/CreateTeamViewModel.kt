package com.tugas.unscollab.viewmodel.team

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.local.SessionManager
import com.tugas.unscollab.data.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTeamViewModel @Inject constructor(
    private val teamRepository: TeamRepository,
    private val sessionManager: SessionManager
): ViewModel() {
    private val _session = MutableStateFlow<Pair<String?, String?>>(null to null)
    val session: StateFlow<Pair<String?, String?>> = _session

    init {
        viewModelScope.launch {
            sessionManager.getSession().collect { pair ->
                _session.value = pair
            }
        }
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun createTeam(
        teamName: String,
        category: String,
        maxMember: Int,
        deadline: String,
        description: String,
        requirement: String,
        tag: String,
        imageUri: Uri?
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val (userId, _) = session.value
                if(userId == null) {
                    _errorMessage.value = "User not logged in"
                    return@launch
                }

                teamRepository.createTeam(
                    idUser = userId,
                    teamName = teamName,
                    category = category,
                    maxMember = maxMember,
                    deadline = deadline,
                    description = description,
                    requirement = requirement,
                    tag = tag,
                    imageUri = imageUri
                )

                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}