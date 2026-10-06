package com.example.homepharmacy.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "intakes",
    foreignKeys = [
        ForeignKey(
            entity = Medicine::class,
            parentColumns = ["id"],
            childColumns = ["medicineId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Intake(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val medicineId: Long,     // Ссылка на Medicine.id
    val timestamp: Long,      // Когда был приём
    val dose: String,         // Например, "1 таблетка"
    val note: String = ""     // Заметка (необязательно)
)