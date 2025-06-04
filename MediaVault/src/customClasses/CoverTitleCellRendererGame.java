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

import models.Game;
import models.Movie;

public class CoverTitleCellRendererGame extends JPanel implements TableCellRenderer{
	Fuentes tipoFuentes = new Fuentes();
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 11f);
	private JLabel coverLabel;
	private JLabel titleLabel;

	public CoverTitleCellRendererGame() {
		setLayout(new BorderLayout(5, 0));
		setOpaque(true);

		coverLabel = new JLabel();
		titleLabel = new JLabel();
		coverLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5)); 
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setVerticalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(txt);
		add(coverLabel, BorderLayout.WEST);
		add(titleLabel, BorderLayout.CENTER);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		int modelRow = table.convertRowIndexToModel(row);
		Game game = (Game) table.getModel().getValueAt(modelRow, 7);
		titleLabel.setText(game.getTitle());

		ImageIcon icon = game.getCircularIcon(40);
		coverLabel.setIcon(icon);

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
