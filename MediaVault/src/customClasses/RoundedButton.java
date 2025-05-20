package customClasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

public class RoundedButton extends JButton{
	
	private Color color;
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
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0,0, getWidth() , getHeight() , radius, radius);
		super.paintComponent(g2);
	}
}
