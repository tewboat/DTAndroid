package com.example.dtandroid.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dtandroid.R
import com.example.domain.entities.Type
import com.example.dtandroid.presentation.ui.adapters.HabitTypePagerAdapter
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