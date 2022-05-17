package com.example.dtandroid

import android.app.Application
import com.example.dtandroid.modules.HabitsDatabaseModule

class HabitApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate(){
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .habitsDatabaseModule(HabitsDatabaseModule(this))
            .build()
    }
}