package com.example.domain.usecases.local

import com.example.domain.entities.Habit
import com.example.domain.interfaces.DatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class SaveHabitsUseCase(
    private val habitRepository: DatabaseRepository<Habit>,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun saveHabits(habits: List<Habit>): Boolean {
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