package com.example.gestiondepense.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userName: String,
    val preferredCurrency: String,
    val monthlyBudget: Double
)