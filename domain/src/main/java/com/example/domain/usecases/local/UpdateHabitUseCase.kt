package com.example.domain.usecases.local

import com.example.domain.entities.Habit
import com.example.domain.interfaces.DatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class UpdateHabitUseCase(
    private val habitRepository: DatabaseRepository<Habit>,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun updateHabit(habit: Habit): Boolean {
        return withContext(dispatcher) {
            try {
                habitRepository.update(habit)
                return@withContext true
            } catch (e: Exception){
                return@withContext false;
            }
        }
    }
}