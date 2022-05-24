package com.example.dtandroid.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entities.relations.HabitWithDoneDates

class HabitsDiffCallback(
    private val oldHabitsList: List<HabitWithDoneDates>,
    private val newHabitsList: List<HabitWithDoneDates>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldHabitsList.size
    }

    override fun getNewListSize(): Int {
        return newHabitsList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldHabitsList[oldItemPosition].habit.uid == newHabitsList[newItemPosition].habit.uid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHabit = oldHabitsList[oldItemPosition]
        val newHabit = newHabitsList[newItemPosition]

        return oldHabit == newHabit
    }
}