package com.example.dtandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Type
import com.example.dtandroid.viewmodels.HabitsViewModel
import kotlinx.android.synthetic.main.fragment_habits_list.floatingAddButton
import kotlinx.android.synthetic.main.fragment_habits_list.habitsRecyclerView

class HabitsListFragment : Fragment(), OnItemClick {
    private lateinit var habitsListAdapter: HabitsListAdapter
    private val habitsViewModel: HabitsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatingAddButton.setOnClickListener {
            val action =
                HabitsListFragmentDirections.actionHabitsListFragmentToHabitCreationFragment(-1L)
            findNavController().navigate(action)
        }

        habitsListAdapter = HabitsListAdapter(habitsViewModel.habitsList.value!!, this)
        habitsRecyclerView.adapter = habitsListAdapter

        habitsViewModel.habitsList.observe(viewLifecycleOwner) { habits ->
            habitsListAdapter.setHabitsList(habits)
            habitsListAdapter.notifyDataSetChanged()
        }
    }


    override fun onClick(habit: Habit, position: Int) {
        val action =
            HabitsListFragmentDirections.actionHabitsListFragmentToHabitCreationFragment(habit.id)
        findNavController().navigate(action)
    }
}