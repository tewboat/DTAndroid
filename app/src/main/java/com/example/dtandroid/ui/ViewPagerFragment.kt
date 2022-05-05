package com.example.dtandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Sort
import com.example.dtandroid.data.Type
import com.example.dtandroid.viewmodels.HabitViewModelFactory
import com.example.dtandroid.viewmodels.HabitsViewModel
import com.example.dtandroid.viewmodels.HabitsViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_pager.*

class ViewPagerFragment : Fragment() {

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

//        FilterDialogFragment().show(childFragmentManager, "bottomSheet")
//        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet).apply {
//            peekHeight = 100
//            state = BottomSheetBehavior.STATE_COLLAPSED
//        }
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        floatingAddButton.setOnClickListener {
            val action =
                ViewPagerFragmentDirections.actionViewPagerFragmentToHabitCreationFragment(String())
            findNavController().navigate(action)
        }
    }
}