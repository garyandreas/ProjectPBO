package backend.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * Model untuk Budget/Anggaran
 * Menetapkan batasan pengeluaran per kategori dengan notifikasi
 */
public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum BudgetPeriod {
        DAILY, WEEKLY, MONTHLY, YEARLY
    }
    
    private int budgetId;
    private int userId;
    private int categoryId;
    private double budgetLimit;
    private double spent;
    private BudgetPeriod budgetPeriod;
    private YearMonth month; // Untuk tracking per bulan
    private double alertThreshold; // Alert pada persentase tertentu (misal 80%)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
    
    // Constructor
    public Budget() {
    }
    
    public Budget(int userId, int categoryId, double budgetLimit, BudgetPeriod period) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.budgetLimit = budgetLimit;
        this.budgetPeriod = period;
        this.spent = 0;
        this.alertThreshold = 80.0; // Default 80%
        this.month = YearMonth.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true;
    }
    
    // Getters dan Setters
    public int getBudgetId() {
        return budgetId;
    }
    
    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public double getBudgetLimit() {
        return budgetLimit;
    }
    
    public void setBudgetLimit(double budgetLimit) {
        this.budgetLimit = budgetLimit;
    }
    
    public double getSpent() {
        return spent;
    }
    
    public void setSpent(double spent) {
        this.spent = spent;
    }
    
    public void addSpent(double amount) {
        this.spent += amount;
        this.updatedAt = LocalDateTime.now();
    }
    
    public BudgetPeriod getBudgetPeriod() {
        return budgetPeriod;
    }
    
    public void setBudgetPeriod(BudgetPeriod budgetPeriod) {
        this.budgetPeriod = budgetPeriod;
    }
    
    public YearMonth getMonth() {
        return month;
    }
    
    public void setMonth(YearMonth month) {
        this.month = month;
    }
    
    public double getAlertThreshold() {
        return alertThreshold;
    }
    
    public void setAlertThreshold(double alertThreshold) {
        this.alertThreshold = alertThreshold;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public double getRemainingBudget() {
        return budgetLimit - spent;
    }
    
    public double getSpentPercentage() {
        return (spent / budgetLimit) * 100;
    }
    
    public boolean isAlertThresholdReached() {
        return getSpentPercentage() >= alertThreshold;
    }
    
    public boolean isBudgetExceeded() {
        return spent > budgetLimit;
    }
    
    @Override
    public String toString() {
        return "Budget{" +
                "budgetId=" + budgetId +
                ", budgetLimit=" + budgetLimit +
                ", spent=" + spent +
                ", budgetPeriod=" + budgetPeriod +
                ", percentage=" + String.format("%.2f", getSpentPercentage()) + "%" +
                '}';
    }
}
