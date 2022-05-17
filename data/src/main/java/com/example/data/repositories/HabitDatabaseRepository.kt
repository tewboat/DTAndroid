package com.example.data.repositories

import com.example.domain.entities.Habit
import com.example.domain.interfaces.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class HabitDatabaseRepository(private val dao: com.example.data.HabitDao) : DatabaseRepository<Habit>{
    override fun getDataList(): Flow<List<Habit>> {
        return dao.getAllHabits()
    }

    override fun getByUid(uid: String): Habit? {
        return dao.getHabitByUid(uid)
    }

    override fun deleteByUid(uid: String) {
        return dao.deleteHabitByUid(uid)
    }

    override fun insertAll(vararg obj: Habit) {
        dao.insertAllHabits(*obj)
    }

    override fun insertAll(objects: List<Habit>) {
        dao.insertAllHabits(objects)
    }

    override fun update(obj: Habit) {
        dao.updateHabit(obj)
    }

}