package com.afoxplus.home.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afoxplus.home.delivery.views.activities.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        HomeActivity.newStartActivity(this)
    }
}