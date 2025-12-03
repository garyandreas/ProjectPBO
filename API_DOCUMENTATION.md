# 🔌 API DOCUMENTATION - Aplikasi Keuangan Pribadi

## 📋 Daftar Isi
- [User Service API](#user-service-api)
- [Account Service API](#account-service-api)
- [Category Service API](#category-service-api)
- [Transaction Service API](#transaction-service-api)
- [Budget Service API](#budget-service-api)
- [Financial Goal Service API](#financial-goal-service-api)
- [Report Generator API](#report-generator-api)

---

## 🔐 User Service API

Mengelola autentikasi dan profil pengguna.

### `registerUser(String username, String email, String password, String fullName, String currency)`
**Deskripsi**: Mendaftarkan pengguna baru
```java
boolean success = userService.registerUser("johndoe", "john@email.com", "pass123", "John Doe", "IDR");
```
**Return**: `true` jika berhasil, `false` jika username/email sudah terdaftar

**Validasi**:
- Username minimal 3 karakter
- Email harus valid
- Password minimal 5 karakter

---

### `loginUser(String username, String password)`
**Deskripsi**: Melakukan login
```java
User user = userService.loginUser("johndoe", "pass123");
```
**Return**: `User` object jika berhasil, `null` jika gagal

---

### `updateUser(int userId, String fullName, String currency)`
**Deskripsi**: Update profil pengguna
```java
boolean success = userService.updateUser(1, "John Doe Updated", "USD");
```
**Return**: `true` jika berhasil

---

### `getUserById(int userId)`
**Deskripsi**: Get user berdasarkan ID
```java
User user = userService.getUserById(1);
```

---

### `deleteUser(int userId)`
**Deskripsi**: Hapus akun pengguna
```java
boolean success = userService.deleteUser(1);
```

---

## 🏦 Account Service API

Mengelola akun/rekening pengguna.

### `createAccount(int userId, Account.AccountType type, String name)`
**Deskripsi**: Buat akun baru
```java
Account account = accountService.createAccount(1, Account.AccountType.BANK, "BRI Saving");
```
**AccountType**: `BANK`, `E_WALLET`, `CASH`, `INVESTMENT`

---

### `getAccountsByUserId(int userId)`
**Deskripsi**: Dapatkan semua akun milik pengguna
```java
List<Account> accounts = accountService.getAccountsByUserId(1);
```

---

### `addBalance(int accountId, double amount)`
**Deskripsi**: Tambah saldo akun
```java
boolean success = accountService.addBalance(1, 100000);
```

---

### `deductBalance(int accountId, double amount)`
**Deskripsi**: Kurangi saldo akun
```java
boolean success = accountService.deductBalance(1, 50000);
if (!success) {
    // Saldo tidak cukup
}
```

---

### `transferBalance(int fromAccountId, int toAccountId, double amount)`
**Deskripsi**: Transfer antar akun
```java
boolean success = accountService.transferBalance(1, 2, 100000);
```
**Catatan**: Rollback jika saldo tidak cukup atau akun tidak ditemukan

---

### `getTotalBalance(int userId)`
**Deskripsi**: Dapatkan total saldo semua akun
```java
double totalBalance = accountService.getTotalBalance(1);
```

---

## 📂 Category Service API

Mengelola kategori dan subkategori transaksi.

### `createCategory(Category.CategoryType type, String name)`
**Deskripsi**: Buat kategori baru
```java
Category category = categoryService.createCategory(Category.CategoryType.EXPENSE, "Makanan");
```
**CategoryType**: `INCOME`, `EXPENSE`

---

### `createSubCategory(int categoryId, String name)`
**Deskripsi**: Buat subkategori
```java
SubCategory subCat = categoryService.createSubCategory(1, "Restoran");
```

---

### `getCategoriesByType(Category.CategoryType type)`
**Deskripsi**: Dapatkan kategori berdasarkan tipe
```java
List<Category> expenses = categoryService.getCategoriesByType(Category.CategoryType.EXPENSE);
```

---

### `createDefaultCategories()`
**Deskripsi**: Buat kategori default
```java
categoryService.createDefaultCategories();
```
**Default Categories**:
- INCOME: Gaji, Bonus, Bisnis, Investasi
- EXPENSE: Makanan, Transportasi, Hiburan, Utilitas, Kesehatan, Pendidikan

---

## 💳 Transaction Service API

Mengelola transaksi keuangan.

### `createTransaction(int userId, int accountId, Transaction.TransactionType type, int categoryId, double amount, String description)`
**Deskripsi**: Catat transaksi baru
```java
Transaction trans = transactionService.createTransaction(
    1,                                      // userId
    1,                                      // accountId
    Transaction.TransactionType.EXPENSE,    // type
    1,                                      // categoryId
    50000,                                  // amount
    "Makan di Restoran"                    // description
);
```
**TransactionType**: `INCOME`, `EXPENSE`, `TRANSFER`

---

### `getTransactionsByUser(int userId)`
**Deskripsi**: Dapatkan semua transaksi pengguna
```java
List<Transaction> transactions = transactionService.getTransactionsByUser(1);
```

---

### `getTransactionsByAccount(int accountId)`
**Deskripsi**: Dapatkan transaksi per akun
```java
List<Transaction> transactions = transactionService.getTransactionsByAccount(1);
```

---

### `getTransactionsByCategory(int categoryId)`
**Deskripsi**: Dapatkan transaksi per kategori
```java
List<Transaction> transactions = transactionService.getTransactionsByCategory(1);
```

---

### `getTransactionsByMonth(int userId, YearMonth month)`
**Deskripsi**: Dapatkan transaksi berdasarkan bulan
```java
YearMonth month = YearMonth.now();
List<Transaction> transactions = transactionService.getTransactionsByMonth(1, month);
```

---

### `getTotalIncomeByMonth(int userId, YearMonth month)`
**Deskripsi**: Dapatkan total pemasukan bulanan
```java
double income = transactionService.getTotalIncomeByMonth(1, YearMonth.now());
```

---

### `getTotalExpenseByMonth(int userId, YearMonth month)`
**Deskripsi**: Dapatkan total pengeluaran bulanan
```java
double expense = transactionService.getTotalExpenseByMonth(1, YearMonth.now());
```

---

## 💰 Budget Service API

Mengelola budget pengeluaran.

### `createBudget(int userId, int categoryId, double limit, Budget.BudgetPeriod period, int alertThreshold)`
**Deskripsi**: Buat budget
```java
Budget budget = budgetService.createBudget(
    1,                              // userId
    1,                              // categoryId (Makanan)
    2000000,                        // limit per periode
    Budget.BudgetPeriod.MONTHLY,    // period
    80                              // alert di 80%
);
```
**BudgetPeriod**: `DAILY`, `WEEKLY`, `MONTHLY`, `YEARLY`

---

### `updateBudgetSpent(int budgetId, double amount)`
**Deskripsi**: Update pengeluaran budget
```java
budgetService.updateBudgetSpent(1, 50000);
```
**Catatan**: Otomatis trigger alert jika melebihi threshold

---

### `getBudgetsByUser(int userId)`
**Deskripsi**: Dapatkan semua budget pengguna
```java
List<Budget> budgets = budgetService.getBudgetsByUser(1);
```

---

### `getBudgetStatus(int budgetId)`
**Deskripsi**: Dapatkan status budget
```java
String status = budgetService.getBudgetStatus(1);
// Return: "✅ Aman", "⚠️ Hati-hati", "🔴 Mendekati Batas", "❌ Terlampaui"
```

---

## 🎯 Financial Goal Service API

Mengelola target tabungan.

### `createGoal(int userId, String name, double target, LocalDate targetDate, FinancialGoal.GoalPriority priority)`
**Deskripsi**: Buat goal tabungan
```java
FinancialGoal goal = goalService.createGoal(
    1,                                      // userId
    "Liburan Bali",                        // name
    5000000,                                // target amount
    LocalDate.of(2024, 12, 31),            // target date
    FinancialGoal.GoalPriority.HIGH        // priority
);
```
**GoalPriority**: `HIGH`, `MEDIUM`, `LOW`

---

### `addFundsToGoal(int goalId, double amount)`
**Deskripsi**: Tambah dana ke goal
```java
boolean success = goalService.addFundsToGoal(1, 1000000);
```
**Return**: `true` jika goal selesai, `false` jika masih berlanjut

---

### `getActiveGoals(int userId)`
**Deskripsi**: Dapatkan goal yang sedang berjalan
```java
List<FinancialGoal> activeGoals = goalService.getActiveGoals(1);
```

---

### `getCompletedGoals(int userId)`
**Deskripsi**: Dapatkan goal yang sudah selesai
```java
List<FinancialGoal> completedGoals = goalService.getCompletedGoals(1);
```

---

### `getProgressPercentage(int goalId)`
**Deskripsi**: Dapatkan persentase progress
```java
double progress = goalService.getProgressPercentage(1);  // Return: 40.5
```

---

### `getDailyRecommendation(int goalId)`
**Deskripsi**: Dapatkan rekomendasi tabungan harian
```java
double dailyAmount = goalService.getDailyRecommendation(1);  // Return: 50000
```

---

## 📊 Report Generator API

Generate laporan keuangan.

### `generateMonthlyReport(int userId, YearMonth month, UserService userService, TransactionService transService, BudgetService budgetService)`
**Deskripsi**: Generate laporan bulanan
```java
String report = reportGenerator.generateMonthlyReport(
    1,
    YearMonth.now(),
    userService,
    transactionService,
    budgetService
);
System.out.println(report);
```

**Output Format**:
```
===============================================
        LAPORAN KEUANGAN PRIBADI
        User: John Doe (IDR)
        Periode: January 2024
===============================================

RINGKASAN BULANAN
  Total Pemasukan    : Rp 10.000.000
  Total Pengeluaran  : Rp 3.500.000
  Saldo Akhir        : Rp 6.500.000

DETAIL PENGELUARAN PER KATEGORI
  Makanan        : Rp 1.200.000 (34%)
  Transportasi   : Rp 800.000   (23%)
  Hiburan        : Rp 600.000   (17%)
  Lainnya        : Rp 900.000   (26%)

===============================================
```

---

### `generateYearlyReport(int userId, int year, UserService userService, TransactionService transService, BudgetService budgetService)`
**Deskripsi**: Generate laporan tahunan
```java
String report = reportGenerator.generateYearlyReport(
    1,
    2024,
    userService,
    transactionService,
    budgetService
);
```

---

### `formatCurrency(double amount, String currency)`
**Deskripsi**: Format uang
```java
String formatted = reportGenerator.formatCurrency(1500000, "IDR");  
// Return: "Rp 1.500.000"
```

---

## 📝 Contoh Penggunaan Lengkap

```java
// Initialize Services
UserService userService = new UserService();
AccountService accountService = new AccountService();
CategoryService categoryService = new CategoryService();
TransactionService transactionService = new TransactionService();
BudgetService budgetService = new BudgetService();
FinancialGoalService goalService = new FinancialGoalService();

// 1. Registrasi User
boolean regSuccess = userService.registerUser("johndoe", "john@email.com", "pass123", "John Doe", "IDR");

// 2. Login
User user = userService.loginUser("johndoe", "pass123");

// 3. Create Default Categories
categoryService.createDefaultCategories();

// 4. Create Account
Account account = accountService.createAccount(user.getUserId(), Account.AccountType.BANK, "BRI Saving");
accountService.addBalance(account.getAccountId(), 5000000);

// 5. Create Budget
Category foodCategory = categoryService.getCategoriesByType(Category.CategoryType.EXPENSE).get(0);
Budget budget = budgetService.createBudget(
    user.getUserId(),
    foodCategory.getCategoryId(),
    2000000,
    Budget.BudgetPeriod.MONTHLY,
    80
);

// 6. Record Transaction
Transaction trans = transactionService.createTransaction(
    user.getUserId(),
    account.getAccountId(),
    Transaction.TransactionType.EXPENSE,
    foodCategory.getCategoryId(),
    150000,
    "Makan siang"
);

// 7. Create Financial Goal
FinancialGoal goal = goalService.createGoal(
    user.getUserId(),
    "Liburan Bali",
    5000000,
    LocalDate.of(2024, 12, 31),
    FinancialGoal.GoalPriority.HIGH
);

// 8. Add funds to goal
goalService.addFundsToGoal(goal.getGoalId(), 500000);

// 9. Generate Report
ReportGenerator reportGen = new ReportGenerator();
String report = reportGen.generateMonthlyReport(
    user.getUserId(),
    YearMonth.now(),
    userService,
    transactionService,
    budgetService
);
System.out.println(report);
```

---

## 🔗 Integrasi dengan Frontend

### Example - LoginFrame:
```java
public void handleLogin() {
    String username = usernameField.getText();
    String password = passwordField.getText();
    
    User user = applicationController.loginUser(username, password);
    if (user != null) {
        // Success - Open MainFrame
        MainFrame mainFrame = new MainFrame(applicationController, user);
        mainFrame.setVisible(true);
        this.dispose();
    } else {
        // Failed - Show error
        JOptionPane.showMessageDialog(this, "Login gagal!");
    }
}
```

### Example - MainFrame Transaction Tab:
```java
public void handleAddTransaction() {
    Transaction.TransactionType type = (Transaction.TransactionType) typeCombo.getSelectedItem();
    Category category = (Category) categoryCombo.getSelectedItem();
    double amount = Double.parseDouble(amountField.getText());
    String description = descField.getText();
    
    boolean success = applicationController.createTransaction(
        currentUser.getUserId(),
        currentAccount.getAccountId(),
        type,
        category.getCategoryId(),
        amount,
        description
    );
    
    if (success) {
        JOptionPane.showMessageDialog(this, "Transaksi berhasil ditambahkan!");
        refreshTransactionTable();
    }
}
```

---

**Dokumentasi API v1.0 - Aplikasi Keuangan Pribadi**
