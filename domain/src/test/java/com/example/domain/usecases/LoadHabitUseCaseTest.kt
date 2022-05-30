package com.example.domain.usecases

import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.interfaces.DatabaseRepository
import com.example.domain.usecases.local.LoadHabitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.kotlin.mock

class LoadHabitUseCaseTest {
    private val testRepository = mock<DatabaseRepository<Habit>>()

    @Test
    fun `should return data with required uid`() {
        val testData = arrayListOf(
            Habit("test", "test", Priority.High, Type.Good, 1, 1, "test uid 1"),
            Habit("test2", "test2", Priority.Low, Type.Bad, 2, 4, "test uid 2")
        )
        Mockito.`when`(testRepository.getByUid(anyString())).thenAnswer{ invocation ->
            testData.first{it.uid == invocation.arguments[0]}
        }
        val loadHabitUseCase = LoadHabitUseCase(testRepository, Dispatchers.Default)
        runBlocking {
            val expected = testData[0]
            val actual = loadHabitUseCase.loadHabit("test uid 1")
            Assert.assertEquals(expected, actual)
        }
    }
}