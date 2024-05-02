package com.afoxplus.home.delivery.flow

import android.app.Activity
import com.afoxplus.home.delivery.views.activities.HomeActivity
import javax.inject.Inject

fun interface HomeFlow {
    fun goToHome(activity: Activity)
}

internal class HomeFlowAction @Inject constructor() : HomeFlow {
    override fun goToHome(activity: Activity) {
        HomeActivity.newStartActivity(activity)
    }
}