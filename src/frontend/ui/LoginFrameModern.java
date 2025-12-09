package frontend.ui;

import frontend.theme.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Modern Login Frame - Minimalis, elegant, contemporary design
 */
public class LoginFrameModern extends JFrame {
    private ModernTextField usernameField;
    private JPasswordField passwordField;
    private ModernButton loginButton;
    private ModernButton registerButton;
    private JLabel statusLabel;

    public LoginFrameModern() {
        setTitle("Aplikasi Keuangan Pribadi");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(450, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(ModernTheme.BACKGROUND);

        // Add window close listener
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(
                        LoginFrameModern.this,
                        "Anda yakin ingin keluar dari aplikasi?",
                        "Konfirmasi Keluar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        initializeComponents();
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(ModernTheme.BACKGROUND);
        mainPanel.setLayout(new BorderLayout());

        // Header panel dengan gradient-like appearance
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(ModernTheme.PRIMARY);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(ModernTheme.PRIMARY_LIGHT);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(0, getHeight(), getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(450, 110));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));

        JLabel titleLabel = new JLabel("Keuangan Pribadi");
        titleLabel.setFont(ModernTheme.FONT_TITLE_LARGE);
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Kelola keuangan dengan lebih baik");
        subtitleLabel.setFont(ModernTheme.FONT_BODY);
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));

        JPanel headerContentPanel = new JPanel();
        headerContentPanel.setOpaque(false);
        headerContentPanel.setLayout(new BoxLayout(headerContentPanel, BoxLayout.Y_AXIS));
        headerContentPanel.add(titleLabel);
        headerContentPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        headerContentPanel.add(subtitleLabel);

        headerPanel.add(headerContentPanel, BorderLayout.WEST);

        // Content panel - Form style dengan proper alignment
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(ModernTheme.BACKGROUND);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_XL, ModernTheme.SPACING_LG,
                ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));

        // Form wrapper
        JPanel formPanel = new JPanel();
        formPanel.setBackground(ModernTheme.BACKGROUND);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setMaximumSize(new Dimension(300, 250));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Username field
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

        // Password field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        passwordLabel.setForeground(ModernTheme.TEXT_PRIMARY);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));

        passwordField = new JPasswordField();
        passwordField.setFont(ModernTheme.FONT_BODY);
        passwordField.setBackground(ModernTheme.SURFACE);
        passwordField.setForeground(ModernTheme.TEXT_PRIMARY);
        passwordField.setCaretColor(ModernTheme.TEXT_PRIMARY);
        // Border dengan padding internal sama seperti ModernTextField
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernTheme.BORDER, 1),
                BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                        ModernTheme.SPACING_SM, ModernTheme.SPACING_MD)));
        passwordField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        passwordField.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add focus listener untuk highlight border biru saat focus
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ModernTheme.PRIMARY, 2),
                        BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                                ModernTheme.SPACING_SM, ModernTheme.SPACING_MD)));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                passwordField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ModernTheme.BORDER, 1),
                        BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                                ModernTheme.SPACING_SM, ModernTheme.SPACING_MD)));
            }
        });

        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_XL));

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ModernTheme.BACKGROUND);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(300, ModernTheme.BUTTON_HEIGHT + 10));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        loginButton = new ModernButton("Login", ModernTheme.PRIMARY, ModernTheme.PRIMARY_LIGHT);
        registerButton = new ModernButton("Daftar", ModernTheme.SUCCESS, new Color(100, 200, 90));

        // Enter Key Support
        passwordField.addActionListener(e -> loginButton.doClick());
        // Simple KeyBinding or Action for Username to jump to Password
        usernameField.addActionListener(e -> passwordField.requestFocusInWindow());

        // Set button sizes
        loginButton.setPreferredSize(new Dimension(140, ModernTheme.BUTTON_HEIGHT));
        registerButton.setPreferredSize(new Dimension(140, ModernTheme.BUTTON_HEIGHT));

        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createHorizontalStrut(ModernTheme.SPACING_MD));
        buttonPanel.add(registerButton);

        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));

        // Status label
        statusLabel = new JLabel();
        statusLabel.setFont(ModernTheme.FONT_CAPTION);
        statusLabel.setForeground(ModernTheme.TEXT_HINT);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(statusLabel);

        contentPanel.add(formPanel, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Footer panel
        JPanel footerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(ModernTheme.PRIMARY);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(ModernTheme.PRIMARY_LIGHT);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(0, 0, getWidth(), 0);
            }
        };
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_MD, ModernTheme.SPACING_LG,
                ModernTheme.SPACING_MD, ModernTheme.SPACING_LG));

        JLabel footerLabel = new JLabel("© 2024 Project by Kelompok 9 | Praktikum PBO");
        footerLabel.setFont(ModernTheme.FONT_CAPTION);
        footerLabel.setForeground(new Color(255, 255, 255, 220));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        footerPanel.add(footerLabel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(java.awt.event.ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(java.awt.event.ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }
}
