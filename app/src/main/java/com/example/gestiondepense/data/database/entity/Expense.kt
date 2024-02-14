package com.example.gestiondepense.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val date: Date,
    val expenseType: String,
    val category: String,
    val isFavorite: Boolean
)