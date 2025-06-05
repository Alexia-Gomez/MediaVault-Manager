package customClasses;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

public class RoundedJComboBox extends AbstractBorder{
	private final int radius;
	private Color borderColor = new Color(186, 186, 186);

	public RoundedJComboBox(int radius) {
		this.radius = radius;

	}

	public void setBorderColor(Color color) {
		this.borderColor = color;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(borderColor);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(5, 10, 5, 10);
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.set(5, 10, 5, 10);
		return insets;
	}
}
