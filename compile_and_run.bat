@echo off
REM ===================================================
REM Aplikasi Keuangan Pribadi - Compile & Run Script
REM ===================================================

setlocal enabledelayedexpansion

echo.
echo ========================================
echo  Aplikasi Keuangan Pribadi
echo  Auto Compile & Run Script
echo ========================================
echo.

REM Create bin directory
if not exist "bin" (
    echo [*] Creating bin directory...
    mkdir bin
)

REM Compile Backend Models
echo [1/6] Compiling Backend Models...
javac -d bin src\backend\models\*.java
if errorlevel 1 (
    echo [ERROR] Failed to compile Backend Models
    pause
    exit /b 1
)

REM Compile Backend Services
echo [2/6] Compiling Backend Services...
javac -d bin -cp bin src\backend\services\*.java
if errorlevel 1 (
    echo [ERROR] Failed to compile Backend Services
    pause
    exit /b 1
)

REM Compile Backend Utils
echo [3/6] Compiling Backend Utils...
javac -d bin -cp bin src\backend\utils\*.java
if errorlevel 1 (
    echo [ERROR] Failed to compile Backend Utils
    pause
    exit /b 1
)

REM Compile Frontend UI
echo [4/6] Compiling Frontend UI...
javac -d bin -cp bin src\frontend\ui\*.java
if errorlevel 1 (
    echo [ERROR] Failed to compile Frontend UI
    pause
    exit /b 1
)

REM Compile Frontend Controller
echo [5/6] Compiling Frontend Controller...
javac -d bin -cp bin src\frontend\controller\*.java
if errorlevel 1 (
    echo [ERROR] Failed to compile Frontend Controller
    pause
    exit /b 1
)

REM Compile Main Application
echo [6/6] Compiling Main Application...
javac -d bin -cp bin src\AplikasiKeuanganPribadi.java
if errorlevel 1 (
    echo [ERROR] Failed to compile Main Application
    pause
    exit /b 1
)

REM Success message
echo.
echo ========================================
echo  SUCCESS! All files compiled!
echo ========================================
echo.
echo Starting Aplikasi Keuangan Pribadi...
echo.

REM Run Application
java -cp bin AplikasiKeuanganPribadi

pause
