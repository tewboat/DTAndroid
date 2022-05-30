package com.example.dtandroid.presentation.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.dtandroid.di.ApplicationComponent
import com.example.dtandroid.HabitApplication
import com.example.dtandroid.R
import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.dtandroid.presentation.viewmodels.HabitViewModel
import com.example.dtandroid.presentation.viewmodels.HabitViewModelFactory
import kotlinx.android.synthetic.main.fragment_habit_creation.*


class HabitCreationFragment : Fragment() {
    private val applicationComponent by lazy {
        (requireActivity().application as HabitApplication)
            .applicationComponent
    }

    private var habitViewModel: HabitViewModel? = null

    private lateinit var radioButtons: ArrayList<RadioButton>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habit_creation, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        habitViewModel = getHabitViewModel(applicationComponent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillView()

        habitViewModel?.let { viewModel ->
            prePopulateData(viewModel.habit)

            viewModel.onSaveClickStatus.observe(viewLifecycleOwner) { saved ->
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
    }

    private fun getHabitViewModel(applicationComponent: ApplicationComponent): HabitViewModel {
        return ViewModelProvider(
            this, HabitViewModelFactory(
                applicationComponent.getLoadHabitUseCase(),
                applicationComponent.getUpdateHabitUseCase(),
                applicationComponent.getSaveHabitUseCase(),
                applicationComponent.getSendRemoteHabitUseCase(),
                HabitCreationFragmentArgs.fromBundle(arguments ?: Bundle()).id ?: String()
            )
        ).get(HabitViewModel::class.java)
    }


    private fun fillView() {
        floatingSaveButton.setOnClickListener { saveHabit() }
        radioButtons = fillRadioGroup()
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Priority.values().map { priority -> priority.res }
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        prioritySpinner.adapter = arrayAdapter
    }

    private fun prePopulateData(habit: Habit?) {
        habit?.apply {
            editTextHabitName.setText(title)
            editTextHabitDescription.setText(description)
            editTextExecutionNumber.setText(count.toString())
            editTextExecutionFrequency.setText(frequency.toString())
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
            habitViewModel?.apply {
                name = editTextHabitName.text.toString()
                description = editTextHabitDescription.text.toString()
                priority = Priority.valueOf(prioritySpinner.selectedItem.toString())
                type =
                    Type.valueOf(requireView().findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString())
                executionNumber = editTextExecutionNumber.text.toString().toInt()
                executionFrequency = editTextExecutionFrequency.text.toString().toInt()
                onSaveClick()
            }
        }
    }
}