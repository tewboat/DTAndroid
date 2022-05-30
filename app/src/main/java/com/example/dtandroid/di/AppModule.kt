package com.example.dtandroid.di

import android.content.Context
import androidx.room.Room
import com.example.data.HabitsDatabase
import com.example.data.repositories.DoneDateDatabaseRepository
import com.example.data.repositories.HabitDatabaseRepository
import com.example.data.repositories.HabitWithDoneDatesDatabaseRepository
import com.example.domain.usecases.local.*
import com.example.domain.usecases.remote.DoneRemoteHabitUseCase
import com.example.domain.usecases.remote.LoadRemoteHabitsUseCase
import com.example.dtandroid.presentation.viewmodels.HabitsViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module()
open class AppModule(private val applicationContext: Context) {

    @Provides
    @Singleton
    fun provideHabitsViewModelFactory(
        loadAllHabitsUseCase: LoadAllHabitsWithDoneDatesUseCase,
        loadRemoteHabitsUseCase: LoadRemoteHabitsUseCase,
        doneRemoteHabitUseCase: DoneRemoteHabitUseCase,
        doneHabitUseCase: DoneHabitUseCase,
        saveHabitsWithDoneDatesUseCase: SaveHabitsWithDoneDatesUseCase
    ): HabitsViewModelFactory {
        return HabitsViewModelFactory(
            loadAllHabitsUseCase,
            loadRemoteHabitsUseCase,
            doneRemoteHabitUseCase,
            doneHabitUseCase,
            saveHabitsWithDoneDatesUseCase
        )
    }

    @Provides
    fun provideSaveHabitsWithDoneDatesUseCase(habitRepository: HabitWithDoneDatesDatabaseRepository): SaveHabitsWithDoneDatesUseCase {
        return SaveHabitsWithDoneDatesUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideLoadAllHabitsWithDoneDatesUseCase(habitRepository: HabitWithDoneDatesDatabaseRepository): LoadAllHabitsWithDoneDatesUseCase {
        return LoadAllHabitsWithDoneDatesUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideLoadHabitUseCase(habitRepository: HabitDatabaseRepository): LoadHabitUseCase {
        return LoadHabitUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideSaveHabitUseCase(habitRepository: HabitDatabaseRepository): SaveHabitUseCase {
        return SaveHabitUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideUpdateHabitUseCase(habitRepository: HabitDatabaseRepository): UpdateHabitUseCase {
        return UpdateHabitUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideDoneHabitUseCase(doneDateDatabaseRepository: DoneDateDatabaseRepository): DoneHabitUseCase {
        return DoneHabitUseCase(doneDateDatabaseRepository, Dispatchers.IO)
    }

    @Provides
    open fun provideHabitDatabaseRepository(habitsDatabase: HabitsDatabase): HabitDatabaseRepository {
        return HabitDatabaseRepository(habitsDatabase.habitDao())
    }

    @Provides
    open fun provideHabitWithDoneDatesDatabaseRepository(habitDatabase: HabitsDatabase): HabitWithDoneDatesDatabaseRepository {
        return HabitWithDoneDatesDatabaseRepository(habitDatabase.habitDao())
    }

    @Provides
    open fun provideDoneDatesDatabaseRepository(habitsDatabase: HabitsDatabase): DoneDateDatabaseRepository {
        return DoneDateDatabaseRepository(habitsDatabase.habitDao())
    }

    @Provides
    fun provideContext(): Context = applicationContext

    @Singleton
    @Provides
    fun provideHabitsDatabase(context: Context): HabitsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HabitsDatabase::class.java,
            "habits_db"
        )
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()
    }
}