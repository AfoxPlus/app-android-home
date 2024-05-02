package com.afoxplus.home.delivery.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.afoxplus.home.utils.TestCoroutineRule
import com.afoxplus.home.utils.UIKitCoroutineDispatcherTest
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    private lateinit var sutHomeVieWModel: HomeViewModel

    private lateinit var coroutines: UIKitCoroutineDispatcher

    @Before
    fun setup() {
        coroutines = UIKitCoroutineDispatcherTest()
        sutHomeVieWModel = HomeViewModel( coroutines)
    }


}