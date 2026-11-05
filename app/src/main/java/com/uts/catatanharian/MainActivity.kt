package com.uts.catatanharian

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uts.catatanharian.adapter.NoteAdapter066
import com.uts.catatanharian.auth.LoginActivity
import com.uts.catatanharian.data.AppDatabase
import com.uts.catatanharian.data.Note066
import com.uts.catatanharian.databinding.ActivityMainBinding
import com.uts.catatanharian.util.SessionManager
import kotlinx.coroutines.launch

/**
 * Layar Dashboard Utama, List Catatan, dan Logout (NIM 066).
 * Bertanggung jawab atas:
 * 1. Pengecekan sesi aktif (redirect ke login jika belum login)
 * 2. Menampilkan sapaan nama user yang sedang login
 * 3. Menampilkan daftar catatan user dengan RecyclerView dan isolasi data
 * 4. Fitur pencarian catatan secara real-time (Search Bar)
 * 5. Logika logout dengan dialog konfirmasi dan pembersihan sesi di SharedPreferences
 * 6. Membuka form tambah catatan (Bagian #5)
 * 7. Membuka form edit dan hapus catatan saat item diklik (Bagian #6)
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding066: ActivityMainBinding
    private lateinit var session066: SessionManager
    private lateinit var noteAdapter066: NoteAdapter066

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

        // 4. Setup RecyclerView & Search Bar
        setupRecyclerView066()
        setupSearchBar066()

        // 5. Pasang aksi tombol logout
        binding066.btnLogout066.setOnClickListener {
            showLogoutConfirmation066()
        }

        // 6. Tombol Tambah Catatan (Membuka AddNoteActivity066)
        binding066.fabAddNote066.setOnClickListener {
            val intent066 = Intent(this, AddNoteActivity066::class.java)
            startActivity(intent066)
        }
    }

    override fun onResume() {
        super.onResume()
        if (session066.isLoggedIn()) {
            val currentKeyword066 = binding066.etSearch066.text?.toString()?.trim() ?: ""
            loadNotes066(currentKeyword066)
        }
    }

    private fun setupUserGreeting066() {
        val username066 = session066.getUsername066()
        binding066.tvUsername066.text = if (username066.isNotBlank()) username066 else "Pengguna"
    }

    private fun setupRecyclerView066() {
        noteAdapter066 = NoteAdapter066 { clickedNote066 ->
            onNoteItemClicked066(clickedNote066)
        }

        binding066.rvNotes066.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter066
        }
    }

    private fun setupSearchBar066() {
        binding066.etSearch066.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword066 = s?.toString()?.trim() ?: ""
                loadNotes066(keyword066)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadNotes066(keyword066: String) {
        val currentUserId066 = session066.getUserId()
        if (currentUserId066 == -1) return

        lifecycleScope.launch {
            val dao066 = AppDatabase.getInstance(this@MainActivity).noteDao066()
            val list066 = if (keyword066.isBlank()) {
                dao066.getAllByUser066(currentUserId066)
            } else {
                dao066.searchByUser066(currentUserId066, keyword066)
            }

            noteAdapter066.submitList066(list066)

            if (list066.isEmpty()) {
                binding066.rvNotes066.visibility = View.GONE
                binding066.layoutEmptyState066.visibility = View.VISIBLE

                if (keyword066.isNotBlank()) {
                    binding066.tvEmptyTitle066.text = "Catatan Tidak Ditemukan"
                    binding066.tvEmptyDesc066.text = "Tidak ada catatan dengan kata kunci \"$keyword066\""
                } else {
                    binding066.tvEmptyTitle066.text = "Belum Ada Catatan"
                    binding066.tvEmptyDesc066.text = "Mulai buat catatan harian baru dengan menekan tombol (+) di bawah."
                }
            } else {
                binding066.rvNotes066.visibility = View.VISIBLE
                binding066.layoutEmptyState066.visibility = View.GONE
            }
        }
    }

    private fun onNoteItemClicked066(note066: Note066) {
        // Membuka form Edit dan Hapus Catatan (Bagian #6)
        val intent066 = Intent(this, EditNoteActivity066::class.java).apply {
            putExtra(EditNoteActivity066.EXTRA_NOTE_ID_066, note066.id)
        }
        startActivity(intent066)
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
