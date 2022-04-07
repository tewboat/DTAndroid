package com.example.dtandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Type
import com.example.dtandroid.viewmodels.HabitsViewModel
import kotlinx.android.synthetic.main.fragment_habits_list.habitsRecyclerView

class HabitsListFragment : Fragment(), OnItemClick {
    private lateinit var habitsListAdapter: HabitsListAdapter
    private val habitsViewModel: HabitsViewModel by activityViewModels()
    private var type: Type? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = arguments?.getSerializable(TYPE_BUNDLE_KEY) as Type

        habitsListAdapter = HabitsListAdapter(habitsViewModel.getHabits{it.type == type}, this)
        habitsRecyclerView.adapter = habitsListAdapter

        habitsViewModel.getLiveData().observe(viewLifecycleOwner) { habits ->
            habitsListAdapter.setHabitsList(habits.filter { it.type == type })
            habitsListAdapter.notifyDataSetChanged()
        }
    }


    override fun onClick(habit: Habit, position: Int) {
        val action =
            ViewPagerFragmentDirections.actionViewPagerFragmentToHabitCreationFragment(habit.id)
        findNavController().navigate(action)
    }

    companion object {
        private const val TYPE_BUNDLE_KEY = "TypeBundleKey"
        fun newInstance(type: Type): HabitsListFragment {
            val args = Bundle()
            args.putSerializable(TYPE_BUNDLE_KEY, type)
            val fragment = HabitsListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}