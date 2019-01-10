package view;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private Color topColor;
    private Color buttomColor;

    public GradientPanel(Color topColor, Color buttomColor){
        this.topColor = topColor;
        this.buttomColor = buttomColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = topColor;
        Color color2 = buttomColor;
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}

