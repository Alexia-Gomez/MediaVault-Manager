package customClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import controllers.MoviesController;
import models.Movie;

public class MovieJDialog extends JDialog {

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
	private ArrayList<Movie> allMovies;
	private Movie selectedMovie = null;

	Fuentes tipoFuentes = new Fuentes();
	Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 17f);
	Font btn = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 14f);
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 12f);
	Font fieldtxt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 11f);

	public MovieJDialog(Frame owner) {
		super(owner, "Búsqueda de producto", true);
		setUndecorated(true);
		setSize(500,430);
		setLocationRelativeTo(owner);

		RoundedPanel prin = new RoundedPanel(20, Color.white, 2);
		prin.setLayout(null);
		setContentPane(prin);

		JLabel title = new JLabel("Búsqueda de título");
		title.setBounds(170, 30, 200, 50);
		title.setFont(titles);
		prin.add(title);

		JLabel label = new JLabel("Ingrese nombre o ID de la película:");
		label.setBounds(55, 85, 280, 20);
		label.setFont(txt);
		prin.add(label);

		barraBus.setBounds(55, 110, 280, 30);
		prin.add(barraBus);

		RoundedButton buscar = new RoundedButton("Buscar");
		buscar.setBounds(365, 110, 80, 30);
		buscar.setBackground(blue);
		buscar.setForeground(Color.white);
		buscar.setRadius(20);
		prin.add(buscar);

		btnSelect.setBounds(280, 370, 110, 30);
		btnSelect.setFont(btn);
		btnSelect.setRadius(15);
		btnSelect.setEnabled(false);
		btnSelect.setBackground(blue);
		btnSelect.setForeground(Color.white);
		prin.add(btnSelect);

		btnCancel.setBounds(140, 370, 100, 30);
		btnCancel.setFont(btn);
		btnCancel.setRadius(15);
		btnCancel.setBorderColor(border);
		btnCancel.setBackground(Color.white);
		btnCancel.setForeground(Color.black);
		prin.add(btnCancel);


		String[] cols = {"ID", "Título", "Estudio"};
		model = new DefaultTableModel(cols, 0) {
			@Override public boolean isCellEditable(int r, int c) { 
				return false; 
			}
		};



		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sorter = new TableRowSorter<>(model);
		table.setFont(fieldtxt);
		table.setRowSorter(sorter);
		table.setShowVerticalLines(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(160);
		table.setRowHeight(30);

		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.WHITE); 
		header.setForeground(Color.BLACK);
		header.setPreferredSize(new Dimension(header.getWidth(), 30));
		header.setFont(fieldtxt); 


		CustomScrollPane scroll = new CustomScrollPane();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		//scroll.getVerticalScrollBar().setUI(new CustomScrollBar());
		scroll.setViewportView(table);


		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, centerRenderer);


		RoundedPanel tableWrapper = new RoundedPanel(15, Color.white, 2);
		tableWrapper.setBounds(50, 160, 400, 185);
		tableWrapper.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		tableWrapper.setLayout(new BorderLayout());
		tableWrapper.add(scroll, BorderLayout.CENTER);
		prin.add(tableWrapper, BorderLayout.SOUTH);

		loadAllMovies();

		btnCancel.addActionListener(e -> dispose());
		btnSelect.addActionListener(e -> {
			int row = table.getSelectedRow();
			if (row >= 0) {
				int m = table.convertRowIndexToModel(row);
				selectedMovie = allMovies.get(m);
				dispose();
			}
		});

		buscar.addActionListener(e -> applyFilter());
		table.getSelectionModel().addListSelectionListener(e ->
		btnSelect.setEnabled(table.getSelectedRow()>=0));
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount()==2 && table.getSelectedRow()>=0){
					int mrow = table.convertRowIndexToModel(table.getSelectedRow());
					selectedMovie = allMovies.get(mrow);
					dispose();
				}
			}
		});

	}

	private void loadAllMovies() {
		try {
			allMovies = new MoviesController().get();
			for (Movie c : allMovies) {
				model.addRow(new Object[]{
						"#" + c.getProduct_id(),
						c.getTitle(),
						c.getStudio()
				});
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Error cargando películas:\n" + ex.getMessage(),
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

	public Movie showDialog() {
		setVisible(true);
		return selectedMovie;
	}
}
