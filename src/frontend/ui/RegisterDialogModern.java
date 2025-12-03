package frontend.ui;

import frontend.theme.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Modern Register Dialog - Consistent dengan LoginFrameModern
 */
public class RegisterDialogModern extends JDialog {
    private ModernTextField usernameField;
    private ModernTextField emailField;
    private ModernTextField fullNameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private ModernTextField pinField;
    private JComboBox<String> currencyCombo;
    private JComboBox<String> languageCombo;
    private ModernButton registerButton;
    private ModernButton cancelButton;
    private JLabel statusLabel;
    private JPanel formPanel;
    private int currentStep = 1; // Step 1: Account, Step 2: Setup
    
    public RegisterDialogModern(JFrame parent) {
        super(parent, "Daftar Akun Baru", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(parent);
        setResizable(false);
        setBackground(ModernTheme.BACKGROUND);
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(ModernTheme.BACKGROUND);
        mainPanel.setLayout(new BorderLayout());
        
        // Header dengan SUCCESS (hijau) untuk variasi
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(ModernTheme.SUCCESS);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.setColor(new Color(100, 200, 90));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(0, getHeight(), getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(450, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                                             ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        
        JLabel titleLabel = new JLabel("Buat Akun Baru");
        titleLabel.setFont(ModernTheme.FONT_TITLE);
        titleLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(ModernTheme.BACKGROUND);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_XL, ModernTheme.SPACING_LG,
                                              ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        
        // Form wrapper
        formPanel = new JPanel();
        formPanel.setBackground(ModernTheme.BACKGROUND);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        usernameLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        usernameField = new ModernTextField();
        usernameField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        usernameField.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
        
        // Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        emailLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(emailLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        emailField = new ModernTextField();
        emailField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        emailField.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(emailField);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
        
        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        passwordLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        passwordField = createModernPasswordField();
        passwordField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        passwordField.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
        
        // Confirm Password
        JLabel confirmLabel = new JLabel("Konfirmasi Password");
        confirmLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        confirmLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        confirmLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(confirmLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        confirmPasswordField = createModernPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        confirmPasswordField.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        confirmPasswordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(confirmPasswordField);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ModernTheme.BACKGROUND);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(300, ModernTheme.BUTTON_HEIGHT + 10));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        registerButton = new ModernButton("Lanjut", ModernTheme.SUCCESS, new Color(100, 200, 90));
        cancelButton = new ModernButton("Batal", ModernTheme.DANGER, new Color(220, 100, 90));
        
        registerButton.setPreferredSize(new Dimension(140, ModernTheme.BUTTON_HEIGHT));
        cancelButton.setPreferredSize(new Dimension(140, ModernTheme.BUTTON_HEIGHT));
        
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalStrut(ModernTheme.SPACING_MD));
        buttonPanel.add(cancelButton);
        
        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
        
        // Status
        statusLabel = new JLabel();
        statusLabel.setFont(ModernTheme.FONT_CAPTION);
        statusLabel.setForeground(ModernTheme.TEXT_HINT);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(statusLabel);
        
        contentPanel.add(formPanel, BorderLayout.WEST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    /**
     * Show setup step untuk PIN, Currency, dan Language
     */
    public void showSetupStep() {
        currentStep = 2;
        
        // Bersihkan form panel dan isi dengan setup fields
        formPanel.removeAll();
        
        // PIN
        JLabel pinLabel = new JLabel("Buat PIN (6 Digit)");
        pinLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        pinLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        pinLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(pinLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        pinField = new ModernTextField();
        pinField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        pinField.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        pinField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(pinField);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
        
        // Currency
        JLabel currencyLabel = new JLabel("Pilih Mata Uang");
        currencyLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        currencyLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        currencyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(currencyLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        String[] currencies = {"IDR (Rupiah)", "USD (Dollar)", "EUR (Euro)", "SGD (Singapore Dollar)"};
        currencyCombo = new JComboBox<>(currencies);
        currencyCombo.setFont(ModernTheme.FONT_BODY);
        currencyCombo.setBackground(ModernTheme.SURFACE);
        currencyCombo.setForeground(ModernTheme.TEXT_PRIMARY);
        currencyCombo.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        currencyCombo.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        currencyCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(currencyCombo);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
        
        // Language
        JLabel languageLabel = new JLabel("Pilih Bahasa");
        languageLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        languageLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        languageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(languageLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        String[] languages = {"Bahasa Indonesia (id)", "English (en)", "中文 (zh)", "Español (es)"};
        languageCombo = new JComboBox<>(languages);
        languageCombo.setFont(ModernTheme.FONT_BODY);
        languageCombo.setBackground(ModernTheme.SURFACE);
        languageCombo.setForeground(ModernTheme.TEXT_PRIMARY);
        languageCombo.setSelectedIndex(0); // Default Bahasa Indonesia
        languageCombo.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        languageCombo.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        languageCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(languageCombo);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_XL));
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ModernTheme.BACKGROUND);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(300, ModernTheme.BUTTON_HEIGHT + 10));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        ModernButton nextButton = new ModernButton("Selesai", ModernTheme.SUCCESS, new Color(100, 200, 90));
        ModernButton backButton = new ModernButton("Kembali", ModernTheme.PRIMARY, new Color(59, 89, 152));
        
        nextButton.setPreferredSize(new Dimension(140, ModernTheme.BUTTON_HEIGHT));
        backButton.setPreferredSize(new Dimension(140, ModernTheme.BUTTON_HEIGHT));
        
        backButton.addActionListener(e -> goBackToStep1());
        nextButton.addActionListener(e -> registerButton.doClick());
        registerButton = nextButton;
        
        buttonPanel.add(nextButton);
        buttonPanel.add(Box.createHorizontalStrut(ModernTheme.SPACING_MD));
        buttonPanel.add(backButton);
        
        formPanel.add(buttonPanel);
        
        formPanel.revalidate();
        formPanel.repaint();
    }
    
    /**
     * Go back to step 1
     */
    public void goBackToStep1() {
        currentStep = 1;
        getContentPane().removeAll();
        initializeComponents();
        revalidate();
        repaint();
    }
    
    private JPasswordField createModernPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(ModernTheme.FONT_BODY);
        field.setBackground(ModernTheme.SURFACE);
        field.setForeground(ModernTheme.TEXT_PRIMARY);
        field.setCaretColor(ModernTheme.TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ModernTheme.BORDER, 1),
            BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                                           ModernTheme.SPACING_SM, ModernTheme.SPACING_MD)
        ));
        
        // Add focus listener untuk highlight border biru
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ModernTheme.PRIMARY, 2),
                    BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                                                   ModernTheme.SPACING_SM, ModernTheme.SPACING_MD)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ModernTheme.BORDER, 1),
                    BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                                                   ModernTheme.SPACING_SM, ModernTheme.SPACING_MD)
                ));
            }
        });
        
        return field;
    }
    
    public String getUsername() {
        return usernameField.getText();
    }
    
    public String getEmail() {
        return emailField != null ? emailField.getText() : "";
    }
    
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }
    
    public String getPin() {
        return pinField != null ? pinField.getText() : "";
    }
    
    public String getCurrency() {
        if (currencyCombo != null) {
            String selected = (String) currencyCombo.getSelectedItem();
            // Extract currency code (IDR, USD, EUR, SGD)
            return selected.split(" ")[0];
        }
        return "IDR";
    }
    
    public String getLanguage() {
        if (languageCombo != null) {
            String selected = (String) languageCombo.getSelectedItem();
            // Extract language code in parentheses
            String code = selected.substring(selected.lastIndexOf("(") + 1, selected.lastIndexOf(")"));
            return code;
        }
        return "id";
    }
    
    public int getCurrentStep() {
        return currentStep;
    }
    
    public void addRegisterListener(java.awt.event.ActionListener listener) {
        registerButton.addActionListener(listener);
    }
    
    public void addCancelListener(java.awt.event.ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
    
    public void setStatus(String status) {
        statusLabel.setText(status);
    }
}
