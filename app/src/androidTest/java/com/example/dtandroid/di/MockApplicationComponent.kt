package com.example.dtandroid.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MockAppModule::class, MockRemoteModule::class])
interface MockApplicationComponent: ApplicationComponent {


    @Component.Builder
    interface Builder {
        fun build(): MockApplicationComponent
        fun mockAppModule(mockAppModule: MockAppModule): Builder
        fun mockRemoteModule(mockRemoteModule: MockRemoteModule): Builder
    }
}