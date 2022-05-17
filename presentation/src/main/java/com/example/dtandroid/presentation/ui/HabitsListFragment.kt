package com.example.dtandroid.presentation.ui

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.entities.DoneDate
import com.example.dtandroid.HabitApplication
import com.example.dtandroid.R
import com.example.domain.entities.Type
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.dtandroid.presentation.ui.adapters.HabitsListAdapter
import com.example.dtandroid.presentation.ui.adapters.OnItemClick
import com.example.dtandroid.presentation.viewmodels.HabitsViewModel
import com.example.dtandroid.presentation.viewmodels.HabitsViewModelFactory
import kotlinx.android.synthetic.main.fragment_habits_list.habitsRecyclerView
import kotlinx.android.synthetic.main.habits_list_item.*
import java.util.*

class HabitsListFragment(private val activity: Activity) : Fragment(), OnItemClick {
    private val applicationComponent = (activity.application as HabitApplication)
        .applicationComponent
    private val habitsViewModel: HabitsViewModel by viewModels {
        return@viewModels HabitsViewModelFactory(
            applicationComponent.getLoadAllHabitsWithDoneDatesUseCase(),
            applicationComponent.getLoadRemoteHabitsUseCase(),
            applicationComponent.getDoneRemoteHabitUseCase(),
            applicationComponent.getDoneHabitUseCase(),
            applicationComponent.getSaveHabitsWithDoneDatesUseCase()
        )
    }

    private lateinit var habitsListAdapter: HabitsListAdapter
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
        habitsListAdapter =
            HabitsListAdapter(
                habitsViewModel.getHabits { it.habit.type == type },
                this
            )
        habitsRecyclerView.adapter = habitsListAdapter

        habitsViewModel.getLiveData().observe(viewLifecycleOwner) { habits ->
            habitsListAdapter.setHabitsList(habits.filter { it.habit.type == type })
            habitsListAdapter.notifyDataSetChanged()
        }

        habitsViewModel.tryLoadData()
    }


    override fun onItemClick(habitWithDoneDates: HabitWithDoneDates, position: Int) {
        val action =
            ViewPagerFragmentDirections.actionViewPagerFragmentToHabitCreationFragment(
                habitWithDoneDates.habit.uid
            )
        findNavController().navigate(action)
    }

    override fun onDoneButtonClick(habitWithDoneDates: HabitWithDoneDates) {
        val textRes = if(habitWithDoneDates.doneDates.size >= habitWithDoneDates.habit.count - 1) {
            if (habitWithDoneDates.habit.type == Type.Good)
                resources.getText(R.string.goodHabitDoneMessage)
            else
                resources.getText(R.string.badHabitDoneMessage)
        }
        else{
            if (habitWithDoneDates.habit.type == Type.Good)
                "${resources.getText(R.string.goodHabitDoneMoreMessage)}: ${habitWithDoneDates.habit.count - habitWithDoneDates.doneDates.size}"
            else
                "${resources.getText(R.string.badHabitDoneMoreMessage)}: ${habitWithDoneDates.habit.count - habitWithDoneDates.doneDates.size}"
        }

        Toast.makeText(requireContext(), textRes, Toast.LENGTH_SHORT).show()
        habitsViewModel.doHabit(habitWithDoneDates.habit)
    }

    companion object {
        private const val TYPE_BUNDLE_KEY = "TypeBundleKey"
        fun newInstance(activity: Activity, type: Type): HabitsListFragment {
            val args = Bundle()
            args.putSerializable(TYPE_BUNDLE_KEY, type)
            val fragment = HabitsListFragment(activity)
            fragment.arguments = args
            return fragment
        }
    }
}