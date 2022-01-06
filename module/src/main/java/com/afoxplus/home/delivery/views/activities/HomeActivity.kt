package com.afoxplus.home.delivery.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.afoxplus.home.databinding.ActivityHomeBinding
import com.afoxplus.home.delivery.models.ScanVO
import com.afoxplus.home.delivery.utils.Converts
import com.afoxplus.products.delivery.flow.ProductFlow
import com.afoxplus.restaurants.delivery.flow.RestaurantFlow
import com.afoxplus.uikit.activities.BaseActivity
import com.afoxplus.uikit.activities.extensions.addFragmentToActivity
import com.google.zxing.integration.android.IntentIntegrator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var productFlow: ProductFlow

    @Inject
    lateinit var restaurantFlow: RestaurantFlow

    companion object {
        fun newStartActivity(activity: Activity) {
            val intent = Intent(activity, HomeActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFragments()
        setupListeners()
    }

    override fun setMainView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUpView() {
        setupFragments()
        setupListeners()
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
                val scanVO = Converts.stringToObject<ScanVO>(data)
                Toast.makeText(this, "scanData: $scanVO", Toast.LENGTH_SHORT).show()
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