package customClasses;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class RoundedButton extends JButton{
	
	private Color color;
	private Color borderColor= null;
	private int radius = 0;
	
	public Color getColor() {
		return color;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public RoundedButton(String string) {
		setContentAreaFilled(false);
		setBorder(null);
		setForeground(Color.white);
		setText(string);
	}	

	
	public void setBorderColor(Color color) {
		this.borderColor=color;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(getBackground());
		g2.fillRoundRect(0,0, getWidth() , getHeight() , radius, radius);
		
		if (borderColor != null) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
		
		super.paintComponent(g2);
	}

}
