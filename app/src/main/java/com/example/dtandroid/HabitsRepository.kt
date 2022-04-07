package com.example.dtandroid

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dtandroid.data.Habit

object HabitsRepository : Repository<Habit> {
    private val habitsList = ArrayList<Habit>()
    private val habitsListLiveData: MutableLiveData<List<Habit>> = MutableLiveData(habitsList)

    override fun getById(id: Int): Habit? {
        return habitsList.firstOrNull { it.id == id }
    }


    override fun deleteById(id: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            habitsList.removeIf { it.id == id }
        }
    }

    fun filter(filtering: (Habit) -> Boolean) {
        habitsListLiveData.value = habitsList.filter(filtering)
    }

    fun <T : Comparable<T>> filterThenSort(filtering: (Habit) -> Boolean, sort: (Habit) -> T) {
        habitsListLiveData.value = habitsList.filter(filtering).sortedBy { sort(it) }
    }

    fun <T : Comparable<T>> sort(selector: (Habit) -> T) {
        if (habitsListLiveData.value!!.isEmpty()) return
        habitsListLiveData.value = habitsListLiveData.value?.sortedBy { selector(it) } as List<Habit>
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun update(obj: Habit) {
        val index = habitsList.indexOfFirst { it.id == obj.id }
        if (index == -1) return
        habitsList[index] = obj
        habitsListLiveData.value = habitsList
    }

    override fun add(obj: Habit) {
        habitsList.add(obj)
        habitsListLiveData.value = habitsList
    }

    override fun addAll(objects: List<Habit>) {
        habitsList.addAll(objects)
        habitsListLiveData.value = habitsList
    }


    override fun getAll(): LiveData<List<Habit>> {
        return habitsListLiveData
    }
}