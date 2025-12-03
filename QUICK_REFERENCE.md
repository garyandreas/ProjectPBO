# ⚡ QUICK REFERENCE CARD

## 🚀 Quick Start Commands

### Windows
```batch
compile_and_run.bat
```

### Linux/Mac
```bash
bash compile_and_run.sh
```

---

## 📚 Documentation Index

| Document | Purpose | When to Use |
|----------|---------|------------|
| **README.md** | Project overview | First time reading |
| **SETUP_GUIDE.md** | Compilation steps | Getting started |
| **API_DOCUMENTATION.md** | API reference | Implementing features |
| **DEVELOPER_GUIDE.md** | Development tips | Adding new code |
| **PROJECT_STRUCTURE.md** | File organization | Understanding layout |
| **COMPLETION_CHECKLIST.md** | Project status | Verification |
| **FILE_MANIFEST.md** | File listing | File locations |

---

## 🎯 Main Classes

### Models (Data)
```
User              → Username, Email, Password
Account           → Bank, E-wallet, Cash, Investment
Category          → Income, Expense
Transaction       → Record income/expense/transfer
Budget            → Set spending limits
FinancialGoal     → Savings goals
RecurringTransaction → Automated transactions
```

### Services (Logic)
```
UserService       → Authentication & profiles
AccountService    → Account management
CategoryService   → Categories
TransactionService → Transaction recording
BudgetService     → Budget tracking
FinancialGoalService → Goal management
ReportGenerator   → Reports
```

### UI (Interface)
```
LoginFrame        → Login screen
MainFrame         → Main application
RegisterDialog    → Registration
ApplicationController → Bridge UI & Backend
```

---

## 🔑 Key Enums

### Account Types
```java
AccountType.BANK          // Bank account
AccountType.E_WALLET      // Digital wallet
AccountType.CASH          // Cash
AccountType.INVESTMENT    // Investment account
```

### Transaction Types
```java
TransactionType.INCOME    // Income
TransactionType.EXPENSE   // Expense
TransactionType.TRANSFER  // Transfer
```

### Budget Periods
```java
BudgetPeriod.DAILY        // Daily budget
BudgetPeriod.WEEKLY       // Weekly budget
BudgetPeriod.MONTHLY      // Monthly budget
BudgetPeriod.YEARLY       // Yearly budget
```

### Goal Status
```java
GoalStatus.ACTIVE         // Active goal
GoalStatus.COMPLETED      // Completed goal
GoalStatus.ABANDONED      // Abandoned goal
```

---

## 💻 Common Code Patterns

### Register & Login
```java
// Register
userService.registerUser("john", "john@mail.com", "pass123", "John Doe", "IDR");

// Login
User user = userService.loginUser("john", "pass123");
```

### Create Account
```java
Account account = accountService.createAccount(
    user.getUserId(),
    Account.AccountType.BANK,
    "My Bank"
);
```

### Add Balance
```java
accountService.addBalance(account.getAccountId(), 1000000);
```

### Record Transaction
```java
Transaction trans = transactionService.createTransaction(
    user.getUserId(),
    account.getAccountId(),
    Transaction.TransactionType.EXPENSE,
    category.getCategoryId(),
    50000,
    "Lunch"
);
```

### Create Budget
```java
Budget budget = budgetService.createBudget(
    user.getUserId(),
    category.getCategoryId(),
    2000000,
    Budget.BudgetPeriod.MONTHLY,
    80  // alert at 80%
);
```

### Create Goal
```java
FinancialGoal goal = goalService.createGoal(
    user.getUserId(),
    "Vacation",
    5000000,
    LocalDate.of(2024, 12, 31),
    FinancialGoal.GoalPriority.HIGH
);
```

---

## 🧪 Running Tests

### Using JUnit 5
```bash
# Compile tests
javac -cp bin:junit-jupiter-api-5.x.x.jar TestCases.java

# Run tests
java -cp bin:junit-jupiter-api-5.x.x.jar TestCases
```

### Manual Testing
1. Run `compile_and_run.bat` or `compile_and_run.sh`
2. Register new account
3. Create categories
4. Add transactions
5. Set budgets
6. Create goals
7. View reports

---

## 📊 Budget Status Indicators

| Status | Meaning | When |
|--------|---------|------|
| ✅ Aman | Safe | < 80% spent |
| ⚠️ Hati-hati | Warning | 80-100% spent |
| 🔴 Mendekati Batas | Near limit | 90-100% spent |
| ❌ Terlampaui | Exceeded | > 100% spent |

---

## 🔄 Data Flow

```
User Login
    ↓
Dashboard (Show Summary)
    ↓
Choose Feature:
  → Add Transaction → Update Balance → Check Budget
  → Set Budget → Track Spending → Show Alert
  → Create Goal → Add Funds → Show Progress
  → View Report
```

---

## 📁 File Locations

```
Main Class:
  AplikasiKeuanganPribadi.java

Models:
  src/backend/models/User.java
  src/backend/models/Account.java
  src/backend/models/Category.java
  src/backend/models/SubCategory.java
  src/backend/models/Transaction.java
  src/backend/models/Budget.java
  src/backend/models/FinancialGoal.java
  src/backend/models/RecurringTransaction.java

Services:
  src/backend/services/UserService.java
  src/backend/services/AccountService.java
  src/backend/services/CategoryService.java
  src/backend/services/TransactionService.java
  src/backend/services/BudgetService.java
  src/backend/services/FinancialGoalService.java

Utilities:
  src/backend/utils/ReportGenerator.java

UI:
  src/frontend/ui/LoginFrame.java
  src/frontend/ui/MainFrame.java
  src/frontend/ui/RegisterDialog.java

Controller:
  src/frontend/controller/ApplicationController.java
```

---

## 🐛 Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| "Package not found" | Check src/ folder structure |
| "Symbol not found" | Verify import statements |
| "GUI not showing" | Check LoginFrame initialization |
| "Login fails" | Verify UserService registration |
| "Balance negative" | Check deductBalance validation |

---

## 📋 Tab Features

### Dashboard Tab
- View total balance
- View monthly income
- View monthly expense
- View savings progress

### Transaksi Tab
- Select transaction type
- Choose category
- Enter amount
- Add description
- View transaction list

### Budget Tab
- View budget limits
- See spending progress
- Check alert status
- View remaining amount

### Target Tabungan Tab
- View savings goals
- See progress bars
- Check target date
- View daily recommendation

### Laporan Tab
- View monthly report
- See category breakdown
- Check income vs expense
- Export option (future)

---

## 🎨 Default Categories

### Income
- Gaji (Salary)
- Bonus
- Bisnis (Business)
- Investasi (Investment)

### Expense
- Makanan (Food)
- Transportasi (Transportation)
- Hiburan (Entertainment)
- Utilitas (Utilities)
- Kesehatan (Healthcare)
- Pendidikan (Education)

---

## 💾 Data Storage (Current)

**Current**: In-memory (ArrayList, HashMap)

**Future Options**:
- File I/O (Serialization)
- MySQL Database
- PostgreSQL Database
- SQLite
- Cloud Storage

---

## 🔐 Security Features

- ✅ Password hashing
- ✅ Input validation
- ✅ Balance validation
- ✅ Authorization checks
- ✅ Exception handling

---

## 📈 Performance Tips

1. Use ArrayLists for collections
2. Use HashMaps for quick lookups
3. Filter at service layer
4. Cache frequently used data
5. Lazy load when possible

---

## 🎓 Learning Path

1. **Start**: README.md
2. **Setup**: SETUP_GUIDE.md
3. **Structure**: PROJECT_STRUCTURE.md
4. **Code**: DEVELOPER_GUIDE.md
5. **API**: API_DOCUMENTATION.md
6. **Extend**: Add new features

---

## 🚀 Next Steps

### For Students
1. ✅ Compile and run
2. ✅ Test features
3. ✅ Review code
4. ✅ Understand architecture
5. ✅ Practice extending

### For Production
1. 🔲 Add database
2. 🔲 Implement persistence
3. 🔲 Add charts
4. 🔲 Email notifications
5. 🔲 Mobile app
6. 🔲 Web version

---

## 📞 Help & Resources

### Documentation
- README.md → Overview
- API_DOCUMENTATION.md → Methods
- DEVELOPER_GUIDE.md → Best practices

### Code Examples
- See API_DOCUMENTATION.md for usage
- See TestCases.java for test patterns
- See each service for implementation

### Common Questions
- **How to add transaction?** → See API_DOCUMENTATION.md
- **How to create goal?** → See DEVELOPER_GUIDE.md
- **How to compile?** → See SETUP_GUIDE.md

---

**Quick Reference v1.0 - Aplikasi Keuangan Pribadi**
