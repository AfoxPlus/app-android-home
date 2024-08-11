package com.afoxplus.home.domain.usecases

import com.afoxplus.home.cross.utils.RESTAURANT_NAME
import com.afoxplus.home.cross.utils.RESTAURANT_NO_TABLE
import com.afoxplus.home.cross.utils.RESTAURANT_ORDER_TYPE
import com.afoxplus.home.cross.utils.RESTAURANT_OWN_DELIVERY
import com.afoxplus.home.domain.entities.RestaurantOrderType
import com.afoxplus.restaurants.entities.RegistrationState
import com.afoxplus.restaurants.entities.Restaurant
import com.afoxplus.restaurants.usecases.FindAndSetToContextRestaurantUseCase
import com.afoxplus.restaurants.usecases.actions.SetToContextRestaurant
import com.afoxplus.uikit.objects.vendor.PaymentMethod
import com.afoxplus.uikit.objects.vendor.Vendor
import com.afoxplus.uikit.objects.vendor.VendorShared
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


class SetRestaurantToCreateOrderTest {

    private val setToContextRestaurant: SetToContextRestaurant = mock()
    private val findRestaurantAndSetToContext: FindAndSetToContextRestaurantUseCase = mock()
    private val vendorShared: VendorShared = mock()

    private lateinit var setRestaurantToCreateOrder: SetRestaurantToCreateOrder

    @Before
    fun setup() {
        setRestaurantToCreateOrder = SetRestaurantToCreateOrder(
            setToContextRestaurant,
            findRestaurantAndSetToContext,
            vendorShared
        )
    }

    @Test
    fun `should set restaurant to create order from object`() {
        runTest {

            val orderType = RestaurantOrderType.TABLE
            setRestaurantToCreateOrder.invoke(restaurant, orderType)

            val vendor = Vendor(
                tableId = RESTAURANT_NO_TABLE,
                restaurantId = restaurant.code,
                additionalInfo = mutableMapOf(
                    RESTAURANT_NAME to restaurant.name,
                    RESTAURANT_OWN_DELIVERY to restaurant.ownDelivery,
                    RESTAURANT_ORDER_TYPE to orderType.name
                ),
                paymentMethod = restaurant.paymentMethods.map {
                    PaymentMethod(it.id, it.paymentName, it.isSelected)
                }
            )
            verify(setToContextRestaurant).invoke(restaurant)
            verify(vendorShared).save(vendor)
        }
    }

    @Test
    fun `should set restaurant to create order from scan`() {
        runTest {
            val vendor = Vendor(tableId = "", restaurantId = "123")
            whenever(findRestaurantAndSetToContext.invoke("123")).thenReturn(restaurant)
            setRestaurantToCreateOrder.invoke(vendor)
            verify(vendorShared).save(any())
        }
    }

    private val restaurant = Restaurant(
        code = "ASD123",
        name = "Sal y pimienta",
        description = "Almuerzos y cena",
        urlImageLogo = "",
        ownDelivery = false,
        paymentMethods = emptyList(),
        itemViewType = 0,
        registrationState = RegistrationState("COD23", "NEW")
    )
}