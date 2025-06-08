package customClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
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
	
	Color blue = new Color(24, 130, 234);
	Color border = new Color(186, 186, 186);
	Color lightGray = new Color(117, 117, 117);
	Color gray = new Color(242, 242, 242);
	Color field = new Color(250, 250, 250);

    private final RoundedJTextField barraBus = new RoundedJTextField(20);
    private final RoundedButton btnSelect  = new RoundedButton("Seleccionar");
    private final RoundedButton btnCancel  = new RoundedButton("Cancelar");
    private final JButton btnSearch  = new JButton("Buscar");  
    private final JTable table;
    private final DefaultTableModel model;
    private final TableRowSorter<DefaultTableModel> sorter;
    private ArrayList<Client> allClients;
    private Client selectedClient = null;
    
    Fuentes tipoFuentes = new Fuentes();
    Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 17f);
	Font btn = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 14f);
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 12f);
	Font fieldtxt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 11f);

    public SearchJDialog(Frame owner) {
        super(owner, "Búsqueda de cliente", true);
        setUndecorated(true);
        setSize(500,480);
        setLocationRelativeTo(owner);
        
        RoundedPanel prin = new RoundedPanel(20);
        prin.setLayout(null);
        prin.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(prin);
        
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setOpaque(false);
        JLabel title = new JLabel("Búsqueda de Cliente");
        title.setBounds(100, 30, 100, 50);
        title.setFont(titles);
        title.setForeground(Color.black);
        header.add(title);
        prin.add(header);
        
        barraBus.setBounds(30, 100, 200, 30);
        barraBus.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        prin.add(barraBus);
        
        RoundedButton buscar = new RoundedButton("Buscar");
        buscar.setBounds(245, 100, 80, 30);
        prin.add(buscar);
        
        btnSelect.setBounds(300, 400, 100, 30);
        btnSelect.setFont(btn);
        btnSelect.setRadius(15);
        btnSelect.setEnabled(false);
        btnSelect.setBackground(blue);
        btnSelect.setForeground(Color.white);
        prin.add(btnSelect);
        
        btnCancel.setBounds(180, 400, 100, 30);
        btnCancel.setFont(btn);
        btnCancel.setRadius(15);
        btnCancel.setBackground(Color.white);
        btnCancel.setForeground(Color.black);
        prin.add(btnCancel);
        
        
        String[] cols = {"ID", "Nombre", "Correo"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        
        CustomScrollPane scroll = new CustomScrollPane();
        scroll.setBorder(null);
        scroll.setViewportView(table);
        
        RoundedPanel tableWrapper = new RoundedPanel(15);
        tableWrapper.setBounds(50, 160, 400, 200);
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        tableWrapper.setLayout(new BorderLayout());
        tableWrapper.add(scroll, BorderLayout.CENTER);
        prin.add(tableWrapper, BorderLayout.SOUTH);

        loadAllClients();
        
        btnCancel.addActionListener(e -> dispose());
        btnSelect.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int m = table.convertRowIndexToModel(row);
                selectedClient = allClients.get(m);
                dispose();
            }
        });
        
        buscar.addActionListener(e -> applyFilter());
        table.getSelectionModel().addListSelectionListener(e ->
            btnSelect.setEnabled(table.getSelectedRow()>=0)
        );
        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount()==2 && table.getSelectedRow()>=0){
                    int mrow = table.convertRowIndexToModel(table.getSelectedRow());
                    selectedClient = allClients.get(mrow);
                    dispose();
                }
            }
        });
        
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
        String term = barraBus.getText().trim();
        if (term.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            String regex = "(?i)" + Pattern.quote(term);
            RowFilter<DefaultTableModel, Object> rf =
                RowFilter.regexFilter(regex, 0, 1,2);
            sorter.setRowFilter(rf);
        }
    }

    public Client showDialog() {
        setVisible(true);
        return selectedClient;
    }
    
}
