package com.tugas.unscollab.viewmodel.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.local.SessionManager
import com.tugas.unscollab.data.model.Student
import com.tugas.unscollab.data.model.User
import com.tugas.unscollab.data.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileUiState {
    object Loading : ProfileUiState
    data class Success(val user: User, val student: Student) : ProfileUiState
    data class Error(val message: String) : ProfileUiState
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private var dbStudentId: String = ""

    // State Dasar
    var userName by mutableStateOf("")
    var userId by mutableStateOf("")
    var userProdi by mutableStateOf("")
    var userRole by mutableStateOf("")
    var userPhotoUri by mutableStateOf("")

    // State Teks (Sesuai 4 Kolom di Database)
    var userBio by mutableStateOf("")
    var userPortofolio by mutableStateOf("")
    var userExperience by mutableStateOf("")
    var userSkill by mutableStateOf("")

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val session = sessionManager.getSession().first()
                val email = session.second

                if (!email.isNullOrEmpty()) {
                    profileRepository.getFullProfile(email).collect { result ->
                        val user = result.first
                        val student = result.second
                        dbStudentId = student.id_student

                        // Isi dari database ke UI
                        userName = student.full_name
                        userId = student.nim
                        userProdi = student.major
                        userRole = user.role
                        userPhotoUri = student.profile_picture ?: ""

                        userBio = student.bio ?: ""
                        userPortofolio = student.portofolio ?: ""
                        userExperience = student.experience ?: ""
                        userSkill = student.skill ?: ""

                        _uiState.value = ProfileUiState.Success(user, student)
                    }
                } else {
                    _uiState.value = ProfileUiState.Error("Sesi login tidak valid.")
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(e.localizedMessage ?: "Terjadi kesalahan")
            }
        }
    }

    fun updateBasicProfile(name: String, id: String, prodi: String, bio: String) {
        userName = name
        userId = id
        userProdi = prodi
        userBio = bio
        viewModelScope.launch {
            try { profileRepository.updateBasicProfile(dbStudentId, name, id, prodi, bio) } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun updateSection(columnName: String, newText: String) {
        when (columnName) {
            "portofolio" -> userPortofolio = newText
            "experience" -> userExperience = newText
            "skill" -> userSkill = newText
        }
        viewModelScope.launch {
            try { profileRepository.updateStudentInfo(dbStudentId, columnName, newText) } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun updateProfilePhoto(uri: String) {
        userPhotoUri = uri
        // Implementasi storage nanti
    }

    fun deleteProfilePhoto() {
        userPhotoUri = ""
        viewModelScope.launch {
            try { profileRepository.updateStudentInfo(dbStudentId, "profile_picture", "") } catch (e: Exception) { e.printStackTrace() }
        }
    }
}