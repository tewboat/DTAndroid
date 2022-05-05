package com.example.dtandroid.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.HabitsDatabase
import com.example.dtandroid.data.RemoteRepository
import com.example.dtandroid.data.Sort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class HabitsViewModel(context: Context) : ViewModel() {
    private val dao = HabitsDatabase.getDatabase(context).habitDao()
    private val habitsList = runBlocking {
        withContext(Dispatchers.IO) {
            dao.getAll()
        }
    }

    fun getLiveData(): LiveData<List<Habit>> {
        return habitsList
    }

    fun getHabits(filtering: (Habit) -> Boolean): List<Habit> {
        return habitsList.value?.filter(filtering) ?: ArrayList()
    }

    fun filter(filtering: (Habit) -> Boolean) {
        //HabitsRepository.filter(filtering)
    }


    fun <T : Comparable<T>> sort(sort: Sort<T>) {
        //HabitsRepository.sort(selector)
    }

    fun addAll(vararg habit: Habit) {
        dao.insert(*habit)
    }

    fun delete(habit: Habit) {
        //HabitsRepository.deleteById(habit.id)
    }

    fun tryLoadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    RemoteRepository.getListHabits().let {
                        dao.insert(it)
                    }
                } catch (e: Exception){
                    Log.d("HabitsViewModel", e.message.toString())
                }
            }
        }
    }
}

class HabitsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HabitsViewModel(context) as T
    }
}

