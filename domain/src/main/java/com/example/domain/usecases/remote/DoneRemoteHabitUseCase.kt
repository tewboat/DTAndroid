package com.example.domain.usecases.remote

import android.util.Log
import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.HabitDone
import com.example.domain.interfaces.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class DoneRemoteHabitUseCase(
    private val remoteRepository: RemoteRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun doHabit(habit: Habit, date: Long) {
        return withContext(dispatcher){
            try {
                remoteRepository.postDone(DoneDate(habit.uid, date))
            } catch (e: Exception){
                Log.d("Retrofit", e.message.toString())
            }
        }
    }
}