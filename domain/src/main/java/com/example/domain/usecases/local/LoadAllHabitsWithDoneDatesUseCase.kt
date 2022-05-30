package com.example.domain.usecases.local

import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.interfaces.DatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class LoadAllHabitsWithDoneDatesUseCase(
    private val habitRepository: DatabaseRepository<HabitWithDoneDates>,
    private val dispatcher: CoroutineDispatcher
) {
    fun loadHabits(): Flow<List<HabitWithDoneDates>> {
        return habitRepository.getDataList()
            .flowOn(dispatcher)
    }
}