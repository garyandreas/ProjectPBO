package backend.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Model untuk RecurringTransaction/Transaksi Berulang
 * Otomasi transaksi rutin yang terjadi berkala
 */
public class RecurringTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum RecurrencePattern {
        DAILY, WEEKLY, MONTHLY, YEARLY
    }
    
    private int recurringId;
    private int userId;
    private int accountId;
    private int categoryId;
    private int subCategoryId;
    private String description;
    private double amount;
    private Transaction.TransactionType transactionType;
    private RecurrencePattern recurrencePattern;
    private int dayOfWeek; // 1=Monday, 7=Sunday (untuk WEEKLY)
    private int dayOfMonth; // 1-31 (untuk MONTHLY)
    private LocalDateTime startDate;
    private LocalDateTime endDate; // null jika tidak ada batas akhir
    private LocalDateTime nextExecutionDate;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructor
    public RecurringTransaction() {
    }
    
    public RecurringTransaction(int userId, int accountId, int categoryId, 
                               double amount, RecurrencePattern pattern) {
        this.userId = userId;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.recurrencePattern = pattern;
        this.transactionType = Transaction.TransactionType.EXPENSE;
        this.isActive = true;
        this.startDate = LocalDateTime.now();
        this.nextExecutionDate = startDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters dan Setters
    public int getRecurringId() {
        return recurringId;
    }
    
    public void setRecurringId(int recurringId) {
        this.recurringId = recurringId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getAccountId() {
        return accountId;
    }
    
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public int getSubCategoryId() {
        return subCategoryId;
    }
    
    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public Transaction.TransactionType getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(Transaction.TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    
    public RecurrencePattern getRecurrencePattern() {
        return recurrencePattern;
    }
    
    public void setRecurrencePattern(RecurrencePattern recurrencePattern) {
        this.recurrencePattern = recurrencePattern;
    }
    
    public int getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public int getDayOfMonth() {
        return dayOfMonth;
    }
    
    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public LocalDateTime getNextExecutionDate() {
        return nextExecutionDate;
    }
    
    public void setNextExecutionDate(LocalDateTime nextExecutionDate) {
        this.nextExecutionDate = nextExecutionDate;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
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
    
    public boolean isReadyToExecute() {
        return isActive && LocalDateTime.now().isAfter(nextExecutionDate) &&
               (endDate == null || LocalDateTime.now().isBefore(endDate));
    }
    
    @Override
    public String toString() {
        return "RecurringTransaction{" +
                "recurringId=" + recurringId +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", recurrencePattern=" + recurrencePattern +
                ", isActive=" + isActive +
                ", nextExecutionDate=" + nextExecutionDate +
                '}';
    }
}
