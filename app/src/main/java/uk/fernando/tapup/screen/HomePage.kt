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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import uk.fernando.tapup.components.NavigationTopBar
import uk.fernando.tapup.navigation.Directions
import uk.fernando.tapup.R
import uk.fernando.util.component.MyButton
import uk.fernando.util.component.MyIconButton
import uk.fernando.util.ext.safeNav

@Composable
fun HomePage(navController: NavController = NavController(LocalContext.current)) {

    Box(Modifier.fillMaxSize()) {

        NavigationTopBar(
            title = stringResource(R.string.app_name),
            rightIcon = {
                MyIconButton(
                    icon = R.drawable.ic_settings,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { navController.safeNav(Directions.settings.path) },
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

            MyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp)
                    .defaultMinSize(minHeight = 50.dp),
                text = stringResource(R.string.play_action).uppercase(),
                onClick = { navController.safeNav(Directions.game.path) }
            )

            MyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 50.dp),
                text = stringResource(R.string.score_action).uppercase(),
                onClick = { navController.safeNav(Directions.score.withArgs("0")) }
            )
        }
    }
}