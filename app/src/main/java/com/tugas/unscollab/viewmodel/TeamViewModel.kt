package com.tugas.unscollab.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.model.Team
import com.tugas.unscollab.data.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel() {

    private val repository: TeamRepository = TeamRepository

    private var _teams = MutableStateFlow<List<Team>>(emptyList())

    val teams: StateFlow<List<Team>> = _teams

    init {
        loadTeams()
    }

    private fun loadTeams() {
        viewModelScope.launch {
            val data = repository.getTeams()
            _teams.value = data
        }
    }
}