package uk.fernando.tapup.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import uk.fernando.tapup.R
import uk.fernando.tapup.components.NavigationTopBar
import uk.fernando.tapup.navigation.Directions
import uk.fernando.util.component.MyIconButton
import uk.fernando.util.ext.safeNav

@Composable
fun GamePage(navController: NavController = NavController(LocalContext.current)) {

    Box(Modifier.fillMaxSize()) {

        NavigationTopBar(
            rightIcon = {
                MyIconButton(
                    icon = R.drawable.ic_close,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { navController.popBackStack() },
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        )

        Column(
            modifier = Modifier
                .align(Center)
                .fillMaxWidth(.6f),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

        }
    }
}