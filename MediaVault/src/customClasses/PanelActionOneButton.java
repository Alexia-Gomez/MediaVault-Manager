package customClasses;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelActionOneButton extends JPanel{
	
	private final ImageIcon delete = new ImageIcon(getClass().getResource("/images/eliminar.png"));
	private final JButton cmdDelete = new JButton();
	

	public PanelActionOneButton() {
		int vgap = (80 - 20) / 2;
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, vgap));
		setOpaque(true);
		
		
		configButton(cmdDelete, delete);

		add(cmdDelete);
	}

	private void configButton(JButton button, ImageIcon icon) {
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setOpaque(false);
		Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		button.setIcon(new ImageIcon(scaled));
	}

	 public void initEvent(TableActionEvent event, int row) {
	        cmdDelete.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent ae) {
	                event.onDelete(row);
	            }
	        });
	    }

}
