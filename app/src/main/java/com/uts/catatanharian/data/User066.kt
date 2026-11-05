package com.uts.catatanharian.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users_066", indices = [Index(value = ["username_066"], unique = true)])
data class User066(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "username_066") val username: String,
    @ColumnInfo(name = "password_066") val password: String
)
