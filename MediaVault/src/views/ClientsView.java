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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
import controllers.MoviesController;
import customClasses.CoverTitleCellRenderer;
import customClasses.CoverTitleCellRendererClient;
import customClasses.CustomJComboBox;
import customClasses.CustomJRadioButton;
import customClasses.CustomScrollBar;
import customClasses.CustomScrollPane;
import customClasses.Fuentes;
import customClasses.IconCellRenderer;
import customClasses.ImageUtils;
import customClasses.RoundedButton;
import customClasses.RoundedJTextField;
import customClasses.RoundedPanel;
import customClasses.SideBar;
import customClasses.TableActionCellEditor;
import customClasses.TableActionCellRender;
import customClasses.TableActionEvent;
import customClasses.Validaciones;
import models.Client;
import models.ClientsModel;
import models.Movie;
import models.MoviesModel;

public class ClientsView {
	
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
	
	ImageIcon lupa = new ImageIcon(ClientsView.class.getResource("/images/lupa.png"));
	ImageIcon mas = new ImageIcon(ClientsView.class.getResource("/images/mas.png"));
	ImageIcon filter = new ImageIcon(ClientsView.class.getResource("/images/filter.png"));
	ImageIcon arrow = new ImageIcon(ClientsView.class.getResource("/images/arrow.png"));
	ImageIcon iconoFrame = new ImageIcon(ClientsView.class.getResource("/images/iconoPrincipal.PNG"));
	ImageIcon upImage = new ImageIcon(ClientsView.class.getResource("/images/upImage.png"));
	ImageIcon edit = new ImageIcon(MoviesView.class.getResource("/images/edit.png"));
	ImageIcon descarga = new ImageIcon(MoviesView.class.getResource("/images/descarga.png"));
	ImageIcon delete = new ImageIcon(MoviesView.class.getResource("/images/eliminarW.png"));
	
	byte[] photoBinario;
	
	TableRowSorter<DefaultTableModel> buscador;
	RoundedJTextField searchBar;
	CustomJRadioButton compra,renta;
    CustomJRadioButton videojuego,pelicula;
    
    RoundedButton foto,clientId,clientFidelity,guardar,cancelar;
    RoundedJTextField namefield,clientBirth,clientEmail,clientSurname,clientPhone;
    boolean editable=false;

	public ClientsView() {

	}

	public void clients() {
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Clientes");
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
		JPanel centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);

		RoundedPanel titlePanel = new RoundedPanel(30, new Color(255, 255, 255));
		titlePanel.setBounds(151, 11, 200, 43);
		centro.add(titlePanel);
		titlePanel.setLayout(new BorderLayout(0, 0));

		JLabel titleLabel = new JLabel("Clientes registrados");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(titles);
		titlePanel.add(titleLabel);
		
		RoundedButton newClient = new RoundedButton("Nuevo cliente");
		newClient.setIcon(new ImageIcon(((ImageIcon) mas).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		newClient.setBounds(795, 11, 169, 43);
		newClient.setIconTextGap(10);
		newClient.setBackground(blue);
		newClient.setFont(btn);
		newClient.setRadius(30);
		newClient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				newClient();
			}
			
			
		});
		centro.add(newClient);
		
		
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
		
		JLabel lblNewLabel_2 = new JLabel("Historial");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(lightGray);
		lblNewLabel_2.setFont(txt);
		tableTitles.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Rentas actuales");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(lightGray);
		lblNewLabel_3.setFont(txt);
		tableTitles.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Descuentos");
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
		ClientsController controller = new ClientsController();
		ArrayList<Client> clients = controller.get();
		
		String[] columnNames = {"", "", "", "", "",""};
		DefaultTableModel model1 = new DefaultTableModel(columnNames, 0){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return column== 4;
		    }
		};
		
		for (Client client : clients) {
			
			String celdaCliente = "<html>"
                    + client.getName() + " " + client.getLast_name() + "<br>"
                    + "<font color='gray'>ID: " + client.getClient_id() + "</font>"
                    + "</html>";
			
			String celdaHistorial = "<html>"
                    + "Rentas: " + client.getTotal_rentals() + "<br>"
                    + "Compras: " + client.getTotal_purchases()
                    + "</html>";
			
			String celdaRentaActual=null;
	        if (renta != null) {
	            /*celdaRentaActual = "<html>"
	                             + /*renta.getMovieTitle() + "<br>"
	                             + "<font color='red'>" + renta.getDueDate().toString() + "</font>"
	                             + "</html>";*/
	        } else {
	            celdaRentaActual = "<html><i>Sin renta</i></html>";
	        }
	        
	        String descuento = null;
	        if(client.getFidelity().equalsIgnoreCase("aficionado")) {
	        	descuento="15% Descuento";
	        }
	        else if(client.getFidelity().equalsIgnoreCase("frecuente")) {
	        	descuento="20% Descuento";
	        }
	        else {
	        	descuento = "Ninguno";
	        }
		    model1.addRow(new Object[] {
		        celdaCliente,
		        celdaHistorial,
		        celdaRentaActual,
		        descuento,
		        "",
		        client
		    });
		}
		
		JTable table = new JTable(model1);
		table.setRowHeight(80);
		table.setShowVerticalLines(false);
		table.setTableHeader(null);
		table.setFont(fieldtxt);
		
		table.getColumnModel().getColumn(0).setCellRenderer(new CoverTitleCellRendererClient());
		
		table.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender());
		
		table.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(new TableActionEvent() {
			public void onEdit(int row) {
				Client client = (Client) table.getModel().getValueAt(row, 5);
				frame.dispose();
				viewClient(client);
				edit();
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
		            Client client = (Client) table.getModel().getValueAt(modeloFila, 5);
		            
		            frame.dispose();
		            viewClient(client);
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

	public void newClient() {
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Nuevo cliente");
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
		
		RoundedButton titleButton = new RoundedButton("Nuevo cliente");
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
				clients();
			}
			
		});
		centro.add(titleButton);

		//ENTRADA DE DATOS
		RoundedPanel dataPanel = new RoundedPanel(30, new Color(255, 255, 255));
		dataPanel.setBounds(150, 65, 810, 245);
		dataPanel.setLayout(null);
		centro.add(dataPanel);
		
		RoundedButton foto = new RoundedButton();
		foto.setBounds(35, 27, 165, 175);
		foto.setImageIcon(upImage);
		foto.setBackground(gray);
		foto.setRadius(20);
		dataPanel.add(foto);
		foto.addActionListener(e ->{
			photoBinario = ImageUtils.imageToByte(foto, 5242880);
			
			if(photoBinario !=null) {
				ImageIcon image = ImageUtils.byteToImageEsc(photoBinario, foto);
				foto.setIcon(image);
			}
		});		
		
		JLabel nameLabel = new JLabel("Nombre:");
		nameLabel.setBounds(230, 20, 1700, 15);
		nameLabel.setFont(txt);
		dataPanel.add(nameLabel);
		
		RoundedJTextField namefield = new RoundedJTextField(20);
		namefield.setBounds(237, 40, 250, 27);
		namefield.setFont(fieldtxt);
		dataPanel.add(namefield);
		
		JLabel birthLabel = new JLabel("Fecha de nacimiento(AAAA-MM-DD):");
		birthLabel.setBounds(230, 80, 250, 15);
		birthLabel.setFont(txt);
		dataPanel.add(birthLabel);
		
		RoundedJTextField clientBirth = new RoundedJTextField(20);
		clientBirth.setBounds(230, 100, 250, 27);
		clientBirth.setFont(fieldtxt);
		dataPanel.add(clientBirth);
		
		JLabel emailLabel = new JLabel("Correo electrónico:");
		emailLabel.setBounds(230, 140, 170, 15);
		emailLabel.setFont(txt);
		dataPanel.add(emailLabel);

		RoundedJTextField clientEmail = new RoundedJTextField(20);
		clientEmail.setBounds(230, 160, 250, 27);
		clientEmail.setFont(fieldtxt);
		dataPanel.add(clientEmail);
		
		RoundedButton clientId = new RoundedButton("ID: #");
		clientId.setBounds(230, 200 ,90, 30);
		clientId.setBackground(gray);
		clientId.setForeground(Color.black);
		clientId.setBorderColor(border);
		clientId.setFont(txt);
		clientId.setRadius(20);
		dataPanel.add(clientId);
		
		JLabel surnameLabel = new JLabel("Apellidos:");
		surnameLabel.setBounds(520, 20, 170, 15);
		surnameLabel.setFont(txt);
		dataPanel.add(surnameLabel);

		RoundedJTextField clientSurname = new RoundedJTextField(20);
		clientSurname.setBounds(520, 40, 250, 27);
		clientSurname.setFont(fieldtxt);
		dataPanel.add(clientSurname);
		
		JLabel phoneLabel = new JLabel("Teléfono:");
		phoneLabel.setBounds(520, 80, 170, 15);
		phoneLabel.setFont(txt);
		dataPanel.add(phoneLabel);

		RoundedJTextField clientPhone = new RoundedJTextField(20);
		clientPhone.setBounds(520, 100, 250, 27);
		clientPhone.setFont(fieldtxt);
		dataPanel.add(clientPhone);
		
		JLabel fidelityLabel = new JLabel("Nivel de fidelidad:");
		fidelityLabel.setBounds(520, 140, 170, 15);
		fidelityLabel.setFont(txt);
		dataPanel.add(fidelityLabel);

		RoundedButton clientFidelity = new RoundedButton("Ninguno");
		clientFidelity.setBounds(520, 160, 250, 27);
		clientFidelity.setFont(fieldtxt);
		clientFidelity.setBackground(gray);
		clientFidelity.setForeground(Color.black);
		clientFidelity.setBorderColor(border);
		clientFidelity.setRadius(20);
		dataPanel.add(clientFidelity);
		
		RoundedButton cancelar = new RoundedButton("Cancelar");
		cancelar.setBounds(520, 200, 80, 30);
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
				ClientsController cc = new ClientsController();
				cc.clients();
			}
			
		});
		
		RoundedButton guardar = new RoundedButton("Guardar cliente");
		guardar.setBounds(660, 200, 110, 30);
		guardar.setBackground(blue);
		guardar.setFont(txt);
		guardar.setRadius(20);
		dataPanel.add(guardar);
		guardar.addActionListener(e ->{
			boolean camposValidos=true; 
			
			if(!Validaciones.validarCampoVacio(namefield)) camposValidos=false;	
			if(!Validaciones.validarCampoVacio(clientSurname)) camposValidos=false;
			if(!Validaciones.validarCampoVacio(clientBirth)) camposValidos= false;
			if(!Validaciones.validarCampoVacio(clientPhone)) camposValidos = false;
			if(!Validaciones.validarCampoVacio(clientEmail)) camposValidos=false;
			
			if(camposValidos) {
				Client cliente = new Client(namefield.getText(),clientSurname.getText(),clientEmail.getText(),clientPhone.getText(),clientBirth.getText(),photoBinario);
				ClientsController cc = new ClientsController();
				if(cc.add(cliente)) {
					System.out.println("Se agrego un cliente nuevo");
					JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.");
				}
			}
			frame.dispose();
			clients();
			
		});
		
		//Tablas
		JPanel tableTitles = new JPanel();
		tableTitles.setBounds(151, 321, 810, 50);
		tableTitles.setLayout(new GridLayout(0, 5, 0, 0));
		tableTitles.setBackground(Color.white);
		centro.add(tableTitles);
		
		JLabel activeRents = new JLabel("Rentas activas");
		activeRents.setHorizontalAlignment(SwingConstants.CENTER);
		activeRents.setFont(txt);
		tableTitles.add(activeRents);
		
		JLabel rentsHistory = new JLabel("Historial de rentas");
		rentsHistory.setHorizontalAlignment(SwingConstants.CENTER);
		rentsHistory.setFont(txt);
		tableTitles.add(rentsHistory);
		
		JLabel purchaseHistory = new JLabel("Historial de compras");
		purchaseHistory.setHorizontalAlignment(SwingConstants.CENTER);
		purchaseHistory.setFont(txt);
		tableTitles.add(purchaseHistory);

	}
	
	public void viewClient(Client client) {
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Detalles de cliente");
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
		
		RoundedButton titleButton = new RoundedButton("Detalles de cliente");
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
				clients();
			}
			
		});
		centro.add(titleButton);

		//ENTRADA DE DATOS
		RoundedPanel dataPanel = new RoundedPanel(30, new Color(255, 255, 255));
		dataPanel.setBounds(150, 65, 810, 245);
		dataPanel.setLayout(null);
		centro.add(dataPanel);
		
		foto = new RoundedButton();
		foto.setBounds(35, 20, 165, 185);
		foto.setBackground(gray);
		try {
			foto.setImageIcon(ImageUtils.getCoverAsIcon(client.photo, foto.getWidth(), foto.getHeight()));
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
		foto.setRadius(20);
		foto.setEnabled(false);
		dataPanel.add(foto);
		foto.addActionListener(e ->{
			if (!foto.isEnabled()) {
				return;
			}
			photoBinario = ImageUtils.imageToByte(foto, 5242880);
			if (photoBinario != null) {
				client.setPhoto(photoBinario);
				foto.setImageIcon(ImageUtils.byteToImageEsc(photoBinario, foto));
			}
		});
		
		JLabel nameLabel = new JLabel("Nombre:");
		nameLabel.setBounds(230, 20, 1700, 15);
		nameLabel.setFont(txt);
		dataPanel.add(nameLabel);
		
		namefield = new RoundedJTextField(20);
		namefield.setBounds(237, 40, 250, 27);
		namefield.setFont(fieldtxt);
		if(client.getName()!=null) {namefield.setText(client.getName());}
		namefield.setFocusable(false);
		dataPanel.add(namefield);
		
		JLabel birthLabel = new JLabel("Fecha de nacimiento(AAAA-MM-DD):");
		birthLabel.setBounds(230, 80, 250, 15);
		birthLabel.setFont(txt);
		dataPanel.add(birthLabel);
		
		clientBirth = new RoundedJTextField(20);
		clientBirth.setBounds(230, 100, 250, 27);
		clientBirth.setFont(fieldtxt);
		if(clientBirth!=null) {clientBirth.setText(client.getBirth_date());}
		clientBirth.setFocusable(false);
		clientBirth.addKeyListener(Validaciones.fechas());
		clientBirth.addKeyListener(Validaciones.limite(10));
		dataPanel.add(clientBirth);
		
		JLabel emailLabel = new JLabel("Correo electrónico:");
		emailLabel.setBounds(230, 140, 170, 15);
		emailLabel.setFont(txt);
		dataPanel.add(emailLabel);

		clientEmail = new RoundedJTextField(20);
		clientEmail.setBounds(230, 160, 250, 27);
		clientEmail.setFont(fieldtxt);
		if(clientEmail != null) {clientEmail.setText(client.getEmail());}
		clientEmail.setFocusable(false);
		dataPanel.add(clientEmail);
		
		clientId = new RoundedButton();
		clientId.setBounds(230, 200 ,90, 30);
		clientId.setBackground(gray);
		clientId.setForeground(Color.black);
		clientId.setBorderColor(border);
		if(client.getClient_id()>0) {clientId.setText("ID: #"+String.valueOf(client.getClient_id()));}
		clientId.setFont(txt);
		clientId.setRadius(20);
		dataPanel.add(clientId);
		
		JLabel surnameLabel = new JLabel("Apellidos:");
		surnameLabel.setBounds(520, 20, 170, 15);
		surnameLabel.setFont(txt);
		dataPanel.add(surnameLabel);

		clientSurname = new RoundedJTextField(20);
		clientSurname.setBounds(520, 40, 250, 27);
		clientSurname.setFont(fieldtxt);
		if(clientSurname != null) {clientSurname.setText(client.getLast_name());}
		clientSurname.setFocusable(false);
		dataPanel.add(clientSurname);
		
		JLabel phoneLabel = new JLabel("Teléfono:");
		phoneLabel.setBounds(520, 80, 170, 15);
		phoneLabel.setFont(txt);
		dataPanel.add(phoneLabel);

		clientPhone = new RoundedJTextField(20);
		clientPhone.setBounds(520, 100, 250, 27);
		clientPhone.setFont(fieldtxt);
		clientPhone.setFocusable(false);
		if(clientPhone != null) {clientPhone.setText(client.getPhone());}
		dataPanel.add(clientPhone);
		
		JLabel fidelityLabel = new JLabel("Nivel de fidelidad:");
		fidelityLabel.setBounds(520, 140, 170, 15);
		fidelityLabel.setFont(txt);
		dataPanel.add(fidelityLabel);

		clientFidelity = new RoundedButton();
		clientFidelity.setBounds(520, 160, 250, 27);
		clientFidelity.setFont(fieldtxt);
		clientFidelity.setBackground(gray);
		clientFidelity.setForeground(Color.black);
		if(clientFidelity != null) {clientFidelity.setText(client.getFidelity());}
		clientFidelity.setEnabled(false);
		clientFidelity.setBorderColor(border);
		clientFidelity.setRadius(20);
		dataPanel.add(clientFidelity);
		
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
				int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar este usuario?", 
						"Confirmar eliminación", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					int client_id = client.getClient_id(); 
					ClientsController cc = new ClientsController();

					if (cc.delete(client_id)) {
						JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito.");
						frame.dispose(); 
						clients(); 
					} else {
						JOptionPane.showMessageDialog(null, "Error al eliminar el usuario.", 
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		cancelar = new RoundedButton("Cancelar");
		cancelar.setBounds(520, 200,80, 30);
		cancelar.setBackground(Color.white);
		cancelar.setForeground(Color.black);
		cancelar.setBorderColor(border);
		cancelar.setFont(txt);
		cancelar.setRadius(20);
		dataPanel.add(cancelar);
		cancelar.setVisible(false);
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientsController cc = new ClientsController();
				frame.dispose();
				cc.clients();
				editable=false;
			}
			
		});
		
		guardar = new RoundedButton("Guardar cambios");
		guardar.setBounds(635, 200, 135, 30);
		guardar.setBackground(blue);
		guardar.setFont(txt);
		guardar.setRadius(20);
		dataPanel.add(guardar);
		guardar.setVisible(false); 
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					boolean camposValidos = true;

					if (!Validaciones.validarCampoVacio(namefield)) camposValidos = false;
					if (!Validaciones.validarCampoVacio(clientSurname)) camposValidos = false;
					if (!Validaciones.validarCampoVacio(clientEmail)) camposValidos = false;
					if (!Validaciones.validarCampoVacio(clientBirth)) camposValidos = false;
					if (!Validaciones.validarCampoVacio(clientPhone)) camposValidos = false;
					if (foto == null) {
						camposValidos = false;
					}

					if(camposValidos) {

						String name = namefield.getText();
						String last_name = clientSurname.getText();
						String email = clientEmail.getText();
						String birth_date = clientBirth.getText();
						String phone = clientPhone.getText();
						byte[] photo = photoBinario;

						Client updatedClient = new Client(name,last_name,email,phone,birth_date,photo);
						updatedClient.setClient_id(client.getClient_id());
						ClientsController controller = new ClientsController();
						boolean success = controller.update(updatedClient);

						if (success) {
							JOptionPane.showMessageDialog(null, "Película actualizada correctamente.");

							namefield.setFocusable(false);
							clientSurname.setFocusable(false);
							clientEmail.setFocusable(false);
							clientBirth.setFocusable(false);
							clientPhone.setFocusable(false);
							foto.setEnabled(false);
							guardar.setVisible(false);
							cancelar.setVisible(false);
							editable=false;
							dataPanel.repaint();
							
						} else {
							JOptionPane.showMessageDialog(null, "Error al actualizar la película.", "Error", JOptionPane.ERROR_MESSAGE);
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
		editar.setBounds(740, 200, 30, 30);
		editar.setBackground(Color.white);
		dataPanel.add(editar);
		editar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!editable) {
					edit();
				}
			}
		});
		
		
	}

	public void filterPanel(JPanel centro) {
		filtro = new RoundedPanel(30, new Color(255, 255, 255),3);
		filtro.setBounds(680, 115, 285, 230);
		filtro.setLayout(null);
		filtro.setVisible(false);
		
		JLabel tipo = new JLabel("Tipo");
		tipo.setBounds(35, 25, 100, 15);
		tipo.setFont(txt);
		filtro.add(tipo);
		
		CustomJRadioButton tipoSale = new CustomJRadioButton();
		tipoSale.setBounds(75, 45, 100, 30);
		tipoSale.setFont(txt);
		tipoSale.setText("Compra");
		filtro.add(tipoSale);
		
		CustomJRadioButton tipoRent = new CustomJRadioButton();
		tipoRent.setBounds(175, 45, 100, 30);
		tipoRent.setFont(txt);
		tipoRent.setText("Renta");
		filtro.add(tipoRent);
		
		JLabel producto = new JLabel("Producto");
		producto.setBounds(35, 100, 100, 15);
		producto.setFont(txt);
		filtro.add(producto);
		
		CustomJRadioButton productGame = new CustomJRadioButton();
		productGame.setBounds(75, 120, 100, 30);
		productGame.setFont(txt);
		productGame.setText("Videojuego");
		filtro.add(productGame);
		
		CustomJRadioButton productMovie = new CustomJRadioButton();
		productMovie.setBounds(175, 120, 100, 30);
		productMovie.setFont(txt);
		productMovie.setText("Película");
		filtro.add(productMovie);
		
		RoundedButton aplicar = new RoundedButton("Aplicar");
		aplicar.setBounds(160, 175, 85, 30);
		aplicar.setRadius(20);
		aplicar.setBackground(blue);
		filtro.add(aplicar);
		
		RoundedButton cerrar = new RoundedButton("Cerrar");
		cerrar.setBounds(50, 175, 85, 30);
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
	
	public void edit() {
		namefield.setFocusable(true);		
		clientBirth.setFocusable(true);		
		clientEmail.setFocusable(true);
		clientSurname.setFocusable(true);	
		clientPhone.setFocusable(true);
		foto.setEnabled(true);
		guardar.setVisible(true);
		cancelar.setVisible(true);
		editable=true;
		
	}

}
