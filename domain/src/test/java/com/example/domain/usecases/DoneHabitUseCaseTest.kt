package com.example.domain.usecases

import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.interfaces.DatabaseRepository
import com.example.domain.usecases.local.DoneHabitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.anyVararg
import org.mockito.kotlin.mock


class DoneHabitUseCaseTest {

    private val testRepository = mock<DatabaseRepository<DoneDate>>()

    private val habit = Habit(uid = "habit uid")
    private val date = 1000L

    @Test
    fun `should save passed value and return true if successful`() {
        var actual: DoneDate? = null
        val expected = DoneDate(
            habit.uid,
            date
        )

        Mockito.`when`(testRepository.insertAll(anyVararg<DoneDate>())).then { invocation ->
            actual = invocation.getArgument<DoneDate>(0)
            return@then Unit
        }

        val doneHabitUseCase = DoneHabitUseCase(testRepository, Dispatchers.Default)
        runBlocking {
            val result = doneHabitUseCase.doHabit(habit, date)
            Assert.assertNotNull(actual)
            Assert.assertEquals(expected, actual)
            Assert.assertTrue(result)
        }
    }

    @Test
    fun `should return false when exception throws`() {
        Mockito.`when`(testRepository.insertAll(anyVararg<DoneDate>())).then {
            throw Exception()
        }
        val doneHabitUseCase = DoneHabitUseCase(testRepository, Dispatchers.Default)
        runBlocking {
            val result = doneHabitUseCase.doHabit(habit, date)
            Assert.assertFalse(result)
        }
    }
}