# PROJECT STRUCTURE & FILE LIST

## 📁 Complete Project Structure

```
ProjectPBOPraktikum/
│
├── 📄 AplikasiKeuanganPribadi.java                    (Main Entry Point - 20 lines)
│
├── 📄 README.md                                        (Project Documentation)
├── 📄 SETUP_GUIDE.md                                   (Kompilasi & Setup)
├── 📄 API_DOCUMENTATION.md                             (API Reference)
├── 📄 DEVELOPER_GUIDE.md                               (Developer Panduan)
├── 📄 TestCases.java                                   (Unit Tests)
├── 📄 PROJECT_STRUCTURE.md                             (File ini)
│
├── 📄 compile_and_run.bat                              (Windows Compilation Script)
├── 📄 compile_and_run.sh                               (Linux/Mac Compilation Script)
│
├── 📁 src/
│   │
│   ├── 📁 backend/
│   │   │
│   │   ├── 📁 models/                                  (Data Models)
│   │   │   ├── 📄 User.java                            (~80 lines)
│   │   │   ├── 📄 Account.java                         (~85 lines)
│   │   │   ├── 📄 Category.java                        (~70 lines)
│   │   │   ├── 📄 SubCategory.java                     (~60 lines)
│   │   │   ├── 📄 Transaction.java                     (~100 lines)
│   │   │   ├── 📄 Budget.java                          (~95 lines)
│   │   │   ├── 📄 FinancialGoal.java                   (~110 lines)
│   │   │   └── 📄 RecurringTransaction.java            (~95 lines)
│   │   │
│   │   ├── 📁 services/                                (Business Logic)
│   │   │   ├── 📄 UserService.java                     (~150 lines)
│   │   │   ├── 📄 AccountService.java                  (~140 lines)
│   │   │   ├── 📄 CategoryService.java                 (~130 lines)
│   │   │   ├── 📄 TransactionService.java              (~160 lines)
│   │   │   ├── 📄 BudgetService.java                   (~140 lines)
│   │   │   └── 📄 FinancialGoalService.java            (~150 lines)
│   │   │
│   │   └── 📁 utils/                                   (Utilities)
│   │       └── 📄 ReportGenerator.java                 (~180 lines)
│   │
│   └── 📁 frontend/
│       │
│       ├── 📁 ui/                                      (User Interface)
│       │   ├── 📄 LoginFrame.java                      (~150 lines)
│       │   ├── 📄 MainFrame.java                       (~350 lines)
│       │   └── 📄 RegisterDialog.java                  (~160 lines)
│       │
│       └── 📁 controller/                              (Application Controller)
│           └── 📄 ApplicationController.java           (~150 lines)
│
├── 📁 bin/                                             (Compiled Classes - Generated)
│   ├── 📁 backend/
│   │   ├── 📁 models/
│   │   │   ├── Account$AccountType.class
│   │   │   ├── Account.class
│   │   │   ├── Budget$BudgetPeriod.class
│   │   │   ├── Budget.class
│   │   │   ├── Category$CategoryType.class
│   │   │   ├── Category.class
│   │   │   ├── FinancialGoal$GoalPriority.class
│   │   │   ├── FinancialGoal$GoalStatus.class
│   │   │   ├── FinancialGoal.class
│   │   │   ├── RecurringTransaction$RecurrencePattern.class
│   │   │   ├── RecurringTransaction.class
│   │   │   ├── SubCategory.class
│   │   │   ├── Transaction$TransactionType.class
│   │   │   ├── Transaction.class
│   │   │   └── User.class
│   │   │
│   │   ├── 📁 services/
│   │   │   ├── AccountService.class
│   │   │   ├── BudgetService.class
│   │   │   ├── CategoryService.class
│   │   │   ├── FinancialGoalService.class
│   │   │   ├── TransactionService.class
│   │   │   └── UserService.class
│   │   │
│   │   └── 📁 utils/
│   │       └── ReportGenerator.class
│   │
│   ├── 📁 frontend/
│   │   ├── 📁 ui/
│   │   │   ├── LoginFrame.class
│   │   │   ├── MainFrame.class
│   │   │   └── RegisterDialog.class
│   │   │
│   │   └── 📁 controller/
│   │       └── ApplicationController.class
│   │
│   └── AplikasiKeuanganPribadi.class
│
└── 📄 Aplikasi Keuangan Pribadi.pdf                   (Requirements Document)
```

---

## 📊 Statistics

### File Count
- **Total Files**: 30+
- **Java Source Files**: 16
- **Documentation Files**: 5
- **Script Files**: 2
- **Test Files**: 1

### Code Statistics
- **Total Lines of Code (Backend)**: ~1,500 LOC
- **Total Lines of Code (Frontend)**: ~650 LOC
- **Total Lines of Code (Controller)**: ~150 LOC
- **Total Lines of Code (Utils)**: ~180 LOC
- **Total Lines of Documentation**: ~2,000+ lines

### Distribution
```
Models          : 8 files     (~695 lines)
Services        : 6 files     (~820 lines)
Frontend UI     : 3 files     (~660 lines)
Controller      : 1 file      (~150 lines)
Utils           : 1 file      (~180 lines)
Tests           : 1 file      (~300 lines)
Docs            : 5 files     (~2000 lines)
Scripts         : 2 files     (~100 lines)
```

---

## 🔑 Key Classes

### Backend Models (8 classes)
| Class | Lines | Enums | Purpose |
|-------|-------|-------|---------|
| User | ~80 | - | User account & authentication |
| Account | ~85 | AccountType | Wallet/Bank accounts |
| Category | ~70 | CategoryType | Transaction categories |
| SubCategory | ~60 | - | Category detail |
| Transaction | ~100 | TransactionType | Individual transactions |
| Budget | ~95 | BudgetPeriod | Budget management |
| FinancialGoal | ~110 | GoalPriority, GoalStatus | Savings goals |
| RecurringTransaction | ~95 | RecurrencePattern | Automated transactions |

### Backend Services (6 classes)
| Service | Lines | Key Methods |
|---------|-------|-------------|
| UserService | ~150 | register, login, update, delete |
| AccountService | ~140 | create, add/deduct balance, transfer |
| CategoryService | ~130 | create category, create default |
| TransactionService | ~160 | create, get by various filters |
| BudgetService | ~140 | create, update, track spending |
| FinancialGoalService | ~150 | create, add funds, track progress |

### Backend Utils (1 class)
| Class | Lines | Purpose |
|-------|-------|---------|
| ReportGenerator | ~180 | Generate monthly/yearly reports |

### Frontend UI (3 classes)
| Class | Lines | Components |
|-------|-------|------------|
| LoginFrame | ~150 | Login form, register button |
| MainFrame | ~350 | 5 tabs with full UI |
| RegisterDialog | ~160 | Registration modal |

### Frontend Controller (1 class)
| Class | Lines | Purpose |
|-------|-------|---------|
| ApplicationController | ~150 | Bridge UI & backend services |

---

## 🎯 Features per Component

### User Service
- ✅ User registration with validation
- ✅ User authentication
- ✅ Profile management
- ✅ User deletion

### Account Service
- ✅ Create multiple accounts
- ✅ Track account balances
- ✅ Transfer between accounts
- ✅ Add/deduct balance with validation

### Category Service
- ✅ Custom category creation
- ✅ Subcategory management
- ✅ Default categories (8+)
- ✅ Category filtering by type

### Transaction Service
- ✅ Record income/expense/transfer
- ✅ Auto balance updates
- ✅ Filter by user/account/category/month
- ✅ Monthly statistics

### Budget Service
- ✅ Set budget limits
- ✅ Track spending
- ✅ Alert system (80%, 100%+)
- ✅ Budget status tracking

### Financial Goal Service
- ✅ Create savings goals
- ✅ Track progress percentage
- ✅ Daily recommendation
- ✅ Auto-completion detection

### Report Generator
- ✅ Monthly reports
- ✅ Yearly reports
- ✅ Category breakdown
- ✅ Income vs expense analysis

---

## 🔄 Compilation Dependencies

```
AplikasiKeuanganPribadi.java
    └─ depends on
        ├─ ApplicationController.java
        │   └─ depends on
        │       ├─ LoginFrame.java
        │       ├─ MainFrame.java
        │       ├─ RegisterDialog.java
        │       ├─ UserService.java
        │       ├─ AccountService.java
        │       ├─ CategoryService.java
        │       ├─ TransactionService.java
        │       ├─ BudgetService.java
        │       └─ FinancialGoalService.java
        │           └─ all depend on Model classes
        │
        └─ Model classes
            ├─ User.java
            ├─ Account.java
            ├─ Category.java
            ├─ SubCategory.java
            ├─ Transaction.java
            ├─ Budget.java
            ├─ FinancialGoal.java
            └─ RecurringTransaction.java
```

---

## 📝 Package Naming Convention

```
backend.models          → Data classes (POJOs)
backend.services        → Business logic
backend.utils           → Utility functions
frontend.ui             → User interface components
frontend.controller     → Application controller
```

---

## 🚀 Build Output

Setelah kompilasi, struktur folder `bin/` akan berisi:

```
bin/
├── AplikasiKeuanganPribadi.class
├── backend/
│   ├── models/
│   │   ├── Account.class
│   │   ├── Account$AccountType.class
│   │   ├── Budget.class
│   │   ├── Budget$BudgetPeriod.class
│   │   ├── Category.class
│   │   ├── Category$CategoryType.class
│   │   ├── FinancialGoal.class
│   │   ├── FinancialGoal$GoalPriority.class
│   │   ├── FinancialGoal$GoalStatus.class
│   │   ├── RecurringTransaction.class
│   │   ├── RecurringTransaction$RecurrencePattern.class
│   │   ├── SubCategory.class
│   │   ├── Transaction.class
│   │   ├── Transaction$TransactionType.class
│   │   └── User.class
│   ├── services/
│   │   ├── AccountService.class
│   │   ├── BudgetService.class
│   │   ├── CategoryService.class
│   │   ├── FinancialGoalService.class
│   │   ├── TransactionService.class
│   │   └── UserService.class
│   └── utils/
│       └── ReportGenerator.class
└── frontend/
    ├── controller/
    │   └── ApplicationController.class
    └── ui/
        ├── LoginFrame.class
        ├── MainFrame.class
        └── RegisterDialog.class
```

---

## 📚 Documentation Files

| File | Purpose | Lines |
|------|---------|-------|
| README.md | Project overview & features | ~400 |
| SETUP_GUIDE.md | Compilation & setup | ~250 |
| API_DOCUMENTATION.md | Complete API reference | ~700 |
| DEVELOPER_GUIDE.md | Development guidelines | ~450 |
| PROJECT_STRUCTURE.md | This file | ~350 |

---

## 🧪 Testing

**Test File**: `TestCases.java`
- **Total Test Cases**: 24
- **Test Categories**:
  - User Service Tests: 5
  - Account Service Tests: 5
  - Category Service Tests: 3
  - Transaction Service Tests: 3
  - Budget Service Tests: 3
  - Financial Goal Service Tests: 4
  - Integration Tests: 1

---

## 🔧 Scripts

### compile_and_run.bat (Windows)
- Kompilasi semua file Java
- Error checking di setiap step
- Otomatis run aplikasi
- Pause sebelum exit

### compile_and_run.sh (Linux/Mac)
- Sama seperti batch file
- Format untuk Unix shell
- Chmod +x compile_and_run.sh sebelum run

---

## 💾 Data Storage

**Current Implementation**: In-memory (ArrayList, HashMap)

**Future Improvements**:
- [ ] File I/O (Serialization)
- [ ] Database (MySQL, PostgreSQL)
- [ ] Cloud Storage
- [ ] Export to Excel/PDF

---

## 🎓 Educational Value

### OOP Concepts Demonstrated:
- ✅ Encapsulation (private fields, public methods)
- ✅ Inheritance (Enum types, potential abstract classes)
- ✅ Polymorphism (Method overloading in services)
- ✅ Abstraction (Service layer hiding complexity)

### Design Patterns:
- ✅ MVC Pattern
- ✅ Service Layer Pattern
- ✅ DAO Pattern (implicit)

### Java Features:
- ✅ Collections (ArrayList, HashMap)
- ✅ Enums (for types/status)
- ✅ LocalDate/YearMonth (Date handling)
- ✅ Streams/Lambda (potential future)
- ✅ Swing GUI
- ✅ Exception Handling

---

**Dokumentasi Lengkap - Aplikasi Keuangan Pribadi v1.0**
