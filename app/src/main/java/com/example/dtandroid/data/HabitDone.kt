package com.example.dtandroid.data

import com.google.gson.annotations.SerializedName

interface ApiResponse

class HabitDone(
    @SerializedName("data") val date: Int = 0,
    @SerializedName("habit_uid") val habit_uid: String? = null
) : ApiResponse