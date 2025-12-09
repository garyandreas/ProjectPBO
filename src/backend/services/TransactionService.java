package backend.services;

import backend.models.Transaction;
import backend.models.Account;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class TransactionService {
    private static final String TRANSACTIONS_DATA_FILE = "transactions_data.dat";
    private List<Transaction> transactions = new ArrayList<>();
    private AccountService accountService;
    private BudgetService budgetService;
    private FinancialGoalService goalService;
    private int nextTransactionId = 1;

    public TransactionService(AccountService accountService, BudgetService budgetService,
            FinancialGoalService goalService) {
        this.accountService = accountService;
        this.budgetService = budgetService;
        this.goalService = goalService;
        loadTransactionsFromFile();
    }

    public TransactionService(AccountService accountService, BudgetService budgetService) {
        this(accountService, budgetService, null);
    }

    // --- CORE FEATURE: CREATE TRANSACTION ---
    public boolean createTransaction(int accountId, int userId, int categoryId,
            Transaction.TransactionType type, double amount,
            String description, int subCategoryId) {
        return createTransaction(accountId, userId, categoryId, type, amount, description, subCategoryId, null);
    }

    public boolean createTransaction(int accountId, int userId, int categoryId,
            Transaction.TransactionType type, double amount,
            String description, int subCategoryId, Integer relatedGoalId) {
        System.out.println("DEBUG: Memulai createTransaction...");

        // 1. Validasi Akun & Saldo
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            System.out.println("GAGAL: Akun dengan ID " + accountId + " tidak ditemukan.");
            return false;
        }

        if (type == Transaction.TransactionType.EXPENSE && account.getBalance() < amount) {
            System.out.println("GAGAL: Saldo tidak cukup! Saldo: " + account.getBalance() + ", Diminta: " + amount);
            return false;
        }

        // 2. Update Saldo Akun
        boolean balanceUpdated = false;
        if (type == Transaction.TransactionType.INCOME || type == Transaction.TransactionType.TRANSFER_IN) {
            balanceUpdated = accountService.updateBalance(accountId, amount);
        } else {
            balanceUpdated = accountService.updateBalance(accountId, -amount);
        }

        if (!balanceUpdated) {
            System.out.println("GAGAL: Gagal update saldo di database akun.");
            return false;
        }

        // 3. Update Budget (Khusus Pengeluaran)
        // Jika ini adalah tabungan (relatedGoalId != null), kita mungkin tidak ingin
        // mengurangi budget kategori normal?
        // Tapi "Tabungan" biasanya juga kategori pengeluaran. Biarkan update budget
        // berjalan.
        if (type == Transaction.TransactionType.EXPENSE && budgetService != null) {
            budgetService.updateBudgetSpent(userId, categoryId, amount);
        }

        // 3.5 Update Goal (Jika ada)
        if (relatedGoalId != null && goalService != null && type == Transaction.TransactionType.EXPENSE) {
            goalService.addFundsToGoal(relatedGoalId, amount);
        }

        // 4. Simpan Transaksi Baru
        // Menggunakan Constructor 5 parameter yang sesuai dengan Transaction.java
        Transaction transaction = new Transaction(accountId, userId, categoryId, type, amount);
        transaction.setDescription(description);

        transaction.setTransactionId(nextTransactionId++);
        transaction.setSubCategoryId(subCategoryId);
        transaction.setRelatedGoalId(relatedGoalId);

        transactions.add(transaction);
        saveTransactionsToFile();

        System.out.println("SUKSES: Transaksi berhasil disimpan. ID: " + transaction.getTransactionId());
        return true;
    }

    // --- GETTERS & FILTERS ---

    public Transaction getTransactionById(int transactionId) {
        return transactions.stream()
                .filter(t -> t.getTransactionId() == transactionId)
                .findFirst()
                .orElse(null);
    }

    public List<Transaction> getTransactionsByUser(int userId) {
        return transactions.stream()
                .filter(t -> t.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByAccount(int accountId) {
        return transactions.stream()
                .filter(t -> t.getAccountId() == accountId)
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByCategory(int userId, int categoryId) {
        return getTransactionsByUser(userId).stream()
                .filter(t -> t.getCategoryId() == categoryId)
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByPeriod(int userId, LocalDateTime startDate, LocalDateTime endDate) {
        return getTransactionsByUser(userId).stream()
                .filter(t -> !t.getTransactionDate().isBefore(startDate) &&
                        !t.getTransactionDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    public List<Transaction> getTransactionsByMonth(int userId, YearMonth month) {
        LocalDateTime startDate = month.atDay(1).atStartOfDay();
        LocalDateTime endDate = month.atEndOfMonth().atTime(23, 59, 59);
        return getTransactionsByPeriod(userId, startDate, endDate);
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public double getTotalIncome(int userId, YearMonth month) {
        return getTransactionsByMonth(userId, month).stream()
                .filter(t -> t.getTransactionType() == Transaction.TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount).sum();
    }

    public double getTotalExpense(int userId, YearMonth month) {
        return getTransactionsByMonth(userId, month).stream()
                .filter(t -> t.getTransactionType() == Transaction.TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount).sum();
    }

    // --- UPDATE & DELETE ---

    public boolean updateTransaction(Transaction transaction) {
        Transaction existing = getTransactionById(transaction.getTransactionId());
        if (existing != null) {
            existing.setDescription(transaction.getDescription());
            existing.setNotes(transaction.getNotes());
            saveTransactionsToFile();
            return true;
        }
        return false;
    }

    public boolean deleteTransaction(int transactionId) {
        Transaction t = getTransactionById(transactionId);
        if (t != null) {
            // Rollback saldo (kembalikan uang)
            if (t.getTransactionType() == Transaction.TransactionType.INCOME) {
                accountService.updateBalance(t.getAccountId(), -t.getAmount());
            } else {
                accountService.updateBalance(t.getAccountId(), t.getAmount());
                // Note: Budget tidak dikembalikan otomatis untuk simplifikasi
            }

            transactions.remove(t);
            saveTransactionsToFile();
            return true;
        }
        return false;
    }

    // --- FILE HANDLING ---

    @SuppressWarnings("unchecked")
    private void loadTransactionsFromFile() {
        File file = new File(TRANSACTIONS_DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    transactions = (List<Transaction>) obj;
                    if (!transactions.isEmpty()) {
                        nextTransactionId = transactions.stream()
                                .mapToInt(Transaction::getTransactionId).max().orElse(0) + 1;
                    }
                }
            } catch (Exception e) {
                System.out.println("Gagal memuat transaksi: " + e.getMessage());
                transactions = new ArrayList<>();
            }
        }
    }

    private void saveTransactionsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TRANSACTIONS_DATA_FILE))) {
            oos.writeObject(transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadFromFile() {
        loadTransactionsFromFile();
    }
}