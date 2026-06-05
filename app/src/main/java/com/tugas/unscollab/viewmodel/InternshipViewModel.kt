package com.tugas.unscollab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tugas.unscollab.data.model.Internship
import com.tugas.unscollab.data.repository.InternshipRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InternshipViewModel (
    private val repository: InternshipRepository = InternshipRepository
) : ViewModel() {
    private val _internships = MutableStateFlow<List<Internship>>(emptyList())
    val internships: StateFlow<List<Internship>> = _internships

    init {
        loadInternships()
    }

    private fun loadInternships() {
        viewModelScope.launch {
            val data = repository.getInternships()
            _internships.value = data
        }
    }
}