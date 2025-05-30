package customClasses;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;


public class CustomJComboBoxUI extends BasicComboBoxUI {

	Fuentes tipoFuentes = new Fuentes();
	Font fieldtxt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 11f);

	private final Color gray = new Color(217, 217, 217);

	private JButton button;
	private final ImageIcon close = new ImageIcon(getClass().getResource("/images/downArrow.png"));
	private final ImageIcon open = new ImageIcon(getClass().getResource("/images/upArrow.png"));
	
	public static ComboBoxUI createUI(JComponent c) {
		return new CustomJComboBoxUI();
	}

	@Override 
	protected JButton createArrowButton() { 
		button = new JButton(); 
		button.setText("");
		button.setContentAreaFilled(false);
		button.setBorder(null);
		button.setIcon(new ImageIcon(((ImageIcon) close).getImage().getScaledInstance(27, 30, Image.SCALE_SMOOTH)));
		return button;
	}

	@Override
	public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
		g.setColor(Color.white);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	@Override
	protected ListCellRenderer<Object> createRenderer() {
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				list.setSelectionBackground(Color.white);
				setFont(fieldtxt);
				if (isSelected) {
					setBackground(gray);
					setForeground(Color.black);
				} else {
					setBackground(Color.white);
					setForeground(Color.black);
				}
				return this;
			}
		};
	}

	
	@Override
	public void installUI(JComponent c) {
	    super.installUI(c);
	    comboBox.setBorder(new RoundedJComboBox(15));
	    comboBox.addPopupMenuListener(new PopupMenuListener() {
	        @Override
	        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
	            
	        	if (arrowButton != null) {
	                arrowButton.setIcon(new ImageIcon(((ImageIcon) open).getImage().getScaledInstance(15, 10, Image.SCALE_SMOOTH)));
	            }
	        	
	        	ComboPopup popup = (ComboPopup) comboBox.getUI().getAccessibleChild(comboBox, 0);

	            if (popup instanceof BasicComboPopup basicPopup) {
	                JScrollPane scrollPane = (JScrollPane) basicPopup.getComponent(0);
	                scrollPane.getVerticalScrollBar().setUI(new CustomScrollBar());

	                JList<?> list = popup.getList();
	                list.setBorder(new RoundedJComboBox(15));
	                
	                JPopupMenu popupMenu = basicPopup;
	                popupMenu.setBorder(BorderFactory.createEmptyBorder());
	                popupMenu.setOpaque(false);
	            }
	        }

	        @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	        	if (arrowButton != null) {
	                arrowButton.setIcon(new ImageIcon(((ImageIcon) close).getImage().getScaledInstance(27, 30, Image.SCALE_SMOOTH)));
	            }
	        }
	        @Override public void popupMenuCanceled(PopupMenuEvent e) {
	        }
	    });
	}
}
	    
