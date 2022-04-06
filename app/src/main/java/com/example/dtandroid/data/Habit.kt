package com.example.dtandroid.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class Habit(
    var name: String = String(),
    var description: String = String(),
    var priority: Priority = Priority.Low,
    var type: Type = Type.Good,
    var executionNumber: Int = 0,
    var executionFrequency: String = String()
) : Parcelable {
    val id: Long = Random.nextLong()
}


enum class Priority(val intValue: Int) {
    Low(0),
    Medium(1),
    High(2)
}

enum class Type(val intValue: Int) {
    Good(0),
    Bad(1)
}