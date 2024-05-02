package com.afoxplus.home.di

import com.afoxplus.home.delivery.flow.HomeFlow
import com.afoxplus.home.delivery.flow.HomeFlowAction
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal abstract class HomeFlowModule {
    @Binds
    abstract fun bindHomeFlow(homeFlowAction: HomeFlowAction): HomeFlow
}