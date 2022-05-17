package com.example.dtandroid.modules

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.room.Room
import com.example.data.HabitsDatabase
import com.example.data.json.HabitWithDoneDatesJsonDeserializer
import com.example.data.repositories.DoneDateDatabaseRepository
import com.example.data.repositories.HabitDatabaseRepository
import com.example.data.repositories.HabitWithDoneDatesDatabaseRepository
import com.example.domain.entities.DoneDate
import com.example.domain.interfaces.DatabaseRepository
import com.example.domain.usecases.local.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.observeOn
import javax.inject.Singleton

@Module
class HabitsDatabaseModule(private val applicationContext: Context) {

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
    fun provideLoadAllHabitsUseCase(habitRepository: HabitDatabaseRepository): LoadAllHabitsUseCase {
        return LoadAllHabitsUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideSaveHabitUseCase(habitRepository: HabitDatabaseRepository): SaveHabitUseCase {
        return SaveHabitUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideSaveHabitsUseCase(habitRepository: HabitDatabaseRepository): SaveHabitsUseCase {
        return SaveHabitsUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideDeleteHabitUseCase(habitRepository: HabitDatabaseRepository): DeleteHabitUseCase {
        return DeleteHabitUseCase(habitRepository, Dispatchers.IO)
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
    fun provideHabitDatabaseRepository(habitsDatabase: HabitsDatabase): HabitDatabaseRepository {
        return HabitDatabaseRepository(habitsDatabase.habitDao())
    }

    @Provides
    fun provideHabitWithDoneDatesDatabaseRepository(habitDatabase: HabitsDatabase): HabitWithDoneDatesDatabaseRepository {
        return HabitWithDoneDatesDatabaseRepository(habitDatabase.habitDao())
    }

    @Provides
    fun provideDoneDatesDatabaseRepository(habitsDatabase: HabitsDatabase): DoneDateDatabaseRepository {
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