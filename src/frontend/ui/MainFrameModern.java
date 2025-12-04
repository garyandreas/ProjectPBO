package frontend.ui;

import frontend.theme.*;
import backend.utils.LocalizationUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class MainFrameModern extends JFrame {
    private JTabbedPane tabbedPane;
    private JLabel userGreetingLabel;
    
    // Dashboard Components
    private JLabel totalBalanceValueLabel;
    private JLabel totalIncomeValueLabel;
    private JLabel totalExpenseValueLabel;
    private JLabel totalSavingValueLabel;
    
    // CHART BARU
    private SimpleBarChart simpleChart; 
    
    // Transaction Form
    private JComboBox<String> transactionTypeCombo;
    private JComboBox<String> transactionCategoryCombo;
    private JComboBox<String> accountCombo;
    private ModernTextField transactionAmountField;
    private ModernTextField transactionDescriptionField;
    private ModernButton addTransactionButton;
    
    // Budget & Goals & Report
    private JTable budgetTable;
    private DefaultTableModel budgetTableModel;
    private ModernButton addBudgetButton;
    private JPanel goalsPanel;
    private ModernButton addGoalButton;
    private JTextArea reportArea;
    
    public MainFrameModern() {
        setTitle(LocalizationUtils.getString("app.title"));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1200, 800);
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
        
        tabbedPane.addTab(LocalizationUtils.getString("tab.dashboard"), createDashboardPanel());
        tabbedPane.addTab(LocalizationUtils.getString("tab.transaction"), createTransactionPanel());
        tabbedPane.addTab(LocalizationUtils.getString("tab.budget"), createBudgetPanel());
        tabbedPane.addTab(LocalizationUtils.getString("tab.goal"), createGoalPanel());
        tabbedPane.addTab(LocalizationUtils.getString("tab.report"), createReportPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    // --- DASHBOARD SECTION ---
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
        headerPanel.setPreferredSize(new Dimension(1200, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_MD, ModernTheme.SPACING_LG, ModernTheme.SPACING_MD, ModernTheme.SPACING_LG));
        
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
        headerContentPanel.add(subtitleLabel);
        
        headerPanel.add(headerContentPanel, BorderLayout.WEST);
        return headerPanel;
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setLayout(new BorderLayout()); 
        panel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG));
        
        // 1. Summary Cards (Top)
        panel.add(createSummaryPanel(), BorderLayout.NORTH);
        
        // 2. Chart Section (Center)
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(ModernTheme.BACKGROUND);
        centerPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, 0, ModernTheme.SPACING_LG, 0));
        
        JLabel chartTitle = new JLabel("Grafik Pengeluaran per Kategori");
        chartTitle.setFont(ModernTheme.FONT_SUBTITLE);
        chartTitle.setForeground(ModernTheme.TEXT_PRIMARY);
        centerPanel.add(chartTitle, BorderLayout.NORTH);
        
        simpleChart = new SimpleBarChart();
        centerPanel.add(simpleChart, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // 3. [BARU] Quick Action Button (South)
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setBackground(ModernTheme.BACKGROUND);
        
        ModernButton quickAddBtn = new ModernButton("+ Catat Transaksi", ModernTheme.PRIMARY, ModernTheme.PRIMARY_LIGHT);
        quickAddBtn.setPreferredSize(new Dimension(180, 45));
        
        // Aksi: Pindah ke tab "Transaksi" (index 1)
        quickAddBtn.addActionListener(e -> tabbedPane.setSelectedIndex(1));
        
        southPanel.add(quickAddBtn);
        panel.add(southPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4, ModernTheme.SPACING_MD, 0));
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setPreferredSize(new Dimension(1000, 100));
        
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
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(new Color(255, 255, 255, 220));
        titleLabel.setFont(ModernTheme.FONT_BODY_BOLD);
        
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(ModernTheme.FONT_TITLE);
        
        card.add(titleLabel);
        card.add(Box.createVerticalGlue());
        card.add(valueLabel);
        
        return card;
    }
    
    // --- TRANSACTION SECTION ---
    private JPanel createTransactionFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ModernTheme.BORDER),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD, ModernTheme.SPACING_SM, ModernTheme.SPACING_MD);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Form Title
        JLabel title = new JLabel(LocalizationUtils.getString("trans.title"));
        title.setFont(ModernTheme.FONT_SUBTITLE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);
        
        // Reset gridwidth
        gbc.gridwidth = 1;
        gbc.gridy++;
        
        // Account
        gbc.gridx = 0; 
        panel.add(createLabel(LocalizationUtils.getString("trans.account")), gbc);
        gbc.gridx = 1;
        accountCombo = new JComboBox<>();
        styleComboBox(accountCombo);
        panel.add(accountCombo, gbc);
        
        // Type
        gbc.gridy++; gbc.gridx = 0;
        panel.add(createLabel(LocalizationUtils.getString("trans.type")), gbc);
        gbc.gridx = 1;
        transactionTypeCombo = new JComboBox<>(new String[]{
            LocalizationUtils.getString("trans.type.income"), 
            LocalizationUtils.getString("trans.type.expense")
        });
        styleComboBox(transactionTypeCombo);
        panel.add(transactionTypeCombo, gbc);
        
        // Category
        gbc.gridy++; gbc.gridx = 0;
        panel.add(createLabel(LocalizationUtils.getString("trans.category")), gbc);
        gbc.gridx = 1;
        transactionCategoryCombo = new JComboBox<>();
        styleComboBox(transactionCategoryCombo);
        panel.add(transactionCategoryCombo, gbc);
        
        // Amount
        gbc.gridy++; gbc.gridx = 0;
        panel.add(createLabel(LocalizationUtils.getString("trans.amount")), gbc);
        gbc.gridx = 1;
        transactionAmountField = new ModernTextField();
        panel.add(transactionAmountField, gbc);
        
        // Description
        gbc.gridy++; gbc.gridx = 0;
        panel.add(createLabel(LocalizationUtils.getString("trans.desc")), gbc);
        gbc.gridx = 1;
        transactionDescriptionField = new ModernTextField();
        panel.add(transactionDescriptionField, gbc);
        
        // Button
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0); // Add top margin
        
        addTransactionButton = new ModernButton(LocalizationUtils.getString("trans.btn"), ModernTheme.PRIMARY, ModernTheme.PRIMARY_LIGHT);
        addTransactionButton.setPreferredSize(new Dimension(200, 40));
        panel.add(addTransactionButton, gbc);
        
        return panel;
    }
    
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Center the form
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(ModernTheme.BACKGROUND);
        centerWrapper.add(createTransactionFormPanel());
        
        panel.add(centerWrapper, BorderLayout.CENTER);
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(ModernTheme.FONT_BODY_BOLD);
        label.setForeground(ModernTheme.TEXT_PRIMARY);
        return label;
    }
    
    private void styleComboBox(JComboBox box) {
        box.setPreferredSize(new Dimension(300, 40));
        box.setFont(ModernTheme.FONT_BODY);
        box.setBackground(ModernTheme.SURFACE);
    }

    // --- OTHER TABS ---
    private JPanel createBudgetPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.BACKGROUND);
        addBudgetButton = new ModernButton(LocalizationUtils.getString("budget.add"), ModernTheme.SUCCESS, new Color(100, 200, 90));
        panel.add(addBudgetButton, BorderLayout.SOUTH);
        
        String[] cols = {LocalizationUtils.getString("col.category"), LocalizationUtils.getString("col.budget"), "Status"};
        budgetTableModel = new DefaultTableModel(new Object[][]{}, cols);
        budgetTable = new JTable(budgetTableModel);
        panel.add(new JScrollPane(budgetTable), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createGoalPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.BACKGROUND);
        addGoalButton = new ModernButton(LocalizationUtils.getString("goal.add"), ModernTheme.WARNING, new Color(220, 180, 60));
        panel.add(addGoalButton, BorderLayout.SOUTH);
        goalsPanel = new JPanel();
        panel.add(new JScrollPane(goalsPanel), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        reportArea = new JTextArea(LocalizationUtils.getString("report.empty"));
        reportArea.setEditable(false);
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return panel;
    }

    // --- METHODS ---
    
    public void setUserGreeting(String username) {
        userGreetingLabel.setText(LocalizationUtils.getString("msg.welcome") + ", " + username + "!");
    }
    
    public void updateDashboard(double totalBalance, double income, double expense, double saving) {
        String symbol = LocalizationUtils.getCurrencySymbol();
        totalBalanceValueLabel.setText(String.format("%s %,.0f", symbol, totalBalance));
        totalIncomeValueLabel.setText(String.format("%s %,.0f", symbol, income));
        totalExpenseValueLabel.setText(String.format("%s %,.0f", symbol, expense));
        totalSavingValueLabel.setText(String.format("%s %,.0f", symbol, saving));
    }
    
    // UPDATE GRAFIK
    public void updateExpenseChart(Map<String, Double> categoryExpenses) {
        if (simpleChart != null) {
            simpleChart.setData(categoryExpenses);
        }
    }
    
    public void setAvailableAccounts(java.util.List<String> accounts) {
        accountCombo.removeAllItems();
        for (String acc : accounts) accountCombo.addItem(acc);
    }
    
    public void setAvailableCategories(java.util.List<String> categories) {
        transactionCategoryCombo.removeAllItems();
        for (String cat : categories) transactionCategoryCombo.addItem(cat);
    }
    
    public String getSelectedAccount() { return (String) accountCombo.getSelectedItem(); }
    public String getTransactionType() { return (String) transactionTypeCombo.getSelectedItem(); }
    public String getTransactionCategory() { return (String) transactionCategoryCombo.getSelectedItem(); }
    public String getTransactionAmount() { return transactionAmountField.getText(); }
    public String getTransactionDescription() { return transactionDescriptionField.getText(); }
    
    public void addTransactionListener(java.awt.event.ActionListener l) { addTransactionButton.addActionListener(l); }
    public void addBudgetListener(java.awt.event.ActionListener l) { addBudgetButton.addActionListener(l); }
    public void addGoalListener(java.awt.event.ActionListener l) { addGoalButton.addActionListener(l); }
    
    // LISTENER TIPE TRANSAKSI
    public void addTransactionTypeListener(java.awt.event.ActionListener l) {
        transactionTypeCombo.addActionListener(l);
    }
    
    public void clearTransactionForm() {
        transactionAmountField.setText("");
        transactionDescriptionField.setText("");
    }
}