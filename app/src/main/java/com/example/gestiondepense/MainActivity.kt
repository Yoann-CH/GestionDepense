package com.example.gestiondepense

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestiondepense.data.database.AppDatabase
import com.example.gestiondepense.data.network.client.RetrofitClient
import com.example.gestiondepense.data.repository.ExpenseRepository
import com.example.gestiondepense.data.repository.UserProfileRepository
import com.example.gestiondepense.ui.screens.ExpenseScreen
import com.example.gestiondepense.ui.screens.ExpensesListScreen
import com.example.gestiondepense.ui.screens.UserProfileCreationScreen
import com.example.gestiondepense.ui.screens.UserProfileScreen
import com.example.gestiondepense.ui.theme.GestionDepenseTheme
import com.example.gestiondepense.viewmodel.ExchangeRateViewModel
import com.example.gestiondepense.viewmodel.ExpenseViewModel
import com.example.gestiondepense.viewmodel.UserProfileViewModel
import com.example.gestiondepense.viewmodel.factory.ExchangeRateViewModelFactory
import com.example.gestiondepense.viewmodel.factory.ExpenseViewModelFactory
import com.example.gestiondepense.viewmodel.factory.UserProfileViewModelFactory

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestionDepenseTheme {
                AppNavigation()
            }
        }
        Log.d(TAG, "L'activité principale est créée.")
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavigation() {
    val appDatabase = AppDatabase.getDatabase(context = LocalContext.current)
    val userProfileRepository = UserProfileRepository(appDatabase.userProfileDao())
    val exchangeRateViewModel: ExchangeRateViewModel = viewModel(
        factory = ExchangeRateViewModelFactory(RetrofitClient.instance)
    )
    val expenseViewModel: ExpenseViewModel = viewModel(
        factory = ExpenseViewModelFactory(ExpenseRepository(appDatabase.expenseDao()))
    )
    val userProfileViewModel: UserProfileViewModel = viewModel(
        factory = UserProfileViewModelFactory(userProfileRepository)
    )
    val hasProfile by userProfileViewModel.hasProfile.collectAsState(initial = null)
    val navController = rememberNavController()
    val expenseDetails by expenseViewModel.expenseDetails.collectAsState()

    if (hasProfile == null) {
        LoadingScreen()
    } else {
        val startDestination = if (hasProfile == true) "expensesList" else "profileCreation"
        NavHost(navController = navController, startDestination = startDestination) {
            composable("profileCreation") {
                UserProfileCreationScreen(
                    userProfileViewModel = userProfileViewModel,
                    exchangeRateViewModel = exchangeRateViewModel,
                    onProfileCreated = { navController.navigate("expensesList") }
                )
            }
            composable("expensesList") {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ExpensesListScreen(navController, expenseViewModel)
                    }
                }
            }
            composable("profile") {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        UserProfileScreen(userProfileViewModel, exchangeRateViewModel)
                    }
                }
            }
            composable("addExpense") {
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ExpenseScreen(navController, expenseViewModel, null)
                    }
                }
            }
            composable("editExpense/{expenseId}") { backStackEntry ->
                val expenseId = backStackEntry.arguments?.getString("expenseId")?.toIntOrNull()
                if (expenseId != null) {
                    expenseViewModel.getExpensesById(expenseId)
                }
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        ExpenseScreen(navController, expenseViewModel, expenseId)
                    }
                }
            }

        }
    }
}


@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.List, "Dépenses") },
            label = { Text("Dépenses") },
            selected = navController.currentDestination?.route == "expensesList",
            onClick = { navController.navigate("expensesList") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountCircle, "Profil") },
            label = { Text("Profil") },
            selected = navController.currentDestination?.route == "profile",
            onClick = { navController.navigate("profile") }
        )
    }
}