package com.example.domain.usecases.remote

import com.example.domain.entities.Habit
import com.example.domain.interfaces.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class SendRemoteHabitUseCase(
    private val remoteRepository: RemoteRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun sendHabit(habit: Habit): String? {
        return withContext(dispatcher) {
            try {
                return@withContext remoteRepository.put(habit).uid
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }
}