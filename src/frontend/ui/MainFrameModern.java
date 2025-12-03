package frontend.ui;

import frontend.theme.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Modern MainFrame - Professional, contemporary UI dengan ModernTheme
 * 5 tabs dengan styling elegant dan minimalis
 */
public class MainFrameModern extends JFrame {
    private JTabbedPane tabbedPane;
    private JLabel userGreetingLabel;
    
    // Dashboard
    private JLabel totalBalanceValueLabel;
    private JLabel totalIncomeValueLabel;
    private JLabel totalExpenseValueLabel;
    private JLabel totalSavingValueLabel;
    
    // Transaction Form
    private JComboBox<String> transactionTypeCombo;
    private JComboBox<String> transactionCategoryCombo;
    private JComboBox<String> accountCombo;
    private ModernTextField transactionAmountField;
    private ModernTextField transactionDescriptionField;
    private ModernButton addTransactionButton;
    
    // Budget
    private JTable budgetTable;
    private DefaultTableModel budgetTableModel;
    private ModernButton addBudgetButton;
    
    // Goals
    private JPanel goalsPanel;
    private ModernButton addGoalButton;
    
    // Report
    private JTextArea reportArea;
    
    public MainFrameModern() {
        setTitle("Aplikasi Keuangan Pribadi");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setResizable(true);
        setBackground(ModernTheme.BACKGROUND);
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(ModernTheme.BACKGROUND);
        mainPanel.setLayout(new BorderLayout());
        
        // Header Panel
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Tabs Panel
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(ModernTheme.FONT_BODY);
        tabbedPane.setBackground(ModernTheme.BACKGROUND);
        tabbedPane.setForeground(ModernTheme.TEXT_PRIMARY);
        
        // Dashboard Tab
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        
        // Transaction Tab
        tabbedPane.addTab("Transaksi", createTransactionPanel());
        
        // Budget Tab
        tabbedPane.addTab("Budget", createBudgetPanel());
        
        // Financial Goal Tab
        tabbedPane.addTab("Target Tabungan", createGoalPanel());
        
        // Report Tab
        tabbedPane.addTab("Laporan", createReportPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(ModernTheme.PRIMARY);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.setColor(ModernTheme.PRIMARY_LIGHT);
                g2d.setStroke(new BasicStroke(1));
                g2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        };
        headerPanel.setPreferredSize(new Dimension(1200, 100));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                                             ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        
        // User greeting
        userGreetingLabel = new JLabel("Selamat datang!");
        userGreetingLabel.setFont(ModernTheme.FONT_TITLE);
        userGreetingLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Kelola keuangan Anda dengan lebih baik");
        subtitleLabel.setFont(ModernTheme.FONT_BODY);
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        
        JPanel headerContentPanel = new JPanel();
        headerContentPanel.setOpaque(false);
        headerContentPanel.setLayout(new BoxLayout(headerContentPanel, BoxLayout.Y_AXIS));
        headerContentPanel.add(userGreetingLabel);
        headerContentPanel.add(Box.createVerticalStrut(ModernTheme.SPACING_SM));
        headerContentPanel.add(subtitleLabel);
        
        headerPanel.add(headerContentPanel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(ModernTheme.SPACING_XL, ModernTheme.SPACING_LG,
                                        ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        
        // Summary cards
        panel.add(createSummaryPanel());
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setMaximumSize(new Dimension(1000, 300));
        
        totalBalanceValueLabel = new JLabel("Rp 0");
        panel.add(createCard("Total Saldo", totalBalanceValueLabel, ModernTheme.SUCCESS));
        
        totalIncomeValueLabel = new JLabel("Rp 0");
        panel.add(createCard("Pemasukan Bulan Ini", totalIncomeValueLabel, ModernTheme.PRIMARY));
        
        totalExpenseValueLabel = new JLabel("Rp 0");
        panel.add(createCard("Pengeluaran Bulan Ini", totalExpenseValueLabel, ModernTheme.DANGER));
        
        totalSavingValueLabel = new JLabel("Rp 0");
        panel.add(createCard("Tabungan", totalSavingValueLabel, ModernTheme.WARNING));
        
        return panel;
    }
    
    private JPanel createCard(String title, JLabel valueLabel, Color bgColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(bgColor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                                       ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        card.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(new Color(255, 255, 255, 220));
        titleLabel.setFont(ModernTheme.FONT_BODY);
        
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(ModernTheme.FONT_TITLE_LARGE);
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(ModernTheme.SPACING_MD));
        card.add(valueLabel);
        
        return card;
    }
    
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                                        ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        
        panel.add(createTransactionFormPanel(), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createTransactionFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Tambah Transaksi"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(ModernTheme.SPACING_MD, ModernTheme.SPACING_MD,
                               ModernTheme.SPACING_MD, ModernTheme.SPACING_MD);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Account
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel accountLabel = new JLabel("Rekening:");
        accountLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        panel.add(accountLabel, gbc);
        
        gbc.gridx = 1;
        accountCombo = new JComboBox<>();
        accountCombo.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        accountCombo.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        accountCombo.setFont(ModernTheme.FONT_BODY);
        panel.add(accountCombo, gbc);
        
        // Type
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel typeLabel = new JLabel("Tipe:");
        typeLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        panel.add(typeLabel, gbc);
        
        gbc.gridx = 1;
        transactionTypeCombo = new JComboBox<>(new String[]{"Pemasukan", "Pengeluaran"});
        transactionTypeCombo.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        transactionTypeCombo.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        transactionTypeCombo.setFont(ModernTheme.FONT_BODY);
        transactionTypeCombo.addActionListener(e -> updateCategoryCombo());
        panel.add(transactionTypeCombo, gbc);
        
        // Category
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel categoryLabel = new JLabel("Kategori:");
        categoryLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        panel.add(categoryLabel, gbc);
        
        gbc.gridx = 1;
        transactionCategoryCombo = new JComboBox<>();
        transactionCategoryCombo.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        transactionCategoryCombo.setMaximumSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        transactionCategoryCombo.setFont(ModernTheme.FONT_BODY);
        panel.add(transactionCategoryCombo, gbc);
        
        updateCategoryCombo();
        
        // Amount
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel amountLabel = new JLabel("Jumlah:");
        amountLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        panel.add(amountLabel, gbc);
        
        gbc.gridx = 1;
        transactionAmountField = new ModernTextField();
        transactionAmountField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        panel.add(transactionAmountField, gbc);
        
        // Description
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel descLabel = new JLabel("Deskripsi:");
        descLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        panel.add(descLabel, gbc);
        
        gbc.gridx = 1;
        transactionDescriptionField = new ModernTextField();
        transactionDescriptionField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        panel.add(transactionDescriptionField, gbc);
        
        // Submit Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        addTransactionButton = new ModernButton("Tambah Transaksi", ModernTheme.PRIMARY, ModernTheme.PRIMARY_LIGHT);
        addTransactionButton.setPreferredSize(new Dimension(300, ModernTheme.BUTTON_HEIGHT));
        panel.add(addTransactionButton, gbc);
        
        return panel;
    }
    
    private JPanel createBudgetPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                                        ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        
        // Table for budgets
        String[] columnNames = {"Kategori", "Budget", "Terpakai", "Sisa", "Status"};
        budgetTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
        budgetTable = new JTable(budgetTableModel);
        budgetTable.setEnabled(false);
        budgetTable.setFont(ModernTheme.FONT_BODY);
        
        panel.add(new JScrollPane(budgetTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ModernTheme.BACKGROUND);
        addBudgetButton = new ModernButton("Tambah Budget", ModernTheme.SUCCESS, new Color(100, 200, 90));
        buttonPanel.add(addBudgetButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createGoalPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                                        ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        
        goalsPanel = new JPanel();
        goalsPanel.setBackground(ModernTheme.BACKGROUND);
        goalsPanel.setLayout(new BoxLayout(goalsPanel, BoxLayout.Y_AXIS));
        
        panel.add(new JScrollPane(goalsPanel), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ModernTheme.BACKGROUND);
        addGoalButton = new ModernButton("Tambah Target", ModernTheme.WARNING, new Color(220, 180, 60));
        buttonPanel.add(addGoalButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createReportPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                                        ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        
        reportArea = new JTextArea();
        reportArea.setFont(ModernTheme.FONT_BODY);
        reportArea.setEditable(false);
        reportArea.setBackground(ModernTheme.SURFACE);
        reportArea.setForeground(ModernTheme.TEXT_PRIMARY);
        
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return panel;
    }
    
    private void updateCategoryCombo() {
        // Get selected transaction type
        String selectedType = (String) transactionTypeCombo.getSelectedItem();
        
        // Clear existing items
        transactionCategoryCombo.removeAllItems();
        
        // Add categories based on type
        if ("Pemasukan".equals(selectedType)) {
            transactionCategoryCombo.addItem("Gaji");
            transactionCategoryCombo.addItem("Side-Work");
            transactionCategoryCombo.addItem("Bonus");
            transactionCategoryCombo.addItem("Jualan");
            transactionCategoryCombo.addItem("Investasi");
            transactionCategoryCombo.addItem("Lainnya");
        } else {
            transactionCategoryCombo.addItem("Konsumsi");
            transactionCategoryCombo.addItem("Transportasi");
            transactionCategoryCombo.addItem("Iuran");
            transactionCategoryCombo.addItem("Tak Terduga");
            transactionCategoryCombo.addItem("Hiburan");
            transactionCategoryCombo.addItem("Kesehatan");
            transactionCategoryCombo.addItem("Pendidikan");
            transactionCategoryCombo.addItem("Lainnya");
        }
    }
    
    // Setter untuk user greeting
    public void setUserGreeting(String username) {
        userGreetingLabel.setText("Selamat datang, " + username + "!");
    }
    
    // Getter methods (same as old MainFrame)
    public JComboBox<String> getTransactionTypeCombo() {
        return transactionTypeCombo;
    }
    
    public JComboBox<String> getTransactionCategoryCombo() {
        return transactionCategoryCombo;
    }
    
    public JComboBox<String> getAccountCombo() {
        return accountCombo;
    }
    
    public void setAvailableAccounts(java.util.List<String> accounts) {
        accountCombo.removeAllItems();
        for (String account : accounts) {
            accountCombo.addItem(account);
        }
    }
    
    public void setCategoryComboModel(java.util.List<String> categories) {
        transactionCategoryCombo.removeAllItems();
        for (String category : categories) {
            transactionCategoryCombo.addItem(category);
        }
    }
    
    public String getTransactionAmount() {
        return transactionAmountField.getText();
    }
    
    public String getTransactionDescription() {
        return transactionDescriptionField.getText();
    }
    
    public void clearTransactionForm() {
        transactionAmountField.setText("");
        transactionDescriptionField.setText("");
    }
    
    public ModernButton getAddTransactionButton() {
        return addTransactionButton;
    }
    
    public ModernButton getAddBudgetButton() {
        return addBudgetButton;
    }
    
    public ModernButton getAddGoalButton() {
        return addGoalButton;
    }
    
    public JTable getBudgetTable() {
        return budgetTable;
    }
    
    public DefaultTableModel getBudgetTableModel() {
        return budgetTableModel;
    }
    
    public JPanel getGoalsPanel() {
        return goalsPanel;
    }
    
    public JTextArea getReportArea() {
        return reportArea;
    }
    
    public JLabel getTotalBalanceValueLabel() {
        return totalBalanceValueLabel;
    }
    
    public JLabel getTotalIncomeValueLabel() {
        return totalIncomeValueLabel;
    }
    
    public JLabel getTotalExpenseValueLabel() {
        return totalExpenseValueLabel;
    }
    
    public JLabel getTotalSavingValueLabel() {
        return totalSavingValueLabel;
    }
    
    // Event listener methods
    public void addTransactionListener(javax.swing.event.ChangeListener listener) {
        addTransactionButton.addActionListener(l -> {
            javax.swing.event.ChangeEvent e = new javax.swing.event.ChangeEvent(this);
            listener.stateChanged(e);
        });
    }
    
    public void addBudgetListener(javax.swing.event.ChangeListener listener) {
        addBudgetButton.addActionListener(l -> {
            javax.swing.event.ChangeEvent e = new javax.swing.event.ChangeEvent(this);
            listener.stateChanged(e);
        });
    }
    
    public void addGoalListener(javax.swing.event.ChangeListener listener) {
        addGoalButton.addActionListener(l -> {
            javax.swing.event.ChangeEvent e = new javax.swing.event.ChangeEvent(this);
            listener.stateChanged(e);
        });
    }
    
    // Getter methods for transaction form
    public String getSelectedAccount() {
        return (String) accountCombo.getSelectedItem();
    }
    
    public String getTransactionType() {
        return (String) transactionTypeCombo.getSelectedItem();
    }
    
    public String getTransactionCategory() {
        return (String) transactionCategoryCombo.getSelectedItem();
    }
    
    // Update dashboard method
    public void updateDashboard(double totalBalance, double income, double expense, double saving) {
        totalBalanceValueLabel.setText(String.format("Rp %,.0f", totalBalance));
        totalIncomeValueLabel.setText(String.format("Rp %,.0f", income));
        totalExpenseValueLabel.setText(String.format("Rp %,.0f", expense));
        totalSavingValueLabel.setText(String.format("Rp %,.0f", saving));
    }
}
