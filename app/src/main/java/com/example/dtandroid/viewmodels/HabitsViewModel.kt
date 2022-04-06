package com.example.dtandroid.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dtandroid.HabitsRepository
import com.example.dtandroid.data.Habit

class HabitsViewModel : ViewModel() {
    val habitsList by lazy {
        HabitsRepository.getAll()
    }

    fun add(habit: Habit) {
        HabitsRepository.add(habit)
    }

    fun delete(habit: Habit) {
        HabitsRepository.deleteById(habit.id)
    }
}


