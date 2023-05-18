package com.afoxplus.home.delivery.flow

import android.app.Activity
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.spy
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class HomeFlowActionTest {

    private val mockActivity: Activity = mock()

    @Test
    fun `GIVEN a flow WHEN implemented flow THEN executed action`() {
        //GIVEN
        val sutHomeFlowAction: HomeFlowAction = spy()

        //WHEN
        sutHomeFlowAction.goToHome(mockActivity)

        //THEN
        assertNotNull(sutHomeFlowAction)
        verify(sutHomeFlowAction, times(numInvocations = 1)).goToHome(mockActivity)
    }
}