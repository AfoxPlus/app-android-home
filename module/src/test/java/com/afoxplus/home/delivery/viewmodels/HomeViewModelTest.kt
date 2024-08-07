package com.afoxplus.home.delivery.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.afoxplus.home.usecases.SetRestaurantToCreateOrder
import com.afoxplus.home.utils.TestCoroutineRule
import com.afoxplus.home.utils.UIKitCoroutineDispatcherTest
import com.afoxplus.uikit.bus.UIKitEventBusWrapper
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockProductEventBus: UIKitEventBusWrapper = mock()

    private val mockSetRestaurantToCreateOrder: SetRestaurantToCreateOrder = mock()

    private lateinit var sutHomeVieWModel: HomeViewModel

    private lateinit var coroutines: UIKitCoroutineDispatcher

    @Before
    fun setup() {
        coroutines = UIKitCoroutineDispatcherTest()
        sutHomeVieWModel = HomeViewModel(
            mockProductEventBus,
            mockSetRestaurantToCreateOrder,
            coroutines
        )
    }


    @Test
    fun `should set restaurant to context from scan data`() {
        runTest {
            val data = "{restaurant}"
            sutHomeVieWModel.onScanResponse(data)
            verify(mockSetRestaurantToCreateOrder).invoke(data)
        }
    }

}