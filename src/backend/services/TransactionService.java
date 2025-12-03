package backend.services;

import backend.models.*;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * Service untuk mengelola Transaction
 * Mencatat dan mengelola transaksi pemasukan dan pengeluaran
 */
public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();
    private AccountService accountService;
    private BudgetService budgetService;
    private int nextTransactionId = 1;
    
    private static final String TRANSACTIONS_DATA_FILE = "transactions_data.dat";
    
    public TransactionService(AccountService accountService, BudgetService budgetService) {
        this.accountService = accountService;
        this.budgetService = budgetService;
        loadTransactionsFromFile();
    }
    
    /**
     * Membuat transaksi baru
     */
    public boolean createTransaction(int accountId, int userId, int categoryId, 
                                     Transaction.TransactionType type, double amount, 
                                     String description, int subCategoryId) {
        // Validasi saldo
        Account account = accountService.getAccountById(accountId);
        if (account == null) return false;
        
        if (type == Transaction.TransactionType.EXPENSE && account.getBalance() < amount) {
            System.out.println("Saldo tidak cukup!");
            return false;
        }
        
        // Buat transaksi
        Transaction transaction = new Transaction(accountId, userId, categoryId, type, amount);
        transaction.setTransactionId(nextTransactionId++);
        transaction.setDescription(description);
        transaction.setSubCategoryId(subCategoryId);
        
        // Update saldo account
        if (type == Transaction.TransactionType.INCOME) {
            accountService.addBalance(accountId, amount);
        } else if (type == Transaction.TransactionType.EXPENSE) {
            accountService.deductBalance(accountId, amount);
        }
        
        // Update budget jika expense
        if (type == Transaction.TransactionType.EXPENSE) {
            budgetService.updateBudgetSpent(userId, categoryId, amount);
        }
        
        transactions.add(transaction);
        System.out.println("Transaksi berhasil dicatat!");
        saveTransactionsToFile();
        return true;
    }
    
    /**
     * Dapatkan transaksi berdasarkan ID
     */
    public Transaction getTransactionById(int transactionId) {
        return transactions.stream()
                .filter(t -> t.getTransactionId() == transactionId)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Dapatkan semua transaksi pengguna
     */
    public List<Transaction> getTransactionsByUser(int userId) {
        return transactions.stream()
                .filter(t -> t.getUserId() == userId)
                .collect(Collectors.toList());
    }
    
    /**
     * Dapatkan transaksi berdasarkan account
     */
    public List<Transaction> getTransactionsByAccount(int accountId) {
        return transactions.stream()
                .filter(t -> t.getAccountId() == accountId)
                .collect(Collectors.toList());
    }
    
    /**
     * Dapatkan transaksi berdasarkan kategori
     */
    public List<Transaction> getTransactionsByCategory(int userId, int categoryId) {
        return getTransactionsByUser(userId).stream()
                .filter(t -> t.getCategoryId() == categoryId)
                .collect(Collectors.toList());
    }
    
    /**
     * Dapatkan transaksi dalam periode tertentu
     */
    public List<Transaction> getTransactionsByPeriod(int userId, LocalDateTime startDate, LocalDateTime endDate) {
        return getTransactionsByUser(userId).stream()
                .filter(t -> !t.getTransactionDate().isBefore(startDate) && 
                            !t.getTransactionDate().isAfter(endDate))
                .collect(Collectors.toList());
    }
    
    /**
     * Dapatkan transaksi bulan ini
     */
    public List<Transaction> getTransactionsByMonth(int userId, YearMonth month) {
        LocalDateTime startDate = month.atDay(1).atStartOfDay();
        LocalDateTime endDate = month.atEndOfMonth().atTime(23, 59, 59);
        return getTransactionsByPeriod(userId, startDate, endDate);
    }
    
    /**
     * Update transaksi
     */
    public boolean updateTransaction(Transaction transaction) {
        Transaction existingTransaction = getTransactionById(transaction.getTransactionId());
        if (existingTransaction != null) {
            existingTransaction.setDescription(transaction.getDescription());
            existingTransaction.setNotes(transaction.getNotes());
            saveTransactionsToFile();
            return true;
        }
        return false;
    }
    
    /**
     * Delete transaksi
     */
    public boolean deleteTransaction(int transactionId) {
        Transaction transaction = getTransactionById(transactionId);
        if (transaction != null) {
            // Rollback saldo account
            Account account = accountService.getAccountById(transaction.getAccountId());
            if (account != null) {
                if (transaction.getTransactionType() == Transaction.TransactionType.INCOME) {
                    accountService.deductBalance(account.getAccountId(), transaction.getAmount());
                } else if (transaction.getTransactionType() == Transaction.TransactionType.EXPENSE) {
                    accountService.addBalance(account.getAccountId(), transaction.getAmount());
                }
            }
            
            boolean result = transactions.remove(transaction);
            if (result) {
                saveTransactionsToFile();
            }
            return result;
        }
        return false;
    }
    
    /**
     * Dapatkan total income dalam bulan
     */
    public double getTotalIncome(int userId, YearMonth month) {
        return getTransactionsByMonth(userId, month).stream()
                .filter(t -> t.getTransactionType() == Transaction.TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    /**
     * Dapatkan total expense dalam bulan
     */
    public double getTotalExpense(int userId, YearMonth month) {
        return getTransactionsByMonth(userId, month).stream()
                .filter(t -> t.getTransactionType() == Transaction.TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    
    /**
     * Dapatkan semua transaksi
     */
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    
    /**
     * Load transaksi dari file
     */
    @SuppressWarnings("unchecked")
    private void loadTransactionsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TRANSACTIONS_DATA_FILE))) {
            transactions = (List<Transaction>) ois.readObject();
            
            // Rebuild nextTransactionId
            if (!transactions.isEmpty()) {
                nextTransactionId = transactions.stream()
                        .mapToInt(Transaction::getTransactionId)
                        .max()
                        .orElse(0) + 1;
            }
            System.out.println("Transactions loaded from file: " + transactions.size() + " transactions");
        } catch (FileNotFoundException e) {
            System.out.println("Transactions file not found. Starting with empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
    }
    
    /**
     * Save transaksi ke file
     */
    private void saveTransactionsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TRANSACTIONS_DATA_FILE))) {
            oos.writeObject(transactions);
            oos.flush();
            System.out.println("Transactions saved to file");
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }
    
    /**
     * Reload transactions dari file
     */
    public void reloadFromFile() {
        loadTransactionsFromFile();
    }
}
