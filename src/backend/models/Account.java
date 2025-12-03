package backend.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Model untuk Account/Rekening
 * Merepresentasikan rekening atau wallet pengguna (Bank, E-wallet, Cash)
 */
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum AccountType {
        BANK, E_WALLET, CASH, INVESTMENT
    }
    
    private int accountId;
    private int userId;
    private String accountName;
    private AccountType accountType;
    private double balance;
    private String currency;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
    
    // Constructor
    public Account() {
    }
    
    public Account(int userId, String accountName, AccountType accountType, double initialBalance) {
        this.userId = userId;
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = initialBalance;
        this.currency = "IDR";
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters dan Setters
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
    
    public String getAccountName() {
        return accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public AccountType getAccountType() {
        return accountType;
    }
    
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public void addBalance(double amount) {
        this.balance += amount;
        this.updatedAt = LocalDateTime.now();
    }
    
    public void deductBalance(double amount) {
        this.balance -= amount;
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
    
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
