package com.tugas.unscollab.ui.screens.team

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.repository.TeamRepository
import com.tugas.unscollab.data.response.TeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTeamViewModel @Inject constructor(
    private val repository: TeamRepository
) : ViewModel() {

    private val _teamData = MutableStateFlow<TeamResponse?>(null)
    val teamData: StateFlow<TeamResponse?> = _teamData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> = _isSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Ambil data tim lama untuk mengisi form (Pre-fill)
    fun getTeamData(idTeam: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getTeamById(idTeam)
                _teamData.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateTeam(
        idTeam: String,
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
                val currentImageUrl = _teamData.value?.team?.team_logo
                repository.updateTeam(
                    idTeam = idTeam,
                    teamName = teamName,
                    category = category,
                    maxMember = maxMember,
                    deadline = deadline,
                    description = description,
                    requirement = requirement,
                    tag = tag,
                    imageUri = imageUri,
                    existingImageUrl = currentImageUrl
                )
                _isSuccess.value = true
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}