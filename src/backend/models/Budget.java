package backend.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum BudgetPeriod {
        WEEKLY, MONTHLY, YEARLY
    }

    private String budgetId;
    private int userId;
    private int categoryId;
    private double budgetLimit;
    private double spent; // Total pengeluaran saat ini
    private BudgetPeriod period;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int alertThreshold; // Persentase untuk notifikasi (misal 80%)

    public Budget(int userId, int categoryId, double budgetLimit, BudgetPeriod period) {
        this.budgetId = java.util.UUID.randomUUID().toString();
        this.userId = userId;
        this.categoryId = categoryId;
        this.budgetLimit = budgetLimit;
        this.period = period;
        this.spent = 0;
        this.startDate = LocalDateTime.now();
        this.alertThreshold = 80; // Default warning di 80%
    }

    // --- Logic Methods ---
    
    public void addSpent(double amount) {
        this.spent += amount;
    }
    
    public double getSpentPercentage() {
        if (budgetLimit == 0) return 0;
        return (spent / budgetLimit) * 100;
    }
    
    public double getRemaining() {
        return budgetLimit - spent;
    }

    // --- Getters & Setters ---

    public String getBudgetId() { return budgetId; }
    public void setBudgetId(String budgetId) { this.budgetId = budgetId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public double getBudgetLimit() { return budgetLimit; }
    public void setBudgetLimit(double budgetLimit) { this.budgetLimit = budgetLimit; }

    public double getSpent() { return spent; }
    public void setSpent(double spent) { this.spent = spent; }

    public BudgetPeriod getPeriod() { return period; }
    public void setPeriod(BudgetPeriod period) { this.period = period; }
    
    public int getAlertThreshold() { return alertThreshold; }
    public void setAlertThreshold(int alertThreshold) { this.alertThreshold = alertThreshold; }
}