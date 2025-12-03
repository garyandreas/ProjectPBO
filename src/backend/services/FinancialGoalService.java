package backend.services;

import backend.models.*;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

/**
 * Service untuk mengelola Financial Goal/Target Tabungan
 * Membuat dan memantau tujuan keuangan
 */
public class FinancialGoalService {
    private List<FinancialGoal> goals = new ArrayList<>();
    private int nextGoalId = 1;
    
    private static final String GOALS_DATA_FILE = "goals_data.dat";
    
    public FinancialGoalService() {
        loadGoalsFromFile();
    }
    
    /**
     * Membuat goal baru
     */
    public FinancialGoal createGoal(int userId, String goalName, double targetAmount, 
                                    java.time.LocalDateTime targetDate) {
        FinancialGoal newGoal = new FinancialGoal(userId, goalName, targetAmount, targetDate);
        newGoal.setGoalId(nextGoalId++);
        goals.add(newGoal);
        System.out.println("Goal '" + goalName + "' berhasil dibuat!");
        saveGoalsToFile();
        return newGoal;
    }
    
    /**
     * Dapatkan goal berdasarkan ID
     */
    public FinancialGoal getGoalById(int goalId) {
        return goals.stream()
                .filter(g -> g.getGoalId() == goalId)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Dapatkan semua goal pengguna
     */
    public List<FinancialGoal> getGoalsByUser(int userId) {
        return goals.stream()
                .filter(g -> g.getUserId() == userId)
                .collect(Collectors.toList());
    }
    
    /**
     * Dapatkan active goals
     */
    public List<FinancialGoal> getActiveGoals(int userId) {
        return getGoalsByUser(userId).stream()
                .filter(g -> g.getStatus() == FinancialGoal.GoalStatus.ACTIVE)
                .collect(Collectors.toList());
    }
    
    /**
     * Dapatkan completed goals
     */
    public List<FinancialGoal> getCompletedGoals(int userId) {
        return getGoalsByUser(userId).stream()
                .filter(g -> g.getStatus() == FinancialGoal.GoalStatus.COMPLETED)
                .collect(Collectors.toList());
    }
    
    /**
     * Tambah dana ke goal
     */
    public void addFundsToGoal(int goalId, double amount) {
        FinancialGoal goal = getGoalById(goalId);
        if (goal != null && amount > 0) {
            goal.addAmount(amount);
            System.out.println("Dana ditambah ke goal: " + amount);
            saveGoalsToFile();
            
            if (goal.isCompleted()) {
                System.out.println("🎉 Selamat! Goal '" + goal.getGoalName() + "' telah tercapai!");
            }
        }
    }
    
    /**
     * Update goal
     */
    public boolean updateGoal(FinancialGoal goal) {
        FinancialGoal existingGoal = getGoalById(goal.getGoalId());
        if (existingGoal != null) {
            existingGoal.setGoalName(goal.getGoalName());
            existingGoal.setGoalDescription(goal.getGoalDescription());
            existingGoal.setTargetAmount(goal.getTargetAmount());
            existingGoal.setTargetDate(goal.getTargetDate());
            existingGoal.setPriority(goal.getPriority());
            saveGoalsToFile();
            return true;
        }
        return false;
    }
    
    /**
     * Ubah status goal
     */
    public boolean updateGoalStatus(int goalId, FinancialGoal.GoalStatus status) {
        FinancialGoal goal = getGoalById(goalId);
        if (goal != null) {
            goal.setStatus(status);
            saveGoalsToFile();
            return true;
        }
        return false;
    }
    
    /**
     * Dapatkan sisa hari untuk mencapai goal
     */
    public int getRemainingDays(FinancialGoal goal) {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(
            java.time.LocalDateTime.now(), 
            goal.getTargetDate()
        );
    }
    
    /**
     * Dapatkan rekomendasi tabungan harian
     */
    public double getDailyRecommendation(FinancialGoal goal) {
        int remainingDays = Math.max(1, getRemainingDays(goal));
        return goal.getRemainingAmount() / remainingDays;
    }
    
    /**
     * Delete goal
     */
    public boolean deleteGoal(int goalId) {
        boolean result = goals.removeIf(g -> g.getGoalId() == goalId);
        if (result) {
            saveGoalsToFile();
        }
        return result;
    }
    
    /**
     * Dapatkan semua goal
     */
    public List<FinancialGoal> getAllGoals() {
        return new ArrayList<>(goals);
    }
    
    /**
     * Load goals dari file
     */
    @SuppressWarnings("unchecked")
    private void loadGoalsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GOALS_DATA_FILE))) {
            goals = (List<FinancialGoal>) ois.readObject();
            
            // Rebuild nextGoalId
            if (!goals.isEmpty()) {
                nextGoalId = goals.stream()
                        .mapToInt(FinancialGoal::getGoalId)
                        .max()
                        .orElse(0) + 1;
            }
            System.out.println("Goals loaded from file: " + goals.size() + " goals");
        } catch (FileNotFoundException e) {
            System.out.println("Goals file not found. Starting with empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading goals: " + e.getMessage());
        }
    }
    
    /**
     * Save goals ke file
     */
    private void saveGoalsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(GOALS_DATA_FILE))) {
            oos.writeObject(goals);
            oos.flush();
            System.out.println("Goals saved to file");
        } catch (IOException e) {
            System.err.println("Error saving goals: " + e.getMessage());
        }
    }
    
    /**
     * Reload goals dari file
     */
    public void reloadFromFile() {
        loadGoalsFromFile();
    }
}
