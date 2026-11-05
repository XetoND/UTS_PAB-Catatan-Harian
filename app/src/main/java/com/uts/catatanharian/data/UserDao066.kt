package com.uts.catatanharian.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao066 {
    @Insert
    suspend fun insertUser066(user: User066): Long

    @Query("SELECT * FROM users_066 WHERE username_066 = :username LIMIT 1")
    suspend fun getByUsername066(username: String): User066?

    @Query("SELECT * FROM users_066 WHERE username_066 = :username AND password_066 = :password LIMIT 1")
    suspend fun login066(username: String, password: String): User066?
}
