package com.example.domain.usecases

import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.interfaces.DatabaseRepository
import com.example.domain.usecases.local.UpdateHabitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class UpdateHabitUseCaseTest {

    @Test
    fun `should update passed value and return true if successful`() {
        val testRepository = mock<DatabaseRepository<Habit>>()
        var actual: Habit? = null
        val expected = Habit("test", "test", Priority.High, Type.Good, 1, 1, "test uid 1")

        Mockito.`when`(testRepository.update(any()))
            .then { invocation ->
                actual = invocation.getArgument<Habit>(0)
                return@then Unit
            }

        val updateHabitUseCase = UpdateHabitUseCase(testRepository, Dispatchers.Default)
        runBlocking {
            val result = updateHabitUseCase.updateHabit(expected)
            Assert.assertNotNull(actual)
            Assert.assertEquals(expected, actual)
            Assert.assertTrue(result)
        }
    }

    @Test
    fun `should return false when exception throws`() {
        val testRepository = mock<DatabaseRepository<Habit>>()
        Mockito.`when`(testRepository.update(any()))
            .then {
                throw Exception()
            }
        val updateHabitUseCase = UpdateHabitUseCase(testRepository, Dispatchers.Default)
        runBlocking {
            val result = updateHabitUseCase.updateHabit(Habit())
            Assert.assertFalse(result)
        }
    }
}