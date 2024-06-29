package com.afoxplus.home.delivery.viewmodels

import com.afoxplus.home.utils.BaseViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseViewModelTest() {
    private lateinit var homeVieWModel: HomeViewModel

    @Before
    fun setup() {
        homeVieWModel = HomeViewModel(testDispatcherProvider)
    }


    @Test
    fun `should show snackbar when scan is error`() {
        runTest {
            val results = mutableListOf<String>()
            val job = launch(testDispatcher) {
                homeVieWModel.snackbarContent.toList(results)
            }
            homeVieWModel.onScanResponse(data = "some data")

            val expected = "No se detectó ningún código QR valido"
            assert(expected == results.last())
            job.cancel()
        }
    }

}