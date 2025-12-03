package frontend.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog untuk registrasi pengguna
 */
public class RegisterDialog extends JDialog {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField fullNameField;
    private JButton registerButton;
    private JButton cancelButton;
    private boolean isRegistered = false;
    
    public RegisterDialog(JFrame parent) {
        super(parent, "Registrasi Pengguna", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(parent);
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Nama Lengkap:"), gbc);
        
        gbc.gridx = 1;
        fullNameField = new JTextField(20);
        mainPanel.add(fullNameField, gbc);
        
        // Username
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        mainPanel.add(usernameField, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        emailField = new JTextField(20);
        mainPanel.add(emailField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);
        
        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel();
        
        registerButton = new JButton("Daftar");
        registerButton.setBackground(new Color(76, 175, 80));
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(e -> register());
        
        cancelButton = new JButton("Batal");
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> cancel());
        
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, gbc);
        
        add(mainPanel);
    }
    
    private void register() {
        if (usernameField.getText().isEmpty() || emailField.getText().isEmpty() ||
            passwordField.getPassword().length == 0 || fullNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }
        
        isRegistered = true;
        dispose();
    }
    
    private void cancel() {
        isRegistered = false;
        dispose();
    }
    
    public boolean isRegistered() {
        return isRegistered;
    }
    
    public String getUsername() {
        return usernameField.getText();
    }
    
    public String getEmail() {
        return emailField.getText();
    }
    
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    public String getFullName() {
        return fullNameField.getText();
    }
}
