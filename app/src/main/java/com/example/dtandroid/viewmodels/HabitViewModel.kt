package com.example.dtandroid.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.dtandroid.data.*

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HabitViewModel(context: Context, val id: String) : ViewModel() {
    private val saveStatus = MutableLiveData<Boolean>()
    private val dao = HabitsDatabase.getDatabase(context).habitDao()
    val onSaveClickStatus: LiveData<Boolean> = saveStatus

    var habit = runBlocking {
        withContext(Dispatchers.IO) {
            dao.getHabitById(id)
        }
    }

    var name = habit?.name ?: String()
    var description = habit?.description ?: String()
    var priority = habit?.priority ?: Priority.Low
    var type = habit?.type ?: Type.Good
    var executionNumber = habit?.executionNumber ?: 0
    var executionFrequency = habit?.executionFrequency ?: 0

    fun onSaveClick() {
        runBlocking {
            withContext(Dispatchers.IO) {
                var isComplete = false
                var tries = 0
                while (!isComplete && tries < 5) {
                    try {
                        habit?.let { h ->
                            h.name = name
                            h.description = description
                            h.priority = priority
                            h.type = type
                            h.executionNumber = executionNumber
                            h.executionFrequency = executionFrequency
                            RemoteRepository.putHabit(h)
                            dao.update(h)
                        } ?: run {
                            val newHabit = Habit(
                                name = name,
                                description = description,
                                priority = priority,
                                type = type,
                                executionNumber = executionNumber,
                                executionFrequency = executionFrequency
                            )
                            val uid = RemoteRepository.putHabit(newHabit)
                            newHabit.uid = uid.uid!!
                            dao.insert(newHabit)
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

class HabitViewModelFactory(private val context: Context, private val habitId: String) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (habitId.isNotEmpty())
            return HabitViewModel(context, habitId) as T
        return HabitViewModel(context, String()) as T
    }
}