package com.example.gestiondepense.data.repository

import com.example.gestiondepense.data.database.dao.UserProfileDao
import com.example.gestiondepense.data.database.entity.UserProfile

class UserProfileRepository(private val userProfileDao: UserProfileDao) {

    suspend fun insert(userProfile: UserProfile) {
        userProfileDao.insert(userProfile)
    }

    suspend fun update(userProfile: UserProfile) {
        userProfileDao.update(userProfile)
    }

    suspend fun delete(userProfile: UserProfile) {
        userProfileDao.delete(userProfile)
    }

    fun getUserProfileById(id: Int): UserProfile {
        return userProfileDao.getUserProfileById(id)
    }
}
