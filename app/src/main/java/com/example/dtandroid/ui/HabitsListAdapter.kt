package com.example.dtandroid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.habits_list_item.view.*

class HabitsListAdapter(private var habits: ArrayList<Habit>, private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<HabitsListAdapter.HabitsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitsViewHolder(inflater.inflate(R.layout.habits_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount(): Int = habits.size

    fun addItem(habit: Habit){
        habits.add(habit)
        notifyItemInserted(habits.size - 1)
    }

    fun setHabitsList(habitsList: ArrayList<Habit>){
        habits = habitsList
        notifyDataSetChanged()
    }

    fun updateItem(habit: Habit, position: Int){
        habits[position] = habit
        notifyItemChanged(position)
    }

    inner class HabitsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(habit: Habit) {
            containerView.habitName.text = habit.name
            containerView.habitDescription.text = habit.description
            containerView.setOnClickListener { onItemClick.onClick(habit, adapterPosition) }
        }
    }
}

interface OnItemClick{
    fun onClick(habit: Habit, position: Int)
}