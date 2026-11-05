package com.uts.catatanharian

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.uts.catatanharian.data.AppDatabase
import com.uts.catatanharian.data.Note066
import com.uts.catatanharian.databinding.ActivityAddNote066Binding
import com.uts.catatanharian.util.SessionManager
import kotlinx.coroutines.launch

/**
 * Layar Form Tambah Catatan Baru (Bagian #5 NIM 066).
 * Bertanggung jawab atas:
 * 1. Formulir input judul dan isi catatan
 * 2. Validasi input agar tidak boleh kosong
 * 3. Menyimpan catatan baru ke database SQLite via Room (terisolasi per user_id)
 */
class AddNoteActivity066 : AppCompatActivity() {

    private lateinit var binding066: ActivityAddNote066Binding
    private lateinit var session066: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Cek sesi
        session066 = SessionManager(this)
        if (!session066.isLoggedIn()) {
            finish()
            return
        }

        // 2. Setup ViewBinding & Insets
        binding066 = ActivityAddNote066Binding.inflate(layoutInflater)
        setContentView(binding066.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding066.layoutAddNote066) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 3. Tombol navigasi dan simpan
        binding066.btnBack066.setOnClickListener { finish() }
        binding066.btnCancelNote066.setOnClickListener { finish() }
        binding066.btnSaveNote066.setOnClickListener { doSaveNote066() }
    }

    private fun doSaveNote066() {
        val judul066 = binding066.etJudul066.text?.toString()?.trim() ?: ""
        val isi066 = binding066.etIsi066.text?.toString()?.trim() ?: ""

        var isValid066 = true

        if (judul066.isEmpty()) {
            binding066.tilJudul066.error = "Judul catatan tidak boleh kosong"
            isValid066 = false
        } else {
            binding066.tilJudul066.error = null
        }

        if (isi066.isEmpty()) {
            binding066.tilIsi066.error = "Isi catatan tidak boleh kosong"
            isValid066 = false
        } else {
            binding066.tilIsi066.error = null
        }

        if (!isValid066) return

        val currentUserId066 = session066.getUserId()
        val currentTimeMillis066 = System.currentTimeMillis()

        val newNote066 = Note066(
            userId = currentUserId066,
            judul = judul066,
            isi = isi066,
            tanggal = currentTimeMillis066
        )

        lifecycleScope.launch {
            val dao066 = AppDatabase.getInstance(this@AddNoteActivity066).noteDao066()
            val insertedId066 = dao066.insertNote066(newNote066)

            if (insertedId066 > 0) {
                Toast.makeText(this@AddNoteActivity066, "Catatan berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@AddNoteActivity066, "Gagal menyimpan catatan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
