package com.nplusone.m4.ui.components

import androidx.navigation.NavDestination
import androidx.navigation.NavHostController

data class QuarterScreen(
    val id:Int,
    val navCtrl:NavHostController,
    val tabDestinations: List<Destinations>,
    val currentDestination:NavDestination?
)