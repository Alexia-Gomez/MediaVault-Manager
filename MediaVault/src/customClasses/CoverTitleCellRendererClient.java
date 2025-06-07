package customClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import models.Client;
import models.Movie;

public class CoverTitleCellRendererClient extends JPanel implements TableCellRenderer{
	Fuentes tipoFuentes = new Fuentes();
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 11f);
	private JLabel photoClient;
	private JLabel titleLabel;

	public CoverTitleCellRendererClient() {
		setLayout(new BorderLayout(5, 0));
		setOpaque(true);

		photoClient = new JLabel();
		titleLabel = new JLabel();
		photoClient.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5)); 
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(txt);
		add(photoClient, BorderLayout.WEST);
		add(titleLabel, BorderLayout.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		int modelRow = table.convertRowIndexToModel(row);
		Client client = (Client) table.getModel().getValueAt(modelRow, 5);
		titleLabel.setText(client.getName());

		ImageIcon icon = ImageUtils.getCircularIcon(client.getPhoto(), 40);
		photoClient.setIcon(icon);

		if (isSelected) {
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());
		} else {
			setBackground(Color.WHITE);
			setForeground(Color.BLACK);
		}

		return this;
	}

}