package com.example.gestiondepense.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import com.example.gestiondepense.data.database.entity.Expense
import com.example.gestiondepense.viewmodel.ExpenseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesListScreen(navController: NavController, expenseViewModel: ExpenseViewModel = viewModel()) {
    val expenses by expenseViewModel.allExpenses.collectAsState()

    LaunchedEffect(expenses) {
        expenseViewModel.getExpenses()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Liste des Dépenses") },
                actions = {
                    IconButton(onClick = { navController.navigate("addExpense") }) {
                        Icon(Icons.Filled.Add, contentDescription = "Ajouter")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (expenses.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Aucune dépense de saisie")
                }
            } else {
                LazyColumn {
                    items(expenses) { expense ->
                        ExpenseItem(
                            expense = expense,
                            onEdit = {
                                navController.navigate("editExpense/${expense.id}")
                            },
                            onDelete = {
                                expenseViewModel.delete(expense)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense, onEdit: () -> Unit, onDelete: () -> Unit) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Montant: ${expense.amount}")
            Text(text = "Date: ${dateFormat.format(expense.date)}")
            Text(text = "Catégorie: ${expense.category}")
        }
        Icon(
            imageVector = if (expense.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = if (expense.isFavorite) "Favori" else "Non Favori",
            modifier = Modifier.padding(end = 8.dp)
        )
        IconButton(onClick = onEdit) {
            Icon(Icons.Filled.Edit, contentDescription = "Modifier")
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Supprimer")
        }
    }
}
