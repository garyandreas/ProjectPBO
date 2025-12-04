package frontend.controller;

import backend.models.*;
import backend.services.*;
import backend.utils.LocalizationUtils;
import frontend.ui.*;
import java.util.*;
import java.util.stream.Collectors;

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
            
            // Reload Data
            accountService.reloadFromFile();
            categoryService.reloadFromFile();
            transactionService.reloadFromFile();
            
            openMainApplication();
        } else {
            javax.swing.JOptionPane.showMessageDialog(loginFrame, "Login gagal!");
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
                     
                     // Create Defaults
                     double bal = 0;
                     try { bal = Double.parseDouble(dialog.getInitialBalance()); } catch(Exception ex) {}
                     
                     accountService.createAccount(newUser.getUserId(), "Dompet Tunai", Account.AccountType.CASH, bal);
                     accountService.createAccount(newUser.getUserId(), "Rekening Bank", Account.AccountType.BANK, 0);
                     categoryService.createDefaultCategories(newUser.getUserId());
                     
                     javax.swing.JOptionPane.showMessageDialog(loginFrame, "Registrasi Berhasil!");
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
        
        mainFrame.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) { showLoginScreen(); }
        });
        
        mainFrame.setUserGreeting(currentUser.getFullName());
        
        refreshAccountDropdown();
        refreshCategoryDropdown(); // Load kategori awal
        setupMainFrameHandlers();
        
        mainFrame.setVisible(true);
        updateDashboard();
    }
    
    private void setupMainFrameHandlers() {
        mainFrame.addTransactionListener(e -> handleAddTransaction());
        // Listener penting: Ubah kategori saat tipe transaksi berubah
        mainFrame.addTransactionTypeListener(e -> refreshCategoryDropdown());
        
        mainFrame.addBudgetListener(e -> javax.swing.JOptionPane.showMessageDialog(mainFrame, "Fitur Budget akan datang!"));
        mainFrame.addGoalListener(e -> javax.swing.JOptionPane.showMessageDialog(mainFrame, "Fitur Goal akan datang!"));
    }
    
    private void refreshAccountDropdown() {
        List<Account> accounts = accountService.getAccountsByUser(currentUser.getUserId());
        List<String> names = accounts.stream().map(Account::getAccountName).collect(Collectors.toList());
        mainFrame.setAvailableAccounts(names);
    }
    
    private void refreshCategoryDropdown() {
        String typeStr = mainFrame.getTransactionType();
        Category.CategoryType type = (typeStr.equals(LocalizationUtils.getString("trans.type.income")) || typeStr.equals("Pemasukan")) 
                                     ? Category.CategoryType.INCOME 
                                     : Category.CategoryType.EXPENSE;
                                     
        List<Category> cats = categoryService.getCategoriesByType(currentUser.getUserId(), type);
        List<String> names = cats.stream().map(Category::getCategoryName).collect(Collectors.toList());
        mainFrame.setAvailableCategories(names);
    }
    
    private void updateDashboard() {
        if (mainFrame == null || currentUser == null) return;
        
        // 1. Calculate Totals
        double totalBalance = accountService.getAccountsByUser(currentUser.getUserId())
                                            .stream().mapToDouble(Account::getBalance).sum();
        
        List<Transaction> transactions = transactionService.getTransactionsByUser(currentUser.getUserId());
        java.time.YearMonth currentMonth = java.time.YearMonth.now();
        
        double income = 0;
        double expense = 0;
        
        // Map untuk Grafik: Kategori -> Total
        Map<String, Double> expenseByCategory = new HashMap<>();
        
        for (Transaction t : transactions) {
            if (java.time.YearMonth.from(t.getTransactionDate()).equals(currentMonth)) {
                if (t.getTransactionType() == Transaction.TransactionType.INCOME) {
                    income += t.getAmount();
                } else {
                    expense += t.getAmount();
                    
                    // Add to chart data
                    Category cat = categoryService.getCategoryById(t.getCategoryId());
                    String catName = (cat != null) ? cat.getCategoryName() : "Unknown";
                    expenseByCategory.put(catName, expenseByCategory.getOrDefault(catName, 0.0) + t.getAmount());
                }
            }
        }
        
        mainFrame.updateDashboard(totalBalance, income, expense, totalBalance - expense);
        mainFrame.updateExpenseChart(expenseByCategory);
    }
    
    private void handleAddTransaction() {
        String accName = mainFrame.getSelectedAccount();
        String catName = mainFrame.getTransactionCategory();
        String typeStr = mainFrame.getTransactionType();
        
        try {
            double amount = Double.parseDouble(mainFrame.getTransactionAmount());
            if (amount <= 0) throw new NumberFormatException();
            
            Transaction.TransactionType type = (typeStr.equals(LocalizationUtils.getString("trans.type.income")) || typeStr.equals("Pemasukan")) 
                                             ? Transaction.TransactionType.INCOME 
                                             : Transaction.TransactionType.EXPENSE;
            
            Account acc = accountService.getAccountsByUser(currentUser.getUserId()).stream()
                            .filter(a -> a.getAccountName().equals(accName)).findFirst().orElse(null);
            
            Category cat = categoryService.getCategoriesByUser(currentUser.getUserId()).stream()
                            .filter(c -> c.getCategoryName().equals(catName)).findFirst().orElse(null);
                            
            if (acc != null && cat != null) {
                boolean success = transactionService.createTransaction(
                    acc.getAccountId(), currentUser.getUserId(), cat.getCategoryId(), type, amount, mainFrame.getTransactionDescription(), 0
                );
                
                if (success) {
                    javax.swing.JOptionPane.showMessageDialog(mainFrame, "Transaksi Berhasil!");
                    mainFrame.clearTransactionForm();
                    updateDashboard(); // Refresh Dashboard
                } else {
                    javax.swing.JOptionPane.showMessageDialog(mainFrame, "Gagal! Saldo mungkin tidak cukup.");
                }
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(mainFrame, "Jumlah tidak valid!");
        }
    }
    
    public void showLoginScreen() {
        if (mainFrame != null) mainFrame.setVisible(false);
        loginFrame.setVisible(true);
        currentUser = null;
    }
}