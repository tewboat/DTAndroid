package com.example.domain.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit

data class HabitWithDoneDates(
    @Embedded
    val habit: Habit,
    @Relation(parentColumn = "uid", entityColumn = "habitUid")
    val doneDates: List<DoneDate>
)