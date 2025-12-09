package frontend.ui;

import frontend.theme.*;
import backend.models.Budget;
import backend.services.CategoryService;
import backend.utils.LocalizationUtils;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MainFrameModern extends JFrame {
    private JTabbedPane tabbedPane;
    private JLabel userGreetingLabel;

    // Dashboard Components
    private JLabel totalBalanceValueLabel;
    private JLabel totalCashValueLabel;
    private JLabel totalBankValueLabel;
    private JLabel totalIncomeValueLabel;
    private JLabel totalExpenseValueLabel;
    private JLabel totalSavingValueLabel;
    private ModernButton transferButton;
    private SimplePieChart expenseChart; // Changed to Pie
    private SimplePieChart incomePieChart; // Added Income Pie

    // Transaction Form
    private JComboBox<String> transactionTypeCombo;
    private JComboBox<String> transactionCategoryCombo;
    private JComboBox<String> accountCombo;
    private ModernTextField transactionAmountField;
    private ModernTextField transactionDescriptionField;
    private ModernButton addTransactionButton;

    // Budget Components
    private JTable budgetTable;
    private DefaultTableModel budgetTableModel;
    private ModernButton addBudgetButton;
    private ModernButton deleteBudgetButton;

    // Goals & Report
    private JPanel goalsPanel;
    private ModernButton addGoalButton;
    private JLabel emptyGoalLabel;
    private JTextArea reportArea;

    // Dashboard Period Selector
    private JComboBox<String> dashPeriodTypeCombo;
    private JComboBox<String> dashMonthCombo;
    private JSpinner dashYearSpinner;
    private ModernButton dashApplyButton;

    // Reports
    private ReportPanel reportPanel;

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
        headerPanel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                ModernTheme.SPACING_LG));

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

        // Period Selector for Dashboard
        JPanel periodPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        periodPanel.setOpaque(false);

        // Standardize dimensions
        Dimension comboSize = new Dimension(100, 30);
        Dimension spinnerSize = new Dimension(80, 30);

        String[] types = { "Bulanan", "Tahunan" };
        dashPeriodTypeCombo = new JComboBox<>(types);
        dashPeriodTypeCombo.setPreferredSize(comboSize);

        String[] months = { "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember" };
        dashMonthCombo = new JComboBox<>(months);
        dashMonthCombo.setPreferredSize(comboSize);
        dashMonthCombo.setSelectedIndex(java.time.LocalDate.now().getMonthValue() - 1);

        dashYearSpinner = new JSpinner(new SpinnerNumberModel(java.time.LocalDate.now().getYear(), 2000, 2100, 1));
        dashYearSpinner.setPreferredSize(spinnerSize);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(dashYearSpinner, "#");
        editor.getTextField().setHorizontalAlignment(SwingConstants.LEFT);
        dashYearSpinner.setEditor(editor);

        dashApplyButton = new ModernButton("Terapkan", ModernTheme.SECONDARY, ModernTheme.SECONDARY);
        dashApplyButton.setForeground(Color.WHITE);
        dashApplyButton.setPreferredSize(new Dimension(100, 30));
        dashApplyButton.setVerticalAlignment(SwingConstants.CENTER); // Ensure vertical centering

        periodPanel.add(new JLabel("Periode: ")).setForeground(Color.WHITE);
        periodPanel.add(dashPeriodTypeCombo);
        periodPanel.add(dashMonthCombo);
        periodPanel.add(dashYearSpinner);
        periodPanel.add(dashApplyButton);

        dashPeriodTypeCombo.addActionListener(e -> {
            boolean isMonthly = dashPeriodTypeCombo.getSelectedItem().equals("Bulanan");
            dashMonthCombo.setVisible(isMonthly);
        });

        // Wrap periodPanel to center it vertically
        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        rightContainer.add(periodPanel, gbc);

        headerPanel.add(rightContainer, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createReportPanel() {
        reportPanel = new ReportPanel();
        return reportPanel;
    }

    public ReportPanel getReportPanel() {
        return reportPanel;
    }

    public void addDashboardFilterListener(java.awt.event.ActionListener listener) {
        dashApplyButton.addActionListener(listener);
    }

    public ReportPanel.FilterParams getDashboardFilter() {
        String type = (String) dashPeriodTypeCombo.getSelectedItem();
        int month = dashMonthCombo.getSelectedIndex() + 1;
        int year = (int) dashYearSpinner.getValue();
        return new ReportPanel.FilterParams(type, month, year);
    }

    private JComponent createDashboardPanel() { // Changed to JComponent
        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(ModernTheme.BACKGROUND);
        scrollContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Summary Cards
        scrollContent.add(createSummaryPanel());
        scrollContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Charts Panel
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 20)); // Side by side
        chartsPanel.setBackground(ModernTheme.BACKGROUND);
        chartsPanel.setPreferredSize(new Dimension(800, 350));
        chartsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        // Expense Chart Card
        JPanel expenseCard = new JPanel(new BorderLayout());
        expenseCard.setBackground(ModernTheme.SURFACE);
        expenseCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel expenseLabel = new JLabel("Grafik Pengeluaran per Kategori");
        expenseLabel.setFont(ModernTheme.FONT_SUBTITLE);
        expenseCard.add(expenseLabel, BorderLayout.NORTH);

        expenseChart = new SimplePieChart();
        expenseCard.add(expenseChart, BorderLayout.CENTER);

        // Income Chart Card
        JPanel incomeCard = new JPanel(new BorderLayout());
        incomeCard.setBackground(ModernTheme.SURFACE);
        incomeCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel incomeLabel = new JLabel("Grafik Pemasukan per Kategori");
        incomeLabel.setFont(ModernTheme.FONT_SUBTITLE);
        incomeCard.add(incomeLabel, BorderLayout.NORTH);

        incomePieChart = new SimplePieChart(); // Added for Income
        incomeCard.add(incomePieChart, BorderLayout.CENTER);

        chartsPanel.add(expenseCard);
        chartsPanel.add(incomeCard);

        scrollContent.add(chartsPanel);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Quick Action
        JPanel quickActionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        quickActionPanel.setBackground(ModernTheme.BACKGROUND);
        quickActionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        transferButton = new ModernButton("Transfer Dana", ModernTheme.INFO, ModernTheme.INFO);
        transferButton.setForeground(Color.WHITE);
        transferButton.setPreferredSize(new Dimension(150, 45));

        // Changed PRIMARY_DARK to PRIMARY
        ModernButton addTransBtn = new ModernButton("+ Catat Transaksi Baru", ModernTheme.PRIMARY, ModernTheme.PRIMARY);
        addTransBtn.setPreferredSize(new Dimension(200, 45));
        addTransBtn.setForeground(Color.WHITE);
        addTransBtn.addActionListener(e -> tabbedPane.setSelectedIndex(1));

        quickActionPanel.add(transferButton);
        quickActionPanel.add(addTransBtn);

        scrollContent.add(quickActionPanel);

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 5, ModernTheme.SPACING_MD, 0));
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setPreferredSize(new Dimension(1000, 120));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        String symbol = LocalizationUtils.getCurrencySymbol();

        totalBalanceValueLabel = new JLabel(symbol + " 0");
        panel.add(createCard("Total Saldo", totalBalanceValueLabel, ModernTheme.PRIMARY));

        totalCashValueLabel = new JLabel(symbol + " 0");
        panel.add(createCard("Dompet Tunai", totalCashValueLabel, ModernTheme.WARNING));

        totalBankValueLabel = new JLabel(symbol + " 0");
        panel.add(createCard("Rekening Bank", totalBankValueLabel, ModernTheme.INFO));

        totalIncomeValueLabel = new JLabel(symbol + " 0");
        panel.add(createCard(LocalizationUtils.getString("card.income"), totalIncomeValueLabel, ModernTheme.SUCCESS));

        totalExpenseValueLabel = new JLabel(symbol + " 0");
        panel.add(createCard(LocalizationUtils.getString("card.expense"), totalExpenseValueLabel, ModernTheme.DANGER));

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

    // --- TRANSACTION ---
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(ModernTheme.BACKGROUND);
        centerWrapper.add(createTransactionFormPanel());
        panel.add(centerWrapper, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTransactionFormPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernTheme.BORDER),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD, ModernTheme.SPACING_SM,
                ModernTheme.SPACING_MD);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel title = new JLabel(LocalizationUtils.getString("trans.title"));
        title.setFont(ModernTheme.FONT_SUBTITLE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(createLabel(LocalizationUtils.getString("trans.account")), gbc);
        gbc.gridx = 1;
        accountCombo = new JComboBox<>();
        styleComboBox(accountCombo);
        panel.add(accountCombo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(createLabel(LocalizationUtils.getString("trans.type")), gbc);
        gbc.gridx = 1;
        transactionTypeCombo = new JComboBox<>(new String[] { LocalizationUtils.getString("trans.type.income"),
                LocalizationUtils.getString("trans.type.expense") });
        styleComboBox(transactionTypeCombo);
        panel.add(transactionTypeCombo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(createLabel(LocalizationUtils.getString("trans.category")), gbc);
        gbc.gridx = 1;
        transactionCategoryCombo = new JComboBox<>();
        styleComboBox(transactionCategoryCombo);
        panel.add(transactionCategoryCombo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(createLabel(LocalizationUtils.getString("trans.amount")), gbc);
        gbc.gridx = 1;
        transactionAmountField = new ModernTextField();
        panel.add(transactionAmountField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(createLabel(LocalizationUtils.getString("trans.desc")), gbc);
        gbc.gridx = 1;
        transactionDescriptionField = new ModernTextField();
        panel.add(transactionDescriptionField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);
        addTransactionButton = new ModernButton(LocalizationUtils.getString("trans.btn"), ModernTheme.PRIMARY,
                ModernTheme.PRIMARY_LIGHT);
        addTransactionButton.setPreferredSize(new Dimension(200, 40));
        panel.add(addTransactionButton, gbc);

        return panel;
    }

    // --- BUDGET (UPDATED WITH DELETE BUTTON) ---
    private JPanel createBudgetPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                ModernTheme.SPACING_LG));

        JLabel title = new JLabel("Daftar Anggaran Bulanan");
        title.setFont(ModernTheme.FONT_SUBTITLE);
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        String[] columnNames = {
                LocalizationUtils.getString("col.category"),
                LocalizationUtils.getString("col.budget"),
                LocalizationUtils.getString("col.used"),
                "Progress (%)",
                LocalizationUtils.getString("col.remaining"),
                LocalizationUtils.getString("col.status")
        };

        budgetTableModel = new DefaultTableModel(new Object[][] {}, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3)
                    return JProgressBar.class;
                return super.getColumnClass(columnIndex);
            }
        };

        budgetTable = new JTable(budgetTableModel);
        budgetTable.setFont(ModernTheme.FONT_BODY);
        budgetTable.setRowHeight(35);
        budgetTable.setShowGrid(false);
        budgetTable.setIntercellSpacing(new Dimension(0, 0));
        budgetTable.getColumnModel().getColumn(3).setCellRenderer(new ProgressRenderer());

        JScrollPane scrollPane = new JScrollPane(budgetTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(ModernTheme.BORDER));
        scrollPane.getViewport().setBackground(ModernTheme.SURFACE);
        panel.add(scrollPane, BorderLayout.CENTER);

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ModernTheme.BACKGROUND);

        // Hapus Button
        deleteBudgetButton = new ModernButton("Hapus Budget", ModernTheme.DANGER, new Color(220, 100, 90));
        deleteBudgetButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(deleteBudgetButton);

        // Tambah Button
        addBudgetButton = new ModernButton(LocalizationUtils.getString("budget.add"), ModernTheme.SUCCESS,
                new Color(100, 200, 90));
        addBudgetButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(addBudgetButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel goalsListPanel;
    private java.util.function.Consumer<backend.models.FinancialGoal> onDepositGoal;

    private JPanel createGoalPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setBorder(new EmptyBorder(ModernTheme.SPACING_LG, ModernTheme.SPACING_LG, ModernTheme.SPACING_LG,
                ModernTheme.SPACING_LG));

        JLabel title = new JLabel("Target Tabungan Saya");
        title.setFont(ModernTheme.FONT_SUBTITLE);
        title.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        goalsListPanel = new JPanel();
        goalsListPanel.setLayout(new BoxLayout(goalsListPanel, BoxLayout.Y_AXIS));
        goalsListPanel.setBackground(ModernTheme.BACKGROUND);

        JScrollPane scrollPane = new JScrollPane(goalsListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(ModernTheme.BACKGROUND);
        panel.add(scrollPane, BorderLayout.CENTER);

        addGoalButton = new ModernButton(LocalizationUtils.getString("goal.add"), ModernTheme.WARNING,
                new Color(220, 180, 60));
        addGoalButton.setPreferredSize(new Dimension(200, 40));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(ModernTheme.BACKGROUND);
        btnPanel.add(addGoalButton);
        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void updateGoalList(List<backend.models.FinancialGoal> goals) {
        goalsListPanel.removeAll();

        if (goals.isEmpty()) {
            JLabel empty = new JLabel("Belum ada target tabungan.", SwingConstants.CENTER);
            empty.setFont(ModernTheme.FONT_BODY);
            goalsListPanel.add(empty);
        } else {
            for (backend.models.FinancialGoal goal : goals) {
                goalsListPanel.add(createGoalCard(goal));
                goalsListPanel.add(Box.createVerticalStrut(10));
            }
        }

        goalsListPanel.revalidate();
        goalsListPanel.repaint();
    }

    private JPanel createGoalCard(backend.models.FinancialGoal goal) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(ModernTheme.SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernTheme.BORDER),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(goal.getGoalName());
        nameLabel.setFont(ModernTheme.FONT_BODY_BOLD);

        String symbol = LocalizationUtils.getCurrencySymbol();
        JLabel amountLabel = new JLabel(String.format("Terkumpul: %s %,.0f / %s %,.0f",
                symbol, goal.getCurrentAmount(), symbol, goal.getTargetAmount()));
        amountLabel.setFont(ModernTheme.FONT_BODY);

        infoPanel.add(nameLabel);
        infoPanel.add(amountLabel);
        card.add(infoPanel, BorderLayout.CENTER);

        // Progress & Button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue((int) goal.getProgressPercentage());
        progressBar.setStringPainted(true);
        progressBar.setForeground(ModernTheme.SUCCESS);
        bottomPanel.add(progressBar, BorderLayout.CENTER);

        if (!goal.isCompleted()) {
            ModernButton depositBtn = new ModernButton("Isi Saldo", ModernTheme.PRIMARY, ModernTheme.PRIMARY_LIGHT);
            depositBtn.setPreferredSize(new Dimension(100, 30));
            depositBtn.addActionListener(e -> {
                if (onDepositGoal != null)
                    onDepositGoal.accept(goal);
            });
            JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btnWrapper.setOpaque(false);
            btnWrapper.setBorder(new EmptyBorder(0, 10, 0, 0)); // Margin-left
            btnWrapper.add(depositBtn);
            bottomPanel.add(btnWrapper, BorderLayout.EAST);
        } else {
            JLabel doneLabel = new JLabel("✅ Tercapai!");
            doneLabel.setForeground(ModernTheme.SUCCESS);
            doneLabel.setFont(ModernTheme.FONT_BODY_BOLD);
            JPanel lblWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            lblWrapper.setOpaque(false);
            lblWrapper.add(doneLabel);
            bottomPanel.add(lblWrapper, BorderLayout.EAST);
        }

        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    // --- HELPERS ---
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

    class ProgressRenderer extends DefaultTableCellRenderer {
        private final JProgressBar b = new JProgressBar(0, 100);

        public ProgressRenderer() {
            super();
            b.setOpaque(true);
            b.setStringPainted(true);
            b.setBorderPainted(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (value instanceof Integer) {
                int val = (int) value;
                b.setValue(val);
                b.setString(val + "%");
                if (val >= 100)
                    b.setForeground(ModernTheme.DANGER);
                else if (val >= 80)
                    b.setForeground(ModernTheme.WARNING);
                else
                    b.setForeground(ModernTheme.SUCCESS);
                b.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                return b;
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // --- PUBLIC METHODS ---
    public void setUserGreeting(String username) {
        userGreetingLabel.setText(LocalizationUtils.getString("msg.welcome") + ", " + username + "!");
    }

    public void updateDashboard(double totalBalance, double cash, double bank, double income, double expense) {
        String symbol = LocalizationUtils.getCurrencySymbol();

        totalBalanceValueLabel.setText(LocalizationUtils.formatCurrencyCompact(totalBalance));
        totalBalanceValueLabel.setToolTipText(String.format("%s %,.0f", symbol, totalBalance));

        totalCashValueLabel.setText(LocalizationUtils.formatCurrencyCompact(cash));
        totalCashValueLabel.setToolTipText(String.format("%s %,.0f", symbol, cash));

        totalBankValueLabel.setText(LocalizationUtils.formatCurrencyCompact(bank));
        totalBankValueLabel.setToolTipText(String.format("%s %,.0f", symbol, bank));

        totalIncomeValueLabel.setText(LocalizationUtils.formatCurrencyCompact(income));
        totalIncomeValueLabel.setToolTipText(String.format("%s %,.0f", symbol, income));

        totalExpenseValueLabel.setText(LocalizationUtils.formatCurrencyCompact(expense));
        totalExpenseValueLabel.setToolTipText(String.format("%s %,.0f", symbol, expense));
    }

    public void addTransferListener(java.awt.event.ActionListener l) {
        transferButton.addActionListener(l);
    }

    public void updateDashboardCharts(Map<String, Double> expenseData, Map<String, Double> incomeData) {
        if (expenseChart != null)
            expenseChart.setData(expenseData);
        if (incomePieChart != null)
            incomePieChart.setData(incomeData);
    }

    public void updateBudgetTable(List<Budget> budgets, CategoryService catService, Map<Integer, Double> periodSpent) {
        budgetTableModel.setRowCount(0);
        String symbol = LocalizationUtils.getCurrencySymbol();
        for (Budget b : budgets) {
            backend.models.Category cat = catService.getCategoryById(b.getCategoryId());
            String catName = (cat != null) ? cat.getCategoryName() : "Unknown";

            double spent = periodSpent.getOrDefault(b.getCategoryId(), 0.0);

            double remaining = b.getBudgetLimit() - spent;
            int percentage = (b.getBudgetLimit() > 0) ? (int) ((spent / b.getBudgetLimit()) * 100) : 0;

            String status = (percentage >= 100) ? "❌ Over" : (percentage >= 80 ? "⚠️ Warning" : "✅ Aman");
            budgetTableModel.addRow(new Object[] { catName, String.format("%s %,.0f", symbol, b.getBudgetLimit()),
                    String.format("%s %,.0f", symbol, spent), percentage,
                    String.format("%s %,.0f", symbol, remaining), status });
        }
    }

    public void setAvailableAccounts(List<String> accounts) {
        accountCombo.removeAllItems();
        for (String acc : accounts)
            accountCombo.addItem(acc);
    }

    public void setAvailableCategories(List<String> categories) {
        transactionCategoryCombo.removeAllItems();
        for (String cat : categories)
            transactionCategoryCombo.addItem(cat);
    }

    public String getSelectedAccount() {
        return (String) accountCombo.getSelectedItem();
    }

    public String getTransactionType() {
        return (String) transactionTypeCombo.getSelectedItem();
    }

    public int getTransactionTypeIndex() {
        return transactionTypeCombo.getSelectedIndex();
    } // Getter Index

    public String getTransactionCategory() {
        return (String) transactionCategoryCombo.getSelectedItem();
    }

    public String getTransactionAmount() {
        return transactionAmountField.getText();
    }

    public String getTransactionDescription() {
        return transactionDescriptionField.getText();
    }

    public int getSelectedBudgetRow() {
        return budgetTable.getSelectedRow();
    } // Getter Row Budget

    public void addTransactionListener(java.awt.event.ActionListener l) {
        addTransactionButton.addActionListener(l);
    }

    public void addTransactionTypeListener(java.awt.event.ActionListener l) {
        transactionTypeCombo.addActionListener(l);
    }

    public void addBudgetListener(java.awt.event.ActionListener l) {
        addBudgetButton.addActionListener(l);
    }

    public void addDeleteBudgetListener(java.awt.event.ActionListener l) {
        deleteBudgetButton.addActionListener(l);
    } // Listener Hapus

    public void addGoalListener(java.awt.event.ActionListener l) {
        addGoalButton.addActionListener(l);
    }

    public void setOnDepositGoal(java.util.function.Consumer<backend.models.FinancialGoal> l) {
        this.onDepositGoal = l;
    }

    public void clearTransactionForm() {
        transactionAmountField.setText("");
        transactionDescriptionField.setText("");
    }
}