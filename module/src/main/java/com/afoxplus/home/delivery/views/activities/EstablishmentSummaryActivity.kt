package com.afoxplus.home.delivery.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.afoxplus.bdui.delivery.screens.EstablishmentDetailScreen
import com.afoxplus.home.delivery.viewmodels.EstablishmentSummaryViewModel
import com.afoxplus.orders.delivery.flow.OrderFlow
import com.afoxplus.uikit.bus.OnClickDeeplinkEvent
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class EstablishmentSummaryActivity : AppCompatActivity() {

    @Inject
    lateinit var orderFlow: OrderFlow
    private val viewModel: EstablishmentSummaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UIKitTheme {
                val establishmentCode by viewModel.establishmentCodeState.collectAsState()
                EstablishmentDetailScreen(
                    establishmentCode = establishmentCode,
                    onBack = { onBackPressedDispatcher.onBackPressed() })
            }
        }
        collectEventBus()
        collectNavigation()
    }

    private fun collectEventBus() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.onEventBusListener.collect { events ->
                when (events) {
                    is OnClickDeeplinkEvent -> {
                        viewModel.handlerDeeplinkEvent(events.deeplink)
                    }
                }
            }
        }
    }

    private fun collectNavigation() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.navigation.collectLatest { navigation ->
                when (navigation) {
                    EstablishmentSummaryViewModel.Navigation.GoToMarketOrder -> orderFlow.goToLandingEstablishmentActivity(
                        this@EstablishmentSummaryActivity
                    )
                }
            }
        }
    }

    companion object {
        fun newStartActivity(activity: Activity) {
            val intent = Intent(activity, EstablishmentSummaryActivity::class.java)
            activity.startActivity(intent)
        }
    }
}