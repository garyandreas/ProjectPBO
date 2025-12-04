package backend.models;

import java.io.Serializable;
import java.time.YearMonth;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum BudgetPeriod {
        WEEKLY, MONTHLY, YEARLY
    }

    private String budgetId;
    private int userId;
    private int categoryId;
    private double budgetLimit;
    private double spent;
    private BudgetPeriod period;
    private YearMonth monthPeriod; // Untuk melacak budget bulan tertentu

    public Budget(int userId, int categoryId, double budgetLimit, BudgetPeriod period) {
        this.budgetId = java.util.UUID.randomUUID().toString();
        this.userId = userId;
        this.categoryId = categoryId;
        this.budgetLimit = budgetLimit;
        this.period = period;
        this.spent = 0;
        this.monthPeriod = YearMonth.now();
    }

    // PENTING: Method ini dibutuhkan oleh BudgetService
    public void addSpent(double amount) {
        this.spent += amount;
    }

    // Getters & Setters
    public String getBudgetId() { return budgetId; }
    public void setBudgetId(String budgetId) { this.budgetId = budgetId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public double getBudgetLimit() { return budgetLimit; }
    public void setBudgetLimit(double limit) { this.budgetLimit = limit; }

    public double getSpent() { return spent; }
    public void setSpent(double spent) { this.spent = spent; }

    public BudgetPeriod getPeriod() { return period; }
    public void setPeriod(BudgetPeriod period) { this.period = period; }
    
    public YearMonth getMonthPeriod() { return monthPeriod; }
    public void setMonthPeriod(YearMonth monthPeriod) { this.monthPeriod = monthPeriod; }
}