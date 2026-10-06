package com.example.homepharmacy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homepharmacy.data.AppDatabase
import com.example.homepharmacy.data.InteractionDataSource
import com.example.homepharmacy.data.PharmacyRepository
import com.example.homepharmacy.ui.MedicineScreen
import com.example.homepharmacy.ui.MedicineViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Собираем зависимости вручную (Koin подключим в следующих модулях)
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = PharmacyRepository(
            medicineDao = database.medicineDao(),
            intakeDao = database.intakeDao(),
            interactionDataSource = InteractionDataSource(applicationContext)
        )

        setContent {
            val viewModel: MedicineViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return MedicineViewModel(repository) as T
                    }
                }
            )
            MedicineScreen(viewModel = viewModel)
        }
    }
}