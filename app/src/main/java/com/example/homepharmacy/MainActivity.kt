package com.example.homepharmacy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homepharmacy.ui.MedicineScreen
import com.example.homepharmacy.ui.MedicineViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MedicineViewModel = viewModel()
            MedicineScreen(viewModel = viewModel)
        }
    }
}