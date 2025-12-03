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
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private ModernButton registerButton;
    private ModernButton cancelButton;
    private JLabel statusLabel;
    
    public RegisterDialogModern(JFrame parent) {
        super(parent, "Daftar Akun Baru", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(450, 500);
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
        JPanel formPanel = new JPanel();
        formPanel.setBackground(ModernTheme.BACKGROUND);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setMaximumSize(new Dimension(300, 300));
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
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_XL));
        
        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ModernTheme.BACKGROUND);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(300, ModernTheme.BUTTON_HEIGHT + 10));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        registerButton = new ModernButton("Daftar", ModernTheme.SUCCESS, new Color(100, 200, 90));
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
    
    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
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
