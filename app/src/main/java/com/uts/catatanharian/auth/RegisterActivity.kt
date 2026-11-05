package com.uts.catatanharian.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uts.catatanharian.data.AppDatabase
import com.uts.catatanharian.data.User066
import com.uts.catatanharian.databinding.ActivityRegister066Binding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding066: ActivityRegister066Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding066 = ActivityRegister066Binding.inflate(layoutInflater)
        setContentView(binding066.root)

        binding066.btnRegister066.setOnClickListener { doRegister066() }
        binding066.tvToLogin066.setOnClickListener { finish() }
    }

    private fun doRegister066() {
        val username066 = binding066.etUsername066.text.toString().trim()
        val password066 = binding066.etPassword066.text.toString()
        val confirm066 = binding066.etConfirm066.text.toString()

        binding066.tilUsername066.error = AuthValidator066.validateUsername066(username066)
        binding066.tilPassword066.error = AuthValidator066.validatePassword066(password066)
        binding066.tilConfirm066.error = AuthValidator066.validateConfirm066(password066, confirm066)
        if (binding066.tilUsername066.error != null ||
            binding066.tilPassword066.error != null ||
            binding066.tilConfirm066.error != null) return

        lifecycleScope.launch {
            val dao066 = AppDatabase.getInstance(this@RegisterActivity).userDao066()
            if (dao066.getByUsername066(username066) != null) {
                binding066.tilUsername066.error = "Username sudah dipakai"
                return@launch
            }
            dao066.insertUser066(User066(username = username066, password = AuthValidator066.hash066(password066)))
            Toast.makeText(this@RegisterActivity, "Registrasi berhasil, silakan login", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
