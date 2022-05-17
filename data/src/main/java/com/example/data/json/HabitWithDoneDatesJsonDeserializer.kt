package com.example.data.json

import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.entities.relations.HabitWithDoneDates
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement

class HabitWithDoneDatesJsonDeserializer : JsonDeserializer<HabitWithDoneDates> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: java.lang.reflect.Type?,
        context: JsonDeserializationContext?
    ): HabitWithDoneDates? {
        json?.asJsonObject?.apply {
            return HabitWithDoneDates(Habit(
                title = get("title").asString,
                count = get("count").asInt,
                description = get("description").asString,
                frequency = get("frequency").asInt,
                priority = Priority.values().first { it.intValue == get("priority").asInt },
                type = Type.values().first { get("type").asInt == it.intValue },
                uid = get("uid").asString
            ),
                get("done_dates").asJsonArray.map { DoneDate(get("uid").asString, it.asLong) }
            )
        }
        return null
    }
}