package uk.fernando.tapup.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface NavigationCommand {
    val path: String
    val arguments: List<NamedNavArgument>

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    fun withArgsFormat(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach { arg ->
                append("/{$arg}")
            }
        }
    }
}

object Directions {

    val home = object : NavigationCommand {
        override val path: String
            get() = "home"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    val game = object : NavigationCommand {
        override val path: String
            get() = "game"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }

    val score = object : NavigationCommand {
        override val path: String
            get() = "score"
        override val arguments: List<NamedNavArgument>
            get() = listOf(
                navArgument(SCORE) { type = NavType.StringType }
            )
    }

    val settings = object : NavigationCommand {
        override val path: String
            get() = "settings"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }
}

const val SCORE = "score"