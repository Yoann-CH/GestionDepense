package com.example.gestiondepense.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestiondepense.viewmodel.ExpenseViewModel
import java.util.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalContext
import com.example.gestiondepense.data.database.entity.Expense

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseScreen(navController: NavController, expenseViewModel: ExpenseViewModel, expenseId: Int?) {
    if (expenseId == null) {
        expenseViewModel.resetExpenseDetails()
    }

    val expenseDetails by expenseViewModel.expenseDetails.collectAsState()
    val context = LocalContext.current

    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(Date()) }
    var category by remember { mutableStateOf("loisir") }
    var isFavorite by remember { mutableStateOf(false) }

    // Mise à jour des variables d'état après le chargement des détails de la dépense
    LaunchedEffect(expenseId) {
        if (expenseId != null) {
            expenseViewModel.getExpensesById(expenseId)
        }
    }

    // Réinitialisation des champs du formulaire basée sur les détails de la dépense chargés
    LaunchedEffect(expenseDetails) {
        amount = expenseDetails?.amount?.toString() ?: ""
        date = expenseDetails?.date ?: Date()
        category = expenseDetails?.category ?: "loisir"
        isFavorite = expenseDetails?.isFavorite ?: false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (expenseId != null) "Modifier une Dépense" else "Ajouter une Dépense") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Montant") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
            )

            Button(onClick = {
                showDatePicker(context, date) { selectedDate ->
                    date = selectedDate
                }
            }) {
                Text("Sélectionner une date")
            }

            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = { },
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = category,
                    onValueChange = { },
                    label = { Text("Type de dépense") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = false,
                    onDismissRequest = { },
                ) {
                    DropdownMenuItem(
                        text = { Text("Loisir") },
                        onClick = { category = "loisir" }
                    )
                    DropdownMenuItem(
                        text = { Text("Transport") },
                        onClick = { category = "transport" }
                    )
                    DropdownMenuItem(
                        text = { Text("Nourriture") },
                        onClick = { category = "nourriture" }
                    )
                    DropdownMenuItem(
                        text = { Text("Autre") },
                        onClick = { category = "autre" }
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isFavorite,
                    onCheckedChange = { isFavorite = it }
                )
                Text("Favori")
            }

            Button(
                onClick = {
                    if (expenseId != null) {
                        expenseViewModel.update(Expense(amount = amount.toDouble(), date = date, category = category, isFavorite = isFavorite))
                    } else {
                        expenseViewModel.insert(Expense(amount = amount.toDouble(), date = date, category = category, isFavorite = isFavorite))
                    }
                    navController.navigate("expensesList")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (expenseId != null) "Modifier" else "Ajouter")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
fun showDatePicker(context: android.content.Context, date: Date, onDateSelected: (Date) -> Unit) {
    val calendar = Calendar.getInstance().apply {
        time = date
    }

    val datePickerDialog = android.app.DatePickerDialog(
        context, { _, year, month, dayOfMonth ->
            val newDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }.time
            onDateSelected(newDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.show()
}