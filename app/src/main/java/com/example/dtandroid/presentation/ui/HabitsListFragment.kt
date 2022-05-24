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
import com.example.dtandroid.HabitApplication
import com.example.dtandroid.R
import com.example.domain.entities.Type
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.dtandroid.presentation.ui.adapters.HabitsListAdapter
import com.example.dtandroid.presentation.ui.adapters.OnItemClick
import com.example.dtandroid.presentation.viewmodels.HabitsViewModel
import com.example.dtandroid.presentation.viewmodels.HabitsViewModelFactory
import kotlinx.android.synthetic.main.fragment_habits_list.habitsRecyclerView
import javax.inject.Inject

class HabitsListFragment : Fragment(), OnItemClick {
    private val applicationComponent by lazy {
        (requireActivity().application as HabitApplication)
        .applicationComponent
    }

    @Inject
    lateinit var habitsViewModelFactory: HabitsViewModelFactory

    private val habitsViewModel: HabitsViewModel by viewModels {
        return@viewModels habitsViewModelFactory
    }

    private lateinit var habitsListAdapter: HabitsListAdapter
    private var type: Type? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        applicationComponent.inject(this)
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type = arguments?.getSerializable(TYPE_BUNDLE_KEY) as Type
        habitsListAdapter =
            HabitsListAdapter(
                habitsViewModel.getHabits { it.habit.type == type }.toMutableList(),
                this
            )
        habitsRecyclerView.adapter = habitsListAdapter

        habitsViewModel.getLiveData().observe(viewLifecycleOwner) { habits ->
            habitsListAdapter.updateHabitsListItems(habits.filter { it.habit.type == type })
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
        val textRes = if (habitWithDoneDates.doneDates.size >= habitWithDoneDates.habit.count - 1) {
            if (habitWithDoneDates.habit.type == Type.Good)
                resources.getText(R.string.goodHabitDoneMessage)
            else
                resources.getText(R.string.badHabitDoneMessage)
        } else {
            if (habitWithDoneDates.habit.type == Type.Good)
                "${resources.getText(R.string.goodHabitDoneMoreMessage)}: ${habitWithDoneDates.habit.count - habitWithDoneDates.doneDates.size - 1}"
            else
                "${resources.getText(R.string.badHabitDoneMoreMessage)}: ${habitWithDoneDates.habit.count - habitWithDoneDates.doneDates.size - 1}"
        }

        Toast.makeText(requireContext(), textRes, Toast.LENGTH_SHORT).show()
        habitsViewModel.doHabit(habitWithDoneDates.habit)
    }

    companion object {
        private const val TYPE_BUNDLE_KEY = "TypeBundleKey"
        fun newInstance(activity: Activity, type: Type): HabitsListFragment {
            val args = Bundle()
            args.putSerializable(TYPE_BUNDLE_KEY, type)
            val fragment = HabitsListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}