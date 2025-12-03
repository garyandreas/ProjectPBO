# 👨‍💻 DEVELOPER GUIDE - Aplikasi Keuangan Pribadi

## 📖 Panduan untuk Pengembang

Dokumentasi ini membantu Anda memahami arsitektur, konvensi, dan best practices dalam pengembangan Aplikasi Keuangan Pribadi.

---

## 📐 Arsitektur Sistem

### Tiga Layer Architecture:

```
┌─────────────────────────────────────────────┐
│  PRESENTATION LAYER (Frontend)              │
│  - Swing GUI Components                     │
│  - User Interfaces                          │
│  - Event Handlers                           │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│  APPLICATION LAYER (Controller)             │
│  - ApplicationController                    │
│  - Business Logic Orchestration             │
│  - Event Routing                            │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│  BUSINESS LOGIC LAYER (Services)            │
│  - UserService                              │
│  - AccountService                           │
│  - CategoryService                          │
│  - TransactionService                       │
│  - BudgetService                            │
│  - FinancialGoalService                     │
│  - ReportGenerator                          │
└─────────────────┬───────────────────────────┘
                  │
┌─────────────────▼───────────────────────────┐
│  DATA LAYER (Models)                        │
│  - User, Account, Category, SubCategory     │
│  - Transaction, Budget, FinancialGoal       │
│  - RecurringTransaction                     │
└─────────────────────────────────────────────┘
```

---

## 🎯 Design Patterns Digunakan

### 1. Model-View-Controller (MVC)
**Tujuan**: Memisahkan concerns antara data, presentation, dan logic.

```
Model (Data Layer)
├─ User.java
├─ Account.java
├─ Category.java
└─ Transaction.java

View (Presentation Layer)
├─ LoginFrame.java
├─ MainFrame.java
└─ RegisterDialog.java

Controller (Application Layer)
└─ ApplicationController.java
```

### 2. Service Pattern
**Tujuan**: Enkapsulasi business logic dalam service classes yang stateless.

```
public class UserService {
    private List<User> users = new ArrayList<>();
    
    // Business logic methods
    public boolean registerUser(...) { }
    public User loginUser(...) { }
    public boolean updateUser(...) { }
}
```

### 3. Singleton Pattern (Implicit)
**Tujuan**: Memastikan hanya satu instance service untuk seluruh aplikasi.

```java
// Di ApplicationController
UserService userService = new UserService();  // Created once
```

---

## 📦 Package Structure

```
src/
├── backend/
│   ├── models/              # Data models (POJOs)
│   │   ├── User.java
│   │   ├── Account.java
│   │   ├── Category.java
│   │   ├── SubCategory.java
│   │   ├── Transaction.java
│   │   ├── Budget.java
│   │   ├── FinancialGoal.java
│   │   └── RecurringTransaction.java
│   │
│   ├── services/            # Business logic layer
│   │   ├── UserService.java
│   │   ├── AccountService.java
│   │   ├── CategoryService.java
│   │   ├── TransactionService.java
│   │   ├── BudgetService.java
│   │   └── FinancialGoalService.java
│   │
│   └── utils/               # Utility functions
│       └── ReportGenerator.java
│
└── frontend/
    ├── ui/                  # GUI components
    │   ├── MainFrame.java
    │   ├── LoginFrame.java
    │   └── RegisterDialog.java
    │
    └── controller/          # Application controller
        └── ApplicationController.java

AplikasiKeuanganPribadi.java  # Entry point
```

---

## 🔍 Code Conventions

### 1. Naming Conventions

**Classes**: PascalCase
```java
public class UserService { }
public class TransactionDialog { }
```

**Methods**: camelCase
```java
public boolean registerUser() { }
public void updateUserProfile() { }
```

**Fields**: camelCase (private)
```java
private int userId;
private double balance;
private List<Account> accounts;
```

**Constants**: UPPER_SNAKE_CASE
```java
public static final int MAX_USERNAME_LENGTH = 50;
public static final double DEFAULT_ALERT_THRESHOLD = 0.80;
```

### 2. Access Modifiers

```java
public class Account {
    // Private fields (encapsulation)
    private int accountId;
    private double balance;
    
    // Public getters
    public int getAccountId() {
        return accountId;
    }
    
    // Public methods
    public void addBalance(double amount) {
        this.balance += amount;
    }
}
```

### 3. Documentation

**Javadoc untuk Classes**:
```java
/**
 * Manages user account and balance operations.
 * 
 * This service handles CRUD operations for accounts
 * and provides methods for balance management.
 */
public class AccountService {
```

**Javadoc untuk Methods**:
```java
/**
 * Creates a new account for the specified user.
 *
 * @param userId the user ID
 * @param type the account type (BANK, E_WALLET, CASH, INVESTMENT)
 * @param name the account name
 * @return the created Account object, or null if user not found
 */
public Account createAccount(int userId, AccountType type, String name) {
```

---

## 🔧 Menambah Fitur Baru

### Langkah 1: Buat Model Class (jika perlu)

```java
// src/backend/models/NewEntity.java
public class NewEntity {
    private int id;
    private String name;
    
    public NewEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

### Langkah 2: Buat Service Class

```java
// src/backend/services/NewEntityService.java
import java.util.*;

public class NewEntityService {
    private List<NewEntity> entities = new ArrayList<>();
    private int nextId = 1;
    
    public NewEntity createEntity(String name) {
        NewEntity entity = new NewEntity(nextId++, name);
        entities.add(entity);
        return entity;
    }
    
    public List<NewEntity> getAllEntities() {
        return new ArrayList<>(entities);
    }
    
    public NewEntity getEntityById(int id) {
        return entities.stream()
            .filter(e -> e.getId() == id)
            .findFirst()
            .orElse(null);
    }
}
```

### Langkah 3: Integrasikan ke ApplicationController

```java
// src/frontend/controller/ApplicationController.java
public class ApplicationController {
    // ... existing services
    private NewEntityService newEntityService = new NewEntityService();
    
    public NewEntity createNewEntity(String name) {
        return newEntityService.createEntity(name);
    }
    
    public List<NewEntity> getNewEntities() {
        return newEntityService.getAllEntities();
    }
}
```

### Langkah 4: Buat UI Component

```java
// src/frontend/ui/NewEntityDialog.java
public class NewEntityDialog extends JDialog {
    private JTextField nameField;
    private ApplicationController controller;
    
    public NewEntityDialog(JFrame parent, ApplicationController controller) {
        super(parent, "Tambah Entity", true);
        this.controller = controller;
        initComponents();
    }
    
    private void initComponents() {
        nameField = new JTextField(20);
        JButton saveBtn = new JButton("Simpan");
        
        saveBtn.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                controller.createNewEntity(name);
                JOptionPane.showMessageDialog(this, "Entity berhasil ditambahkan!");
                this.dispose();
            }
        });
        
        // Layout
        setLayout(new FlowLayout());
        add(new JLabel("Nama:"));
        add(nameField);
        add(saveBtn);
        pack();
        setLocationRelativeTo(null);
    }
}
```

### Langkah 5: Integrasikan ke MainFrame

```java
// Di MainFrame.java
JButton newEntityBtn = new JButton("Tambah Entity");
newEntityBtn.addActionListener(e -> {
    NewEntityDialog dialog = new NewEntityDialog(this, controller);
    dialog.setVisible(true);
});
```

---

## 🧪 Testing Best Practices

### Unit Test Example:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    
    private UserService userService = new UserService();
    
    @Test
    public void testRegisterUserSuccess() {
        boolean result = userService.registerUser(
            "testuser",
            "test@email.com",
            "pass123",
            "Test User",
            "IDR"
        );
        assertTrue(result);
    }
    
    @Test
    public void testRegisterUserDuplicate() {
        userService.registerUser("testuser", "test@email.com", "pass123", "Test", "IDR");
        boolean result = userService.registerUser("testuser", "test2@email.com", "pass123", "Test", "IDR");
        assertFalse(result);
    }
    
    @Test
    public void testLoginUserSuccess() {
        userService.registerUser("testuser", "test@email.com", "pass123", "Test", "IDR");
        User user = userService.loginUser("testuser", "pass123");
        assertNotNull(user);
    }
}
```

---

## ⚠️ Error Handling

### Service Level Exception Handling:

```java
public class TransactionService {
    public Transaction createTransaction(
        int userId, int accountId, TransactionType type,
        int categoryId, double amount, String description) {
        
        try {
            // Validate inputs
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }
            
            // Business logic
            Account account = accountService.getAccountById(accountId);
            if (account == null) {
                throw new RuntimeException("Account not found");
            }
            
            // Process transaction
            Transaction transaction = new Transaction(...);
            
            // Update account balance
            if (type == TransactionType.EXPENSE) {
                if (!accountService.deductBalance(accountId, amount)) {
                    throw new RuntimeException("Insufficient balance");
                }
            }
            
            return transaction;
            
        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
            return null;
        } catch (RuntimeException e) {
            System.err.println("Transaction Error: " + e.getMessage());
            return null;
        }
    }
}
```

### UI Level Exception Handling:

```java
// Di MainFrame atau Dialog
private void handleAction() {
    try {
        String input = inputField.getText();
        if (input.isEmpty()) {
            showError("Input tidak boleh kosong");
            return;
        }
        
        double value = Double.parseDouble(input);
        if (value <= 0) {
            showError("Nilai harus positif");
            return;
        }
        
        // Process
        boolean success = controller.doSomething(value);
        if (success) {
            showSuccess("Berhasil!");
        } else {
            showError("Gagal memproses");
        }
        
    } catch (NumberFormatException e) {
        showError("Format angka tidak valid");
    } catch (Exception e) {
        showError("Error: " + e.getMessage());
    }
}

private void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
}

private void showSuccess(String message) {
    JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
}
```

---

## 🔄 Data Flow Example

### User Registration Flow:

```
1. User mengisi form di RegisterDialog
       ↓
2. User klik "Daftar"
       ↓
3. Validasi input di UI (tidak kosong, format valid)
       ↓
4. Panggil controller.registerUser()
       ↓
5. Controller panggil userService.registerUser()
       ↓
6. Service validasi (username unik, password cukup kuat)
       ↓
7. Service hash password
       ↓
8. Service simpan User ke dalam memory
       ↓
9. Service return success/failure
       ↓
10. Controller return hasil ke UI
       ↓
11. UI show success/error message
       ↓
12. Jika sukses, tutup dialog dan show LoginFrame
```

### Transaction Recording Flow:

```
1. User input transaksi di MainFrame tab Transaksi
       ↓
2. User klik "Tambah Transaksi"
       ↓
3. Validasi input (amount > 0, category selected, etc)
       ↓
4. Panggil controller.createTransaction()
       ↓
5. Controller panggil transactionService.createTransaction()
       ↓
6. Service buat Transaction object
       ↓
7. Service simpan transaction
       ↓
8. Service update account balance
       ↓
9. Service update budget spent
       ↓
10. Service check budget status dan send alert jika perlu
       ↓
11. Return success
       ↓
12. Controller return hasil ke UI
       ↓
13. UI refresh transaction table
       ↓
14. UI update dashboard cards
```

---

## 🚀 Performance Tips

### 1. Use Collections Efficiently

```java
// ❌ Bad - O(n²) complexity
List<Transaction> filtered = new ArrayList<>();
for (Transaction t : allTransactions) {
    if (t.getCategoryId() == categoryId) {
        filtered.add(t);
    }
}

// ✅ Good - O(n) with streams
List<Transaction> filtered = allTransactions.stream()
    .filter(t -> t.getCategoryId() == categoryId)
    .collect(Collectors.toList());
```

### 2. Cache Frequent Queries

```java
public class TransactionService {
    private Map<Integer, List<Transaction>> transactionsByUser = new HashMap<>();
    
    public List<Transaction> getTransactionsByUser(int userId) {
        // Check cache first
        if (transactionsByUser.containsKey(userId)) {
            return transactionsByUser.get(userId);
        }
        
        // Query and cache
        List<Transaction> transactions = queries.getTransactions(userId);
        transactionsByUser.put(userId, transactions);
        return transactions;
    }
}
```

### 3. Lazy Loading

```java
public class Account {
    private List<Transaction> transactions;  // Lazy loaded
    
    public List<Transaction> getTransactions() {
        if (transactions == null) {
            // Load only when needed
            transactions = transactionService.getByAccount(this.accountId);
        }
        return transactions;
    }
}
```

---

## 📚 Additional Resources

- [Oracle Java Documentation](https://docs.oracle.com/javase/)
- [Java Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Design Patterns in Java](https://refactoring.guru/design-patterns)

---

**Happy Coding! 🎉**
