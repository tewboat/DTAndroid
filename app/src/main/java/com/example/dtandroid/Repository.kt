package com.example.dtandroid

import androidx.lifecycle.LiveData
import com.example.dtandroid.data.Habit


interface Repository<T> {
    fun getById(id: Long): Habit?
    fun getAll(): LiveData<ArrayList<T>>
    fun deleteById(id: Long)
    fun deleteAll()
    fun update(obj: T)
    fun add(obj: T)
    fun addAll(objects: List<T>)
}
