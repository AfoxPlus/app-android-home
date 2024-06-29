package com.afoxplus.home.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.home.utils.Converts.stringToObject
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import com.afoxplus.uikit.objects.vendor.Vendor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val coroutines: UIKitCoroutineDispatcher
) : ViewModel() {

    private val mNavigation: MutableSharedFlow<Navigation> by lazy { MutableSharedFlow() }
    val navigation: SharedFlow<Navigation> get() = mNavigation
    private val mSnackbarContent: MutableSharedFlow<String> by lazy { MutableSharedFlow() }
    val snackbarContent = mSnackbarContent.asSharedFlow()

    fun onScanResponse(data: String) = viewModelScope.launch(coroutines.getMainDispatcher()) {
        try {
            val vendor = stringToObject<Vendor>(data)
            mNavigation.emit(Navigation.GoToMarketOrder(vendor = vendor))
        } catch (ex: Exception) {
            ex.printStackTrace()
            mSnackbarContent.emit(SCAN_ERROR_MESSAGE)
        }
    }

    sealed class Navigation {
        data class GoToMarketOrder(val vendor: Vendor) : Navigation()
    }

    companion object {
        private const val SCAN_ERROR_MESSAGE = "No se detectó ningún código QR valido"
    }
}