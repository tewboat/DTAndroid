package com.example.dtandroid.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Habit::class], version = 1)
abstract class HabitsDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var instance: HabitsDatabase? = null

        fun getDatabase(context: Context): HabitsDatabase {
            val tempInstance = instance
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitsDatabase::class.java,
                    "habits_db"
                )
                    .allowMainThreadQueries()
                    .build()
                instance = newInstance
                return newInstance
            }
        }
    }
}