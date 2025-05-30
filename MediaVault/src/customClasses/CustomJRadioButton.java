package customClasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JRadioButton;

public class CustomJRadioButton extends JRadioButton {
	

	Color lightGray = new Color(153, 202, 247);
	
	public CustomJRadioButton() {
        setOpaque(false);
        setBackground(new Color(69, 124, 235));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
       
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int ly = (getHeight() - 16) / 2;
        
        if (isSelected()) {

        	g2.setColor(getBackground());
            g2.fillOval(1, ly, 16, 16);
            g2.fillOval(2, ly + 1, 14, 14);

            
        } else {

        	g2.setColor(lightGray);
            g2.fillOval(1, ly, 16, 16);
            g2.fillOval(2, ly + 1, 14, 14);
        }
        g2.dispose();
    }
	
}
