package com.example.data.repositories

import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.HabitDone
import com.example.domain.entities.HabitUID
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.interfaces.RemoteRepository


class HabitRemoteRepository(private val doubletappApi: com.example.data.DoubletappApi) : RemoteRepository {
    private val token = "2f051711-d95a-4fde-88c1-371129b6bc99"

    override suspend fun getAll(): List<HabitWithDoneDates> = doubletappApi.getHabits(token)

    override suspend fun put(habit: Habit): HabitUID = doubletappApi.putHabit(token, habit)

    override suspend fun deleteByUid(uid: String): HabitUID = doubletappApi.deleteHabit(token, uid)

    override suspend fun postDone(doneDate: DoneDate) =
        doubletappApi.done(token, doneDate)

}
