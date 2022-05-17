package com.example.domain.usecases.local

import com.example.domain.entities.Habit
import com.example.domain.interfaces.DatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoadHabitUseCase(
    private val habitRepository: DatabaseRepository<Habit>,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadHabit(habitUid: String): Habit? {
        return withContext(dispatcher) {
            return@withContext habitRepository.getByUid(habitUid)
        }
    }
}