package com.example.dtandroid

import android.app.Application
import com.example.dtandroid.di.AppModule
import com.example.dtandroid.di.ApplicationComponent
import com.example.dtandroid.di.DaggerApplicationComponent

open class HabitApplication : Application() {
    val applicationComponent: ApplicationComponent by lazy { initializeComponent() }

    protected open fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}