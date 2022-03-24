package com.example.dtandroid.ui

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dtandroid.data.Type

class HabitTypePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return Type.values().size
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("TAG", "$position")
        return HabitsListFragment.newInstance(Type.values()[position])
    }
}