package com.example.dtandroid

import com.example.domain.usecases.local.*
import com.example.domain.usecases.remote.DeleteRemoteHabitUseCase
import com.example.domain.usecases.remote.DoneRemoteHabitUseCase
import com.example.domain.usecases.remote.LoadRemoteHabitsUseCase
import com.example.domain.usecases.remote.SendRemoteHabitUseCase
import com.example.dtandroid.modules.HabitsDatabaseModule
import com.example.dtandroid.modules.HabitsRemoteModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [HabitsDatabaseModule::class, HabitsRemoteModule::class])
interface ApplicationComponent {

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