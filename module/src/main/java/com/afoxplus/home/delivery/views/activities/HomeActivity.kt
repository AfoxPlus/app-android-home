package com.afoxplus.home.delivery.views.activities

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import com.afoxplus.home.databinding.ActivityHomeBinding
import com.afoxplus.home.delivery.viewmodels.HomeViewModel
import com.afoxplus.orders.delivery.flow.OrderFlow
import com.afoxplus.products.delivery.flow.ProductFlow
import com.afoxplus.restaurants.delivery.flow.RestaurantFlow
import com.afoxplus.uikit.activities.BaseActivity
import com.afoxplus.uikit.activities.extensions.addFragmentToActivity
import com.afoxplus.uikit.bus.EventObserver
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var productFlow: ProductFlow

    @Inject
    lateinit var restaurantFlow: RestaurantFlow

    @Inject
    lateinit var orderFlow: OrderFlow

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
        viewModel.homeRestaurantClicked.observe(this, EventObserver { openScan() })
        viewModel.productOfferClicked.observe(this, EventObserver { openScan() })
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
        val integrator = IntentIntegrator(this).apply {
            setPrompt("Scan a barcode")
            setCameraId(0)
            setBeepEnabled(false)
            setTorchEnabled(false)
            setBarcodeImageEnabled(true)
            setOrientationLocked(true)
        }
        integrator.initiateScan()
    }

    private fun analyzeScanResponse(data: String) {
        data.isNotEmpty().let {
            try {
                //val scanVO = Converts.stringToObject<ScanVO>(data)
                //TODO: Send data get from scan goToMarketOrder(activity, scanVO)
                orderFlow.goToMarketOrder(this)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            result.contents?.let {
                analyzeScanResponse(it)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}