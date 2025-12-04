package backend.services;

import backend.models.*;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.nio.file.*;

/**
 * Service untuk mengelola Account/Rekening
 * Menangani operasi CRUD, manajemen saldo, dan persistensi data
 */
public class AccountService {
    private List<Account> accounts = new ArrayList<>();
    private int nextAccountId = 1;
    private static final String DATA_FILE = "accounts_data.dat";
    
    public AccountService() {
        loadAccountsFromFile();
    }
    
    private void loadAccountsFromFile() {
        try {
            java.io.File file = new java.io.File(DATA_FILE);
            System.out.println("[AccountService] Looking for file: " + file.getAbsolutePath());
            System.out.println("[AccountService] File exists: " + file.exists());
            
            if (Files.exists(Paths.get(DATA_FILE))) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE));
                accounts = (List<Account>) ois.readObject();
                ois.close();
                
                if (!accounts.isEmpty()) {
                    nextAccountId = accounts.stream()
                            .mapToInt(Account::getAccountId)
                            .max()
                            .orElse(0) + 1;
                }
                
                System.out.println("[AccountService] ✅ Loaded " + accounts.size() + " accounts from file.");
            } else {
                System.out.println("[AccountService] ℹ️ File does not exist: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("[AccountService] ❌ Error loading: " + e.getMessage());
            accounts = new ArrayList<>();
        }
    }
    
    private void saveAccountsToFile() {
        try {
            java.io.File file = new java.io.File(DATA_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE));
            oos.writeObject(accounts);
            oos.close();
            System.out.println("[AccountService] ✅ Saved " + accounts.size() + " accounts to: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("[AccountService] ❌ Error saving: " + e.getMessage());
        }
    }
    
    /**
     * Membuat account baru
     */
    public Account createAccount(int userId, String accountName, 
                                  Account.AccountType accountType, double initialBalance) {
        Account newAccount = new Account(userId, accountName, accountType, initialBalance);
        newAccount.setAccountId(nextAccountId++);
        accounts.add(newAccount);
        saveAccountsToFile();
        System.out.println("Account " + accountName + " berhasil dibuat!");
        return newAccount;
    }
    
    /**
     * Dapatkan semua account pengguna
     */
    public List<Account> getAccountsByUser(int userId) {
        List<Account> allUserAccounts = accounts.stream()
                .filter(a -> a.getUserId() == userId)
                .collect(Collectors.toList());
        
        List<Account> activeUserAccounts = allUserAccounts.stream()
                .filter(Account::isActive)
                .collect(Collectors.toList());
        
        if (allUserAccounts.size() != activeUserAccounts.size()) {
            System.out.println("[AccountService] ⚠️  WARNING: User " + userId + " has " + allUserAccounts.size() + 
                             " total accounts but only " + activeUserAccounts.size() + " are ACTIVE!");
            for (Account a : allUserAccounts) {
                System.out.println("       - " + a.getAccountName() + " (ID: " + a.getAccountId() + ", Active: " + a.isActive() + ")");
            }
        }
        
        return activeUserAccounts;
    }
    
    /**
     * Dapatkan account berdasarkan ID
     */
    public Account getAccountById(int accountId) {
        return accounts.stream()
                .filter(a -> a.getAccountId() == accountId)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Update account
     */
    public boolean updateAccount(Account account) {
        Account existingAccount = getAccountById(account.getAccountId());
        if (existingAccount != null) {
            existingAccount.setAccountName(account.getAccountName());
            existingAccount.setDescription(account.getDescription());
            existingAccount.setCurrency(account.getCurrency());
            return true;
        }
        return false;
    }
    
    /**
     * Delete/non-aktifkan account
     */
    public boolean deleteAccount(int accountId) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.setActive(false);
            return true;
        }
        return false;
    }
    
    /**
     * Tambah saldo account
     */
    public boolean addBalance(int accountId, double amount) {
        Account account = getAccountById(accountId);
        if (account != null && amount > 0) {
            account.addBalance(amount);
            System.out.println("Saldo ditambah: " + amount + " pada account " + account.getAccountName());
            saveAccountsToFile();
            return true;
        }
        return false;
    }
    
    /**
     * Kurangi saldo account
     */
    public boolean deductBalance(int accountId, double amount) {
        Account account = getAccountById(accountId);
        if (account != null && amount > 0 && account.getBalance() >= amount) {
            account.deductBalance(amount);
            System.out.println("Saldo dikurangi: " + amount + " pada account " + account.getAccountName());
            saveAccountsToFile();
            return true;
        }
        return false;
    }

    /**
     * Update saldo (Method BARU untuk TransactionService)
     * Jika amount positif -> addBalance
     * Jika amount negatif -> deductBalance (dengan nilai absolut)
     */
    public boolean updateBalance(int accountId, double amount) {
        if (amount >= 0) {
            return addBalance(accountId, amount);
        } else {
            return deductBalance(accountId, Math.abs(amount));
        }
    }
    
    /**
     * Transfer antar account
     */
    public boolean transferBalance(int fromAccountId, int toAccountId, double amount) {
        if (deductBalance(fromAccountId, amount)) {
            if (addBalance(toAccountId, amount)) {
                System.out.println("Transfer berhasil: " + amount);
                return true;
            } else {
                // Rollback jika gagal
                addBalance(fromAccountId, amount);
                return false;
            }
        }
        return false;
    }
    
    /**
     * Dapatkan total saldo pengguna
     */
    public double getTotalBalance(int userId) {
        return getAccountsByUser(userId).stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }
    
    /**
     * Dapatkan semua account
     */
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }
    
    /**
     * Reload accounts dari file
     */
    public void reloadFromFile() {
        loadAccountsFromFile();
    }
}