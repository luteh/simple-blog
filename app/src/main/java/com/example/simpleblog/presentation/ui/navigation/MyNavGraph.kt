package com.example.simpleblog.presentation.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.simpleblog.presentation.feature.NavGraphs
import com.example.simpleblog.presentation.feature.destinations.DetailScreenDestination
import com.example.simpleblog.presentation.feature.destinations.HomeScreenDestination
import com.example.simpleblog.presentation.feature.destinations.PostFormScreenDestination
import com.example.simpleblog.presentation.feature.detail.DetailScreen
import com.example.simpleblog.presentation.feature.home.HomeScreen
import com.example.simpleblog.presentation.feature.postform.PostFormScreen
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalMaterialApi
@InternalCoroutinesApi
@Composable
fun MyNavGraph() {
    val navController = rememberNavController()
    DestinationsNavHost(
        navController = navController,
        navGraph = NavGraphs.root,
        startRoute = NavGraphs.root.startRoute
    ) {
        composable(HomeScreenDestination) {
            HomeScreen(
                destinationsNavigator, navController
            )
        }
        composable(
            DetailScreenDestination
        ) {
            DetailScreen(
                navArgs, destinationsNavigator, navController
            )
        }
        composable(
            PostFormScreenDestination
        ) {
            PostFormScreen(
                navArgs, destinationsNavigator, navController
            )
        }
    }
}