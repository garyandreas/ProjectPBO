package frontend.theme;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Modern Text Field - Dengan border & styling minimalis
 */
public class ModernTextField extends JTextField {
    private boolean isFocused = false;
    
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
                                           ModernTheme.SPACING_SM, ModernTheme.SPACING_MD)
        );
        setBorder(border);
        
        setBackground(ModernTheme.SURFACE);
        
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                isFocused = true;
                Border focusedBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ModernTheme.PRIMARY, 2),
                    BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                                                   ModernTheme.SPACING_SM, ModernTheme.SPACING_MD)
                );
                setBorder(focusedBorder);
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                isFocused = false;
                Border border = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ModernTheme.BORDER, 1),
                    BorderFactory.createEmptyBorder(ModernTheme.SPACING_SM, ModernTheme.SPACING_MD,
                                                   ModernTheme.SPACING_SM, ModernTheme.SPACING_MD)
                );
                setBorder(border);
            }
        });
    }
}
