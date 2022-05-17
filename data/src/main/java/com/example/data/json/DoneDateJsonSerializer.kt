package com.example.data.json

import android.util.Log
import com.example.domain.entities.DoneDate
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class DoneDateJsonSerializer: JsonSerializer<DoneDate> {
    override fun serialize(
        src: DoneDate?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val json = JsonObject()
        json.addProperty("habit_uid", src?.habitUid)
        json.addProperty("date", src?.date)
        Log.d("Retrofit", json.toString())
        return json
    }
}