package com.afoxplus.home.delivery.components

import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit

@Composable
internal fun FragmentContainer(
    modifier: Modifier = Modifier,
    fragmentManager: FragmentManager,
    commit: FragmentTransaction.(containerId: Int) -> Unit
) {
    val containerId by rememberSaveable { mutableIntStateOf(View.generateViewId()) }
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            fragmentManager.findFragmentById(containerId)?.view
                ?.also { (it.parent as? ViewGroup)?.removeView(it) }
                ?: FragmentContainerView(context)
                    .apply { id = containerId }
                    .also {
                        fragmentManager.commit { commit(it.id) }
                    }
        }
    )

    DisposableEffect(LocalLifecycleOwner.current) {
        onDispose {
            fragmentManager.findFragmentById(containerId)?.let { fragment ->
                fragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commitAllowingStateLoss()
            }
        }
    }
}