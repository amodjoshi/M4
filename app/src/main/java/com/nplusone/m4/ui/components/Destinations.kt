package com.nplusone.m4.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Contract for information needed on every App navigation destination
 */
interface Destinations {
    val icon: ImageVector
    val route: String
}


/**
 * App navigation destinations
 */
object Home0 : Destinations {
    override val icon = Icons.Filled.Home
    override val route = "home0"
}

object Settings0 : Destinations {
    override val icon = Icons.Filled.Settings
    override val route = "settings0"
}

object Incorrects0 : Destinations {
    override val icon = Icons.Filled.Menu
    override val route = "incorrects0"
}

/**
* App navigation destinations
*/
object Home1 : Destinations {
    override val icon = Icons.Filled.Home
    override val route = "home1"
}

object Settings1 : Destinations {
    override val icon = Icons.Filled.Settings
    override val route = "settings1"
}

object Incorrects1 : Destinations {
    override val icon = Icons.Filled.Menu
    override val route = "incorrects1"
}

/**
* App navigation destinations
*/
object Home2 : Destinations {
    override val icon = Icons.Filled.Home
    override val route = "home2"
}

object Settings2 : Destinations {
    override val icon = Icons.Filled.Settings
    override val route = "settings2"
}

object Incorrects2 : Destinations {
    override val icon = Icons.Filled.Menu
    override val route = "incorrects2"
}

/**
* App navigation destinations
*/
object Home3 : Destinations {
    override val icon = Icons.Filled.Home
    override val route = "home3"
}

object Settings3 : Destinations {
    override val icon = Icons.Filled.Settings
    override val route = "settings3"
}

object Incorrects3 : Destinations {
    override val icon = Icons.Filled.Menu
    override val route = "incorrects3"
}