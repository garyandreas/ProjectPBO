package backend.services;

import backend.models.*;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.time.YearMonth;

/**
 * Service untuk mengelola Budget
 * Mengatur batasan pengeluaran dan memberikan notifikasi alert
 */
public class BudgetService {
    private List<Budget> budgets = new ArrayList<>();
    private int nextBudgetId = 1;
    
    private static final String BUDGETS_DATA_FILE = "budgets_data.dat";
    
    public BudgetService() {
        loadBudgetsFromFile();
    }
    
    /**
     * Membuat budget baru
     */
    public Budget createBudget(int userId, int categoryId, double budgetLimit, 
                               Budget.BudgetPeriod period) {
        Budget newBudget = new Budget(userId, categoryId, budgetLimit, period);
        newBudget.setBudgetId(nextBudgetId++);
        budgets.add(newBudget);
        System.out.println("Budget berhasil dibuat untuk kategori!");
        saveBudgetsToFile();
        return newBudget;
    }
    
    /**
     * Dapatkan budget berdasarkan ID
     */
    public Budget getBudgetById(int budgetId) {
        return budgets.stream()
                .filter(b -> b.getBudgetId() == budgetId)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Dapatkan semua budget pengguna
     */
    public List<Budget> getBudgetsByUser(int userId) {
        return budgets.stream()
                .filter(b -> b.getUserId() == userId && b.isActive())
                .collect(Collectors.toList());
    }
    
    /**
     * Dapatkan budget pengguna untuk bulan tertentu
     */
    public List<Budget> getBudgetsByMonth(int userId, YearMonth month) {
        return getBudgetsByUser(userId).stream()
                .filter(b -> b.getMonth().equals(month))
                .collect(Collectors.toList());
    }
    
    /**
     * Update pengeluaran pada budget
     */
    public void updateBudgetSpent(int userId, int categoryId, double amount) {
        Budget budget = budgets.stream()
                .filter(b -> b.getUserId() == userId && b.getCategoryId() == categoryId && b.isActive())
                .findFirst()
                .orElse(null);
        
        if (budget != null) {
            budget.addSpent(amount);
            saveBudgetsToFile();
            
            // Check alert threshold
            if (budget.getSpentPercentage() >= budget.getAlertThreshold()) {
                sendAlert(budget);
            }
        }
    }
    
    /**
     * Kirim alert jika budget mencapai threshold
     */
    private void sendAlert(Budget budget) {
        double spentPercent = budget.getSpentPercentage();
        if (spentPercent >= 100) {
            System.out.println("⚠️ ALERT: Budget sudah terlampaui! Pengeluaran: " + 
                             String.format("%.2f", budget.getSpent()) + " dari " + 
                             String.format("%.2f", budget.getBudgetLimit()));
        } else if (spentPercent >= budget.getAlertThreshold()) {
            System.out.println("⚠️ WARNING: Budget mencapai " + String.format("%.2f", spentPercent) + 
                             "%. Hati-hati dengan pengeluaran!");
        }
    }
    
    /**
     * Update budget
     */
    public boolean updateBudget(Budget budget) {
        Budget existingBudget = getBudgetById(budget.getBudgetId());
        if (existingBudget != null) {
            existingBudget.setBudgetLimit(budget.getBudgetLimit());
            existingBudget.setAlertThreshold(budget.getAlertThreshold());
            existingBudget.setBudgetPeriod(budget.getBudgetPeriod());
            saveBudgetsToFile();
            return true;
        }
        return false;
    }
    
    /**
     * Reset budget untuk bulan baru
     */
    public void resetMonthlyBudgets(int userId) {
        getBudgetsByUser(userId).stream()
                .filter(b -> b.getBudgetPeriod() == Budget.BudgetPeriod.MONTHLY)
                .forEach(b -> {
                    b.setSpent(0);
                    b.setMonth(YearMonth.now());
                });
        saveBudgetsToFile();
    }
    
    /**
     * Delete budget
     */
    public boolean deleteBudget(int budgetId) {
        Budget budget = getBudgetById(budgetId);
        if (budget != null) {
            budget.setActive(false);
            saveBudgetsToFile();
            return true;
        }
        return false;
    }
    
    /**
     * Dapatkan budget status
     */
    public String getBudgetStatus(Budget budget) {
        double percentage = budget.getSpentPercentage();
        if (percentage < 50) {
            return "✅ Aman";
        } else if (percentage < 80) {
            return "⚠️ Hati-hati";
        } else if (percentage < 100) {
            return "🔴 Mendekati batas";
        } else {
            return "❌ Sudah terlampaui";
        }
    }
    
    /**
     * Dapatkan semua budget
     */
    public List<Budget> getAllBudgets() {
        return new ArrayList<>(budgets);
    }
    
    /**
     * Load budgets dari file
     */
    @SuppressWarnings("unchecked")
    private void loadBudgetsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BUDGETS_DATA_FILE))) {
            budgets = (List<Budget>) ois.readObject();
            
            // Rebuild nextBudgetId
            if (!budgets.isEmpty()) {
                nextBudgetId = budgets.stream()
                        .mapToInt(Budget::getBudgetId)
                        .max()
                        .orElse(0) + 1;
            }
            System.out.println("Budgets loaded from file: " + budgets.size() + " budgets");
        } catch (FileNotFoundException e) {
            System.out.println("Budgets file not found. Starting with empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading budgets: " + e.getMessage());
        }
    }
    
    /**
     * Save budgets ke file
     */
    private void saveBudgetsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BUDGETS_DATA_FILE))) {
            oos.writeObject(budgets);
            oos.flush();
            System.out.println("Budgets saved to file");
        } catch (IOException e) {
            System.err.println("Error saving budgets: " + e.getMessage());
        }
    }
    
    /**
     * Reload budgets dari file
     */
    public void reloadFromFile() {
        loadBudgetsFromFile();
    }
}
