package com.example.gestiondepense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestiondepense.data.repository.UserProfileRepository
import com.example.gestiondepense.data.database.entity.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(private val userProfileRepository: UserProfileRepository) : ViewModel() {

    private val _hasProfile = MutableStateFlow(false)
    val hasProfile: StateFlow<Boolean> = _hasProfile

    // Ajoutez un StateFlow pour UserProfile
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            val userProfile = userProfileRepository.getUserProfile()
            _userProfile.value = userProfile
            _hasProfile.value = userProfile != null
        }
    }

    fun createUserProfile(name: String, currency: String, monthlyBudget: Double) {
        val newUserProfile = UserProfile(userName = name, preferredCurrency = currency, monthlyBudget = monthlyBudget)
        viewModelScope.launch {
            userProfileRepository.insert(newUserProfile)
            _userProfile.value = newUserProfile
            _hasProfile.value = true
        }
    }

    fun updateUserProfile(updatedProfile: UserProfile) {
        viewModelScope.launch {
            userProfileRepository.update(updatedProfile)
            _userProfile.value = updatedProfile
        }
    }
}
