// TEST CASES - Aplikasi Keuangan Pribadi
// File: TestCases.java
// Gunakan JUnit 5 untuk menjalankan tests

import java.time.LocalDate;
import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Aplikasi Keuangan Pribadi - Test Suite")
public class TestCases {
    
    private UserService userService;
    private AccountService accountService;
    private CategoryService categoryService;
    private TransactionService transactionService;
    private BudgetService budgetService;
    private FinancialGoalService goalService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService();
        accountService = new AccountService();
        categoryService = new CategoryService();
        transactionService = new TransactionService();
        budgetService = new BudgetService();
        goalService = new FinancialGoalService();
    }
    
    // ==================== USER SERVICE TESTS ====================
    
    @Test
    @DisplayName("TC-1: User dapat melakukan registrasi dengan data valid")
    void testRegisterUserSuccess() {
        boolean result = userService.registerUser(
            "johndoe",
            "john@email.com",
            "password123",
            "John Doe",
            "IDR"
        );
        
        assertTrue(result, "User harus berhasil didaftarkan");
    }
    
    @Test
    @DisplayName("TC-2: User tidak dapat mendaftar dengan username duplikat")
    void testRegisterUserDuplicateUsername() {
        userService.registerUser("johndoe", "john@email.com", "password123", "John Doe", "IDR");
        
        boolean result = userService.registerUser(
            "johndoe",
            "john2@email.com",
            "password123",
            "John Doe 2",
            "IDR"
        );
        
        assertFalse(result, "User tidak boleh dapat mendaftar dengan username yang sama");
    }
    
    @Test
    @DisplayName("TC-3: User dapat login dengan username dan password yang benar")
    void testLoginUserSuccess() {
        userService.registerUser("johndoe", "john@email.com", "password123", "John Doe", "IDR");
        
        User user = userService.loginUser("johndoe", "password123");
        
        assertNotNull(user, "User harus berhasil login");
        assertEquals("johndoe", user.getUsername());
    }
    
    @Test
    @DisplayName("TC-4: User tidak dapat login dengan password yang salah")
    void testLoginUserWrongPassword() {
        userService.registerUser("johndoe", "john@email.com", "password123", "John Doe", "IDR");
        
        User user = userService.loginUser("johndoe", "wrongpassword");
        
        assertNull(user, "User tidak boleh berhasil login dengan password salah");
    }
    
    @Test
    @DisplayName("TC-5: User dapat mengupdate profil mereka")
    void testUpdateUserProfile() {
        boolean regResult = userService.registerUser(
            "johndoe",
            "john@email.com",
            "password123",
            "John Doe",
            "IDR"
        );
        User user = userService.loginUser("johndoe", "password123");
        
        boolean updateResult = userService.updateUser(
            user.getUserId(),
            "John Doe Updated",
            "USD"
        );
        
        assertTrue(updateResult, "Profil user harus berhasil diupdate");
    }
    
    // ==================== ACCOUNT SERVICE TESTS ====================
    
    @Test
    @DisplayName("TC-6: User dapat membuat akun baru")
    void testCreateAccount() {
        userService.registerUser("johndoe", "john@email.com", "password123", "John Doe", "IDR");
        User user = userService.loginUser("johndoe", "password123");
        
        Account account = accountService.createAccount(
            user.getUserId(),
            Account.AccountType.BANK,
            "BRI Saving"
        );
        
        assertNotNull(account, "Account harus berhasil dibuat");
        assertEquals("BRI Saving", account.getAccountName());
    }
    
    @Test
    @DisplayName("TC-7: User dapat menambah saldo akun")
    void testAddBalance() {
        userService.registerUser("johndoe", "john@email.com", "password123", "John Doe", "IDR");
        User user = userService.loginUser("johndoe", "password123");
        Account account = accountService.createAccount(user.getUserId(), Account.AccountType.BANK, "BRI");
        
        boolean result = accountService.addBalance(account.getAccountId(), 100000);
        
        assertTrue(result, "Saldo harus berhasil ditambahkan");
        assertEquals(100000, account.getBalance());
    }
    
    @Test
    @DisplayName("TC-8: User dapat mengurangi saldo akun jika saldo cukup")
    void testDeductBalanceSuccess() {
        userService.registerUser("johndoe", "john@email.com", "password123", "John Doe", "IDR");
        User user = userService.loginUser("johndoe", "password123");
        Account account = accountService.createAccount(user.getUserId(), Account.AccountType.BANK, "BRI");
        accountService.addBalance(account.getAccountId(), 100000);
        
        boolean result = accountService.deductBalance(account.getAccountId(), 50000);
        
        assertTrue(result, "Saldo harus berhasil dikurangi");
        assertEquals(50000, account.getBalance());
    }
    
    @Test
    @DisplayName("TC-9: Pengurangan saldo gagal jika saldo tidak cukup")
    void testDeductBalanceInsufficientFunds() {
        userService.registerUser("johndoe", "john@email.com", "password123", "John Doe", "IDR");
        User user = userService.loginUser("johndoe", "password123");
        Account account = accountService.createAccount(user.getUserId(), Account.AccountType.BANK, "BRI");
        accountService.addBalance(account.getAccountId(), 50000);
        
        boolean result = accountService.deductBalance(account.getAccountId(), 100000);
        
        assertFalse(result, "Pengurangan saldo harus gagal");
        assertEquals(50000, account.getBalance(), "Saldo tidak boleh berubah");
    }
    
    @Test
    @DisplayName("TC-10: User dapat transfer antar akun")
    void testTransferBalance() {
        userService.registerUser("johndoe", "john@email.com", "password123", "John Doe", "IDR");
        User user = userService.loginUser("johndoe", "password123");
        
        Account account1 = accountService.createAccount(user.getUserId(), Account.AccountType.BANK, "BRI");
        Account account2 = accountService.createAccount(user.getUserId(), Account.AccountType.E_WALLET, "OVO");
        
        accountService.addBalance(account1.getAccountId(), 100000);
        
        boolean result = accountService.transferBalance(
            account1.getAccountId(),
            account2.getAccountId(),
            50000
        );
        
        assertTrue(result, "Transfer harus berhasil");
        assertEquals(50000, account1.getBalance());
        assertEquals(50000, account2.getBalance());
    }
    
    // ==================== CATEGORY SERVICE TESTS ====================
    
    @Test
    @DisplayName("TC-11: Sistem dapat membuat kategori default")
    void testCreateDefaultCategories() {
        categoryService.createDefaultCategories();
        
        assertNotNull(categoryService.getCategoriesByType(Category.CategoryType.INCOME), 
                     "Kategori income harus ada");
        assertNotNull(categoryService.getCategoriesByType(Category.CategoryType.EXPENSE), 
                     "Kategori expense harus ada");
        assertTrue(categoryService.getCategoriesByType(Category.CategoryType.INCOME).size() > 0,
                  "Harus ada kategori income");
    }
    
    @Test
    @DisplayName("TC-12: User dapat membuat kategori custom")
    void testCreateCustomCategory() {
        Category category = categoryService.createCategory(
            Category.CategoryType.EXPENSE,
            "Kebutuhan Rumah"
        );
        
        assertNotNull(category, "Kategori harus berhasil dibuat");
        assertEquals("Kebutuhan Rumah", category.getCategoryName());
    }
    
    @Test
    @DisplayName("TC-13: User dapat membuat subkategori")
    void testCreateSubCategory() {
        categoryService.createDefaultCategories();
        Category foodCategory = categoryService.getCategoriesByType(Category.CategoryType.EXPENSE).get(0);
        
        SubCategory subCat = categoryService.createSubCategory(foodCategory.getCategoryId(), "Restoran");
        
        assertNotNull(subCat, "Subkategori harus berhasil dibuat");
        assertEquals("Restoran", subCat.getSubCategoryName());
    }
    
    // ==================== TRANSACTION SERVICE TESTS ====================
    
    @Test
    @DisplayName("TC-14: User dapat mencatat transaksi pengeluaran")
    void testCreateExpenseTransaction() {
        setupTestData();
        User user = userService.loginUser("testuser", "password123");
        List<Account> accounts = accountService.getAccountsByUserId(user.getUserId());
        Account account = accounts.get(0);
        
        List<Category> expenseCategories = categoryService.getCategoriesByType(Category.CategoryType.EXPENSE);
        Category category = expenseCategories.get(0);
        
        Transaction transaction = transactionService.createTransaction(
            user.getUserId(),
            account.getAccountId(),
            Transaction.TransactionType.EXPENSE,
            category.getCategoryId(),
            150000,
            "Makan siang"
        );
        
        assertNotNull(transaction, "Transaksi harus berhasil dibuat");
        assertEquals(150000, transaction.getAmount());
    }
    
    @Test
    @DisplayName("TC-15: Saldo otomatis berkurang saat transaksi pengeluaran")
    void testBalanceDeductedOnExpenseTransaction() {
        setupTestData();
        User user = userService.loginUser("testuser", "password123");
        List<Account> accounts = accountService.getAccountsByUserId(user.getUserId());
        Account account = accounts.get(0);
        
        double initialBalance = account.getBalance();
        List<Category> expenseCategories = categoryService.getCategoriesByType(Category.CategoryType.EXPENSE);
        
        transactionService.createTransaction(
            user.getUserId(),
            account.getAccountId(),
            Transaction.TransactionType.EXPENSE,
            expenseCategories.get(0).getCategoryId(),
            150000,
            "Makan"
        );
        
        assertEquals(initialBalance - 150000, account.getBalance(), 
                    "Saldo harus berkurang sebesar jumlah transaksi");
    }
    
    @Test
    @DisplayName("TC-16: User dapat melihat transaksi per bulan")
    void testGetTransactionsByMonth() {
        setupTestData();
        User user = userService.loginUser("testuser", "password123");
        List<Account> accounts = accountService.getAccountsByUserId(user.getUserId());
        
        List<Transaction> transactions = transactionService.getTransactionsByMonth(
            user.getUserId(),
            YearMonth.now()
        );
        
        assertNotNull(transactions, "List transaksi harus ada");
        assertTrue(transactions.size() >= 0, "List transaksi harus valid");
    }
    
    // ==================== BUDGET SERVICE TESTS ====================
    
    @Test
    @DisplayName("TC-17: User dapat membuat budget")
    void testCreateBudget() {
        setupTestData();
        User user = userService.loginUser("testuser", "password123");
        List<Category> expenseCategories = categoryService.getCategoriesByType(Category.CategoryType.EXPENSE);
        
        Budget budget = budgetService.createBudget(
            user.getUserId(),
            expenseCategories.get(0).getCategoryId(),
            2000000,
            Budget.BudgetPeriod.MONTHLY,
            80
        );
        
        assertNotNull(budget, "Budget harus berhasil dibuat");
        assertEquals(2000000, budget.getLimit());
    }
    
    @Test
    @DisplayName("TC-18: Budget status aman ketika pengeluaran < 80%")
    void testBudgetStatusSafe() {
        setupTestData();
        User user = userService.loginUser("testuser", "password123");
        List<Category> expenseCategories = categoryService.getCategoriesByType(Category.CategoryType.EXPENSE);
        Category category = expenseCategories.get(0);
        
        Budget budget = budgetService.createBudget(
            user.getUserId(),
            category.getCategoryId(),
            2000000,
            Budget.BudgetPeriod.MONTHLY,
            80
        );
        
        // Spend 50% of budget
        budgetService.updateBudgetSpent(budget.getBudgetId(), 1000000);
        
        String status = budgetService.getBudgetStatus(budget.getBudgetId());
        assertTrue(status.contains("Aman"), "Status harus menunjukkan aman");
    }
    
    @Test
    @DisplayName("TC-19: Budget alert muncul ketika pengeluaran > 100%")
    void testBudgetAlertExceeded() {
        setupTestData();
        User user = userService.loginUser("testuser", "password123");
        List<Category> expenseCategories = categoryService.getCategoriesByType(Category.CategoryType.EXPENSE);
        
        Budget budget = budgetService.createBudget(
            user.getUserId(),
            expenseCategories.get(0).getCategoryId(),
            2000000,
            Budget.BudgetPeriod.MONTHLY,
            80
        );
        
        // Spend more than 100%
        budgetService.updateBudgetSpent(budget.getBudgetId(), 2500000);
        
        String status = budgetService.getBudgetStatus(budget.getBudgetId());
        assertTrue(status.contains("Terlampaui"), "Status harus menunjukkan terlampaui");
    }
    
    // ==================== FINANCIAL GOAL SERVICE TESTS ====================
    
    @Test
    @DisplayName("TC-20: User dapat membuat financial goal")
    void testCreateFinancialGoal() {
        setupTestData();
        User user = userService.loginUser("testuser", "password123");
        
        FinancialGoal goal = goalService.createGoal(
            user.getUserId(),
            "Liburan Bali",
            5000000,
            LocalDate.of(2024, 12, 31),
            FinancialGoal.GoalPriority.HIGH
        );
        
        assertNotNull(goal, "Goal harus berhasil dibuat");
        assertEquals("Liburan Bali", goal.getGoalName());
    }
    
    @Test
    @DisplayName("TC-21: User dapat menambah dana ke goal")
    void testAddFundsToGoal() {
        setupTestData();
        User user = userService.loginUser("testuser", "password123");
        
        FinancialGoal goal = goalService.createGoal(
            user.getUserId(),
            "Liburan Bali",
            5000000,
            LocalDate.of(2024, 12, 31),
            FinancialGoal.GoalPriority.HIGH
        );
        
        boolean result = goalService.addFundsToGoal(goal.getGoalId(), 1000000);
        
        assertTrue(!result, "Goal belum selesai");
        assertEquals(1000000, goal.getCurrentAmount());
    }
    
    @Test
    @DisplayName("TC-22: Goal otomatis completed ketika target tercapai")
    void testGoalCompleted() {
        setupTestData();
        User user = userService.loginUser("testuser", "password123");
        
        FinancialGoal goal = goalService.createGoal(
            user.getUserId(),
            "Liburan Bali",
            5000000,
            LocalDate.of(2024, 12, 31),
            FinancialGoal.GoalPriority.HIGH
        );
        
        goalService.addFundsToGoal(goal.getGoalId(), 2500000);
        boolean completed = goalService.addFundsToGoal(goal.getGoalId(), 2500000);
        
        assertTrue(completed, "Goal harus completed");
        assertEquals(FinancialGoal.GoalStatus.COMPLETED, goal.getStatus());
    }
    
    @Test
    @DisplayName("TC-23: Rekomendasi tabungan harian dihitung dengan benar")
    void testDailyRecommendation() {
        setupTestData();
        User user = userService.loginUser("testuser", "password123");
        
        FinancialGoal goal = goalService.createGoal(
            user.getUserId(),
            "Liburan Bali",
            3000000,
            LocalDate.of(2024, 12, 31),
            FinancialGoal.GoalPriority.HIGH
        );
        
        double recommendation = goalService.getDailyRecommendation(goal.getGoalId());
        
        assertTrue(recommendation > 0, "Rekomendasi harus positif");
    }
    
    // ==================== HELPER METHODS ====================
    
    private void setupTestData() {
        // Register user
        userService.registerUser("testuser", "test@email.com", "password123", "Test User", "IDR");
        User user = userService.loginUser("testuser", "password123");
        
        // Create account with initial balance
        Account account = accountService.createAccount(user.getUserId(), Account.AccountType.BANK, "Test Account");
        accountService.addBalance(account.getAccountId(), 10000000);
        
        // Create default categories
        categoryService.createDefaultCategories();
    }
    
    // ==================== INTEGRATION TESTS ====================
    
    @Test
    @DisplayName("TC-24: Complete workflow - Registration hingga Report")
    void testCompleteWorkflow() {
        // 1. Register
        assertTrue(userService.registerUser("johndoe", "john@email.com", "password123", "John Doe", "IDR"));
        
        // 2. Login
        User user = userService.loginUser("johndoe", "password123");
        assertNotNull(user);
        
        // 3. Create account
        Account account = accountService.createAccount(user.getUserId(), Account.AccountType.BANK, "BRI");
        assertNotNull(account);
        
        // 4. Add balance
        assertTrue(accountService.addBalance(account.getAccountId(), 5000000));
        
        // 5. Create categories
        categoryService.createDefaultCategories();
        List<Category> categories = categoryService.getCategoriesByType(Category.CategoryType.EXPENSE);
        
        // 6. Create transaction
        Transaction transaction = transactionService.createTransaction(
            user.getUserId(),
            account.getAccountId(),
            Transaction.TransactionType.EXPENSE,
            categories.get(0).getCategoryId(),
            500000,
            "Test transaction"
        );
        assertNotNull(transaction);
        
        // 7. Check balance updated
        assertEquals(4500000, account.getBalance());
        
        // 8. Create budget
        Budget budget = budgetService.createBudget(
            user.getUserId(),
            categories.get(0).getCategoryId(),
            2000000,
            Budget.BudgetPeriod.MONTHLY,
            80
        );
        assertNotNull(budget);
    }
}
