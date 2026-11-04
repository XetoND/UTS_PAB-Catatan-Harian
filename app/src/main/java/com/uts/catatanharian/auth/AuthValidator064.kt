package com.uts.catatanharian.auth

import java.security.MessageDigest

object AuthValidator064 {
    const val MIN_PASSWORD_064 = 6

    /** Return pesan error, atau null kalau valid. */
    fun validateUsername064(username: String): String? =
        if (username.isBlank()) "Username tidak boleh kosong" else null

    fun validatePassword064(password: String): String? = when {
        password.isEmpty() -> "Password tidak boleh kosong"
        password.length < MIN_PASSWORD_064 -> "Password minimal $MIN_PASSWORD_064 karakter"
        else -> null
    }

    fun validateConfirm064(password: String, confirm: String): String? = when {
        confirm.isEmpty() -> "Konfirmasi password tidak boleh kosong"
        password != confirm -> "Konfirmasi password tidak cocok"
        else -> null
    }

    fun hash064(password: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(password.toByteArray())
            .joinToString("") { "%02x".format(it) }
}
