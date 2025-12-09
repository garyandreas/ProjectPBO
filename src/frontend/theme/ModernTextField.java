package frontend.theme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Modern Text Field - Dengan border & styling minimalis
 */
public class ModernTextField extends JTextField {

    public ModernTextField() {
        this("");
    }

    public ModernTextField(String text) {
        super(text, 20);
        setupUI();
    }

    private void setupUI() {
        setFont(ModernTheme.FONT_BODY);
        setForeground(ModernTheme.TEXT_PRIMARY);
        setCaretColor(ModernTheme.PRIMARY);
        setPreferredSize(new Dimension(300, ModernTheme.INPUT_HEIGHT));

        // Custom border
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernTheme.BORDER, 1),
                BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                        ModernTheme.SPACING_SM, ModernTheme.SPACING_MD));
        setBorder(border);

        setBackground(ModernTheme.SURFACE);

        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {

                Border focusedBorder = BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ModernTheme.PRIMARY, 2),
                        BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                                ModernTheme.SPACING_SM, ModernTheme.SPACING_MD));
                setBorder(focusedBorder);
            }

            public void focusLost(java.awt.event.FocusEvent e) {

                Border border = BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ModernTheme.BORDER, 1),
                        BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                                ModernTheme.SPACING_SM, ModernTheme.SPACING_MD));
                setBorder(border);
            }
        });
    }

    private String placeholder;

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && placeholder != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(ModernTheme.TEXT_HINT);
            g2.setFont(getFont());
            int padding = ModernTheme.SPACING_MD + 1; // Match border inset
            int y = (getHeight() - g.getFontMetrics().getHeight()) / 2 + g.getFontMetrics().getAscent();
            g2.drawString(placeholder, padding, y);
        }
    }
}
