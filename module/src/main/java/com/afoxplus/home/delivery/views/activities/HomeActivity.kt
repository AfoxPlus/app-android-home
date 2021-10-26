package com.afoxplus.home.delivery.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afoxplus.home.databinding.ActivityHomeBinding
import com.afoxplus.home.delivery.models.ScanVO
import com.afoxplus.home.utils.Converts

import com.google.zxing.integration.android.IntentIntegrator

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

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
        setupListeners()
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