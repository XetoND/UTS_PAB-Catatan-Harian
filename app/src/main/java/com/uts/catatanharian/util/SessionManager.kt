package com.uts.catatanharian.util

import android.content.Context

// Pengelola Sesi dan data user aktif (NIM 066).
// Menyimpan user_id dan username di SharedPreferences untuk autentikasi dan isolasi data.
class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun saveSession(userId: Int, username: String) {
        prefs.edit().putInt("user_id", userId).putString("username", username).apply()
    }

    fun isLoggedIn(): Boolean = prefs.getInt("user_id", -1) != -1

    fun getUserId(): Int = prefs.getInt("user_id", -1)

    fun getUsername066(): String = prefs.getString("username", "") ?: ""

    fun clear() = prefs.edit().clear().apply()
}
