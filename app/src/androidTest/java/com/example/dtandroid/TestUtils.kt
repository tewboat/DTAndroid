package com.example.dtandroid


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.entities.relations.HabitWithDoneDates
import org.hamcrest.Description
import org.hamcrest.Matcher


class TestUtils {
    companion object {
        val data = arrayListOf(
            HabitWithDoneDates(
                Habit("test", "test", Priority.High, Type.Good, 1, 1, "test uid 1"),
                emptyList()
            ),
            HabitWithDoneDates(
                Habit("test2", "test2", Priority.Low, Type.Bad, 1, 4, "test uid 2"), arrayListOf(
                    DoneDate("test uid 2", 1111)
                )
            )
        )


        fun atPosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
            return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description) {
                    description.appendText("has item at position $position: ")
                    itemMatcher.describeTo(description)
                }

                override fun matchesSafely(view: RecyclerView): Boolean {
                    val viewHolder: RecyclerView.ViewHolder =
                        view.findViewHolderForAdapterPosition(position)
                            ?: return false
                    return itemMatcher.matches(viewHolder.itemView)
                }
            }
        }
    }
}