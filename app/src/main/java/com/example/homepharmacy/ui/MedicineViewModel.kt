package com.example.homepharmacy.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit

class MedicineViewModel : ViewModel() {

    // Тестовые данные (позже заменим на Room)
    private val allMedicines = listOf(
        Medicine(1, "Парацетамол", "Таблетки", System.currentTimeMillis() + TimeUnit.DAYS.toMillis(5), 10),
        Medicine(2, "Ибупрофен", "Таблетки", System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30), 5),
        Medicine(3, "Амброксол", "Сироп", System.currentTimeMillis() + TimeUnit.DAYS.toMillis(2), 1),
        Medicine(4, "Витамин C", "Таблетки", System.currentTimeMillis() + TimeUnit.DAYS.toMillis(100), 50)
    )

    // Текущий выбранный фильтр (null = показать все)
    private val _selectedForm = MutableStateFlow<String?>(null)
    val selectedForm: StateFlow<String?> = _selectedForm.asStateFlow()

    // Отфильтрованный и отсортированный список
    private val _medicines = MutableStateFlow<List<Medicine>>(emptyList())
    val medicines: StateFlow<List<Medicine>> = _medicines.asStateFlow()

    init {
        updateList()
    }

    fun setFilter(form: String?) {
        _selectedForm.value = form
        updateList()
    }

    private fun updateList() {
        val filtered = if (_selectedForm.value == null) {
            allMedicines
        } else {
            allMedicines.filter { it.form == _selectedForm.value }
        }
        // Сортировка по сроку годности: сначала те, что скоро истекают
        _medicines.value = filtered.sortedBy { it.expirationDate }
    }
}