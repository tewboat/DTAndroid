package com.example.dtandroid.viewmodels

import android.content.Context
import androidx.lifecycle.*

import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.HabitsDatabase
import com.example.dtandroid.data.Priority
import com.example.dtandroid.data.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HabitViewModel(context: Context, val id: Int) : ViewModel() {
    private val saveStatus = MutableLiveData<Boolean>()
    private val dao = HabitsDatabase.getDatabase(context).habitDao()
    val onSaveClickStatus: LiveData<Boolean> = saveStatus

    val habit = runBlocking {
        withContext(Dispatchers.IO) {
            dao.getHabitById(id)
        }
    }


    var name = habit?.name ?: String()
    var description = habit?.description ?: String()
    var priority = habit?.priority ?: Priority.Low
    var type = habit?.type ?: Type.Good
    var executionNumber = habit?.executionNumber ?: 0
    var executionFrequency = habit?.executionFrequency ?: String()

    fun onSaveClick() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    habit?.let { h ->
                        h.name = name
                        h.description = description
                        h.priority = priority
                        h.type = type
                        h.executionNumber = executionNumber
                        h.executionFrequency = executionFrequency
                        dao.update(h)
                    } ?: run {
                        dao.insertAll(
                            Habit(
                                name = name,
                                description = description,
                                priority = priority,
                                type = type,
                                executionNumber = executionNumber,
                                executionFrequency = executionFrequency
                            )
                        )
                    }
                    saveStatus.postValue(true)
                } catch (e: Exception) {
                    saveStatus.postValue(false)
                }
            }
        }
    }
}

class HabitViewModelFactory(private val context: Context, private val habitId: Int?) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (habitId != null)
            return HabitViewModel(context, habitId) as T
        return HabitViewModel(context, -1) as T
    }
}