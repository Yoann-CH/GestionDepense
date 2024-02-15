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
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(navController: NavController, expenseViewModel: ExpenseViewModel) {
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(Date()) }
    var category by remember { mutableStateOf("loisir") }
    var isFavorite by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajouter une Dépense") }
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

            Text("Date sélectionnée : ${dateFormat.format(date)}")
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
                    expenseViewModel.insert(Expense(amount = amount.toDouble(), date = date, category = category, isFavorite = isFavorite))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ajouter")
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