package com.example.gestiondepense.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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

    val focusRequester = remember { FocusRequester() }
    val exchangeRates by exchangeRateViewModel.exchangeRates.observeAsState(initial = null)
    var expanded by remember { mutableStateOf(false) }
    var userName by rememberSaveable { mutableStateOf("") }
    var currency by rememberSaveable { mutableStateOf("EUR") }
    var monthlyBudget by rememberSaveable { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
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
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.focusRequester(focusRequester)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                exchangeRates?.conversion_rates?.keys?.forEach { rate: String ->
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

        errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(8.dp))
        }

        Button(
            onClick = {
                if (userName.isEmpty() || currency.isEmpty() || monthlyBudget.isEmpty()) {
                    errorMessage = "Veuillez remplir tous les champs."
                } else {
                    val budget = monthlyBudget.toDoubleOrNull()
                    if (budget == null) {
                        errorMessage = "Le budget mensuel doit être un nombre valide."
                    } else {
                        errorMessage = null
                        userProfileViewModel.createUserProfile(
                            name = userName,
                            currency = currency,
                            monthlyBudget = budget
                        )
                        onProfileCreated()
                    }
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Créer")
        }
    }
}


