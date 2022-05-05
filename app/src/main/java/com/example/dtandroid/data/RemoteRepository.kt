package com.example.dtandroid.data

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.IOException


interface DoubletappApi {
    @GET("habit")
    suspend fun getHabits(@Header("Authorization") token: String): List<Habit>

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
        @Field("habit_uid") uid: String,
        @Field("date") date: Int
    ): HabitDone
}

object RemoteRepository {
    private const val url = "https://droid-test-server.doubletapp.ru/api/"
    private const val token = "2f051711-d95a-4fde-88c1-371129b6bc99"

    private val gson = GsonBuilder()
        .registerTypeAdapter(Habit::class.java, HabitJsonSerializer())
        .registerTypeAdapter(Habit::class.java, HabitJsonDeserializer())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val doubletappApi = retrofit.create(DoubletappApi::class.java)


    suspend fun getListHabits(): List<Habit> = doubletappApi.getHabits(token)


    suspend fun putHabit(habit: Habit): HabitUID = doubletappApi.putHabit(token, habit)

    suspend fun deleteHabit(habit: Habit): HabitUID = doubletappApi.deleteHabit(token, habit.uid)

    suspend fun postDone(habit: Habit, date: Int): HabitDone = doubletappApi.done(token, habit.uid, date)

}
