package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit

@Database(entities = [Habit::class, DoneDate::class], version = 11)
abstract class HabitsDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}