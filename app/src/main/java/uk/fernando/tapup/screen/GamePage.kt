package uk.fernando.tapup.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import uk.fernando.tapup.R
import uk.fernando.tapup.components.NavigationTopBar
import uk.fernando.tapup.ext.timerFormat
import uk.fernando.tapup.viewmodel.GameViewModel
import uk.fernando.util.component.MyIconButton

@Composable
fun GamePage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: GameViewModel = getViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.startGame()
    }

    Column(Modifier.fillMaxSize()) {

        TopBar(
            viewModel = viewModel,
            onCloseClick = { navController.popBackStack() }
        )

        // Last selected number
        Text(
            text = "${viewModel.lastNumberSelected.value}",
            modifier = Modifier.align(CenterHorizontally),
            color = MaterialTheme.colorScheme.onBackground
        )

        // Rotating numbers
        Column(
            modifier = Modifier
                .fillMaxWidth(.6f),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


        }
    }
}

@Composable
private fun TopBar(viewModel: GameViewModel, onCloseClick: () -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(top = 5.dp, start = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.ic_timer),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = viewModel.chronometerSeconds.value.timerFormat(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        MyIconButton(
            icon = R.drawable.ic_close,
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = onCloseClick,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}