package com.example.dtandroid.presentation.viewmodels

import androidx.lifecycle.*
import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.usecases.local.DoneHabitUseCase
import com.example.domain.usecases.local.LoadAllHabitsWithDoneDatesUseCase
import com.example.domain.usecases.local.SaveHabitsUseCase
import com.example.domain.usecases.local.SaveHabitsWithDoneDatesUseCase
import com.example.domain.usecases.remote.DoneRemoteHabitUseCase
import com.example.domain.usecases.remote.LoadRemoteHabitsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class HabitsViewModel(
    private val getAllHabitsWithDoneDatesUseCase: LoadAllHabitsWithDoneDatesUseCase,
    private val loadRemoteHabitsUseCase: LoadRemoteHabitsUseCase,
    private val doneRemoteHabitUseCase: DoneRemoteHabitUseCase,
    private val doneHabitUseCase: DoneHabitUseCase,
    private val saveHabitsWithDoneDatesUseCase: SaveHabitsWithDoneDatesUseCase
) : ViewModel() {
    private val habitsList: LiveData<List<HabitWithDoneDates>> = runBlocking {
        withContext(Dispatchers.IO) {
            getAllHabitsWithDoneDatesUseCase.loadHabits().asLiveData()
        }
    }

    fun getLiveData(): LiveData<List<HabitWithDoneDates>> {
        return habitsList
    }

    fun getHabits(filtering: (HabitWithDoneDates) -> Boolean): List<HabitWithDoneDates> {
        return habitsList.value?.filter(filtering) ?: ArrayList()
    }

    fun filter(filtering: (Habit) -> Boolean) {
        //HabitsRepository.filter(filtering)
    }

    fun doHabit(habit: Habit) {
        viewModelScope.launch {
            val milliseconds = System.currentTimeMillis()
            doneHabitUseCase.doHabit(habit, milliseconds)
            doneRemoteHabitUseCase.doHabit(habit, milliseconds)
        }
    }


    fun delete(habit: Habit) {
        //HabitsRepository.deleteById(habit.id)
    }

    fun tryLoadData() {
        viewModelScope.launch {
            loadRemoteHabitsUseCase.loadHabits()?.let {
                saveHabitsWithDoneDatesUseCase.saveHabits(it)
            }
        }
    }
}


class HabitsViewModelFactory(
    private val loadAllHabitsUseCase: LoadAllHabitsWithDoneDatesUseCase,
    private val loadRemoteHabitsUseCase: LoadRemoteHabitsUseCase,
    private val doneRemoteHabitUseCase: DoneRemoteHabitUseCase,
    private val doneHabitUseCase: DoneHabitUseCase,
    private val saveHabitsWithDoneDatesUseCase: SaveHabitsWithDoneDatesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HabitsViewModel(
            loadAllHabitsUseCase,
            loadRemoteHabitsUseCase,
            doneRemoteHabitUseCase,
            doneHabitUseCase,
            saveHabitsWithDoneDatesUseCase
        ) as T
    }
}

