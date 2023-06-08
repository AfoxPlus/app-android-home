package com.afoxplus.home.delivery.views.activities

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.afoxplus.home.R
import com.afoxplus.home.databinding.ActivityHomeBinding
import com.afoxplus.home.delivery.viewmodels.HomeViewModel
import com.afoxplus.orders.delivery.flow.OrderFlow
import com.afoxplus.products.delivery.flow.ProductFlow
import com.afoxplus.restaurants.delivery.flow.RestaurantFlow
import com.afoxplus.uikit.activities.UIKitBaseActivity
import com.afoxplus.uikit.activities.extensions.addFragmentToActivity
import com.afoxplus.uikit.bus.UIKitEventObserver
import com.afoxplus.uikit.objects.vendor.VendorShared
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : UIKitBaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private val barcodeLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            result.contents?.let {
                analyzeScanResponse(it)
            }
        }

    @Inject
    lateinit var productFlow: ProductFlow

    @Inject
    lateinit var restaurantFlow: RestaurantFlow

    @Inject
    lateinit var orderFlow: OrderFlow

    @Inject
    lateinit var vendorShared: VendorShared

    companion object {
        fun newStartActivity(activity: Activity) {
            val intent = Intent(activity, HomeActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun setMainView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUpView() {
        setupFragments()
        setupListeners()
    }

    override fun observerViewModel() {
        viewModel.homeRestaurantClicked.observe(this) {
            openScan()
        }
        viewModel.productOfferClicked.observe(this, UIKitEventObserver { openScan() })

        lifecycleScope.launchWhenCreated {
            viewModel.navigation.collectLatest { navigation ->
                when (navigation) {
                    HomeViewModel.Navigation.GoToMarketOrder -> orderFlow.goToMarketOrderActivity(
                        this@HomeActivity
                    )
                }
            }
        }
    }

    private fun setupFragments() {
        addFragmentToActivity(
            supportFragmentManager,
            productFlow.getProductHomeOfferFragment(),
            binding.fcvProducts.id
        )
        addFragmentToActivity(
            supportFragmentManager,
            restaurantFlow.getRestaurantHomeFragment(),
            binding.fcvRestaurants.id
        )
    }

    private fun setupListeners() {
        binding.fabScan.setOnClickListener {
            openScan()
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
}