package com.afoxplus.home.demo.di

import com.afoxplus.home.demo.global.AppPropertiesDemo
import com.afoxplus.network.global.AppProperties
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HomeDemoModule {

    @Binds
    fun bindAppProperties(demo: AppPropertiesDemo): AppProperties
}