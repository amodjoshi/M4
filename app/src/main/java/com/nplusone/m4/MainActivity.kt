package com.nplusone.m4

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nplusone.m4.ui.components.Destinations
import com.nplusone.m4.ui.components.Home0
import com.nplusone.m4.ui.components.Home1
import com.nplusone.m4.ui.components.Home2
import com.nplusone.m4.ui.components.Home3
import com.nplusone.m4.ui.components.Incorrects0
import com.nplusone.m4.ui.components.Incorrects1
import com.nplusone.m4.ui.components.Incorrects2
import com.nplusone.m4.ui.components.Incorrects3
import com.nplusone.m4.ui.components.MultiGameScreen
import com.nplusone.m4.ui.components.QuarterScreen
import com.nplusone.m4.ui.components.ScreenDim
import com.nplusone.m4.ui.components.Settings0
import com.nplusone.m4.ui.components.Settings1
import com.nplusone.m4.ui.components.Settings2
import com.nplusone.m4.ui.components.Settings3
import com.nplusone.m4.ui.settings.TAG
import com.nplusone.m4.ui.theme.M4Theme
import com.nplusone.m4.ui.viewmodel.GameViewModel

// Jan 8,2024 - v1.0 - Released it to Anvir
// Jan 8,2024 - v1.1 - Fixed issue where quantity tap was not working

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "Calling onCreate...")
        super.onCreate(savedInstanceState)
        val displayMetrics: DisplayMetrics = getResources().getDisplayMetrics()
        ScreenDim.dpHeight = displayMetrics.heightPixels / displayMetrics.density
        ScreenDim.dpWidth = displayMetrics.widthPixels / displayMetrics.density

        val screens = listOf(0, 1, 2, 3)
        val homes = listOf<Destinations>(Home0, Home1, Home2, Home3)
        val settings = listOf<Destinations>(Settings0, Settings1, Settings2, Settings3)
        val incorrects = listOf<Destinations>(Incorrects0, Incorrects1, Incorrects2, Incorrects3)
        var qs = mutableListOf<QuarterScreen>()
        var gvms = mutableListOf<GameViewModel>()
        for (screen in screens) {
            gvms.add(GameViewModel(screen))
        }

        setContent {
            M4Theme {
                for (screen in screens) {
                    // Controller per screen
                    val navController = rememberNavController()
                    val currentBackStack by navController.currentBackStackEntryAsState()
                    //Log.d(TAG, "currentBackStack = $currentBackStack")
                    //Log.d(TAG, "destination = ${currentBackStack?.destination}")
                    val currentQs = QuarterScreen(
                        id = screen,
                        navCtrl = navController,
                        currentDestination = currentBackStack?.destination,
                        tabDestinations = listOf(
                            homes[screen],
                            settings[screen],
                            incorrects[screen]
                        )
                    )
                    if (qs.size != 4) {
                        qs.add(currentQs)
                    } else {
                        qs[screen] = currentQs
                    }
                }
                Log.d(TAG, "Calling MultiGameScreen...")

                // Root composable
                MultiGameScreen(
                    quarterScreens = qs,
                    gvms = gvms
                )
            }
        }
    }
}

