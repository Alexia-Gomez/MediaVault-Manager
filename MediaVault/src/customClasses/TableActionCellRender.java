package customClasses;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class TableActionCellRender extends DefaultTableCellRenderer {
	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        PanelAction action = new PanelAction();
        
        action.setBackground(table.getSelectionBackground());
        if (!isSelected) {
            action.setBackground(Color.WHITE);
        } else {
            action.setBackground(table.getSelectionBackground());
        }
        return action;
	}	
}
