package com.example.homepharmacy.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IntakeDao {

    @Query("SELECT * FROM intakes WHERE medicineId = :medicineId ORDER BY timestamp DESC")
    fun getIntakesForMedicine(medicineId: Long): Flow<List<Intake>>

    @Insert
    suspend fun insert(intake: Intake)
}