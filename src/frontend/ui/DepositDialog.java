package frontend.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import frontend.theme.*;

public class DepositDialog extends JDialog {
    private JComboBox<String> accountCombo;
    private JTextField amountField;
    private ModernButton saveButton;
    private ModernButton cancelButton;

    private boolean isConfirmed = false;

    public DepositDialog(Frame owner, List<String> accountNames) {
        super(owner, "Tambah Tabungan", true);
        setSize(400, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        initializeComponents(accountNames);
    }

    private void initializeComponents(List<String> accountNames) {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ModernTheme.BACKGROUND);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Account
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Ambil dari Akun:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        accountCombo = new JComboBox<>(accountNames.toArray(new String[0]));
        formPanel.add(accountCombo, gbc);

        // Amount
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Jumlah (Rp):"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        amountField = new JTextField();
        formPanel.add(amountField, gbc);

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
        if (accountCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Pilih akun sumber.");
            return false;
        }
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nominal harus angka valid & > 0.");
            return false;
        }
        return true;
    }

    public String getSelectedAccountName() {
        return (String) accountCombo.getSelectedItem();
    }

    public double getAmount() {
        return Double.parseDouble(amountField.getText());
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
