package customClasses;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageUtils {

	public ImageUtils() {
		
	}
	
	 public static byte[] imageToByte(JComponent parentComp, long maxFileBytes) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecciona una imagen");
        chooser.setFileFilter(new FileNameExtensionFilter("JPEG, PNG", "jpg", "jpeg", "png"));

        int result = chooser.showOpenDialog(parentComp);
        if (result != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File file = chooser.getSelectedFile();
        long fileSize = file.length();
        if (fileSize > maxFileBytes) {
            JOptionPane.showMessageDialog(parentComp,"Imagen muy grande","Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            BufferedImage original = ImageIO.read(file);
            if (original == null) {
                JOptionPane.showMessageDialog(parentComp,"No se pudo leer la imagen seleccionada.","Error",JOptionPane.ERROR_MESSAGE);
                return null;
            }

            int nuevoAncho   = parentComp.getWidth();
            int nuevoAltura  = (original.getHeight() * nuevoAncho) / original.getWidth();

            BufferedImage scaled = new BufferedImage(nuevoAncho, nuevoAltura, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = scaled.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(original, 0, 0, nuevoAncho, nuevoAltura, null);
            g2.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(scaled, "jpg", baos);
            baos.flush();
            byte[] bytes = baos.toByteArray();
            baos.close();

            return bytes;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parentComp,"Error al procesar la imagen: " + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
	
	 public static ImageIcon byteToImageEsc(byte[] imageBytes, JComponent component) {
        if (imageBytes == null || imageBytes.length == 0) {
        	JOptionPane.showMessageDialog(component,"No se pudo leer la imagen seleccionada.","Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        try {
            BufferedImage original = ImageIO.read(new java.io.ByteArrayInputStream(imageBytes));
            if (original == null) {
                return null;
            }

            BufferedImage scaled = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = scaled.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawImage(original, 0, 0, component.getWidth(), component.getHeight(), null);
            g2.dispose();

            return new ImageIcon(scaled);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
	
	
	public static ImageIcon getCoverAsIcon(byte[] image ,int w, int h) {
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
	
	public static ImageIcon getCircularIcon(byte[] image,int diameter) {
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
