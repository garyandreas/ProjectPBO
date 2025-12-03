# 🔧 DEBUG GUIDE - Masalah & Solusi

## ❌ MASALAH YANG SUDAH DIPERBAIKI

### Masalah: "ApplicationController cannot be resolved"
**Penyebab:** 
- File structure tidak sesuai Java conventions
- `AplikasiKeuanganPribadi.java` ada di root folder (bukan di src)
- Import paths tidak cocok dengan struktur package

**Solusi (SUDAH DITERAPKAN):**
- ✅ Pindahkan `AplikasiKeuanganPribadi.java` ke `src/AplikasiKeuanganPribadi.java`
- ✅ Pastikan semua files punya package declaration yang benar
- ✅ Update compile scripts untuk compile dari folder yang benar

---

## ✅ STRUKTUR YANG BENAR

```
ProjectPBOPraktikum/
├── src/                              ← Source root (bukan package!)
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
│   │   ├── services/
│   │   │   ├── UserService.java
│   │   │   ├── AccountService.java
│   │   │   ├── CategoryService.java
│   │   │   ├── TransactionService.java
│   │   │   ├── BudgetService.java
│   │   │   └── FinancialGoalService.java
│   │   └── utils/
│   │       └── ReportGenerator.java
│   ├── frontend/
│   │   ├── ui/
│   │   │   ├── LoginFrame.java
│   │   │   ├── MainFrame.java
│   │   │   └── RegisterDialog.java
│   │   └── controller/
│   │       └── ApplicationController.java
│   └── AplikasiKeuanganPribadi.java  ← Main entry point (NO package!)
│
└── bin/                              ← Compiled output
```

---

## 🚀 COMPILE & RUN

### Windows
```batch
compile_and_run.bat
```

### Linux/Mac
```bash
bash compile_and_run.sh
```

### Manual (Step by Step)
```bash
cd c:\Mine\UNRI\Semester 3\PBO\ProjectPBOPraktikum

# Create output folder
mkdir bin

# 1. Compile Models
javac -d bin src\backend\models\*.java

# 2. Compile Services (needs models in classpath)
javac -d bin -cp bin src\backend\services\*.java

# 3. Compile Utils
javac -d bin -cp bin src\backend\utils\*.java

# 4. Compile Frontend UI
javac -d bin -cp bin src\frontend\ui\*.java

# 5. Compile Frontend Controller
javac -d bin -cp bin src\frontend\controller\*.java

# 6. Compile Main Entry Point
javac -d bin -cp bin src\AplikasiKeuanganPribadi.java

# 7. Run!
java -cp bin AplikasiKeuanganPribadi
```

---

## 🎯 KUNCI SUKSES

1. **Package Declaration yang BENAR:**
   ```java
   // ✅ BENAR - untuk file di src/backend/models/
   package backend.models;
   
   // ✅ BENAR - untuk file di src/frontend/ui/
   package frontend.ui;
   
   // ❌ SALAH - src bukan package!
   // package src.backend.models;  ← JANGAN!
   ```

2. **Import yang BENAR:**
   ```java
   // ✅ BENAR
   import backend.models.*;
   import frontend.controller.*;
   import frontend.ui.*;
   
   // ❌ SALAH - jangan pakai src!
   // import src.backend.models.*;  ← JANGAN!
   ```

3. **Compile Order yang BENAR:**
   - Models dulu (tidak punya dependency)
   - Services kedua (depends on Models)
   - Utils ketiga
   - Frontend UI keempat
   - Frontend Controller kelima
   - Main Entry Point terakhir

4. **Classpath yang BENAR:**
   ```bash
   # ✅ BENAR
   javac -d bin -cp bin src/backend/services/*.java
   
   # ❌ SALAH - path harus dari root project
   # cd src/backend/services
   # javac -d ../../bin *.java  ← Complicated!
   ```

---

## 🧪 TESTING

### Test 1: Check Compilation
```bash
# Should show no errors
javac -d bin src\AplikasiKeuanganPribadi.java
echo %errorlevel%  # Should be 0
```

### Test 2: Check Classes Generated
```bash
# Should show .class files
dir bin\backend\models\
dir bin\frontend\ui\
```

### Test 3: Run Application
```bash
java -cp bin AplikasiKeuanganPribadi
# Should print welcome message and open GUI
```

---

## ❓ TROUBLESHOOTING

### Error: "Package does not match expected"
**Solution:** 
- Check file location matches package declaration
- File `src/backend/models/User.java` harus punya `package backend.models;`
- File `src/frontend/ui/LoginFrame.java` harus punya `package frontend.ui;`

### Error: "Cannot find symbol"
**Solution:**
- Compile dependencies dulu (models sebelum services)
- Use `-cp bin` flag untuk tambahkan compiled classes ke classpath

### Error: "File not found"
**Solution:**
- Check path dari root project folder
- Windows: gunakan backslash `\` atau forward slash `/`
- Linux/Mac: gunakan forward slash `/`

### Error: "GUI not showing"
**Solution:**
- Aplikasi jalan di background (Swing)
- Check console output untuk error messages
- Verify all UI files compiled without error

---

## ✨ STATUS SEKARANG

✅ **SEMUA ERROR SUDAH DIPERBAIKI**
- Models: Compiled ✓
- Services: Compiled ✓
- Utils: Compiled ✓
- Frontend UI: Compiled ✓
- Frontend Controller: Compiled ✓
- Main Entry Point: Compiled ✓
- Application: RUNNING ✓

---

**DEBUG SELESAI! APLIKASI SIAP DIGUNAKAN! 🎉**
