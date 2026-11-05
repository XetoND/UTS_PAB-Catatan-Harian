package com.uts.catatanharian.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uts.catatanharian.data.Note066
import com.uts.catatanharian.databinding.ItemNote066Binding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Adapter RecyclerView untuk menampilkan daftar catatan harian (NIM 066).
 */
class NoteAdapter066(
    private val onItemClick066: (Note066) -> Unit
) : RecyclerView.Adapter<NoteAdapter066.NoteViewHolder066>() {

    private var notesList066: List<Note066> = emptyList()
    private val dateFormatter066 = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

    fun submitList066(newNotes: List<Note066>) {
        this.notesList066 = newNotes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder066 {
        val binding066 = ItemNote066Binding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteViewHolder066(binding066)
    }

    override fun onBindViewHolder(holder: NoteViewHolder066, position: Int) {
        holder.bind066(notesList066[position])
    }

    override fun getItemCount(): Int = notesList066.size

    inner class NoteViewHolder066(private val binding066: ItemNote066Binding) :
        RecyclerView.ViewHolder(binding066.root) {

        fun bind066(note066: Note066) {
            binding066.tvNoteTitle066.text = note066.judul
            binding066.tvNoteContent066.text = note066.isi
            binding066.tvNoteDate066.text = dateFormatter066.format(Date(note066.tanggal))

            binding066.cardNote066.setOnClickListener {
                onItemClick066(note066)
            }
        }
    }
}
