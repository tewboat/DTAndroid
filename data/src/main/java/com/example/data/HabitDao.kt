package com.example.data

import androidx.room.*
import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.relations.HabitWithDoneDates
import kotlinx.coroutines.flow.Flow


@Dao
abstract class HabitDao {
    @Query("select * from habits")
    abstract fun getAllHabits(): Flow<List<Habit>>

    @Query("select * from habits where uid = :habitUid")
    abstract fun getHabitByUid(habitUid: String): Habit?

    @Query("delete from habits where uid = :habitUid")
    abstract fun deleteHabitByUid(habitUid: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllHabits(vararg habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllHabits(habits: List<Habit>)

    @Update
    abstract fun updateHabit(vararg habit: Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDoneDate(doneDates: DoneDate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDoneDate(doneDates: List<DoneDate>)

    @Query("select * from habits")
    abstract fun getAllHabitsWithDoneDates(): Flow<List<HabitWithDoneDates>>

    @Query("select * from habits where uid = :habitUid")
    abstract fun getHabitWithDoneDatesByUid(habitUid: String): HabitWithDoneDates?

    fun insertAllHabitsWithDoneDates(vararg habits: HabitWithDoneDates){
        insertAllHabitsWithDoneDates(habits.toList())
    }

    fun insertAllHabitsWithDoneDates(habits: List<HabitWithDoneDates>){
        for (habit in habits){
            insertAllHabits(habit.habit)
            insertDoneDate(habit.doneDates)
        }
    }

}