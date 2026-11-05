package com.uts.catatanharian.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// DAO catatan untuk Bagian #2 Data Layer (NIM 066).
// Semua query difilter berdasarkan user_id_066 untuk menjamin isolasi data antar user.
@Dao
interface NoteDao066 {
    @Insert
    suspend fun insertNote066(note: Note066): Long

    @Query("SELECT * FROM notes_066 WHERE user_id_066 = :userId ORDER BY tanggal_066 DESC")
    suspend fun getAllByUser066(userId: Int): List<Note066>

    @Query("SELECT * FROM notes_066 WHERE id = :id AND user_id_066 = :userId LIMIT 1")
    suspend fun getById066(id: Int, userId: Int): Note066?

    @Query("UPDATE notes_066 SET judul_066 = :judul, isi_066 = :isi WHERE id = :id AND user_id_066 = :userId")
    suspend fun updateNote066(id: Int, userId: Int, judul: String, isi: String): Int

    @Query("DELETE FROM notes_066 WHERE id = :id AND user_id_066 = :userId")
    suspend fun deleteNote066(id: Int, userId: Int): Int

    @Query("SELECT * FROM notes_066 WHERE user_id_066 = :userId AND (judul_066 LIKE '%' || :keyword || '%' OR isi_066 LIKE '%' || :keyword || '%') ORDER BY tanggal_066 DESC")
    suspend fun searchByUser066(userId: Int, keyword: String): List<Note066>
}
