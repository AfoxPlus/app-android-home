package com.afoxplus.home.demo.di

import com.afoxplus.home.demo.global.HomeStartDemoFlow
import com.afoxplus.module.delivery.flow.StartDemoFlow
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HomeDemoModule {

    @Binds
    fun bindStartDemoFlow(demo: HomeStartDemoFlow): StartDemoFlow
}