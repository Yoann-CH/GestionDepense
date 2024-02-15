package com.example.gestiondepense.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gestiondepense.data.database.entity.UserProfile
import com.example.gestiondepense.viewmodel.UserProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestiondepense.viewmodel.ExchangeRateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(userProfileViewModel: UserProfileViewModel = viewModel(), exchangeRateViewModel: ExchangeRateViewModel = viewModel(),) {
    val userProfile by userProfileViewModel.userProfile.collectAsState()

    val exchangeRates by exchangeRateViewModel.exchangeRates.observeAsState()
    var expanded by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf(userProfile?.userName ?: "") }
    var currency by remember { mutableStateOf(userProfile?.preferredCurrency ?: "EUR") }
    var monthlyBudget by remember { mutableStateOf((userProfile?.monthlyBudget ?: 0.0).toString()) }

    LaunchedEffect(userProfile) {
        exchangeRateViewModel.fetchExchangeRates("EUR")
        userName = userProfile?.userName ?: ""
        currency = userProfile?.preferredCurrency ?: "EUR"
        monthlyBudget = userProfile?.monthlyBudget?.toString() ?: "0"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isEditing) {
            // Champs modifiables
            TextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Name") }
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    readOnly = true,
                    value = currency,
                    onValueChange = { },
                    label = { Text("Currency") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    exchangeRates?.conversion_rates?.keys?.sorted()?.forEach { rate ->
                        DropdownMenuItem(
                            text = { Text(rate) },
                            onClick = {
                                currency = rate
                                expanded = false
                            }
                        )
                    }
                }
            }

            TextField(
                value = monthlyBudget,
                onValueChange = { monthlyBudget = it },
                label = { Text("Monthly Budget") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        } else {
            // Affichage en lecture seule
            Text("Name: $userName")
            Text("Currency: $currency")
            Text("Monthly Budget: $monthlyBudget")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (isEditing) {
                    userProfileViewModel.updateUserProfile(UserProfile(userName = userName, preferredCurrency = currency, monthlyBudget = monthlyBudget.toDouble()))
                }
                isEditing = !isEditing
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(if (isEditing) "Sauvegarder" else "Modifier")
        }
    }
}


