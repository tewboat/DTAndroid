package com.example.dtandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Sort
import com.example.dtandroid.data.Type
import com.example.dtandroid.viewmodels.HabitsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_pager.*

class ViewPagerFragment : Fragment() {

    private val habitsViewModel: HabitsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            habitTypesViewPager.adapter = HabitTypePagerAdapter(it)
            TabLayoutMediator(habitsPagerTabLayout, habitTypesViewPager) { tab, position ->
                tab.text = Type.values()[position].toString()
            }.attach()
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet).apply {
            peekHeight = 100
            state = BottomSheetBehavior.STATE_COLLAPSED

        }

        floatingAddButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            val action =
                ViewPagerFragmentDirections.actionViewPagerFragmentToHabitCreationFragment(-1)
            findNavController().navigate(action)
        }

        searchEditText.doAfterTextChanged {
            habitsViewModel.filter { habit -> habit.name.startsWith(it.toString()) }
        }

        sortSpinner.adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, sorting.map { resources.getString(it.res) }).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        sortSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position){
                    0 -> habitsViewModel.sort(Sort.byName.selector)
                    1 -> habitsViewModel.sort(Sort.byPriority.selector)
                    2 -> habitsViewModel.sort(Sort.byExecutionNumber.selector)
                }
            }
        }
    }

    companion object {
        private val sorting = arrayListOf(
            Sort.byName,
            Sort.byPriority,
            Sort.byExecutionNumber
        )
    }
}