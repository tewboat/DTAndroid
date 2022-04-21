package com.example.dtandroid.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.HabitsDatabase
import com.example.dtandroid.data.Sort


class HabitsViewModel(context: Context) : ViewModel() {
    private val dao = HabitsDatabase.getDatabase(context).habitDao()
    private val habitsList = dao.getAll()

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
        //HabitsRepository.filter(filtering)
    }

    fun <T : Comparable<T>> filterThenSort(filtering: (Habit) -> Boolean, selector: (Habit) -> T) {
        //HabitsRepository.filterThenSort(filtering, selector)
    }

    fun <T : Comparable<T>> sort(sort: Sort<T>) {
        //HabitsRepository.sort(selector)
    }

    fun add(habit: Habit) {
        //HabitsRepository.add(habit)
    }

    fun delete(habit: Habit) {
        //HabitsRepository.deleteById(habit.id)
    }
}

class HabitsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HabitsViewModel(context) as T
    }
}

