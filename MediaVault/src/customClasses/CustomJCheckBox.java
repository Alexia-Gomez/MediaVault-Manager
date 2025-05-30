package customClasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JCheckBox;

public class CustomJCheckBox extends JCheckBox{

	private final int border= 4;
	
	public CustomJCheckBox() {
		setOpaque(false);
		setBackground(new Color(153, 202, 247));
		
	}
	
	public void paint (Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int ly = (getHeight() - 16) / 2;
		
		if(isSelected()) {
			
			if(isEnabled()) {
				g2.setColor(getBackground());
			
			} else {
				g2.setColor(new Color(217, 217, 217));
			}
			g2.fillRoundRect(1, ly, 16, 16, border, border);
			
		} else {
			g2.setColor(new Color(217, 217, 217));
            g2.fillRoundRect(1, ly, 16, 16, border, border);
            g2.fillRoundRect(2, ly + 1, 14, 14, border, border);
		}
		
		g2.dispose();
	}
}
