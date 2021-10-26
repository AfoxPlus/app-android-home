package com.afoxplus.module.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afoxplus.module.view.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        HomeActivity.newStartActivity(this)
    }
}