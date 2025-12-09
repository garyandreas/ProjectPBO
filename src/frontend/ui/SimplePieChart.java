package frontend.ui;

import frontend.theme.ModernTheme;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.Map;
import javax.swing.*;

public class SimplePieChart extends JPanel {
    private Map<String, Double> data;
    // Warna-warna vibrant untuk chart
    private final Color[] colors = {
            new Color(59, 130, 246), // Blue
            new Color(239, 68, 68), // Red
            new Color(16, 185, 129), // Green
            new Color(245, 158, 11), // Yellow/Orange
            new Color(139, 92, 246), // Purple
            new Color(236, 72, 153), // Pink
            new Color(14, 165, 233), // Sky
            new Color(249, 115, 22), // Orange
            new Color(20, 184, 166), // Teal
            new Color(99, 102, 241) // Indigo
    };

    public SimplePieChart() {
        setBackground(ModernTheme.SURFACE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (data == null || data.isEmpty()) {
            drawEmptyState(g2d);
            return;
        }

        double total = data.values().stream().mapToDouble(Double::doubleValue).sum();
        if (total <= 0) {
            drawEmptyState(g2d);
            return;
        }

        int width = getWidth();
        int height = getHeight();
        int minSize = Math.min(width, height);
        int radius = (int) (minSize * 0.7) / 2;
        int cx = width / 2;
        int cy = height / 2;

        // Draw Pie Segments
        double currentAngle = 90; // Start at top
        int colorIdx = 0;

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            double value = entry.getValue();
            double angle = (value / total) * 360;

            g2d.setColor(colors[colorIdx % colors.length]);
            Arc2D arc = new Arc2D.Double(cx - radius, cy - radius, radius * 2, radius * 2, currentAngle, -angle,
                    Arc2D.PIE);
            g2d.fill(arc);

            // Draw Legend or Labels if needed?
            // For now let's draw a simple legend on the right if there's space, or
            // tooltips?
            // Let's keep it simple: Draw values inside if slice is big enough?
            // Actually, let's draw a hollow circle in the middle for "Donut" chart look,
            // clearer.

            currentAngle -= angle;
            colorIdx++;
        }

        // Donut Hole
        int holeRadius = radius / 2;
        g2d.setColor(getBackground());
        g2d.fillOval(cx - holeRadius, cy - holeRadius, holeRadius * 2, holeRadius * 2);

        // Draw Legend
        drawLegend(g2d, total);
    }

    private void drawEmptyState(Graphics2D g2d) {
        g2d.setColor(ModernTheme.TEXT_SECONDARY);
        g2d.setFont(ModernTheme.FONT_BODY);
        String msg = "Tidak ada data";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
    }

    private void drawLegend(Graphics2D g2d, double total) {
        // Draw legend at the bottom or corner
        // For simplicity, let's just make sure chart is centered and legend is drawn
        // overlay or separate.
        // Let's draw legend on the right side if width permits
        if (getWidth() > getHeight() + 150) {
            int lx = getWidth() - 150;
            int ly = 20;
            int colorIdx = 0;

            g2d.setFont(new Font("SansSerif", Font.PLAIN, 12));

            for (Map.Entry<String, Double> entry : data.entrySet()) {
                g2d.setColor(colors[colorIdx % colors.length]);
                g2d.fillRect(lx, ly, 10, 10);

                g2d.setColor(ModernTheme.TEXT_PRIMARY);
                String label = entry.getKey();
                if (label.length() > 15)
                    label = label.substring(0, 12) + "...";

                String pct = String.format("%.1f%%", (entry.getValue() / total) * 100);
                String text = label + " (" + pct + ")";

                g2d.drawString(text, lx + 15, ly + 9);

                ly += 20;
                colorIdx++;
                if (ly > getHeight() - 20)
                    break; // Don't overflow
            }
        }
    }
}
