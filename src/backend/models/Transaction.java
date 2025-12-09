package backend.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum TransactionType {
        INCOME, EXPENSE, TRANSFER
    }

    private int transactionId;
    private int accountId;
    private int userId;
    private int categoryId;
    private int subCategoryId;
    private TransactionType transactionType;
    private double amount;
    private String description;
    private LocalDateTime transactionDate;
    private LocalDateTime createdAt;
    private String notes;
    private boolean isRecurring;
    private int recurringId;
    private Integer relatedGoalId; // Link to Financial Goal (optional)

    public Transaction() {
    }

    // Constructor Utama (5 Parameter)
    public Transaction(int accountId, int userId, int categoryId,
            TransactionType transactionType, double amount) {
        this.accountId = accountId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.isRecurring = false;
    }

    // Getters & Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public int getRecurringId() {
        return recurringId;
    }

    public void setRecurringId(int recurringId) {
        this.recurringId = recurringId;
    }

    public Integer getRelatedGoalId() {
        return relatedGoalId;
    }

    public void setRelatedGoalId(Integer relatedGoalId) {
        this.relatedGoalId = relatedGoalId;
    }
}