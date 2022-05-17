package com.example.data.json

import com.example.domain.entities.DoneDate
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class DoneDateJsonDeserializer: JsonDeserializer<DoneDate> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DoneDate? {
        json?.asJsonObject?.apply {
            return DoneDate(
                get("habit_uid").asString,
                get("date").asLong
            )
        }
        return null
    }
}