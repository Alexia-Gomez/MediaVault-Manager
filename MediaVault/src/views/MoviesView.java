package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;


import controllers.HomeController;
import controllers.MoviesController;
import customClasses.CustomJCheckBox;
import customClasses.CustomJComboBox;
import customClasses.CustomJRadioButton;
import customClasses.CustomScrollBar;
import customClasses.CustomScrollPane;
import customClasses.Fuentes;
import customClasses.IconCellRenderer;
import customClasses.RoundedButton;
import customClasses.RoundedJTextField;
import customClasses.RoundedPanel;
import customClasses.SideBar;
import models.Movie;
import models.MoviesModel;

public class MoviesView {
	
	RoundedPanel filtro;
	JPanel centro;

	Color blue = new Color(24, 130, 234);
	Color border = new Color(186, 186, 186);
	Color lightGray = new Color(117, 117, 117);
	Color gray = new Color(242, 242, 242);
	Color field = new Color(250, 250, 250);

	Fuentes tipoFuentes = new Fuentes();
	Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 17f);
	Font btn = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 14f);
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 12f);
	Font fieldtxt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 11f);

	ImageIcon lupa = new ImageIcon(MoviesView.class.getResource("/images/lupa.png"));
	ImageIcon mas = new ImageIcon(MoviesView.class.getResource("/images/mas.png"));
	ImageIcon filter = new ImageIcon(MoviesView.class.getResource("/images/filter.png"));
	ImageIcon arrow = new ImageIcon(MoviesView.class.getResource("/images/arrow.png"));
	ImageIcon iconoFrame = new ImageIcon(MoviesView.class.getResource("/images/iconoPrincipal.PNG"));
	ImageIcon edit = new ImageIcon(MoviesView.class.getResource("/images/edit.png"));
	ImageIcon descarga = new ImageIcon(MoviesView.class.getResource("/images/descarga.png"));
	ImageIcon delete = new ImageIcon(MoviesView.class.getResource("/images/eliminarW.png"));
	
	byte[] coverBinario = null;
	
	TableRowSorter<DefaultTableModel> buscador;
	RoundedJTextField searchBar;
	CustomJRadioButton classA, classB, classB15,classC;
    CustomJRadioButton scifi, adventure, horror, comedy, action,allGen,allClass;

	public MoviesView() {

	}

	public void movies() {
		// VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Películas");
		frame.setIconImage(iconoFrame.getImage());


		// PANEL LATERAL
		RoundedPanel sidepanel = new RoundedPanel(10, blue);
		sidepanel.setLocation(0, 0);
		sidepanel.setSize(128, 606);
		sidepanel.setLayout(new GridLayout(0, 1, 0, 0));
		frame.getContentPane().add(sidepanel);

		sidepanel.add(SideBar.inicio(frame));
		sidepanel.add(SideBar.clientes(frame));
		sidepanel.add(SideBar.nuevaOperacion(frame));
		sidepanel.add(SideBar.rentaCompra(frame));
		sidepanel.add(SideBar.juegos(frame));
		sidepanel.add(SideBar.peliculas(frame));


		//PANEL CENTRO
		centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);

		RoundedPanel titlePanel = new RoundedPanel(30, new Color(255, 255, 255));
		titlePanel.setBounds(151, 11, 100, 43);
		centro.add(titlePanel);
		titlePanel.setLayout(new BorderLayout(0, 0));

		JLabel titleLabel = new JLabel("Películas");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(titles);
		titlePanel.add(titleLabel);

		RoundedButton newMovie = new RoundedButton("Nueva película");
		newMovie.setIcon(new ImageIcon(((ImageIcon) mas).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		newMovie.setBounds(780, 11, 185, 43);
		newMovie.setIconTextGap(10);
		newMovie.setBackground(blue);
		newMovie.setFont(btn);
		newMovie.setRadius(30);
		newMovie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				newMovie();
			}
			
		});
		centro.add(newMovie);


		//TABLA (HOLDER)
		//TITLES
		JPanel tableTitles = new JPanel();
		tableTitles.setBounds(151, 144, 810, 29);
		tableTitles.setLayout(new GridLayout(0, 7, 0, 0));
		centro.add(tableTitles);

		JLabel title = new JLabel("Título");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(lightGray);
		title.setFont(txt);
		tableTitles.add(title);

		JLabel studio = new JLabel("Estudio");
		studio.setHorizontalAlignment(SwingConstants.CENTER);
		studio.setForeground(lightGray);
		studio.setFont(txt);
		tableTitles.add(studio);

		JLabel classification = new JLabel("Clasificación");
		classification.setHorizontalAlignment(SwingConstants.CENTER);
		classification.setForeground(lightGray);
		classification.setFont(txt);
		tableTitles.add(classification);

		JLabel releaseDate = new JLabel("Año");
		releaseDate.setHorizontalAlignment(SwingConstants.CENTER);
		releaseDate.setForeground(lightGray);
		releaseDate.setFont(txt);
		tableTitles.add(releaseDate);

		JLabel genre = new JLabel("Género");
		genre.setHorizontalAlignment(SwingConstants.CENTER);
		genre.setForeground(lightGray);
		genre.setFont(txt);
		tableTitles.add(genre);

		JLabel rent = new JLabel("Renta");
		rent.setHorizontalAlignment(SwingConstants.CENTER);
		rent.setForeground(lightGray);
		rent.setFont(txt);
		tableTitles.add(rent);

		JLabel sale = new JLabel("Venta");
		sale.setHorizontalAlignment(SwingConstants.CENTER);
		sale.setForeground(lightGray);
		sale.setFont(txt);
		tableTitles.add(sale);

		//TABLA
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(151, 184, 810, 322);
		tablePanel.setLayout(new BorderLayout(0, 0));
		tablePanel.setBackground(Color.white);
		centro.add(tablePanel);

		// Obtener datos de la BD
		MoviesModel model = new MoviesModel();
		ArrayList<Movie> movies = model.get();
		
		String[] columnNames = {"", "", "", "", "", "", "", ""};
		DefaultTableModel model1 = new DefaultTableModel(columnNames, 0){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};

		for (Movie movie : movies) {
		    model1.addRow(new Object[] {
		        movie.getTitle(),
		        movie.getStudio(),
		        movie.getClassification(),
		        movie.getRelease_date(),
		        movie.getGenre(),
		        movie.getRent_stock(),
		        movie.getSale_stock(),
		        movie
		    });
		}

		JTable table = new JTable(model1);
		table.setRowHeight(80);
		table.setShowVerticalLines(false);
		table.setTableHeader(null);
		table.setFont(fieldtxt);
		
		table.getColumnModel().getColumn(5).setCellRenderer(new IconCellRenderer());
		
		table.getColumnModel().getColumn(6).setCellRenderer(new IconCellRenderer());
		
		TableColumnModel cm = table.getColumnModel();
		cm.removeColumn(cm.getColumn(7));
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = table.rowAtPoint(e.getPoint());
		        int col = table.columnAtPoint(e.getPoint());

		        if (col == 0 && fila != -1) {
		            int modeloFila = table.convertRowIndexToModel(fila);
		            Movie movie = (Movie) model1.getValueAt(modeloFila, 7);
		            
		            frame.dispose();
		            viewMovie(movie);
		        }
			}
		});

		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		//toda la tabla centrada
		table.setDefaultRenderer(Object.class, centerRenderer);

		CustomScrollPane scrollPane = new CustomScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportView(table);		

		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBar());
		tablePanel.add(scrollPane);

		buscador = new TableRowSorter<>(model1);
		table.setRowSorter(buscador);

		//BARRA
		RoundedPanel barra = new RoundedPanel(30, new Color(255, 255, 255));
		barra.setBounds(151, 65, 810, 68);
		barra.setLayout(null);
		centro.add(barra);

		JLabel lupaIcon = new JLabel("");
		lupaIcon.setIcon(new ImageIcon(((ImageIcon) lupa).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		lupaIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lupaIcon.setBounds(20, 20, 30, 30);
		barra.add(lupaIcon);

		searchBar = new RoundedJTextField(20);
		searchBar.setBounds(65, 20, 520, 30);
		searchBar.setBackground(field);
		searchBar.setFont(txt);
		barra.add(searchBar);

		RoundedButton buscar = new RoundedButton("Buscar");
		buscar.setBounds(600, 20, 86, 30);
		buscar.setBackground(blue);
		buscar.setFont(btn);
		buscar.setRadius(20);
		buscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}

		});
		barra.add(buscar);

		filterPanel(centro);

		RoundedButton filtrar = new RoundedButton("Filtrar");
		filtrar.setIcon(new ImageIcon(((ImageIcon) filter).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		filtrar.setHorizontalTextPosition(SwingConstants.LEFT);
		filtrar.setHorizontalAlignment(SwingConstants.CENTER);
		filtrar.setBounds(700, 20, 86, 30);
		filtrar.setBackground(Color.white);
		filtrar.setForeground(Color.black);
		filtrar.setBorderColor(border);
		filtrar.setIconTextGap(5);
		filtrar.setRadius(20);
		filtrar.setFont(btn);
		filtrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				filtro.setVisible(true);
			}

		});
		barra.add(filtrar);

		// Mostrar datos
		/*for (Movie movie : movies) {
		    JLabel title = new JLabel(movie.getTitle(), SwingConstants.CENTER);
		    title.setFont(txt);
		    tablePanel.add(title);

		    JLabel studio = new JLabel(movie.getStudio(), SwingConstants.CENTER);
		    studio.setFont(txt);
		    tablePanel.add(studio);

		    JLabel classification = new JLabel(movie.getClassification(), SwingConstants.CENTER);
		    classification.setFont(txt);
		    tablePanel.add(classification);

		    JLabel releaseDate = new JLabel(movie.getRelease_date(), SwingConstants.CENTER);
		    releaseDate.setFont(txt);
		    tablePanel.add(releaseDate);

		    JLabel genre = new JLabel(movie.getGenre(), SwingConstants.CENTER);
		    genre.setFont(txt);
		    tablePanel.add(genre);

		    JLabel rent = new JLabel(String.valueOf(movie.getRent_stock()), SwingConstants.CENTER);
		    rent.setFont(txt);
		    tablePanel.add(rent);

		    JLabel sale = new JLabel(String.valueOf(movie.getRent_stock()), SwingConstants.CENTER);
		    sale.setFont(txt);
		    tablePanel.add(sale);
		}*/

	}

	public void newMovie() {
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Nueva película");
		frame.setIconImage(iconoFrame.getImage());

		//PANEL LATERAL
		RoundedPanel sidepanel = new RoundedPanel(10, blue);
		sidepanel.setLocation(0, 0);
		sidepanel.setSize(128, 606);
		sidepanel.setLayout(new GridLayout(0, 1, 0, 0));
		frame.getContentPane().add(sidepanel);

		sidepanel.add(SideBar.inicio(frame));
		sidepanel.add(SideBar.clientes(frame));
		sidepanel.add(SideBar.nuevaOperacion(frame));
		sidepanel.add(SideBar.rentaCompra(frame));
		sidepanel.add(SideBar.juegos(frame));
		sidepanel.add(SideBar.peliculas(frame));


		//PANEL CENTRO
		centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);

		RoundedButton titleButton = new RoundedButton("Nueva película");
		titleButton.setIcon(new ImageIcon(((ImageIcon) arrow).getImage().getScaledInstance(15, 20, Image.SCALE_SMOOTH)));
		titleButton.setBounds(151, 11, 200, 43);
		titleButton.setBackground(Color.white);
		titleButton.setForeground(Color.black);
		titleButton.setFont(titles);
		titleButton.setIconTextGap(20);
		titleButton.setRadius(20);
		titleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				movies();
			}

		});
		centro.add(titleButton);
		
		//ENTRADA DE DATOS
		RoundedPanel dataPanel = new RoundedPanel(30, new Color(255, 255, 255));
		dataPanel.setBounds(150, 110, 810, 400);
		dataPanel.setLayout(null);
		centro.add(dataPanel);
		
		RoundedButton foto = new RoundedButton();
		foto.setBounds(35, 20, 165, 185);
		foto.setBackground(gray);
		foto.setRadius(20);
		dataPanel.add(foto);
		
		foto.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooserImagen = new JFileChooser();
				chooserImagen.setDialogTitle("Selecciona una imagen");
                chooserImagen.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                        "JPEG, PNG", "jpg", "jpeg", "png"));
                int result = chooserImagen.showOpenDialog(null);
                
                if(result == JFileChooser.APPROVE_OPTION) {
                	File archivoSeleccionado = chooserImagen.getSelectedFile();
                	long tamaño = archivoSeleccionado.length();
                	
                	if (tamaño>5242880) {
                		JOptionPane.showMessageDialog(foto,"Imagen mayor a 2MB","Error", JOptionPane.ERROR_MESSAGE);
                		return;
                	}
                	else {
                		try {
                			BufferedImage imgOriginal = ImageIO.read(archivoSeleccionado);

                			int nuevoAncho = foto.getWidth()-20;
                			int nuevoAlto = (imgOriginal.getHeight() * nuevoAncho) / imgOriginal.getWidth();

                			BufferedImage imgEscalada = new BufferedImage(nuevoAncho, nuevoAlto, BufferedImage.TYPE_INT_RGB);
                			Graphics2D g2 = imgEscalada.createGraphics();
                			
                			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                			g2.drawImage(imgOriginal, 0, 0, nuevoAncho, nuevoAlto, null);
                			g2.dispose();

                			ByteArrayOutputStream baos = new ByteArrayOutputStream();
                			ImageIO.write(imgEscalada, "jpg", baos);
                			baos.flush();
                			coverBinario = baos.toByteArray();
                			baos.close();
                			
                			ImageIcon imagenReducida = new ImageIcon(imgEscalada);
                			int width = foto.getWidth()-20;
                    		int height = foto.getHeight()-20;
                    		
                    		JLabel labelImagen = new JLabel(imagenReducida);
                            labelImagen.setBounds(10, 10, width, height);
                            foto.removeAll();
                            foto.add(labelImagen);    
                            foto.revalidate();
                            foto.repaint();
                    		
                    	}catch(Exception x) {
                    		System.out.println(x.getMessage());
                    	}
                	}
                	
                }
			}
		});

		RoundedButton movieId = new RoundedButton("ID: #12345");
		movieId.setBounds(70, 250 ,100, 30);
		movieId.setBackground(gray);
		movieId.setForeground(Color.black);
		movieId.setBorderColor(border);
		movieId.setFont(txt);
		movieId.setRadius(20);
		dataPanel.add(movieId);
		
		JLabel titleLabel = new JLabel("Titulo de la película:");
		titleLabel.setBounds(237, 20, 250, 15);
		titleLabel.setFont(txt);
		dataPanel.add(titleLabel);
		
		RoundedJTextField movieTitle = new RoundedJTextField(20);
		movieTitle.setBounds(237, 40, 250, 30);
		movieTitle.setFont(fieldtxt);
		dataPanel.add(movieTitle);
		
		JLabel dateLabel = new JLabel("Fecha de estreno(AAAA-MM-DD):");
		dateLabel.setBounds(237, 85, 250, 15);
		dateLabel.setFont(txt);
		dataPanel.add(dateLabel);
		
		RoundedJTextField movieDate = new RoundedJTextField(20);
		movieDate.setBounds(237, 105, 250, 27);
		movieDate.setFont(fieldtxt);
		dataPanel.add(movieDate);
		
		JLabel rentStockLabel = new JLabel("Stock de renta:");
		rentStockLabel.setBounds(237, 215, 250, 15);
		rentStockLabel.setFont(txt);
		dataPanel.add(rentStockLabel);
		
		RoundedJTextField movieRentStock = new RoundedJTextField(20);
		movieRentStock.setBounds(237, 235, 250, 27);
		movieRentStock.setFont(fieldtxt);
		dataPanel.add(movieRentStock);
		
		JLabel rentLabel = new JLabel("Precio de renta:");
		rentLabel.setBounds(237, 280, 250, 15);
		rentLabel.setFont(txt);
		dataPanel.add(rentLabel);
		
		RoundedJTextField movieRent = new RoundedJTextField(20);
		movieRent.setBounds(237, 300, 250, 27);
		movieRent.setFont(fieldtxt);
		dataPanel.add(movieRent);
		
		JLabel dispLabel = new JLabel("Disponibilidad:");
		dispLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dispLabel.setBounds(237, 150, 250, 15);
		dispLabel.setFont(txt);
		dataPanel.add(dispLabel);
		
		CustomJCheckBox renta = new CustomJCheckBox();
		renta.setBounds(290, 170, 60, 27);
		renta.setText("Renta");
		renta.setFont(txt);
		dataPanel.add(renta);
		
		CustomJCheckBox venta = new CustomJCheckBox();
		venta.setBounds(380, 170, 60, 27);
		venta.setText("Venta");
		venta.setFont(txt);
		dataPanel.add(venta);
		
		
		JLabel studioLabel = new JLabel("Estudio:");
		studioLabel.setBounds(520, 20, 250, 15);
		studioLabel.setFont(txt);
		dataPanel.add(studioLabel);
		
		RoundedJTextField movieStudio = new RoundedJTextField(20);
		movieStudio.setBounds(520, 40, 250, 27);
		movieStudio.setFont(fieldtxt);
		dataPanel.add(movieStudio);
		
		JLabel classLabel = new JLabel("Clasificación:");
		classLabel.setBounds(520, 85, 250, 15);
		classLabel.setFont(txt);
		dataPanel.add(classLabel);
		
		CustomJComboBox movieClass = new CustomJComboBox();
		movieClass.setModel( new DefaultComboBoxModel( new String[] { "A", "B", "B-15", "C" }));
		movieClass.setBounds(520, 105, 250, 27);
		movieClass.setFont(fieldtxt);
		dataPanel.add(movieClass);
		
		JLabel genreLabel = new JLabel("Género:");
		genreLabel.setBounds(520, 150, 250, 15);
		genreLabel.setFont(txt);
		dataPanel.add(genreLabel);
		
		CustomJComboBox movieGenre = new CustomJComboBox();
		movieGenre.setModel( new DefaultComboBoxModel( new String[] { "Sci-fi", "Aventura", "Horror", "Comedia", "Acción" }));
		movieGenre.setBounds(520, 170, 250, 27);
		movieGenre.setFont(fieldtxt);
		dataPanel.add(movieGenre);
		
		JLabel saleStockLabel = new JLabel("Stock de venta:");
		saleStockLabel.setBounds(520, 215, 250, 15);
		saleStockLabel.setFont(txt);
		dataPanel.add(saleStockLabel);
		
		RoundedJTextField movieSaleStock = new RoundedJTextField(20);
		movieSaleStock.setBounds(520, 235, 250, 27);
		movieSaleStock.setFont(fieldtxt);
		dataPanel.add(movieSaleStock);
		
		JLabel saleLabel = new JLabel("Precio de venta:");
		saleLabel.setBounds(520, 280, 250, 15);
		saleLabel.setFont(txt);
		dataPanel.add(saleLabel);
		
		RoundedJTextField movieSale = new RoundedJTextField(20);
		movieSale.setBounds(520, 300, 250, 27);
		movieSale.setFont(fieldtxt);
		dataPanel.add(movieSale);
		
		RoundedButton guardar = new RoundedButton("Guardar película");
		guardar.setBounds(620, 355, 150, 30);
		guardar.setBackground(blue);
		guardar.setFont(txt);
		guardar.setRadius(20);
		dataPanel.add(guardar);
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rent_stock =  Integer.parseInt(movieRentStock.getText());
				int sale_stock =  Integer.parseInt(movieSaleStock.getText());
				
				double precioVenta = Double.parseDouble(movieSale.getText());
				double precioRenta = Double.parseDouble(movieRent.getText());

				Movie pelicula = new Movie(movieTitle.getText(), movieStudio.getText(), (String) movieClass.getSelectedItem(),
						movieDate.getText(), (String) movieGenre.getSelectedItem(), rent_stock, sale_stock,coverBinario,precioVenta,precioRenta);
				MoviesController mc = new MoviesController();
				
				if(mc.addMovie(pelicula)) {
					System.out.println("Se agrego una pelicula nueva");
				}
						
			}
		});

		
		RoundedButton cancelar = new RoundedButton("Cancelar");
		cancelar.setBounds(520, 355,80, 30);
		cancelar.setBackground(Color.white);
		cancelar.setForeground(Color.black);
		cancelar.setBorderColor(border);
		cancelar.setFont(txt);
		cancelar.setRadius(20);
		dataPanel.add(cancelar);
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MoviesController mc = new MoviesController();
				mc.movies();
			}
			
		});
		
	}
	
	public void viewMovie(Movie movie) {
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Detalles de película");
		frame.setIconImage(iconoFrame.getImage());

		//PANEL LATERAL
		RoundedPanel sidepanel = new RoundedPanel(10, blue);
		sidepanel.setLocation(0, 0);
		sidepanel.setSize(128, 606);
		sidepanel.setLayout(new GridLayout(0, 1, 0, 0));
		frame.getContentPane().add(sidepanel);

		sidepanel.add(SideBar.inicio(frame));
		sidepanel.add(SideBar.clientes(frame));
		sidepanel.add(SideBar.nuevaOperacion(frame));
		sidepanel.add(SideBar.rentaCompra(frame));
		sidepanel.add(SideBar.juegos(frame));
		sidepanel.add(SideBar.peliculas(frame));


		//PANEL CENTRO
		centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);

		RoundedButton titleButton = new RoundedButton("Ver película");
		titleButton.setIcon(new ImageIcon(((ImageIcon) arrow).getImage().getScaledInstance(15, 20, Image.SCALE_SMOOTH)));
		titleButton.setBounds(151, 11, 200, 43);
		titleButton.setBackground(Color.white);
		titleButton.setForeground(Color.black);
		titleButton.setFont(titles);
		titleButton.setIconTextGap(20);
		titleButton.setRadius(20);
		titleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				MoviesController hc = new MoviesController();
				hc.movies();
			}

		});
		centro.add(titleButton);
		
		RoundedPanel dataPanel = new RoundedPanel(30, new Color(255, 255, 255));
		dataPanel.setBounds(150, 110, 810, 400);
		dataPanel.setLayout(null);
		centro.add(dataPanel);

		RoundedButton foto = new RoundedButton();
		foto.setBounds(35, 20, 165, 185);
		foto.setBackground(gray);
		foto.setImageIcon(movie.getCoverAsIcon(foto.getWidth(), foto.getHeight()));
		foto.setRadius(20);
		dataPanel.add(foto);
		
		RoundedButton movieId = new RoundedButton();
		movieId.setBounds(70, 250 ,100, 30);
		movieId.setForeground(Color.black);
		movieId.setBorderColor(border);
		movieId.setBackground(gray);
		movieId.setText("ID: #"+String.valueOf(movie.product_id));
		movieId.setFont(txt);
		movieId.setRadius(20);
		dataPanel.add(movieId);
		
		JLabel titleLabel = new JLabel("Titulo de la película:");
		titleLabel.setBounds(237, 20, 250, 15);
		titleLabel.setFont(txt);
		dataPanel.add(titleLabel);
		
		RoundedJTextField movieTitle = new RoundedJTextField(20);
		movieTitle.setBounds(237, 40, 250, 30);
		movieTitle.setText(movie.getTitle());
		movieTitle.setFocusable(false);
		movieTitle.setFont(fieldtxt);
		dataPanel.add(movieTitle);
		
		JLabel dateLabel = new JLabel("Fecha de estreno:");
		dateLabel.setBounds(237, 85, 250, 15);
		dateLabel.setFont(txt);
		dataPanel.add(dateLabel);
		
		RoundedJTextField movieDate = new RoundedJTextField(20);
		movieDate.setBounds(237, 105, 250, 27);
		movieDate.setText(movie.release_date);
		movieDate.setFocusable(false);
		movieDate.setFont(fieldtxt);
		dataPanel.add(movieDate);
		
		JLabel rentStockLabel = new JLabel("Stock de renta:");
		rentStockLabel.setBounds(237, 215, 250, 15);
		rentStockLabel.setFont(txt);
		dataPanel.add(rentStockLabel);
		
		RoundedJTextField movieRentStock = new RoundedJTextField(20);
		movieRentStock.setBounds(237, 235, 250, 27);
		movieRentStock.setText(String.valueOf(movie.rent_stock));
		movieRentStock.setFocusable(false);
		movieRentStock.setFont(fieldtxt);
		dataPanel.add(movieRentStock);
		
		JLabel rentLabel = new JLabel("Precio de renta:");
		rentLabel.setBounds(237, 280, 250, 15);
		rentLabel.setFont(txt);
		dataPanel.add(rentLabel);
		
		RoundedJTextField movieRent = new RoundedJTextField(20);
		movieRent.setBounds(237, 300, 250, 27);
		movieRent.setText("$"+String.valueOf(movie.rent_price));
		movieRent.setFocusable(false);
		movieRent.setFont(fieldtxt);
		dataPanel.add(movieRent);
		
		JLabel dispLabel = new JLabel("Disponibilidad:");
		dispLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dispLabel.setBounds(237, 150, 250, 15);
		dispLabel.setFont(txt);
		dataPanel.add(dispLabel);
		
		CustomJCheckBox renta = new CustomJCheckBox();
		renta.setBounds(290, 170, 60, 27);
		renta.setText("Renta");
		renta.setFont(txt);
		dataPanel.add(renta);
		
		CustomJCheckBox venta = new CustomJCheckBox();
		venta.setBounds(380, 170, 60, 27);
		venta.setText("Venta");
		venta.setFont(txt);
		dataPanel.add(venta);
		
		JLabel studioLabel = new JLabel("Estudio:");
		studioLabel.setBounds(520, 20, 250, 15);
		studioLabel.setFont(txt);
		dataPanel.add(studioLabel);
		
		RoundedJTextField movieStudio = new RoundedJTextField(20);
		movieStudio.setBounds(520, 40, 250, 27);
		movieStudio.setText(movie.studio);
		movieStudio.setFocusable(false);
		movieStudio.setFont(fieldtxt);
		dataPanel.add(movieStudio);
		
		JLabel classLabel = new JLabel("Clasificación:");
		classLabel.setBounds(520, 85, 250, 15);
		classLabel.setFont(txt);
		dataPanel.add(classLabel);
		
		CustomJComboBox movieClass = new CustomJComboBox();
		movieClass.setModel( new DefaultComboBoxModel( new String[] { "A", "B", "B-15", "C" }));
		movieClass.setBounds(520, 105, 250, 27);
		movieClass.setFont(fieldtxt);
		movieClass.setEnabled(false);
		dataPanel.add(movieClass);
		
		JLabel genreLabel = new JLabel("Género:");
		genreLabel.setBounds(520, 150, 250, 15);
		genreLabel.setFont(txt);
		dataPanel.add(genreLabel);
		
		CustomJComboBox movieGenre = new CustomJComboBox();
		movieGenre.setModel( new DefaultComboBoxModel( new String[] { "Sci-fi", "Aventura", "Horror", "Comedia", "Acción" }));
		movieGenre.setBounds(520, 170, 250, 27);
		movieGenre.setFont(fieldtxt);
		movieGenre.setEnabled(false);
		dataPanel.add(movieGenre);
		
		JLabel saleStockLabel = new JLabel("Stock de venta:");
		saleStockLabel.setBounds(520, 215, 250, 15);
		saleStockLabel.setFont(txt);
		dataPanel.add(saleStockLabel);
		
		RoundedJTextField movieSaleStock = new RoundedJTextField(20);
		movieSaleStock.setBounds(520, 235, 250, 27);
		movieSaleStock.setText(String.valueOf(movie.sale_stock));
		movieSaleStock.setFocusable(false);
		movieSaleStock.setFont(fieldtxt);
		dataPanel.add(movieSaleStock);
		
		JLabel saleLabel = new JLabel("Precio de venta:");
		saleLabel.setBounds(520, 280, 250, 15);
		saleLabel.setFont(txt);
		dataPanel.add(saleLabel);
		
		RoundedJTextField movieSale = new RoundedJTextField(20);
		movieSale.setBounds(520, 300, 250, 27);
		movieSale.setText("$"+String.valueOf(movie.sale_price));
		movieSale.setFocusable(false);
		movieSale.setFont(fieldtxt);
		dataPanel.add(movieSale);
		
		RoundedButton descargar = new RoundedButton("Descargar ficha"); 
		descargar.setIcon(new ImageIcon(((ImageIcon) descarga).getImage().getScaledInstance(15, 17, Image.SCALE_SMOOTH)));
		descargar.setHorizontalTextPosition(SwingConstants.RIGHT);
		descargar.setIconTextGap(10);
		descargar.setBounds(570, 355, 150, 30);
		descargar.setBackground(blue);
		descargar.setFont(txt);
		descargar.setRadius(20);
		dataPanel.add(descargar);
		descargar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        generarPDF(movie);
		        JOptionPane.showMessageDialog(null, "PDF generado con éxito.");
		    }
		});


		
		RoundedButton eliminar = new RoundedButton("Eliminar película");
		eliminar.setIcon(new ImageIcon(((ImageIcon) delete).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		eliminar.setHorizontalTextPosition(SwingConstants.RIGHT);
		eliminar.setIconTextGap(10);
		eliminar.setBounds(40, 355, 160, 30);
		eliminar.setBackground(Color.red);
		eliminar.setFont(txt);
		eliminar.setRadius(20);
		dataPanel.add(eliminar);
		eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta película?", 
						"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					int productId = movie.getProduct_id(); 
					MoviesController mc = new MoviesController();

					if (mc.deleteMovie(productId)) {
						JOptionPane.showMessageDialog(null, "Película eliminada con éxito.");
						frame.dispose(); 
						movies(); 
					} else {
						JOptionPane.showMessageDialog(null, "Error al eliminar la película.", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		RoundedButton cancelar = new RoundedButton("Cancelar");
		cancelar.setBounds(237, 354,80, 30);
		cancelar.setBackground(Color.white);
		cancelar.setForeground(Color.black);
		cancelar.setBorderColor(border);
		cancelar.setFont(txt);
		cancelar.setRadius(20);
		dataPanel.add(cancelar);
		cancelar.setVisible(false);
		
		RoundedButton guardar = new RoundedButton("Guardar cambios");
		guardar.setBounds(350, 354, 135, 30);
		guardar.setBackground(blue);
		guardar.setFont(txt);
		guardar.setRadius(20);
		dataPanel.add(guardar);
		guardar.setVisible(false); 
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String title = movieTitle.getText();
					String releaseDate = movieDate.getText();
					int rentStock = Integer.parseInt(movieRentStock.getText());
					double rentPrice = Double.parseDouble(movieRent.getText().replace("$", ""));
					int saleStock = Integer.parseInt(movieSaleStock.getText());
					double salePrice = Double.parseDouble(movieSale.getText().replace("$", ""));
					String studio = movieStudio.getText();
					String classification = movieClass.getSelectedItem().toString();
					String genre = movieGenre.getSelectedItem().toString();
					boolean isRentAvailable = renta.isSelected();
					boolean isSaleAvailable = venta.isSelected();

					Movie updatedMovie = new Movie();
					updatedMovie.product_id = movie.product_id;
					updatedMovie.setTitle(title);
					updatedMovie.release_date = releaseDate;
					updatedMovie.rent_stock = rentStock;
					updatedMovie.rent_price = rentPrice;
					updatedMovie.sale_stock = saleStock;
					updatedMovie.sale_price = salePrice;
					updatedMovie.studio = studio;
					updatedMovie.classification = classification;
					updatedMovie.genre = genre;

					MoviesController controller = new MoviesController();
					boolean success = controller.updateMovie(updatedMovie, updatedMovie.product_id);

					if (success) {
						JOptionPane.showMessageDialog(null, "Película actualizada correctamente.");
						
						movieTitle.setFocusable(false);
						movieDate.setFocusable(false);
						movieRentStock.setFocusable(false);
						movieRent.setFocusable(false);
						movieStudio.setFocusable(false);
						movieClass.setEnabled(false);
						movieGenre.setEnabled(false);
						movieSaleStock.setFocusable(false);
						movieSale.setFocusable(false);
						descargar.setEnabled(true);
						cancelar.setVisible(false);
						guardar.setVisible(false);
						
					} else {
						JOptionPane.showMessageDialog(null, "Error al actualizar la película.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Verifica los campos numéricos (precio y stock).", "Entrada inválida", JOptionPane.WARNING_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Ocurrió un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		RoundedButton editar = new RoundedButton();
		editar.setIcon(new ImageIcon(((ImageIcon) edit).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		editar.setBounds(740, 355, 30, 30);
		editar.setBackground(Color.white);
		dataPanel.add(editar);
		editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				movieTitle.setFocusable(true);
				movieDate.setFocusable(true);
				movieRentStock.setFocusable(true);
				movieRent.setFocusable(true);
				movieStudio.setFocusable(true);
				movieClass.setEnabled(true);
				movieGenre.setEnabled(true);
				movieSaleStock.setFocusable(true);
				movieSale.setFocusable(true);
				descargar.setEnabled(false);
				cancelar.setVisible(true);
				guardar.setVisible(true);
			}
		});
	}

	public void filtrar() {
		String textoBarra = searchBar.getText();
		RowFilter<DefaultTableModel, Object> filtroTexto;
		if(textoBarra.isEmpty()) {
			filtroTexto = RowFilter.regexFilter(".*", 0);
		} else {
			filtroTexto = RowFilter.regexFilter("(?i)^" + Pattern.quote(textoBarra), 0);
		}
		
		String classSelec=null;
		RowFilter<DefaultTableModel, Object> filtroClass;
	    if (classA.isSelected())        
	    	classSelec = "A";
	    else if (classB.isSelected())   
	    	classSelec = "B";
	    else if (classB15.isSelected()) 
	    	classSelec = "B-15";
	    else if (classC.isSelected()) 
	    	classSelec = "C";
	    else if (allClass.isSelected())                            
	    	classSelec = "Todos"; 
	    
	    if (classSelec == null || classSelec.equals("Todos")  ) {
	    	filtroClass = RowFilter.regexFilter(".*", 2);
	    }
	    else {
	    	filtroClass = RowFilter.regexFilter("^" + Pattern.quote(classSelec) + "$", 2);
	    }
	    
	    String generoSelec =null;
	    RowFilter<DefaultTableModel, Object> filtroGenero;
	    if (scifi.isSelected())         
	    	generoSelec = "Sci-fi";
	    else if (adventure.isSelected()) 
	    	generoSelec = "Aventura";
	    else if (horror.isSelected())    
	    	generoSelec = "Horror";
	    else if (comedy.isSelected())    
	    	generoSelec = "Comedia";
	    else if (action.isSelected())    
	    	generoSelec = "Acción";
	    else if (allGen.isSelected())                             
	    	generoSelec = "Todos";
	    
	    if (generoSelec == null || generoSelec.equals("Todos")) {
	    	filtroGenero = RowFilter.regexFilter(".*", 4);
	    } else {
	    	filtroGenero = RowFilter.regexFilter("^" + Pattern.quote(generoSelec) + "$", 4);
	    }
		
	    List<RowFilter<DefaultTableModel, Object>> listaFiltros = new ArrayList<>();
	    listaFiltros.add(filtroTexto);
	    listaFiltros.add(filtroClass);
	    listaFiltros.add(filtroGenero);
	    RowFilter<DefaultTableModel, Object> filtroCombinado = RowFilter.andFilter(listaFiltros);
		buscador.setRowFilter(filtroCombinado);
	}
	
	
	public void filterPanel(JPanel centro) {
		filtro = new RoundedPanel(30, new Color(255, 255, 255),3);
		filtro.setBounds(625, 115, 340, 300);
		filtro.setLayout(null);
		filtro.setVisible(false);
		
		JLabel clasif = new JLabel("Clasificación");
		clasif.setBounds(40, 30, 100, 15);
		clasif.setFont(txt);
		filtro.add(clasif);
		
		classA = new CustomJRadioButton();
		classA.setBounds(35, 55, 50, 30);
		classA.setFont(fieldtxt);
		classA.setText("A");
		filtro.add(classA);
		
		classB = new CustomJRadioButton();
		classB.setBounds(140, 55, 50, 30);
		classB.setFont(fieldtxt);
		classB.setText("B");
		filtro.add(classB);
		
		classB15 = new CustomJRadioButton();
		classB15.setBounds(240, 55, 50, 30);
		classB15.setFont(fieldtxt);
		classB15.setText("B-15");
		filtro.add(classB15);
		
		classC = new CustomJRadioButton();
		classC.setBounds(140, 100, 50, 30);
		classC.setFont(fieldtxt);
		classC.setText("C");
		filtro.add(classC);
		
		allClass = new CustomJRadioButton();
		allClass.setBounds(240, 100, 100, 30);
		allClass.setFont(fieldtxt);
		allClass.setText("Todos");
		filtro.add(allClass);
		
		ButtonGroup grupoClass = new ButtonGroup();
		grupoClass.add(classA);
		grupoClass.add(classB);
		grupoClass.add(classB15);
		grupoClass.add(classC);
		grupoClass.add(allClass);
		
		
		JLabel gen = new JLabel("Género");
		gen.setBounds(40, 125, 100, 15);
		gen.setFont(txt);
		filtro.add(gen);
		
		scifi = new CustomJRadioButton();
		scifi.setBounds(35, 150, 100, 30);
		scifi.setFont(fieldtxt);
		scifi.setText("Sci-fi");
		filtro.add(scifi);
		
		adventure = new CustomJRadioButton();
		adventure.setBounds(140, 150, 100, 30);
		adventure.setFont(fieldtxt);
		adventure.setText("Aventura");
		filtro.add(adventure);
		
		horror = new CustomJRadioButton();
		horror.setBounds(240, 150, 100, 30);
		horror.setFont(fieldtxt);
		horror.setText("Horror");
		filtro.add(horror);
		
		comedy = new CustomJRadioButton();
		comedy.setBounds(140, 195, 100, 30);
		comedy.setFont(fieldtxt);
		comedy.setText("Comedia");
		filtro.add(comedy);
		
		action = new CustomJRadioButton();
		action.setBounds(35, 195, 100, 30);
		action.setFont(fieldtxt);
		action.setText("Acción");
		filtro.add(action);
		
		allGen = new CustomJRadioButton();
		allGen.setBounds(240, 195, 100, 30);
		allGen.setFont(fieldtxt);
		allGen.setText("Todos");
		filtro.add(allGen);
		
		ButtonGroup grupoGenero = new ButtonGroup();
		grupoGenero.add(scifi);
		grupoGenero.add(adventure);
		grupoGenero.add(horror);
		grupoGenero.add(comedy);
		grupoGenero.add(action);
		grupoGenero.add(allGen);
		
		RoundedButton cerrar = new RoundedButton("Cerrar");
		cerrar.setBounds(60, 240, 85, 30);
		cerrar.setRadius(20);
		cerrar.setBackground(field);
		cerrar.setForeground(Color.gray);
		cerrar.setBorderColor(border);
		cerrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				filtro.setVisible(false);
			}
			
		});
		filtro.add(cerrar);
		
		RoundedButton aplicar = new RoundedButton("Aplicar");
		aplicar.setBounds(200, 240, 85, 30);
		aplicar.setRadius(20);
		aplicar.setBackground(blue);
		aplicar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	 filtrar();
		    }
		});
		filtro.add(aplicar);
		centro.add(filtro);
		centro.setComponentZOrder(filtro, 0);
			
	}
	
	public void generarPDF(Movie movie) {
		String movieTitle = movie.getTitle();
	    String dest = "resources/files/"+movieTitle+"_ficha.pdf";

	    try {
	        PdfWriter writer = new PdfWriter(dest);
	        PdfDocument pdfDoc = new PdfDocument(writer);
	        Document document = new Document(pdfDoc);

	        document.add(new Paragraph("Ficha de Película").setFontSize(18));
	        document.add(new Paragraph("ID: " + movie.product_id));
	        document.add(new Paragraph("Título: " + movie.getTitle()));
	        document.add(new Paragraph("Fecha de estreno: " + movie.release_date));
	        document.add(new Paragraph("Precio renta: $" + movie.rent_price));
	        document.add(new Paragraph("Stock renta: " + movie.rent_stock));
	        document.add(new Paragraph("Precio venta: $" + movie.sale_price));
	        document.add(new Paragraph("Stock venta: " + movie.sale_stock));
	        document.add(new Paragraph("Estudio: " + movie.studio));
	        document.add(new Paragraph("Clasificación: " + movie.classification));
	        document.add(new Paragraph("Género: " + movie.genre));

	        document.close();

	        System.out.println("PDF generado correctamente: " + dest);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
