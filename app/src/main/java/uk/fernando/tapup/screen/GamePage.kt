package uk.fernando.tapup.screen

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import uk.fernando.tapup.R
import uk.fernando.tapup.ext.timerFormat
import uk.fernando.tapup.util.GameStatus
import uk.fernando.tapup.viewmodel.GameViewModel
import uk.fernando.util.component.MyIconButton

@Composable
fun GamePage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: GameViewModel = hiltViewModel()
) {

    val coroutine = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.startGame()
    }

    Column(Modifier.fillMaxSize()) {

        TopBar(
            viewModel = viewModel,
            onCloseClick = { navController.popBackStack() }
        )

        MistakesLeft(viewModel)

        // Last selected number
        Text(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(CenterHorizontally),
            text = "${viewModel.lastNumberSelected.value}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        // Rotating numbers
        SlideNumbers(
            viewModel = viewModel,
            onNumberSelected = {
                coroutine.launch {
                    viewModel.onNumberClick(it).collect { status ->
                        when(status){
                            GameStatus.CORRECT -> {

                            }
                            GameStatus.WRONG -> {

                            }
                            else -> {

                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun TopBar(viewModel: GameViewModel, onCloseClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 7.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
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

@Composable
private fun MistakesLeft(viewModel: GameViewModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = stringResource(R.string.mistakes_left),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        for (i in 1..viewModel.mistakeLeft.value) {
            Icon(
                modifier = Modifier.size(36.dp),
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SlideNumbers(viewModel: GameViewModel, onNumberSelected: (Int) -> Unit) {

    AnimatedContent(
        targetState = viewModel.currentNumber.value,
        modifier = Modifier.fillMaxSize(),
        transitionSpec = {
            (slideInHorizontally { height -> height } + fadeIn() with
                    slideOutHorizontally { height -> -height } + fadeOut()).using(
                SizeTransform(clip = false)
            )
        }
    ) { number ->

        Box(
            Modifier
                .fillMaxWidth()
                .clickable { onNumberSelected(number) }) {
            Text(
                modifier = Modifier.align(Center),
                text = "$number",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 70.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

    }
}