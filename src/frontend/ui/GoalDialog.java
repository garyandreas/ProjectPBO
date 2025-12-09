package frontend.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import frontend.theme.*;

public class GoalDialog extends JDialog {
    private JTextField nameField;
    private JTextField targetField;
    private JSpinner dateSpinner;
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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nama Target:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        nameField = new JTextField();
        formPanel.add(nameField, gbc);

        // Target Amount
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Target Nominal (Rp):"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        targetField = new JTextField();
        formPanel.add(targetField, gbc);

        // Deadline
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Target Tanggal:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        formPanel.add(dateSpinner, gbc);

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
        return true;
    }

    public String getGoalName() {
        return nameField.getText();
    }

    public double getTargetAmount() {
        return Double.parseDouble(targetField.getText());
    }

    public LocalDateTime getTargetDate() {
        return ((java.util.Date) dateSpinner.getValue()).toInstant()
                .atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
