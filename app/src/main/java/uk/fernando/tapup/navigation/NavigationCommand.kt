package uk.fernando.tapup.navigation

import androidx.navigation.NamedNavArgument

interface NavigationCommand {
    val path: String
    val arguments: List<NamedNavArgument>
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
            get() = emptyList()
    }

    val settings = object : NavigationCommand {
        override val path: String
            get() = "settings"
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
    }
}