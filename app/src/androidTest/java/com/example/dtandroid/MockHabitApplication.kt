package com.example.dtandroid

import com.example.dtandroid.di.ApplicationComponent
import com.example.dtandroid.di.DaggerApplicationComponent
import com.example.dtandroid.di.MockAppModule
import com.example.dtandroid.di.MockRemoteModule

class MockHabitApplication : HabitApplication() {
    override fun initializeComponent(): ApplicationComponent {
        return DaggerApplicationComponent
            .builder()
            .appModule(MockAppModule(this))
            .remoteModule(MockRemoteModule())
            .build()
    }
}