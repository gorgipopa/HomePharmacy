package com.example.homepharmacy.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {

    @Query("SELECT * FROM medicines WHERE (:form IS NULL OR form = :form) ORDER BY expirationDate ASC")
    fun getMedicines(form: String?): Flow<List<Medicine>>

    @Query("SELECT * FROM medicines WHERE id = :id")
    suspend fun getById(id: Long): Medicine?

    @Insert
    suspend fun insert(medicine: Medicine): Long

    @Insert
    suspend fun insertAll(medicines: List<Medicine>)

    @Update
    suspend fun update(medicine: Medicine)

    @Delete
    suspend fun delete(medicine: Medicine)

    @Query("SELECT COUNT(*) FROM medicines")
    suspend fun count(): Int
}