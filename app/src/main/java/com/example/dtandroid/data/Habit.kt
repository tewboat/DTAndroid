package com.example.dtandroid.data

import android.os.Parcelable
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dtandroid.R
import com.google.gson.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "habits")
@Parcelize
data class Habit(
    @SerializedName("title")
    var name: String = String(),
    var description: String = String(),
    var priority: Priority = Priority.Low,
    var type: Type = Type.Good,
    @SerializedName("count")
    var executionNumber: Int = 0,
    @SerializedName("frequency")
    var executionFrequency: Int = 0,
    @SerializedName("uid")
    @PrimaryKey var uid: String = String()
) : Parcelable

class HabitJsonSerializer : JsonSerializer<Habit> {
    override fun serialize(
        src: Habit?,
        typeOfSrc: java.lang.reflect.Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonObject().apply {
        addProperty("count", src?.executionNumber)
        addProperty("date", 0)
        addProperty("description", src?.description)
        add("done_states", JsonArray())
        addProperty("frequency", src?.executionFrequency)
        addProperty("priority", src?.priority?.intValue)
        addProperty("title", src?.name)
        addProperty("type", src?.type?.intValue)
        if (!src?.uid.isNullOrEmpty())
            addProperty("uid", src?.uid)
        Log.d("Json", toString())
    }
}

class HabitJsonDeserializer : JsonDeserializer<Habit> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: java.lang.reflect.Type?,
        context: JsonDeserializationContext?
    ): Habit {
        json?.asJsonObject?.apply {
            return Habit(
                name = get("title").asString,
                executionNumber = get("count").asInt,
                description = get("description").asString,
                executionFrequency = get("frequency").asInt,
                priority = Priority.values().first { it.intValue == get("priority").asInt },
                type = Type.values().first { get("type").asInt == it.intValue },
                uid = get("uid").asString
            )
        }
        return Habit()
    }
}


enum class Priority(val intValue: Int, val res: Int) {
    Low(2, R.string.priority_low),
    Medium(1, R.string.priority_medium),
    High(0, R.string.priority_high)
}

enum class Type(val intValue: Int) {
    Good(0),
    Bad(1)
}

sealed class Sort<T : Comparable<T>>(val selector: (Habit) -> T, val res: Int) {
    class ByName : Sort<String>({ habit -> habit.name }, R.string.sort_by_name)
    class ByPriority :
        Sort<Int>({ habit -> habit.priority.intValue }, R.string.sort_by_priority)

    class ByExecutionNumber :
        Sort<Int>({ habit -> habit.executionNumber }, R.string.sort_by_execution_number)
}