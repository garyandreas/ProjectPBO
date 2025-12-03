package frontend.theme;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Modern Button - Custom styling dengan rounded corners & smooth appearance
 */
public class ModernButton extends JButton {
    private Color backgroundColor;
    private Color hoverColor;
    private Color pressColor;
    private boolean isHovered = false;
    private boolean isPressed = false;
    
    public ModernButton(String text, Color bgColor, Color hoverColor) {
        super(text);
        this.backgroundColor = bgColor;
        this.hoverColor = hoverColor;
        this.pressColor = new Color(
            Math.max(bgColor.getRed() - 20, 0),
            Math.max(bgColor.getGreen() - 20, 0),
            Math.max(bgColor.getBlue() - 20, 0)
        );
        
        setupUI();
        setupListeners();
    }
    
    private void setupUI() {
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setFont(ModernTheme.FONT_BUTTON);
        setPreferredSize(new Dimension(120, ModernTheme.BUTTON_HEIGHT));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void setupListeners() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                isHovered = true;
                repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                isHovered = false;
                isPressed = false;
                repaint();
            }
            public void mousePressed(java.awt.event.MouseEvent e) {
                isPressed = true;
                repaint();
            }
            public void mouseReleased(java.awt.event.MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Determine color
        Color currentColor = backgroundColor;
        if (isPressed) currentColor = pressColor;
        else if (isHovered) currentColor = hoverColor;
        
        // Draw background dengan rounded corners
        int arcSize = ModernTheme.CORNER_RADIUS_MD * 2;
        g2d.setColor(currentColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);
        
        // Draw shadow jika hovered
        if (isHovered && !isPressed) {
            g2d.setColor(ModernTheme.SHADOW_DARK);
            g2d.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 1, arcSize, arcSize);
        }
        
        // Draw text
        super.paintComponent(g);
    }
}
