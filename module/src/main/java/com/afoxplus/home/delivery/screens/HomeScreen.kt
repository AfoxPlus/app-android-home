package com.afoxplus.home.delivery.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.afoxplus.home.R
import com.afoxplus.uikit.designsystem.atoms.UIKitIcon
import com.afoxplus.uikit.designsystem.atoms.UIKitText
import com.afoxplus.uikit.designsystem.foundations.UIKitColorTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitIconTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTypographyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    onClickScan: () -> Unit,
    ordersStatusContent: @Composable () -> Unit = {},
    restaurantsContent: @Composable () -> Unit
) {
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onClickScan() },
                shape = CircleShape,
                containerColor = UIKitColorTheme.primaryColor,
                contentColor = UIKitColorTheme.light01
            ) {
                UIKitIcon(icon = UIKitIconTheme.icon_scan_outline, tint = UIKitColorTheme.light01)
            }
        },
        topBar = {

            TopAppBar(title = {
                UIKitText(
                    text = stringResource(id = R.string.app_name_top_app_bar),
                    style = UIKitTypographyTheme.header04Bold
                )
            })
        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(it)
            ) {
                ordersStatusContent()
                restaurantsContent()
            }
        }
    )

}