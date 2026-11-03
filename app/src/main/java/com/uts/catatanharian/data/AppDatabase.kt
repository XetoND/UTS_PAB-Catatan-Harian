package com.uts.catatanharian.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// NOTE: placeholder minimal. Orang Room menambahkan entity Note ke daftar entities
// dan menaikkan version. Entity User064 + userDao064() harus tetap ada.
@Database(entities = [User064::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao064(): UserDao064

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "catatan_harian.db"
                ).build().also { INSTANCE = it }
            }
    }
}
