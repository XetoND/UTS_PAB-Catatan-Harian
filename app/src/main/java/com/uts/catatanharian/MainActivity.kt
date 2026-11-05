package com.uts.catatanharian

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uts.catatanharian.auth.LoginActivity
import com.uts.catatanharian.databinding.ActivityMainBinding
import com.uts.catatanharian.util.SessionManager

/**
 * Layar Dashboard Utama dan Logout (NIM 066).
 * Bertanggung jawab atas:
 * 1. Pengecekan sesi aktif (redirect ke login jika belum login)
 * 2. Menampilkan sapaan nama user yang sedang login
 * 3. Logika logout dengan dialog konfirmasi dan pembersihan sesi di SharedPreferences
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding066: ActivityMainBinding
    private lateinit var session066: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Cek sesi: Jika pengguna belum login, arahkan langsung ke LoginActivity
        session066 = SessionManager(this)
        if (!session066.isLoggedIn()) {
            redirectToLogin066()
            return
        }

        // 2. Setup ViewBinding dan layout
        binding066 = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding066.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding066.mainLayout066) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 3. Tampilkan sapaan pengguna aktif
        setupUserGreeting066()

        // 4. Pasang aksi tombol logout
        binding066.btnLogout066.setOnClickListener {
            showLogoutConfirmation066()
        }
    }

    private fun setupUserGreeting066() {
        val username066 = session066.getUsername066()
        binding066.tvUsername066.text = if (username066.isNotBlank()) username066 else "Pengguna"
    }

    private fun showLogoutConfirmation066() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage("Apakah Anda yakin ingin keluar dari akun ini?")
            .setPositiveButton("Logout") { _, _ ->
                performLogout066()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun performLogout066() {
        session066.clear()
        Toast.makeText(this, "Berhasil logout", Toast.LENGTH_SHORT).show()
        redirectToLogin066()
    }

    private fun redirectToLogin066() {
        val intent066 = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent066)
        finish()
    }
}
