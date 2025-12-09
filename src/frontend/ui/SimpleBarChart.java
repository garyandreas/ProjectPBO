package frontend.ui;

import frontend.theme.ModernTheme;
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class SimpleBarChart extends JPanel {
    private Map<String, Double> data;

    public SimpleBarChart() {
        setBackground(ModernTheme.SURFACE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public void setData(Map<String, Double> data) {
        this.data = data;
        repaint(); // Gambar ulang saat data berubah
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (data == null || data.isEmpty()) {
            g.setColor(ModernTheme.TEXT_SECONDARY);
            g.setFont(ModernTheme.FONT_BODY);
            String msg = "Belum ada data pengeluaran";
            FontMetrics fm = g.getFontMetrics();
            g.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. Hitung Skala
        double maxVal = data.values().stream().mapToDouble(Double::doubleValue).max().orElse(1.0);
        if (maxVal == 0)
            maxVal = 1;

        int width = getWidth();
        int height = getHeight();
        int padding = 40; // Ruang untuk label
        int barCount = data.size();

        // Lebar tiap batang dinamis
        int barWidth = Math.min(60, (width - (2 * padding)) / barCount - 10);
        int gap = (width - (2 * padding) - (barCount * barWidth)) / (barCount + 1);

        int x = padding + gap;
        int bottomY = height - padding;
        int chartHeight = height - (2 * padding);

        // 2. Gambar Grafik
        g2d.setFont(ModernTheme.FONT_CAPTION);

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            String category = entry.getKey();
            double value = entry.getValue();

            // Hitung tinggi batang
            int barHeight = (int) ((value / maxVal) * chartHeight);
            if (barHeight < 5 && value > 0)
                barHeight = 5; // Minimal height agar terlihat

            int y = bottomY - barHeight;

            // Gambar Batang
            g2d.setColor(ModernTheme.DANGER); // Merah untuk pengeluaran
            g2d.fillRoundRect(x, y, barWidth, barHeight, 10, 10);

            // Gambar Nilai di atas batang
            g2d.setColor(ModernTheme.TEXT_PRIMARY);
            String valStr = formatCompact(value);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(valStr, x + (barWidth - fm.stringWidth(valStr)) / 2, y - 5);

            // Gambar Label Kategori di bawah batang (truncate jika terlalu panjang)
            String label = category.length() > 8 ? category.substring(0, 6) + ".." : category;
            g2d.setColor(ModernTheme.TEXT_SECONDARY);
            g2d.drawString(label, x + (barWidth - fm.stringWidth(label)) / 2, bottomY + 15);

            x += barWidth + gap;
        }

        // Garis sumbu X
        g2d.setColor(ModernTheme.BORDER);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(padding, bottomY, width - padding, bottomY);
    }

    // Helper format angka pendek
    private String formatCompact(double val) {
        return backend.utils.LocalizationUtils.formatCurrencyCompact(val);
    }
}