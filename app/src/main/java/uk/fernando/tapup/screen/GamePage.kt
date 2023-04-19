package uk.fernando.tapup.screen

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import uk.fernando.tapup.R
import uk.fernando.tapup.components.SimpleCard
import uk.fernando.tapup.ext.timerFormat
import uk.fernando.tapup.navigation.Directions
import uk.fernando.tapup.util.GameStatus
import uk.fernando.tapup.viewmodel.GameViewModel
import uk.fernando.util.component.MyAnimatedVisibility
import uk.fernando.util.component.MyButton
import uk.fernando.util.component.MyDialog
import uk.fernando.util.component.MyIconButton
import uk.fernando.util.ext.clickableSingle
import uk.fernando.util.ext.safeNav
import java.util.*

@Composable
fun GamePage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: GameViewModel = hiltViewModel(),
) {
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.startGame()
    }

    Box(Modifier.fillMaxSize()) {
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
                            when (status) {
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

        // Dialogs
        DialogResult(
            viewModel = viewModel,
            isSoundEnable = true,
            onClose = { score ->
                navController.popBackStack()
                navController.safeNav(Directions.score.withArgs("$score"))
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
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 3.dp),
                painter = painterResource(R.drawable.img_x),
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
                .clickableSingle { onNumberSelected(number) }) {
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

@Composable
fun DialogResult(
    viewModel: GameViewModel,
    isSoundEnable: Boolean,
    onClose: (Int) -> Unit,
) {
    MyAnimatedVisibility(viewModel.gameStatus.value == GameStatus.END_GAME) {

//        LaunchedEffect(Unit) { MediaPlayer.create(context, if (level.star > 0) R.raw.sound_finish else R.raw.sound_game_over).playAudio(isSoundEnable) }

//                fullScreenAd.showAdvert()

        MyDialog {
            Column(
                Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = CenterHorizontally
            ) {

                // Title
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(60.dp)
                        .offset(y = -(30).dp),
                    color = MaterialTheme.colorScheme.primary,
                    shadowElevation = 4.dp,
                    shape = CutCornerShape(15),
                    border = BorderStroke(2.dp, Color.White.copy(0.2f))
                ) {
                    Box(Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Center),
                            text = stringResource(R.string.well_done).uppercase(Locale.ENGLISH),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                SimpleCard(
                    modifier = Modifier.padding(vertical = 35.dp),
                    title = R.string.score,
                    value = viewModel.lastNumberSelected.value
                )

                MyButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .defaultMinSize(minHeight = 50.dp),
                    onClick = { onClose(viewModel.lastNumberSelected.value) },
                    text = stringResource(R.string.see_score)
                )
            }
        }
    }
}