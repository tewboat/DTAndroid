package com.example.domain.usecases

import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.interfaces.RemoteRepository
import com.example.domain.usecases.remote.DoneRemoteHabitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class DoneRemoteHabitUseCaseTest {

    @Test
    fun `should post passed value and return true if successful`() {
        val testRepository = mock<RemoteRepository>()

        val habit = Habit("test", "test", Priority.High, Type.Good, 1, 1, "test uid 1")
        val date = 1000L

        var actual: DoneDate? = null
        val expected = DoneDate(habit.uid, date)

        runBlocking {
            Mockito.`when`(testRepository.postDone(any())).then { invocation ->
                actual = invocation.getArgument<DoneDate>(0)
                return@then Unit
            }
            val doneRemoteHabitUseCase = DoneRemoteHabitUseCase(testRepository, Dispatchers.Default)
            val result = doneRemoteHabitUseCase.doHabit(habit, date)
            Assert.assertNotNull(actual)
            Assert.assertEquals(expected, actual)
            Assert.assertTrue(result)
        }
    }

    @Test
    fun `should return false when exception throws`() {
        val testRepository = mock<RemoteRepository>()
        runBlocking {
            Mockito.`when`(testRepository.postDone(any()))
                .then {
                    throw Exception()
                }
            val doneRemoteHabitUseCase = DoneRemoteHabitUseCase(testRepository, Dispatchers.Default)
            runBlocking {
                val result = doneRemoteHabitUseCase.doHabit(Habit(), 1000)
                Assert.assertFalse(result)
            }
        }
    }
}