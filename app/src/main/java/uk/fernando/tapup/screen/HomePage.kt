package uk.fernando.tapup.screen

import android.media.MediaPlayer
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.fernando.advertising.component.AdBanner
import uk.fernando.tapup.R
import uk.fernando.tapup.components.NavigationTopBar
import uk.fernando.tapup.navigation.Directions
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
            icon = R.drawable.icon_settings,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            onClick = { navController.safeNav(Directions.settings.path) },
            tint = Color.Unspecified
        )

        Column(
            modifier = Modifier
                .align(Center)
                .fillMaxWidth(.6f),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            MyImageButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp)
                    .defaultMinSize(minHeight = 50.dp),
                image = R.drawable.bt_purple,
                text = stringResource(R.string.play_action).uppercase(),
                onClick = { navController.safeNav(Directions.game.path) }
            )

//            MyButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .defaultMinSize(minHeight = 50.dp),
//                text = stringResource(R.string.score_action).uppercase(),
//                onClick = { navController.safeNav(Directions.score.withArgs("0")) }
//            )
            MyImageButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 50.dp),
                image = R.drawable.bt_purple,
                text = stringResource(R.string.score_action).uppercase(),
                onClick = { }
            )
        }

        Box(
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 8.dp)
        ) {
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
    fontSize: TextUnit = 23.sp,
    soundEffect: Int? = uk.fernando.uikit.R.raw.click,
    textModifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    val soundClick = if (soundEffect == null) null else MediaPlayer.create(LocalContext.current, soundEffect)

    Box(modifier = modifier.clickableSingle(ripple = false) {
        soundClick?.playAudio()

        multipleEventsCutter.processEvent(onClick)
    }) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )

        Text(
            modifier = textModifier.align(Center),
            text = text,
            color = if (enabled) textColor else Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
    }
}