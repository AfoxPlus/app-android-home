package com.afoxplus.home.demo.global

import android.content.Intent
import com.afoxplus.home.demo.delivery.views.activites.MainActivity
import com.afoxplus.module.delivery.flow.StartDemoFlow
import com.afoxplus.uikit.activities.UIKitBaseActivity
import javax.inject.Inject

class HomeStartDemoFlow @Inject constructor() : StartDemoFlow {

    override fun start(activity: UIKitBaseActivity) {
        activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}