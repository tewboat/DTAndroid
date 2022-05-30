package com.example.dtandroid.di

import com.example.data.DoubletappApi
import com.example.data.json.DoneDateJsonDeserializer
import com.example.data.json.DoneDateJsonSerializer
import com.example.data.json.HabitJsonSerializer
import com.example.data.json.HabitWithDoneDatesJsonDeserializer
import com.example.data.repositories.HabitRemoteRepository
import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.usecases.remote.DoneRemoteHabitUseCase
import com.example.domain.usecases.remote.LoadRemoteHabitsUseCase
import com.example.domain.usecases.remote.SendRemoteHabitUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class RemoteModule {

    @Provides
    fun provideLoadRemoteHabitsUseCase(habitRemoteRepository: HabitRemoteRepository) : LoadRemoteHabitsUseCase {
        return LoadRemoteHabitsUseCase(habitRemoteRepository, Dispatchers.IO)
    }

    @Provides
    open fun provideSendRemoteHabitUseCase(habitRemoteRepository: HabitRemoteRepository) : SendRemoteHabitUseCase {
        return SendRemoteHabitUseCase(habitRemoteRepository, Dispatchers.IO)
    }

    @Provides
    fun provideDoneRemoteHabitUseCase(habitRemoteRepository: HabitRemoteRepository) : DoneRemoteHabitUseCase {
        return DoneRemoteHabitUseCase(habitRemoteRepository, Dispatchers.IO)
    }

    @Provides
    fun provideHabitRemoteRepository(doubletappApi: DoubletappApi): HabitRemoteRepository{
        return HabitRemoteRepository(doubletappApi)
    }

    @Provides
    fun provideRetrofitService(retrofit: Retrofit): DoubletappApi {
        return retrofit.create(DoubletappApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Habit::class.java, HabitJsonSerializer())
            .registerTypeAdapter(HabitWithDoneDates::class.java, HabitWithDoneDatesJsonDeserializer())
            .registerTypeAdapter(DoneDate::class.java, DoneDateJsonSerializer())
            .registerTypeAdapter(DoneDate::class.java, DoneDateJsonDeserializer())
            .create()
    }
}