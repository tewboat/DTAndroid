package com.example.dtandroid.presentation.ui

import junit.framework.TestCase
import org.junit.runner.RunWith
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import com.example.dtandroid.R


@RunWith(AndroidJUnit4::class)
internal class HabitsListFragmentTest: TestCase(){

    private lateinit var scenario: FragmentScenario<HabitsListFragment>

    @Before
    fun setup(){
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_DTAndroid)
        scenario.moveToState(Lifecycle.State.STARTED)
    }


}