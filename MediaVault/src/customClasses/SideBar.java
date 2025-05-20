package customClasses;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class SideBar extends JButton {
	Fuentes tipoFuentes = new Fuentes();
	Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", Font.BOLD, 16f);
	
	public SideBar(String text, Icon icon) {
		super("<html><center>" + text.replace(" ", "<br>") + "</center></html>", icon);
		setFont(titles);
		setIcon(new ImageIcon(((ImageIcon) icon).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setBackground(new Color(24, 130, 234));
		setForeground(Color.white);
		setBorder(null);	
    }
}
