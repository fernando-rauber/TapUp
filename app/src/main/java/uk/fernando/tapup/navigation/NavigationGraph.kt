package uk.fernando.tapup.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import uk.fernando.tapup.screen.HomePage

@ExperimentalAnimationApi
fun NavGraphBuilder.buildGraph(navController: NavController) {

    composable(Directions.home.path) {
        HomePage(navController)
    }

    composable(Directions.game.path) {

    }

    composable(Directions.score.path) {

    }

    composable(Directions.settings.path) {

    }
}