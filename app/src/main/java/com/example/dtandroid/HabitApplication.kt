package com.example.dtandroid

import android.app.Application
import com.example.dtandroid.di.AppModule
import com.example.dtandroid.di.ApplicationComponent
import com.example.dtandroid.di.DaggerApplicationComponent

class HabitApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate(){
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}