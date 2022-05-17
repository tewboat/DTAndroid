package com.example.domain.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index

@Entity(
    tableName = "done_dates",
    foreignKeys = [ForeignKey(
        parentColumns = ["uid"],
        childColumns = ["habitUid"],
        entity = Habit::class,
        onDelete = CASCADE
    )],
    indices = [Index("habitUid")],
    primaryKeys = ["habitUid", "date"]
)
data class DoneDate(
    val habitUid: String,
    val date: Long,
)