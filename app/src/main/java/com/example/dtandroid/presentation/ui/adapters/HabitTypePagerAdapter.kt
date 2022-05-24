package com.example.dtandroid.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.domain.entities.Type
import com.example.dtandroid.presentation.ui.HabitsListFragment

class HabitTypePagerAdapter(private val activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return Type.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return HabitsListFragment.newInstance(activity, Type.values()[position])
    }
}