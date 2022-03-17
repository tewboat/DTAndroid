package com.example.dtandroid.ui

import android.content.Intent
import kotlinx.android.synthetic.main.activity_habit_creation.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import com.example.dtandroid.data.Priority
import com.example.dtandroid.data.Type
import com.example.dtandroid.utils.Constants

class HabitCreationActivity : AppCompatActivity() {
    private lateinit var habit: Habit
    private lateinit var radioButtons: ArrayList<RadioButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_creation)

        floatingSaveButton.setOnClickListener { saveHabit() }

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            Priority.values().map { priority -> priority.toString() }
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        prioritySpinner.adapter = arrayAdapter
        radioButtons = fillRadioGroup()

        if (intent != null && intent.hasExtra(Constants.INTENT_OBJECT)) {
            habit = intent.getParcelableExtra(Constants.INTENT_OBJECT)!!
            prePopulateData(habit)
        } else {
            habit = Habit()
        }
    }

    private fun prePopulateData(habit: Habit) {
        editTextHabitName.setText(habit.name)
        editTextHabitDescription.setText(habit.description)
        editTextExecutionNumber.setText(habit.executionNumber.toString())
        editTextExecutionFrequency.setText(habit.executionFrequency)
        radioGroup.check(radioButtons[Type.values().indexOf(habit.type)].id)
        prioritySpinner.setSelection(Priority.values().indexOf(habit.priority))
    }

    private fun fillRadioGroup(): ArrayList<RadioButton> {
        val radioButtons = ArrayList<RadioButton>()
        for (idx in Type.values().indices) {
            val radioButton = RadioButton(this).apply { text = Type.values()[idx].toString() }
            radioGroup.addView(radioButton)
            radioButtons.add(radioButton)
        }
        radioGroup.check(radioButtons.first().id)
        return radioButtons
    }

    private fun saveHabit() {
        if (validateFields()) {
            habit.name = editTextHabitName.text.toString()
            habit.description = editTextHabitDescription.text.toString()
            habit.priority = Priority.valueOf(prioritySpinner.selectedItem.toString())
            habit.type =
                Type.valueOf(findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString())
            habit.executionNumber = editTextExecutionNumber.text.toString().toInt()
            habit.executionFrequency = editTextExecutionFrequency.text.toString()

            val intent = Intent().apply {
                putExtra(Constants.INTENT_OBJECT, habit)
                putExtra(Constants.REQUEST_CODE, intent.getIntExtra(Constants.REQUEST_CODE, 0))
                if (intent.hasExtra(Constants.POSITION)){
                    putExtra(Constants.POSITION, intent.getIntExtra(Constants.POSITION, -1))
                }
            }
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun validateFields(): Boolean {
        if (editTextHabitName.text.isEmpty()) {
            Toast.makeText(
                this,
                "Fill in the name field",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        if (editTextExecutionNumber.text.isEmpty()) {
            Toast.makeText(
                this,
                "Fill in the execution number field",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

}