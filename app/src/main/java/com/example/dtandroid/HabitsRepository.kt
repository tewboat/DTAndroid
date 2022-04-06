package com.example.dtandroid

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dtandroid.data.Habit

object HabitsRepository : Repository<Habit> {
    private val habitsList = MutableLiveData<ArrayList<Habit>>(ArrayList())

    override fun getById(id: Long): Habit? {
        return habitsList.value!!.firstOrNull() { it.id == id }
    }


    override fun deleteById(id: Long) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            habitsList.value!!.removeIf { it.id == id }
        }
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun update(obj: Habit) {
        val index = habitsList.value!!.indexOfFirst { it.id == obj.id }
        habitsList.value!![index] = obj
    }

    override fun add(obj: Habit) {
        habitsList.value!!.add(obj)
    }

    override fun addAll(objects: List<Habit>) {
        habitsList.value!!.addAll(objects)
    }


    override fun getAll(): LiveData<ArrayList<Habit>> {
        return habitsList
    }
}