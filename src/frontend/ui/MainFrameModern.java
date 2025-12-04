package frontend.ui;

import frontend.theme.*;
import backend.utils.LocalizationUtils; // Import baru
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class MainFrameModern extends JFrame {
    private JTabbedPane tabbedPane;
    private JLabel userGreetingLabel;
    
    // Dashboard
    private JLabel totalBalanceValueLabel;
    private JLabel totalIncomeValueLabel;
    private JLabel totalExpenseValueLabel;
    private JLabel totalSavingValueLabel;
    private JLabel totalBalanceTitle;
    private JLabel totalIncomeTitle;
    private JLabel totalExpenseTitle;
    private JLabel totalSavingTitle;
    
    // Transaction Form
    private JComboBox<String> transactionTypeCombo;
    private JComboBox<String> transactionCategoryCombo;
    private JComboBox<String> accountCombo;
    private ModernTextField transactionAmountField;
    private ModernTextField transactionDescriptionField;
    private ModernButton addTransactionButton;
    private JLabel transactionAccountLabel;
    private JLabel transactionTypeLabel;
    private JLabel transactionCategoryLabel;
    private JLabel transactionAmountLabel;
    private JLabel transactionDescLabel;
    
    // Budget
    private JTable budgetTable;
    private DefaultTableModel budgetTableModel;
    private ModernButton addBudgetButton;
    
    // Goals
    private JPanel goalsPanel;
    private ModernButton addGoalButton;
    private JLabel emptyGoalLabel;
    
    // Report
    private JTextArea reportArea;
    
    public MainFrameModern() {
        setTitle(LocalizationUtils.getString("app.title"));
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
        
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(ModernTheme.FONT_BODY);
        tabbedPane.setBackground(ModernTheme.BACKGROUND);
        tabbedPane.setForeground(ModernTheme.TEXT_PRIMARY);
        
        // Gunakan LocalizationUtils untuk nama tab
        tabbedPane.addTab(LocalizationUtils.getString("tab.dashboard"), createDashboardPanel());
        tabbedPane.addTab(LocalizationUtils.getString("tab.transaction"), createTransactionPanel());
        tabbedPane.addTab(LocalizationUtils.getString("tab.budget"), createBudgetPanel());
        tabbedPane.addTab(LocalizationUtils.getString("tab.goal"), createGoalPanel());
        tabbedPane.addTab(LocalizationUtils.getString("tab.report"), createReportPanel());
        
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
        
        // Greeting default
        userGreetingLabel = new JLabel(LocalizationUtils.getString("msg.welcome"));
        userGreetingLabel.setFont(ModernTheme.FONT_TITLE);
        userGreetingLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel(LocalizationUtils.getString("header.subtitle"));
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
        
        panel.add(createSummaryPanel());
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setMaximumSize(new Dimension(1000, 300));
        
        String symbol = LocalizationUtils.getCurrencySymbol();
        
        totalBalanceValueLabel = new JLabel(symbol + " 0");
        panel.add(createCard(LocalizationUtils.getString("card.balance"), totalBalanceValueLabel, ModernTheme.SUCCESS));
        
        totalIncomeValueLabel = new JLabel(symbol + " 0");
        panel.add(createCard(LocalizationUtils.getString("card.income"), totalIncomeValueLabel, ModernTheme.PRIMARY));
        
        totalExpenseValueLabel = new JLabel(symbol + " 0");
        panel.add(createCard(LocalizationUtils.getString("card.expense"), totalExpenseValueLabel, ModernTheme.DANGER));
        
        totalSavingValueLabel = new JLabel(symbol + " 0");
        panel.add(createCard(LocalizationUtils.getString("card.saving"), totalSavingValueLabel, ModernTheme.WARNING));
        
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
        panel.setBorder(BorderFactory.createTitledBorder(LocalizationUtils.getString("trans.title")));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(ModernTheme.SPACING_MD, ModernTheme.SPACING_MD,
                               ModernTheme.SPACING_MD, ModernTheme.SPACING_MD);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Account
        gbc.gridx = 0; gbc.gridy = 0;
        transactionAccountLabel = new JLabel(LocalizationUtils.getString("trans.account"));
        transactionAccountLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        panel.add(transactionAccountLabel, gbc);
        
        gbc.gridx = 1;
        accountCombo = new JComboBox<>();
        accountCombo.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        accountCombo.setFont(ModernTheme.FONT_BODY);
        panel.add(accountCombo, gbc);
        
        // Type
        gbc.gridx = 0; gbc.gridy = 1;
        transactionTypeLabel = new JLabel(LocalizationUtils.getString("trans.type"));
        transactionTypeLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        panel.add(transactionTypeLabel, gbc);
        
        gbc.gridx = 1;
        transactionTypeCombo = new JComboBox<>(new String[]{
            LocalizationUtils.getString("trans.type.income"), 
            LocalizationUtils.getString("trans.type.expense")
        });
        transactionTypeCombo.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        transactionTypeCombo.setFont(ModernTheme.FONT_BODY);
        transactionTypeCombo.addActionListener(e -> updateCategoryCombo());
        panel.add(transactionTypeCombo, gbc);
        
        // Category
        gbc.gridx = 0; gbc.gridy = 2;
        transactionCategoryLabel = new JLabel(LocalizationUtils.getString("trans.category"));
        transactionCategoryLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        panel.add(transactionCategoryLabel, gbc);
        
        gbc.gridx = 1;
        transactionCategoryCombo = new JComboBox<>();
        transactionCategoryCombo.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        transactionCategoryCombo.setFont(ModernTheme.FONT_BODY);
        panel.add(transactionCategoryCombo, gbc);
        
        updateCategoryCombo();
        
        // Amount
        gbc.gridx = 0; gbc.gridy = 3;
        transactionAmountLabel = new JLabel(LocalizationUtils.getString("trans.amount"));
        transactionAmountLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        panel.add(transactionAmountLabel, gbc);
        
        gbc.gridx = 1;
        transactionAmountField = new ModernTextField();
        transactionAmountField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        panel.add(transactionAmountField, gbc);
        
        // Description
        gbc.gridx = 0; gbc.gridy = 4;
        transactionDescLabel = new JLabel(LocalizationUtils.getString("trans.desc"));
        transactionDescLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        panel.add(transactionDescLabel, gbc);
        
        gbc.gridx = 1;
        transactionDescriptionField = new ModernTextField();
        transactionDescriptionField.setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));
        panel.add(transactionDescriptionField, gbc);
        
        // Submit Button
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        addTransactionButton = new ModernButton(LocalizationUtils.getString("trans.btn"), ModernTheme.PRIMARY, ModernTheme.PRIMARY_LIGHT);
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
        
        String[] columnNames = {
            LocalizationUtils.getString("col.category"),
            LocalizationUtils.getString("col.budget"),
            LocalizationUtils.getString("col.used"),
            LocalizationUtils.getString("col.remaining"),
            LocalizationUtils.getString("col.status")
        };
        
        budgetTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
        budgetTable = new JTable(budgetTableModel);
        budgetTable.setEnabled(false);
        budgetTable.setFont(ModernTheme.FONT_BODY);
        
        panel.add(new JScrollPane(budgetTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ModernTheme.BACKGROUND);
        addBudgetButton = new ModernButton(LocalizationUtils.getString("budget.add"), ModernTheme.SUCCESS, new Color(100, 200, 90));
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
        
        // Empty Label handled in Controller/Update method generally, but adding init here
        emptyGoalLabel = new JLabel(LocalizationUtils.getString("goal.empty"));
        emptyGoalLabel.setFont(ModernTheme.FONT_BODY);
        goalsPanel.add(emptyGoalLabel);
        
        panel.add(new JScrollPane(goalsPanel), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(ModernTheme.BACKGROUND);
        addGoalButton = new ModernButton(LocalizationUtils.getString("goal.add"), ModernTheme.WARNING, new Color(220, 180, 60));
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
        reportArea.setText(LocalizationUtils.getString("report.empty"));
        
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return panel;
    }
    
    private void updateCategoryCombo() {
        String selectedType = (String) transactionTypeCombo.getSelectedItem();
        transactionCategoryCombo.removeAllItems();
        
        // Gunakan string dari resource untuk perbandingan
        if (LocalizationUtils.getString("trans.type.income").equals(selectedType)) {
            transactionCategoryCombo.addItem("Gaji");
            transactionCategoryCombo.addItem("Bonus");
            transactionCategoryCombo.addItem("Investasi");
            transactionCategoryCombo.addItem("Lainnya");
        } else {
            transactionCategoryCombo.addItem("Konsumsi");
            transactionCategoryCombo.addItem("Transportasi");
            transactionCategoryCombo.addItem("Hiburan");
            transactionCategoryCombo.addItem("Kesehatan");
            transactionCategoryCombo.addItem("Pendidikan");
            transactionCategoryCombo.addItem("Lainnya");
        }
    }
    
    public void setUserGreeting(String username) {
        userGreetingLabel.setText(LocalizationUtils.getString("msg.welcome") + ", " + username + "!");
    }
    
    // Getters
    public JComboBox<String> getTransactionTypeCombo() { return transactionTypeCombo; }
    public JComboBox<String> getTransactionCategoryCombo() { return transactionCategoryCombo; }
    public JComboBox<String> getAccountCombo() { return accountCombo; }
    public String getTransactionAmount() { return transactionAmountField.getText(); }
    public String getTransactionDescription() { return transactionDescriptionField.getText(); }
    public ModernButton getAddTransactionButton() { return addTransactionButton; }
    public ModernButton getAddBudgetButton() { return addBudgetButton; }
    public ModernButton getAddGoalButton() { return addGoalButton; }
    public JTable getBudgetTable() { return budgetTable; }
    public DefaultTableModel getBudgetTableModel() { return budgetTableModel; }
    public JPanel getGoalsPanel() { return goalsPanel; }
    public JTextArea getReportArea() { return reportArea; }
    
    public void clearTransactionForm() {
        transactionAmountField.setText("");
        transactionDescriptionField.setText("");
    }
    
    public void setAvailableAccounts(java.util.List<String> accounts) {
        accountCombo.removeAllItems();
        for (String account : accounts) {
            accountCombo.addItem(account);
        }
    }
    
    public void addTransactionListener(javax.swing.event.ChangeListener listener) {
        addTransactionButton.addActionListener(l -> listener.stateChanged(new javax.swing.event.ChangeEvent(this)));
    }
    public void addBudgetListener(javax.swing.event.ChangeListener listener) {
        addBudgetButton.addActionListener(l -> listener.stateChanged(new javax.swing.event.ChangeEvent(this)));
    }
    public void addGoalListener(javax.swing.event.ChangeListener listener) {
        addGoalButton.addActionListener(l -> listener.stateChanged(new javax.swing.event.ChangeEvent(this)));
    }
    
    public String getSelectedAccount() { return (String) accountCombo.getSelectedItem(); }
    public String getTransactionType() { return (String) transactionTypeCombo.getSelectedItem(); }
    public String getTransactionCategory() { return (String) transactionCategoryCombo.getSelectedItem(); }
    
    public void updateDashboard(double totalBalance, double income, double expense, double saving) {
        String symbol = LocalizationUtils.getCurrencySymbol();
        totalBalanceValueLabel.setText(String.format("%s %,.0f", symbol, totalBalance));
        totalIncomeValueLabel.setText(String.format("%s %,.0f", symbol, income));
        totalExpenseValueLabel.setText(String.format("%s %,.0f", symbol, expense));
        totalSavingValueLabel.setText(String.format("%s %,.0f", symbol, saving));
    }
}