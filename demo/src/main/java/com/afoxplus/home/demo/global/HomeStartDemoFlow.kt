package com.afoxplus.home.demo.global

import android.content.Intent
import com.afoxplus.home.demo.delivery.views.activities.MainActivity
import com.afoxplus.demo_config.delivery.flow.StartDemoFlow
import com.afoxplus.uikit.activities.UIKitBaseActivity
import javax.inject.Inject

class HomeStartDemoFlow @Inject constructor() : StartDemoFlow {

    override fun start(activity: UIKitBaseActivity) {
        activity.startActivity(Intent(activity, MainActivity::class.java))
    }
}