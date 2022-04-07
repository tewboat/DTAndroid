package com.example.dtandroid.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dtandroid.HabitsRepository
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Sort

class HabitsViewModel : ViewModel() {
    private val habitsList by lazy {
        HabitsRepository.getAll()
    }

    fun getLiveData(): LiveData<List<Habit>> {
        return habitsList
    }

    fun getHabits(): List<Habit> {
        return habitsList.value ?: ArrayList()
    }

    fun getHabits(filtering: (Habit) -> Boolean): List<Habit> {
        return habitsList.value?.filter(filtering) ?: ArrayList()
    }

    fun filter(filtering: (Habit) -> Boolean) {
        HabitsRepository.filter(filtering)
    }

    fun <T : Comparable<T>> filterThenSort(filtering: (Habit) -> Boolean, selector: (Habit) -> T) {
        HabitsRepository.filterThenSort(filtering, selector)
    }

    fun <T : Comparable<T>> sort(selector: (Habit) -> T) {
        HabitsRepository.sort(selector)
    }

    fun add(habit: Habit) {
        HabitsRepository.add(habit)
    }

    fun delete(habit: Habit) {
        HabitsRepository.deleteById(habit.id)
    }
}


