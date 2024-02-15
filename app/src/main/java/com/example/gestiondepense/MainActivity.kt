package com.example.gestiondepense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestiondepense.viewmodel.UserProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gestiondepense.ui.screens.ExpensesListScreen
import com.example.gestiondepense.ui.screens.UserProfileCreationScreen
import com.example.gestiondepense.ui.screens.UserProfileScreen
import com.example.gestiondepense.ui.theme.GestionDepenseTheme
import androidx.compose.ui.Modifier
import com.example.gestiondepense.viewmodel.ExchangeRateViewModel
import com.example.gestiondepense.viewmodel.ExpenseViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestionDepenseTheme {
                val userProfileViewModel: UserProfileViewModel = viewModel()
                val exchangeRateViewModel: ExchangeRateViewModel = viewModel()
                val expenseViewModel: ExpenseViewModel = viewModel()
                val hasProfile by userProfileViewModel.hasProfile.collectAsState()

                AppNavigation(userProfileViewModel,exchangeRateViewModel, expenseViewModel, hasProfile)
            }
        }
    }
}


@Composable
fun AppNavigation(userProfileViewModel: UserProfileViewModel,exchangeRateViewModel: ExchangeRateViewModel, expenseViewModel: ExpenseViewModel, hasProfile: Boolean) {
    val navController = rememberNavController()
    val startDestination = if (hasProfile) "mainScreen" else "profileCreation"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("profileCreation") {
            UserProfileCreationScreen(
                userProfileViewModel = userProfileViewModel,
                exchangeRateViewModel = exchangeRateViewModel,
                onProfileCreated = { navController.navigate("mainScreen/expensesList") { popUpTo("profileCreation") { inclusive = true } } }
            )
        }
        composable("mainScreen/{screen}") { backStackEntry ->
            MainScreen(navController = navController, userProfileViewModel, exchangeRateViewModel, expenseViewModel, backStackEntry.arguments?.getString("screen"))
        }
    }
}


@Composable
fun MainScreen(navController: NavController,userProfileViewModel: UserProfileViewModel, exchangeRateViewModel: ExchangeRateViewModel, expenseViewModel: ExpenseViewModel, screen: String?) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when(screen) {
                "expensesList" -> ExpensesListScreen(navController, expenseViewModel)
                "profile" -> UserProfileScreen(userProfileViewModel, exchangeRateViewModel)
                else -> Text("Écran non trouvé")
            }
        }
    }
}



@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.List, "Dépenses") },
            label = { Text("Dépenses") },
            selected = navController.currentDestination?.route == "mainScreen/expensesList",
            onClick = { navController.navigate("mainScreen/expensesList") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountCircle, "Profil") },
            label = { Text("Profil") },
            selected = navController.currentDestination?.route == "mainScreen/profile",
            onClick = { navController.navigate("mainScreen/profile") }
        )
    }
}