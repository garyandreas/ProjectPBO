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

    private List<Budget> currentBudgets;

    public ApplicationController() {
        this.userService = new UserService();
        this.accountService = new AccountService();
        this.categoryService = new CategoryService();
        this.budgetService = new BudgetService();
        this.goalService = new FinancialGoalService();
        this.transactionService = new TransactionService(accountService, budgetService, goalService);

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
                boolean success = userService.registerUser(dialog.getUsername(), dialog.getEmail(),
                        dialog.getPassword(), dialog.getFullName());
                if (success) {
                    User newUser = userService.getUserByUsername(dialog.getUsername());
                    newUser.setPin(dialog.getPin());
                    newUser.setCurrency(dialog.getCurrency());
                    newUser.setLanguage(dialog.getLanguage());
                    userService.updateUser(newUser);

                    try {
                        double bal = Double.parseDouble(dialog.getInitialBalance());
                        accountService.createAccount(newUser.getUserId(), "Dompet Tunai", Account.AccountType.CASH,
                                bal);
                    } catch (Exception ex) {
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
            public void windowClosing(java.awt.event.WindowEvent e) {
                showLoginScreen();
            }
        });

        mainFrame.setUserGreeting(currentUser.getFullName());

        refreshAccountDropdown();
        refreshCategoryDropdown();
        setupMainFrameHandlers();

        mainFrame.setVisible(true);
        updateDashboard();
        refreshBudgetTable();
        refreshGoalList();
    }

    public void showLoginScreen() {
        if (mainFrame != null)
            mainFrame.setVisible(false);
        loginFrame.setVisible(true);
        currentUser = null;
    }

    private void setupMainFrameHandlers() {
        mainFrame.addTransactionListener(e -> handleAddTransaction());
        mainFrame.addTransactionTypeListener(e -> refreshCategoryDropdown());

        mainFrame.addBudgetListener(e -> handleAddBudget());
        mainFrame.addDeleteBudgetListener(e -> handleDeleteBudget());

        mainFrame.addGoalListener(e -> handleAddGoal());
        mainFrame.setOnDepositGoal(this::handleDepositToGoal);

        mainFrame.addTransferListener(e -> handleTransfer());

        // Listeners for Dashboard Period & Reports
        mainFrame.addDashboardFilterListener(e -> {
            updateDashboard();
            ReportPanel.FilterParams filter = mainFrame.getDashboardFilter();
            updateReports(filter);
        });
    }

    private void handleTransfer() {
        if (currentUser == null)
            return;
        List<Account> accounts = accountService.getAccountsByUser(currentUser.getUserId());
        List<String> accNames = accounts.stream().map(Account::getAccountName).collect(Collectors.toList());

        TransferDialog dialog = new TransferDialog(mainFrame, accNames);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String sourceName = dialog.getSourceAccount();
            String targetName = dialog.getTargetAccount();
            double amount = dialog.getAmount();

            if (sourceName.equals(targetName)) {
                JOptionPane.showMessageDialog(mainFrame, "Akun asal dan tujuan tidak boleh sama!");
                return;
            }

            Account sourceAcc = accounts.stream().filter(a -> a.getAccountName().equals(sourceName)).findFirst()
                    .orElse(null);
            Account targetAcc = accounts.stream().filter(a -> a.getAccountName().equals(targetName)).findFirst()
                    .orElse(null);

            if (sourceAcc != null && targetAcc != null && amount > 0) {
                // Find or Create Transfer Category
                Category transferCat = categoryService
                        .getCategoriesByType(currentUser.getUserId(), Category.CategoryType.EXPENSE).stream()
                        .filter(c -> c.getCategoryName().equalsIgnoreCase("Transfer")
                                || c.getCategoryName().equalsIgnoreCase("Umum"))
                        .findFirst().orElse(null);

                if (transferCat == null) {
                    transferCat = categoryService.createCategory(currentUser.getUserId(), "Transfer",
                            Category.CategoryType.EXPENSE);
                }

                // 1. Transaction Out from Source (TRANSFER type)
                boolean successOut = transactionService.createTransaction(
                        sourceAcc.getAccountId(), currentUser.getUserId(), transferCat.getCategoryId(),
                        Transaction.TransactionType.TRANSFER, amount, "Transfer ke " + targetName, 0);

                if (successOut) {
                    // 2. Transaction In to Target (TRANSFER_IN type)
                    transactionService.createTransaction(
                            targetAcc.getAccountId(), currentUser.getUserId(), transferCat.getCategoryId(),
                            Transaction.TransactionType.TRANSFER_IN, amount, "Transfer dari " + sourceName, 0);

                    JOptionPane.showMessageDialog(mainFrame, "Transfer Berhasil!");
                    updateDashboard();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Transfer Gagal. Saldo tidak cukup.");
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Data transfer tidak valid.");
            }
        }
    }

    // --- DASHBOARD & REPORTS ---
    private void updateDashboard() {
        if (mainFrame == null || currentUser == null)
            return;

        ReportPanel.FilterParams filter = mainFrame.getDashboardFilter();

        List<Account> accounts = accountService.getAccountsByUser(currentUser.getUserId());
        double totalBalance = 0; // Calculated below

        double cashBalance = accounts.stream()
                .filter(a -> a.getAccountType() == Account.AccountType.CASH)
                .mapToDouble(Account::getBalance).sum();

        double bankBalance = accounts.stream()
                .filter(a -> a.getAccountType() == Account.AccountType.BANK)
                .mapToDouble(Account::getBalance).sum();

        // Explicit logic: Sum of Cash + Bank
        totalBalance = cashBalance + bankBalance;

        List<Transaction> transactions;
        if ("Bulanan".equals(filter.periodType)) {
            transactions = transactionService.getTransactionsByMonth(currentUser.getUserId(),
                    java.time.YearMonth.of(filter.year, filter.month));
        } else {
            java.time.LocalDateTime start = java.time.LocalDate.of(filter.year, 1, 1).atStartOfDay();
            java.time.LocalDateTime end = java.time.LocalDate.of(filter.year, 12, 31).atTime(23, 59, 59);
            transactions = transactionService.getTransactionsByPeriod(currentUser.getUserId(), start, end);
        }

        double income = 0, expense = 0;
        Map<String, Double> expenseByCategory = new HashMap<>();
        Map<String, Double> incomeByCategory = new HashMap<>();

        for (Transaction t : transactions) {
            Category cat = categoryService.getCategoryById(t.getCategoryId());
            String catName = (cat != null) ? cat.getCategoryName() : "Unknown";

            if (t.getTransactionType() == Transaction.TransactionType.INCOME) {
                income += t.getAmount();
                incomeByCategory.put(catName, incomeByCategory.getOrDefault(catName, 0.0) + t.getAmount());
            } else if (t.getTransactionType() == Transaction.TransactionType.EXPENSE) {
                expense += t.getAmount();
                expenseByCategory.put(catName, expenseByCategory.getOrDefault(catName, 0.0) + t.getAmount());
            }
            // TRANSFER/TRANSFER_IN types are excluded from Income/Expense totals/Charts
        }
        mainFrame.updateDashboard(totalBalance, cashBalance, bankBalance, income, expense);
        mainFrame.updateDashboardCharts(expenseByCategory, incomeByCategory);
        refreshBudgetTable(); // Sync Budget tab with Dashboard Period

        // Also update reports to ensure sync if called directly
        ReportPanel.FilterParams reportFilter = mainFrame.getDashboardFilter();
        updateReports(reportFilter);
    }

    private void updateReports(ReportPanel.FilterParams filter) {
        if (mainFrame == null || currentUser == null)
            return;

        List<Transaction> transactions;
        if ("Bulanan".equals(filter.periodType)) {
            transactions = transactionService.getTransactionsByMonth(currentUser.getUserId(),
                    java.time.YearMonth.of(filter.year, filter.month));
        } else {
            java.time.LocalDateTime start = java.time.LocalDate.of(filter.year, 1, 1).atStartOfDay();
            java.time.LocalDateTime end = java.time.LocalDate.of(filter.year, 12, 31).atTime(23, 59, 59);
            transactions = transactionService.getTransactionsByPeriod(currentUser.getUserId(), start, end);
        }

        double income = 0, expense = 0;
        Map<String, Double> expenseByCategory = new HashMap<>();

        // Create Map for Category Names to pass to ReportPanel
        Map<Integer, String> categoryNames = new HashMap<>();
        List<Category> allCats = categoryService.getCategoriesByUser(currentUser.getUserId());
        for (Category c : allCats) {
            categoryNames.put(c.getCategoryId(), c.getCategoryName());
        }

        for (Transaction t : transactions) {
            // Aggregate for Charts
            if (t.getTransactionType() == Transaction.TransactionType.INCOME)
                income += t.getAmount();
            else if (t.getTransactionType() == Transaction.TransactionType.EXPENSE) {
                expense += t.getAmount();
                Category cat = categoryService.getCategoryById(t.getCategoryId());
                String catName = (cat != null) ? cat.getCategoryName() : "Unknown";
                expenseByCategory.put(catName, expenseByCategory.getOrDefault(catName, 0.0) + t.getAmount());
            }

            // Ensure category name is in map (fallback)
            if (!categoryNames.containsKey(t.getCategoryId())) {
                Category cat = categoryService.getCategoryById(t.getCategoryId());
                categoryNames.put(t.getCategoryId(), (cat != null) ? cat.getCategoryName() : "Unknown");
            }
        }

        // Pass full data to ReportPanel including raw transactions for the List & CSV
        mainFrame.getReportPanel().updateData(transactions, categoryNames, expenseByCategory, income, expense);
    }

    // --- TRANSACTION HANDLERS ---
    private void handleAddTransaction() {
        String accName = mainFrame.getSelectedAccount();
        String catName = mainFrame.getTransactionCategory();
        int typeIndex = mainFrame.getTransactionTypeIndex();

        try {
            double amount = Double.parseDouble(mainFrame.getTransactionAmount());
            if (amount <= 0)
                throw new NumberFormatException();

            Transaction.TransactionType type = (typeIndex == 0)
                    ? Transaction.TransactionType.INCOME
                    : Transaction.TransactionType.EXPENSE;

            Account acc = accountService.getAccountsByUser(currentUser.getUserId()).stream()
                    .filter(a -> a.getAccountName().equals(accName)).findFirst().orElse(null);

            Category.CategoryType catType = (type == Transaction.TransactionType.INCOME) ? Category.CategoryType.INCOME
                    : Category.CategoryType.EXPENSE;
            Category cat = categoryService.getCategoriesByType(currentUser.getUserId(), catType).stream()
                    .filter(c -> c.getCategoryName().equals(catName)).findFirst().orElse(null);

            if (acc != null && cat != null) {
                boolean success = transactionService.createTransaction(
                        acc.getAccountId(), currentUser.getUserId(), cat.getCategoryId(), type, amount,
                        mainFrame.getTransactionDescription(), 0);

                if (success) {
                    JOptionPane.showMessageDialog(mainFrame, "Transaksi Berhasil!");
                    mainFrame.clearTransactionForm();
                    updateDashboard();
                    refreshBudgetTable();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Gagal! Saldo mungkin tidak cukup.");
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Gagal: Kategori atau Akun tidak valid.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(mainFrame, "Jumlah tidak valid!");
        }
    }

    private void refreshAccountDropdown() {
        if (currentUser == null)
            return;
        List<Account> accounts = accountService.getAccountsByUser(currentUser.getUserId());
        List<String> names = accounts.stream().map(Account::getAccountName).collect(Collectors.toList());
        mainFrame.setAvailableAccounts(names);
    }

    private void refreshCategoryDropdown() {
        if (currentUser == null)
            return;
        int typeIndex = mainFrame.getTransactionTypeIndex();
        Category.CategoryType type = (typeIndex == 0) ? Category.CategoryType.INCOME : Category.CategoryType.EXPENSE;

        List<Category> cats = categoryService.getCategoriesByType(currentUser.getUserId(), type);
        List<String> names = cats.stream().map(Category::getCategoryName).collect(Collectors.toList());
        mainFrame.setAvailableCategories(names);
    }

    // --- BUDGET HANDLERS ---
    private void handleAddBudget() {
        List<Category> expenseCats = categoryService.getCategoriesByType(currentUser.getUserId(),
                Category.CategoryType.EXPENSE);
        if (expenseCats.isEmpty()) {
            JOptionPane.showMessageDialog(mainFrame, "Tidak ada kategori pengeluaran tersedia.");
            return;
        }

        JPanel panel = new JPanel(new java.awt.GridLayout(0, 1));
        JComboBox<String> catCombo = new JComboBox<>();
        for (Category c : expenseCats)
            catCombo.addItem(c.getCategoryName());
        JTextField limitField = new JTextField();

        panel.add(new JLabel("Pilih Kategori:"));
        panel.add(catCombo);
        panel.add(new JLabel("Batas Anggaran (Rp):"));
        panel.add(limitField);

        int result = JOptionPane.showConfirmDialog(mainFrame, panel, "Buat Anggaran Baru", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String selectedCatName = (String) catCombo.getSelectedItem();
                double limit = Double.parseDouble(limitField.getText());
                Category selectedCat = expenseCats.stream().filter(c -> c.getCategoryName().equals(selectedCatName))
                        .findFirst().orElse(null);

                if (selectedCat != null) {
                    budgetService.createBudget(currentUser.getUserId(), selectedCat.getCategoryId(), limit,
                            Budget.BudgetPeriod.MONTHLY);
                    JOptionPane.showMessageDialog(mainFrame, "Budget berhasil dibuat!");
                    refreshBudgetTable();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Jumlah anggaran harus berupa angka!");
            }
        }
    }

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

    private void refreshBudgetTable() {
        if (mainFrame != null && currentUser != null) {
            this.currentBudgets = budgetService.getBudgetsByUser(currentUser.getUserId());

            ReportPanel.FilterParams filter = mainFrame.getDashboardFilter();
            List<Transaction> transactions;
            if ("Bulanan".equals(filter.periodType)) {
                transactions = transactionService.getTransactionsByMonth(currentUser.getUserId(),
                        java.time.YearMonth.of(filter.year, filter.month));
            } else {
                java.time.LocalDateTime start = java.time.LocalDate.of(filter.year, 1, 1).atStartOfDay();
                java.time.LocalDateTime end = java.time.LocalDate.of(filter.year, 12, 31).atTime(23, 59, 59);
                transactions = transactionService.getTransactionsByPeriod(currentUser.getUserId(), start, end);
            }

            Map<Integer, Double> spentMap = new HashMap<>();
            for (Transaction t : transactions) {
                if (t.getTransactionType() == Transaction.TransactionType.EXPENSE) {
                    spentMap.put(t.getCategoryId(), spentMap.getOrDefault(t.getCategoryId(), 0.0) + t.getAmount());
                }
            }

            mainFrame.updateBudgetTable(currentBudgets, categoryService, spentMap);
        }
    }

    // --- GOAL HANDLERS ---
    private void handleAddGoal() {
        GoalDialog dialog = new GoalDialog(mainFrame);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            goalService.createGoal(currentUser.getUserId(),
                    dialog.getGoalName(),
                    dialog.getTargetAmount(),
                    dialog.getTargetDate());
            refreshGoalList();
            JOptionPane.showMessageDialog(mainFrame, "Target Tabungan Berhasil Dibuat!");
        }
    }

    private void handleDepositToGoal(FinancialGoal goal) {
        List<Account> accounts = accountService.getAccountsByUser(currentUser.getUserId());
        List<String> accNames = accounts.stream().map(Account::getAccountName).collect(Collectors.toList());

        DepositDialog dialog = new DepositDialog(mainFrame, accNames);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String accName = dialog.getSelectedAccountName();
            double amount = dialog.getAmount();
            Account acc = accounts.stream().filter(a -> a.getAccountName().equals(accName)).findFirst().orElse(null);

            if (acc != null) {
                Category savingsCat = categoryService
                        .getCategoriesByType(currentUser.getUserId(), Category.CategoryType.EXPENSE).stream()
                        .filter(c -> c.getCategoryName().equalsIgnoreCase("Tabungan")
                                || c.getCategoryName().equalsIgnoreCase("Savings"))
                        .findFirst().orElse(null);

                if (savingsCat == null) {
                    savingsCat = categoryService.createCategory(currentUser.getUserId(), "Tabungan",
                            Category.CategoryType.EXPENSE);
                }

                boolean success = transactionService.createTransaction(
                        acc.getAccountId(),
                        currentUser.getUserId(),
                        savingsCat.getCategoryId(),
                        Transaction.TransactionType.EXPENSE,
                        amount,
                        "Deposit ke: " + goal.getGoalName(),
                        0,
                        goal.getGoalId());

                if (success) {
                    JOptionPane.showMessageDialog(mainFrame, "Berhasil menambah tabungan!");
                    updateDashboard();
                    refreshGoalList();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Gagal! Saldo tidak cukup.");
                }
            }
        }
    }

    private void refreshGoalList() {
        if (mainFrame != null && currentUser != null) {
            List<FinancialGoal> goals = goalService.getActiveGoals(currentUser.getUserId());
            goals.addAll(goalService.getCompletedGoals(currentUser.getUserId()));
            mainFrame.updateGoalList(goals);
        }
    }
}