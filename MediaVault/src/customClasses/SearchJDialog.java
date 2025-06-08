package customClasses;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controllers.ClientsController;
import models.Client;

public class SearchJDialog extends JDialog {

    private final JTextField txtSearch = new JTextField(20);
    private final JButton btnSelect  = new JButton("Seleccionar");
    private final JButton btnCancel  = new JButton("Cancelar");
    private final JButton btnSearch  = new JButton("Buscar");  
    private final JTable table;
    private final DefaultTableModel model;
    private final TableRowSorter<DefaultTableModel> sorter;
    private ArrayList<Client> allClients;
    private Client selectedClient = null;

    public SearchJDialog(Frame owner) {
        super(owner, "Búsqueda de cliente", true);

        // 1) Modelo y tabla
        String[] cols = {"ID", "Nombre", "Correo"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // 2) Carga inicial de todos los clientes
        loadAllClients();

        // 3) Layout
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        north.add(new JLabel("Buscar (ID o nombre):"));
        north.add(btnSearch);
        north.add(txtSearch);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnSelect.setEnabled(false);
        south.add(btnCancel);
        south.add(btnSelect);

        setLayout(new BorderLayout(10,10));
        add(north, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(owner);

        // 4) Listeners
        btnSearch.addActionListener(e -> applyFilter());

        // Seleccionar cliente
        btnSelect.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                selectedClient = allClients.get(modelRow);
                dispose();
            }
        });

        // Cancelar
        btnCancel.addActionListener(e -> dispose());

        // Habilitar botón solo con selección
        table.getSelectionModel().addListSelectionListener(e ->
            btnSelect.setEnabled(table.getSelectedRow() >= 0)
        );
    }

    private void loadAllClients() {
        try {
            allClients = new ClientsController().get();
            for (Client c : allClients) {
                model.addRow(new Object[]{
                    "#" + c.getClient_id(),
                    c.getName() + " " + c.getLast_name(),
                    c.getEmail()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error cargando clientes:\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void applyFilter() {
        String term = txtSearch.getText().trim();
        if (term.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            String regex = "(?i)" + Pattern.quote(term);
            // filtrar en columna 0 (ID) y 1 (Nombre)
            RowFilter<DefaultTableModel, Object> rf =
                RowFilter.regexFilter(regex, 0, 1,2);
            sorter.setRowFilter(rf);
        }
    }

    /**
     * Muestra el diálogo y devuelve el cliente elegido,
     * o null si se canceló.
     */
    public Client showDialog() {
        setVisible(true);
        return selectedClient;
    }
    
}
