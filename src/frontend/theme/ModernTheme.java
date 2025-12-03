package frontend.theme;

import java.awt.*;

/**
 * Modern Theme - Unified design system untuk aplikasi
 * Minimalis, contemporary, dengan konsistensi visual yang baik
 */
public class ModernTheme {
    
    // Color Palette - Modern & Professional
    public static final Color PRIMARY = new Color(59, 89, 152);        // Deep Blue
    public static final Color PRIMARY_LIGHT = new Color(66, 103, 178); // Lighter Blue
    public static final Color SECONDARY = new Color(0, 188, 212);      // Cyan
    public static final Color SUCCESS = new Color(76, 175, 80);        // Green
    public static final Color WARNING = new Color(255, 152, 0);        // Orange
    public static final Color DANGER = new Color(244, 67, 54);         // Red
    public static final Color INFO = new Color(33, 150, 243);          // Light Blue
    
    // Neutral Colors
    public static final Color BACKGROUND = new Color(248, 249, 250);   // Off-white
    public static final Color SURFACE = new Color(255, 255, 255);      // White
    public static final Color TEXT_PRIMARY = new Color(33, 33, 33);    // Dark Gray
    public static final Color TEXT_SECONDARY = new Color(117, 117, 117); // Medium Gray
    public static final Color TEXT_HINT = new Color(189, 189, 189);    // Light Gray
    public static final Color BORDER = new Color(224, 224, 224);       // Very Light Gray
    public static final Color DIVIDER = new Color(236, 236, 236);      // Divider Gray
    
    // Shadows
    public static final Color SHADOW_DARK = new Color(0, 0, 0, 30);
    public static final Color SHADOW_LIGHT = new Color(0, 0, 0, 15);
    
    // Fonts
    public static final Font FONT_TITLE_LARGE = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_CAPTION = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 12);
    
    // Spacing
    public static final int SPACING_XS = 4;
    public static final int SPACING_SM = 8;
    public static final int SPACING_MD = 12;
    public static final int SPACING_LG = 16;
    public static final int SPACING_XL = 24;
    public static final int SPACING_XXL = 32;
    
    // Border Radius (untuk reference, apply di JPanel subclass)
    public static final int CORNER_RADIUS_SM = 4;
    public static final int CORNER_RADIUS_MD = 8;
    public static final int CORNER_RADIUS_LG = 12;
    
    // Component Sizes
    public static final int BUTTON_HEIGHT = 40;
    public static final int INPUT_HEIGHT = 36;
    public static final int ICON_SIZE_SM = 18;
    public static final int ICON_SIZE_MD = 24;
    public static final int ICON_SIZE_LG = 32;
}
