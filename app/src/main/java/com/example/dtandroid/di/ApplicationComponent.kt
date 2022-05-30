package com.example.dtandroid.di

import com.example.domain.usecases.local.*
import com.example.domain.usecases.remote.DoneRemoteHabitUseCase
import com.example.domain.usecases.remote.LoadRemoteHabitsUseCase
import com.example.domain.usecases.remote.SendRemoteHabitUseCase
import com.example.dtandroid.presentation.ui.FilterDialogFragment
import com.example.dtandroid.presentation.ui.HabitsListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RemoteModule::class])
interface ApplicationComponent {

    fun inject(fragment: HabitsListFragment)

    fun inject(fragment: FilterDialogFragment)

    fun getLoadHabitUseCase(): LoadHabitUseCase

    fun getSaveHabitUseCase(): SaveHabitUseCase

    fun getUpdateHabitUseCase(): UpdateHabitUseCase

    fun getLoadRemoteHabitsUseCase(): LoadRemoteHabitsUseCase

    fun getSendRemoteHabitUseCase(): SendRemoteHabitUseCase

    fun getDoneRemoteHabitUseCase(): DoneRemoteHabitUseCase

    fun getLoadAllHabitsWithDoneDatesUseCase(): LoadAllHabitsWithDoneDatesUseCase

    fun getDoneHabitUseCase(): DoneHabitUseCase

    fun getSaveHabitsWithDoneDatesUseCase(): SaveHabitsWithDoneDatesUseCase

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent
        fun appModule(mockAppModule: AppModule): Builder
        fun remoteModule(mockRemoteModule: RemoteModule): Builder
    }
}