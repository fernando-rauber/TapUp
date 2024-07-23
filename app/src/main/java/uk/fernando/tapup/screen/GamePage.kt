package uk.fernando.tapup.screen

import android.content.Intent
import android.media.MediaPlayer
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import uk.fernando.advertising.AdInterstitial
import uk.fernando.tapup.R
import uk.fernando.tapup.activity.MainActivity
import uk.fernando.tapup.components.SimpleCard
import uk.fernando.tapup.ext.timerFormat
import uk.fernando.tapup.navigation.Directions
import uk.fernando.tapup.service.MusicService
import uk.fernando.tapup.theme.*
import uk.fernando.tapup.util.GameStatus
import uk.fernando.tapup.viewmodel.GameViewModel
import uk.fernando.uikit.component.MyAnimatedVisibility
import uk.fernando.uikit.component.MyDialog
import uk.fernando.uikit.component.MyIconButton
import uk.fernando.uikit.component.MyImageButton
import uk.fernando.uikit.event.OnLifecycleEvent
import uk.fernando.uikit.ext.clickableSingle
import uk.fernando.uikit.ext.playAudio
import uk.fernando.uikit.ext.safeNav
import java.util.*

@Composable
fun GamePage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: GameViewModel = hiltViewModel(),
) {
    val activity = LocalContext.current as MainActivity
    val coroutine = rememberCoroutineScope()
    val fullScreenAd = AdInterstitial(activity, stringResource(R.string.ad_interstitial_end_level))
    val soundCorrect = MediaPlayer.create(LocalContext.current, R.raw.sound_correct)
    val soundWrong = MediaPlayer.create(LocalContext.current, R.raw.wrong)

    OnLifecycleEvent { _, event ->
        if (viewModel.isSoundEnable.value) {
            if (event == Lifecycle.Event.ON_CREATE) {
                val intent = Intent(MusicService.PLAY_GAME_MUSIC)
                intent.setPackage(activity.packageName)
                activity.startService(intent)
            }
            if (event == Lifecycle.Event.ON_STOP) {
                val intent = Intent(MusicService.PLAY_HOME_MUSIC)
                intent.setPackage(activity.packageName)
                activity.startService(intent)
            }
        }
    }

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

            // Rotating numbers
            SlideNumbers(
                viewModel = viewModel,
                onNumberSelected = {
                    coroutine.launch {
                        viewModel.onNumberClick(it).collect { status ->
                            when (status) {
                                GameStatus.CORRECT -> soundCorrect.playAudio()
                                GameStatus.WRONG -> soundWrong.playAudio()
                                else -> {}
                            }
                        }
                    }
                }
            )
        }

        Timer(Modifier.align(BottomCenter), viewModel)

        // Dialogs
        DialogResult(
            viewModel = viewModel,
            fullScreenAd = fullScreenAd,
            onReplayClick = viewModel::replay,
            onScoreClick = {
                navController.popBackStack()
                navController.safeNav(Directions.score.path)
            }
        )
    }
}

@Composable
private fun TopBar(viewModel: GameViewModel, onCloseClick: () -> Unit) {
    Box(Modifier.fillMaxWidth()) {

        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.bg_header),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )

        Text(
            modifier = Modifier
                .align(BottomCenter)
                .offset(y = (-15).dp),
            text = viewModel.lastNumberSelected.value.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = game_orange
        )

        MyIconButton(
            icon = R.drawable.icon_exit,
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = onCloseClick,
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun MistakesLeft(viewModel: GameViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..viewModel.mistakeLeft.value) {
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 3.dp),
                painter = painterResource(R.drawable.icon_exit),
                contentDescription = null,
                tint = game_red
            )
        }
    }
}

@Composable
private fun SlideNumbers(viewModel: GameViewModel, onNumberSelected: (Int) -> Unit) {

    AnimatedContent(
        targetState = viewModel.currentNumber.value,
        modifier = Modifier.fillMaxSize(),
        transitionSpec = {
            ((slideInHorizontally { height -> height } + fadeIn()).togetherWith(slideOutHorizontally { height -> -height } + fadeOut())).using(
                SizeTransform(clip = false)
            )
        },
        label = ""
    ) { number ->
        Box(
            Modifier
                .fillMaxWidth()
                .clickableSingle(ripple = false) { onNumberSelected(number) }
        ) {
            Text(
                modifier = Modifier.align(Center),
                text = "$number",
                fontFamily = myFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 70.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun Timer(modifier: Modifier, viewModel: GameViewModel) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 7.dp),
            text = viewModel.chronometerSeconds.value.timerFormat(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Box(contentAlignment = CenterEnd) {

            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.progress_bar_bg),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth(((viewModel.chronometerSeconds.value * 1.666) / 100).toFloat())
                    .padding(horizontal = 5.dp),
                painter = painterResource(R.drawable.progress_bar),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )

            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(R.drawable.progress_bar_shape),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}

@Composable
fun DialogResult(
    viewModel: GameViewModel,
    fullScreenAd: AdInterstitial,
    onReplayClick: () -> Unit,
    onScoreClick: () -> Unit,
) {
    val context = LocalContext.current

    MyAnimatedVisibility(viewModel.gameStatus.value == GameStatus.END_GAME) {

        LaunchedEffect(Unit) {
            fullScreenAd.showAdvert()
            MediaPlayer.create(context, R.raw.end_game).playAudio()
        }

        MyDialog(RoundedCornerShape(10)) {

            Box(
                Modifier
                    .height(IntrinsicSize.Min)
                    .background(Brush.verticalGradient(colors = listOf(light_blue, light_blue2)), RoundedCornerShape(10))
            ) {

                // Just border
                Box(
                    Modifier
                        .fillMaxSize()
                        .border(8.dp, Color.White, RoundedCornerShape(10)),
                    content = {}
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = CenterHorizontally
                ) {

                    // Title
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(60.dp)
                            .offset(y = -(28).dp)
                    ) {
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(R.drawable.dialog_title),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                        )

                        Text(
                            modifier = Modifier.align(Center),
                            text = stringResource(R.string.well_done).uppercase(Locale.ENGLISH),
                            style = MaterialTheme.typography.titleLarge,
                            color = game_orange
                        )
                    }

                    SimpleCard(
                        modifier = Modifier.padding(top = 15.dp, bottom = 30.dp),
                        title = R.string.score,
                        value = viewModel.lastNumberSelected.value
                    )

                    Row(Modifier.padding(8.dp)) {

                        MyImageButton(
                            modifier = Modifier
                                .weight(1f)
                                .defaultMinSize(minHeight = 60.dp),
                            image = R.drawable.bt_purple,
                            textColor = Color.White,
                            trailingIcon = R.drawable.ic_replay,
                            onClick = onReplayClick
                        )

                        Spacer(Modifier.width(8.dp))

                        MyImageButton(
                            modifier = Modifier
                                .weight(1f)
                                .defaultMinSize(minHeight = 60.dp),
                            image = R.drawable.bt_yellow,
                            textColor = purple,
                            trailingIcon = R.drawable.ic_trophy,
                            onClick = onScoreClick
                        )
                    }
                }
            }
        }
    }
}