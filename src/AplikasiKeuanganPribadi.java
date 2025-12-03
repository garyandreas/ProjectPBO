import frontend.controller.ApplicationController;
import javax.swing.*;

/**
 * Main Entry Point Aplikasi Keuangan Pribadi
 * 
 * Aplikasi berbasis Java OOP dengan struktur:
 * - Backend: Models, Services, Utilities
 * - Frontend: UI Components, Controllers
 * 
 * Fitur Utama:
 * 1. Pencatatan Transaksi (Pemasukan & Pengeluaran)
 * 2. Manajemen Account/Rekening
 * 3. Kategori Pengeluaran
 * 4. Budget Tracking dengan Alert
 * 5. Target Tabungan
 * 6. Laporan & Analisis
 * 7. Transaksi Berulang
 * 
 * Author: Praktikum PBO
 * Date: 2024
 */
public class AplikasiKeuanganPribadi {
    
    public static void main(String[] args) {
        // Jalankan aplikasi di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            System.out.println("========================================");
            System.out.println("  APLIKASI KEUANGAN PRIBADI");
            System.out.println("  Praktikum PBO - Semester 3");
            System.out.println("========================================\n");
            
            try {
                // Initialize Application Controller - ini yang handle semua logic
                ApplicationController controller = new ApplicationController();
                
                // Tampilkan login frame dari controller (dengan event handlers)
                System.out.println("DEBUG: About to show login screen");
                controller.showLoginScreen();
                System.out.println("DEBUG: Login screen shown");
                
                System.out.println("✅ Aplikasi berhasil dimulai!");
                System.out.println("📝 Silahkan login atau daftar akun baru.\n");
            } catch (Exception e) {
                System.err.println("ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
