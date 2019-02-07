package view;

import javax.swing.*;
import java.awt.*;

/**
 * Extends JPanel and makes a JPanel with a gradient background.
 * @author Albin Frick
 */
public class GradientPanel extends JPanel {
    private Color topColor;
    private Color buttomColor;

    /**
     * Constructor of the class. Takes in two colors, bottom and top.
     * @param topColor - Color
     * @param bottomColor - Color
     */
    public GradientPanel(Color topColor, Color bottomColor){
        this.topColor = topColor;
        this.buttomColor = bottomColor;
    }

    /**
     * Over rides the paint component in the JPanel.
     * Blends the given colors and renders them to the panel.
     * @param g
     */
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

