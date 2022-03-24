package com.example.dtandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Type
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
            TabLayoutMediator(habitsPagerTabLayout, habitTypesViewPager){ tab, position ->
                tab.text = Type.values()[position].toString()
            }.attach()
        }

        setFragmentResultListener("habitResult") { key, bundle ->
            childFragmentManager.setFragmentResult("habitResult", bundle)
        }
    }
}