package customClasses;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelDisplay extends JPanel{
	
	Fuentes tipoFuentes = new Fuentes();
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 11f);
	
	
	private final ImageIcon check = new ImageIcon(getClass().getResource("/images/check.png"));
	private final ImageIcon eliminar = new ImageIcon(getClass().getResource("/images/eliminar.png"));
	
	private final JButton yes = new JButton();
	private final JButton no = new JButton();
	
	public PanelDisplay(int stock) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setOpaque(true);
        
        configButton(yes, check, stock);
		configButton(no, eliminar, stock);
        
        if (stock == 0) {
            add(no);
        } else {
            add(yes);
        }

    }
	
	private void configButton(JButton button, Icon icon, int stock) {
		button.setIcon(new ImageIcon(((ImageIcon) icon).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setText(String.valueOf(stock));
		button.setContentAreaFilled(false);
		button.setFont(txt);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
	}
	
}
