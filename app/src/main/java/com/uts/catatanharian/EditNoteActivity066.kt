package com.uts.catatanharian

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.uts.catatanharian.data.AppDatabase
import com.uts.catatanharian.data.Note066
import com.uts.catatanharian.databinding.ActivityEditNote066Binding
import com.uts.catatanharian.util.SessionManager
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Layar Form Edit dan Hapus Catatan (Bagian #6 NIM 066).
 * Bertanggung jawab atas:
 * 1. Mengambil detail catatan berdasarkan id dan user_id (Isolasi Data)
 * 2. Memperbarui catatan (Update)
 * 3. Menghapus catatan dengan dialog konfirmasi (Delete)
 */
class EditNoteActivity066 : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE_ID_066 = "extra_note_id_066"
    }

    private lateinit var binding066: ActivityEditNote066Binding
    private lateinit var session066: SessionManager
    private var noteId066: Int = -1
    private var currentNote066: Note066? = null
    private val dateFormatter066 = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Cek sesi
        session066 = SessionManager(this)
        if (!session066.isLoggedIn()) {
            finish()
            return
        }

        noteId066 = intent.getIntExtra(EXTRA_NOTE_ID_066, -1)
        if (noteId066 == -1) {
            Toast.makeText(this, "ID catatan tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // 2. Setup ViewBinding & Insets
        binding066 = ActivityEditNote066Binding.inflate(layoutInflater)
        setContentView(binding066.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding066.layoutEditNote066) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 3. Muat data catatan yang dipilih
        loadNoteDetail066()

        // 4. Pasang listener tombol
        binding066.btnBackEdit066.setOnClickListener { finish() }
        binding066.btnUpdateNote066.setOnClickListener { doUpdateNote066() }
        binding066.btnDeleteNote066.setOnClickListener { showDeleteConfirmation066() }
        binding066.btnTopDelete066.setOnClickListener { showDeleteConfirmation066() }
    }

    private fun loadNoteDetail066() {
        val currentUserId066 = session066.getUserId()

        lifecycleScope.launch {
            val dao066 = AppDatabase.getInstance(this@EditNoteActivity066).noteDao066()
            val note066 = dao066.getById066(noteId066, currentUserId066)

            if (note066 == null) {
                Toast.makeText(this@EditNoteActivity066, "Catatan tidak ditemukan atau Anda tidak memiliki akses", Toast.LENGTH_SHORT).show()
                finish()
                return@launch
            }

            currentNote066 = note066
            binding066.etEditJudul066.setText(note066.judul)
            binding066.etEditIsi066.setText(note066.isi)
            binding066.tvEditDateInfo066.text = "Dibuat pada: ${dateFormatter066.format(Date(note066.tanggal))}"
        }
    }

    private fun doUpdateNote066() {
        val judul066 = binding066.etEditJudul066.text?.toString()?.trim() ?: ""
        val isi066 = binding066.etEditIsi066.text?.toString()?.trim() ?: ""

        var isValid066 = true

        if (judul066.isEmpty()) {
            binding066.tilEditJudul066.error = "Judul catatan tidak boleh kosong"
            isValid066 = false
        } else {
            binding066.tilEditJudul066.error = null
        }

        if (isi066.isEmpty()) {
            binding066.tilEditIsi066.error = "Isi catatan tidak boleh kosong"
            isValid066 = false
        } else {
            binding066.tilEditIsi066.error = null
        }

        if (!isValid066) return

        val currentUserId066 = session066.getUserId()

        lifecycleScope.launch {
            val dao066 = AppDatabase.getInstance(this@EditNoteActivity066).noteDao066()
            val rowsUpdated066 = dao066.updateNote066(
                id = noteId066,
                userId = currentUserId066,
                judul = judul066,
                isi = isi066
            )

            if (rowsUpdated066 > 0) {
                Toast.makeText(this@EditNoteActivity066, "Catatan berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@EditNoteActivity066, "Gagal memperbarui catatan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDeleteConfirmation066() {
        val judulCatatan066 = currentNote066?.judul ?: "catatan ini"

        MaterialAlertDialogBuilder(this)
            .setTitle("Hapus Catatan?")
            .setMessage("Apakah Anda yakin ingin menghapus \"$judulCatatan066\"? Tindakan ini tidak dapat dibatalkan.")
            .setPositiveButton("Hapus") { _, _ ->
                doDeleteNote066()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun doDeleteNote066() {
        val currentUserId066 = session066.getUserId()

        lifecycleScope.launch {
            val dao066 = AppDatabase.getInstance(this@EditNoteActivity066).noteDao066()
            val rowsDeleted066 = dao066.deleteNote066(noteId066, currentUserId066)

            if (rowsDeleted066 > 0) {
                Toast.makeText(this@EditNoteActivity066, "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@EditNoteActivity066, "Gagal menghapus catatan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
