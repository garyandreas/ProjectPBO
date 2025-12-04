package frontend.controller;

import backend.models.*;
import backend.services.*;
import frontend.ui.*;
import java.util.*;
import backend.utils.LocalizationUtils;

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
        this.mainFrame = null;
        
        setupEventHandlers();
    }
    
    private void setupEventHandlers() {
        loginFrame.addLoginListener(e -> handleLogin());
        loginFrame.addRegisterListener(e -> handleRegister());
    }
    
    private void handleLogin() {
        String username = loginFrame.getUsername();
        String password = loginFrame.getPassword();
        
        System.out.println("🔐 LOGIN ATTEMPT: " + username);
        
        User user = userService.loginUser(username, password);
        if (user != null) {
            this.currentUser = user;
            System.out.println("✅ Login SUCCESS: " + user.getFullName());
            
            // === [BARU] SET LANGUAGE ===
            // Set bahasa aplikasi berdasarkan preferensi user
            LocalizationUtils.setLanguage(user.getLanguage());
            System.out.println("🌍 Language set to: " + user.getLanguage());

            // Reload All Data
            accountService.reloadFromFile();
            categoryService.reloadFromFile();
            transactionService.reloadFromFile();
            budgetService.reloadFromFile();
            goalService.reloadFromFile();
            
            openMainApplication();
        } else {
            javax.swing.JOptionPane.showMessageDialog(loginFrame, 
                "Login gagal! Username atau password salah.");
        }
    }
    
    private void handleRegister() {
        RegisterDialogModern dialog = new RegisterDialogModern(loginFrame);
        dialog.addRegisterListener(e -> {
            // === STEP 1: Akun ===
            if (dialog.getCurrentStep() == 1) {
                String fullName = dialog.getFullName();
                String username = dialog.getUsername();
                String email = dialog.getEmail();
                String password = dialog.getPassword();
                String confirmPassword = dialog.getConfirmPassword();
                
                // Validasi Input
                if (fullName.isEmpty()) { dialog.setStatus("Nama Lengkap tidak boleh kosong"); return; }
                if (username.isEmpty()) { dialog.setStatus("Username tidak boleh kosong"); return; }
                if (email.isEmpty()) { dialog.setStatus("Email tidak boleh kosong"); return; }
                if (!email.contains("@")) { dialog.setStatus("Format email tidak valid"); return; }
                if (password.isEmpty()) { dialog.setStatus("Password tidak boleh kosong"); return; }
                if (!password.equals(confirmPassword)) { dialog.setStatus("Password tidak cocok"); return; }
                if (username.length() < 3) { dialog.setStatus("Username minimal 3 karakter"); return; }
                
                // Jika valid, lanjut ke Step 2
                dialog.showSetupStep();
                
            } else if (dialog.getCurrentStep() == 2) {
                // === STEP 2: Setup & Finish ===
                // Ambil data yang tersimpan di field dialog (dari step 1 juga)
                String fullName = dialog.getFullName(); 
                String username = dialog.getUsername();
                String email = dialog.getEmail();
                String password = dialog.getPassword();
                
                // Ambil data step 2
                String pin = dialog.getPin();
                String currency = dialog.getCurrency();
                String language = dialog.getLanguage();
                String mainGoal = dialog.getMainGoal();
                String balanceStr = dialog.getInitialBalance();
                
                // Validasi Step 2
                if (pin.isEmpty() || pin.length() != 6 || !pin.matches("\\d+")) {
                    dialog.setStatus("PIN harus 6 digit angka!");
                    return;
                }
                
                double initialBalance = 0;
                try {
                    initialBalance = Double.parseDouble(balanceStr);
                    if (initialBalance < 0) {
                        dialog.setStatus("Saldo awal tidak boleh negatif!");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    dialog.setStatus("Saldo harus berupa angka!");
                    return;
                }
                
                System.out.println("👤 REGISTERING: " + username);
                
                // REGISTER: Pass 'fullName' instead of 'username' for the name parameter
                boolean success = userService.registerUser(username, email, password, fullName);
                
                if (success) {
                    User newUser = userService.getUserByUsername(username);
                    
                    // Update profil tambahan
                    newUser.setPin(pin);
                    newUser.setCurrency(currency);
                    newUser.setLanguage(language);
                    newUser.setMainGoal(mainGoal);
                    userService.updateUser(newUser);
                    
                    // Buat Akun Default
                    accountService.createAccount(newUser.getUserId(), "Dompet Tunai", 
                                                Account.AccountType.CASH, initialBalance);
                    accountService.createAccount(newUser.getUserId(), "Rekening Bank", 
                                                Account.AccountType.BANK, 0);
                    
                    // Buat Kategori Default
                    categoryService.createDefaultCategories(newUser.getUserId());
                    
                    System.out.println("✅ REGISTRATION COMPLETE");
                    
                    javax.swing.JOptionPane.showMessageDialog(loginFrame, 
                        "Registrasi berhasil!\n" +
                        "Selamat datang, " + fullName + "!\n" +
                        "Silahkan login.");
                    
                    loginFrame.clearFields();
                    dialog.dispose();
                } else {
                    dialog.setStatus("Registrasi gagal! Username/email sudah ada.");
                }
            }
        });
        
        dialog.addCancelListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }
    
    // ... Method openMainApplication, addTransaction, dll tetap sama (copy dari sebelumnya jika perlu) ...
    // Pastikan untuk copy-paste bagian bawah file controller yang tidak diubah di sini
    
    private void openMainApplication() {
        loginFrame.setVisible(false);
        mainFrame = new MainFrameModern();
        mainFrame.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                showLoginScreen();
            }
        });
        
        // Sapa user dengan Nama Lengkap
        mainFrame.setUserGreeting(currentUser.getFullName());
        
        setupMainFrameHandlers();
        mainFrame.setVisible(true);
        
        // Populate accounts
        java.util.List<Account> userAccounts = accountService.getAccountsByUser(currentUser.getUserId());
        java.util.List<String> accountNames = new java.util.ArrayList<>();
        for (Account a : userAccounts) {
            accountNames.add(a.getAccountName());
        }
        mainFrame.setAvailableAccounts(accountNames);
        
        updateDashboard();
    }
    
    // ... Copy method lain (addTransaction, addBudget, addGoal, showLoginScreen, updateDashboard, getters) ...
    // Agar ringkas, saya asumsikan method-method di bawah ini sama persis dengan versi sebelumnya.
    // Jika Anda butuh full code 100% lagi, beritahu saya.
    
    private void setupMainFrameHandlers() {
        mainFrame.addTransactionListener(e -> handleAddTransaction());
        mainFrame.addBudgetListener(e -> handleAddBudget());
        mainFrame.addGoalListener(e -> handleAddGoal());
    }
    
    // ... Insert logic handleAddTransaction, handleAddBudget, handleAddGoal here ...
    // (Sama seperti sebelumnya)
    
    private void handleAddTransaction() {
        if (currentUser == null) return;
        
        String account = mainFrame.getSelectedAccount();
        String type = mainFrame.getTransactionType();
        String category = mainFrame.getTransactionCategory();
        String amountStr = mainFrame.getTransactionAmount();
        String description = mainFrame.getTransactionDescription();
        String selectedType = mainFrame.getTransactionType();
        Transaction.TransactionType transType;
        
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(mainFrame, "Jumlah harus berupa angka!");
            return;
        }
        
        if (amount <= 0) {
            javax.swing.JOptionPane.showMessageDialog(mainFrame, "Jumlah harus lebih dari 0!");
            return;
        }
        
        if (selectedType.equals(LocalizationUtils.getString("trans.type.income")) || selectedType.equals("Pemasukan")) {
            transType = Transaction.TransactionType.INCOME;
        } else {
            transType = Transaction.TransactionType.EXPENSE;
        }
        
        Transaction.TransactionType transType = type.equals("Pemasukan") ? 
            Transaction.TransactionType.INCOME : Transaction.TransactionType.EXPENSE;
        
        boolean success = addTransaction(account, category, transType, amount, description);
        
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
        javax.swing.JDialog dialog = new javax.swing.JDialog(mainFrame, "Tambah Budget", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(mainFrame);
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(5, 2, 10, 10));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(new javax.swing.JLabel("Kategori:"));
        javax.swing.JComboBox<String> categoryCombo = new javax.swing.JComboBox<>();
        for (Category cat : categories) categoryCombo.addItem(cat.getCategoryName());
        panel.add(categoryCombo);
        panel.add(new javax.swing.JLabel("Batas Budget (Rp):"));
        javax.swing.JTextField budgetField = new javax.swing.JTextField();
        panel.add(budgetField);
        panel.add(new javax.swing.JLabel("Periode:"));
        javax.swing.JComboBox<String> periodCombo = new javax.swing.JComboBox<>(new String[]{"MONTHLY", "YEARLY"});
        panel.add(periodCombo);
        panel.add(new javax.swing.JLabel("Alert Threshold (%):"));
        javax.swing.JTextField thresholdField = new javax.swing.JTextField("80");
        panel.add(thresholdField);
        javax.swing.JButton saveBtn = new javax.swing.JButton("Simpan");
        saveBtn.addActionListener(e -> {
            try {
                int idx = categoryCombo.getSelectedIndex();
                Category cat = categories.get(idx);
                double limit = Double.parseDouble(budgetField.getText());
                Budget.BudgetPeriod period = periodCombo.getSelectedIndex() == 0 ? Budget.BudgetPeriod.MONTHLY : Budget.BudgetPeriod.YEARLY;
                int th = Integer.parseInt(thresholdField.getText());
                Budget b = budgetService.createBudget(currentUser.getUserId(), cat.getCategoryId(), limit, period);
                b.setAlertThreshold(th);
                javax.swing.JOptionPane.showMessageDialog(dialog, "Budget berhasil!");
                dialog.dispose();
            } catch(Exception ex) { javax.swing.JOptionPane.showMessageDialog(dialog, "Input error"); }
        });
        dialog.add(panel, java.awt.BorderLayout.CENTER);
        dialog.add(saveBtn, java.awt.BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void handleAddGoal() {
        if (currentUser == null) return;
        javax.swing.JDialog dialog = new javax.swing.JDialog(mainFrame, "Tambah Target", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(mainFrame);
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(5, 2, 10, 10));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(new javax.swing.JLabel("Nama Target:"));
        javax.swing.JTextField nameField = new javax.swing.JTextField();
        panel.add(nameField);
        panel.add(new javax.swing.JLabel("Target Jumlah (Rp):"));
        javax.swing.JTextField amountField = new javax.swing.JTextField();
        panel.add(amountField);
        panel.add(new javax.swing.JLabel("Target Tanggal (yyyy-MM-dd):"));
        javax.swing.JTextField dateField = new javax.swing.JTextField("2024-12-31");
        panel.add(dateField);
        javax.swing.JButton saveBtn = new javax.swing.JButton("Simpan");
        saveBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double amount = Double.parseDouble(amountField.getText());
                java.time.LocalDateTime date = java.time.LocalDateTime.parse(dateField.getText() + "T00:00:00");
                goalService.createGoal(currentUser.getUserId(), name, amount, date);
                javax.swing.JOptionPane.showMessageDialog(dialog, "Goal berhasil!");
                dialog.dispose();
            } catch(Exception ex) { javax.swing.JOptionPane.showMessageDialog(dialog, "Input error: " + ex.getMessage()); }
        });
        dialog.add(panel, java.awt.BorderLayout.CENTER);
        dialog.add(saveBtn, java.awt.BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public void showLoginScreen() {
        if (mainFrame != null) mainFrame.setVisible(false);
        loginFrame.setVisible(true);
        currentUser = null;
    }

    private void updateDashboard() {
        if (mainFrame == null || currentUser == null) return;
        double totalBalance = 0;
        for (Account a : accountService.getAccountsByUser(currentUser.getUserId())) totalBalance += a.getBalance();
        
        double income = 0;
        double expense = 0;
        java.time.YearMonth currentMonth = java.time.YearMonth.now();
        for (Transaction t : transactionService.getTransactionsByUser(currentUser.getUserId())) {
            if (java.time.YearMonth.from(t.getTransactionDate()).equals(currentMonth)) {
                if (t.getTransactionType() == Transaction.TransactionType.INCOME) income += t.getAmount();
                else expense += t.getAmount();
            }
        }
        mainFrame.updateDashboard(totalBalance, income, expense, totalBalance - expense);
    }

    public boolean addTransaction(String accName, String catName, Transaction.TransactionType type, double amt, String desc) {
        if (currentUser == null) return false;
        Account acc = accountService.getAccountsByUser(currentUser.getUserId()).stream().filter(a -> a.getAccountName().equals(accName)).findFirst().orElse(null);
        Category cat = categoryService.getCategoriesByUser(currentUser.getUserId()).stream().filter(c -> c.getCategoryName().equals(catName)).findFirst().orElse(null);
        if (acc == null || cat == null) return false;
        return transactionService.createTransaction(acc.getAccountId(), currentUser.getUserId(), cat.getCategoryId(), type, amt, desc, 0);
    }
}