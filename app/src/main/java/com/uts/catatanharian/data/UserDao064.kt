package com.uts.catatanharian.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao064 {
    @Insert
    suspend fun insertUser064(user: User064): Long

    @Query("SELECT * FROM users_064 WHERE username_064 = :username LIMIT 1")
    suspend fun getByUsername064(username: String): User064?

    @Query("SELECT * FROM users_064 WHERE username_064 = :username AND password_064 = :password LIMIT 1")
    suspend fun login064(username: String, password: String): User064?
}
