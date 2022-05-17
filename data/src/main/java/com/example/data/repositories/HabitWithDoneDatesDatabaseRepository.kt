package com.example.data.repositories

import com.example.data.HabitDao
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.interfaces.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class HabitWithDoneDatesDatabaseRepository(private val dao: HabitDao) : DatabaseRepository<HabitWithDoneDates> {
    override fun getDataList(): Flow<List<HabitWithDoneDates>> {
        return dao.getAllHabitsWithDoneDates()
    }

    override fun getByUid(uid: String): HabitWithDoneDates? {
        return dao.getHabitWithDoneDatesByUid(uid)
    }

    override fun deleteByUid(uid: String) {
        dao.deleteHabitByUid(uid)
    }

    override fun insertAll(vararg obj: HabitWithDoneDates) {
        dao.insertAllHabitsWithDoneDates(*obj)
    }

    override fun insertAll(objects: List<HabitWithDoneDates>) {
        dao.insertAllHabitsWithDoneDates(objects)
    }

    override fun update(obj: HabitWithDoneDates) {
        TODO("Not yet implemented")
    }
}