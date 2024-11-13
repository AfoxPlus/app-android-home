package com.afoxplus.home.delivery.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.afoxplus.home.R
import com.afoxplus.home.delivery.components.FragmentContainer
import com.afoxplus.home.delivery.screens.HomeScreen
import com.afoxplus.home.delivery.viewmodels.HomeViewModel
import com.afoxplus.orders.delivery.flow.OrderFlow
import com.afoxplus.restaurants.delivery.flow.RestaurantFlow
import com.afoxplus.restaurants.delivery.views.events.OnClickDeliveryEvent
import com.afoxplus.restaurants.delivery.views.events.OnClickRestaurantHomeEvent
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()

    private val barcodeLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            result.contents?.let {
                analyzeScanResponse(it)
            }
        }

    @Inject
    lateinit var restaurantFlow: RestaurantFlow

    @Inject
    lateinit var orderFlow: OrderFlow


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UIKitTheme {
                HomeScreen(
                    ordersStatusContent = {
                        Box(modifier = Modifier.padding(top = UIKitTheme.spacing.spacing12)) {
                            FragmentContainer(
                                fragmentManager = supportFragmentManager,
                                commit = { add(it, orderFlow.getStateOrdersFragment()) }
                            )
                        }
                    },
                    restaurantsContent = {
                        FragmentContainer(
                            fragmentManager = supportFragmentManager,
                            commit = { add(it, restaurantFlow.getRestaurantHomeFragment()) }
                        )
                    },
                    onClickScan = { openScan() })
            }

        }
        collectEventBus()
        collectNavigation()
    }

    private fun collectEventBus() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.onEventBusListener.collect { events ->
                when (events) {
                    is OnClickRestaurantHomeEvent -> {
                        viewModel.setRestaurantFromTable(events.restaurant)
                    }

                    is OnClickDeliveryEvent -> {
                        viewModel.setRestaurantFromDelivery(events.restaurant)
                    }
                }
            }
        }
    }

    private fun collectNavigation() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.navigation.collectLatest { navigation ->
                when (navigation) {
                    HomeViewModel.Navigation.GoToMarketOrder -> orderFlow.goToLandingEstablishmentActivity(
                        this@HomeActivity
                    )
                }
            }
        }
    }


    private fun openScan() {
        val options = ScanOptions().apply {
            setPrompt(getString(R.string.home_scan_prompt))
            setCameraId(0)
            setBeepEnabled(false)
            setTorchEnabled(false)
            setBarcodeImageEnabled(false)
            setOrientationLocked(false)
        }
        barcodeLauncher.launch(options)
    }

    private fun analyzeScanResponse(data: String) {
        data.isNotEmpty().let { viewModel.onScanResponse(data) }
    }


    companion object {
        fun newStartActivity(activity: Activity) {
            val intent = Intent(activity, HomeActivity::class.java)
            activity.startActivity(intent)
        }
    }

}