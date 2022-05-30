package com.example.domain.usecases

import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.interfaces.DatabaseRepository
import com.example.domain.usecases.local.LoadAllHabitsWithDoneDatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LoadAllHabitsWithDoneDatesUseCaseTest {

    private val testRepository = mock<DatabaseRepository<HabitWithDoneDates>>()

    @Test
    fun `should return the same data as in repository`(){
        val expected = arrayListOf(
            HabitWithDoneDates(Habit("test", "test", Priority.High, Type.Good, 1, 1, "test uid 1"), emptyList()),
            HabitWithDoneDates(Habit("test2", "test2", Priority.Low, Type.Bad, 2, 4, "test uid 2"), arrayListOf(
                DoneDate("test uid 2", 1111)
            )
        ))
        Mockito.`when`(testRepository.getDataList()).thenReturn(flow { emit(expected) })
        val loadAllHabitsWithDoneDatesUseCase = LoadAllHabitsWithDoneDatesUseCase(testRepository, Dispatchers.Default)
        runBlocking {
            loadAllHabitsWithDoneDatesUseCase.loadHabits().collect { actual ->
                Assert.assertArrayEquals(expected.toTypedArray(), actual.toTypedArray())
            }
        }
    }
}