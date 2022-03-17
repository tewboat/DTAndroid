package com.example.dtandroid.ui

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dtandroid.R
import com.example.dtandroid.data.Habit
import com.example.dtandroid.utils.Constants

class MainActivity : AppCompatActivity(), OnItemClick {

    private val habitsListAdapter = HabitsListAdapter(ArrayList(), this)

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent = result.data!!
                val habit = data.getParcelableExtra<Habit>(Constants.INTENT_OBJECT)
                when (data.getIntExtra(Constants.REQUEST_CODE, 0)) {
                    Constants.INTENT_CREATE -> habitsListAdapter.addItem(habit!!)
                    Constants.INTENT_UPDATE -> {
                        val position = data.getIntExtra(Constants.POSITION, -1)
                        habitsListAdapter.updateItem(habit!!, position)
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        habitsRecyclerView.adapter = habitsListAdapter
        floatingAddButton.setOnClickListener { _ ->
            val intent = Intent(this, HabitCreationActivity::class.java).apply{
                putExtra(Constants.REQUEST_CODE, Constants.INTENT_CREATE)
            }
            resultLauncher.launch(intent)
        }
    }

    override fun onClick(habit: Habit, position: Int) {
        val intent = Intent(this, HabitCreationActivity::class.java).apply {
            putExtra(Constants.INTENT_OBJECT, habit)
            putExtra(Constants.POSITION, position)
            putExtra(Constants.REQUEST_CODE, Constants.INTENT_UPDATE)
        }
        resultLauncher.launch(intent)
    }
}