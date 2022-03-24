package com.example.dtandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Priority
import com.example.dtandroid.data.Type
import kotlinx.android.synthetic.main.fragment_habit_creation.editTextExecutionFrequency
import kotlinx.android.synthetic.main.fragment_habit_creation.editTextExecutionNumber
import kotlinx.android.synthetic.main.fragment_habit_creation.editTextHabitDescription
import kotlinx.android.synthetic.main.fragment_habit_creation.editTextHabitName
import kotlinx.android.synthetic.main.fragment_habit_creation.floatingSaveButton
import kotlinx.android.synthetic.main.fragment_habit_creation.prioritySpinner


class HabitCreationFragment : Fragment() {
    private var habit: Habit? = null
    private var type: Type? = null
    private lateinit var radioButtons: ArrayList<RadioButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val args = HabitCreationFragmentArgs.fromBundle(it)
            habit = args.habit
            type = args.type
        }
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
        prePopulateData(habit)
    }

    private fun fillView() {
        floatingSaveButton.setOnClickListener { saveHabit() }
        //radioButtons = fillRadioGroup()
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Priority.values().map { priority -> priority.toString() }
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        prioritySpinner.adapter = arrayAdapter
    }

    private fun prePopulateData(habit: Habit?) {
        if (habit == null)
            return
        editTextHabitName.setText(habit.name)
        editTextHabitDescription.setText(habit.description)
        editTextExecutionNumber.setText(habit.executionNumber.toString())
        editTextExecutionFrequency.setText(habit.executionFrequency)
        //radioGroup.check(radioButtons[Type.values().indexOf(habit.type)].id)
        prioritySpinner.setSelection(Priority.values().indexOf(habit.priority))
    }

//    private fun fillRadioGroup(): ArrayList<RadioButton> {
//        val radioButtons = ArrayList<RadioButton>()
//        for (idx in Type.values().indices) {
//            val radioButton =
//                RadioButton(requireContext()).apply { text = Type.values()[idx].toString() }
//            radioGroup.addView(radioButton)
//            radioButtons.add(radioButton)
//        }
//        radioGroup.check(radioButtons.first().id)
//        return radioButtons
//    }

    private fun validateFields(): Boolean {
        if (editTextHabitName.text.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Fill in the name field",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        if (editTextExecutionNumber.text.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Fill in the execution number field",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    private fun saveHabit() {
        if (validateFields()) {
            if (habit == null)
                habit = Habit()
            habit!!.name = editTextHabitName.text.toString()
            habit!!.description = editTextHabitDescription.text.toString()
            habit!!.priority = Priority.valueOf(prioritySpinner.selectedItem.toString())
            habit!!.type = type!!
            //Type.valueOf(requireView().findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString())
            habit!!.executionNumber = editTextExecutionNumber.text.toString().toInt()
            habit!!.executionFrequency = editTextExecutionFrequency.text.toString()

            setFragmentResult("habitResult", Bundle().apply {
                putParcelable("habit", habit)
            })


            findNavController().popBackStack()
        }
    }
}