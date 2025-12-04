package backend.services;

import backend.models.User;
import java.io.*;
import java.util.*;

public class UserService {
    private static final String DATA_FILE = "users_data.dat";
    private List<User> users;

    public UserService() {
        this.users = new ArrayList<>();
        reloadFromFile();
    }

    @SuppressWarnings("unchecked")
    public void reloadFromFile() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (List<User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                users = new ArrayList<>();
            }
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String email, String password, String fullName) {
        // Cek duplikasi
        if (users.stream().anyMatch(u -> u.getUsername().equals(username) || u.getEmail().equals(email))) {
            return false;
        }

        User newUser = new User(username, email, password, fullName);
        
        // --- LOGIKA ID BARU (PENTING) ---
        // Cari ID paling besar yang ada, lalu tambah 1.
        int maxId = users.stream().mapToInt(User::getUserId).max().orElse(0);
        newUser.setUserId(maxId + 1);
        // --------------------------------
        
        users.add(newUser);
        saveToFile();
        return true;
    }

    public User loginUser(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPasswordHash().equals(password))
                .findFirst()
                .orElse(null);
    }
    
    public User getUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == updatedUser.getUserId()) {
                users.set(i, updatedUser);
                saveToFile();
                return;
            }
        }
    }
}