package com.example.dtandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Priority
import com.example.dtandroid.data.Type
import com.example.dtandroid.viewmodels.HabitViewModel
import com.example.dtandroid.viewmodels.HabitViewModelFactory
import kotlinx.android.synthetic.main.fragment_habit_creation.*
import kotlinx.coroutines.runBlocking


class HabitCreationFragment : Fragment() {
    private lateinit var radioButtons: ArrayList<RadioButton>
    private val habitViewModel: HabitViewModel by viewModels {
        arguments?.let {
            val args = HabitCreationFragmentArgs.fromBundle(it)
            return@viewModels HabitViewModelFactory(requireContext().applicationContext, args.id)
        }
        return@viewModels HabitViewModelFactory(requireContext().applicationContext, String())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_creation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillView()
        prePopulateData(habitViewModel.habit)

        habitViewModel.onSaveClickStatus.observe(viewLifecycleOwner) { saved ->
            if (saved) {
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.error_while_saving_habit_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun fillView() {
        floatingSaveButton.setOnClickListener { saveHabit() }
        radioButtons = fillRadioGroup()
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Priority.values().map { priority -> resources.getString(priority.res) }
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        prioritySpinner.adapter = arrayAdapter
    }

    private fun prePopulateData(habit: Habit?) {
        habit?.apply {
            editTextHabitName.setText(name)
            editTextHabitDescription.setText(description)
            editTextExecutionNumber.setText(executionNumber.toString())
            editTextExecutionFrequency.setText(executionFrequency.toString())
            radioGroup.check(radioButtons[Type.values().indexOf(type)].id)
            prioritySpinner.setSelection(Priority.values().indexOf(priority))
        }
    }

    private fun fillRadioGroup(): ArrayList<RadioButton> {
        val radioButtons = ArrayList<RadioButton>()
        for (idx in Type.values().indices) {
            val radioButton =
                RadioButton(requireContext()).apply { text = Type.values()[idx].toString() }
            radioGroup.addView(radioButton)
            radioButtons.add(radioButton)
        }
        radioGroup.check(radioButtons.first().id)
        return radioButtons
    }

    private fun validateFields(): Boolean {
        if (editTextHabitName.text.isEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.name_field_not_filled_message),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        if (editTextExecutionNumber.text.isEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.execution_number_field_not_filled_message),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        if (editTextExecutionFrequency.text.isEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.execution_frequency_field_not_filled_message),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        if (editTextHabitDescription.text.isEmpty()) {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.execution_frequency_field_not_filled_message),
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    private fun saveHabit() {
        if (validateFields()) {
            habitViewModel.apply {
                name = editTextHabitName.text.toString()
                description = editTextHabitDescription.text.toString()
                priority = Priority.valueOf(prioritySpinner.selectedItem.toString())
                type =
                    Type.valueOf(requireView().findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString())
                executionNumber = editTextExecutionNumber.text.toString().toInt()
                executionFrequency = editTextExecutionFrequency.text.toString().toInt()
            }

            habitViewModel.onSaveClick()
        }
    }
}