package backend.utils;

import java.time.YearMonth;
import java.util.*;

/**
 * Utility class untuk laporan dan statistik keuangan
 */
public class ReportGenerator {
    
    /**
     * Generate laporan bulanan
     */
    public static Map<String, Object> generateMonthlyReport(
            double income, double expense, double totalBalance, 
            Map<String, Double> expenseByCategory) {
        
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("bulan", YearMonth.now().toString());
        report.put("totalPemasukan", income);
        report.put("totalPengeluaran", expense);
        report.put("selisih", income - expense);
        report.put("sisaSaldo", totalBalance);
        report.put("rincianPengeluaran", expenseByCategory);
        
        return report;
    }
    
    /**
     * Generate laporan tahunan
     */
    public static Map<String, Object> generateYearlyReport(
            Map<String, Double> monthlyIncome,
            Map<String, Double> monthlyExpense) {
        
        Map<String, Object> report = new LinkedHashMap<>();
        double totalYearlyIncome = monthlyIncome.values().stream().mapToDouble(Double::doubleValue).sum();
        double totalYearlyExpense = monthlyExpense.values().stream().mapToDouble(Double::doubleValue).sum();
        
        report.put("tahun", java.time.Year.now().toString());
        report.put("totalPemasukanTahunan", totalYearlyIncome);
        report.put("totalPengeluaranTahunan", totalYearlyExpense);
        report.put("keuntunganBersih", totalYearlyIncome - totalYearlyExpense);
        report.put("dataPerBulan", monthlyIncome);
        
        return report;
    }
    
    /**
     * Hitung persentase budget yang digunakan
     */
    public static double calculateBudgetPercentage(double spent, double limit) {
        if (limit <= 0) return 0;
        return (spent / limit) * 100;
    }
    
    /**
     * Format nilai ke format rupiah
     */
    public static String formatCurrency(double value) {
        return String.format("Rp %,.0f", value);
    }
    
    /**
     * Format nilai ke format desimal 2 tempat
     */
    public static String formatDecimal(double value) {
        return String.format("%.2f", value);
    }
}
