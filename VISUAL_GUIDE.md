# 🎨 VISUAL GUIDE - Aplikasi Keuangan Pribadi

Panduan visual untuk memahami struktur dan alur aplikasi.

---

## 🏗️ Overall Architecture

```
┌─────────────────────────────────────────────────────────┐
│             APLIKASI KEUANGAN PRIBADI                   │
└─────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────┐
│               PRESENTATION LAYER                         │
│  ┌───────────────┬───────────────┬───────────────┐      │
│  │  LoginFrame   │  MainFrame    │ RegisterDialog│      │
│  │               │ (5 Tabs)      │               │      │
│  │ • Username    │ • Dashboard   │ • Full Name   │      │
│  │ • Password    │ • Transaksi   │ • Username    │      │
│  │ • Buttons     │ • Budget      │ • Email       │      │
│  │               │ • Target      │ • Password    │      │
│  │               │ • Laporan     │               │      │
│  └───────────────┴───────────────┴───────────────┘      │
└──────────────────────┬───────────────────────────────────┘
                       │
┌──────────────────────▼───────────────────────────────────┐
│          APPLICATION LAYER (Controller)                  │
│         ApplicationController                            │
│  • Event Handling                                        │
│  • Service Integration                                   │
│  • User Context Management                              │
└──────────────────────┬───────────────────────────────────┘
                       │
┌──────────────────────▼───────────────────────────────────┐
│          BUSINESS LOGIC LAYER (Services)                 │
│  ┌─────────────┬─────────────┬──────────────┐          │
│  │User Service │Account Svc  │Category Svc  │          │
│  ├─────────────┼─────────────┼──────────────┤          │
│  │Transaction  │Budget Svc   │Goal Service  │          │
│  │Service      │             │              │          │
│  └─────────────┴─────────────┴──────────────┘          │
│  • Business Logic                                       │
│  • Validation                                           │
│  • Error Handling                                       │
└──────────────────────┬───────────────────────────────────┘
                       │
┌──────────────────────▼───────────────────────────────────┐
│             DATA LAYER (Models/POJO)                     │
│  ┌──────────┬──────────┬────────────┬──────────┐        │
│  │   User   │ Account  │ Category   │ Budget   │        │
│  ├──────────┼──────────┼────────────┼──────────┤        │
│  │Transact. │ Goal     │SubCategory │Recurring│        │
│  └──────────┴──────────┴────────────┴──────────┘        │
│  • Plain Objects                                        │
│  • Getters/Setters                                      │
│  • Serializable                                         │
└──────────────────────────────────────────────────────────┘
```

---

## 📊 Data Model Relationship

```
                    ┌─────────┐
                    │  USER   │ (userId, username, email, etc)
                    └────┬────┘
                         │
         ┌───────────────┼───────────────┐
         │               │               │
         ▼               ▼               ▼
    ┌─────────┐    ┌────────────┐  ┌──────────────┐
    │ ACCOUNT │    │ CATEGORY   │  │FINANCIAL GOAL│
    │ (1-many)│    │ (1-many)   │  │  (1-many)    │
    └────┬────┘    └─────┬──────┘  └──────────────┘
         │                │
         │                ▼
         │          ┌────────────────┐
         │          │ SUB-CATEGORY   │
         │          │ (1-many)       │
         │          └────────────────┘
         │
         ▼
    ┌──────────────┐
    │ TRANSACTION  │◄──────┐
    │ (many)       │       │
    └──────┬───────┘       │
           │               │
           └──────────┬────┘
                      ▼
                  ┌────────┐
                  │ BUDGET │
                  │(1-many)│
                  └────────┘
```

---

## 🔄 Application Flow

```
START
  │
  ▼
┌──────────────────────┐
│  AplikasiKeuangan    │
│  pribadi.java        │
│  (Main Entry)        │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│  Initialize          │
│  • Create Services   │
│  • Create Controller │
└──────────┬───────────┘
           │
           ▼
┌──────────────────────┐
│  Show LoginFrame     │
└──────────┬───────────┘
           │
     ┌─────┴──────┐
     │            │
     ▼            ▼
┌─────────┐  ┌─────────────┐
│ Login   │  │ Register    │
│Success? │  │Success?     │
└────┬────┘  └─────┬───────┘
     │ YES         │ YES
     │        ┌────┴───────────┐
     │        │ Create Account │
     │        │ Create Default │
     │        │ Categories     │
     │        └────┬───────────┘
     │             │
     ▼             ▼
┌──────────────────────┐
│  Show MainFrame      │
│  5 Tabs:             │
│  • Dashboard         │
│  • Transaksi         │
│  • Budget            │
│  • Target Tabungan   │
│  • Laporan           │
└──────────┬───────────┘
           │
     ┌─────┴─────┬─────────┬──────────┬─────────┐
     │     │     │         │          │         │
     ▼     ▼     ▼         ▼          ▼         ▼
 [DB]  [Trans] [Budget] [Target]  [Report]  [Menu]
     │     │     │         │          │         │
     └─────┴─────┴─────────┴──────────┴─────────┘
           │
           ▼
        LOGOUT
           │
           ▼
        END
```

---

## 📱 User Interface Tabs

```
┌─────────────────────────────────────────────────────────┐
│                    MAIN FRAME                           │
├─────────────────────────────────────────────────────────┤
│ [Dashboard] [Transaksi] [Budget] [Target] [Laporan]    │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  TAB 1: DASHBOARD                                       │
│  ┌─────────────────┬──────────────────┐               │
│  │ Total Saldo     │ Pemasukan Bulan  │               │
│  │ Rp 10.000.000   │ Rp 5.000.000     │               │
│  ├─────────────────┼──────────────────┤               │
│  │ Pengeluaran     │ Total Tabungan   │               │
│  │ Rp 2.000.000    │ Rp 3.000.000     │               │
│  └─────────────────┴──────────────────┘               │
│                                                         │
│  TAB 2: TRANSAKSI                                       │
│  Tipe: [Dropdown: Expense ▼]                           │
│  Kategori: [Dropdown: Makanan ▼]                       │
│  Jumlah: [50000 ]                                       │
│  Deskripsi: [Makan siang]                              │
│  [+ Tambah Transaksi]                                   │
│                                                         │
│  Riwayat Transaksi:                                     │
│  ┌────┬──────────┬─────────┬──────┐                   │
│  │No  │ Kategori │ Jumlah  │ Tgl  │                   │
│  ├────┼──────────┼─────────┼──────┤                   │
│  │1   │ Makanan  │50.000   │Today │                   │
│  │2   │ Transport│100.000  │Today │                   │
│  └────┴──────────┴─────────┴──────┘                   │
│                                                         │
│  TAB 3: BUDGET                                          │
│  ┌───────────┬────────┬────────┬──────┐              │
│  │ Kategori  │ Budget │ Terpakai│Status│              │
│  ├───────────┼────────┼────────┼──────┤              │
│  │ Makanan   │2.000.000│500.000│✅Aman│              │
│  │ Transport │1.000.000│900.000│⚠️Warn│              │
│  └───────────┴────────┴────────┴──────┘              │
│                                                         │
│  TAB 4: TARGET TABUNGAN                                │
│  Goal: Liburan Bali                                     │
│  Target: Rp 5.000.000 | Target Date: 2024-12-31       │
│  Progress: ████████░░░░░░░░░░░░░ 40%                  │
│  Rekomendasi Harian: Rp 50.000                         │
│                                                         │
│  TAB 5: LAPORAN                                         │
│  [Laporan Bulanan]                                      │
│  ===============================================        │
│  Ringkasan Bulanan (January 2024)                      │
│  Total Pemasukan    : Rp 10.000.000                    │
│  Total Pengeluaran  : Rp 3.500.000                     │
│  Saldo Akhir        : Rp 6.500.000                     │
│                                                         │
│  Detail Pengeluaran:                                    │
│  - Makanan     : Rp 1.200.000 (34%)                    │
│  - Transport   : Rp 800.000   (23%)                    │
│  - Hiburan     : Rp 600.000   (17%)                    │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## 💻 Class Hierarchy

```
MODELS (POJO - Plain Old Java Objects)
│
├─ User
│   ├─ userId: int
│   ├─ username: String
│   ├─ email: String
│   ├─ passwordHash: String
│   ├─ fullName: String
│   └─ currency: String
│
├─ Account
│   ├─ accountId: int
│   ├─ userId: int
│   ├─ type: AccountType
│   ├─ accountName: String
│   ├─ balance: double
│   └─ createdAt: LocalDate
│
├─ Category
│   ├─ categoryId: int
│   ├─ type: CategoryType
│   ├─ categoryName: String
│   └─ subCategories: List<SubCategory>
│
├─ SubCategory
│   ├─ subCategoryId: int
│   ├─ categoryId: int
│   └─ subCategoryName: String
│
├─ Transaction
│   ├─ transactionId: int
│   ├─ userId: int
│   ├─ accountId: int
│   ├─ categoryId: int
│   ├─ type: TransactionType
│   ├─ amount: double
│   ├─ date: LocalDate
│   └─ description: String
│
├─ Budget
│   ├─ budgetId: int
│   ├─ userId: int
│   ├─ categoryId: int
│   ├─ limit: double
│   ├─ spent: double
│   ├─ period: BudgetPeriod
│   └─ alertThreshold: int
│
├─ FinancialGoal
│   ├─ goalId: int
│   ├─ userId: int
│   ├─ goalName: String
│   ├─ target: double
│   ├─ currentAmount: double
│   ├─ targetDate: LocalDate
│   ├─ priority: GoalPriority
│   └─ status: GoalStatus
│
└─ RecurringTransaction
    ├─ recurringId: int
    ├─ userId: int
    ├─ accountId: int
    ├─ categoryId: int
    ├─ amount: double
    ├─ pattern: RecurrencePattern
    └─ nextExecutionDate: LocalDate

SERVICES (Business Logic)
│
├─ UserService
│   ├─ registerUser(): boolean
│   ├─ loginUser(): User
│   ├─ updateUser(): boolean
│   ├─ deleteUser(): boolean
│   └─ getUserById(): User
│
├─ AccountService
│   ├─ createAccount(): Account
│   ├─ getAccountsByUserId(): List<Account>
│   ├─ addBalance(): boolean
│   ├─ deductBalance(): boolean
│   ├─ transferBalance(): boolean
│   └─ getTotalBalance(): double
│
├─ CategoryService
│   ├─ createCategory(): Category
│   ├─ createSubCategory(): SubCategory
│   ├─ getCategoriesByType(): List<Category>
│   └─ createDefaultCategories(): void
│
├─ TransactionService
│   ├─ createTransaction(): Transaction
│   ├─ getTransactionsByUser(): List<Transaction>
│   ├─ getTransactionsByAccount(): List<Transaction>
│   ├─ getTransactionsByCategory(): List<Transaction>
│   ├─ getTransactionsByMonth(): List<Transaction>
│   ├─ getTotalIncomeByMonth(): double
│   └─ getTotalExpenseByMonth(): double
│
├─ BudgetService
│   ├─ createBudget(): Budget
│   ├─ updateBudgetSpent(): void
│   ├─ getBudgetsByUser(): List<Budget>
│   ├─ getBudgetStatus(): String
│   └─ sendAlert(): void
│
├─ FinancialGoalService
│   ├─ createGoal(): FinancialGoal
│   ├─ addFundsToGoal(): boolean
│   ├─ getActiveGoals(): List<FinancialGoal>
│   ├─ getCompletedGoals(): List<FinancialGoal>
│   ├─ getProgressPercentage(): double
│   └─ getDailyRecommendation(): double
│
└─ ReportGenerator (Utility)
    ├─ generateMonthlyReport(): String
    ├─ generateYearlyReport(): String
    └─ formatCurrency(): String

UI COMPONENTS
│
├─ LoginFrame (JFrame)
│   ├─ usernameField: JTextField
│   ├─ passwordField: JPasswordField
│   ├─ loginButton: JButton
│   └─ registerButton: JButton
│
├─ MainFrame (JFrame)
│   ├─ tabbedPane: JTabbedPane
│   ├─ dashboardTab: JPanel
│   ├─ transactionTab: JPanel
│   ├─ budgetTab: JPanel
│   ├─ goalTab: JPanel
│   └─ reportTab: JPanel
│
└─ RegisterDialog (JDialog)
    ├─ fullNameField: JTextField
    ├─ usernameField: JTextField
    ├─ emailField: JTextField
    ├─ passwordField: JPasswordField
    ├─ registerButton: JButton
    └─ cancelButton: JButton

CONTROLLER
│
└─ ApplicationController
    ├─ userService: UserService
    ├─ accountService: AccountService
    ├─ categoryService: CategoryService
    ├─ transactionService: TransactionService
    ├─ budgetService: BudgetService
    ├─ goalService: FinancialGoalService
    ├─ currentUser: User
    └─ Wrapper Methods for UI
```

---

## 🔄 Transaction Flow Diagram

```
USER INITIATES EXPENSE TRANSACTION
│
├─ Event: "Add Transaction Button Clicked"
│  │
│  ▼
├─ UI: Validate Input
│  ├─ Check amount > 0
│  ├─ Check category selected
│  └─ Check description not empty
│
├─ Controller: Route to Service
│  │
│  ▼
├─ TransactionService.createTransaction()
│  │
│  ├─ Create Transaction object
│  │
│  ├─ Add to transaction list
│  │
│  ├─ Update Account Balance
│  │  └─ AccountService.deductBalance()
│  │     ├─ Validate balance >= amount
│  │     ├─ Deduct amount
│  │     └─ Return success/failure
│  │
│  ├─ Update Budget Spending
│  │  └─ BudgetService.updateBudgetSpent()
│  │     ├─ Find budget for category
│  │     ├─ Add to spent amount
│  │     └─ Check threshold
│  │
│  └─ Send Alert if needed
│     └─ BudgetService.sendAlert()
│        ├─ Calculate percentage
│        ├─ Compare with threshold
│        └─ Send appropriate alert
│
├─ Return Success to Controller
│  │
│  ▼
├─ Controller: Update UI
│  │
│  ├─ Refresh transaction table
│  ├─ Update balance display
│  ├─ Update budget status
│  └─ Show success message
│
└─ COMPLETE: Transaction Recorded & Balance Updated
```

---

## 📊 Budget Alert System

```
BUDGET TRACKING FLOW

Budget Limit: Rp 2.000.000 (100%)
Alert Threshold: 80%

Spending Status:

0% ─────────────┐
    [Aman]      │
    ✅           │
                 │
50% ────────────┤ [0-50%] = Aman ✅
    [Aman]      │
    ✅           │
                 │
80% ────────────┼─────┐ [50-80%] = Aman ✅
    THRESHOLD   │     │
    Alert!      │     │ [80-90%] = Hati-hati ⚠️
                 ├─────┤
90% ────────────┤     │ [90-100%] = Mendekati Batas 🔴
    Mendekati   │     │
    [Batas]     │     │ [>100%] = Terlampaui ❌
    🔴          │     │
                 ├─────┘
100% ───────────┼─────┐
     Limit      │     │
     Terlampaui!│     │
     ❌         │     │
                 │     │
>100% ──────────┴─────┘
```

---

## 🎯 Goal Progress Tracking

```
FINANCIAL GOAL: Liburan Bali

Target Amount: Rp 5.000.000
Target Date: 2024-12-31
Today: 2024-09-15
Days Remaining: 107

Current Amount: Rp 2.000.000
Progress: 40%

Progress Bar:
████████░░░░░░░░░░░░ 40%

Remaining Amount: Rp 3.000.000

Daily Recommendation:
Rp 3.000.000 ÷ 107 days = Rp 28.037 per day

Status: ACTIVE ✅

Next Milestones:
50% → Rp 2.500.000 (in 25 days)
75% → Rp 3.750.000 (in 65 days)
100% → Rp 5.000.000 (Target Date! 🎉)
```

---

## 🔐 Authentication Flow

```
USER REGISTRATION

┌─────────────────────────┐
│ User enters credentials │
│ • Full Name             │
│ • Username              │
│ • Email                 │
│ • Password              │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Validate Input          │
│ • Not empty             │
│ • Valid email format    │
│ • Username 3+ chars     │
│ • Password 5+ chars     │
└────────────┬────────────┘
             │
       ┌─────┴──────┐
       │            │
    Valid        Invalid
       │            │
       ▼            ▼
  Continue      Show Error
       │         & Return
       ▼
┌─────────────────────────┐
│ Check Username Exists   │
└────────────┬────────────┘
             │
       ┌─────┴──────┐
       │            │
     New         Exists
       │            │
       ▼            ▼
  Continue      Show Error
       │         & Return
       ▼
┌─────────────────────────┐
│ Hash Password           │
│ Using bcrypt/SHA       │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Save User to Database   │
│ (currently in-memory)   │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Create Default Accounts │
│ • Cash Account          │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Create Default Categories
│ • 8+ income/expense cats│
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────┐
│ Registration Complete   │
│ ✅ Success              │
│ Redirect to Login       │
└─────────────────────────┘
```

---

## 📋 Method Call Sequence

```
Application Startup Sequence:

AplikasiKeuanganPribadi.main()
    │
    ├─ new ApplicationController()
    │   ├─ new UserService()
    │   ├─ new AccountService()
    │   ├─ new CategoryService()
    │   ├─ new TransactionService()
    │   ├─ new BudgetService()
    │   └─ new FinancialGoalService()
    │
    ├─ new LoginFrame(controller)
    │   └─ setVisible(true)
    │
    └─ waitForUserAction()
        │
        ├─ IF login clicked
        │   └─ controller.loginUser()
        │       └─ userService.loginUser()
        │           ├─ Find user by username
        │           ├─ Verify password
        │           └─ Return user or null
        │
        └─ IF register clicked
            └─ new RegisterDialog()
                └─ controller.registerUser()
                    ├─ userService.registerUser()
                    ├─ accountService.createDefaultAccount()
                    └─ categoryService.createDefaultCategories()
```

---

**Visual Guide Complete**

Gunakan diagram ini untuk memahami alur dan struktur aplikasi dengan lebih baik!
