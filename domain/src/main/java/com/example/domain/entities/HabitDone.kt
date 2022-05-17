package com.example.domain.entities

interface ApiResponse

class HabitDone(
    val date: Int = 0,
    val habit_uid: String? = null
) : ApiResponse