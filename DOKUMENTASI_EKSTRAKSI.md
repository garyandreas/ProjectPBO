# Dokumentasi Aplikasi Keuangan Pribadi

**Penulis:** Kelompok 9 (Albert Wujaya, Azwa Zahira, Farhan Eka Yuda, Gary Andreas, Nur Diana Tri Zahara)

---

## 1. LATAR BELAKANG

### Permasalahan
- Mahasiswa mengalami kesulitan dalam mengatur uang saku bulanan
- Belum memiliki kebiasaan dan pemahaman perencanaan keuangan yang baik
- Perlu solusi praktis dan relevan dengan kebutuhan mahasiswa

### Solusi
Aplikasi keuangan pribadi yang membantu mahasiswa untuk:
- Mencatat transaksi harian
- Mengatur anggaran
- Menganalisis kebiasaan pengeluaran secara sistematis

---

## 2. TUJUAN DAN SASARAN

### Tujuan Aplikasi
Membantu mahasiswa mengelola keuangan secara teratur, efisien, dan bijak dengan:
- Pencatatan transaksi yang teratur
- Pengaturan anggaran yang terstruktur
- Analisis pengeluaran yang mendalam
- Mendorong pembentukan kebiasaan finansial yang sehat

### Sasaran Pengguna
- **Utama:** Mahasiswa yang hidup mandiri dan perlu mengatur uang saku secara mandiri
- **Tambahan:** Kalangan muda lainnya yang ingin belajar mengelola keuangan pribadi

---

## 3. KONSEP

### Filosofi Desain
Aplikasi dirancang dengan fokus pada:
- **Kemudahan Penggunaan:** Antarmuka sederhana dan mudah dipahami
- **Pencatatan Menyeluruh:** Mencatat setiap pemasukan dan pengeluaran
- **Visibilitas Finansial:** Memberikan gambaran menyeluruh kondisi finansial
- **Edukasi Finansial:** Mengajarkan kedisiplinan dalam mengelola keuangan

### Fungsi Utama
1. **Alat Pencatat Keuangan:** Mencatat semua transaksi finansial
2. **Media Edukasi:** Edukasi melalui laporan, pengingat, dan visualisasi grafik
3. **Pembentukan Kebiasaan:** Mendorong kedisiplinan dan bijaksana dalam pengeluaran
4. **Panduan Menuju Kemandirian Ekonomi:** Langkah awal menuju kestabilan finansial

---

## 4. FITUR-FITUR APLIKASI

### 4.1 Pencatatan Transaksi
**Deskripsi:** Mencatat seluruh pemasukan dan pengeluaran dari pengguna

**Mekanisme:**
- Pengguna dapat mencatat setiap transaksi keuangan
- Data transaksi disimpan dan terintegrasi dengan sistem

**Output:**
- Menampilkan total saldo
- Total pemasukan
- Total pengeluaran

### 4.2 Ringkasan Keuangan
**Deskripsi:** Menyajikan visualisasi data keuangan pengguna

**Mekanisme:**
- Mengumpulkan semua data transaksi yang telah dicatat
- Mengagregasi data berdasarkan periode waktu
- Menghasilkan grafik atau laporan untuk visualisasi

**Output:**
- Grafik bulanan atau mingguan
- Trend pengeluaran dan pemasukan
- Analisis perbandingan antar periode

### 4.3 Budgeting (Pengaturan Anggaran)
**Deskripsi:** Menetapkan batas pengeluaran dan memantau progres pengeluaran

**Mekanisme:**
- Pengguna menetapkan budget untuk kategori pengeluaran
- Sistem membandingkan pengeluaran aktual dengan budget
- Memberikan indikator visual progres
- Mengirim notifikasi ketika anggaran hampir habis atau terlampaui

**Output:**
- Visualisasi progres anggaran
- Indikator visual (warna atau bar)
- Notifikasi peringatan
- Status anggaran (aman, peringatan, terlampaui)

### 4.4 Tabungan (Financial Goal)
**Deskripsi:** Membuat dan memantau tujuan tabungan

**Mekanisme:**
- Pengguna membuat target tabungan dengan menentukan:
  - Nama tujuan
  - Nominal target
  - Jangka waktu
  - Sumber dana
- Sistem secara otomatis melacak progres berdasarkan transaksi
- Menampilkan visualisasi progress bar

**Output:**
- Progress bar pencapaian
- Persentase capaian
- Estimasi waktu tercapainya target
- Notifikasi ketika target tercapai

---

## 5. ALUR PENGGUNAAN (USER FLOW)

### Step 1: Pendaftaran dan Setup Awal
**Deskripsi:** Pengguna membuat akun dan melakukan konfigurasi awal

**Proses:**
1. Membuat akun melalui email
2. Mengisi saldo awal untuk setiap akun/rekening
3. Memilih mata uang
4. Memilih bahasa preferensi
5. Menentukan tujuan utama (mengatur pengeluaran, melacak tagihan, menabung, dst)
6. Opsional: Mengatur keamanan (PIN, password, 2FA)

**Output:**
- Akun pengguna tercipta
- Profil konfigurasi tersimpan
- Akun siap digunakan

---

### Step 2: Beranda (Dashboard)
**Deskripsi:** Tampilan utama setelah login yang menampilkan ringkasan keuangan

**Konten Dashboard:**
- Total saldo dari semua akun
- Jumlah pemasukan bulan berjalan
- Jumlah pengeluaran bulan berjalan
- Grafik pengeluaran per kategori
- Tombol cepat untuk menambah transaksi baru
- Ringkasan kondisi keuangan

**Fungsi:**
- Memantau kesehatan finansial secara cepat
- Akses mudah ke fitur utama
- Motivasi pengguna melalui visualisasi data

---

### Step 3: Menambahkan Transaksi
**Deskripsi:** Memasukkan data transaksi baru (pemasukan/pengeluaran)

**Proses:**
1. Pengguna menekan tombol "+" atau menu "Tambah Transaksi"
2. Memilih jenis transaksi:
   - Pemasukan
   - Pengeluaran
   - Transfer antar akun
3. Mengisi detail transaksi:
   - Nominal/jumlah uang
   - Kategori
   - Akun sumber/tujuan
   - Tanggal transaksi
   - Catatan/deskripsi tambahan
   - Opsional: Foto struk/bukti
4. Menyimpan transaksi

**Otomatisasi:**
- Saldo akun diperbarui otomatis
- Laporan dan grafik diperbarui secara real-time
- Data tersinkron ke cloud (jika diaktifkan)

---

### Step 4: Mengatur Kategori dan Anggaran
**Deskripsi:** Membuat kategori pengeluaran dan menetapkan batasan budget

**Proses Kategori:**
1. Pengguna membuat kategori pengeluaran (misal: Makanan, Transportasi, Hiburan)
2. Membuat subkategori jika diperlukan (misal: Makanan → Makan Di Luar, Groceries)

**Proses Budgeting:**
1. Masuk ke menu "Anggaran" atau "Budgeting"
2. Membuat budget baru dengan:
   - Kategori yang dituju
   - Nominal batas pengeluaran
   - Periode (bulanan, mingguan, custom)
3. Sistem otomatis melacak pengeluaran per kategori
4. Menampilkan progress dengan:
   - Indikator warna (hijau: aman, kuning: hati-hati, merah: terlampaui)
   - Progress bar persentase
   - Notifikasi alert

---

### Step 5: Melihat Laporan & Analisis
**Deskripsi:** Melihat laporan keuangan terperinci dan analisis mendalam

**Fitur Laporan:**
1. Akses menu "Laporan" atau "Statistik"
2. Pilih periode laporan:
   - Harian
   - Mingguan
   - Bulanan
   - Tahunan
   - Custom range

**Konten Laporan:**
- Grafik pengeluaran per kategori
- Perbandingan pemasukan vs pengeluaran
- Arus kas (cash flow)
- Trend pengeluaran
- Detail transaksi
- Ringkasan category breakdown

**Fitur Export:**
- Ekspor ke PDF
- Ekspor ke Excel
- Share laporan
- Print laporan

---

### Step 6: Rencana Keuangan & Tabungan
**Deskripsi:** Membuat dan memantau target keuangan jangka panjang

**Proses Membuat Target:**
1. Masuk ke menu "Target Tabungan" atau "Financial Goals"
2. Klik "Buat Target Baru"
3. Isi detail target:
   - Nama target (misal: "Liburan ke Bali")
   - Nominal target
   - Target date / Jangka waktu
   - Sumber dana (dari akun mana)
   - Catatan tambahan
4. Simpan target

**Sistem Tracking Otomatis:**
- Aplikasi melacak transaksi pengguna
- Menghitung akumulasi dana untuk target tertentu
- Menampilkan progress bar real-time
- Persentase pencapaian
- Estimasi tanggal tercapai

**Notifikasi:**
- Alert ketika progress mencapai milestone (50%, 75%, 100%)
- Reminder untuk menabung lebih banyak

---

### Step 7: Pengingat & Transaksi Otomatis
**Deskripsi:** Membantu pengguna agar konsisten mencatat dan mengatur pengeluaran rutin

**Fitur Pengingat:**
1. Pengguna dapat menetapkan reminder untuk:
   - Mencatat transaksi harian
   - Cek anggaran
   - Review laporan mingguan
2. Aplikasi mengirim notifikasi push sesuai jadwal

**Fitur Transaksi Berulang:**
1. Pengguna dapat membuat transaksi recurring untuk:
   - Gaji bulanan
   - Langganan (Netflix, Spotify)
   - Pembayaran cicilan
   - Tagihan bulanan (listrik, air)
2. Detail transaksi berulang:
   - Nama transaksi
   - Nominal
   - Frekuensi (harian, mingguan, bulanan, tahunan)
   - Tanggal mulai dan akhir (opsional)
3. Sistem akan otomatis mencatat transaksi sesuai jadwal

**Fitur Notifikasi Jatuh Tempo:**
- Mengirim alert jika ada tagihan mendekati tanggal jatuh tempo
- Mengingatkan tagihan yang sudah lewat

---

### Step 8: Backup & Sinkronisasi Data
**Deskripsi:** Menjaga keamanan data dan akses lintas perangkat

**Opsi Sinkronisasi Cloud:**
- Pengguna dapat mengaktifkan cloud sync
- Data tersimpan online dengan enkripsi
- Akses dari berbagai perangkat
- Data otomatis tersinkron real-time

**Backup Manual:**
1. Fitur backup manual tersedia di menu pengaturan
2. Opsi backup:
   - Ke Google Drive
   - Ke penyimpanan lokal (file export)
   - Ke cloud storage lainnya
3. Proses backup:
   - Pilih tanggal range data
   - Pilih lokasi penyimpanan
   - Konfirmasi
   - Backup selesai

**Restore Data:**
- Ketika login di perangkat baru, data otomatis diunduh dari cloud
- Opsi restore manual dari file backup
- Pemulihan data berlangsung seamless

---

### Step 9: Penggunaan Rutin
**Deskripsi:** Panduan penggunaan aplikasi secara berkelanjutan dalam kehidupan sehari-hari

**Routine Harian:**
1. Buka dashboard setiap pagi/sore
2. Cek saldo saat ini
3. Catat transaksi baru
4. Lihat notifikasi pengingat
5. Monitor anggaran harian

**Routine Mingguan:**
1. Review pengeluaran minggu lalu
2. Lihat performa terhadap budget
3. Rencana pengeluaran minggu depan
4. Ubah kategori atau budget jika perlu

**Routine Bulanan:**
1. Lihat laporan bulanan lengkap
2. Analisis pengeluaran per kategori
3. Evaluasi target tabungan
4. Sesuaikan budget untuk bulan depan
5. Identifikasi pengeluaran yang bisa dikurangi

**Tips untuk Disiplin Finansial:**
- Catat setiap transaksi secara konsisten
- Jangan lewatkan reminder
- Review laporan secara berkala
- Sesuaikan target dengan realitas

---

## 6. STRUKTUR DATA / ENTITAS

### 6.1 User Account
```
User
├── user_id (Primary Key)
├── email
├── password (hashed)
├── username
├── nama_lengkap
├── mata_uang (currency)
├── bahasa (language)
├── pin (optional)
├── created_at
├── updated_at
└── is_active
```

### 6.2 Account / Rekening
```
Account
├── account_id (Primary Key)
├── user_id (Foreign Key)
├── nama_akun (Bank, E-wallet, Tunai, dst)
├── tipe_akun (Bank, E-wallet, Cash, Savings)
├── saldo_awal
├── saldo_saat_ini
├── mata_uang
├── created_at
└── updated_at
```

### 6.3 Category / Kategori
```
Category
├── category_id (Primary Key)
├── user_id (Foreign Key)
├── nama_kategori
├── tipe_kategori (Income/Expense)
├── deskripsi
├── icon/emoji
├── color
├── created_at
└── updated_at
```

### 6.4 Sub-Category
```
SubCategory
├── subcategory_id (Primary Key)
├── category_id (Foreign Key)
├── nama_subcategory
├── deskripsi
├── icon/emoji
└── created_at
```

### 6.5 Transaction
```
Transaction
├── transaction_id (Primary Key)
├── user_id (Foreign Key)
├── account_id (Foreign Key)
├── category_id (Foreign Key)
├── subcategory_id (Foreign Key, nullable)
├── tipe_transaksi (Income/Expense/Transfer)
├── nominal
├── tanggal
├── deskripsi/catatan
├── bukti_file (photo attachment, nullable)
├── is_recurring (boolean)
├── created_at
└── updated_at
```

### 6.6 Recurring Transaction
```
RecurringTransaction
├── recurring_id (Primary Key)
├── user_id (Foreign Key)
├── nama_transaksi
├── deskripsi
├── nominal
├── account_id (Foreign Key)
├── category_id (Foreign Key)
├── frekuensi (daily/weekly/monthly/yearly)
├── tanggal_mulai
├── tanggal_akhir (nullable)
├── last_executed
├── next_execution
├── is_active
├── created_at
└── updated_at
```

### 6.7 Budget
```
Budget
├── budget_id (Primary Key)
├── user_id (Foreign Key)
├── category_id (Foreign Key)
├── nominal_batas
├── periode (weekly/monthly/custom)
├── tanggal_mulai
├── tanggal_akhir
├── alert_threshold (%)
├── is_active
├── created_at
└── updated_at
```

### 6.8 Financial Goal / Target Tabungan
```
FinancialGoal
├── goal_id (Primary Key)
├── user_id (Foreign Key)
├── nama_tujuan
├── deskripsi
├── nominal_target
├── nominal_saat_ini
├── sumber_dana (account_id, nullable)
├── target_date
├── kategori
├── priority
├── is_completed
├── completed_date (nullable)
├── created_at
└── updated_at
```

### 6.9 Reminder
```
Reminder
├── reminder_id (Primary Key)
├── user_id (Foreign Key)
├── tipe (transaction_record/budget_review/goal_check)
├── deskripsi
├── jadwal (cron atau specific time)
├── frekuensi
├── last_sent
├── next_send
├── is_active
├── created_at
└── updated_at
```

### 6.10 Backup
```
Backup
├── backup_id (Primary Key)
├── user_id (Foreign Key)
├── backup_date
├── backup_type (manual/auto)
├── backup_location (cloud/local/gdrive)
├── file_size
├── status (success/failed)
├── created_at
└── notes
```

---

## 7. PROSES/ALUR BISNIS

### 7.1 Proses Pencatatan Transaksi
```
START
  ↓
Pengguna buka aplikasi
  ↓
Pengguna click "+ Tambah Transaksi"
  ↓
Pilih jenis transaksi (Income/Expense/Transfer)
  ↓
Isi detail:
- Nominal
- Kategori/SubKategori
- Akun sumber/tujuan
- Tanggal
- Catatan
- Bukti (opsional)
  ↓
Simpan transaksi
  ↓
Sistem UPDATE saldo akun
  ↓
Sistem UPDATE laporan & grafik
  ↓
Notifikasi "Transaksi berhasil disimpan"
  ↓
END
```

### 7.2 Proses Setup Awal
```
START
  ↓
User registrasi dengan email
  ↓
Verifikasi email
  ↓
Setup profil:
- Nama lengkap
- Preferensi bahasa
- Mata uang
  ↓
Isi saldo awal akun
  ↓
Pilih tujuan utama
  ↓
Opsional: Setup keamanan (PIN)
  ↓
Profil selesai, akun siap pakai
  ↓
Dashboard pertama kali ditampilkan
  ↓
END
```

### 7.3 Proses Budgeting
```
START
  ↓
User buka menu "Anggaran"
  ↓
Klik "Buat Anggaran Baru"
  ↓
Pilih kategori & nominal batas
  ↓
Atur periode (weekly/monthly)
  ↓
Simpan budget
  ↓
Sistem aktif tracking pengeluaran untuk kategori tsb
  ↓
Setiap transaksi dicek:
- Jika pengeluaran < 80% budget → Status AMAN
- Jika 80% ≤ pengeluaran ≤ 100% → Kirim NOTIFIKASI
- Jika pengeluaran > 100% → Status TERLAMPAUI
  ↓
Dashboard menampilkan progress bar anggaran
  ↓
END
```

### 7.4 Proses Pembuatan Target Tabungan
```
START
  ↓
User buka "Target Tabungan"
  ↓
Klik "Buat Target Baru"
  ↓
Isi data:
- Nama target
- Nominal target
- Jangka waktu
- Sumber dana
  ↓
Simpan target
  ↓
Sistem mencatat nominal_target
  ↓
Setiap transaksi dari sumber dana tercatat:
- Sistem cek apakah untuk target ini
- Update nominal_saat_ini
- Hitung progres (%)
  ↓
Jika saldo akun bertambah:
- Sistem otomatis tambah nominal_saat_ini
  ↓
Tampilkan progress bar real-time
  ↓
Jika nominal_saat_ini ≥ nominal_target:
- Status TERCAPAI
- Kirim notifikasi congratulation
- Mark is_completed = TRUE
  ↓
END
```

### 7.5 Proses Laporan & Analisis
```
START
  ↓
User buka menu "Laporan"
  ↓
Pilih periode (daily/weekly/monthly/yearly/custom)
  ↓
Sistem query transaksi sesuai periode
  ↓
Agregasi data:
- Total pemasukan
- Total pengeluaran
- Breakdown per kategori
- Net income
  ↓
Generate grafik:
- Pie chart (pengeluaran per kategori)
- Line chart (trend pemasukan/pengeluaran)
- Bar chart (perbandingan bulanan)
  ↓
Tampilkan laporan lengkap
  ↓
Opsi: Export ke PDF/Excel/Share
  ↓
END
```

### 7.6 Proses Transaksi Berulang
```
START
  ↓
User buat "Transaksi Berulang" (misal: gaji bulanan)
  ↓
Isi:
- Nama transaksi
- Nominal
- Frekuensi (monthly)
- Tanggal (misal: setiap tanggal 1)
  ↓
Sistem set next_execution = 1 Januari 2024
  ↓
Setiap harinya (automated job):
- Cek apakah ada transaksi recurring
- Jika next_execution = hari ini
- Create transaksi otomatis
- Update saldo akun
- Set next_execution = 1 Februari 2024
  ↓
User lihat transaksi otomatis di laporan
  ↓
Notifikasi "Transaksi berulang tereksekusi"
  ↓
END
```

### 7.7 Proses Backup & Sinkronisasi
```
START
  ↓
User masuk menu "Pengaturan"
  ↓

Option A: CLOUD SYNC
  → Enable cloud sync
  → Sistem otomatis sync data setiap 5 menit
  → Data terenkripsi di server cloud
  → Login di device lain → data otomatis muncul
  ↓

Option B: MANUAL BACKUP
  → Klik "Backup Sekarang"
  → Pilih lokasi: Google Drive / Local / Cloud
  → Sistem export semua data user
  → File disimpan dengan timestamp
  → Notifikasi "Backup selesai"
  ↓

Option C: RESTORE
  → Login di device baru
  → Sistem deteksi ada backup di cloud
  → Ask user "Restore data?"
  → User confirm
  → Data restore otomatis
  → Semua transaksi, budget, goals muncul
  ↓
END
```

---

## 8. PERSYARATAN TEKNIS

### 8.1 Persyaratan Fungsional
- ✅ Fitur pencatatan transaksi (pemasukan/pengeluaran/transfer)
- ✅ Fitur kategorisasi transaksi
- ✅ Fitur budgeting dengan alert
- ✅ Fitur target tabungan
- ✅ Fitur laporan & grafik analisis
- ✅ Fitur transaksi berulang otomatis
- ✅ Fitur pengingat/reminder
- ✅ Fitur sinkronisasi cloud
- ✅ Fitur backup/restore data
- ✅ Dashboard ringkasan keuangan

### 8.2 Persyaratan Non-Fungsional

#### Keamanan
- Password hashing (bcrypt/Argon2)
- Enkripsi data sensitif
- Opsi autentikasi 2FA
- PIN protection (opsional)
- Secure cloud communication (HTTPS/TLS)

#### Performa
- Response time < 2 detik untuk operasi standar
- Support 10,000+ transaksi per akun
- Optimasi database queries
- Caching untuk laporan sering diakses

#### Availability
- Uptime 99%
- Backup otomatis setiap 24 jam
- Disaster recovery plan
- Load balancing untuk high traffic

#### Scalability
- Arsitektur microservices
- Database yang scalable
- Cloud infrastructure
- Support pertumbuhan pengguna

#### Usability
- UI/UX yang intuitif
- Responsive design (mobile & desktop)
- Dokumentasi user yang jelas
- Tutorial dan help section

#### Kompatibilitas
- Cross-platform (Web, Android, iOS)
- Multiple currencies
- Multiple languages
- Browser compatibility

### 8.3 Technology Stack (Recommended)

#### Backend
- Framework: Spring Boot / Node.js / Django
- Database: PostgreSQL / MySQL
- Cache: Redis
- Cloud: AWS / Google Cloud / Azure

#### Frontend
- Web: React / Vue / Angular
- Mobile: React Native / Flutter
- State Management: Redux / Vuex
- UI Library: Material UI / Bootstrap

#### Infrastructure
- Container: Docker
- Orchestration: Kubernetes
- CI/CD: Jenkins / GitHub Actions
- Monitoring: ELK Stack / Prometheus

### 8.4 Persyaratan API
- RESTful API design
- JSON format for data exchange
- Rate limiting
- API versioning
- Comprehensive API documentation (Swagger/OpenAPI)

### 8.5 Database Schema Relationships
```
User (1) ──→ (∞) Account
         ──→ (∞) Category
         ──→ (∞) Transaction
         ──→ (∞) Budget
         ──→ (∞) FinancialGoal
         ──→ (∞) RecurringTransaction

Account (1) ──→ (∞) Transaction

Category (1) ──→ (∞) SubCategory
            ──→ (∞) Transaction
            ──→ (∞) Budget

SubCategory (1) ──→ (∞) Transaction

Budget (1) ──→ (∞) Transactions (relasi calculation)

FinancialGoal (1) ──→ (∞) Transactions (tracking)
```

---

## 9. RINGKASAN

### Fitur Utama
| Fitur | Deskripsi | Benefit |
|-------|-----------|---------|
| **Pencatatan** | Catat semua transaksi | Transparan & terorganisir |
| **Ringkasan Keuangan** | Grafik & report | Visualisasi kondisi finansial |
| **Budgeting** | Set limit pengeluaran | Kontrol pengeluaran |
| **Tabungan** | Track target keuangan | Motivasi menabung |
| **Laporan Analisis** | Report detail & export | Insight mendalam |
| **Transaksi Otomatis** | Recurring transactions | Efisiensi pencatatan |
| **Pengingat** | Notifikasi reminder | Konsistensi penggunaan |
| **Sinkronisasi** | Cloud backup & sync | Aman & akses lintas device |

### Target User
- Mahasiswa (utama)
- Young professionals
- Siapa saja yang ingin belajar financial literacy

### Success Metrics
- ✅ Pengguna rutin mencatat transaksi
- ✅ Pengguna disiplin dengan budget
- ✅ Peningkatan financial awareness
- ✅ Tercapainya target keuangan
- ✅ Kebiasaan finansial yang sehat

---

**Dokumen ini merangkum SEMUA fitur, mekanisme, requirement, dan proses bisnis dari Aplikasi Keuangan Pribadi secara terstruktur dan komprehensif.**
