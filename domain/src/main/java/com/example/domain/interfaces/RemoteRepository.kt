package com.example.domain.interfaces

import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.HabitDone
import com.example.domain.entities.HabitUID
import com.example.domain.entities.relations.HabitWithDoneDates

interface RemoteRepository {
    suspend fun getAll(): List<HabitWithDoneDates>
    suspend fun put(habit: Habit): HabitUID
    suspend fun deleteByUid(uid: String): HabitUID
    suspend fun postDone(doneDate: DoneDate): HabitDone
}