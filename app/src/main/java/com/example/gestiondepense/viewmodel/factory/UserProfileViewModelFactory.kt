package com.example.gestiondepense.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestiondepense.data.repository.UserProfileRepository
import com.example.gestiondepense.viewmodel.UserProfileViewModel

class UserProfileViewModelFactory(private val userProfileRepository: UserProfileRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserProfileViewModel(userProfileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}