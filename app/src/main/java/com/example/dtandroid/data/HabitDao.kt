package com.example.dtandroid.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HabitDao {
    @Query("select * from habits")
    fun getAll(): LiveData<List<Habit>>

    @Query("select * from habits where id in (:habitsIds)")
    fun getAllByIds(habitsIds: IntArray): LiveData<List<Habit>>

    @Query("select * from habits where id = (:habitId)")
    fun getHabitById(habitId: Int): Habit

    @Insert
    fun insertAll(vararg habit: Habit)

    @Delete
    fun delete(habits: Habit)

    @Update
    fun update(vararg habit: Habit)
}