package uk.fernando.tapup.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import uk.fernando.tapup.R
import uk.fernando.tapup.components.NavigationTopBar

@Composable
fun ScorePage(navController: NavController = NavController(LocalContext.current)) {

    Box(Modifier.fillMaxSize()) {

        NavigationTopBar(
            title = stringResource(R.string.score_action),
            onBackClick = {navController.popBackStack()}
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