package uk.fernando.tapup.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import uk.fernando.advertising.component.AdBanner
import uk.fernando.tapup.R
import uk.fernando.tapup.navigation.Directions
import uk.fernando.tapup.theme.myFontFamily
import uk.fernando.tapup.viewmodel.HomeViewModel
import uk.fernando.uikit.component.MyIconButton
import uk.fernando.uikit.component.MyImageButton
import uk.fernando.uikit.ext.safeNav

@Composable
fun HomePage(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeViewModel = hiltViewModel()
) {

    Box(Modifier.fillMaxSize()) {

        TopBar(
            isMusicEnable = viewModel.isSoundEnable.value,
            onMusicClick = viewModel::updateSound,
            onSettingsClick = { navController.safeNav(Directions.settings.path) })

        Column(
            modifier = Modifier
                .align(Center)
                .fillMaxWidth(.9f),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 22.sp)) {
                        append(stringResource(R.string.game_instructions_title))
                    }
                    append("\n\n")
                    withStyle(style = SpanStyle(fontSize = 16.sp)) {
                        append(stringResource(R.string.game_instructions))
                    }
                },
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = myFontFamily,
                lineHeight = 25.sp,
                textAlign = TextAlign.Center
            )

            MyImageButton(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 50.dp)
                    .defaultMinSize(minHeight = 60.dp),
                image = R.drawable.bt_purple,
                text = stringResource(R.string.play_action).uppercase(),
                trailingIcon = R.drawable.ic_play,
                onClick = { navController.safeNav(Directions.game.path) }
            )

            MyImageButton(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .defaultMinSize(minHeight = 60.dp),
                image = R.drawable.bt_purple,
                text = stringResource(R.string.score_action).uppercase(),
                trailingIcon = R.drawable.ic_trophy,
                onClick = { navController.safeNav(Directions.score.path) }
            )
        }

        Box(modifier = Modifier.align(BottomCenter)) {
            AdBanner(unitId = R.string.ad_banner_home)
        }
    }
}

@Composable
private fun TopBar(
    isMusicEnable: Boolean,
    onMusicClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Row(Modifier.padding(8.dp)) {
        MyIconButton(
            icon = if (isMusicEnable) R.drawable.ic_music_on else R.drawable.ic_music_off,
            modifier = Modifier,
            onClick = onMusicClick,
            tint = Color.Unspecified
        )

        Spacer(Modifier.weight(1f))

        MyIconButton(
            icon = R.drawable.ic_settings,
            modifier = Modifier,
            onClick = onSettingsClick,
            tint = Color.White.copy(0.7f)
        )
    }
}