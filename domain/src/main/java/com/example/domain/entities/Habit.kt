package com.example.domain.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "habits", indices = [Index("uid")])
data class Habit(
    var title: String = String(),
    var description: String = String(),
    var priority: Priority = Priority.Low,
    var type: Type = Type.Good,
    var count: Int = 0,
    var frequency: Int = 0,
    @PrimaryKey var uid: String = String(),
    @Ignore var doneDates: List<DoneDate>? = null
)



