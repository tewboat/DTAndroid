package com.example.domain.usecases

import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.interfaces.DatabaseRepository
import com.example.domain.usecases.local.SaveHabitsWithDoneDatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SaveHabitsWithDoneDatesUseCaseTest {

    @Test
    fun `should save passed value and return true if successful`() {
        val testRepository = mock<DatabaseRepository<HabitWithDoneDates>>()
        var actual: List<HabitWithDoneDates>? = null
        val expected = arrayListOf(
            HabitWithDoneDates(
                Habit("test", "test", Priority.High, Type.Good, 1, 1, "test uid 1"),
                emptyList()
            ),
            HabitWithDoneDates(
                Habit("test2", "test2", Priority.Low, Type.Bad, 2, 4, "test uid 2"), arrayListOf(
                    DoneDate("test uid 2", 1111)
                )
            )
        )

        Mockito.`when`(testRepository.insertAll(ArgumentMatchers.anyList()))
            .then { invocation ->
                actual = invocation.getArgument<List<HabitWithDoneDates>>(0)
                return@then Unit
            }

        val saveHabitsWithDoneDatesUseCase =
            SaveHabitsWithDoneDatesUseCase(testRepository, Dispatchers.Default)
        runBlocking {
            val result = saveHabitsWithDoneDatesUseCase.saveHabits(expected)
            Assert.assertNotNull(actual)
            Assert.assertEquals(expected, actual)
            Assert.assertTrue(result)
        }
    }

    @Test
    fun `should return false when exception throws`() {
        val testRepository = mock<DatabaseRepository<HabitWithDoneDates>>()
        Mockito.`when`(testRepository.insertAll(ArgumentMatchers.anyList()))
            .then {
                throw Exception()
            }
        val saveHabitsWithDoneDatesUseCase = SaveHabitsWithDoneDatesUseCase(testRepository, Dispatchers.Default)
        runBlocking {
            val result = saveHabitsWithDoneDatesUseCase.saveHabits(listOf())
            Assert.assertFalse(result)
        }
    }
}