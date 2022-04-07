package com.example.dtandroid.data

import android.os.Parcelable
import com.example.dtandroid.R
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class Habit(
    val id: Int = Random.nextInt(0, 100000),
    var name: String = String(),
    var description: String = String(),
    var priority: Priority = Priority.Low,
    var type: Type = Type.Good,
    var executionNumber: Int = 0,
    var executionFrequency: String = String()
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

class Sort<T : Comparable<T>>(val selector: (Habit) -> T, val res: Int){
    companion object {
        val byName = Sort({ habit -> habit.name }, R.string.sort_by_name)
        val byPriority = Sort({ habit -> habit.priority.intValue }, R.string.sort_by_priority)
        val byExecutionNumber = Sort({ habit -> habit.executionNumber }, R.string.sort_by_execution_number)
    }
}