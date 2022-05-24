package com.example.dtandroid.presentation.viewmodels

import androidx.lifecycle.*
import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.usecases.local.LoadHabitUseCase
import com.example.domain.usecases.local.SaveHabitUseCase
import com.example.domain.usecases.local.UpdateHabitUseCase
import com.example.domain.usecases.remote.SendRemoteHabitUseCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.NullPointerException

class HabitViewModel(
    private val loadHabitUseCase: LoadHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val saveHabitUseCase: SaveHabitUseCase,
    private val sendRemoteHabitUseCase: SendRemoteHabitUseCase,
    private val uid: String
) :
    ViewModel() {
    private val saveStatus = MutableLiveData<Boolean>()
    val onSaveClickStatus: LiveData<Boolean> = saveStatus

    var habit = runBlocking {
        withContext(Dispatchers.IO) {
            loadHabitUseCase.loadHabit(uid)
        }
    }

    var name = habit?.title ?: String()
    var description = habit?.description ?: String()
    var priority = habit?.priority ?: Priority.Low
    var type = habit?.type ?: Type.Good
    var executionNumber = habit?.count ?: 0
    var executionFrequency = habit?.frequency ?: 0

    fun onSaveClick() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var isComplete = false
                var tries = 0
                while (!isComplete && tries < 5) {
                    try {
                        habit?.let { h ->
                            h.title = name
                            h.description = description
                            h.priority = priority
                            h.type = type
                            h.count = executionNumber
                            h.frequency = executionFrequency
                            sendRemoteHabitUseCase.sendHabit(h)
                            updateHabitUseCase.updateHabit(h)
                        } ?: run {
                            val newHabit = Habit(
                                title = name,
                                description = description,
                                priority = priority,
                                type = type,
                                count = executionNumber,
                                frequency = executionFrequency
                            )
                            val uid = sendRemoteHabitUseCase.sendHabit(newHabit)
                            uid?.let {
                                newHabit.uid = it
                                saveHabitUseCase.saveHabit(newHabit)
                            } ?: {
                                throw NullPointerException()
                            }
                        }
                        isComplete = true
                        saveStatus.postValue(true)
                    } catch (e: Exception) {
                        tries++
                    }
                }
                if (!isComplete)
                    saveStatus.postValue(false)
            }
        }
    }
}

class HabitViewModelFactory(
    private val loadHabitUseCase: LoadHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val saveHabitUseCase: SaveHabitUseCase,
    private val sendRemoteHabitUseCase: SendRemoteHabitUseCase,
    private val uid: String
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (uid.isNotEmpty())
            return HabitViewModel(
                loadHabitUseCase,
                updateHabitUseCase,
                saveHabitUseCase,
                sendRemoteHabitUseCase,
                uid
            ) as T
        return HabitViewModel(
            loadHabitUseCase,
            updateHabitUseCase,
            saveHabitUseCase,
            sendRemoteHabitUseCase,
            String()
        ) as T
    }
}