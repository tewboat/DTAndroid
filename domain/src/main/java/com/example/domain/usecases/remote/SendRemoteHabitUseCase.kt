package com.example.domain.usecases.remote

import android.util.Log
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
                val result = remoteRepository.put(habit).uid
                Log.d("sendHabit", result.toString())
                return@withContext result
            } catch (e: Exception) {
                Log.d("sendHabit", e.toString())
                return@withContext null
            }
        }
    }
}