package com.afoxplus.home.demo.global

import com.afoxplus.home.demo.BuildConfig
import com.afoxplus.network.global.AppProperties
import javax.inject.Inject

class AppPropertiesDemo @Inject constructor() : AppProperties {
    override fun getCurrencyID(): String {
        return ""
    }

    override fun getDeviceData(): String {
        return "home_demo"
    }

    override fun getUserUUID(): String {
        return ""
    }

    override fun isAppDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}