#!/bin/bash
# ===================================================
# Aplikasi Keuangan Pribadi - Compile & Run Script
# ===================================================

echo ""
echo "========================================"
echo "  Aplikasi Keuangan Pribadi"
echo "  Auto Compile & Run Script"
echo "========================================"
echo ""

# Create bin directory
if [ ! -d "bin" ]; then
    echo "[*] Creating bin directory..."
    mkdir -p bin
fi

# Compile Backend Models
echo "[1/6] Compiling Backend Models..."
javac -d bin src/backend/models/*.java
if [ $? -ne 0 ]; then
    echo "[ERROR] Failed to compile Backend Models"
    exit 1
fi

# Compile Backend Services
echo "[2/6] Compiling Backend Services..."
javac -d bin -cp bin src/backend/services/*.java
if [ $? -ne 0 ]; then
    echo "[ERROR] Failed to compile Backend Services"
    exit 1
fi

# Compile Backend Utils
echo "[3/6] Compiling Backend Utils..."
javac -d bin -cp bin src/backend/utils/*.java
if [ $? -ne 0 ]; then
    echo "[ERROR] Failed to compile Backend Utils"
    exit 1
fi

# Compile Frontend UI
echo "[4/6] Compiling Frontend UI..."
javac -d bin -cp bin src/frontend/ui/*.java
if [ $? -ne 0 ]; then
    echo "[ERROR] Failed to compile Frontend UI"
    exit 1
fi

# Compile Frontend Controller
echo "[5/6] Compiling Frontend Controller..."
javac -d bin -cp bin src/frontend/controller/*.java
if [ $? -ne 0 ]; then
    echo "[ERROR] Failed to compile Frontend Controller"
    exit 1
fi

# Compile Main Application
echo "[6/6] Compiling Main Application..."
javac -d bin -cp bin src/AplikasiKeuanganPribadi.java
if [ $? -ne 0 ]; then
    echo "[ERROR] Failed to compile Main Application"
    exit 1
fi

# Success message
echo ""
echo "========================================"
echo "  SUCCESS! All files compiled!"
echo "========================================"
echo ""
echo "Starting Aplikasi Keuangan Pribadi..."
echo ""

# Run Application
java -cp bin AplikasiKeuanganPribadi
