package com.example.dtandroid.di

import com.example.domain.usecases.local.*
import com.example.domain.usecases.remote.DeleteRemoteHabitUseCase
import com.example.domain.usecases.remote.DoneRemoteHabitUseCase
import com.example.domain.usecases.remote.LoadRemoteHabitsUseCase
import com.example.domain.usecases.remote.SendRemoteHabitUseCase
import com.example.dtandroid.di.AppModule
import com.example.dtandroid.di.RemoteModule
import com.example.dtandroid.presentation.ui.FilterDialogFragment
import com.example.dtandroid.presentation.ui.HabitCreationFragment
import com.example.dtandroid.presentation.ui.HabitsListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RemoteModule::class])
interface ApplicationComponent {

    fun inject(fragment: HabitsListFragment)

    fun inject(fragment: FilterDialogFragment)

    fun getLoadHabitUseCase(): LoadHabitUseCase

    fun getLoadAllHabitsUseCase(): LoadAllHabitsUseCase

    fun getSaveHabitUseCase(): SaveHabitUseCase

    fun getSaveHabitsUseCase(): SaveHabitsUseCase

    fun getDeleteHabitUseCase(): DeleteHabitUseCase

    fun getUpdateHabitUseCase(): UpdateHabitUseCase

    fun getLoadRemoteHabitsUseCase(): LoadRemoteHabitsUseCase

    fun getSendRemoteHabitUseCase(): SendRemoteHabitUseCase

    fun getDoneRemoteHabitUseCase(): DoneRemoteHabitUseCase

    fun getDeleteRemoteHabitUseCase(): DeleteRemoteHabitUseCase

    fun getLoadAllHabitsWithDoneDatesUseCase(): LoadAllHabitsWithDoneDatesUseCase

    fun getDoneHabitUseCase(): DoneHabitUseCase

    fun getSaveHabitsWithDoneDatesUseCase(): SaveHabitsWithDoneDatesUseCase
}