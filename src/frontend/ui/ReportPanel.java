package frontend.ui;

import backend.models.Transaction;
import backend.utils.LocalizationUtils;
import frontend.theme.ModernButton;
import frontend.theme.ModernTheme;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ReportPanel extends JPanel {
    private ModernButton exportButton;
    private SimplePieChart expensePieChart;
    private SimpleBarChart summaryBarChart;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private List<Transaction> currentTransactions;

    // We no longer need local filter params since we use MainFrame's filter
    public static class FilterParams {
        public String periodType;
        public int month;
        public int year;

        public FilterParams(String p, int m, int y) {
            periodType = p;
            month = m;
            year = y;
        }
    }

    // Listener for filter changes (still used by controller, but triggered
    // externally)

    public ReportPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(ModernTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top: Export Button only (aligned right)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setBackground(ModernTheme.BACKGROUND);

        exportButton = new ModernButton("Export ke CSV", ModernTheme.INFO, Color.WHITE);
        exportButton.setPreferredSize(new Dimension(150, 40));
        exportButton.addActionListener(e -> exportToCSV());
        topPanel.add(exportButton);

        add(topPanel, BorderLayout.NORTH);

        // Center: Charts and List
        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        contentPanel.setBackground(ModernTheme.BACKGROUND);

        contentPanel.add(createChartsPanel());
        contentPanel.add(createTransactionListPanel());

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createChartsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 20));
        panel.setBackground(ModernTheme.BACKGROUND);

        // EXPENSE PIE CHART
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(ModernTheme.SURFACE);
        JLabel title1 = new JLabel("Distribusi Pengeluaran", SwingConstants.CENTER);
        title1.setFont(ModernTheme.FONT_SUBTITLE);
        title1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        leftPanel.add(title1, BorderLayout.NORTH);

        expensePieChart = new SimplePieChart();
        leftPanel.add(expensePieChart, BorderLayout.CENTER);

        // SUMMARY BAR CHART
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(ModernTheme.SURFACE);
        JLabel title2 = new JLabel("Ringkasan Pemasukan & Pengeluaran", SwingConstants.CENTER);
        title2.setFont(ModernTheme.FONT_SUBTITLE);
        title2.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        rightPanel.add(title2, BorderLayout.NORTH);

        summaryBarChart = new SimpleBarChart();
        rightPanel.add(summaryBarChart, BorderLayout.CENTER);

        panel.add(leftPanel);
        panel.add(rightPanel);

        return panel;
    }

    private JPanel createTransactionListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.SURFACE);

        JLabel title = new JLabel("Arus Kas (Mutasi)", SwingConstants.LEFT);
        title.setFont(ModernTheme.FONT_SUBTITLE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panel.add(title, BorderLayout.NORTH);

        String[] columns = { "Tanggal", "Kategori", "Deskripsi", "Jumlah" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionTable = new JTable(tableModel);
        transactionTable.setRowHeight(30);
        transactionTable.setFont(ModernTheme.FONT_BODY);
        transactionTable.getTableHeader().setFont(ModernTheme.FONT_BODY_BOLD);
        transactionTable.setShowVerticalLines(false);
        transactionTable.setGridColor(new Color(230, 230, 230));

        // Custom Renderer for Amount
        transactionTable.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof String) {
                    String s = (String) value;
                    if (s.contains("-")) {
                        setForeground(ModernTheme.DANGER);
                    } else {
                        setForeground(ModernTheme.SUCCESS);
                    }
                    setHorizontalAlignment(SwingConstants.RIGHT);
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(transactionTable);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    public void setOnFilterListener(java.util.function.Consumer<FilterParams> listener) {
        // this.onFilterChanged = listener; // Disabled
    }

    public void updateData(List<Transaction> transactions, Map<String, Double> expenseData, double totalIncome,
            double totalExpense) {
        this.currentTransactions = transactions;

        // 1. Update Charts
        expensePieChart.setData(expenseData);

        Map<String, Double> summaryData = new HashMap<>();
        summaryData.put("Pemasukan", totalIncome);
        summaryData.put("Pengeluaran", totalExpense);
        summaryBarChart.setData(summaryData);

        // 2. Update Table
        updateTable(transactions);
    }

    private void updateTable(List<Transaction> transactions) {
        tableModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Transaction t : transactions) {
            t.getTransactionDate().format(fmt);
            // Category name? We need Category Service or pass names?
            // To simplify, we can assume ApplicationController passes fully populated
            // objects or we fetch names there.
            // Wait, Transaction only has categoryId.
            // We need a helper or just display ID? No, user needs names.
            // Let's rely on ApplicationController to pass a DTO or we modify this method to
            // accept a Map<Id, Name>
            // OR simpler: ApplicationController can pass a List<Object[]> suitable for the
            // table?
            // For now, let's just use "Category" placeholder or modify
            // ApplicationController to resolve it.
            // BETTER: pass Map<Integer, String> categoryNames to updateData.

            // Re-evaluating: To save complexity, I will ask ApplicationController to fetch
            // Category Name
            // BUT Transaction object is standard.
            // Let's assume we can't easily get category name here without service.
            // I will default category name to "ID: " + id, OR better: Controller should
            // pass a List of ViewModels/Rows.

        }
    }

    // Changing signature to accept category map
    public void updateData(List<Transaction> transactions, Map<Integer, String> categoryNames,
            Map<String, Double> expenseData, double totalIncome, double totalExpense) {
        this.currentTransactions = transactions;
        expensePieChart.setData(expenseData);

        Map<String, Double> summaryData = new HashMap<>();
        summaryData.put("Pemasukan", totalIncome);
        summaryData.put("Pengeluaran", totalExpense);
        summaryBarChart.setData(summaryData);

        tableModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String symbol = LocalizationUtils.getCurrencySymbol();

        for (Transaction t : transactions) {
            String date = t.getTransactionDate().format(fmt);
            String catName = categoryNames.getOrDefault(t.getCategoryId(), "Unknown");
            String desc = t.getDescription();

            boolean isExpense = (t.getTransactionType() == Transaction.TransactionType.EXPENSE
                    || t.getTransactionType() == Transaction.TransactionType.TRANSFER);
            String amountStr = String.format("%s%s %,.0f", isExpense ? "-" : "+", symbol, t.getAmount());

            tableModel.addRow(new Object[] { date, catName, desc, amountStr });
        }
    }

    private void exportToCSV() {
        if (currentTransactions == null || currentTransactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk diekspor!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan CSV");
        fileChooser.setSelectedFile(new File("laporan_keuangan.csv"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(fileToSave)) {
                writer.println("Tanggal,Tipe,Kategori,Deskripsi,Jumlah,Notes");
                for (Transaction t : currentTransactions) {
                    writer.printf("%s,%s,%d,%s,%.2f,%s%n",
                            t.getTransactionDate().toString(),
                            t.getTransactionType(),
                            t.getCategoryId(), // Just ID for CSV is fine, or cleaner: avoid resolving name here to keep
                                               // simple
                            t.getDescription(),
                            t.getAmount(),
                            t.getNotes());
                }
                JOptionPane.showMessageDialog(this, "Data berhasil diekspor ke: " + fileToSave.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal mengekspor data: " + ex.getMessage());
            }
        }
    }

    // Used by Controller to trigger chart updates. Controller should now call
    // updateData() instead of updateCharts()
    public void updateCharts(Map<String, Double> expenseData, double totalIncome, double totalExpense) {
        // Legacy support if needed, or remove. keeping empty or redirecting?
        // Let's remove this method from Controller call and use updateData.
    }
}
