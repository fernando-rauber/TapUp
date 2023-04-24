package uk.fernando.tapup.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.fernando.tapup.screen.GamePage
import uk.fernando.tapup.screen.HomePage
import uk.fernando.tapup.screen.ScorePage
import uk.fernando.tapup.screen.SettingsPage

@ExperimentalAnimationApi
fun NavGraphBuilder.buildGraph(navController: NavController) {

    composable(Directions.home.path) {
        HomePage(navController)
    }
    composable(Directions.game.path) {
        GamePage(navController)
    }
    composable(Directions.score.path) {
        ScorePage(navController)
    }
    composable(Directions.settings.path) {
        SettingsPage(navController)
    }
}