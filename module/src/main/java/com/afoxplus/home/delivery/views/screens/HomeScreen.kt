package com.afoxplus.home.delivery.views.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.afoxplus.home.R
import com.afoxplus.home.delivery.viewmodels.HomeViewModel
import com.afoxplus.uikit.designsystem.atoms.UIKitIcon
import com.afoxplus.uikit.designsystem.atoms.UIKitText
import com.afoxplus.uikit.designsystem.foundations.UIKitColorTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitIcon as IconFoundation
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTypographyTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    viewModel: HomeViewModel,
    onClickScan: () -> Unit,
    ordersStatusContent: @Composable () -> Unit = {},
    restaurantsContent: @Composable () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onClickScan() },
                shape = CircleShape,
                containerColor = UIKitColorTheme.primaryColor,
                contentColor = UIKitColorTheme.light01
            ) {
                UIKitIcon(icon = IconFoundation.Icon(R.drawable.icon_scan))
            }
        },
        topBar = {
            TopAppBar(title = {
                UIKitText(
                    text = stringResource(id = R.string.home_title),
                    style = UIKitTypographyTheme.header04Bold
                )
            })
        },
        content = {
            LazyColumn(
                contentPadding = it,
                modifier = Modifier.fillMaxSize()
            ) {
                item { ordersStatusContent() }
                item { restaurantsContent() }
            }
        }
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.snackbarContent.collectLatest { message ->
            snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Long)
        }
    }
}