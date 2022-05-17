package com.example.data

import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.HabitDone
import com.example.domain.entities.HabitUID
import com.example.domain.entities.relations.HabitWithDoneDates
import retrofit2.http.*

interface DoubletappApi {
    @GET("habit")
    suspend fun getHabits(@Header("Authorization") token: String): List<HabitWithDoneDates>

    @PUT("habit")
    suspend fun putHabit(@Header("Authorization") token: String, @Body habit: Habit): HabitUID

    @DELETE("habit")
    suspend fun deleteHabit(
        @Header("Authorization") token: String,
        @Field("uid") uid: String
    ): HabitUID

    @POST("habit_done")
    suspend fun done(
        @Header("Authorization") token: String,
        @Body doneDate: DoneDate
    ): HabitDone
}