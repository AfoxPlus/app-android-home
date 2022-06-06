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
    fun `Given a flow When implemented flow Then executed action`() {

        val sutHomeFlowAction: HomeFlowAction = spy()

        sutHomeFlowAction.goToHome(mockActivity)

        assertNotNull(sutHomeFlowAction)
        verify(sutHomeFlowAction, times(numInvocations = 1)).goToHome(mockActivity)
    }
}