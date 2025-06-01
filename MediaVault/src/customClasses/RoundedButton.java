package customClasses;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RoundedButton extends JButton{
	
	private Color color;
	private Color borderColor= null;
	private ImageIcon image= null;
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
	
	public void setImageIcon(ImageIcon icon) {
		this.image = icon;
		repaint();
	}

	public RoundedButton(String string) {
		setContentAreaFilled(false);
		setBorder(null);
		setForeground(Color.white);
		setText(string);
	}	
	
	public RoundedButton() {
		setContentAreaFilled(false);
		setBorder(null);
		setForeground(Color.white);
	}

	
	public void setBorderColor(Color color) {
		this.borderColor=color;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Shape roundRect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setClip(roundRect);

        if (image != null) {
            Image img = image.getImage();
            g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        } else {
            g2.setColor(getBackground());
            g2.fill(roundRect);
        }

        if (borderColor != null) {
            g2.setClip(null);
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1f));
            g2.draw(roundRect);
        }
		
		super.paintComponent(g2);
	}

}
