package uk.fernando.tapup.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import uk.fernando.tapup.navigation.Directions
import uk.fernando.tapup.navigation.buildGraph
import uk.fernando.tapup.theme.TapUpTheme


@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val controller = rememberNavController()

            TapUpTheme {

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
