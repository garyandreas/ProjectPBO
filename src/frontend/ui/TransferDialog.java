package frontend.ui;

import frontend.theme.ModernTheme;
import frontend.theme.ModernButton;
import frontend.theme.ModernTextField;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TransferDialog extends JDialog {
    private JComboBox<String> sourceAccountCombo;
    private JComboBox<String> targetAccountCombo;
    private ModernTextField amountField;
    private boolean confirmed = false;

    public TransferDialog(JFrame parent, List<String> accountNames) {
        super(parent, "Transfer Dana", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setBackground(ModernTheme.BACKGROUND);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ModernTheme.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Source Account
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(createLabel("Dari Akun:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        sourceAccountCombo = new JComboBox<>(accountNames.toArray(new String[0]));
        styleComboBox(sourceAccountCombo);
        panel.add(sourceAccountCombo, gbc);

        // Target Account
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createLabel("Ke Akun:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        targetAccountCombo = new JComboBox<>(accountNames.toArray(new String[0]));
        styleComboBox(targetAccountCombo);
        panel.add(targetAccountCombo, gbc);

        // Amount
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(createLabel("Jumlah Transfer:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        amountField = new ModernTextField();
        panel.add(amountField, gbc);

        add(panel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(ModernTheme.BACKGROUND);
        buttonPanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        ModernButton cancelButton = new ModernButton("Batal", ModernTheme.DANGER, Color.WHITE);
        cancelButton.addActionListener(e -> dispose());

        ModernButton confirmButton = new ModernButton("Transfer", ModernTheme.PRIMARY, Color.WHITE);
        confirmButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(ModernTheme.FONT_BODY_BOLD);
        label.setForeground(ModernTheme.TEXT_PRIMARY);
        return label;
    }

    private void styleComboBox(JComboBox<String> box) {
        box.setPreferredSize(new Dimension(300, 35));
        box.setFont(ModernTheme.FONT_BODY);
        box.setBackground(ModernTheme.SURFACE);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getSourceAccount() {
        return (String) sourceAccountCombo.getSelectedItem();
    }

    public String getTargetAccount() {
        return (String) targetAccountCombo.getSelectedItem();
    }

    public double getAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
