package com.example.dtandroid.di

import android.content.Context
import com.example.data.HabitsDatabase
import com.example.data.repositories.DoneDateDatabaseRepository
import com.example.data.repositories.HabitWithDoneDatesDatabaseRepository
import com.example.domain.entities.DoneDate
import com.example.domain.entities.Habit
import com.example.domain.entities.Priority
import com.example.domain.entities.Type
import com.example.domain.entities.relations.HabitWithDoneDates
import com.example.domain.interfaces.DatabaseRepository
import com.example.dtandroid.TestUtils

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock


@Module
open class MockAppModule(context: Context) : AppModule(context) {

    @Provides
    override fun provideHabitWithDoneDatesDatabaseRepository(habitDatabase: HabitsDatabase): DatabaseRepository<HabitWithDoneDates> {

        val flow = flow<List<HabitWithDoneDates>> { emit(TestUtils.data) }
        val habitWithDoneDatesDatabaseRepository =
            mock(HabitWithDoneDatesDatabaseRepository::class.java)
        Mockito.`when`(habitWithDoneDatesDatabaseRepository.getDataList())
            .thenReturn(flow)
        Mockito.`when`(habitWithDoneDatesDatabaseRepository.insertAll(ArgumentMatchers.anyList()))
            .then { invocation ->
                flow.transform {
                    val clonedData = TestUtils.data.clone() as ArrayList<*>
                    clonedData.add(invocation.getArgument(0))
                    emit(clonedData)
                }
            }
        return habitWithDoneDatesDatabaseRepository
    }

    @Provides
    override fun provideDoneDatesDatabaseRepository(habitsDatabase: HabitsDatabase): DoneDateDatabaseRepository {
        return DoneDateDatabaseRepository(habitsDatabase.habitDao())
    }
}