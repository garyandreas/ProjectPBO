# PANDUAN SETUP & KOMPILASI

## 📋 Prasyarat
- Java Development Kit (JDK) 8 atau lebih tinggi
- IDE: IntelliJ IDEA, NetBeans, Eclipse, atau VS Code (dengan Java extension)
- Terminal/Command Prompt

---

## 🔧 Setup Project

### Step 1: Prepare Directory Structure
```bash
# Buat folder build untuk output
mkdir bin
mkdir out
```

### Step 2: Compile Backend Models
```bash
javac -d bin src/backend/models/User.java
javac -d bin src/backend/models/Account.java
javac -d bin src/backend/models/Category.java
javac -d bin src/backend/models/SubCategory.java
javac -d bin src/backend/models/Transaction.java
javac -d bin src/backend/models/Budget.java
javac -d bin src/backend/models/FinancialGoal.java
javac -d bin src/backend/models/RecurringTransaction.java
```

### Step 3: Compile Backend Services
```bash
javac -d bin -cp bin src/backend/services/UserService.java
javac -d bin -cp bin src/backend/services/AccountService.java
javac -d bin -cp bin src/backend/services/CategoryService.java
javac -d bin -cp bin src/backend/services/TransactionService.java
javac -d bin -cp bin src/backend/services/BudgetService.java
javac -d bin -cp bin src/backend/services/FinancialGoalService.java
```

### Step 4: Compile Backend Utils
```bash
javac -d bin -cp bin src/backend/utils/ReportGenerator.java
```

### Step 5: Compile Frontend UI
```bash
javac -d bin -cp bin src/frontend/ui/MainFrame.java
javac -d bin -cp bin src/frontend/ui/LoginFrame.java
javac -d bin -cp bin src/frontend/ui/RegisterDialog.java
```

### Step 6: Compile Frontend Controller
```bash
javac -d bin -cp bin src/frontend/controller/ApplicationController.java
```

### Step 7: Compile Main Application
```bash
javac -d bin -cp bin AplikasiKeuanganPribadi.java
```

### Step 8: Run Application
```bash
java -cp bin AplikasiKeuanganPribadi
```

---

## 🚀 Cara Cepat (Menggunakan Script)

### Windows (Batch File):
Buat file `compile.bat`:
```batch
@echo off
mkdir bin 2>nul

echo Compiling Backend Models...
javac -d bin src\backend\models\*.java

echo Compiling Backend Services...
javac -d bin -cp bin src\backend\services\*.java

echo Compiling Backend Utils...
javac -d bin -cp bin src\backend\utils\*.java

echo Compiling Frontend UI...
javac -d bin -cp bin src\frontend\ui\*.java

echo Compiling Frontend Controller...
javac -d bin -cp bin src\frontend\controller\*.java

echo Compiling Main Application...
javac -d bin -cp bin AplikasiKeuanganPribadi.java

echo Running Application...
java -cp bin AplikasiKeuanganPribadi

pause
```

Jalankan: `compile.bat`

### Linux/Mac (Shell Script):
Buat file `compile.sh`:
```bash
#!/bin/bash

mkdir -p bin

echo "Compiling Backend Models..."
javac -d bin src/backend/models/*.java

echo "Compiling Backend Services..."
javac -d bin -cp bin src/backend/services/*.java

echo "Compiling Backend Utils..."
javac -d bin -cp bin src/backend/utils/*.java

echo "Compiling Frontend UI..."
javac -d bin -cp bin src/frontend/ui/*.java

echo "Compiling Frontend Controller..."
javac -d bin -cp bin src/frontend/controller/*.java

echo "Compiling Main Application..."
javac -d bin -cp bin AplikasiKeuanganPribadi.java

echo "Running Application..."
java -cp bin AplikasiKeuanganPribadi
```

Jalankan: `bash compile.sh`

---

## 🎯 Menggunakan IDE

### IntelliJ IDEA:
1. File → Open → Pilih folder ProjectPBOPraktikum
2. Setup SDK (Project Structure → SDKs → Java 8+)
3. Mark src folder as Sources Root
4. Run → Run 'AplikasiKeuanganPribadi'

### NetBeans:
1. File → New Project → Java Project dengan Existing Sources
2. Pilih folder ProjectPBOPraktikum
3. Configure Build
4. F6 untuk run

### Eclipse:
1. File → New → Java Project
2. Uncheck "Use default location"
3. Browse ke ProjectPBOPraktikum
4. Right-click → Run As → Java Application

### VS Code:
1. Install Extension: "Extension Pack for Java"
2. Open folder ProjectPBOPraktikum
3. Run main class dengan click "Run" di atas main method

---

## 🐛 Troubleshooting

### Error: "The declared package does not match"
**Solusi**: Pastikan file sudah di folder yang benar
```
User.java harus di: src/backend/models/User.java
```

### Error: "Cannot find symbol"
**Solusi**: Pastikan compile dengan -cp bin untuk dependencies
```bash
javac -d bin -cp bin src/backend/models/User.java
```

### Error: "No class definition found"
**Solusi**: Pastikan semua file sudah di-compile sebelum menjalankan
```bash
# Compile semua file sebelum run
java -cp bin AplikasiKeuanganPribadi
```

---

## 📝 Testing

### Test User Registration:
1. Jalankan aplikasi
2. Klik "Daftar"
3. Isi form dengan data
4. Klik "Daftar"
5. Cek pesan sukses

### Test Login:
1. Gunakan username/password yang sudah didaftar
2. Klik "Login"
3. Seharusnya membuka Dashboard

### Test Transaction:
1. Pergi ke tab "Transaksi"
2. Isi form dengan:
   - Tipe: Pengeluaran
   - Kategori: Makanan
   - Jumlah: 50000
   - Deskripsi: Test
3. Klik "Tambah Transaksi"

---

## 📊 Struktur File Input/Output

### Tidak ada file eksternal (In-memory)
Saat ini aplikasi menyimpan data dalam memory. Untuk production:

```java
// Tambahkan FileI/O atau Database
// Contoh dengan FileI/O:
ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.dat"));
oos.writeObject(users);
oos.close();
```

---

## 📦 JAR Executable

### Buat JAR file:
```bash
# Buat manifest file
echo Main-Class: AplikasiKeuanganPribadi > manifest.txt

# Buat JAR
jar cfm AplikasiKeuangan.jar manifest.txt -C bin .

# Run JAR
java -jar AplikasiKeuangan.jar
```

---

**Happy Coding! 🎉**
