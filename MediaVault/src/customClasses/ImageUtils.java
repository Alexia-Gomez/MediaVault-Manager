package customClasses;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {

	public ImageUtils() {
		
	}
	
	public ImageIcon getCoverAsIcon(byte[] image ,int w, int h) {
	    if (image != null) {
	        try {
	            BufferedImage img = ImageIO.read(new ByteArrayInputStream(image));
	            Image scaledImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	            return new ImageIcon(scaledImg);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}
	
	public ImageIcon getCircularIcon(byte[] image,int diameter) {
	    if (image != null) {
	        try {
	            BufferedImage original = ImageIO.read(new ByteArrayInputStream(image));
	            Image scaled = original.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
	            BufferedImage circleBuffer = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);

	            Graphics2D g2 = circleBuffer.createGraphics();
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	            Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter);
	            g2.setClip(circle);
	            g2.drawImage(scaled, 0, 0, null);
	            g2.dispose();

	            return new ImageIcon(circleBuffer);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}

}
