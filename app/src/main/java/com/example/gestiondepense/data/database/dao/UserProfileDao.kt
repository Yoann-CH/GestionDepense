package com.example.gestiondepense.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gestiondepense.data.database.entity.UserProfile

@Dao
interface UserProfileDao {
    @Insert
    suspend fun insert(userProfile: UserProfile)

    @Update
    suspend fun update(userProfile: UserProfile)

    @Delete
    suspend fun delete(userProfile: UserProfile)

    @Query("SELECT * FROM UserProfile WHERE id = :id")
    suspend fun getUserProfileById(id: Int): UserProfile

    @Query("SELECT * FROM UserProfile LIMIT 1")
    suspend fun getUserProfile(): UserProfile?
}