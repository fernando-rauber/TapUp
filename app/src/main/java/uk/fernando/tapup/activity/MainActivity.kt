package uk.fernando.tapup.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.fernando.tapup.R
import uk.fernando.tapup.navigation.Directions
import uk.fernando.tapup.navigation.buildGraph
import uk.fernando.tapup.service.MusicService
import uk.fernando.tapup.theme.TapUpTheme
import uk.fernando.uikit.component.UpdateStatusBar


@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val controller = rememberNavController()

            UpdateStatusBar(Color.Black)

            TapUpTheme {

                Box {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(R.drawable.bg_night),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    NavHost(
                        navController = controller,
                        startDestination = Directions.home.path
                    ) {
                        buildGraph(controller)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(MusicService.PLAY_HOME_MUSIC)
        intent.setPackage(packageName)
        startService(intent)
    }

    override fun onStop() {
        super.onStop()
        stopService(Intent(this, MusicService::class.java))
    }
}