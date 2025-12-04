package frontend.controller;

import backend.models.*;
import backend.services.*;
import backend.utils.LocalizationUtils;
import frontend.ui.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.*;

public class ApplicationController {
    private UserService userService;
    private AccountService accountService;
    private CategoryService categoryService;
    private TransactionService transactionService;
    private BudgetService budgetService;
    private FinancialGoalService goalService;
    
    private User currentUser;
    private LoginFrameModern loginFrame;
    private MainFrameModern mainFrame;
    
    // Simpan list budget yang sedang tampil untuk mapping saat delete
    private List<Budget> currentBudgets;
    
    public ApplicationController() {
        this.userService = new UserService();
        this.accountService = new AccountService();
        this.categoryService = new CategoryService();
        this.budgetService = new BudgetService();
        this.transactionService = new TransactionService(accountService, budgetService);
        this.goalService = new FinancialGoalService();
        
        this.loginFrame = new LoginFrameModern();
        setupEventHandlers();
    }
    
    private void setupEventHandlers() {
        loginFrame.addLoginListener(e -> handleLogin());
        loginFrame.addRegisterListener(e -> handleRegister());
    }
    
    private void handleLogin() {
        String username = loginFrame.getUsername();
        String password = loginFrame.getPassword();
        User user = userService.loginUser(username, password);
        
        if (user != null) {
            this.currentUser = user;
            LocalizationUtils.setLanguage(user.getLanguage());
            
            accountService.reloadFromFile();
            categoryService.reloadFromFile();
            transactionService.reloadFromFile();
            budgetService.reloadFromFile();
            goalService.reloadFromFile();
            
            openMainApplication();
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Login gagal!");
        }
    }
    
    private void handleRegister() {
        RegisterDialogModern dialog = new RegisterDialogModern(loginFrame);
        dialog.addRegisterListener(e -> {
             if (dialog.getCurrentStep() == 1) {
                 dialog.showSetupStep();
             } else {
                 boolean success = userService.registerUser(dialog.getUsername(), dialog.getEmail(), dialog.getPassword(), dialog.getFullName());
                 if (success) {
                     User newUser = userService.getUserByUsername(dialog.getUsername());
                     newUser.setPin(dialog.getPin());
                     newUser.setCurrency(dialog.getCurrency());
                     newUser.setLanguage(dialog.getLanguage());
                     userService.updateUser(newUser);
                     
                     try {
                        double bal = Double.parseDouble(dialog.getInitialBalance());
                        accountService.createAccount(newUser.getUserId(), "Dompet Tunai", Account.AccountType.CASH, bal);
                     } catch(Exception ex) {
                        accountService.createAccount(newUser.getUserId(), "Dompet Tunai", Account.AccountType.CASH, 0);
                     }
                     accountService.createAccount(newUser.getUserId(), "Rekening Bank", Account.AccountType.BANK, 0);
                     categoryService.createDefaultCategories(newUser.getUserId());
                     
                     JOptionPane.showMessageDialog(loginFrame, "Registrasi Berhasil!");
                     dialog.dispose();
                 } else {
                     dialog.setStatus("Registrasi gagal.");
                 }
             }
        });
        dialog.addCancelListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void openMainApplication() {
        loginFrame.setVisible(false);
        mainFrame = new MainFrameModern();
        
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) { showLoginScreen(); }
        });
        
        mainFrame.setUserGreeting(currentUser.getFullName());
        
        refreshAccountDropdown();
        refreshCategoryDropdown();
        setupMainFrameHandlers();
        
        mainFrame.setVisible(true);
        updateDashboard();
        refreshBudgetTable();
    }
    
    private void setupMainFrameHandlers() {
        mainFrame.addTransactionListener(e -> handleAddTransaction());
        mainFrame.addTransactionTypeListener(e -> refreshCategoryDropdown());
        
        mainFrame.addBudgetListener(e -> handleAddBudget());
        mainFrame.addDeleteBudgetListener(e -> handleDeleteBudget()); // Handler Hapus
        
        mainFrame.addGoalListener(e -> JOptionPane.showMessageDialog(mainFrame, "Fitur Goal akan datang!"));
    }
    
    // --- FITUR DELETE BUDGET ---
    private void handleDeleteBudget() {
        int selectedRow = mainFrame.getSelectedBudgetRow();
        if (selectedRow >= 0 && currentBudgets != null && selectedRow < currentBudgets.size()) {
            Budget selectedBudget = currentBudgets.get(selectedRow);
            Category cat = categoryService.getCategoryById(selectedBudget.getCategoryId());
            String catName = (cat != null) ? cat.getCategoryName() : "Unknown";
            
            int confirm = JOptionPane.showConfirmDialog(mainFrame, 
                "Hapus anggaran untuk kategori '" + catName + "'?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = budgetService.deleteBudget(selectedBudget.getBudgetId());
                if (deleted) {
                    refreshBudgetTable();
                    JOptionPane.showMessageDialog(mainFrame, "Budget dihapus.");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Gagal menghapus.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Pilih budget yang akan dihapus dari tabel.");
        }
    }
    
    private void handleAddBudget() {
        List<Category> expenseCats = categoryService.getCategoriesByType(currentUser.getUserId(), Category.CategoryType.EXPENSE);
        if (expenseCats.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Tidak ada kategori pengeluaran tersedia.");
            return;
        }
        
        JPanel panel = new JPanel(new java.awt.GridLayout(0, 1));
        JComboBox<String> catCombo = new JComboBox<>();
        for (Category c : expenseCats) catCombo.addItem(c.getCategoryName());
        JTextField limitField = new JTextField();
        
        panel.add(new JLabel("Pilih Kategori:"));
        panel.add(catCombo);
        panel.add(new JLabel("Batas Anggaran (Rp):"));
        panel.add(limitField);
        
        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Buat Anggaran Baru", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String selectedCatName = (String) catCombo.getSelectedItem();
                double limit = Double.parseDouble(limitField.getText());
                Category selectedCat = expenseCats.stream().filter(c -> c.getCategoryName().equals(selectedCatName)).findFirst().orElse(null);
                
                if (selectedCat != null) {
                    budgetService.createBudget(currentUser.getUserId(), selectedCat.getCategoryId(), limit, Budget.BudgetPeriod.MONTHLY);
                    JOptionPane.showMessageDialog(mainFrame, "Budget berhasil dibuat!");
                    refreshBudgetTable();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Jumlah anggaran harus berupa angka!");
            }
        }
    }
    
    // --- LOGIC TRANSAKSI DIPERBAIKI (LEBIH ROBUST) ---
    private void handleAddTransaction() {
        String accName = mainFrame.getSelectedAccount();
        String catName = mainFrame.getTransactionCategory();
        // Gunakan Index untuk memastikan tipe
        int typeIndex = mainFrame.getTransactionTypeIndex(); // 0 = Income, 1 = Expense
        
        try {
            double amount = Double.parseDouble(mainFrame.getTransactionAmount());
            if (amount <= 0) throw new NumberFormatException();
            
            Transaction.TransactionType type = (typeIndex == 0) 
                                             ? Transaction.TransactionType.INCOME 
                                             : Transaction.TransactionType.EXPENSE;
            
            // Ambil Account
            Account acc = accountService.getAccountsByUser(currentUser.getUserId()).stream()
                            .filter(a -> a.getAccountName().equals(accName)).findFirst().orElse(null);
            
            // Ambil Kategori: Filter juga berdasarkan TIPE agar tidak tertukar nama kategori yang sama
            Category.CategoryType catType = (type == Transaction.TransactionType.INCOME) ? Category.CategoryType.INCOME : Category.CategoryType.EXPENSE;
            
            Category cat = categoryService.getCategoriesByType(currentUser.getUserId(), catType).stream()
                            .filter(c -> c.getCategoryName().equals(catName)).findFirst().orElse(null);
                            
            if (acc != null && cat != null) {
                boolean success = transactionService.createTransaction(acc.getAccountId(), currentUser.getUserId(), cat.getCategoryId(), type, amount, mainFrame.getTransactionDescription(), 0);
                
                if (success) {
                    if (type == Transaction.TransactionType.EXPENSE) {
                        Budget budget = budgetService.getBudgetByCategory(currentUser.getUserId(), cat.getCategoryId());
                        if (budget != null) {
                            budget.addSpent(amount);
                            budgetService.updateBudget(budget);
                        }
                    }
                    
                    JOptionPane.showMessageDialog(mainFrame, "Transaksi Berhasil!");
                    mainFrame.clearTransactionForm();
                    updateDashboard();
                    refreshBudgetTable(); 
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Gagal! Saldo mungkin tidak cukup.");
                }
            } else {
                System.out.println("DEBUG: Account/Category not found. Acc: " + accName + ", Cat: " + catName + ", TypeIdx: " + typeIndex);
                JOptionPane.showMessageDialog(mainFrame, "Gagal: Kategori atau Akun tidak valid.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Jumlah tidak valid!");
        }
    }
    
    private void refreshBudgetTable() {
        if (mainFrame != null && currentUser != null) {
            // Simpan ke variable global agar bisa dipakai delete
            this.currentBudgets = budgetService.getBudgetsByUser(currentUser.getUserId());
            mainFrame.updateBudgetTable(currentBudgets, categoryService);
        }
    }
    
    private void refreshAccountDropdown() {
        List<Account> accounts = accountService.getAccountsByUser(currentUser.getUserId());
        List<String> names = accounts.stream().map(Account::getAccountName).collect(Collectors.toList());
        mainFrame.setAvailableAccounts(names);
    }
    
    private void refreshCategoryDropdown() {
        // Gunakan index agar konsisten
        int typeIndex = mainFrame.getTransactionTypeIndex();
        Category.CategoryType type = (typeIndex == 0) ? Category.CategoryType.INCOME : Category.CategoryType.EXPENSE;
        
        List<Category> cats = categoryService.getCategoriesByType(currentUser.getUserId(), type);
        List<String> names = cats.stream().map(Category::getCategoryName).collect(Collectors.toList());
        mainFrame.setAvailableCategories(names);
    }
    
    private void updateDashboard() {
        if (mainFrame == null || currentUser == null) return;
        double totalBalance = accountService.getAccountsByUser(currentUser.getUserId()).stream().mapToDouble(Account::getBalance).sum();
        List<Transaction> transactions = transactionService.getTransactionsByUser(currentUser.getUserId());
        java.time.YearMonth currentMonth = java.time.YearMonth.now();
        double income = 0, expense = 0;
        Map<String, Double> expenseByCategory = new HashMap<>();
        
        for (Transaction t : transactions) {
            if (java.time.YearMonth.from(t.getTransactionDate()).equals(currentMonth)) {
                if (t.getTransactionType() == Transaction.TransactionType.INCOME) income += t.getAmount();
                else {
                    expense += t.getAmount();
                    Category cat = categoryService.getCategoryById(t.getCategoryId());
                    String catName = (cat != null) ? cat.getCategoryName() : "Unknown";
                    expenseByCategory.put(catName, expenseByCategory.getOrDefault(catName, 0.0) + t.getAmount());
                }
            }
        }
        mainFrame.updateDashboard(totalBalance, income, expense, totalBalance - expense);
        mainFrame.updateExpenseChart(expenseByCategory);
    }
    
    public void showLoginScreen() {
        if (mainFrame != null) mainFrame.setVisible(false);
        loginFrame.setVisible(true);
        currentUser = null;
    }
}