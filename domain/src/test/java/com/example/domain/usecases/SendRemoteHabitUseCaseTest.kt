package com.example.domain.usecases

import com.example.domain.entities.*
import com.example.domain.interfaces.RemoteRepository
import com.example.domain.usecases.remote.SendRemoteHabitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class SendRemoteHabitUseCaseTest {
    @Test
    fun `should send new habit to remote and return new uid`() {

        val testRepository = mock<RemoteRepository>()
        val habit = Habit("test", "test", Priority.High, Type.Good, 1, 1)
        val expected = "new uid"

        runBlocking {
            Mockito.`when`(testRepository.put(any())).thenReturn(HabitUID(uid = expected))
            val sendRemoteHabitsUseCase =
                SendRemoteHabitUseCase(testRepository, Dispatchers.Default)
            val actual = sendRemoteHabitsUseCase.sendHabit(habit)
            Assert.assertNotNull(actual)
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun `should send existed habit to remote and return its uid`() {

        val testRepository = mock<RemoteRepository>()
        val habit = Habit("test", "test", Priority.High, Type.Good, 1, 1, uid = "new uid")

        runBlocking {
            Mockito.`when`(testRepository.put(any())).thenReturn(HabitUID(uid = habit.uid))
            val sendRemoteHabitsUseCase =
                SendRemoteHabitUseCase(testRepository, Dispatchers.Default)
            val actual = sendRemoteHabitsUseCase.sendHabit(habit)
            Assert.assertNotNull(actual)
            Assert.assertEquals(habit.uid, actual)
        }
    }

    @Test
    fun `should return null when exception throws`() {

        val testRepository = mock<RemoteRepository>()
        val habit = Habit("test", "test", Priority.High, Type.Good, 1, 1, uid = "new uid")

        runBlocking {
            Mockito.`when`(testRepository.put(any())).then{
                throw Exception()
            }
            val sendRemoteHabitsUseCase =
                SendRemoteHabitUseCase(testRepository, Dispatchers.Default)
            val actual = sendRemoteHabitsUseCase.sendHabit(habit)
            Assert.assertNull(actual)
        }
    }
}