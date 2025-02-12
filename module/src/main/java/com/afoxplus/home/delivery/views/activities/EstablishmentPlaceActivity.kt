package com.afoxplus.home.delivery.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.afoxplus.home.delivery.viewmodels.EstablishmentPlaceViewModel
import com.afoxplus.places.delivery.events.OnClickEstablishmentEvent
import com.afoxplus.places.delivery.graphs.AppNavGraph
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class EstablishmentPlaceActivity : ComponentActivity() {

    private val viewModel: EstablishmentPlaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UIKitTheme {
                Scaffold(
                    contentWindowInsets = WindowInsets.safeContent
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .padding(paddingValues = paddingValues)
                            .fillMaxSize()
                    ) {
                        AppNavGraph(
                            activity = this@EstablishmentPlaceActivity,
                            rememberNavController()
                        ) {
                            finish()
                        }
                    }
                }
            }
        }
        collectEventBus()
    }

    private fun collectEventBus() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.onEventBusListener.collect { events ->
                when (events) {
                    is OnClickEstablishmentEvent -> {
                        viewModel.setEstablishment(events.establishment)
                        EstablishmentSummaryActivity.newStartActivity(
                            this@EstablishmentPlaceActivity
                        )
                    }
                }
            }
        }
    }

    companion object {
        fun newStartActivity(activity: Activity) {
            val intent = Intent(activity, EstablishmentPlaceActivity::class.java)
            activity.startActivity(intent)
        }
    }
}