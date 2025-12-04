@echo off
echo ==========================================
echo      MEMBERSIHKAN DAN KOMPILASI ULANG
echo ==========================================

:: 1. Hapus folder bin lama agar bersih
if exist bin rmdir /s /q bin
mkdir bin

:: 2. Compile DARI ROOT (Main Class) 
:: Ini akan otomatis mengompilasi semua file lain yang dibutuhkan secara otomatis
echo Sedang mengompilasi...
javac -d bin -sourcepath src src/AplikasiKeuanganPribadi.java

:: 3. Cek Error
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] GAGAL KOMPILASI!
    echo Periksa pesan error di atas untuk detailnya.
    pause
    exit /b
)

echo.
echo [SUKSES] Aplikasi berjalan...
echo ==========================================
java -cp bin AplikasiKeuanganPribadi
pause