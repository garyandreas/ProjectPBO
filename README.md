# 📊 APLIKASI KEUANGAN PRIBADI - DOKUMENTASI LENGKAP

## 📋 Daftar Isi
1. [Pengenalan](#pengenalan)
2. [Fitur Utama](#fitur-utama)
3. [Struktur Arsitektur](#struktur-arsitektur)
4. [Class Diagram](#class-diagram)
5. [Panduan Menggunakan](#panduan-menggunakan)
6. [Struktur Folder](#struktur-folder)

---

## 🎯 Pengenalan

**Aplikasi Keuangan Pribadi** adalah aplikasi desktop berbasis Java OOP yang dirancang untuk membantu pengguna mengelola keuangan pribadi mereka dengan fitur-fitur lengkap seperti pencatatan transaksi, budgeting, target tabungan, dan laporan keuangan.

### Teknologi yang Digunakan:
- **Bahasa**: Java 8+
- **GUI Framework**: Java Swing
- **Arsitektur**: Model-View-Controller (MVC)
- **Pola Design**: Service Pattern, Singleton Pattern

---

## ✨ Fitur Utama

### 1. **Autentikasi Pengguna**
   - Registrasi akun baru
   - Login dengan username & password
   - Profil pengguna yang dapat diperbarui

### 2. **Manajemen Account/Rekening**
   - Membuat multiple akun (Bank, E-wallet, Cash)
   - Tracking saldo real-time
   - Transfer antar akun
   - Tipe akun: BANK, E_WALLET, CASH, INVESTMENT

### 3. **Pencatatan Transaksi**
   - Catat pemasukan (INCOME)
   - Catat pengeluaran (EXPENSE)
   - Kategori & subkategori transaksi
   - Deskripsi dan catatan transaksi
   - Tracking transaksi berdasarkan tanggal, kategori, atau akun

### 4. **Kategori & Subkategori**
   - Kategori default: Gaji, Bonus, Makanan, Transportasi, Hiburan, dll
   - Custom kategori sesuai kebutuhan
   - Subkategori untuk detail lebih lanjut (Restoran, Bensin, Bioskop, dll)

### 5. **Budget Management**
   - Atur batasan pengeluaran per kategori
   - Tracking pengeluaran real-time
   - Alert system (80%, 100%, dan melebihi batas)
   - Status budget: ✅ Aman, ⚠️ Hati-hati, 🔴 Mendekati Batas, ❌ Terlampaui
   - Periode budget: DAILY, WEEKLY, MONTHLY, YEARLY

### 6. **Target Tabungan (Financial Goals)**
   - Buat goal dengan target amount dan tanggal target
   - Track progress otomatis
   - Tambah dana ke goal
   - Status goal: ACTIVE, COMPLETED, ABANDONED
   - Rekomendasi tabungan harian
   - Priority: HIGH, MEDIUM, LOW

### 7. **Transaksi Berulang**
   - Setup transaksi otomatis (gaji, sewa, dll)
   - Pola berulang: DAILY, WEEKLY, MONTHLY, YEARLY
   - Eksekusi otomatis sesuai jadwal
   - Opsi end date (jika ada)

### 8. **Laporan & Analisis**
   - Laporan bulanan
   - Laporan tahunan
   - Breakdown pengeluaran per kategori
   - Statistik income vs expense
   - Format currency: Rupiah

### 9. **Dashboard**
   - Summary cards (Total Saldo, Income, Expense, Saving)
   - Visualisasi data
   - Quick access ke fitur utama

---

## 🏗️ Struktur Arsitektur

### Model-View-Controller (MVC)

```
┌─────────────────────────────────────────────────┐
│             PRESENTATION LAYER                  │
│  (Frontend - GUI dengan Java Swing)             │
│  - LoginFrame                                   │
│  - MainFrame                                    │
│  - RegisterDialog                               │
│  - UI Components                                │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│          CONTROLLER LAYER                       │
│  ApplicationController                          │
│  - Event Handling                               │
│  - Business Logic Orchestration                 │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│           SERVICE LAYER                         │
│  - UserService                                  │
│  - AccountService                               │
│  - CategoryService                              │
│  - TransactionService                           │
│  - BudgetService                                │
│  - FinancialGoalService                         │
│  - ReportGenerator (Utility)                    │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│            MODEL LAYER                          │
│  Data Classes (POJOs):                          │
│  - User                                         │
│  - Account                                      │
│  - Category, SubCategory                        │
│  - Transaction                                  │
│  - Budget                                       │
│  - FinancialGoal                                │
│  - RecurringTransaction                         │
└─────────────────────────────────────────────────┘
```

---

## 📊 Class Diagram

### Backend Models:

```
User (id, username, email, password, currency)
  │
  ├─→ Account (id, userId, type, balance)
  │     │
  │     ├─→ Transaction (id, amount, type, category)
  │     │
  │     └─→ RecurringTransaction (id, pattern, frequency)
  │
  ├─→ Category (id, type, name) 
  │     │
  │     └─→ SubCategory (id, name)
  │
  ├─→ Budget (id, categoryId, limit, spent, period)
  │
  └─→ FinancialGoal (id, name, target, current, status)
```

### Backend Services:

```
UserService
├─ registerUser()
├─ loginUser()
├─ updateUser()
└─ getAllUsers()

AccountService
├─ createAccount()
├─ getAccountsByUser()
├─ addBalance()
├─ deductBalance()
└─ transferBalance()

CategoryService
├─ createCategory()
├─ createSubCategory()
├─ getCategoriesByType()
└─ createDefaultCategories()

TransactionService
├─ createTransaction()
├─ getTransactionsByUser()
├─ getTransactionsByMonth()
├─ getTotalIncome()
└─ getTotalExpense()

BudgetService
├─ createBudget()
├─ updateBudgetSpent()
├─ sendAlert()
└─ getBudgetStatus()

FinancialGoalService
├─ createGoal()
├─ addFundsToGoal()
├─ getActiveGoals()
├─ getDailyRecommendation()
└─ getCompletedGoals()
```

---

## 📁 Struktur Folder

```
ProjectPBOPraktikum/
├── AplikasiKeuanganPribadi.java          (Main Entry Point)
├── README.md                              (File ini)
│
├── src/
│   ├── backend/
│   │   ├── models/
│   │   │   ├── User.java
│   │   │   ├── Account.java
│   │   │   ├── Category.java
│   │   │   ├── SubCategory.java
│   │   │   ├── Transaction.java
│   │   │   ├── Budget.java
│   │   │   ├── FinancialGoal.java
│   │   │   └── RecurringTransaction.java
│   │   │
│   │   ├── services/
│   │   │   ├── UserService.java
│   │   │   ├── AccountService.java
│   │   │   ├── CategoryService.java
│   │   │   ├── TransactionService.java
│   │   │   ├── BudgetService.java
│   │   │   └── FinancialGoalService.java
│   │   │
│   │   └── utils/
│   │       └── ReportGenerator.java
│   │
│   └── frontend/
│       ├── ui/
│       │   ├── MainFrame.java
│       │   ├── LoginFrame.java
│       │   └── RegisterDialog.java
│       │
│       └── controller/
│           └── ApplicationController.java
│
└── Aplikasi Keuangan Pribadi.pdf        (Requirements Document)
```

---

## 🚀 Panduan Menggunakan

### Menjalankan Aplikasi:

```bash
# Compile semua file Java
javac -d bin src/backend/models/*.java
javac -d bin src/backend/services/*.java
javac -d bin src/backend/utils/*.java
javac -d bin src/frontend/ui/*.java
javac -d bin src/frontend/controller/*.java
javac -cp bin AplikasiKeuanganPribadi.java

# Jalankan aplikasi
java -cp bin AplikasiKeuanganPribadi
```

### Alur Penggunaan:

#### 1. **Login/Registrasi**
   ```
   ┌─────────────────────────┐
   │  Aplikasi Keuangan      │
   │  Login Screen           │
   │                         │
   │  [Username Field]       │
   │  [Password Field]       │
   │  [Login] [Daftar]       │
   └─────────────────────────┘
   ```

#### 2. **Dashboard Utama**
   - Lihat summary saldo dan transaksi
   - Akses ke 5 menu utama: Dashboard, Transaksi, Budget, Goal, Laporan

#### 3. **Pencatatan Transaksi**
   ```
   Tipe: Pengeluaran
   Kategori: Makanan
   Jumlah: 50000
   Deskripsi: Makan di Restoran
   [Tambah Transaksi]
   ```

#### 4. **Manajemen Budget**
   ```
   Kategori: Makanan
   Budget: 2000000
   Alert Threshold: 80%
   [Simpan Budget]
   ```

#### 5. **Target Tabungan**
   ```
   Nama Goal: Liburan ke Bali
   Target: 5000000
   Target Date: 2024-06-30
   [Buat Goal]
   ```

---

## 💡 Konsep OOP yang Diterapkan

### 1. **Encapsulation** (Enkapsulasi)
```java
public class Account {
    private int accountId;           // Private field
    private double balance;
    
    public double getBalance() {     // Public getter
        return balance;
    }
    
    public void addBalance(double amount) {  // Public method
        this.balance += amount;
    }
}
```

### 2. **Inheritance** (Pewarisan)
```java
public enum TransactionType {
    INCOME, EXPENSE, TRANSFER
}
// DiGunakan di Transaction dan RecurringTransaction
```

### 3. **Polymorphism** (Polimorfisme)
```java
// Service dengan method overloading
getTransactionsByUser(int userId)
getTransactionsByAccount(int accountId)
getTransactionsByCategory(int categoryId)
getTransactionsByMonth(int userId, YearMonth month)
```

### 4. **Abstraction** (Abstraksi)
```java
// Service layer menyembunyikan kompleksitas dari UI
public boolean createTransaction(...) {
    // Complex business logic tersembunyi
    // UI hanya perlu memanggil method ini
}
```

---

## 📈 Fitur Lanjutan

### 1. **Budget Alert System**
```
Pengeluaran < 50%  → ✅ Aman
Pengeluaran 50-80% → ⚠️ Hati-hati
Pengeluaran 80-100% → 🔴 Mendekati Batas
Pengeluaran > 100%  → ❌ Sudah Terlampaui
```

### 2. **Recurring Transaction**
```
Contoh: Gaji bulanan
Pattern: MONTHLY
Day: 1 (tanggal 1 setiap bulan)
Amount: 10000000
→ Otomatis dicatat setiap bulannya
```

### 3. **Financial Goal Tracking**
```
Goal: Liburan Bali
Target: 5,000,000
Current: 2,000,000
Progress: 40%
Daily Recommendation: Rp 50,000
```

---

## 🔒 Fitur Keamanan

1. **Password Hashing** - Password di-hash sebelum disimpan
2. **User Validation** - Validasi setiap input pengguna
3. **Balance Validation** - Cek saldo sebelum transaksi
4. **Data Integrity** - Rollback transaksi jika gagal

---

## 📝 Catatan Pengembangan

### Yang Sudah Diimplementasi:
✅ Struktur Backend lengkap (Models & Services)
✅ Frontend dengan Java Swing
✅ Login & Registrasi
✅ Dashboard
✅ CRUD Operations
✅ Business Logic

### Untuk Pengembangan Lebih Lanjut:
🔲 Database integration (MySQL/PostgreSQL)
🔲 Data persistence (File I/O / Database)
🔲 Export laporan (PDF/Excel)
🔲 Chart visualization (JFreeChart)
🔲 Email notifications
🔲 Mobile app version
🔲 Web app version

---

## 👥 Tim Pengembang

**Praktikum PBO - Semester 3**
- Universitas Riau (UNRI)
- Kelas: PBO Praktikum

---

## 📞 Support & Contact

Untuk pertanyaan atau masukan tentang aplikasi ini, silahkan hubungi dosen pembimbing atau teaching assistant.

---

**Dibuat dengan ❤️ menggunakan Java OOP**
