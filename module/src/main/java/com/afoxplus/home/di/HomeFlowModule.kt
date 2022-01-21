package com.afoxplus.home.di

import com.afoxplus.home.delivery.flow.HomeFlow
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object HomeFlowModule {

    @Provides
    fun bindHomeFlow(): HomeFlow = HomeFlow.build()
}