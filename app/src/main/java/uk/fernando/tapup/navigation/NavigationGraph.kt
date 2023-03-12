package uk.fernando.tapup.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.buildGraph(navController: NavController) {

    composable(Directions.home.path) {

    }

    composable(Directions.game.path) {

    }

    composable(Directions.score.path) {

    }

    composable(Directions.settings.path) {

    }
}