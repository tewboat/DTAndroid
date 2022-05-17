package com.example.domain.usecases.local

import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.interfaces.DatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class SaveHabitsWithDoneDatesUseCase(
    private val habitRepository: DatabaseRepository<HabitWithDoneDates>,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun saveHabits(habits: List<HabitWithDoneDates>): Boolean {
        return withContext(dispatcher) {
            try {
                habitRepository.insertAll(habits)
                return@withContext true
            } catch (e: Exception) {
                return@withContext false
            }
        }
    }
}