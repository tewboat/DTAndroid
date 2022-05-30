package com.example.domain.usecases

import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.interfaces.DatabaseRepository
import com.example.domain.usecases.local.SaveHabitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.anyVararg
import org.mockito.kotlin.mock

class SaveHabitUseCaseTest {

    @Test
    fun `should save passed value and return true if successful`() {
        val testRepository = mock<DatabaseRepository<Habit>>()
        var actual: Habit? = null
        val expected = Habit("test", "test", Priority.High, Type.Good, 1, 1, "test uid 1")

        Mockito.`when`(testRepository.insertAll(anyVararg<Habit>()))
            .then { invocation ->
                actual = invocation.getArgument<Habit>(0)
                return@then Unit
            }

        val saveHabitUseCase = SaveHabitUseCase(testRepository, Dispatchers.Default)
        runBlocking {
            val result = saveHabitUseCase.saveHabit(expected)
            Assert.assertNotNull(actual)
            Assert.assertEquals(expected, actual)
            Assert.assertTrue(result)
        }
    }

    @Test
    fun `should return false when exception throws`() {
        val testRepository = mock<DatabaseRepository<Habit>>()
        Mockito.`when`(testRepository.insertAll(anyVararg<Habit>()))
            .then {
                throw Exception()
            }
        val saveHabitUseCase = SaveHabitUseCase(testRepository, Dispatchers.Default)
        runBlocking {
            val result = saveHabitUseCase.saveHabit(Habit())
            Assert.assertFalse(result)
        }
    }

}