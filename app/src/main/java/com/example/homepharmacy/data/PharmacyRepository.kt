package com.example.homepharmacy.data

import kotlinx.coroutines.flow.Flow

class PharmacyRepository(
    private val medicineDao: MedicineDao,
    private val intakeDao: IntakeDao,
    private val interactionDataSource: InteractionDataSource
) {

    fun getMedicines(form: String?): Flow<List<Medicine>> =
        medicineDao.getMedicines(form)

    suspend fun addMedicine(medicine: Medicine): Long =
        medicineDao.insert(medicine)

    suspend fun updateMedicine(medicine: Medicine) =
        medicineDao.update(medicine)

    suspend fun deleteMedicine(medicine: Medicine) =
        medicineDao.delete(medicine)

    fun getIntakes(medicineId: Long): Flow<List<Intake>> =
        intakeDao.getIntakesForMedicine(medicineId)

    suspend fun addIntake(intake: Intake) =
        intakeDao.insert(intake)

    suspend fun getInteractions(): List<Interaction> =
        interactionDataSource.loadInteractions()

    suspend fun seedIfEmpty() {
        if (medicineDao.count() == 0) {
            val now = System.currentTimeMillis()
            val day = 24 * 60 * 60 * 1000L
            medicineDao.insertAll(
                listOf(
                    Medicine(
                        name = "Парацетамол",
                        form = "Таблетки",
                        activeSubstance = "Парацетамол",
                        quantity = 10,
                        productionDate = now - 30 * day,
                        expirationDate = now + 5 * day
                    ),
                    Medicine(
                        name = "Ибупрофен",
                        form = "Таблетки",
                        activeSubstance = "Ибупрофен",
                        quantity = 5,
                        productionDate = now - 60 * day,
                        expirationDate = now + 30 * day
                    ),
                    Medicine(
                        name = "Амброксол",
                        form = "Сироп",
                        activeSubstance = "Амброксол",
                        quantity = 1,
                        productionDate = now - 90 * day,
                        expirationDate = now + 2 * day
                    ),
                    Medicine(
                        name = "Витамин C",
                        form = "Таблетки",
                        activeSubstance = "Витамин C",
                        quantity = 50,
                        productionDate = now - 10 * day,
                        expirationDate = now + 100 * day
                    )
                )
            )
        }
    }
}