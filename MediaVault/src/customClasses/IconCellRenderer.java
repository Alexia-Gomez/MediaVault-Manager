package customClasses;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class IconCellRenderer extends DefaultTableCellRenderer {
	
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
        int stock = 0;
        if (value instanceof Integer) {
            stock = (int) value;
        }

        PanelDisplay panel = new PanelDisplay(stock);
        panel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
        return panel;
    }
	
}
