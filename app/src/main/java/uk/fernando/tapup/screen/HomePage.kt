package uk.fernando.tapup.screen

import android.media.MediaPlayer
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.fernando.advertising.component.AdBanner
import uk.fernando.tapup.R
import uk.fernando.tapup.navigation.Directions
import uk.fernando.tapup.theme.myFontKaph
import uk.fernando.uikit.component.MyIconButton
import uk.fernando.uikit.event.MultipleEventsCutter
import uk.fernando.uikit.event.get
import uk.fernando.uikit.ext.clickableSingle
import uk.fernando.uikit.ext.playAudio
import uk.fernando.uikit.ext.safeNav

@Composable
fun HomePage(navController: NavController = NavController(LocalContext.current)) {

    Box(Modifier.fillMaxSize()) {

        MyIconButton(
            icon = R.drawable.ic_settings,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            onClick = { navController.safeNav(Directions.settings.path) },
            tint = Color.White.copy(0.7f)
        )

        Column(
            modifier = Modifier
                .align(Center)
                .fillMaxWidth(.9f),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(R.string.game_instructions),
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = myFontKaph,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )

            MyImageButton(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(vertical = 50.dp)
                    .defaultMinSize(minHeight = 60.dp),
                image = R.drawable.bt_purple,
                text = stringResource(R.string.play_action).uppercase(),
                trailingIcon = R.drawable.ic_play,
                onClick = { navController.safeNav(Directions.game.path) }
            )

            MyImageButton(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .defaultMinSize(minHeight = 60.dp),
                image = R.drawable.bt_purple,
                text = stringResource(R.string.score_action).uppercase(),
                trailingIcon = R.drawable.ic_trophy,
                onClick = { navController.safeNav(Directions.score.withArgs("0")) }
            )
        }

        Box(modifier = Modifier.align(BottomCenter)) {
            AdBanner(unitId = R.string.ad_banner_home)
        }
    }
}

@Composable
fun MyImageButton(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    text: String,
    enabled: Boolean = true,
    textColor: Color = Color.White,
    fontSize: TextUnit = 27.sp,
    soundEffect: Int? = uk.fernando.uikit.R.raw.click,
    textModifier: Modifier = Modifier,
    @DrawableRes trailingIcon: Int? = null,
    onClick: () -> Unit,
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    val soundClick = if (soundEffect == null) null else MediaPlayer.create(LocalContext.current, soundEffect)

    Box(modifier = modifier
        .height(IntrinsicSize.Min)
        .width(IntrinsicSize.Min)
        .clickableSingle(ripple = false) {
            soundClick?.playAudio()

            multipleEventsCutter.processEvent(onClick)
        }) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )

        Row(
            modifier = Modifier.align(Center),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (trailingIcon != null)
                Icon(
                    modifier = Modifier
                        .size(52.dp)
                        .padding(end = 10.dp),
                    painter = painterResource(trailingIcon),
                    contentDescription = null,
                    tint = textColor
                )

            Text(
                modifier = textModifier,
                text = text,
                color = if (enabled) textColor else Color.Black,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = fontSize
            )
        }
    }
}