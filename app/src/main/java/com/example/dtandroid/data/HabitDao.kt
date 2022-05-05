package com.example.dtandroid.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface HabitDao {
    @Query("select * from habits")
    fun getAll(): LiveData<List<Habit>>

    @Query("select * from habits where uid in (:habitsIds)")
    fun getAllByIds(habitsIds: ArrayList<String>): LiveData<List<Habit>>

    @Query("select * from habits where uid = (:habitId)")
    fun getHabitById(habitId: String): Habit?

//    @Query("select * from habits where id = (:habitId)")
//    fun getHabitById(habitId: Int): LiveData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(habits: List<Habit>)

    @Delete
    fun delete(habits: Habit)

    @Update
    fun update(vararg habit: Habit)
}