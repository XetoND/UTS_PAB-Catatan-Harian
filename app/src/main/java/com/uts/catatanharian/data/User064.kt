package com.uts.catatanharian.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// NOTE: entity minimal untuk fitur Register & Login (064).
// Bagian Room (orang lain) boleh melengkapi, tapi nama tabel/kolom 064 jangan diubah.
@Entity(tableName = "users_064", indices = [Index(value = ["username_064"], unique = true)])
data class User064(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "username_064") val username: String,
    @ColumnInfo(name = "password_064") val password: String
)
