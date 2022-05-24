package com.example.dtandroid.presentation.ui

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.dtandroid.HabitApplication
import com.example.dtandroid.R
import com.example.dtandroid.presentation.viewmodels.HabitsViewModel
import com.example.dtandroid.presentation.viewmodels.HabitsViewModelFactory
import kotlinx.android.synthetic.main.fragment_filter_dialog.*
import javax.inject.Inject


class FilterDialogFragment : BottomSheetDialogFragment() {
    private val applicationComponent by lazy {
        (requireActivity().application as HabitApplication)
            .applicationComponent
    }

    @Inject
    lateinit var habitsViewModelFactory: HabitsViewModelFactory

    private val habitsViewModel: HabitsViewModel by viewModels {
        return@viewModels habitsViewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        applicationComponent.inject(this)
        return inflater.inflate(R.layout.fragment_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchEditText.doAfterTextChanged {
            habitsViewModel.filter { habit -> habit.title.startsWith(it.toString()) }
        }

        sortSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.sorting)
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

//        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                when (position) {
//                    0 -> habitsViewModel.sort(Sort.ByName())
//                    1 -> habitsViewModel.sort(Sort.ByPriority())
//                    2 -> habitsViewModel.sort(Sort.ByExecutionNumber())
//                }
//            }
//        }
    }
}