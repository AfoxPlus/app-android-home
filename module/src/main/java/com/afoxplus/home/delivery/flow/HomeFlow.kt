package com.afoxplus.home.delivery.flow

import android.app.Activity
import com.afoxplus.home.delivery.views.activities.HomeActivity
import javax.inject.Inject

interface HomeFlow {
    companion object {
        fun build(): HomeFlow = HomeFlowImpl()
    }

    fun goToHome(activity: Activity)
}

internal class HomeFlowImpl @Inject constructor() : HomeFlow {
    override fun goToHome(activity: Activity) {
        HomeActivity.newStartActivity(activity)
    }
}