package com.uts.catatanharian.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// Entity catatan untuk Bagian #2 Data Layer (NIM 066).
// Berelasi ke User064 dengan foreign key onDelete CASCADE dan index user_id_066.
@Entity(
    tableName = "notes_066",
    foreignKeys = [ForeignKey(
        entity = User064::class,
        parentColumns = ["id"],
        childColumns = ["user_id_066"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["user_id_066"])]
)
data class Note066(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id_066") val userId: Int,
    @ColumnInfo(name = "judul_066") val judul: String,
    @ColumnInfo(name = "isi_066") val isi: String,
    @ColumnInfo(name = "tanggal_066") val tanggal: Long
)
