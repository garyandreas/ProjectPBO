package backend.services;

import backend.models.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Service untuk mengelola User
 * Menangani operasi CRUD, autentikasi, dan persistensi data
 */
public class UserService {
    private List<User> users = new ArrayList<>();
    private int nextUserId = 1;
    private static final String DATA_FILE = "users_data.dat";
    
    public UserService() {
        loadUsersFromFile();
    }
    
    /**
     * Load data users dari file
     */
    private void loadUsersFromFile() {
        try {
            if (Files.exists(Paths.get(DATA_FILE))) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE));
                users = (List<User>) ois.readObject();
                ois.close();
                
                // Update nextUserId
                if (!users.isEmpty()) {
                    nextUserId = users.stream()
                            .mapToInt(User::getUserId)
                            .max()
                            .orElse(0) + 1;
                }
                
                System.out.println("Loaded " + users.size() + " users from file.");
            }
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
            users = new ArrayList<>();
        }
    }
    
    /**
     * Save data users ke file
     */
    private void saveUsersToFile() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE));
            oos.writeObject(users);
            oos.close();
            System.out.println("Users saved to file successfully.");
        } catch (Exception e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    
    /**
     * Register pengguna baru
     */
    public boolean registerUser(String username, String email, String password, String fullName) {
        // Validasi username dan email unik
        if (getUserByUsername(username) != null || getUserByEmail(email) != null) {
            System.out.println("Username atau email sudah terdaftar!");
            return false;
        }
        
        User newUser = new User(username, email, hashPassword(password), fullName);
        newUser.setUserId(nextUserId++);
        users.add(newUser);
        saveUsersToFile();  // Save setelah register
        System.out.println("Pengguna " + username + " berhasil terdaftar!");
        return true;
    }
    
    /**
     * Login pengguna
     */
    public User loginUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && verifyPassword(password, user.getPasswordHash())) {
            System.out.println("Login berhasil untuk " + username);
            return user;
        }
        System.out.println("Username atau password salah!");
        return null;
    }
    
    /**
     * Cari pengguna berdasarkan username
     */
    public User getUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Cari pengguna berdasarkan email
     */
    public User getUserByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Cari pengguna berdasarkan ID
     */
    public User getUserById(int userId) {
        return users.stream()
                .filter(u -> u.getUserId() == userId)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Update profil pengguna
     */
    public boolean updateUser(User user) {
        User existingUser = getUserById(user.getUserId());
        if (existingUser != null) {
            existingUser.setFullName(user.getFullName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setCurrency(user.getCurrency());
            existingUser.setUpdatedAt(java.time.LocalDateTime.now());
            return true;
        }
        return false;
    }
    
    /**
     * Hash password (simple implementation)
     */
    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());
    }
    
    /**
     * Verifikasi password
     */
    private boolean verifyPassword(String password, String hash) {
        return hashPassword(password).equals(hash);
    }
    
    /**
     * Dapatkan semua pengguna
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
    
    /**
     * Delete pengguna
     */
    public boolean deleteUser(int userId) {
        boolean deleted = users.removeIf(u -> u.getUserId() == userId);
        if (deleted) {
            saveUsersToFile();  // Save setelah delete
        }
        return deleted;
    }
}
