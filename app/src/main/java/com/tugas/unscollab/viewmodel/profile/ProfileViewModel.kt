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

sealed interface UpdateState {
    object Idle    : UpdateState
    object Loading : UpdateState
    object Success : UpdateState
    data class Error(val message: String) : UpdateState
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Idle)
    val updateState: StateFlow<UpdateState> = _updateState.asStateFlow()

    private var dbStudentId: String = ""

    var userName     by mutableStateOf(""); private set
    var userId       by mutableStateOf(""); private set
    var userProdi    by mutableStateOf(""); private set
    var userRole     by mutableStateOf(""); private set
    var userPhotoUri by mutableStateOf(""); private set

    var userBio        by mutableStateOf(""); private set
    var userPortofolio by mutableStateOf(""); private set
    var userExperience by mutableStateOf(""); private set
    var userSkill      by mutableStateOf(""); private set

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            try {
                val session = sessionManager.getSession().first()
                val email   = session.second

                if (!email.isNullOrEmpty()) {
                    profileRepository.getFullProfile(email).collect { (user, student) ->
                        dbStudentId    = student.id_student

                        userName       = student.full_name ?: ""
                        userId         = student.nim ?: ""
                        userProdi      = student.major ?: ""
                        userRole       = user.role ?: "student"
                        userPhotoUri   = student.profile_picture ?: ""

                        userBio        = student.bio          ?: ""
                        userPortofolio = student.portofolio   ?: ""
                        userExperience = student.experience   ?: ""
                        userSkill      = student.skill        ?: ""

                        _uiState.value = ProfileUiState.Success(user, student)
                    }
                } else {
                    _uiState.value = ProfileUiState.Error("Sesi login tidak valid.")
                }
            } catch (e: Exception) {
                _uiState.value = ProfileUiState.Error(
                    e.localizedMessage ?: "Terjadi kesalahan saat memuat profil."
                )
            }
        }
    }

    fun updateBasicProfile(name: String, id: String, prodi: String, bio: String) {
        if (!validateStudentId()) return

        userName  = name
        userId    = id
        userProdi = prodi
        userBio   = bio

        viewModelScope.launch {
            _updateState.value = UpdateState.Loading
            try {
                profileRepository.updateBasicProfile(dbStudentId, name, id, prodi, bio)
                _updateState.value = UpdateState.Success
            } catch (e: Exception) {
                println("DEBUG: gagal updateBasicProfile")
                _updateState.value = UpdateState.Error(
                    e.localizedMessage ?: "Gagal menyimpan profil."
                )
            }
        }
    }

    fun updateSection(columnName: String, newText: String) {
        if (!validateStudentId()) return

        when (columnName) {
            "portofolio" -> userPortofolio = newText
            "experience" -> userExperience = newText
            "skill"      -> userSkill      = newText
            else -> {
                _updateState.value = UpdateState.Error("Kolom tidak dikenal.")
                return
            }
        }

        viewModelScope.launch {
            _updateState.value = UpdateState.Loading
            try {
                profileRepository.updateStudentInfo(dbStudentId, columnName, newText)
                _updateState.value = UpdateState.Success
            } catch (e: Exception) {
                println("DEBUG: gagal updateSection")
                _updateState.value = UpdateState.Error(
                    e.localizedMessage ?: "Gagal menyimpan data."
                )
            }
        }
    }

    fun updateProfilePhoto(localFilePath: String) {
        if (!validateStudentId()) return

        viewModelScope.launch {
            _updateState.value = UpdateState.Loading
            try {
                val uri = android.net.Uri.parse(localFilePath)
                val file = java.io.File(uri.path!!)

                val publicUrl = profileRepository.uploadProfilePhoto(dbStudentId, file, userPhotoUri)

                userPhotoUri = publicUrl
                _updateState.value = UpdateState.Success
            } catch (e: Exception) {
                println("DEBUG: gagal updateProfilePhoto")
                e.printStackTrace()
                _updateState.value = UpdateState.Error(
                    e.localizedMessage ?: "Gagal mengunggah foto profil."
                )
            }
        }
    }

    fun deleteProfilePhoto() {
        if (!validateStudentId()) return

        viewModelScope.launch {
            _updateState.value = UpdateState.Loading
            try {
                profileRepository.deleteProfilePhoto(dbStudentId, userPhotoUri)

                userPhotoUri = ""
                _updateState.value = UpdateState.Success
            } catch (e: Exception) {
                println("DEBUG: gagal deleteProfilePhoto")
                _updateState.value = UpdateState.Error(
                    e.localizedMessage ?: "Gagal menghapus foto profil."
                )
            }
        }
    }

    fun resetUpdateState() {
        _updateState.value = UpdateState.Idle
    }

    private fun validateStudentId(): Boolean {
        return if (dbStudentId.isBlank()) {
            println("DEBUG: dbStudentId kosong")
            _updateState.value = UpdateState.Error("ID mahasiswa tidak ditemukan, coba login ulang.")
            false
        } else true
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
        }
    }
}