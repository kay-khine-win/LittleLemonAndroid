package com.littlelemon.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationComposable(navController: NavHostController, isLoggedIn: Boolean, menuItems: List<MenuItemRoom>) {
//fun NavigationComposable(navController: NavHostController, isLoggedIn: Boolean) {

    val startDestination = if (isLoggedIn) "Home" else "Onboarding"
    NavHost(navController = navController, startDestination = startDestination) {
        composable("Onboarding") {
            Onboarding(navController)
        }

        composable("Home") {
               Home(navController, menuItems)

        }

        composable("Profile") {
            Profile(navController)
        }
    }
}