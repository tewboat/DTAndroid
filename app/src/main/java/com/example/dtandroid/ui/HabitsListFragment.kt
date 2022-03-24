package com.example.dtandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Type
import kotlinx.android.synthetic.main.fragment_habits_list.floatingAddButton
import kotlinx.android.synthetic.main.fragment_habits_list.habitsRecyclerView

class HabitsListFragment : Fragment(), OnItemClick {
    private lateinit var habitsListAdapter: HabitsListAdapter
    private lateinit var habitsList: ArrayList<Habit>
    private lateinit var habitType: Type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            getString(HABIT_TYPE_KEY)?.let {
                habitType = Type.valueOf(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        floatingAddButton.setOnClickListener {
            val action = ViewPagerFragmentDirections.actionViewPagerFragment4ToHabitCreationFragment(null, habitType)
            findNavController().navigate(action)
        }

        setFragmentResultListener("habitResult") { key, bundle ->
            val habit = bundle.getParcelable<Habit>("habit")
            if (habit != null) {
                habitsListAdapter.addItem(habit)
            }
        }

        if (!this::habitsList.isInitialized)
            habitsList = ArrayList()
        habitsListAdapter = HabitsListAdapter(habitsList, this)
        habitsRecyclerView.adapter = habitsListAdapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("habitsList", habitsList)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState == null || savedInstanceState.isEmpty)
            return
        savedInstanceState.getParcelableArrayList<Habit>("habitsList")?.let {
            habitsList = it
        }
    }

    override fun onClick(habit: Habit, position: Int) {
        val action =
            HabitsListFragmentDirections.actionHabitsListFragmentToHabitCreationFragment(habit, habit.type)
        findNavController().navigate(action)
    }

    companion object {
        private const val HABIT_TYPE_KEY = "habitType"
        fun newInstance(type: Type) : Fragment {
            return HabitsListFragment().apply {
                arguments = Bundle().apply {
                    putString(HABIT_TYPE_KEY, type.toString())
                }
            }
        }
    }
}