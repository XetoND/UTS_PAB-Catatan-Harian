package com.uts.catatanharian.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uts.catatanharian.MainActivity
import com.uts.catatanharian.data.AppDatabase
import com.uts.catatanharian.databinding.ActivityLogin066Binding
import com.uts.catatanharian.util.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding066: ActivityLogin066Binding
    private lateinit var session066: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        session066 = SessionManager(this)
        if (session066.isLoggedIn()) {
            goToDashboard066()
            return
        }
        binding066 = ActivityLogin066Binding.inflate(layoutInflater)
        setContentView(binding066.root)

        binding066.btnLogin066.setOnClickListener { doLogin066() }
        binding066.tvToRegister066.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun doLogin066() {
        val username066 = binding066.etUsername066.text.toString().trim()
        val password066 = binding066.etPassword066.text.toString()

        binding066.tilUsername066.error = AuthValidator066.validateUsername066(username066)
        binding066.tilPassword066.error =
            if (password066.isEmpty()) "Password tidak boleh kosong" else null
        if (binding066.tilUsername066.error != null || binding066.tilPassword066.error != null) return

        lifecycleScope.launch {
            val dao066 = AppDatabase.getInstance(this@LoginActivity).userDao066()
            val user066 = dao066.login066(username066, AuthValidator066.hash066(password066))
            if (user066 == null) {
                Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
            } else {
                session066.saveSession(user066.id, user066.username)
                goToDashboard066()
            }
        }
    }

    private fun goToDashboard066() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
