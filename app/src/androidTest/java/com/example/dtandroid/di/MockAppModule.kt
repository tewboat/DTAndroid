package com.example.dtandroid.di

import android.content.Context
import com.example.data.HabitsDatabase
import com.example.data.repositories.DoneDateDatabaseRepository
import com.example.data.repositories.HabitDatabaseRepository
import com.example.data.repositories.HabitWithDoneDatesDatabaseRepository
import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.entities.relations.HabitWithDoneDates

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.flow
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.kotlin.mock

@Module
open class MockAppModule(context: Context) : AppModule(context) {
    private val data = arrayListOf(
        HabitWithDoneDates(
            Habit("test", "test", Priority.High, Type.Good, 1, 1, "test uid 1"),
            emptyList()
        ),
        HabitWithDoneDates(
            Habit("test2", "test2", Priority.Low, Type.Bad, 2, 4, "test uid 2"), arrayListOf(
                DoneDate("test uid 2", 1111)
            )
        )
    )

    @Provides
    override fun provideHabitWithDoneDatesDatabaseRepository(habitDatabase: HabitsDatabase): HabitWithDoneDatesDatabaseRepository {
        val habitWithDoneDatesDatabaseRepository = mock<HabitWithDoneDatesDatabaseRepository>()
        Mockito.`when`(habitWithDoneDatesDatabaseRepository.getDataList()).thenReturn(flow {
            emit(data)
        })
        Mockito.`when`(habitWithDoneDatesDatabaseRepository.insertAll(ArgumentMatchers.anyList()))
        return HabitWithDoneDatesDatabaseRepository(habitDatabase.habitDao())
    }

    @Provides
    override fun provideDoneDatesDatabaseRepository(habitsDatabase: HabitsDatabase): DoneDateDatabaseRepository {
        return DoneDateDatabaseRepository(habitsDatabase.habitDao())
    }
}