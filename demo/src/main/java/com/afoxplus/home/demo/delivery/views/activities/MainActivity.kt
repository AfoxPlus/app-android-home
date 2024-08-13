package com.afoxplus.home.demo.delivery.views.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.afoxplus.home.delivery.flow.HomeFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    @Inject
    lateinit var homeFlow: HomeFlow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runnable = Runnable {
            if (!isFinishing) {
                homeFlow.goToHome(this)
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