package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
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

import controllers.ClientsController;
import controllers.MoviesController;
import controllers.OperationsController;
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
import models.Operation;

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
    
    RoundedButton foto,clientId,clientFidelity,guardar,cancelar, infoPDF;
    RoundedJTextField namefield,clientBirth,clientEmail,clientSurname,clientPhone;
    
    private JLabel title, product, date, discount, subtotal, total;
    
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
		//sidepanel.add(SideBar.rentaCompra(frame));
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
		//sidepanel.add(SideBar.rentaCompra(frame));
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
		//sidepanel.add(SideBar.rentaCompra(frame));
		sidepanel.add(SideBar.juegos(frame));
		sidepanel.add(SideBar.peliculas(frame));
		
		
		//PANEL CENTRO
		centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);
		
		RoundedButton titleButton = new RoundedButton("Información del cliente");
		titleButton.setIcon(new ImageIcon(((ImageIcon) arrow).getImage().getScaledInstance(15, 20, Image.SCALE_SMOOTH)));
		titleButton.setBounds(151, 11, 240, 43);
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
		
		infoPDF = new RoundedButton("Decargar información");
		infoPDF.setIcon(new ImageIcon(((ImageIcon) descarga).getImage().getScaledInstance(16, 19, Image.SCALE_SMOOTH)));
		infoPDF.setHorizontalTextPosition(SwingConstants.RIGHT);
		infoPDF.setIconTextGap(10);
		infoPDF.setBounds(520, 200, 180, 30);
		infoPDF.setBackground(blue);
		infoPDF.setFont(txt);
		infoPDF.setRadius(20);
		dataPanel.add(infoPDF);
		infoPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarPDF(client);
				
			}
			
		});

		
		RoundedButton eliminar = new RoundedButton("Eliminar usuario");
		eliminar.setIcon(new ImageIcon(((ImageIcon) delete).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		eliminar.setHorizontalTextPosition(SwingConstants.RIGHT);
		eliminar.setIconTextGap(10);
		eliminar.setBounds(150, 565, 160, 30);
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
							infoPDF.setVisible(true);
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
		
		
		JPanel pestañas = new JPanel();
		pestañas.setBounds(151, 321, 810, 50);
		pestañas.setLayout(new GridLayout(0, 5, 0, 0));
		pestañas.setBackground(Color.white);
		centro.add(pestañas);
		
		JButton activeRents = new JButton("Rentas activas");
		activeRents.setHorizontalAlignment(SwingConstants.CENTER);
		activeRents.setBorder(null);
		activeRents.setBackground(Color.white);
		activeRents.setFont(txt);
		pestañas.add(activeRents);
		
		JButton rentsHistory = new JButton("Historial de rentas");
		rentsHistory.setHorizontalAlignment(SwingConstants.CENTER);
		rentsHistory.setBorder(null);
		rentsHistory.setBackground(Color.white);
		rentsHistory.setFont(txt);
		pestañas.add(rentsHistory);
		
		JButton purchaseHistory = new JButton("Historial de compras");
		purchaseHistory.setHorizontalAlignment(SwingConstants.CENTER);
		purchaseHistory.setBorder(null);
		purchaseHistory.setBackground(Color.white);
		purchaseHistory.setFont(txt);
		pestañas.add(purchaseHistory);
		
		
		
		//Titulos tabla
		JPanel tableTitles = new JPanel();
		tableTitles.setBounds(151, 370, 810, 30);
		tableTitles.setLayout(new GridLayout(0, 6, 0, 0));
		centro.add(tableTitles);
		
		title = new JLabel("Título");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(lightGray);
		title.setVisible(false);
		title.setFont(txt);
		tableTitles.add(title);
		
		product = new JLabel("Producto");
		product.setHorizontalAlignment(SwingConstants.CENTER);
		product.setForeground(lightGray);
		product.setVisible(false);
		product.setFont(txt);
		tableTitles.add(product);
		
		date = new JLabel("");
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setForeground(lightGray);
		date.setVisible(false);
		date.setFont(txt);
		tableTitles.add(date);
		
		discount = new JLabel("Descuento");
		discount.setHorizontalAlignment(SwingConstants.CENTER);
		discount.setForeground(lightGray);
		discount.setVisible(false);
		discount.setFont(txt);
		tableTitles.add(discount);
		
		subtotal = new JLabel("Importe");
		subtotal.setHorizontalAlignment(SwingConstants.CENTER);
		subtotal.setForeground(lightGray);
		subtotal.setVisible(false);
		subtotal.setFont(txt);
		tableTitles.add(subtotal);
		
		total = new JLabel("Importe final");
		total.setHorizontalAlignment(SwingConstants.CENTER);
		total.setForeground(lightGray);
		total.setVisible(false);
		total.setFont(txt);
		tableTitles.add(total);

		
		//tablas
		
		JPanel tablePanelContainer = new JPanel(new CardLayout());
        tablePanelContainer.setBounds(151, 400, 810, 150);
        centro.add(tablePanelContainer);
		
		JPanel panelActivas = new JPanel();
		panelActivas.setBounds(151, 400, 810, 150);
		panelActivas.setLayout(new BorderLayout(0, 0));
		//centro.add(panelActivas);
		
		JPanel panelRentas = new JPanel();
		panelRentas.setBounds(151, 400, 810, 150);
		panelRentas.setLayout(new BorderLayout(0, 0));
		//centro.add(panelRentas);
		
		JPanel panelCompras = new JPanel();
		panelCompras.setBounds(151, 400, 810, 150);
		panelCompras.setLayout(new BorderLayout(0, 0));

		OperationsController opController = new OperationsController(); 
		System.out.println("Client ID: " + client.getClient_id());
		List<Operation> allOperations = opController.getOperationsByClientId(client.getClient_id());
		for (Operation op : allOperations) {
		    System.out.println("Op -> tipo: " + op.getOperation_type() + ", título: " + op.getProductTitle());
		}
		
		List<Operation> rentHistory = new ArrayList<>();
		List<Operation> purchaseHistoryy = new ArrayList<>();

		for (Operation op : allOperations) {
		    if ("rent".equalsIgnoreCase(op.getOperation_type())) { 
		        rentHistory.add(op);
		    } else if ("sale".equalsIgnoreCase(op.getOperation_type())) { 
		        purchaseHistoryy.add(op);
		    }
		}
		System.out.println("Total operations fetched: " + allOperations.size()); 

		
		
		//tabla rentas
		String[] rentColumnNames = {"Título", "Producto", "Fecha de renta", "Descuento","Precio Unitario", "Precio Final"};

		DefaultTableModel rentModel = new DefaultTableModel(rentColumnNames, 0);

		for (Operation rent : rentHistory) {
		    String productType = (rent.getMovie() != null) ? "movie" : "videogame";
		    String descuento = null;
	        if(client.getFidelity().equalsIgnoreCase("aficionado")) {
	        	descuento="15%";
	        }
	        else if(client.getFidelity().equalsIgnoreCase("frecuente")) {
	        	descuento="20%";
	        }
	        else {
	        	descuento = "Ninguno";
	        }
		    
		    Object[] rowData = {
		        rent.getProductTitle(),
		        productType,
		        rent.getOperation_date(),
		        descuento,
		        "$" + String.format("%.2f", rent.getPrecioUnitario()),
		        "$" + String.format("%.2f", rent.getPrecioFinal())
		        
		    };
		    rentModel.addRow(rowData);
		}

		JTable rentHistoryTable = new JTable(rentModel);
		rentHistoryTable.setFont(fieldtxt);
		rentHistoryTable.setRowHeight(50);
		rentHistoryTable.setShowVerticalLines(false);
		rentHistoryTable.setTableHeader(null);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		rentHistoryTable.setDefaultRenderer(Object.class, centerRenderer);
		
		CustomScrollPane scrollPane = new CustomScrollPane();
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportView(rentHistoryTable);		

		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBar());
		panelRentas.add(scrollPane);

		
		
		//tabla compras
		String[] purchaseColumnNames = {"Título", "Producto", "Fecha de compra", "Descuento","Precio Unitario", "Precio Final"};

		DefaultTableModel purchaseModel = new DefaultTableModel(purchaseColumnNames, 0);

		for (Operation purchase : purchaseHistoryy) {
		    String productType = (purchase.getMovie() != null) ? "movie" : "videogame";
		    String descuento = null;
	        if(client.getFidelity().equalsIgnoreCase("aficionado")) {
	        	descuento="15%";
	        }
	        else if(client.getFidelity().equalsIgnoreCase("frecuente")) {
	        	descuento="20%";
	        }
	        else {
	        	descuento = "Ninguno";
	        }
	        
		    Object[] rowData = {
		        purchase.getProductTitle(),
		        productType,
		        purchase.getOperation_date(),
		        descuento,
		        "$" + String.format("%.2f", purchase.getPrecioUnitario()),
		        "$" + String.format("%.2f", purchase.getPrecioFinal())
		    };
		    purchaseModel.addRow(rowData);
		}
		
		
		JTable purchaseHistoryTable = new JTable(purchaseModel);
		purchaseHistoryTable.setFont(fieldtxt);
		purchaseHistoryTable.setRowHeight(50);
		purchaseHistoryTable.setTableHeader(null);
		purchaseHistoryTable.setShowVerticalLines(false);
		
		purchaseHistoryTable.setDefaultRenderer(Object.class, centerRenderer);
		
		CustomScrollPane scrollPane2 = new CustomScrollPane();
		scrollPane2.setBorder(BorderFactory.createEmptyBorder());
		scrollPane2.setViewportView(purchaseHistoryTable);		

		scrollPane2.getVerticalScrollBar().setUI(new CustomScrollBar());
		panelCompras.add(scrollPane2);
		
		tablePanelContainer.add(panelActivas, "RENTAS_ACTIVAS");
        tablePanelContainer.add(panelRentas, "HISTORIAL_RENTAS");
        tablePanelContainer.add(panelCompras, "HISTORIAL_COMPRAS");
		
        CardLayout cl = (CardLayout) tablePanelContainer.getLayout();
        
        
        activeRents.addActionListener(e -> {
        	setSelectedTab(activeRents, rentsHistory, purchaseHistory);
		    cl.show(tablePanelContainer, "RENTAS_ACTIVAS");
		    titulosTablas("activas");
		});
		
        rentsHistory.addActionListener(e -> {
        	 setSelectedTab(rentsHistory, activeRents, purchaseHistory);
            cl.show(tablePanelContainer, "HISTORIAL_RENTAS");
            titulosTablas("rentas");
        });

        purchaseHistory.addActionListener(e -> {
            setSelectedTab(purchaseHistory, activeRents, rentsHistory);
            cl.show(tablePanelContainer, "HISTORIAL_COMPRAS");
            titulosTablas("compras");
        });
		
	}

	private void titulosTablas(String tipo) {
	    switch (tipo) {
	        case "activas":
	            title.setVisible(true);
	            product.setVisible(false);
	            date.setText("Fecha de renta");
	            date.setVisible(true);
	            discount.setVisible(false);
	            subtotal.setVisible(false);
	            total.setVisible(false);
	            break;
	        case "rentas":
	        	title.setVisible(true);
	        	product.setVisible(true);
	        	date.setText("Fecha de renta");
	        	date.setVisible(true);
	            discount.setVisible(true);
	            subtotal.setVisible(true);
	            total.setVisible(true);
	            break;
	        case "compras":
	        	title.setVisible(true);
	        	product.setVisible(true);
	        	date.setText("Fecha de compra");
	        	date.setVisible(true);
	            discount.setVisible(true);
	            subtotal.setVisible(true);
	            total.setVisible(true);
	            break;
	        default:
	            break;
	    }
	}
	
	private void setSelectedTab(JButton selected, JButton... others) {
	    MatteBorder underline = BorderFactory.createMatteBorder(0, 0, 2, 0, blue); 

	    selected.setBorder(underline);
	    for (JButton btn : others) {
	        btn.setBorder(null); 
	    }
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
		infoPDF.setVisible(false);
		editable=true;
		
	}
	
	public void generarPDF(Client client) {
		JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Guardar ficha de película como...");
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
	            
	            Paragraph pt = new Paragraph("Información de cliente")
	            		.setFontSize(20)
	            		.setTextAlignment(TextAlignment.CENTER);
	            document.add(pt);

	            
	            document.add(espacio);
	            
	            byte[] imagenBytes = client.getPhoto();
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
	                    .add(new Text("Datos personales"))
	                    .add(new Text("\nNombre: "+client.getName()))
	                    .add(new Text("\nApellidos: "+client.getLast_name()))
	                    .add(new Text("\nFecha de nacimiento: "+client.getBirth_date()))
	                    .add(new Text("\nNivel de fidelidad:"+client.getFidelity()))
	                    .add(new Text("\n\nContacto"))
	                    .add(new Text("\nTeléfono: "+client.getPhone()))
	                    .add(new Text("\nCorreo electrónico: "+client.getEmail()));

	            Cell celdaDatos = new Cell()
	            		.add(datos)
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

	            
	            document.close();

		        JOptionPane.showMessageDialog(null, "PDF generado correctamente.");


	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
	}
	}

}
