package com.example.data.json

import android.os.Build
import android.util.Log
import com.example.domain.entities.Habit
import com.google.gson.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class HabitJsonSerializer : JsonSerializer<Habit> {
    override fun serialize(
        src: Habit?,
        typeOfSrc: java.lang.reflect.Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonObject().apply {
        addProperty("count", src?.count)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            addProperty("date", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
        }
        addProperty("description", src?.description)
        add("done_states", JsonArray())
        addProperty("frequency", src?.frequency)
        addProperty("priority", src?.priority?.intValue)
        addProperty("title", src?.title)
        addProperty("type", src?.type?.intValue)
        if (!src?.uid.isNullOrEmpty())
            addProperty("uid", src?.uid)
    }
}