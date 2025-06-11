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
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import controllers.GamesController;
import controllers.HomeController;
import controllers.MoviesController;
import customClasses.CoverTitleCellRenderer;
import customClasses.CoverTitleCellRendererGame;
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
import customClasses.Validaciones;
import models.Game;
import models.GamesModel;


public class GamesView {

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

	ImageIcon lupa = new ImageIcon(GamesView.class.getResource("/images/lupa.png"));
	ImageIcon mas = new ImageIcon(GamesView.class.getResource("/images/mas.png"));
	ImageIcon filter = new ImageIcon(GamesView.class.getResource("/images/filter.png"));
	ImageIcon arrow = new ImageIcon(GamesView.class.getResource("/images/arrow.png"));
	ImageIcon iconoFrame = new ImageIcon(LoginView.class.getResource("/images/iconoPrincipal.PNG"));
	ImageIcon edit = new ImageIcon(MoviesView.class.getResource("/images/edit.png"));
	ImageIcon descarga = new ImageIcon(MoviesView.class.getResource("/images/descarga.png"));
	ImageIcon delete = new ImageIcon(MoviesView.class.getResource("/images/eliminarW.png"));
	ImageIcon upImage = new ImageIcon(MoviesView.class.getResource("/images/upImage.png"));

	byte[] coverBinario = null;

	TableRowSorter<DefaultTableModel> buscador;
	RoundedJTextField searchBar;
	CustomJRadioButton xbox, Switch, ps4, allPlatforms;
	CustomJRadioButton sports, adventure, racing, shooter, action, allGen;

	public GamesView() {

	}

	public void games() {

		// VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Videojuegos");
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
		//sidepanel.add(SideBar.rentaCompra(frame));
		sidepanel.add(SideBar.juegos(frame));
		sidepanel.add(SideBar.peliculas(frame));


		//PANEL CENTRO
		centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);

		RoundedPanel titlePanel = new RoundedPanel(30, new Color(255, 255, 255));
		titlePanel.setBounds(151, 11, 120, 43);
		centro.add(titlePanel);
		titlePanel.setLayout(new BorderLayout(0, 0));

		JLabel titleLabel = new JLabel("Videojuegos");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(titles);
		titlePanel.add(titleLabel);

		RoundedButton newGame = new RoundedButton("Nuevo videojuego");
		newGame.setIcon(new ImageIcon(((ImageIcon) mas).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		newGame.setBounds(760, 11, 185, 43);
		newGame.setIconTextGap(10);
		newGame.setBackground(blue);
		newGame.setFont(btn);
		newGame.setRadius(30);
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				newGame();
			}

		});
		centro.add(newGame);

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
		barra.add(buscar);
		buscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				filtrar();
			}

		});
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
				//filtro.setVisible(true);
			}

		});
		barra.add(filtrar);


		//TABLA (HOLDER)
		//TITLES
		JPanel tableTitles = new JPanel();
		tableTitles.setBounds(151, 144, 810, 29);
		tableTitles.setLayout(new GridLayout(0, 7, 0, 0));
		centro.add(tableTitles);

		JLabel lblNewLabel_1 = new JLabel("Título");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(lightGray);
		lblNewLabel_1.setFont(txt);
		tableTitles.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Plataforma");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(lightGray);
		lblNewLabel_2.setFont(txt);
		tableTitles.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Clasificación");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(lightGray);
		lblNewLabel_3.setFont(txt);
		tableTitles.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Año");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setForeground(lightGray);
		lblNewLabel_4.setFont(txt);
		tableTitles.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Género");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setForeground(lightGray);
		lblNewLabel_5.setFont(txt);
		tableTitles.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Renta");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setForeground(lightGray);
		lblNewLabel_6.setFont(txt);
		tableTitles.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Venta");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setForeground(lightGray);
		lblNewLabel_7.setFont(txt);
		tableTitles.add(lblNewLabel_7);

		//TABLA
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(151, 184, 810, 322);
		tablePanel.setLayout(new BorderLayout(0, 0));
		tablePanel.setBackground(Color.white);
		centro.add(tablePanel);

		// Obtener datos de la BD
		GamesModel model = new GamesModel();
		ArrayList<Game> games = model.get();

		String[] columnNames = {"", "", "", "", "", "", "", ""};
		DefaultTableModel model1 = new DefaultTableModel(columnNames, 0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (Game game : games) {
			model1.addRow(new Object[] {
					game.getTitle(),
					game.getPlatform(),
					game.getClassification(),
					game.getRelease_date(),
					game.getGenre(),
					game.getRent_stock(),
					game.getSale_stock(),
					game
			});
		}

		JTable table = new JTable(model1);
		table.setRowHeight(80);
		table.setShowVerticalLines(false);
		table.setTableHeader(null);
		table.setFont(fieldtxt);

		table.getColumnModel().getColumn(0).setCellRenderer(new CoverTitleCellRendererGame());

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
					Game game = (Game) table.getModel().getValueAt(modeloFila, 7);

					frame.dispose();
					viewGame(game);
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
	}

	public void newGame() {
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Nuevo videojuego");
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
		//sidepanel.add(SideBar.rentaCompra(frame));
		sidepanel.add(SideBar.juegos(frame));
		sidepanel.add(SideBar.peliculas(frame));


		//PANEL CENTRO
		centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);

		RoundedButton titleButton = new RoundedButton("Nuevo videojuego");
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
				games();
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
		foto.setImageIcon(upImage);
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
                		JOptionPane.showMessageDialog(foto,"Imagen muy grande","Error", JOptionPane.ERROR_MESSAGE);
                		return;
                	}
                	else {
                		try {
                			BufferedImage imgOriginal = ImageIO.read(archivoSeleccionado);

                			int nuevoAncho = foto.getWidth();
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
                			int width = foto.getWidth();
                    		int height = foto.getHeight();
                    		foto.setIcon(imagenReducida);
                    		
                    	}catch(Exception x) {
                    		System.out.println(x.getMessage());
                    	}
                	}
                	
                }
			}
		});


		RoundedButton gameId = new RoundedButton("ID: #");
		gameId.setBounds(70, 250 ,100, 30);
		gameId.setBackground(gray);
		gameId.setForeground(Color.black);
		gameId.setBorderColor(border);
		gameId.setFont(txt);
		gameId.setRadius(20);
		dataPanel.add(gameId);

		JLabel titleLabel = new JLabel("Titulo del juego:");
		titleLabel.setBounds(237, 20, 250, 15);
		titleLabel.setFont(txt);
		dataPanel.add(titleLabel);

		RoundedJTextField gameTitle = new RoundedJTextField(20);
		gameTitle.setBounds(237, 40, 250, 30);
		gameTitle.addKeyListener(Validaciones.limite(30));
		gameTitle.setFont(fieldtxt);
		dataPanel.add(gameTitle);

		JLabel dateLabel = new JLabel("Fecha de lanzamiento(AAAA-MM-DD):");
		dateLabel.setBounds(237, 85, 250, 15);
		dateLabel.setFont(txt);
		dataPanel.add(dateLabel);

		RoundedJTextField gameDate = new RoundedJTextField(20);
		gameDate.setBounds(237, 105, 250, 27);
		gameDate.addKeyListener(Validaciones.fechas());
		gameDate.addKeyListener(Validaciones.limite(10));
		gameDate.setFont(fieldtxt);
		dataPanel.add(gameDate);

		JLabel rentStockLabel = new JLabel("Stock de renta:");
		rentStockLabel.setBounds(237, 215, 250, 15);
		rentStockLabel.setFont(txt);
		dataPanel.add(rentStockLabel);

		RoundedJTextField gameRentStock = new RoundedJTextField(20);
		gameRentStock.setBounds(237, 235, 250, 27);
		gameRentStock.addKeyListener(Validaciones.enteros());
		gameRentStock.addKeyListener(Validaciones.limite(3));
		gameRentStock.setFont(fieldtxt);
		gameRentStock.setEnabled(false);
		dataPanel.add(gameRentStock);

		JLabel rentLabel = new JLabel("Precio de renta:");
		rentLabel.setBounds(237, 280, 250, 15);
		rentLabel.setFont(txt);
		dataPanel.add(rentLabel);

		RoundedJTextField gameRent = new RoundedJTextField(20);
		gameRent.setBounds(237, 300, 250, 27);
		gameRent.addKeyListener(Validaciones.conDecimal());
		gameRent.addKeyListener(Validaciones.limite(10));
		gameRent.setFont(fieldtxt);
		gameRent.setEnabled(false);
		dataPanel.add(gameRent);

		JLabel dispLabel = new JLabel("Disponibilidad:");
		dispLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dispLabel.setBounds(237, 150, 250, 15);
		dispLabel.setFont(txt);
		dataPanel.add(dispLabel);

		JLabel platformLabel = new JLabel("Plataforma:");
		platformLabel.setBounds(520, 20, 250, 15);
		platformLabel.setFont(txt);
		dataPanel.add(platformLabel);

		RoundedJTextField gamePlatform = new RoundedJTextField(20);
		gamePlatform.setBounds(520, 40, 250, 27);
		gamePlatform.addKeyListener(Validaciones.limite(20));
		gamePlatform.setFont(fieldtxt);
		dataPanel.add(gamePlatform);

		JLabel classLabel = new JLabel("Clasificación:");
		classLabel.setBounds(520, 85, 250, 15);
		classLabel.setFont(txt);
		dataPanel.add(classLabel);

		CustomJComboBox gameClass = new CustomJComboBox();
		gameClass.setModel( new DefaultComboBoxModel( new String[] { "Selecciona", "E", "T", "M", "A" }));
		gameClass.setBounds(520, 105, 250, 27);
		gameClass.setFont(fieldtxt);
		dataPanel.add(gameClass);

		JLabel genreLabel = new JLabel("Género:");
		genreLabel.setBounds(520, 150, 250, 15);
		genreLabel.setFont(txt);
		dataPanel.add(genreLabel);

		CustomJComboBox gameGenre = new CustomJComboBox();
		gameGenre.setModel( new DefaultComboBoxModel( new String[] { "Selecciona", "Deportes", "Acciones", "Carreras", "Aventura", "Disparos", "Terror" }));
		gameGenre.setBounds(520, 170, 250, 27);
		gameGenre.setFont(fieldtxt);
		dataPanel.add(gameGenre);

		JLabel saleStockLabel = new JLabel("Stock de venta:");
		saleStockLabel.setBounds(520, 215, 250, 15);
		saleStockLabel.setFont(txt);
		dataPanel.add(saleStockLabel);

		RoundedJTextField gameSaleStock = new RoundedJTextField(20);
		gameSaleStock.setBounds(520, 235, 250, 27);
		gameSaleStock.addKeyListener(Validaciones.enteros());
		gameSaleStock.addKeyListener(Validaciones.limite(3));
		gameSaleStock.setFont(fieldtxt);
		gameSaleStock.setEnabled(false);
		dataPanel.add(gameSaleStock);

		JLabel saleLabel = new JLabel("Precio de venta:");
		saleLabel.setBounds(520, 280, 250, 15);
		saleLabel.setFont(txt);
		dataPanel.add(saleLabel);

		RoundedJTextField gameSale = new RoundedJTextField(20);
		gameSale.setBounds(520, 300, 250, 27);
		gameSale.addKeyListener(Validaciones.conDecimal());
		gameSale.addKeyListener(Validaciones.limite(10));
		gameSale.setFont(fieldtxt);
		gameSale.setEnabled(false);
		dataPanel.add(gameSale);

		CustomJCheckBox renta = new CustomJCheckBox();
		renta.setBounds(290, 170, 60, 27);
		renta.setText("Renta");
		renta.setFont(txt);
		dataPanel.add(renta);
		renta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean check = (e.getStateChange() == ItemEvent.SELECTED);
				gameRentStock.setEnabled(check);
				gameRent.setEnabled(check);
				if(!check) {
					gameRentStock.setText("");
					gameRent.setText("");
				}
			}

		});

		CustomJCheckBox venta = new CustomJCheckBox();
		venta.setBounds(380, 170, 60, 27);
		venta.setText("Venta");
		venta.setFont(txt);
		dataPanel.add(venta);
		venta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean check = (e.getStateChange() == ItemEvent.SELECTED);
				gameSaleStock.setEnabled(check);
				gameSale.setEnabled(check);
				if(!check) {
					gameRentStock.setText("");
					gameRent.setText("");
				}
			}

		});

		RoundedButton guardar = new RoundedButton("Guardar videojuego");
		guardar.setBounds(620, 355, 150, 30);
		guardar.setBackground(blue);
		guardar.setFont(txt);
		guardar.setRadius(20);
		dataPanel.add(guardar);
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean camposValidos = true;

				if (!Validaciones.validarCampoVacio(gameTitle)) camposValidos = false;
				if (!Validaciones.validarCampoVacio(gamePlatform)) camposValidos = false;
				if (!Validaciones.validarCampoVacio(gameDate)) camposValidos = false;

				if (!Validaciones.validarCombo(gameClass)) camposValidos = false;
				if (!Validaciones.validarCombo(gameGenre)) camposValidos = false;

				if (renta.isSelected()) {
					if (!Validaciones.validarCampoVacio(gameRentStock)) camposValidos = false;
					if (!Validaciones.validarCampoVacio(gameRent)) camposValidos = false;
				} 

				if (venta.isSelected()) {
					if (!Validaciones.validarCampoVacio(gameSaleStock)) camposValidos = false;
					if (!Validaciones.validarCampoVacio(gameSale)) camposValidos = false;
				}

				if(camposValidos) {	

					int rent_stock = renta.isSelected() ? Integer.parseInt(gameRentStock.getText()) : 0;
					int sale_stock = venta.isSelected() ? Integer.parseInt(gameSaleStock.getText()) : 0;

					double precioVenta = venta.isSelected() ? Double.parseDouble(gameSale.getText()) : 0.0;
					double precioRenta = renta.isSelected() ? Double.parseDouble(gameRent.getText()) : 0.0;

					Game videojuego = new Game(gameTitle.getText(), gamePlatform.getText(), (String) gameClass.getSelectedItem(),
							gameDate.getText(), (String) gameGenre.getSelectedItem(), rent_stock, sale_stock,coverBinario,precioVenta,precioRenta);

					GamesController gc = new GamesController();

					if(gc.addGame(videojuego)) {
						System.out.println("Se agrego un videojuego nuevo");
					}
					frame.dispose();
					games();
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
				GamesController gc = new GamesController();
				gc.games();
			}
			
		});

	}

	public void viewGame(Game game) {
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Detalles de videojuego");
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
		//sidepanel.add(SideBar.rentaCompra(frame));
		sidepanel.add(SideBar.juegos(frame));
		sidepanel.add(SideBar.peliculas(frame));


		//PANEL CENTRO
		centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);

		RoundedButton titleButton = new RoundedButton("Ver videojuego");
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
				GamesController gc = new GamesController();
				gc.games();
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
		try {
			foto.setImageIcon(game.getCoverAsIcon(foto.getWidth(), foto.getHeight()));
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
		foto.setRadius(20);
		foto.setEnabled(false);
		dataPanel.add(foto);
		foto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!foto.isEnabled()) return;
				
				JFileChooser chooserImagen = new JFileChooser();
				chooserImagen.setDialogTitle("Selecciona una imagen");
                chooserImagen.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                        "JPEG, PNG", "jpg", "jpeg", "png"));
                int result = chooserImagen.showOpenDialog(null);
                
                if(result == JFileChooser.APPROVE_OPTION) {
                	File archivoSeleccionado = chooserImagen.getSelectedFile();
                	long tamaño = archivoSeleccionado.length();
                	
                	if (tamaño>5242880) {
                		JOptionPane.showMessageDialog(foto,"Imagen muy grande","Error", JOptionPane.ERROR_MESSAGE);
                		return;
                	}
                	else {
                		try {
                			BufferedImage imgOriginal = ImageIO.read(archivoSeleccionado);

                			int nuevoAncho = foto.getWidth();
                			int nuevoAlto = (imgOriginal.getHeight() * nuevoAncho) / imgOriginal.getWidth();

                			BufferedImage imgEscalada = new BufferedImage(nuevoAncho, nuevoAlto, BufferedImage.TYPE_INT_RGB);
                			Graphics2D g2 = imgEscalada.createGraphics();
                			
                			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                			g2.drawImage(imgOriginal, 0, 0, nuevoAncho, nuevoAlto, null);
                			g2.dispose();

                			ByteArrayOutputStream baos = new ByteArrayOutputStream();
                			ImageIO.write(imgEscalada, "jpg", baos);
                			baos.flush();
                			byte[] coverBinario = baos.toByteArray();
                			baos.close();
                			
                			game.setCover(coverBinario);
                			
                			ImageIcon imagenReducida = new ImageIcon(imgEscalada);
                    		
                    		foto.setImageIcon(imagenReducida);
                            
                    	}catch(Exception x) {
                    		System.out.println(x.getMessage());
                    	}
                	}
                	
                }
				
			}
			
		});
		
		RoundedButton gameId = new RoundedButton();
		gameId.setBounds(70, 250 ,100, 30);
		gameId.setForeground(Color.black);
		gameId.setBorderColor(border);
		gameId.setBackground(gray);
		if(game.product_id>0) {gameId.setText("ID: #"+String.valueOf(game.product_id));}
		gameId.setFont(txt);
		gameId.setRadius(20);
		dataPanel.add(gameId);
		
		JLabel titleLabel = new JLabel("Titulo del videojuego:");
		titleLabel.setBounds(237, 20, 250, 15);
		titleLabel.setFont(txt);
		dataPanel.add(titleLabel);
		
		RoundedJTextField gameTitle = new RoundedJTextField(20);
		gameTitle.setBounds(237, 40, 250, 30);
		if(game.getTitle()!=null) gameTitle.setText(game.getTitle());
		gameTitle.addKeyListener(Validaciones.limite(30));
		gameTitle.setFocusable(false);
		gameTitle.setFont(fieldtxt);
		dataPanel.add(gameTitle);
		
		JLabel dateLabel = new JLabel("Fecha de estreno:");
		dateLabel.setBounds(237, 85, 250, 15);
		dateLabel.setFont(txt);
		dataPanel.add(dateLabel);
		
		RoundedJTextField gameDate = new RoundedJTextField(20);
		gameDate.setBounds(237, 105, 250, 27);
		if (game.getRelease_date()!=null)gameDate.setText(game.release_date);
		gameDate.addKeyListener(Validaciones.fechas());
		gameDate.addKeyListener(Validaciones.limite(10));
		gameDate.setFocusable(false);
		gameDate.setFont(fieldtxt);
		dataPanel.add(gameDate);
		
		CustomJCheckBox renta = new CustomJCheckBox();
		renta.setBounds(290, 170, 60, 27);
		renta.setEnabled(false);
		if (game.getRent_stock()>0) renta.setSelected(game.getRent_stock()>0);
		renta.setText("Renta");
		renta.setFont(txt);
		dataPanel.add(renta);
		
		CustomJCheckBox venta = new CustomJCheckBox();
		venta.setBounds(380, 170, 60, 27);
		venta.setEnabled(false);
		if(game.getSale_stock()>0) venta.setSelected(game.getSale_stock()>0);
		venta.setText("Venta");
		venta.setFont(txt);
		dataPanel.add(venta);
		
		JLabel rentStockLabel = new JLabel("Stock de renta:");
		rentStockLabel.setBounds(237, 215, 250, 15);
		rentStockLabel.setFont(txt);
		dataPanel.add(rentStockLabel);
		
		RoundedJTextField gameRentStock = new RoundedJTextField(20);
		gameRentStock.setBounds(237, 235, 250, 27);
		if(game.getRent_stock()>0) gameRentStock.setText(String.valueOf(game.rent_stock));
		gameRentStock.addKeyListener(Validaciones.enteros());
		gameRentStock.addKeyListener(Validaciones.limite(3));
		gameRentStock.setFocusable(false);
		gameRentStock.setFont(fieldtxt);
		dataPanel.add(gameRentStock);
		
		JLabel rentLabel = new JLabel("Precio de renta:");
		rentLabel.setBounds(237, 280, 250, 15);
		rentLabel.setFont(txt);
		dataPanel.add(rentLabel);
		
		RoundedJTextField gameRent = new RoundedJTextField(20);
		gameRent.setBounds(237, 300, 250, 27);
		if(game.rent_price>0)   gameRent.setText("$"+String.valueOf(game.rent_price));
		gameRent.addKeyListener(Validaciones.conDecimal());
		gameRent.addKeyListener(Validaciones.limite(10));
		gameRent.setFocusable(false);
		gameRent.setFont(fieldtxt);
		dataPanel.add(gameRent);
		
		JLabel dispLabel = new JLabel("Disponibilidad:");
		dispLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dispLabel.setBounds(237, 150, 250, 15);
		dispLabel.setFont(txt);
		dataPanel.add(dispLabel);
		
		JLabel platformLabel = new JLabel("Plataforma:");
		platformLabel.setBounds(520, 20, 250, 15);
		platformLabel.setFont(txt);
		dataPanel.add(platformLabel);
		
		RoundedJTextField gamePlatform = new RoundedJTextField(20);
		gamePlatform.setBounds(520, 40, 250, 27);
		if(game.getPlatform()!=null)   gamePlatform.setText(game.platform);
		gamePlatform.addKeyListener(Validaciones.limite(20));
		gamePlatform.setFocusable(false);
		gamePlatform.setFont(fieldtxt);
		dataPanel.add(gamePlatform);
		
		JLabel classLabel = new JLabel("Clasificación:");
		classLabel.setBounds(520, 85, 250, 15);
		classLabel.setFont(txt);
		dataPanel.add(classLabel);
		
		CustomJComboBox gameClass = new CustomJComboBox();
		gameClass.setModel( new DefaultComboBoxModel( new String[] { "Selecciona", "E", "T", "M", "A" }));
		gameClass.setBounds(520, 105, 250, 27);
		gameClass.setEnabled(false);
		if(game.getClassification()!=null)  gameClass.setSelectedItem(game.classification);
		gameClass.setFont(fieldtxt);
		dataPanel.add(gameClass);
		
		JLabel genreLabel = new JLabel("Género:");
		genreLabel.setBounds(520, 150, 250, 15);
		genreLabel.setFont(txt);
		dataPanel.add(genreLabel);
		
		CustomJComboBox gameGenre = new CustomJComboBox();
		gameGenre.setModel( new DefaultComboBoxModel( new String[] { "Selecciona", "Deportes", "Acción", "Carreras", "Aventura", "Disparos", "Terror" }));
		gameGenre.setBounds(520, 170, 250, 27);
		gameGenre.setFont(fieldtxt);
		gameGenre.setEnabled(false);
		if(game.getGenre()!=null)   gameGenre.setSelectedItem(game.genre);
		dataPanel.add(gameGenre);
		
		JLabel saleStockLabel = new JLabel("Stock de venta:");
		saleStockLabel.setBounds(520, 215, 250, 15);
		saleStockLabel.setFont(txt);
		dataPanel.add(saleStockLabel);
		
		RoundedJTextField gameSaleStock = new RoundedJTextField(20);
		gameSaleStock.setBounds(520, 235, 250, 27);
		if(game.getSale_stock()>0)  gameSaleStock.setText(String.valueOf(game.sale_stock));
		gameSaleStock.addKeyListener(Validaciones.enteros());
		gameSaleStock.addKeyListener(Validaciones.limite(3));
		gameSaleStock.setFocusable(false);
		gameSaleStock.setFont(fieldtxt);
		dataPanel.add(gameSaleStock);
		
		JLabel saleLabel = new JLabel("Precio de venta:");
		saleLabel.setBounds(520, 280, 250, 15);
		saleLabel.setFont(txt);
		dataPanel.add(saleLabel);
		
		RoundedJTextField gameSale = new RoundedJTextField(20);
		gameSale.setBounds(520, 300, 250, 27);
		if(game.getSale_price()>0)  gameSale.setText("$"+String.valueOf(game.sale_price));
		gameSale.addKeyListener(Validaciones.conDecimal());
		gameSale.addKeyListener(Validaciones.limite(10));
		gameSale.setFocusable(false);
		gameSale.setFont(fieldtxt);
		dataPanel.add(gameSale);
		
		
		renta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean check = (e.getStateChange() == ItemEvent.SELECTED);
				gameRentStock.setFocusable(check);
				gameRent.setFocusable(check);
			}
			
		});
		
		venta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean check = (e.getStateChange() == ItemEvent.SELECTED);
				gameSaleStock.setFocusable(check);
				gameSale.setFocusable(check);
			}
			
		});
		
		
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
		        generarPDF(game);
		        //JOptionPane.showMessageDialog(null, "PDF generado con éxito.");
		    }
		});


		
		RoundedButton eliminar = new RoundedButton("Eliminar videojuego");
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
				int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este videojuego?", 
						"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					int productId = game.getProduct_id(); 
					MoviesController mc = new MoviesController();

					if (mc.deleteMovie(productId)) {
						JOptionPane.showMessageDialog(null, "Videojuego eliminado con éxito.");
						frame.dispose(); 
						games(); 
					} else {
						JOptionPane.showMessageDialog(null, "Error al eliminar el videojuego.", 
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
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MoviesController mc = new MoviesController();
				frame.dispose();
				mc.movies();
			}
			
		});
		
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

					boolean camposValidos = true;
					
					if (!Validaciones.validarCampoVacio(gameTitle)) camposValidos = false;
					if (!Validaciones.validarCampoVacio(gamePlatform)) camposValidos = false;
					if (!Validaciones.validarCampoVacio(gameDate)) camposValidos = false;

					if (!Validaciones.validarCombo(gameClass)) camposValidos = false;
					if (!Validaciones.validarCombo(gameGenre)) camposValidos = false;

					if (renta.isSelected()) {
						if (!Validaciones.validarCampoVacio(gameRentStock)) camposValidos = false;
						if (!Validaciones.validarCampoVacio(gameRent)) camposValidos = false;
					} 

					if (venta.isSelected()) {
						if (!Validaciones.validarCampoVacio(gameSaleStock)) camposValidos = false;
						if (!Validaciones.validarCampoVacio(gameSale)) camposValidos = false;
					}

					if(camposValidos) {
						
						String title = gameTitle.getText();
						String releaseDate = gameDate.getText();
						
						int rentStock = renta.isSelected() ? Integer.parseInt(gameRentStock.getText()) : 0;
						double rentPrice = renta.isSelected() ? Double.parseDouble(gameRent.getText().replace("$", "")) : 0.0;
						int saleStock = venta.isSelected() ? Integer.parseInt(gameSaleStock.getText()) : 0;
						double salePrice = venta.isSelected() ? Double.parseDouble(gameSale.getText().replace("$", "")) : 0.0;
						
						String platform = gamePlatform.getText();
						String classification = gameClass.getSelectedItem().toString();
						String genre = gameGenre.getSelectedItem().toString();
						
						Game updatedGame = new Game();
						updatedGame.product_id = game.product_id;
						updatedGame.setTitle(title);
						updatedGame.release_date = releaseDate;
						updatedGame.rent_stock = rentStock;
						updatedGame.rent_price = rentPrice;
						updatedGame.sale_stock = saleStock;
						updatedGame.sale_price = salePrice;
						updatedGame.platform = platform;
						updatedGame.classification = classification;
						updatedGame.genre = genre;
						updatedGame.setCover(game.getCover());

						GamesController controller = new GamesController();
						boolean success = controller.updateGame(updatedGame, updatedGame.product_id);

						if (success) {
							JOptionPane.showMessageDialog(null, "Videojuego actualizado correctamente.");

							gameTitle.setFocusable(false);
							gameDate.setFocusable(false);
							gameRentStock.setFocusable(false);
							gameRent.setFocusable(false);
							gamePlatform.setFocusable(false);
							gameClass.setEnabled(false);
							gameGenre.setEnabled(false);
							gameSaleStock.setFocusable(false);
							gameSale.setFocusable(false);
							descargar.setEnabled(true);
							cancelar.setVisible(false);
							guardar.setVisible(false);
							foto.setEnabled(false);
						} else {
							JOptionPane.showMessageDialog(null, "Error al actualizar el videojuego.", "Error", JOptionPane.ERROR_MESSAGE);
						}
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
				gameTitle.setFocusable(true);
				gameDate.setFocusable(true);
				gamePlatform.setFocusable(true);
				gameClass.setEnabled(true);
				gameGenre.setEnabled(true);
				descargar.setEnabled(false);
				cancelar.setVisible(true);
				guardar.setVisible(true);
				renta.setEnabled(true);
				venta.setEnabled(true);
				gameRentStock.setFocusable(renta.isSelected());
				gameRent.setFocusable(renta.isSelected());
				gameSaleStock.setFocusable(venta.isSelected());
				gameSale.setFocusable(venta.isSelected());
				foto.setEnabled(true);
				
			}
		});
	}

	public void filterPanel(JPanel centro) {
		filtro = new RoundedPanel(30, new Color(255, 255, 255),3);
		filtro.setBounds(625, 115, 340, 300);
		filtro.setLayout(null);
		filtro.setVisible(false);
		
		JLabel platform = new JLabel("Clasificación");
		platform.setBounds(40, 30, 100, 15);
		platform.setFont(txt);
		filtro.add(platform);
		
		xbox = new CustomJRadioButton();
		xbox.setBounds(35, 55, 100, 30);
		xbox.setFont(fieldtxt);
		xbox.setText("Xbox Series");
		filtro.add(xbox);
		
		Switch = new CustomJRadioButton();
		Switch.setBounds(140, 55, 90, 30);
		Switch.setFont(fieldtxt);
		Switch.setText("N Switch");
		filtro.add(Switch);
		
		ps4 = new CustomJRadioButton();
		ps4.setBounds(240, 55, 70, 30);
		ps4.setFont(fieldtxt);
		ps4.setText("PS5");
		filtro.add(ps4);
		
		allPlatforms = new CustomJRadioButton();
		allPlatforms.setBounds(240, 100, 100, 30);
		allPlatforms.setFont(fieldtxt);
		allPlatforms.setText("Todos");
		filtro.add(allPlatforms);
		
		ButtonGroup grupoPlatform = new ButtonGroup();
		grupoPlatform.add(xbox);
		grupoPlatform.add(Switch);
		grupoPlatform.add(ps4);
		grupoPlatform.add(allPlatforms);
		
		JLabel gen = new JLabel("Género");
		gen.setBounds(40, 125, 100, 15);
		gen.setFont(txt);
		filtro.add(gen);
		
		sports = new CustomJRadioButton();
		sports.setBounds(35, 150, 100, 30);
		sports.setFont(fieldtxt);
		sports.setText("Deportes");
		filtro.add(sports);
		
		adventure = new CustomJRadioButton();
		adventure.setBounds(140, 150, 100, 30);
		adventure.setFont(fieldtxt);
		adventure.setText("Aventura");
		filtro.add(adventure);
		
		racing = new CustomJRadioButton();
		racing.setBounds(240, 150, 100, 30);
		racing.setFont(fieldtxt);
		racing.setText("Terror");
		filtro.add(racing);
		
		shooter = new CustomJRadioButton();
		shooter.setBounds(35, 195, 100, 30);
		shooter.setFont(fieldtxt);
		shooter.setText("Plataformas");
		filtro.add(shooter);
		
		action = new CustomJRadioButton();
		action.setBounds(140, 195, 100, 30);
		action.setFont(fieldtxt);
		action.setText("Acción");
		filtro.add(action);
		
		allGen = new CustomJRadioButton();
		allGen.setBounds(240, 195, 100, 30);
		allGen.setFont(fieldtxt);
		allGen.setText("Todos");
		filtro.add(allGen);
		
		ButtonGroup grupoGenero = new ButtonGroup();
		grupoGenero.add(sports);
		grupoGenero.add(adventure);
		grupoGenero.add(racing);
		grupoGenero.add(shooter);
		grupoGenero.add(action);
		grupoGenero.add(allGen);
		
		
		RoundedButton aplicar = new RoundedButton("Aplicar");
		aplicar.setBounds(200, 240, 85, 30);
		aplicar.setRadius(20);
		aplicar.setBackground(blue);
		filtro.add(aplicar);
		aplicar.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	 filtrar();
		    	 filtro.setVisible(false);		    }
		});
		
		RoundedButton cerrar = new RoundedButton("Cerrar");
		cerrar.setBounds(60, 240, 85, 30);
		cerrar.setRadius(20);
		cerrar.setBackground(field);
		cerrar.setForeground(Color.black);
		cerrar.setBorderColor(border);
		cerrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				filtro.setVisible(false);
			}
			
		});
		filtro.add(cerrar);
		
		centro.add(filtro);
		centro.setComponentZOrder(filtro, 0);
		centro.repaint();
		centro.revalidate();
			
	}

	public void filtrar() {
		String textoBarra = searchBar.getText();
		RowFilter<DefaultTableModel, Object> filtroTexto;
		if(textoBarra.isEmpty()) {
			filtroTexto = RowFilter.regexFilter(".*", 0);
		} else {
			filtroTexto = RowFilter.regexFilter("(?i)^" + Pattern.quote(textoBarra), 0);
		}
		
		String PlatfoSelec=null;
		RowFilter<DefaultTableModel, Object> filtroPlatform;
	    if (xbox.isSelected())        
	    	PlatfoSelec = "Xbox Series";
	    else if (Switch.isSelected())   
	    	PlatfoSelec = "Nintendo Switch";
	    else if (ps4.isSelected()) 
	    	PlatfoSelec = "PS5";
	    else if (allPlatforms.isSelected())                            
	    	PlatfoSelec = "Todos"; 
	    
	    if (PlatfoSelec == null || PlatfoSelec.equals("Todos")  ) {
	    	filtroPlatform = RowFilter.regexFilter(".*", 1);
	    }
	    else {
	    	filtroPlatform = RowFilter.regexFilter("^" + Pattern.quote(PlatfoSelec) + "$", 1);
	    }
		
		
	    String generoSelec =null;
	    RowFilter<DefaultTableModel, Object> filtroGenero;
	    if (sports.isSelected())         
	    	generoSelec = "Deportes";
	    else if (adventure.isSelected()) 
	    	generoSelec = "Aventura";
	    else if (racing.isSelected())    
	    	generoSelec = "Terror";
	    else if (shooter.isSelected())    
	    	generoSelec = "Plataformas";
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
	    listaFiltros.add(filtroPlatform);
	    listaFiltros.add(filtroGenero);
	    RowFilter<DefaultTableModel, Object> filtroCombinado = RowFilter.andFilter(listaFiltros);
		buscador.setRowFilter(filtroCombinado);
		
	}
	
	
	
	public void generarPDF(Game game) {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Guardar ficha de videojuego como...");
	    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf"));

	    int userSelection = fileChooser.showSaveDialog(null);

	    if (userSelection == JFileChooser.APPROVE_OPTION) {
	        File fileToSave = fileChooser.getSelectedFile();
	        String filePath = fileToSave.getAbsolutePath();

	        if (!filePath.toLowerCase().endsWith(".pdf")) {
	            filePath += ".pdf";
	        }

	        try {
	            PdfWriter writer = new PdfWriter(filePath);
	            PdfDocument pdfDoc = new PdfDocument(writer);
	            Document document = new Document(pdfDoc);

	            Paragraph espacio = new Paragraph()
	            		.setMarginTop(40);
	            document.add(espacio);
	            
	            Paragraph pt = new Paragraph("Ficha de videojuego")
	            		.setFontSize(20)
	            		.setTextAlignment(TextAlignment.CENTER);
	            document.add(pt);

	            
	            document.add(espacio);
	            
	            byte[] imagenBytes = game.getCover();
	            com.itextpdf.layout.element.Image poster = null;
	            if (imagenBytes != null && imagenBytes.length > 0) {
	                ImageData imgData = ImageDataFactory.create(imagenBytes);
	                poster = new com.itextpdf.layout.element.Image(imgData);
	            }

	          //Tabla
	            Table tabla = new Table(UnitValue.createPercentArray(new float[]{10, 30, 10, 50}))
	                    .setWidth(UnitValue.createPercentValue(100));
	            
	            
	            Cell celdaEspacio = new Cell()
	            		.setBorder(null);
	            tabla.addCell(celdaEspacio);
	            
	            // Celda  con portada
	            if (poster != null) {
	                Cell celdaImagen = new Cell()
	                		.setHorizontalAlignment(HorizontalAlignment.CENTER)
	                		.setTextAlignment(TextAlignment.LEFT)
	                		.setBorder(null)
	                		.add(poster);
	                tabla.addCell(celdaImagen);
	            } else {
	                tabla.addCell(new Cell());
	            }
	            
	            tabla.addCell(celdaEspacio);
	            
	            // Celda con datos
	            Paragraph datos = new Paragraph()
	                    .add(new Text("ID: "+game.product_id + "\n"))
	                    .add(new Text("\nTítulo: "+game.getTitle()+ "\n"))
	                    .add(new Text("\nGénero: "+game.getGenre()+ "\n"))
	                    .add(new Text("\nClasificación: "+game.getClassification()+ "\n"))
	                    .add(new Text("\nPlataforma: "+game.getPlatform()+ "\n"))
	                    .add(new Text("\nFecha de estreno: "+game.getRelease_date()+ "\n"));

	            Cell celdaDatos = new Cell().add(datos)
	                    .setTextAlignment(TextAlignment.LEFT)
	                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
	                    .setFontSize(13)
	                    .setBorder(null);

	            tabla.addCell(celdaDatos);
	            
	            document.add(tabla);
	            
	            document.add(espacio);
	            
	            Table tabla2 = new Table(UnitValue.createPercentArray(new float[]{10,40, 40, 10}))
	                    .setWidth(UnitValue.createPercentValue(100));
	            
	            tabla2.addCell(celdaEspacio);
	            
	            tabla2.addCell(new Cell()
	            		.add(new Paragraph("Venta")
	            			.setTextAlignment(TextAlignment.CENTER)
	            			.setFontSize(14))
	            		.setBorder(null));
	            
	            tabla2.addCell(new Cell()
	            		.add(new Paragraph("Renta")
	            			.setTextAlignment(TextAlignment.CENTER)
	            			.setFontSize(14))
	            		.setBorder(null));
	            
	            tabla2.addCell(celdaEspacio);

	            // Segunda fila
	            
	            tabla2.addCell(celdaEspacio);
	            
	            tabla2.addCell(new Cell()
	            		.add(new Paragraph("Stock: "+game.getSale_stock()))
	            	.setBorder(null));
	            
	            tabla2.addCell(new Cell()
	            		.add(new Paragraph("Stock: "+game.getRent_stock()))
	            	.setBorder(null));

	            tabla2.addCell(celdaEspacio);

	            // Tercera fila        

	            tabla2.addCell(celdaEspacio);	            
	            
	            tabla2.addCell(new Cell()
	            		.add(new Paragraph("Precio: $"+game.getSale_price()))
	            	.setBorder(null));
	            tabla2.addCell(new Cell()
	            		.add(new Paragraph("Precio: $"+game.getRent_price()))
	            	.setBorder(null));
	            
	            tabla2.addCell(celdaEspacio);
	            
	            document.add(tabla2);

	            
	            document.close();

		        JOptionPane.showMessageDialog(null, "PDF generado correctamente.");


	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}
