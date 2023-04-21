package uk.fernando.tapup.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.fernando.tapup.R
import uk.fernando.tapup.navigation.Directions
import uk.fernando.tapup.navigation.buildGraph
import uk.fernando.tapup.theme.TapUpTheme
import uk.fernando.tapup.theme.background
import uk.fernando.uikit.component.UpdateStatusBar


@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val controller = rememberNavController()

            UpdateStatusBar(background)

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
}