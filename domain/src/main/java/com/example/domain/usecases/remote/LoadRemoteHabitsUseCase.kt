package com.example.domain.usecases.remote

import android.util.Log
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.interfaces.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoadRemoteHabitsUseCase(
    private val remoteRepository: RemoteRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun loadHabits(): List<HabitWithDoneDates>? {
        return withContext(dispatcher) {
            try {
                return@withContext remoteRepository.getAll()
            } catch (e: Exception) {
                Log.d("Retrofit", e.message.toString())
                return@withContext null
            }
        }
    }
}