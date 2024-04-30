package com.afoxplus.home.delivery.views.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(onClickScan: () -> Unit, restaurantsContent: @Composable () -> Unit) {
    UIKitTheme {
        Scaffold(
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onClickScan() },
                    shape = CircleShape,
                    containerColor = UIKitColorTheme.primaryColor,
                    contentColor = UIKitColorTheme.light01
                ) {
                    UIKitIcon(icon = UIKitIconTheme.icon_whatsapp_outline)
                }
            },
            topBar = { TopAppBar(title = { UIKitText(text = stringResource(id = R.string.home_title)) }) }) {
            Box(modifier = Modifier.padding(it)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = UIKitTheme.spacing.spacing16)
                ) {
                    item {
                        restaurantsContent()
                    }
                }

            }
        }
    }
}