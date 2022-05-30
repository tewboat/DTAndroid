package com.example.domain.usecases.local

import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.interfaces.DatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DoneHabitUseCase(
    private val habitRepository: DatabaseRepository<DoneDate>,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun doHabit(habit: Habit, date: Long): Boolean {
        return withContext(dispatcher) {
            try {
                habitRepository.insertAll(
                    DoneDate(
                        habit.uid,
                        date
                    )
                )
                return@withContext true
            } catch (e: Exception) {
                return@withContext false
            }
        }
    }
}