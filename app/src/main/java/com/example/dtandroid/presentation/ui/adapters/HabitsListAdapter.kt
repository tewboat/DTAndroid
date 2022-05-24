package com.example.dtandroid.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dtandroid.R
import com.example.domain.entities.Habit
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.dtandroid.utils.HabitsDiffCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.habits_list_item.view.*

class HabitsListAdapter(
    private var habits: MutableList<HabitWithDoneDates>,
    private val onItemClick: OnItemClick
) :
    RecyclerView.Adapter<HabitsListAdapter.HabitsViewHolder>() {

    fun updateHabitsListItems(habits: List<HabitWithDoneDates>){
        val diffCallback = HabitsDiffCallback(this.habits, habits)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.habits.clear()
        this.habits.addAll(habits)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitsViewHolder(inflater.inflate(R.layout.habits_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount(): Int = habits.size

    inner class HabitsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(habitWithDoneDate: HabitWithDoneDates) {
            containerView.habitName.text = habitWithDoneDate.habit.title
            containerView.habitDescription.text = habitWithDoneDate.habit.description
            containerView.setOnClickListener {
                onItemClick.onItemClick(
                    habitWithDoneDate,
                    adapterPosition
                )
            }
            containerView.doneButton.setOnClickListener {
                onItemClick.onDoneButtonClick(habitWithDoneDate)
            }
        }
    }
}

interface OnItemClick {
    fun onItemClick(habitWithDoneDates: HabitWithDoneDates, position: Int)
    fun onDoneButtonClick(habitWithDoneDates: HabitWithDoneDates)
}