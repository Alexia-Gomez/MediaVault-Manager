package customClasses;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class PanelAction extends JPanel{
	
	private final ImageIcon edit = new ImageIcon(getClass().getResource("/images/edit.png"));
	private final ImageIcon delete = new ImageIcon(getClass().getResource("/images/eliminar.png"));

	private final JButton cmdEdit = new JButton();
	private final JButton cmdDelete = new JButton();
	

	public PanelAction() {
		setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
		setOpaque(true);

		configButton(cmdEdit, edit);
		configButton(cmdDelete, delete);

		add(cmdEdit);
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
	        cmdEdit.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent ae) {
	                event.onEdit(row);
	            }
	        });
	        cmdDelete.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent ae) {
	                event.onDelete(row);
	            }
	        });
	    }

}
