package com.example.dtandroid.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dtandroid.R
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "habits")
@Parcelize
data class Habit(
    var name: String = String(),
    var description: String = String(),
    var priority: Priority = Priority.Low,
    var type: Type = Type.Good,
    var executionNumber: Int = 0,
    var executionFrequency: String = String(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable

enum class Priority(val intValue: Int, val res: Int) {
    Low(2, R.string.priority_low),
    Medium(1, R.string.priority_medium),
    High(0, R.string.priority_high)
}

enum class Type(val intValue: Int) {
    Good(0),
    Bad(1)
}

sealed class Sort<T : Comparable<T>>(val selector: (Habit) -> T, val res: Int){
        class ByName : Sort<String>({ habit -> habit.name }, R.string.sort_by_name)
        class ByPriority : Sort<Int>({ habit -> habit.priority.intValue }, R.string.sort_by_priority)
        class ByExecutionNumber : Sort<Int>({ habit -> habit.executionNumber }, R.string.sort_by_execution_number)
}