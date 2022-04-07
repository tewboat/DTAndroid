package com.example.dtandroid

import androidx.lifecycle.LiveData
import com.example.dtandroid.data.Habit


interface Repository<T> {
    fun getById(id: Int): Habit?
    fun getAll(): LiveData<List<Habit>>
    fun deleteById(id: Int)
    fun deleteAll()
    fun update(obj: T)
    fun add(obj: T)
    fun addAll(objects: List<T>)
}
