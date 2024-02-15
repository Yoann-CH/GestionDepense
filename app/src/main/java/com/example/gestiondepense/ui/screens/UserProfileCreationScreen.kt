package com.example.gestiondepense.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import com.example.gestiondepense.viewmodel.ExchangeRateViewModel
import com.example.gestiondepense.viewmodel.UserProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileCreationScreen(
    exchangeRateViewModel: ExchangeRateViewModel,
    userProfileViewModel: UserProfileViewModel,
    onProfileCreated: () -> Unit
) {
    LaunchedEffect(Unit) {
        exchangeRateViewModel.fetchExchangeRates("EUR")
    }

    val exchangeRates by exchangeRateViewModel.exchangeRates.observeAsState(initial = null)
    var expanded by remember { mutableStateOf(false) }
    var userName by rememberSaveable { mutableStateOf("") }
    var currency by rememberSaveable { mutableStateOf("") }
    var monthlyBudget by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Name") }
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                readOnly = true,
                value = currency,
                onValueChange = { },
                label = { Text("Currency") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                exchangeRates?.rates?.keys?.forEach { rate: String ->
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
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (userName.isNotEmpty() && currency.isNotEmpty() && monthlyBudget.isNotEmpty()) {
                    userProfileViewModel.createUserProfile(
                        name = userName,
                        currency = currency,
                        monthlyBudget = monthlyBudget.toDoubleOrNull() ?: 0.0
                    )
                    onProfileCreated()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Créer")
        }
    }
}


