package com.afoxplus.home.delivery.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afoxplus.home.usecases.actions.SetDataToContext
import com.afoxplus.home.utils.TestCoroutineRule
import com.afoxplus.home.utils.UIKitCoroutineDispatcherTest
import com.afoxplus.restaurants.delivery.flow.RestaurantBridge
import com.afoxplus.restaurants.entities.RegistrationState
import com.afoxplus.restaurants.entities.Restaurant
import com.afoxplus.uikit.bus.UIKitEventBusWrapper
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockProductEventBus: UIKitEventBusWrapper = mock()

    private val mockRestaurantBridge: RestaurantBridge = mock()

    private val mockSetDataToContext: SetDataToContext = mock()

    private lateinit var sutHomeVieWModel: HomeViewModel

    private lateinit var coroutines: UIKitCoroutineDispatcher

    @Before
    fun setup() {
        coroutines = UIKitCoroutineDispatcherTest()
        sutHomeVieWModel = HomeViewModel(
            mockProductEventBus,
            mockRestaurantBridge,
            mockSetDataToContext,
            coroutines
        )
    }

    private val mockRestaurant: Restaurant = Restaurant(
        code = "123",
        name = "Viky",
        description = "description",
        urlImageLogo = "",
        registrationState = RegistrationState("", ""),
        itemViewType = 0
    )

    @Test
    fun lestGo() {
        //Given
        println("Here - T1")
        val restaurantData: LiveData<Restaurant> = MutableLiveData<Restaurant>(mockRestaurant)
        whenever(mockRestaurantBridge.fetchRestaurant()).doReturn(restaurantData)

        //When
        println("Here - T2")
        val asd = sutHomeVieWModel.homeRestaurantClicked.observeForever {
            println("Here - inside: $it")
        }
        println("Here - asd:$asd")
        /*sutHomeVieWModel.homeRestaurantClicked

        //Then
        println("Here - T3")

        assertNotNull(asd)
        println("Here - T4")
        verify(mockRestaurantBridge, times(numInvocations = 1)).fetchRestaurant()*/
    }

    @Test
    fun `should execute use case when call onScanResponse`() = runTest {
        val data = ""
        sutHomeVieWModel.onScanResponse(data)
        verify(mockSetDataToContext).invoke(data)
    }


}