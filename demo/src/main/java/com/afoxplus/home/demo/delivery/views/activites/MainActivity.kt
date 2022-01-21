package com.afoxplus.home.demo.delivery.views.activites

import com.afoxplus.home.delivery.flow.HomeFlow
import com.afoxplus.home.demo.databinding.ActivityMainBinding
import com.afoxplus.uikit.activities.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var homeFlow: HomeFlow

    override fun setMainView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setUpView() {
        binding.btnGoToHome.setOnClickListener { homeFlow.goToHome(this) }
    }
}