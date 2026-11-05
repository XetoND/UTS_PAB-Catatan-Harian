package com.uts.catatanharian.auth

import java.security.MessageDigest

object AuthValidator066 {
    const val MIN_PASSWORD_066 = 6

    /** Return pesan error, atau null kalau valid. */
    fun validateUsername066(username: String): String? =
        if (username.isBlank()) "Username tidak boleh kosong" else null

    fun validatePassword066(password: String): String? = when {
        password.isEmpty() -> "Password tidak boleh kosong"
        password.length < MIN_PASSWORD_066 -> "Password minimal $MIN_PASSWORD_066 karakter"
        else -> null
    }

    fun validateConfirm066(password: String, confirm: String): String? = when {
        confirm.isEmpty() -> "Konfirmasi password tidak boleh kosong"
        password != confirm -> "Konfirmasi password tidak cocok"
        else -> null
    }

    fun hash066(password: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(password.toByteArray())
            .joinToString("") { "%02x".format(it) }
}
