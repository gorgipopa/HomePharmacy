package com.example.homepharmacy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineScreen(viewModel: MedicineViewModel) {
    val medicines by viewModel.medicines.collectAsStateWithLifecycle()
    val selectedForm by viewModel.selectedForm.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Домашняя аптечка") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Блок фильтрации
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedForm == null,
                    onClick = { viewModel.setFilter(null) },
                    label = { Text("Все") }
                )
                FilterChip(
                    selected = selectedForm == "Таблетки",
                    onClick = { viewModel.setFilter("Таблетки") },
                    label = { Text("Таблетки") }
                )
                FilterChip(
                    selected = selectedForm == "Сироп",
                    onClick = { viewModel.setFilter("Сироп") },
                    label = { Text("Сиропы") }
                )
            }

            // Список лекарств
            if (medicines.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Нет лекарств")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(medicines) { med ->
                        MedicineCard(med)
                    }
                }
            }
        }
    }
}

@Composable
fun MedicineCard(medicine: Medicine) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val dateStr = dateFormat.format(Date(medicine.expirationDate))

    // Сколько дней осталось до истечения срока
    val daysLeft = (medicine.expirationDate - System.currentTimeMillis()) / (1000 * 60 * 60 * 24)
    val isCritical = daysLeft <= 7

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isCritical) MaterialTheme.colorScheme.errorContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = medicine.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text("Форма: ${medicine.form}")
            Text("В наличии: ${medicine.quantity} шт.")
            Text(
                text = "Годен до: $dateStr",
                color = if (isCritical) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}