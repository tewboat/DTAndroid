package com.example.dtandroid.viewmodels

import androidx.annotation.Nullable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dtandroid.HabitsRepository
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Priority
import com.example.dtandroid.data.Type

class HabitViewModel(private val id: Long) : ViewModel() {
    val habit = HabitsRepository.getById(id)

    var name = habit?.name ?: String()
    var description = habit?.description ?: String()
    var priority = habit?.priority ?: Priority.Low
    var type = habit?.type ?: Type.Good
    var executionNumber = habit?.executionNumber ?: 0
    var executionFrequency = habit?.executionFrequency ?: String()

    fun onSaveClick() {
        if (habit == null) {
            val newHabit =
                Habit(name, description, priority, type, executionNumber, executionFrequency)
            HabitsRepository.add(newHabit)
        }
        else {
            val updatedHabit = habit.copy(
                name = name,
                description = description,
                priority = priority,
                type = type,
                executionNumber = executionNumber,
                executionFrequency = executionFrequency
            )
            HabitsRepository.update(updatedHabit)
        }
    }
}

class HabitViewModelFactory(private val habitId: Long?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (habitId != null)
            return HabitViewModel(habitId) as T
        return HabitViewModel(-1) as T
    }
}