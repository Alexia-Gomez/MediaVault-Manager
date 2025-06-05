package customClasses;

import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.border.Border;


public class CustomJComboBox extends JComboBox {

	public CustomJComboBox() {
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        setUI(CustomJComboBoxUI.createUI(this));
        setBorder(new RoundedJComboBox(15));
        setOpaque(false);
        setVisible(true);
        
    }
	
	public void setErrorBorder(boolean error) {
		Border currentBorder = getBorder();
		if (currentBorder instanceof RoundedJComboBox roundedBorder) {
			if (error) {
				roundedBorder.setBorderColor(Color.RED);
			} else {
				roundedBorder.setBorderColor(new Color(186, 186, 186));
			}
			repaint();
		}
	}
	
}
