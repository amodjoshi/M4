package com.nplusone.m4.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nplusone.m4.datalayer.Mapper
import com.nplusone.m4.datalayer.Util
import com.nplusone.m4.navigateSingleTopTo
import com.nplusone.m4.ui.home.HomeScreen
import com.nplusone.m4.ui.incorrects.IncorrectsScreen
import com.nplusone.m4.ui.settings.SettingsScreen
import com.nplusone.m4.ui.settings.TAG
import com.nplusone.m4.ui.viewmodel.GameViewModel
import com.nplusone.m4.ui.viewmodel.Quantity

// Pass the view model to the MultiGameScreen
// and access the uistate
@Composable
fun MultiGameScreen(
    quarterScreens: List<QuarterScreen>,
    gvms: List<GameViewModel>
) {
    // Game UI State
    val gus0 by gvms[0].uiState.collectAsState()
    val gus1 by gvms[1].uiState.collectAsState()
    val gus2 by gvms[2].uiState.collectAsState()
    val gus3 by gvms[3].uiState.collectAsState()

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier.graphicsLayer {
                    this.rotationZ = if (gus0.isRotate) 180f else 0f
                }
            ) {
                GameScreen(
                    myTabRowScreens = quarterScreens[0].tabDestinations,
                    navController = quarterScreens[0].navCtrl,
                    currentDestination = quarterScreens[0].currentDestination,
                    gvms[0]
                )
            }
            Surface(
                modifier = Modifier.graphicsLayer {
                    this.rotationZ = if (gus1.isRotate) 180f else 0f
                }
            ) {
                GameScreen(
                    myTabRowScreens = quarterScreens[1].tabDestinations,
                    navController = quarterScreens[1].navCtrl,
                    currentDestination = quarterScreens[1].currentDestination,
                    gvms[1]
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Surface(
                modifier = Modifier.graphicsLayer {
                    this.rotationZ = if (gus2.isRotate) 180f else 0f
                }
            ) {
                GameScreen(
                    myTabRowScreens = quarterScreens[2].tabDestinations,
                    navController = quarterScreens[2].navCtrl,
                    currentDestination = quarterScreens[2].currentDestination,
                    gvms[2]
                )
            }
            Surface(
                modifier = Modifier.graphicsLayer {
                    this.rotationZ = if (gus3.isRotate) 180f else 0f
                }
            ) {
                GameScreen(
                    myTabRowScreens = quarterScreens[3].tabDestinations,
                    navController = quarterScreens[3].navCtrl,
                    currentDestination = quarterScreens[3].currentDestination,
                    gvms[3]
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    myTabRowScreens: List<Destinations>,
    navController: NavHostController,
    currentDestination: NavDestination?,
    gvm: GameViewModel
) {
    // Game UI State
    val gus by gvm.uiState.collectAsState()

    Log.d(TAG, "GameScreen:currentDestination = $currentDestination")
    Log.d(TAG, "GameScreen:route = ${currentDestination?.route}")
    val currentScreen = myTabRowScreens
        .find { it.route == currentDestination?.route }
        ?: myTabRowScreens[0]

    Scaffold(
        bottomBar = {
            MyTabRow(
                allScreens = myTabRowScreens,
                onTabSelected = { newScreen ->
                    navController.navigateSingleTopTo(newScreen.route)
                },
                currentScreen = currentScreen
            )
        },
        modifier = Modifier.size(
            width = Dp(ScreenDim.dpWidth / 2),
            height = Dp(ScreenDim.dpHeight / 2)
        )
    ) { innerPadding ->
        Row(
            modifier = Modifier.size(
                width = Dp(ScreenDim.dpWidth / 2),
                height = Dp(ScreenDim.dpHeight / 2)
            ),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavHost(
                navController = navController,
                startDestination = myTabRowScreens[1].route,
                modifier = Modifier
                    .padding(innerPadding)
                    .size(
                        width = Dp(ScreenDim.dpWidth / 2),
                        height = Dp(ScreenDim.dpHeight / 2)
                    )
            ) {
                // Builder parameter will be defined here as the graph.
                // The builder parameter expects a function, so Navigation
                // Compose provides the NavGraphBuilder.composable extension
                // function to easily add individual composable destinations
                // to the navigation graph and define the necessary navigation
                // information.
                composable(route = myTabRowScreens[0].route) {
                    HomeScreen(
                        gus = gus,
                        onClickHandler = {
                            val homeBundle = Mapper.mcqButtonClickFlow(gus, it)
                            gvm.onGenerateClick(homeBundle)
                            // Is it the end?
                            val target = Util.decodeQ(gus.quantity)
                            Log.d(
                                TAG,
                                "onClickHandler:id=${gus.id},target=$target,totAtt=${homeBundle.totalAttempted}"
                            )
                            if (homeBundle.totalAttempted == target) {// Stop
                                gvm.onStartToggle(false)
                            }
                        }
                    )
                }
                composable(route = myTabRowScreens[1].route) {
                    SettingsScreen(
                        gus = gus,
                        onNavToHome = { value ->
                            gvm.onStartToggle(value)
                            if (value) {
                                val homeBundle = Mapper.startFlow(gus)
                                gvm.onGenerateClick(homeBundle)
                                navController.navigate(myTabRowScreens[0].route)
                            }
                        },
                        onOpChange = { opValue ->
                            gvm.onOpChange(opValue)
                        },
                        onAddSubDigitsChange = { digits ->
                            gvm.onAddSubDigitsChange(digits)
                        },
                        onOverflowClick = { overflowVal ->
                            gvm.onOverflowClick(overflowVal)
                        },
                        onHorizClick = { hOrV ->
                            gvm.onHorizClick(hOrV)
                        },
                        onEnglishClick = { enOrMr ->
                            gvm.onEnglishClick(enOrMr)
                        },
                        onQuantityClick = { quantityVal ->
                            gvm.onQuantityClick(quantityVal)
                        },
                        onRotateClick = { rotateVal ->
                            gvm.onRotateClick(rotateVal)
                        },
                        onPadasSelected = { padasVal ->
                            gvm.onPadasClick(padasVal)
                        },
                        onNextQuanClick = {
                            gvm.onNextQuanClick(it)
                        },
                        onMulSubSkillChange = {
                            gvm.onMulSubSkillChange(it)
                        },
                        onDivSubSkillChange = {
                            gvm.onDivSubSkillChange(it)
                        },
                        onDivisorChange = {
                            gvm.onDivisorChange(it)
                        },
                        onLogClick = {
                            gvm.onLoggerClick(!gus.isLoggerOn)
                        }
                    )
                }
                composable(route = myTabRowScreens[2].route) {
                    gvm.onUpdateIncorrects(gus.incorrectsRepo)
                    //Log.d(TAG,"${gus.incorrectsRepo.toString()}")
                    IncorrectsScreen(gus = gus)
                }
            }
        }
    }
}
