package frontend.controller;

import backend.models.*;
import backend.services.*;
import frontend.ui.*;
import java.util.*;

/**
 * Controller untuk menghubungkan Backend dan Frontend
 * Mengelola logic aplikasi dan event handling
 */
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
        // Initialize services
        this.userService = new UserService();
        this.accountService = new AccountService();
        this.categoryService = new CategoryService();
        this.budgetService = new BudgetService();
        this.transactionService = new TransactionService(accountService, budgetService);
        this.goalService = new FinancialGoalService();
        
        // Initialize UI
        this.loginFrame = new LoginFrameModern();
        this.mainFrame = null;
        
        setupEventHandlers();
    }
    
    private void setupEventHandlers() {
        // Login event
        loginFrame.addLoginListener(e -> handleLogin());
        
        // Register event
        loginFrame.addRegisterListener(e -> handleRegister());
    }
    
    private void handleLogin() {
        String username = loginFrame.getUsername();
        String password = loginFrame.getPassword();
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🔐 LOGIN ATTEMPT: " + username);
        System.out.println("=".repeat(80));
        
        User user = userService.loginUser(username, password);
        if (user != null) {
            this.currentUser = user;
            System.out.println("✅ Login SUCCESS: " + user.getFullName() + " (ID: " + user.getUserId() + ")");
            
            // RELOAD DATA FROM FILE
            System.out.println("\n📥 RELOADING DATA FROM FILES...");
            
            System.out.println("   1️⃣  Reloading accounts...");
            accountService.reloadFromFile();
            java.util.List<Account> accounts = accountService.getAccountsByUser(user.getUserId());
            System.out.println("      ✓ Found " + accounts.size() + " accounts for user:");
            for (Account a : accounts) {
                System.out.println("        - " + a.getAccountName() + " (ID: " + a.getAccountId() + ", Balance: Rp " + a.getBalance() + ")");
            }
            
            System.out.println("   2️⃣  Reloading categories...");
            categoryService.reloadFromFile();
            java.util.List<Category> categories = categoryService.getCategoriesByUser(user.getUserId());
            System.out.println("      ✓ Found " + categories.size() + " categories for user:");
            for (Category c : categories) {
                System.out.println("        - " + c.getCategoryName() + " (" + c.getCategoryType() + ", ID: " + c.getCategoryId() + ")");
            }
            
            System.out.println("   3️⃣  Reloading transactions...");
            transactionService.reloadFromFile();
            
            System.out.println("   4️⃣  Reloading budgets...");
            budgetService.reloadFromFile();
            
            System.out.println("   5️⃣  Reloading goals...");
            goalService.reloadFromFile();
            
            System.out.println("✅ ALL DATA RELOADED SUCCESSFULLY\n");
            
            openMainApplication();
        } else {
            System.out.println("❌ Login FAILED: Invalid credentials");
            javax.swing.JOptionPane.showMessageDialog(loginFrame, 
                "Login gagal! Username atau password salah.");
        }
    }
    
    private void handleRegister() {
        RegisterDialogModern dialog = new RegisterDialogModern(loginFrame);
        dialog.setVisible(true);
        
        String username = dialog.getUsername();
        String password = dialog.getPassword();
        String confirmPassword = dialog.getConfirmPassword();
        
        if (username.isEmpty()) {
            dialog.setStatus("Username tidak boleh kosong");
            return;
        }
        
        if (password.isEmpty()) {
            dialog.setStatus("Password tidak boleh kosong");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            dialog.setStatus("Password tidak cocok");
            return;
        }
        
        if (username.length() < 3) {
            dialog.setStatus("Username minimal 3 karakter");
            return;
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("👤 REGISTERING NEW USER");
        System.out.println("=".repeat(80));
        
        boolean success = userService.registerUser(username, "", password, username);
        if (success) {
            // Create default accounts and categories for new user
            User newUser = userService.getUserByUsername(username);
            System.out.println("✅ User registered: " + newUser.getFullName() + " (ID: " + newUser.getUserId() + ")");
            
            System.out.println("\n📝 Creating default accounts...");
            Account acc1 = accountService.createAccount(newUser.getUserId(), "Cash", 
                                        Account.AccountType.CASH, 0);
            System.out.println("   ✓ Created: " + acc1.getAccountName() + " (ID: " + acc1.getAccountId() + ")");
            
            Account acc2 = accountService.createAccount(newUser.getUserId(), "Bank Mandiri", 
                                        Account.AccountType.BANK, 0);
            System.out.println("   ✓ Created: " + acc2.getAccountName() + " (ID: " + acc2.getAccountId() + ")");
            
            System.out.println("\n🏷️  Creating default categories...");
            categoryService.createDefaultCategories(newUser.getUserId());
            System.out.println("   ✓ Categories created");
            
            System.out.println("\n✅ REGISTRATION COMPLETE");
            System.out.println("=".repeat(80) + "\n");
            
            javax.swing.JOptionPane.showMessageDialog(loginFrame, 
                "Registrasi berhasil! Silahkan login dengan akun baru Anda.");
            
            // Reset form agar siap untuk login
            loginFrame.clearFields();
            dialog.dispose();
        } else {
            dialog.setStatus("Registrasi gagal! Username mungkin sudah digunakan.");
        }
    }
    
    private void openMainApplication() {
        System.out.println("DEBUG: openMainApplication() called");
        System.out.println("DEBUG: currentUser = " + (currentUser != null ? currentUser.getFullName() : "null"));
        
        // Hide login frame
        loginFrame.setVisible(false);
        System.out.println("DEBUG: LoginFrame hidden");
        
        // Create and show main frame dengan close handler
        mainFrame = new MainFrameModern();
        System.out.println("DEBUG: MainFrameModern created");
        
        mainFrame.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("DEBUG: Window close event triggered");
                showLoginScreen();
            }
        });
        
        // Set user greeting
        mainFrame.setUserGreeting(currentUser.getFullName());
        
        // Setup event handlers untuk MainFrame buttons
        setupMainFrameHandlers();
        
        System.out.println("DEBUG: Setting MainFrame visible...");
        // Make sure it's visible
        mainFrame.toFront();
        mainFrame.requestFocus();
        mainFrame.setVisible(true);
        System.out.println("DEBUG: MainFrame is now visible");
        
        // Populate account dropdown dengan accounts dari database
        System.out.println("\n📋 SETTING UP ACCOUNT DROPDOWN...");
        java.util.List<Account> userAccounts = accountService.getAccountsByUser(currentUser.getUserId());
        System.out.println("   Total accounts for " + currentUser.getFullName() + ": " + userAccounts.size());
        
        java.util.List<String> accountNames = new java.util.ArrayList<>();
        for (Account a : userAccounts) {
            accountNames.add(a.getAccountName());
            System.out.println("   ✓ " + a.getAccountName());
        }
        mainFrame.setAvailableAccounts(accountNames);
        
        if (accountNames.isEmpty()) {
            System.out.println("⚠️  WARNING: User has NO accounts! Transaction will fail!");
        }
        
        // Update dashboard dengan data user
        if (currentUser != null) {
            updateDashboard();
            System.out.println("✅ Dashboard updated for user: " + currentUser.getFullName());
        }
        
        System.out.println("=".repeat(80) + "\n");
    }
    
    private void setupMainFrameHandlers() {
        // Transaction button
        mainFrame.addTransactionListener(e -> handleAddTransaction());
        
        // Budget button
        mainFrame.addBudgetListener(e -> handleAddBudget());
        
        // Goal button
        mainFrame.addGoalListener(e -> handleAddGoal());
    }
    
    private void handleAddTransaction() {
        if (currentUser == null) return;
        
        String account = mainFrame.getSelectedAccount();
        String type = mainFrame.getTransactionType();
        String category = mainFrame.getTransactionCategory();
        String amountStr = mainFrame.getTransactionAmount();
        String description = mainFrame.getTransactionDescription();
        
        // Parse amount
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(mainFrame, "Jumlah harus berupa angka!");
            return;
        }
        
        System.out.println("\n=== DEBUG: handleAddTransaction called ===");
        System.out.println("  Current User: " + currentUser.getFullName() + " (ID: " + currentUser.getUserId() + ")");
        System.out.println("  Account: " + account);
        System.out.println("  Type: " + type);
        System.out.println("  Category: " + category);
        System.out.println("  Amount: " + amount);
        System.out.println("  Description: " + description);
        
        if (amount <= 0) {
            javax.swing.JOptionPane.showMessageDialog(mainFrame, "Jumlah harus lebih dari 0!");
            return;
        }
        
        // Convert type string to enum
        Transaction.TransactionType transType = type.equals("Pemasukan") ? 
            Transaction.TransactionType.INCOME : Transaction.TransactionType.EXPENSE;
        
        System.out.println("DEBUG: Calling addTransaction with type: " + transType);
        boolean success = addTransaction(account, category, transType, amount, description);
        System.out.println("DEBUG: addTransaction returned: " + success);
        
        if (success) {
            javax.swing.JOptionPane.showMessageDialog(mainFrame, "Transaksi berhasil ditambahkan!");
            mainFrame.clearTransactionForm();
            updateDashboard();
        } else {
            javax.swing.JOptionPane.showMessageDialog(mainFrame, "Gagal menambahkan transaksi!");
        }
    }
    
    private void handleAddBudget() {
        if (currentUser == null) return;
        
        List<Category> categories = categoryService.getCategoriesByUser(currentUser.getUserId());
        if (categories.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(mainFrame, "Tidak ada kategori. Buat kategori terlebih dahulu!");
            return;
        }
        
        // Create dialog
        javax.swing.JDialog dialog = new javax.swing.JDialog(mainFrame, "Tambah Budget", true);
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(mainFrame);
        
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new java.awt.GridLayout(5, 2, 10, 10));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Category
        panel.add(new javax.swing.JLabel("Kategori:"));
        javax.swing.JComboBox<String> categoryCombo = new javax.swing.JComboBox<>();
        for (Category cat : categories) {
            categoryCombo.addItem(cat.getCategoryName());
        }
        panel.add(categoryCombo);
        
        // Budget Limit
        panel.add(new javax.swing.JLabel("Batas Budget (Rp):"));
        javax.swing.JTextField budgetField = new javax.swing.JTextField();
        panel.add(budgetField);
        
        // Period
        panel.add(new javax.swing.JLabel("Periode:"));
        javax.swing.JComboBox<String> periodCombo = new javax.swing.JComboBox<>(new String[]{"MONTHLY", "YEARLY"});
        panel.add(periodCombo);
        
        // Alert Threshold
        panel.add(new javax.swing.JLabel("Alert Threshold (%):"));
        javax.swing.JTextField thresholdField = new javax.swing.JTextField("80");
        panel.add(thresholdField);
        
        // Buttons
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        javax.swing.JButton saveBtn = new javax.swing.JButton("Simpan");
        javax.swing.JButton cancelBtn = new javax.swing.JButton("Batal");
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        saveBtn.addActionListener(e -> {
            try {
                int categoryIndex = categoryCombo.getSelectedIndex();
                Category selectedCat = categories.get(categoryIndex);
                double limit = Double.parseDouble(budgetField.getText());
                Budget.BudgetPeriod period = periodCombo.getSelectedIndex() == 0 ? 
                    Budget.BudgetPeriod.MONTHLY : Budget.BudgetPeriod.YEARLY;
                int threshold = Integer.parseInt(thresholdField.getText());
                
                Budget budget = new Budget(currentUser.getUserId(), selectedCat.getCategoryId(), limit, period);
                budget.setAlertThreshold(threshold);
                budgetService.createBudget(currentUser.getUserId(), selectedCat.getCategoryId(), limit, period);
                
                javax.swing.JOptionPane.showMessageDialog(dialog, "Budget berhasil ditambahkan!");
                dialog.dispose();
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(dialog, "Input tidak valid!");
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel, java.awt.BorderLayout.CENTER);
        dialog.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    private void handleAddGoal() {
        if (currentUser == null) return;
        
        // Create dialog
        javax.swing.JDialog dialog = new javax.swing.JDialog(mainFrame, "Tambah Target Tabungan", true);
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(mainFrame);
        
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new java.awt.GridLayout(5, 2, 10, 10));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Goal Name
        panel.add(new javax.swing.JLabel("Nama Target:"));
        javax.swing.JTextField nameField = new javax.swing.JTextField();
        panel.add(nameField);
        
        // Goal Amount
        panel.add(new javax.swing.JLabel("Target Jumlah (Rp):"));
        javax.swing.JTextField amountField = new javax.swing.JTextField();
        panel.add(amountField);
        
        // Target Date
        panel.add(new javax.swing.JLabel("Target Tanggal:"));
        javax.swing.JTextField dateField = new javax.swing.JTextField("yyyy-MM-dd");
        panel.add(dateField);
        
        // Priority
        panel.add(new javax.swing.JLabel("Prioritas:"));
        javax.swing.JComboBox<String> priorityCombo = new javax.swing.JComboBox<>(new String[]{"LOW", "MEDIUM", "HIGH"});
        panel.add(priorityCombo);
        
        // Buttons
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        javax.swing.JButton saveBtn = new javax.swing.JButton("Simpan");
        javax.swing.JButton cancelBtn = new javax.swing.JButton("Batal");
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        saveBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double amount = Double.parseDouble(amountField.getText());
                String dateStr = dateField.getText();
                
                if (name.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(dialog, "Nama target tidak boleh kosong!");
                    return;
                }
                
                java.time.LocalDateTime targetDate = java.time.LocalDateTime.parse(dateStr + "T00:00:00");
                goalService.createGoal(currentUser.getUserId(), name, amount, targetDate);
                
                javax.swing.JOptionPane.showMessageDialog(dialog, "Target tabungan berhasil ditambahkan!");
                dialog.dispose();
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(dialog, "Input tidak valid! Format tanggal: yyyy-MM-dd");
            }
        });
        
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel, java.awt.BorderLayout.CENTER);
        dialog.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    public void showLoginScreen() {
        System.out.println("DEBUG: showLoginScreen() called");
        if (mainFrame != null) {
            mainFrame.setVisible(false);
        }
        
        System.out.println("DEBUG: Setting LoginFrame visible");
        loginFrame.toFront();
        loginFrame.requestFocus();
        loginFrame.setVisible(true);
        System.out.println("DEBUG: LoginFrame is now visible");
        
        currentUser = null;
    }
    
    /**
     * Update dashboard dengan data dari database
     */
    private void updateDashboard() {
        if (mainFrame == null || currentUser == null) return;
        
        // Hitung total balance dari semua account
        double totalBalance = 0;
        java.util.List<Account> accounts = accountService.getAccountsByUser(currentUser.getUserId());
        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }
        
        // Hitung income dan expense dari transaksi bulan ini
        double income = 0;
        double expense = 0;
        java.util.List<Transaction> transactions = transactionService.getTransactionsByUser(currentUser.getUserId());
        
        java.time.YearMonth currentMonth = java.time.YearMonth.now();
        for (Transaction trans : transactions) {
            java.time.YearMonth transMonth = java.time.YearMonth.from(trans.getTransactionDate());
            if (transMonth.equals(currentMonth)) {
                if (trans.getTransactionType() == Transaction.TransactionType.INCOME) {
                    income += trans.getAmount();
                } else {
                    expense += trans.getAmount();
                }
            }
        }
        
        double saving = totalBalance - expense;
        
        // Update UI
        mainFrame.updateDashboard(totalBalance, income, expense, saving);
    }
    
    /**
     * Tambah transaksi dari UI
     */
    public boolean addTransaction(String accountName, String categoryName, 
                                  Transaction.TransactionType type, 
                                  double amount, String description) {
        if (currentUser == null) {
            System.out.println("DEBUG: currentUser is null");
            return false;
        }
        
        System.out.println("DEBUG: addTransaction starting");
        System.out.println("  Looking for account: " + accountName);
        System.out.println("  Looking for category: " + categoryName);
        
        // Get account by name
        java.util.List<Account> userAccounts = accountService.getAccountsByUser(currentUser.getUserId());
        System.out.println("  User has " + userAccounts.size() + " accounts");
        for (Account a : userAccounts) {
            System.out.println("    - " + a.getAccountName() + " (ID: " + a.getAccountId() + ")");
        }
        
        Account account = userAccounts.stream()
                .filter(a -> a.getAccountName().equals(accountName))
                .findFirst()
                .orElse(null);
        
        if (account == null) {
            System.out.println("DEBUG: Account not found!");
            return false;
        }
        System.out.println("DEBUG: Account found: " + account.getAccountName());
        
        // Get category by name
        java.util.List<Category> userCategories = categoryService.getCategoriesByUser(currentUser.getUserId());
        System.out.println("  User has " + userCategories.size() + " categories");
        for (Category c : userCategories) {
            System.out.println("    - " + c.getCategoryName() + " (ID: " + c.getCategoryId() + ")");
        }
        
        Category category = userCategories.stream()
                .filter(c -> c.getCategoryName().equals(categoryName))
                .findFirst()
                .orElse(null);
        
        if (category == null) {
            System.out.println("DEBUG: Category not found!");
            return false;
        }
        System.out.println("DEBUG: Category found: " + category.getCategoryName());
        
        System.out.println("DEBUG: Creating transaction...");
        boolean result = transactionService.createTransaction(
            account.getAccountId(),
            currentUser.getUserId(),
            category.getCategoryId(),
            type,
            amount,
            description,
            0
        );
        System.out.println("DEBUG: Transaction created: " + result);
        return result;
    }
    
    /**
     * Tambah budget dari UI
     */
    public boolean addBudget(String categoryName, double limit, Budget.BudgetPeriod period) {
        if (currentUser == null) return false;
        
        Category category = categoryService.getCategoriesByUser(currentUser.getUserId())
                .stream()
                .filter(c -> c.getCategoryName().equals(categoryName))
                .findFirst()
                .orElse(null);
        
        if (category == null) return false;
        
        Budget budget = budgetService.createBudget(
            currentUser.getUserId(),
            category.getCategoryId(),
            limit,
            period
        );
        
        return budget != null;
    }
    
    /**
     * Tambah goal dari UI
     */
    public boolean addGoal(String goalName, double targetAmount, java.time.LocalDateTime targetDate) {
        if (currentUser == null) return false;
        
        FinancialGoal goal = goalService.createGoal(
            currentUser.getUserId(),
            goalName,
            targetAmount,
            targetDate
        );
        
        return goal != null;
    }
    
    // Getters
    public User getCurrentUser() {
        return currentUser;
    }
    
    public UserService getUserService() {
        return userService;
    }
    
    public AccountService getAccountService() {
        return accountService;
    }
    
    public CategoryService getCategoryService() {
        return categoryService;
    }
    
    public TransactionService getTransactionService() {
        return transactionService;
    }
    
    public BudgetService getBudgetService() {
        return budgetService;
    }
    
    public FinancialGoalService getGoalService() {
        return goalService;
    }
}
