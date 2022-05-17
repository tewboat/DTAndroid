//package com.example.domain.entities
//
//import com.example.dtandroid.R
//
//sealed class Sort<T : Comparable<T>>(val selector: (Habit) -> T, val res: Int) {
//    class ByName : Sort<String>({ habit -> habit.name }, R.string.sort_by_name)
//    class ByPriority :
//        Sort<Int>({ habit -> habit.priority.intValue }, R.string.sort_by_priority)
//
//    class ByExecutionNumber :
//        Sort<Int>({ habit -> habit.executionNumber }, R.string.sort_by_execution_number)
//}