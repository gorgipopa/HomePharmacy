package com.example.homepharmacy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homepharmacy.data.Medicine
import com.example.homepharmacy.data.PharmacyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MedicineViewModel(private val repository: PharmacyRepository) : ViewModel() {

    private val _selectedForm = MutableStateFlow<String?>(null)
    val selectedForm: StateFlow<String?> = _selectedForm.asStateFlow()

    // Реактивный список из Room: при смене фильтра flatMapLatest переподписывается
    val medicines: StateFlow<List<Medicine>> = _selectedForm
        .flatMapLatest { form -> repository.getMedicines(form) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    init {
        // Наполняем БД тестовыми данными при первом запуске
        viewModelScope.launch {
            repository.seedIfEmpty()
        }
    }

    fun setFilter(form: String?) {
        _selectedForm.value = form
    }

    fun deleteMedicine(medicine: Medicine) {
        viewModelScope.launch {
            repository.deleteMedicine(medicine)
        }
    }
}