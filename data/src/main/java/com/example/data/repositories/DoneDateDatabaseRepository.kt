package com.example.data.repositories

import com.example.data.HabitDao
import com.example.domain.entities.DoneDate
import com.example.domain.interfaces.DatabaseRepository
import kotlinx.coroutines.flow.Flow

class DoneDateDatabaseRepository(private val dao: HabitDao): DatabaseRepository<DoneDate> {
    override fun getDataList(): Flow<List<DoneDate>> {
        TODO("Not yet implemented")
    }

    override fun getByUid(uid: String): DoneDate? {
        TODO("Not yet implemented")
    }

    override fun deleteByUid(uid: String) {
        TODO("Not yet implemented")
    }

    override fun insertAll(vararg obj: DoneDate) {
        dao.insertDoneDate(obj.toList())
    }

    override fun insertAll(objects: List<DoneDate>) {
        dao.insertDoneDate(objects)
    }

    override fun update(obj: DoneDate) {
        TODO("Not yet implemented")
    }
}