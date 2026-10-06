package com.example.homepharmacy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val form: String,            // "Таблетки" или "Сироп"
    val activeSubstance: String, // Действующее вещество (для матрицы взаимодействий)
    val quantity: Int,           // Остаток в упаковке
    val productionDate: Long,    // Дата производства (timestamp)
    val expirationDate: Long     // Дата истечения срока годности (timestamp)
)