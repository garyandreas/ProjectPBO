package backend.services;

import backend.models.Budget;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BudgetService {
    private static final String DATA_FILE = "budgets.dat";
    private List<Budget> budgets;

    public BudgetService() {
        this.budgets = new ArrayList<>();
        reloadFromFile();
    }

    @SuppressWarnings("unchecked")
    public void reloadFromFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    budgets = (List<Budget>) obj;
                } else {
                    budgets = new ArrayList<>();
                }
            } catch (Exception e) {
                budgets = new ArrayList<>();
            }
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(budgets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Budget createBudget(int userId, int categoryId, double limit, Budget.BudgetPeriod period) {
        Optional<Budget> existing = budgets.stream()
            .filter(b -> b.getUserId() == userId && b.getCategoryId() == categoryId)
            .findFirst();
            
        if (existing.isPresent()) {
            Budget b = existing.get();
            b.setBudgetLimit(limit);
            b.setPeriod(period);
            saveToFile();
            return b;
        }

        Budget newBudget = new Budget(userId, categoryId, limit, period);
        budgets.add(newBudget);
        saveToFile();
        return newBudget;
    }
    
    public void updateBudget(Budget budget) {
        for (int i = 0; i < budgets.size(); i++) {
            if (budgets.get(i).getBudgetId().equals(budget.getBudgetId())) {
                budgets.set(i, budget);
                saveToFile();
                return;
            }
        }
    }
    
    // --- FITUR BARU: HAPUS BUDGET ---
    public boolean deleteBudget(String budgetId) {
        boolean removed = budgets.removeIf(b -> b.getBudgetId().equals(budgetId));
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    public List<Budget> getBudgetsByUser(int userId) {
        return budgets.stream()
                .filter(b -> b.getUserId() == userId)
                .collect(Collectors.toList());
    }
    
    public Budget getBudgetByCategory(int userId, int categoryId) {
        return budgets.stream()
                .filter(b -> b.getUserId() == userId && b.getCategoryId() == categoryId)
                .findFirst()
                .orElse(null);
    }
}