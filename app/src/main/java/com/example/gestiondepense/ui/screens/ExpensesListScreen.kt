package com.example.gestiondepense.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestiondepense.R
import com.example.gestiondepense.data.database.entity.Expense
import com.example.gestiondepense.viewmodel.ExpenseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesListScreen(navController: NavController, expenseViewModel: ExpenseViewModel = viewModel()) {
    val expenses by expenseViewModel.allExpenses.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Liste des Dépenses") },
                actions = {
                    IconButton(onClick = { /* Naviguer vers l'écran de création de dépense */ }) {
                        Icon(Icons.Filled.Add, contentDescription = "Ajouter")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (expenses.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = R.drawable.ic_empty_list), contentDescription = "Liste Vide", modifier = Modifier.size(64.dp))
                        Text("Aucune dépense de saisie")
                    }
                }
            } else {
                LazyColumn {
                    items(expenses) { expense ->
                        ExpenseItem(expense, onEdit = { /* Modifier la dépense */ }, onDelete = { /* Supprimer la dépense */ })
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense, onEdit: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(expense.expenseType, modifier = Modifier.weight(1f))
        IconButton(onClick = onEdit) {
            Icon(Icons.Filled.Edit, contentDescription = "Modifier")
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Supprimer")
        }
    }
}
