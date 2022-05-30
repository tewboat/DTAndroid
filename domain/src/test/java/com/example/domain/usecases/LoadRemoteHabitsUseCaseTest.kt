package com.example.domain.usecases

import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.interfaces.RemoteRepository
import com.example.domain.usecases.remote.LoadRemoteHabitsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LoadRemoteHabitsUseCaseTest {

    @Test
    fun `should return the same data as in repository`(){
        val testRepository = mock<RemoteRepository>()
        val expected = arrayListOf(
            HabitWithDoneDates(Habit("test", "test", Priority.High, Type.Good, 1, 1, "test uid 1"), emptyList()),
            HabitWithDoneDates(
                Habit("test2", "test2", Priority.Low, Type.Bad, 2, 4, "test uid 2"), arrayListOf(
                DoneDate("test uid 2", 1111)
            )
            )
        )
        runBlocking {
            Mockito.`when`(testRepository.getAll()).thenReturn(expected)
            val loadRemoteHabitsUseCase = LoadRemoteHabitsUseCase(testRepository, Dispatchers.Default)
            val actual = loadRemoteHabitsUseCase.loadHabits()
            Assert.assertNotNull(actual)
            Assert.assertArrayEquals(expected.toTypedArray(), actual?.toTypedArray())
        }
    }

    @Test
    fun `should return null when exception throws`(){
        val testRepository = mock<RemoteRepository>()

        runBlocking {
            Mockito.`when`(testRepository.getAll()).then{
                throw Exception()
            }
            val loadRemoteHabitsUseCase = LoadRemoteHabitsUseCase(testRepository, Dispatchers.Default)
            val actual = loadRemoteHabitsUseCase.loadHabits()
            Assert.assertNull(actual)
        }
    }
}