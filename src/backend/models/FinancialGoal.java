package backend.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Model untuk FinancialGoal/Target Tabungan
 * Membuat dan memantau tujuan keuangan
 */
public class FinancialGoal implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum GoalStatus {
        ACTIVE, COMPLETED, ABANDONED
    }
    
    private int goalId;
    private int userId;
    private String goalName;
    private String goalDescription;
    private double targetAmount;
    private double currentAmount;
    private LocalDateTime targetDate;
    private GoalStatus status;
    private String priority; // HIGH, MEDIUM, LOW
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructor
    public FinancialGoal() {
    }
    
    public FinancialGoal(int userId, String goalName, double targetAmount, LocalDateTime targetDate) {
        this.userId = userId;
        this.goalName = goalName;
        this.targetAmount = targetAmount;
        this.currentAmount = 0;
        this.targetDate = targetDate;
        this.status = GoalStatus.ACTIVE;
        this.priority = "MEDIUM";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters dan Setters
    public int getGoalId() {
        return goalId;
    }
    
    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getGoalName() {
        return goalName;
    }
    
    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }
    
    public String getGoalDescription() {
        return goalDescription;
    }
    
    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }
    
    public double getTargetAmount() {
        return targetAmount;
    }
    
    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }
    
    public double getCurrentAmount() {
        return currentAmount;
    }
    
    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }
    
    public void addAmount(double amount) {
        this.currentAmount += amount;
        this.updatedAt = LocalDateTime.now();
        if (this.currentAmount >= this.targetAmount) {
            this.status = GoalStatus.COMPLETED;
        }
    }
    
    public LocalDateTime getTargetDate() {
        return targetDate;
    }
    
    public void setTargetDate(LocalDateTime targetDate) {
        this.targetDate = targetDate;
    }
    
    public GoalStatus getStatus() {
        return status;
    }
    
    public void setStatus(GoalStatus status) {
        this.status = status;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public double getRemainingAmount() {
        return Math.max(0, targetAmount - currentAmount);
    }
    
    public double getProgressPercentage() {
        return (currentAmount / targetAmount) * 100;
    }
    
    public boolean isCompleted() {
        return currentAmount >= targetAmount;
    }
    
    @Override
    public String toString() {
        return "FinancialGoal{" +
                "goalId=" + goalId +
                ", goalName='" + goalName + '\'' +
                ", targetAmount=" + targetAmount +
                ", currentAmount=" + currentAmount +
                ", progress=" + String.format("%.2f", getProgressPercentage()) + "%" +
                ", status=" + status +
                '}';
    }
}
