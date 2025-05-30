package customClasses;

import java.awt.Color;

import javax.swing.JComboBox;


public class CustomJComboBox extends JComboBox {

	public CustomJComboBox() {
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        setUI(CustomJComboBoxUI.createUI(this));
        setBorder(new RoundedJComboBox(15));
        setOpaque(false);
        setVisible(true);
        
    }
	
}
