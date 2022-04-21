package com.example.dtandroid.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.HabitsDatabase
import com.example.dtandroid.data.Priority
import com.example.dtandroid.data.Type

class HabitViewModel(context: Context,  val id: Int) : ViewModel() {
    private val dao = HabitsDatabase.getDatabase(context).habitDao()
    val habit = dao.getHabitById(id)

    var name = habit?.name ?: String()
    var description = habit?.description ?: String()
    var priority = habit?.priority ?: Priority.Low
    var type = habit?.type ?: Type.Good
    var executionNumber = habit?.executionNumber ?: 0
    var executionFrequency = habit?.executionFrequency ?: String()

    fun onSaveClick() {
        if (habit == null) {
            val newHabit =
                Habit(
                    name = name,
                    description = description,
                    priority = priority,
                    type = type,
                    executionNumber = executionNumber,
                    executionFrequency = executionFrequency
                )
            dao.insertAll(newHabit)
        } else {
            habit.let {
                it.name = name
                it.description = description
                it.priority = priority
                it.type = type
                it.executionNumber = executionNumber
                it.executionFrequency = executionFrequency
            }
            dao.update(habit)
        }
    }
}

class HabitViewModelFactory(private val context: Context,  private val habitId: Int?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (habitId != null)
            return HabitViewModel(context, habitId) as T
        return HabitViewModel(context,-1) as T
    }
}