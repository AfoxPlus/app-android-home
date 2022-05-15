package com.afoxplus.home.delivery.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afoxplus.home.utils.TestCoroutineRule
import com.afoxplus.restaurants.delivery.flow.RestaurantBridge
import com.afoxplus.restaurants.entities.RegistrationState
import com.afoxplus.restaurants.entities.Restaurant
import com.afoxplus.uikit.bus.EventBusListener
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockProductEventBus: EventBusListener = mock()

    private val mockRestaurantBridge: RestaurantBridge = mock()

    private val mockDispatcher: CoroutineDispatcher by lazy { Dispatchers.Main }

    private lateinit var sutHomeVieWModel: HomeViewModel

    @Before
    fun setup() {
        sutHomeVieWModel = HomeViewModel(mockProductEventBus, mockRestaurantBridge, mockDispatcher)
    }

    private val mockRestaurant: Restaurant = Restaurant(
        "123", "Viky", "description", "",
        RegistrationState("", ""), 0
    )

    /*@InternalCoroutinesApi
    @Test
    fun oka() {
        testCoroutineRule.runBlockingTest {

            val restaurant: Restaurant = object : Restaurant {
                override val code: String
                    get() = "123"
                override val description: String
                    get() = "Restaurant de Doña Viky"
                override var itemViewType: Int
                    get() = 123
                    set(value) {}
                override val name: String
                    get() = "Doña Viky"
                override val registrationState: RegistrationState
                    get() = object : RegistrationState {
                        override val code: String
                            get() = "123"
                        override val state: String
                            get() = "state"

                        override fun describeContents(): Int {
                            return 0
                        }

                        override fun writeToParcel(dest: Parcel?, flags: Int) {
                            println("helouda!")
                        }

                    }
                override val urlImageLogo: String
                    get() = ""

                override fun describeContents(): Int {
                    return 1
                }

                override fun writeToParcel(dest: Parcel?, flags: Int) {
                    println("nosepo!")
                }
            }


            val flowEventBus = flow<EventBus> {
                emit(OnClickRestaurantHomeEvent.build(restaurant = restaurant))
            }

            sutHomeVieWModel = HomeViewModel(mockProductEventBus, mockDispatcher)

            whenever(mockProductEventBus.subscribe()).doReturn(flowEventBus)

            //sutHomeVieWModel.helouda()

            val eventBus = mockProductEventBus.subscribe()
            println("Here - eventBus: $eventBus")

            eventBus.collectLatest {
                println("Here - inside! $it")
                Assert.assertNotNull(it)
                *//*val algo = sutHomeVieWModel.homeRestaurantClicked.getOrAwaitValue()
                println("Here - $algo")*//*
            }

            Assert.assertTrue(true)

        }
    }*/

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


}