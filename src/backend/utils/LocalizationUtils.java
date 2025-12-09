package backend.utils;

import java.util.HashMap;
import java.util.Map;

public class LocalizationUtils {
    private static String currentLanguage = "id"; // Default
    private static final Map<String, Map<String, String>> dictionary = new HashMap<>();

    static {
        // --- BAHASA INDONESIA ---
        Map<String, String> idMap = new HashMap<>();
        idMap.put("app.title", "Aplikasi Keuangan Pribadi");
        idMap.put("header.title", "Keuangan Pribadi");
        idMap.put("header.subtitle", "Kelola keuangan Anda dengan lebih baik");

        // Tabs
        idMap.put("tab.dashboard", "Dashboard");
        idMap.put("tab.transaction", "Transaksi");
        idMap.put("tab.budget", "Budget");
        idMap.put("tab.goal", "Target Tabungan");
        idMap.put("tab.report", "Laporan");

        // Dashboard Cards
        idMap.put("card.balance", "Total Saldo");
        idMap.put("card.income", "Pemasukan Bulan Ini");
        idMap.put("card.expense", "Pengeluaran Bulan Ini");
        idMap.put("card.saving", "Tabungan");

        // Transaction Form
        idMap.put("trans.title", "Tambah Transaksi");
        idMap.put("trans.account", "Rekening:");
        idMap.put("trans.type", "Tipe:");
        idMap.put("trans.category", "Kategori:");
        idMap.put("trans.amount", "Jumlah:");
        idMap.put("trans.desc", "Deskripsi:");
        idMap.put("trans.btn", "Tambah Transaksi");
        idMap.put("trans.type.income", "Pemasukan");
        idMap.put("trans.type.expense", "Pengeluaran");

        // Budget
        idMap.put("budget.add", "Tambah Budget");
        idMap.put("col.category", "Kategori");
        idMap.put("col.budget", "Budget");
        idMap.put("col.used", "Terpakai");
        idMap.put("col.remaining", "Sisa");
        idMap.put("col.status", "Status");

        // Goals
        idMap.put("goal.add", "Tambah Target");
        idMap.put("goal.empty", "Belum ada target tabungan. Tambahkan target baru!");

        // Report
        idMap.put("report.title", "Laporan Keuangan");
        idMap.put("report.empty", "Belum ada transaksi.");

        // Common
        idMap.put("msg.welcome", "Selamat datang");

        dictionary.put("id", idMap);

        // --- ENGLISH ---
        Map<String, String> enMap = new HashMap<>();
        enMap.put("app.title", "Personal Finance App");
        enMap.put("header.title", "Personal Finance");
        enMap.put("header.subtitle", "Manage your money better");

        // Tabs
        enMap.put("tab.dashboard", "Dashboard");
        enMap.put("tab.transaction", "Transactions");
        enMap.put("tab.budget", "Budget");
        enMap.put("tab.goal", "Financial Goals");
        enMap.put("tab.report", "Reports");

        // Dashboard Cards
        enMap.put("card.balance", "Total Balance");
        enMap.put("card.income", "Monthly Income");
        enMap.put("card.expense", "Monthly Expense");
        enMap.put("card.saving", "Total Savings");

        // Transaction Form
        enMap.put("trans.title", "Add Transaction");
        enMap.put("trans.account", "Account:");
        enMap.put("trans.type", "Type:");
        enMap.put("trans.category", "Category:");
        enMap.put("trans.amount", "Amount:");
        enMap.put("trans.desc", "Description:");
        enMap.put("trans.btn", "Add Transaction");
        enMap.put("trans.type.income", "Income");
        enMap.put("trans.type.expense", "Expense");

        // Budget
        enMap.put("budget.add", "Add Budget");
        enMap.put("col.category", "Category");
        enMap.put("col.budget", "Limit");
        enMap.put("col.used", "Spent");
        enMap.put("col.remaining", "Left");
        enMap.put("col.status", "Status");

        // Goals
        enMap.put("goal.add", "Add Goal");
        enMap.put("goal.empty", "No goals yet. Create a new one!");

        // Report
        enMap.put("report.title", "Financial Report");
        enMap.put("report.empty", "No transactions recorded.");

        // Common
        enMap.put("msg.welcome", "Welcome");

        dictionary.put("en", enMap);
    }

    public static void setLanguage(String langCode) {
        if (dictionary.containsKey(langCode)) {
            currentLanguage = langCode;
        } else {
            currentLanguage = "id"; // Fallback
        }
    }

    public static String getString(String key) {
        return dictionary.get(currentLanguage).getOrDefault(key, key);
    }

    public static String getCurrencySymbol() {
        return currentLanguage.equals("id") ? "Rp" : "$";
    }

    // Helper format angka pendek (10k, 1.5 jt, 1.2 M, 1.5 T)
    public static String formatCurrencyCompact(double val) {
        String symbol = getCurrencySymbol();
        if (val >= 1_000_000_000_000L)
            return String.format("%s %.2f T", symbol, val / 1_000_000_000_000.0);
        if (val >= 1_000_000_000)
            return String.format("%s %.2f M", symbol, val / 1_000_000_000.0);
        if (val >= 1_000_000)
            return String.format("%s %.1f jt", symbol, val / 1_000_000.0);
        if (val >= 1_000)
            return String.format("%s %.0f rb", symbol, val / 1_000.0);
        return String.format("%s %.0f", symbol, val);
    }
}