package frontend.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import frontend.theme.*;

public class GoalDialog extends JDialog {
    private ModernTextField nameField;
    private ModernTextField targetField;
    // Composite Date Picker
    private JComboBox<Integer> dayCombo;
    private JComboBox<String> monthCombo;
    private JSpinner yearSpinner;

    private ModernButton saveButton;
    private ModernButton cancelButton;

    private boolean isConfirmed = false;

    public GoalDialog(Frame owner) {
        super(owner, "Buat Target Tabungan Baru", true);
        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initializeComponents();
    }

    private void initializeComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ModernTheme.BACKGROUND);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 15, 8, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nama Target:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        nameField = new ModernTextField();
        nameField.setPlaceholder("Masukkan Nama Target");
        nameField.setPreferredSize(new Dimension(200, ModernTheme.INPUT_HEIGHT + 5));
        formPanel.add(nameField, gbc);

        // Target Amount
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Target Nominal (Rp):"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        targetField = new ModernTextField();
        targetField.setPlaceholder("Masukkan Nominal (Rp)");
        targetField.setPreferredSize(new Dimension(200, ModernTheme.INPUT_HEIGHT + 5));
        formPanel.add(targetField, gbc);

        // Deadline
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Target Tanggal:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;

        // Date Picker Panel
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        datePanel.setBackground(ModernTheme.BACKGROUND);

        // Centered Renderer
        DefaultListCellRenderer centerRenderer = new DefaultListCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Day: 1-31
        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++)
            days[i] = i + 1;
        dayCombo = new JComboBox<>(days);
        dayCombo.setPreferredSize(new Dimension(65, ModernTheme.INPUT_HEIGHT + 8));
        dayCombo.setBackground(Color.WHITE);
        dayCombo.setBorder(BorderFactory.createLineBorder(ModernTheme.BORDER));
        dayCombo.setRenderer(centerRenderer);

        // Month: Names
        String[] months = { "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember" };
        monthCombo = new JComboBox<>(months);
        monthCombo.setPreferredSize(new Dimension(130, ModernTheme.INPUT_HEIGHT + 8));
        monthCombo.setBackground(Color.WHITE);
        monthCombo.setBorder(BorderFactory.createLineBorder(ModernTheme.BORDER));
        monthCombo.setRenderer(centerRenderer);

        // Set current month/day
        java.time.LocalDate today = java.time.LocalDate.now();
        dayCombo.setSelectedItem(today.getDayOfMonth());
        monthCombo.setSelectedIndex(today.getMonthValue() - 1);

        // Year: Spinner
        yearSpinner = new JSpinner(new SpinnerNumberModel(today.getYear(), 2020, 2100, 1));
        yearSpinner.setPreferredSize(new Dimension(90, ModernTheme.INPUT_HEIGHT + 8));
        yearSpinner.setBorder(BorderFactory.createLineBorder(ModernTheme.BORDER));
        JSpinner.NumberEditor yearEditor = new JSpinner.NumberEditor(yearSpinner, "#");
        yearEditor.getTextField().setHorizontalAlignment(SwingConstants.CENTER);
        yearEditor.getTextField().setBackground(Color.WHITE);
        yearSpinner.setEditor(yearEditor);

        datePanel.add(dayCombo);
        datePanel.add(monthCombo);
        datePanel.add(yearSpinner);

        formPanel.add(datePanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ModernTheme.BACKGROUND);

        saveButton = new ModernButton("Simpan", ModernTheme.PRIMARY, ModernTheme.PRIMARY_LIGHT);
        cancelButton = new ModernButton("Batal", ModernTheme.DANGER, Color.RED);

        saveButton.addActionListener(e -> {
            if (validateInput()) {
                isConfirmed = true;
                setVisible(false);
            }
        });

        cancelButton.addActionListener(e -> setVisible(false));

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama target tidak boleh kosong.");
            return false;
        }
        try {
            double amount = Double.parseDouble(targetField.getText());
            if (amount <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nominal harus angka valid & > 0.");
            return false;
        }

        // Validate Date validity (e.g. 31 Feb)
        try {
            int d = (Integer) dayCombo.getSelectedItem();
            int m = monthCombo.getSelectedIndex() + 1;
            int y = (Integer) yearSpinner.getValue();
            java.time.LocalDate.of(y, m, d); // validation check
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tanggal tidak valid (misal: 31 Feb).");
            return false;
        }

        return true;
    }

    public String getGoalName() {
        return nameField.getText();
    }

    public double getTargetAmount() {
        return Double.parseDouble(targetField.getText());
    }

    public LocalDateTime getTargetDate() {
        int d = (Integer) dayCombo.getSelectedItem();
        int m = monthCombo.getSelectedIndex() + 1;
        int y = (Integer) yearSpinner.getValue();
        return java.time.LocalDate.of(y, m, d).atStartOfDay();
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
