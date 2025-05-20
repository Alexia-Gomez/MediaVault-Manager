package customClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
	
	private Color backgroundColor;
    private int cornerRadius;
    private int shadowSize;

    public RoundedPanel(int radius, Color bgColor, int shadowSize) {
        super();
        this.cornerRadius = radius;
        this.backgroundColor = bgColor;
        this.shadowSize = shadowSize;
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(shadowSize, shadowSize, shadowSize, shadowSize));
    }
    
    public RoundedPanel(int radius, Color bgColor) {
    	super();
        cornerRadius = radius;
        backgroundColor = bgColor;
        setOpaque(false);
    }

    public RoundedPanel(int radius) {
        this(radius, null, 10);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int shadowAlpha = 60;
        for (int i = 0; i < shadowSize; i++) {
            int alpha = (shadowAlpha * (shadowSize - i)) / shadowSize;
            g2.setColor(new Color(167, 171, 168, alpha));
            g2.fillRoundRect(i, i,width - (2 * i),height - (2 * i),cornerRadius,cornerRadius);
        }

        if (backgroundColor != null) {
            g2.setColor(backgroundColor);
        } else {
            g2.setColor(getBackground());
        }

        g2.fillRoundRect(shadowSize,shadowSize,width - (2 * shadowSize), height - (2 * shadowSize),cornerRadius,cornerRadius );
        g2.dispose();
    }
}
