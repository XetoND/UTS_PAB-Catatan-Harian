# Catatan Harian

Aplikasi Android untuk mencatat catatan harian, dengan login per pengguna dan data yang privat. Dibuat untuk tugas UTS mata kuliah **Pengembangan Aplikasi Bergerak** (Telkom University, Semester 7).

Aplikasi berjalan sepenuhnya offline. Data disimpan di SQLite lewat Room, tanpa internet dan tanpa API.

## Fitur

- Register dan login dengan password yang di-hash (SHA-256)
- Sesi login disimpan di SharedPreferences
- Dashboard interaktif dengan sapaan nama user dan fitur Logout
- Catatan bersifat privat: setiap query ke database difilter dengan `user_id`, sehingga satu pengguna tidak bisa membaca, mengubah, atau menghapus catatan pengguna lain
- CRUD catatan di lapisan data, termasuk pencarian berdasarkan judul dan isi

## Status pengerjaan

| # | Bagian | Cakupan | Status |
|---|---|---|---|
| 1 | Auth | Register, login, validasi, hash password | Selesai |
| 2 | Data layer | Entity `Note`, `NoteDao`, `AppDatabase`, test | Selesai |
| 3 | Dashboard dan Logout | Layar utama, sapaan, logout, cek sesi | Selesai |
| 4 | List catatan | RecyclerView, adapter, tampilan kosong, search bar | Belum |
| 5 | Form tambah | Layar tambah, validasi input, simpan | Belum |
| 6 | Edit dan Hapus | Layar edit, hapus dengan konfirmasi | Belum |

## Teknologi

| Komponen | Versi |
|---|---|
| Bahasa | Kotlin |
| Android Gradle Plugin | 9.4.0 |
| KSP | 2.3.13 |
| Room | 2.8.5 |
| Material Components | 1.10.0 |
| compileSdk / targetSdk | 37 |
| minSdk | 24 (Android 7.0) |
| JDK | 11 (source dan target compatibility) |

ViewBinding aktif.

## Struktur proyek

```
app/src/main/java/com/uts/catatanharian/
├── MainActivity.kt              # Layar Dashboard & Logout (NIM 066)
├── auth/
│   ├── LoginActivity.kt         # Launcher; langsung ke MainActivity bila sesi aktif
│   ├── RegisterActivity.kt
│   └── AuthValidator066.kt      # Validasi input dan hash password (NIM 066)
├── data/
│   ├── AppDatabase.kt           # Room database, versi 3
│   ├── User066.kt               # Tabel users_066
│   ├── UserDao066.kt
│   ├── Note066.kt               # Tabel notes_066
│   └── NoteDao066.kt
└── util/
    └── SessionManager.kt        # Simpan dan baca sesi (SharedPreferences)
```

### Konvensi penamaan

Sesuai ketentuan soal, seluruh nama class, tabel, kolom, dan fungsi hasil modifikasi diakhiri **3 digit NIM (`066`)**:

- Bagian Auth: `User066`, `UserDao066`, `AuthValidator066`, tabel `users_066`
- Bagian Data layer: `Note066`, `NoteDao066`, tabel `notes_066`
- Bagian Dashboard: `MainActivity` (komponen layout dan method `*066`)

## Skema database

Nama database: `catatan_harian.db`, versi 3.

**`users_066`**

| Kolom | Tipe | Keterangan |
|---|---|---|
| `id` | Int | Primary key, auto increment |
| `username_066` | String | Unique index |
| `password_066` | String | Hash SHA-256 |

**`notes_066`**

| Kolom | Tipe | Keterangan |
|---|---|---|
| `id` | Int | Primary key, auto increment |
| `user_id_066` | Int | Foreign key ke `users_066.id`, `ON DELETE CASCADE`, diberi index |
| `judul_066` | String | |
| `isi_066` | String | |
| `tanggal_066` | Long | Epoch millis; format ke teks dilakukan di UI |

### API `NoteDao066`

Semua fungsi `suspend` dan semuanya terfilter `userId`.

| Fungsi | Keterangan |
|---|---|
| `insertNote066(note): Long` | Tambah catatan, mengembalikan id baru |
| `getAllByUser066(userId): List<Note066>` | Semua catatan milik user, tanggal terbaru dulu |
| `getById066(id, userId): Note066?` | `null` bila catatan bukan milik user |
| `updateNote066(id, userId, judul, isi): Int` | Mengembalikan jumlah baris terubah; `tanggal` tidak diubah |
| `deleteNote066(id, userId): Int` | Mengembalikan jumlah baris terhapus |
| `searchByUser066(userId, keyword): List<Note066>` | Cari di judul dan isi |

`userId` diambil dari `SessionManager.getUserId()`.

> **Migrasi database:** `AppDatabase` memakai `fallbackToDestructiveMigration()`. Setiap perubahan skema menghapus data lama. Setelah menarik perubahan yang menaikkan versi database, uninstall aplikasi atau clear data sekali di emulator/perangkat.

## Aturan validasi

- Username dan password tidak boleh kosong
- Password minimal 6 karakter
- Konfirmasi password harus cocok
- Username harus unik

## Cara menjalankan

Prasyarat: Android Studio versi terbaru yang mendukung AGP 9.4.0, dan emulator atau perangkat dengan Android 7.0 (API 24) ke atas.

1. Clone repo, lalu buka foldernya di Android Studio.
2. Tunggu Gradle sync selesai.
3. Pilih konfigurasi `app` dan jalankan di emulator atau perangkat.

Lewat command line:

```bash
./gradlew assembleDebug
```

Di Windows, pakai `gradlew.bat assembleDebug`.

## Pengujian

Test untuk data layer ada di `app/src/androidTest/java/com/uts/catatanharian/data/ExampleInstrumentedTest.kt` (kelas `NoteDao066Test`). Test memakai database in-memory dan butuh emulator atau perangkat yang berjalan.

Yang diuji:

- Insert dan baca catatan, termasuk urutan tanggal terbaru dulu
- Isolasi data antar pengguna pada baca, update, dan delete
- Pencarian terfilter per pengguna

Menjalankan dari command line:

```bash
./gradlew connectedDebugAndroidTest
```

## Ketentuan tugas

- Deadline pengumpulan: **9 November 2026**
- Alur navigasi yang diminta: Login → Dashboard → List Data (RecyclerView) → Form Tambah → Edit/Hapus
