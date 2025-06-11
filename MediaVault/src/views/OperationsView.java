package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import controllers.ClientsController;
import controllers.OperationsController;
import controllers.PromotionsController;
import customClasses.CoverTitleCellRendererClient;
import customClasses.CoverTitleCellRendererOperationClient;
import customClasses.CustomScrollBar;
import customClasses.CustomScrollPane;
import customClasses.Fuentes;
import customClasses.GameJDialog;
import customClasses.ImageUtils;
import customClasses.MovieJDialog;
import customClasses.RoundedButton;
import customClasses.RoundedJTextField;
import customClasses.RoundedPanel;
import customClasses.SearchJDialog;
import customClasses.SideBar;
import customClasses.TableActionCellEditor;
import customClasses.TableActionCellEditorOneButton;
import customClasses.TableActionCellRender;
import customClasses.TableActionCellRendererOneButton;
import customClasses.TableActionEvent;
import customClasses.Validaciones;
import models.Client;
import models.Game;
import models.Movie;
import models.Operation;
import models.Promotion;

public class OperationsView {

	RoundedPanel filtro,dataPanel;
	JPanel centro;
	
	Color blue = new Color(24, 130, 234);
	Color border = new Color(186, 186, 186);
	Color lightGray = new Color(117, 117, 117);
	Color field = new Color(250, 250, 250);
	Color select = new Color(221, 236, 252);
	
	Fuentes tipoFuentes = new Fuentes();
	Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 17f);
	Font btn = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 14f);
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 12f);
	Font fieldtxt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 11f);
	
	ImageIcon lupa = new ImageIcon(OperationsView.class.getResource("/images/lupa.png"));
	ImageIcon mas = new ImageIcon(OperationsView.class.getResource("/images/mas.png"));
	ImageIcon filter = new ImageIcon(OperationsView.class.getResource("/images/filter.png"));
	ImageIcon arrow = new ImageIcon(OperationsView.class.getResource("/images/arrow.png"));
	ImageIcon iconoFrame = new ImageIcon(LoginView.class.getResource("/images/iconoPrincipal.PNG"));
	ImageIcon checkW = new ImageIcon(OperationsView.class.getResource("/images/checkW.png"));
	ImageIcon delete = new ImageIcon(MoviesView.class.getResource("/images/eliminarW.png"));
	
	private RoundedButton selectedOperation = null;
	private RoundedButton selectedProduct = null;
	
	TableRowSorter<DefaultTableModel> buscador;
	RoundedJTextField searchBar, searchBar2, txtClient,returnDate,saleDate;
	
	Client currentClient;
	Movie currentMovie;
	Game currentGame;
	
	RoundedPanel cobro;
	
	JLabel precioBase,promocion,descuento_Cliente,total;
	JLabel numPromo, numDescuento;
	JLabel cant_precio_base, cant_descuento, cant_promo,cant_total;
	JLabel returnLabel,lblFoto2;
	
	String operationType;

	public OperationsView() {

	}

	public void operations() {

		// VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Operaciones");
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
		titlePanel.setBounds(151, 11, 130, 43);
		centro.add(titlePanel);
		titlePanel.setLayout(new BorderLayout(0, 0));

		JLabel titleLabel = new JLabel("Operaciones");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(titles);
		titlePanel.add(titleLabel);
		
		RoundedButton newOp= new RoundedButton("Registrar operación");
		newOp.setIcon(new ImageIcon(((ImageIcon) mas).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		newOp.setBounds(765, 11, 200, 43);
		newOp.setIconTextGap(10);
		newOp.setBackground(blue);
		newOp.setRadius(30);
		newOp.setFont(btn);
		newOp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				newOperation();
			}
			
		});
		centro.add(newOp);
		
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

		RoundedJTextField searchBar = new RoundedJTextField(20);
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
		
		//TABLA (HOLDER)
		//TITLES
		JPanel tableTitles = new JPanel();
		tableTitles.setBounds(151, 144, 810, 29);
		tableTitles.setLayout(new GridLayout(0, 5, 0, 0));
		centro.add(tableTitles);

		JLabel lblNewLabel_1 = new JLabel("Cliente");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(lightGray);
		lblNewLabel_1.setFont(txt);
		tableTitles.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Tipo");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(lightGray);
		lblNewLabel_2.setFont(txt);
		tableTitles.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Productos");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(lightGray);
		lblNewLabel_3.setFont(txt);
		tableTitles.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Fecha de operación");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setForeground(lightGray);
		lblNewLabel_4.setFont(txt);
		tableTitles.add(lblNewLabel_4);

		JLabel lblAcciones = new JLabel("Acciones");
		lblAcciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblAcciones.setForeground(lightGray);
		lblAcciones.setFont(txt);
		tableTitles.add(lblAcciones);

		//TABLA
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(151, 184, 810, 322);
		tablePanel.setLayout(new BorderLayout(0, 0));
		tablePanel.setBackground(Color.white);
		centro.add(tablePanel);
		
		//Obtener clientes de la BD
		OperationsController controller = new OperationsController();
		ArrayList<Operation> operations = controller.get();
		
		String[] columnNames = {"", "", "", "", "",""};
		DefaultTableModel model1 = new DefaultTableModel(columnNames, 0){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return column== 4;
		    }
		};
		
		for (Operation operation : operations) {
			Client client = operation.getCliente();
			String celdaCliente = "<html>"
                    + client.getName() + " " + client.getLast_name() + "<br>"
                    + "<font color='gray'>ID: " + client.getClient_id() + "</font>"
                    + "</html>";
			
			String type = operation.getOperation_type().equalsIgnoreCase("rent")? "Renta": "Venta";
			
			String producto = "<html><font color='green'>" +
			        operation.getProductTitle() +
			        "</font></html>";
	        
			String fecha = operation.getOperation_date();
			
			
		    model1.addRow(new Object[] {
		        celdaCliente,
		        type,
		        producto,
		        fecha,
		        "",
		        operation
		    });
		}
		
		JTable table = new JTable(model1);
		table.setRowHeight(80);
		table.setShowVerticalLines(false);
		table.setTableHeader(null);
		table.setFont(fieldtxt);
		
		table.getColumnModel().getColumn(0).setCellRenderer(new CoverTitleCellRendererOperationClient());
		
		table.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRendererOneButton());
		
		table.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditorOneButton(new TableActionEvent() {
			public void onDelete(int row) {
				Operation operation = (Operation) table.getModel().getValueAt(row, 5);
				int confirm = JOptionPane.showConfirmDialog(frame, "¿Estás seguro de eliminar esta operacion?", 
						"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					int operation_id = operation.getId_operation(); 
					OperationsController oc = new OperationsController();
					
					if (oc.delete(operation_id)) {
						JOptionPane.showMessageDialog(frame, "Operación eliminada con éxito."); 
						frame.dispose();
						operations();
						
					} else {
						JOptionPane.showMessageDialog(frame, "Error al eliminar la operacion.", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				
			}
		}));
		
		TableColumnModel cm = table.getColumnModel();
		cm.removeColumn(cm.getColumn(5));
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int fila = table.rowAtPoint(e.getPoint());
		        int col = table.columnAtPoint(e.getPoint());

		        if (col == 0 && fila != -1) {
		        	int modeloFila = table.convertRowIndexToModel(fila);
		            Operation operation = (Operation) table.getModel().getValueAt(modeloFila, 5);
		            
		            frame.dispose();
		            viewOperation(operation);
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

	public void newOperation() {
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Nueva operación");
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

		RoundedButton titleButton = new RoundedButton("Registrar operación");
		titleButton.setIcon(new ImageIcon(((ImageIcon) arrow).getImage().getScaledInstance(15, 20, Image.SCALE_SMOOTH)));
		titleButton.setBounds(151, 11, 230, 43);
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
				operations();
			}

		});
		centro.add(titleButton);

		//ENTRADA DE DATOS
		dataPanel = new RoundedPanel(30, new Color(255, 255, 255));
		dataPanel.setBounds(151, 65, 810, 345);
		dataPanel.setLayout(null);
		centro.add(dataPanel);
		
		JLabel clientLabel = new JLabel("Cliente");
		clientLabel.setBounds(50, 20, 100, 15);
		clientLabel.setFont(txt);
		dataPanel.add(clientLabel);
		
		JLabel lupaIcon = new JLabel("");
		lupaIcon.setIcon(new ImageIcon(((ImageIcon) lupa).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		lupaIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lupaIcon.setBounds(65, 40, 30, 30);
		dataPanel.add(lupaIcon);

		JLabel lblFoto = new JLabel();
		lblFoto.setBounds(120, 34, 40, 40);
		dataPanel.add(lblFoto);
		
		searchBar = new RoundedJTextField(20);
		searchBar.setBounds(115, 40, 520, 30);
		searchBar.setBackground(field);
		searchBar.setFont(txt);
		searchBar.setFocusable(false);
		dataPanel.add(searchBar);
		
		RoundedButton buscar = new RoundedButton("Buscar");
		buscar.setBounds(665, 40, 86, 30);
		buscar.setBackground(blue);
		buscar.setFont(btn);
		buscar.setRadius(20);
		dataPanel.add(buscar);
		buscar.addActionListener(e ->{
			SearchJDialog dlg = new SearchJDialog(frame);
		    Client sel = dlg.showDialog();
		    if (sel != null) {
		        currentClient = sel;
		        ImageIcon foto = ImageUtils.getCircularIcon(sel.getPhoto(), 30);
		        lblFoto.setIcon(foto);
		        searchBar.setText("         #" + sel.getClient_id() + "   "+sel.getName() +"   "+ sel.getEmail() );
		        calculos();
		    }
		});
		
		
		JLabel operationLabel = new JLabel("Tipo de operación");
		operationLabel.setBounds(50, 80, 150, 15);
		operationLabel.setFont(txt);
		dataPanel.add(operationLabel);
		
		RoundedButton renta = new RoundedButton("Renta");
		renta.setBounds(45, 100, 310, 30);
		renta.setBackground(Color.white);
		renta.setForeground(Color.black);
		renta.setBorderColor(border);
		renta.setFont(txt);
		renta.setRadius(20);
		dataPanel.add(renta);
		
		RoundedButton compra = new RoundedButton("Compra");
		compra.setBounds(440, 100, 310, 30);
		compra.setBackground(Color.white);
		compra.setForeground(Color.black);
		compra.setBorderColor(border);
		compra.setFont(txt);
		compra.setRadius(20);
		dataPanel.add(compra);

		ActionListener operationListener = e -> { 
			RoundedButton clicked = (RoundedButton) e.getSource();

			if (selectedOperation != null) {
				selectedOperation.setBackground(Color.white);
				selectedOperation.setForeground(Color.black);
			}

			clicked.setBackground(select);
			selectedOperation = clicked;
			operationType = clicked.getText();
			mostrarDevolucion();
		};

		renta.addActionListener(operationListener);
		compra.addActionListener(operationListener);
		
		JLabel productLabel = new JLabel("Tipo de producto");
		productLabel.setBounds(50, 150, 150, 15);
		productLabel.setFont(txt);
		dataPanel.add(productLabel);
		
		RoundedButton game = new RoundedButton("Videojuego");
		game.setBounds(45, 170, 310, 30);
		game.setBackground(Color.white);
		game.setForeground(Color.black);
		game.setBorderColor(border);
		game.setFont(txt);
		game.setRadius(20);
		dataPanel.add(game);
		
		RoundedButton movie = new RoundedButton("Película");
		movie.setBounds(440, 170, 310, 30);
		movie.setBackground(Color.white);
		movie.setForeground(Color.black);
		movie.setBorderColor(border);
		movie.setFont(txt);
		movie.setRadius(20);
		dataPanel.add(movie);

		ActionListener productListener = e -> { 
			RoundedButton clicked = (RoundedButton) e.getSource();

			if (selectedProduct != null) {
				selectedProduct.setBackground(Color.white);
				selectedProduct.setForeground(Color.black);
			}

			clicked.setBackground(select);
			selectedProduct = clicked;
			
			if (clicked == movie) {
		        currentGame = null;         
		    } else if (clicked == game) {
		        currentMovie = null;        
		    }
			
			cobro.setVisible(false);
			searchBar2.setText("");
			lblFoto2.setIcon(null);
		};
		
		game.addActionListener(productListener);
		movie.addActionListener(productListener);

		JLabel titleLabel = new JLabel("Seleccionar título");
		titleLabel.setBounds(50, 215, 150, 15);
		titleLabel.setFont(txt);
		dataPanel.add(titleLabel);

		JLabel lupaIcon2 = new JLabel("");
		lupaIcon2.setIcon(new ImageIcon(((ImageIcon) lupa).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		lupaIcon2.setHorizontalAlignment(SwingConstants.CENTER);
		lupaIcon2.setBounds(65, 235, 30, 30);
		dataPanel.add(lupaIcon2);
		
		lblFoto2 = new JLabel();
		lblFoto2.setBounds(120, 229, 40, 40);
		dataPanel.add(lblFoto2);
		
		searchBar2 = new RoundedJTextField(20);
		searchBar2.setBounds(115, 235, 520, 30);
		searchBar2.setBackground(field);
		searchBar2.setFont(txt);
		searchBar2.setFocusable(false);
		dataPanel.add(searchBar2);
		
		panelCobro();
		cobro.setVisible(false);

		RoundedButton buscar2 = new RoundedButton("Buscar");
		buscar2.setBounds(665, 235, 86, 30);
		buscar2.setBackground(blue);
		buscar2.setFont(btn);
		buscar2.setRadius(20);
		dataPanel.add(buscar2);
		buscar2.addActionListener(e ->{

			if (selectedProduct == movie) {
				MovieJDialog mdlg = new MovieJDialog(frame);
				Movie sel = mdlg.showDialog();
				if (sel != null) {
					currentMovie = sel;
					ImageIcon foto = ImageUtils.getCircularIcon(sel.getCover(), 30);
					lblFoto2.setIcon(foto);
					searchBar2.setText("         #" + sel.getProduct_id() + "   "+sel.getTitle() +"   "+ sel.getStudio() );
					calculos();
				}
				
			}
			else if (selectedProduct == game) {
				GameJDialog gdlg = new GameJDialog(frame);
				Game sel2 = gdlg.showDialog();
				if (sel2 != null) {
					currentGame = sel2;
					ImageIcon foto = ImageUtils.getCircularIcon(sel2.getCover(), 30);
					lblFoto2.setIcon(foto);
					searchBar2.setText("         #" + sel2.getProduct_id() + "   "+sel2.getTitle() +"   "+ sel2.getPlatform() );
					calculos();
				}
			}

		});
		
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime operationDate = LocalDateTime.now();
		String fecha_operacion = operationDate.format(fmt);
		
		
		JLabel saleLabel = new JLabel("Fecha de pago");
		saleLabel.setBounds(50, 275, 150, 15);
		saleLabel.setFont(txt);
		dataPanel.add(saleLabel);
		
		saleDate = new RoundedJTextField(20);
		saleDate.setText(fecha_operacion);
		saleDate.setBounds(45, 295, 310, 27);
		saleDate.addKeyListener(Validaciones.fechas());
		saleDate.addKeyListener(Validaciones.limite(10));
		saleDate.setFont(fieldtxt);
		saleDate.setFocusable(false);
		dataPanel.add(saleDate);
		
		
		returnLabel = new JLabel("Fecha de devolucion");
		returnLabel.setBounds(440, 275, 150, 15);
		returnLabel.setVisible(false);
		returnLabel.setFont(txt);
		dataPanel.add(returnLabel);
		
		returnDate = new RoundedJTextField(20);
		returnDate.setBounds(440, 295, 310, 27);
		returnDate.setVisible(false);
		returnDate.setFocusable(false);
		returnDate.addKeyListener(Validaciones.fechas());
		returnDate.addKeyListener(Validaciones.limite(10));
		returnDate.setFont(fieldtxt);
		dataPanel.add(returnDate);
		
		
		RoundedButton cancelar = new RoundedButton("Cancelar");
		cancelar.setBounds(680, 560,80, 30);
		cancelar.setBackground(Color.white);
		cancelar.setForeground(Color.black);
		cancelar.setBorderColor(border);
		cancelar.setFont(txt);
		cancelar.setRadius(20);
		centro.add(cancelar);
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				OperationsController oc = new OperationsController();
				oc.operations();
			}
			
		});
		
		
		RoundedButton guardar = new RoundedButton("Completar operación");
		guardar.setIcon(new ImageIcon(((ImageIcon) checkW).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
		guardar.setBounds(780, 560, 170, 30);
		guardar.setBackground(blue);
		guardar.setFont(txt);
		guardar.setRadius(20);
		centro.add(guardar);
		guardar.addActionListener(e ->{
			if (currentClient == null) {
		        JOptionPane.showMessageDialog(frame,"Seleccione un cliente.","Datos incompletos",JOptionPane.WARNING_MESSAGE);
		        return;
		    }

		    if (selectedOperation == null) {
		        JOptionPane.showMessageDialog(frame,"Elija un tipo de operacion (Renta/Compra).","Datos incompletos",JOptionPane.WARNING_MESSAGE);
		        return;
		    }

		    if (currentMovie==null && currentGame==null) {
		        JOptionPane.showMessageDialog(frame,"Debe seleccionar un producto (película o juego).","Datos incompletos",JOptionPane.WARNING_MESSAGE);
		        return;
		    }
		    
		    boolean movieSelec = currentMovie != null;
		    
		    double unitPrice  = Double.parseDouble(cant_precio_base.getText().replace("$",""));
		    double totalAmount= Double.parseDouble(cant_total.getText().replace("$",""));
		    
		    String tipoOperacion=null;
		    if (operationType.equals("Compra")) {
		    	tipoOperacion = "sale";
		    }
		    else if (operationType.equals("Renta")) {
		    	tipoOperacion = "rent";
		    }
		    	
		    Operation op;
		    if (movieSelec) {
		    	op = new Operation(currentClient,currentMovie,tipoOperacion,saleDate.getText(),unitPrice,totalAmount);
		    }
		    else {
		    	op = new Operation(currentClient,currentGame,tipoOperacion,saleDate.getText(),unitPrice,totalAmount);
		    }
		    
		    OperationsController oc = new OperationsController();
		    if (oc.add(op)) {
		    	System.out.println("La operacion se registró exitosamente");
				JOptionPane.showMessageDialog(null, "Operacion registrada.");
		    }
		    frame.dispose();
		    operations();
		    reiniciar();
		});
	}
	
	public void viewOperation(Operation operation) {
		Client client = operation.getCliente();
		Movie movieObject=operation.getMovie();
		Game gameObject = operation.getGame();
		String operationTypeLocal = operation.getOperation_type().equalsIgnoreCase("rent")? "Renta":"Compra";
		String operationDate = operation.getOperation_date();
		
		currentClient=client;
		currentMovie=movieObject;
		currentGame=gameObject;

		
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Nueva operación");
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

		RoundedButton titleButton = new RoundedButton("Registrar operación");
		titleButton.setIcon(new ImageIcon(((ImageIcon) arrow).getImage().getScaledInstance(15, 20, Image.SCALE_SMOOTH)));
		titleButton.setBounds(151, 11, 230, 43);
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
				operations();
			}

		});
		centro.add(titleButton);

		//ENTRADA DE DATOS
		dataPanel = new RoundedPanel(30, new Color(255, 255, 255));
		dataPanel.setBounds(151, 65, 810, 345);
		dataPanel.setLayout(null);
		centro.add(dataPanel);
		
		JLabel clientLabel = new JLabel("Cliente");
		clientLabel.setBounds(50, 20, 100, 15);
		clientLabel.setFont(txt);
		dataPanel.add(clientLabel);
		
		JLabel lupaIcon = new JLabel("");
		lupaIcon.setIcon(new ImageIcon(((ImageIcon) lupa).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		lupaIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lupaIcon.setBounds(65, 40, 30, 30);
		dataPanel.add(lupaIcon);

		JLabel lblFoto = new JLabel();
		lblFoto.setBounds(120, 34, 40, 40);
		lblFoto.setIcon(ImageUtils.getCircularIcon(client.getPhoto(), 30));
		dataPanel.add(lblFoto);
		
		searchBar = new RoundedJTextField(20);
		searchBar.setBounds(115, 40, 520, 30);
		searchBar.setBackground(field);
		searchBar.setFont(txt);
		searchBar.setFocusable(false);
		searchBar.setText("#" + client.getClient_id()+ "  " + client.getName() + "  " + client.getEmail());
		dataPanel.add(searchBar);
		
		RoundedButton buscar = new RoundedButton("Buscar");
		buscar.setBounds(665, 40, 86, 30);
		buscar.setBackground(blue);
		buscar.setFont(btn);
		buscar.setRadius(20);
		dataPanel.add(buscar);
		
		JLabel operationLabel = new JLabel("Tipo de operación");
		operationLabel.setBounds(50, 80, 150, 15);
		operationLabel.setFont(txt);
		dataPanel.add(operationLabel);
		
		RoundedButton renta = new RoundedButton("Renta");
		renta.setBounds(45, 100, 310, 30);
		renta.setBackground(Color.white);
		renta.setForeground(Color.black);
		renta.setBorderColor(border);
		renta.setFont(txt);
		renta.setRadius(20);
		dataPanel.add(renta);
		
		RoundedButton compra = new RoundedButton("Compra");
		compra.setBounds(440, 100, 310, 30);
		compra.setBackground(Color.white);
		compra.setForeground(Color.black);
		compra.setBorderColor(border);
		compra.setFont(txt);
		compra.setRadius(20);
		dataPanel.add(compra);
		
		if (operationTypeLocal.equals("Renta")) {
	        renta.setBackground(select);
	        selectedOperation = renta;
	    } else {
	        compra.setBackground(select);
	        selectedOperation = compra;
	    }

	    operationType = operationTypeLocal;
		
		JLabel productLabel = new JLabel("Tipo de producto");
		productLabel.setBounds(50, 150, 150, 15);
		productLabel.setFont(txt);
		dataPanel.add(productLabel);
		
		RoundedButton game = new RoundedButton("Videojuego");
		game.setBounds(45, 170, 310, 30);
		game.setBackground(Color.white);
		game.setForeground(Color.black);
		game.setBorderColor(border);
		game.setFont(txt);
		game.setRadius(20);
		dataPanel.add(game);
		
		RoundedButton movie = new RoundedButton("Película");
		movie.setBounds(440, 170, 310, 30);
		movie.setBackground(Color.white);
		movie.setForeground(Color.black);
		movie.setBorderColor(border);
		movie.setFont(txt);
		movie.setRadius(20);
		dataPanel.add(movie);


		JLabel titleLabel = new JLabel("Seleccionar título");
		titleLabel.setBounds(50, 215, 150, 15);
		titleLabel.setFont(txt);
		dataPanel.add(titleLabel);

		JLabel lupaIcon2 = new JLabel("");
		lupaIcon2.setIcon(new ImageIcon(((ImageIcon) lupa).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		lupaIcon2.setHorizontalAlignment(SwingConstants.CENTER);
		lupaIcon2.setBounds(65, 235, 30, 30);
		dataPanel.add(lupaIcon2);
		
		lblFoto2 = new JLabel();
		lblFoto2.setBounds(120, 229, 40, 40);
		dataPanel.add(lblFoto2);
		
		searchBar2 = new RoundedJTextField(20);
		searchBar2.setBounds(115, 235, 520, 30);
		searchBar2.setBackground(field);
		searchBar2.setFont(txt);
		searchBar2.setFocusable(false);
		dataPanel.add(searchBar2);
		
		if (movieObject != null) {
	        movie.setBackground(select);
	        lblFoto2.setIcon(ImageUtils.getCircularIcon(movieObject.getCover(), 30));
	        searchBar2.setText("#" + movieObject.getProduct_id()+ "  " + movieObject.getTitle() + "  " + movieObject.getStudio());
	        selectedProduct = movie;
	    } else {
	        game.setBackground(select);
	        lblFoto2.setIcon(ImageUtils.getCircularIcon(gameObject.getCover(), 30));
	        searchBar2.setText("#" + gameObject.getProduct_id()+ "  " + gameObject.getTitle() + "  " + gameObject.getPlatform());
	        selectedProduct = game;
	    }
		
		

		RoundedButton buscar2 = new RoundedButton("Buscar");
		buscar2.setBounds(665, 235, 86, 30);
		buscar2.setBackground(blue);
		buscar2.setFont(btn);
		buscar2.setRadius(20);
		dataPanel.add(buscar2);
		
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		
		
		JLabel saleLabel = new JLabel("Fecha de pago");
		saleLabel.setBounds(50, 275, 150, 15);
		saleLabel.setFont(txt);
		dataPanel.add(saleLabel);
		
		saleDate = new RoundedJTextField(20);
		saleDate.setText(operationDate);
		saleDate.setBounds(45, 295, 310, 27);
		saleDate.addKeyListener(Validaciones.fechas());
		saleDate.addKeyListener(Validaciones.limite(10));
		saleDate.setFont(fieldtxt);
		saleDate.setFocusable(false);
		dataPanel.add(saleDate);
		
		
		returnLabel = new JLabel("Fecha de devolucion");
		returnLabel.setBounds(440, 275, 150, 15);
		returnLabel.setVisible(false);
		returnLabel.setFont(txt);
		dataPanel.add(returnLabel);
		
		returnDate = new RoundedJTextField(20);
		returnDate.setBounds(440, 295, 310, 27);
		returnDate.setVisible(false);
		returnDate.setFocusable(false);
		returnDate.addKeyListener(Validaciones.fechas());
		returnDate.addKeyListener(Validaciones.limite(10));
		returnDate.setFont(fieldtxt);
		dataPanel.add(returnDate);
		
		
		
		mostrarDevolucion();
		panelCobro();
		calculos();
		
		RoundedButton eliminar = new RoundedButton("Eliminar usuario");
		eliminar.setIcon(new ImageIcon(((ImageIcon) delete).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		eliminar.setHorizontalTextPosition(SwingConstants.RIGHT);
		eliminar.setIconTextGap(10);
		eliminar.setBounds(150, 570, 160, 30);
		eliminar.setBackground(Color.red);
		eliminar.setFont(txt);
		eliminar.setRadius(20);
		centro.add(eliminar);
		eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta operacion?", 
						"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					int operation_id = operation.getId_operation(); 
					OperationsController oc = new OperationsController();

					if (oc.delete(operation_id)) {
						JOptionPane.showMessageDialog(null, "Operación eliminada con éxito.");
						frame.dispose(); 
						operations(); 
					} else {
						JOptionPane.showMessageDialog(null, "Error al eliminar la operacion.", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
				
	}
	
	public void mostrarDevolucion() {
		
		if (operationType.equalsIgnoreCase("Renta") ) {
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String returnTime = LocalDateTime.now().plusDays(30).format(fmt);
			
			returnLabel.setVisible(true);
	        returnDate.setVisible(true);
	        returnDate.setText(returnTime);	
		}
		else {
			returnLabel.setVisible(false);
			returnDate.setVisible(false);
		}
		dataPanel.revalidate();
		dataPanel.repaint();
		
	}
	
	public void calculos() {
		if (currentClient == null || (currentMovie==null && currentGame==null) || selectedOperation ==null) {
			return;
		}
		int promo_id;
		double precioBase =0;
		if (currentMovie !=null) {
			if(selectedOperation.equals("Renta")) {
				precioBase=currentMovie.getRent_price();
			}
			else {
				precioBase = currentMovie.getSale_price();
			}
			promo_id = currentMovie.get_fk_promotion_id();
		}
		else if (currentGame != null) {
			if (selectedOperation.equals("Renta")) {
				precioBase  = currentGame.getRent_price();
			}
			else {
				precioBase = currentGame.getSale_price();
			}
			promo_id = currentGame.get_fk_promotion_id();
		}
		else
			return;
		
		double descuentoFidelity = 0;
		if (currentClient.getFidelity().equals("aficionado")) {
			descuentoFidelity = 0.15;
		}
		else if(currentClient.getFidelity().equals("frecuente")) {
			descuentoFidelity = 0.20;
		}
		
		double descuentoPromo =0;
		if(promo_id >0) {
			Promotion promo = new PromotionsController().get(promo_id);
	        if (promo != null)
	        	descuentoPromo = promo.getPromo_discount();
		}
		
		String promotxt = String.format("%.0f%%", descuentoPromo * 100);
		numPromo.setText(promotxt);
		
		String descuentotxt = String.format("%.0f%%", descuentoFidelity * 100);
		numDescuento.setText(descuentotxt);
		
		cant_precio_base.setText("$ "+String.format("%.2f",precioBase));
		
		double dinero_descuento = precioBase * descuentoFidelity;
		cant_descuento.setText("$ "+String.format("%.2f", dinero_descuento));
		
		double dinero_promocion = precioBase * descuentoPromo;
		cant_promo.setText("$ "+String.format("%.2f", dinero_promocion));
		
		double pagoTotal = precioBase - (dinero_descuento+dinero_promocion);
		cant_total.setText("$ "+String.format("%.2f",pagoTotal));
		
		cobro.setVisible(true);
		cobro.revalidate();
		cobro.repaint();
		
	}
	
	public void reiniciar() {
		currentClient     = null;
		currentMovie      = null;
		currentGame       = null;
		selectedOperation = null;
		operationType     = null;

		searchBar .setText("");
		searchBar2.setText("");
		lblFoto2 .setIcon(null);

		cobro.setVisible(false);
		returnLabel.setVisible(false);
		returnDate.setVisible(false);
		numPromo      .setText("");
		numDescuento  .setText("");
		cant_precio_base.setText("");
		cant_descuento.setText("");
		cant_promo    .setText("");
		cant_total    .setText("");
		returnLabel   .setVisible(false);
		returnDate    .setVisible(false);
	}
	
	public void panelCobro() {
		cobro = new RoundedPanel(30,new Color(255, 255, 255));
		cobro.setLayout(null);
		cobro.setBounds(201, 420, 710, 130);
		cobro.setOpaque(false);
		cobro.setBackground(Color.white);
		centro.add(cobro);
		
		precioBase = new JLabel("Precio base: ");
		precioBase.setFont(txt);
		precioBase.setForeground(Color.black);
		precioBase.setBounds(10, 10, 100, 20);
		cobro.add(precioBase);
		
		promocion = new JLabel("Promoción:");
		promocion.setFont(txt);
		promocion.setForeground(Color.black);
		promocion.setBounds(10, 40, 100, 20);
		cobro.add(promocion);
		
		descuento_Cliente = new JLabel("Descuento nivel de fidelidad:");
		descuento_Cliente.setFont(txt);
		descuento_Cliente.setForeground(Color.black);
		descuento_Cliente.setBounds(10, 70, 200, 20);
		cobro.add(descuento_Cliente);
		
		total = new JLabel("Total:");
		total.setBounds(10, 100, 60, 20);
		total.setFont(btn);
		total.setForeground(Color.black);
		cobro.add(total);
		
		numPromo = new JLabel();
		numPromo.setBounds(330, 40, 40, 20);
		numPromo.setFont(txt);
		numPromo.setForeground(Color.black);
		cobro.add(numPromo);
		
		numDescuento = new JLabel();
		numDescuento.setBounds(330, 70, 40, 20);
		numDescuento.setFont(txt);
		numDescuento.setForeground(Color.black);
		cobro.add(numDescuento);
		
		cant_precio_base = new JLabel();
		cant_precio_base.setBounds(600, 10, 100, 20);
		cant_precio_base.setFont(txt);
		cant_precio_base.setForeground(Color.black);
		cobro.add(cant_precio_base);
		
		cant_descuento= new JLabel();
		cant_descuento.setBounds(600, 70, 100, 20);
		cant_descuento.setForeground(Color.black);
		cant_descuento.setFont(txt);
		cobro.add(cant_descuento);
		
		cant_promo = new JLabel();
		cant_promo.setBounds(600, 40, 100, 20);
		cant_promo.setFont(txt);
		cant_promo.setForeground(Color.black);
		cobro.add(cant_promo);
		
		cant_total = new JLabel();
		cant_total.setBounds(600, 100, 100, 20);
		cant_total.setFont(btn);
		cant_total.setForeground(Color.black);
		cobro.add(cant_total);
	}
	
	public void filterPanel(JPanel centro) {
		filtro = new RoundedPanel(30, new Color(255, 255, 255),3);
		filtro.setBounds(700, 115, 265, 200);
		filtro.setLayout(null);
		filtro.setVisible(false);
		
		RoundedButton aplicar = new RoundedButton("Aplicar");
		aplicar.setBounds(150, 150, 85, 30);
		aplicar.setRadius(20);
		aplicar.setBackground(blue);
		filtro.add(aplicar);
		
		RoundedButton cerrar = new RoundedButton("Cerrar");
		cerrar.setBounds(45, 150, 85, 30);
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
		
	}

}
