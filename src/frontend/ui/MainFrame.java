package frontend.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Frame utama aplikasi
 * Menampilkan dashboard dan navigasi principal dengan UI yang responsif
 */
public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    
    // Dashboard
    private JLabel totalBalanceValueLabel;
    private JLabel totalIncomeValueLabel;
    private JLabel totalExpenseValueLabel;
    private JLabel totalSavingValueLabel;
    
    // Transaction Form
    private JComboBox<String> transactionTypeCombo;
    private JComboBox<String> transactionCategoryCombo;
    private JComboBox<String> accountCombo;
    private JTextField transactionAmountField;
    private JTextField transactionDescriptionField;
    private JButton addTransactionButton;
    
    // Budget
    private JTable budgetTable;
    private DefaultTableModel budgetTableModel;
    private JButton addBudgetButton;
    
    // Goals
    private JPanel goalsPanel;
    private JButton addGoalButton;
    
    // Report
    private JTextArea reportArea;
    
    public MainFrame() {
        setTitle("Aplikasi Keuangan Pribadi");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 12));
        
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
        
        add(tabbedPane);
    }
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Dashboard Keuangan Pribadi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));
        
        panel.add(createSummaryPanel());
        panel.add(Box.createVerticalGlue());
        return panel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4, 15, 0));
        
        totalBalanceValueLabel = new JLabel("Rp 0");
        panel.add(createCard("Total Saldo", totalBalanceValueLabel, new Color(76, 175, 80)));
        
        totalIncomeValueLabel = new JLabel("Rp 0");
        panel.add(createCard("Pemasukan Bulan Ini", totalIncomeValueLabel, new Color(33, 150, 243)));
        
        totalExpenseValueLabel = new JLabel("Rp 0");
        panel.add(createCard("Pengeluaran Bulan Ini", totalExpenseValueLabel, new Color(244, 67, 54)));
        
        totalSavingValueLabel = new JLabel("Rp 0");
        panel.add(createCard("Tabungan", totalSavingValueLabel, new Color(255, 152, 0)));
        
        return panel;
    }
    
    private JPanel createCard(String title, JLabel valueLabel, Color bgColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        
        return card;
    }
    
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Manajemen Transaksi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        panel.add(createTransactionFormPanel(), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createTransactionFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Tambah Transaksi"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Account
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Rekening:"), gbc);
        
        gbc.gridx = 1;
        accountCombo = new JComboBox<>();
        panel.add(accountCombo, gbc);
        
        // Type
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tipe:"), gbc);
        
        gbc.gridx = 1;
        transactionTypeCombo = new JComboBox<>(new String[]{"Pemasukan", "Pengeluaran"});
        transactionTypeCombo.addActionListener(e -> updateCategoryCombo());
        panel.add(transactionTypeCombo, gbc);
        
        // Category
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Kategori:"), gbc);
        
        gbc.gridx = 1;
        transactionCategoryCombo = new JComboBox<>();
        panel.add(transactionCategoryCombo, gbc);
        
        // Initialize categories
        updateCategoryCombo();
        
        // Amount
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Jumlah:"), gbc);
        
        gbc.gridx = 1;
        transactionAmountField = new JTextField(15);
        panel.add(transactionAmountField, gbc);
        
        // Description
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Deskripsi:"), gbc);
        
        gbc.gridx = 1;
        transactionDescriptionField = new JTextField(15);
        panel.add(transactionDescriptionField, gbc);
        
        // Submit Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        addTransactionButton = new JButton("Tambah Transaksi");
        addTransactionButton.setBackground(new Color(33, 150, 243));
        addTransactionButton.setForeground(Color.WHITE);
        addTransactionButton.setOpaque(true);
        addTransactionButton.setBorderPainted(true);
        addTransactionButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(addTransactionButton, gbc);
        
        return panel;
    }
    
    private JPanel createBudgetPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Manajemen Budget");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Table for budgets
        String[] columnNames = {"Kategori", "Budget", "Terpakai", "Sisa", "Status"};
        budgetTableModel = new DefaultTableModel(new Object[][] {}, columnNames);
        budgetTable = new JTable(budgetTableModel);
        budgetTable.setEnabled(false);
        
        panel.add(new JScrollPane(budgetTable), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        addBudgetButton = new JButton("Tambah Budget");
        buttonPanel.add(addBudgetButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createGoalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Target Tabungan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        goalsPanel = new JPanel();
        goalsPanel.setLayout(new BoxLayout(goalsPanel, BoxLayout.Y_AXIS));
        
        JLabel emptyLabel = new JLabel("Belum ada target tabungan. Tambahkan target baru!");
        emptyLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        goalsPanel.add(Box.createVerticalStrut(20));
        goalsPanel.add(emptyLabel);
        
        panel.add(new JScrollPane(goalsPanel), BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        addGoalButton = new JButton("Tambah Target");
        buttonPanel.add(addGoalButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createReportPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Laporan Keuangan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        reportArea = new JTextArea();
        reportArea.setText("LAPORAN KEUANGAN\n\nBelum ada transaksi.");
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        panel.add(new JScrollPane(reportArea), BorderLayout.CENTER);
        return panel;
    }
    
    // Event listener setters
    public void addTransactionListener(ActionListener listener) {
        addTransactionButton.addActionListener(listener);
    }
    
    public void addBudgetListener(ActionListener listener) {
        addBudgetButton.addActionListener(listener);
    }
    
    public void addGoalListener(ActionListener listener) {
        addGoalButton.addActionListener(listener);
    }
    
    // Transaction getters
    public String getSelectedAccount() {
        return (String) accountCombo.getSelectedItem();
    }
    
    public String getTransactionType() {
        return (String) transactionTypeCombo.getSelectedItem();
    }
    
    public String getTransactionCategory() {
        return (String) transactionCategoryCombo.getSelectedItem();
    }
    
    public double getTransactionAmount() {
        try {
            return Double.parseDouble(transactionAmountField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
            return 0;
        }
    }
    
    public String getTransactionDescription() {
        return transactionDescriptionField.getText();
    }
    
    public void clearTransactionForm() {
        transactionAmountField.setText("");
        transactionDescriptionField.setText("");
    }
    
    // Dashboard update
    public void updateDashboard(double totalBalance, double income, double expense, double saving) {
        DecimalFormat currencyFormat = new DecimalFormat("#,##0");
        
        if (totalBalanceValueLabel != null) {
            totalBalanceValueLabel.setText("Rp " + currencyFormat.format(totalBalance));
        }
        if (totalIncomeValueLabel != null) {
            totalIncomeValueLabel.setText("Rp " + currencyFormat.format(income));
        }
        if (totalExpenseValueLabel != null) {
            totalExpenseValueLabel.setText("Rp " + currencyFormat.format(expense));
        }
        if (totalSavingValueLabel != null) {
            totalSavingValueLabel.setText("Rp " + currencyFormat.format(saving));
        }
    }
    
    // Report update
    public void updateReport(String reportText) {
        reportArea.setText(reportText);
    }
    
    /**
     * Update kategori combo box berdasarkan tipe transaksi
     */
    private void updateCategoryCombo() {
        String selectedType = (String) transactionTypeCombo.getSelectedItem();
        transactionCategoryCombo.removeAllItems();
        
        if ("Pemasukan".equals(selectedType)) {
            // Income categories
            transactionCategoryCombo.addItem("Gaji");
            transactionCategoryCombo.addItem("Side-Work");
            transactionCategoryCombo.addItem("Bonus");
            transactionCategoryCombo.addItem("Jualan");
            transactionCategoryCombo.addItem("Investasi");
            transactionCategoryCombo.addItem("Lainnya");
        } else {
            // Expense categories
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
    
    /**
     * Set available accounts untuk dropdown
     */
    public void setAvailableAccounts(java.util.List<String> accountNames) {
        accountCombo.removeAllItems();
        for (String accountName : accountNames) {
            accountCombo.addItem(accountName);
        }
        System.out.println("[MainFrame] ✅ Loaded " + accountNames.size() + " accounts to dropdown");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
