package frontend.ui;

import frontend.theme.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegisterDialogModern extends JDialog {
    // Component Fields
    private ModernTextField fullNameField; // BARU: Field Nama Lengkap
    private ModernTextField usernameField;
    private ModernTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    
    // Step 2 Fields
    private ModernTextField pinField;
    private ModernTextField initialBalanceField;
    private JComboBox<String> currencyCombo;
    private JComboBox<String> mainGoalCombo;
    private JComboBox<String> languageCombo;
    
    private ModernButton registerButton; // Tombol internal untuk trigger event
    private ModernButton cancelButton;
    private JLabel statusLabel;
    private JPanel formPanel;
    private int currentStep = 1; 
    
    public RegisterDialogModern(JFrame parent) {
        super(parent, "Daftar Akun Baru", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(450, 800); // Sedikit diperpanjang untuk muat field baru
        setLocationRelativeTo(parent);
        setResizable(false);
        setBackground(ModernTheme.BACKGROUND);
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(ModernTheme.BACKGROUND);
        mainPanel.setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(ModernTheme.SUCCESS); // Hijau
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(new Color(100, 200, 90));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(0, getHeight(), getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(450, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        JLabel titleLabel = new JLabel("Buat Akun Baru");
        titleLabel.setFont(ModernTheme.FONT_TITLE);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(ModernTheme.BACKGROUND);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_XL, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        
        formPanel = new JPanel();
        formPanel.setBackground(ModernTheme.BACKGROUND);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // --- STEP 1 FORM ---
        
        // 1. Nama Lengkap (BARU)
        addLabelAndField("Nama Lengkap", fullNameField = new ModernTextField());
        
        // 2. Username
        addLabelAndField("Username", usernameField = new ModernTextField());
        
        // 3. Email
        addLabelAndField("Email", emailField = new ModernTextField());
        
        // 4. Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        passLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        passwordField = createModernPasswordField();
        passwordField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        passwordField.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));

        // 5. Confirm Password
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

    public void showSetupStep() {
        currentStep = 2;
        formPanel.removeAll();
        
        // Step 2 Fields
        addLabelAndField("Buat PIN (6 Digit)", pinField = new ModernTextField());
        addLabelAndField("Saldo Awal (Rp)", initialBalanceField = new ModernTextField("0"));
        
        // Currency
        JLabel currencyLabel = new JLabel("Pilih Mata Uang");
        currencyLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        currencyLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        currencyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(currencyLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        String[] currencies = {"IDR (Rupiah)", "USD (Dollar)", "EUR (Euro)", "SGD (Singapore Dollar)"};
        currencyCombo = new JComboBox<>(currencies);
        setupCombo(currencyCombo);
        formPanel.add(currencyCombo);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
        
        // Main Goal
        JLabel goalLabel = new JLabel("Tujuan Utama");
        goalLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        goalLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        goalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(goalLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        String[] goals = {"Mengatur Pengeluaran", "Melacak Tagihan", "Menabung", "Investasi", "Bebas Hutang"};
        mainGoalCombo = new JComboBox<>(goals);
        setupCombo(mainGoalCombo);
        formPanel.add(mainGoalCombo);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
        
        // Language
        JLabel languageLabel = new JLabel("Pilih Bahasa");
        languageLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        languageLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        languageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(languageLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        String[] languages = {"Bahasa Indonesia (id)", "English (en)", "中文 (zh)"};
        languageCombo = new JComboBox<>(languages);
        setupCombo(languageCombo);
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
        
        // === PERBAIKAN FREEZE DI SINI ===
        // Kita trigger registerButton (yang terhubung ke Controller)
        // JANGAN override registerButton dengan nextButton!
        nextButton.addActionListener(e -> registerButton.doClick()); 
        
        buttonPanel.add(nextButton);
        buttonPanel.add(Box.createHorizontalStrut(ModernTheme.SPACING_MD));
        buttonPanel.add(backButton);
        
        formPanel.add(buttonPanel);
        
        formPanel.revalidate();
        formPanel.repaint();
    }
    
    private void addLabelAndField(String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(ModernTheme.FONT_BODY_BOLD);
        label.setForeground(ModernTheme.TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(label);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        
        field.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        field.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(field);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
    }
    
    private void setupCombo(JComboBox box) {
        box.setFont(ModernTheme.FONT_BODY);
        box.setBackground(ModernTheme.SURFACE);
        box.setForeground(ModernTheme.TEXT_PRIMARY);
        box.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        box.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JPasswordField createModernPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(ModernTheme.FONT_BODY);
        field.setBackground(ModernTheme.SURFACE);
        field.setForeground(ModernTheme.TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ModernTheme.BORDER, 1),
            BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD, ModernTheme.SPACING_SM, ModernTheme.SPACING_MD)
        ));
        return field;
    }

    public void goBackToStep1() {
        currentStep = 1;
        getContentPane().removeAll();
        initializeComponents();
        revalidate();
        repaint();
    }

    // Getters
    public String getFullName() { return fullNameField.getText(); } // BARU
    public String getUsername() { return usernameField.getText(); }
    public String getEmail() { return emailField.getText(); }
    public String getPassword() { return new String(passwordField.getPassword()); }
    public String getConfirmPassword() { return new String(confirmPasswordField.getPassword()); }
    public String getPin() { return pinField != null ? pinField.getText() : ""; }
    public String getInitialBalance() { return initialBalanceField != null ? initialBalanceField.getText() : "0"; }
    
    public String getMainGoal() {
        if (mainGoalCombo != null) return (String) mainGoalCombo.getSelectedItem();
        return "Mengatur Pengeluaran";
    }

    public String getCurrency() {
        if (currencyCombo != null) {
            String selected = (String) currencyCombo.getSelectedItem();
            return selected.split(" ")[0];
        }
        return "IDR";
    }
    
    public String getLanguage() {
        if (languageCombo != null) {
            String selected = (String) languageCombo.getSelectedItem();
            return selected.substring(selected.lastIndexOf("(") + 1, selected.lastIndexOf(")"));
        }
        return "id";
    }
    
    public int getCurrentStep() { return currentStep; }
    public void addRegisterListener(java.awt.event.ActionListener listener) { registerButton.addActionListener(listener); }
    public void addCancelListener(java.awt.event.ActionListener listener) { cancelButton.addActionListener(listener); }
    public void setStatus(String status) { statusLabel.setText(status); }
}