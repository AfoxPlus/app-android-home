package com.afoxplus.home.demo.delivery.views.activites

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.afoxplus.home.delivery.views.activities.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runnable = Runnable {
            if (!isFinishing) {
                HomeActivity.newStartActivity(this)
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 1500)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

}