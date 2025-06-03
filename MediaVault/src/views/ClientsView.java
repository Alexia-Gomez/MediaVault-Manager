package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controllers.ClientsController;
import customClasses.CustomJComboBox;
import customClasses.CustomJRadioButton;
import customClasses.Fuentes;
import customClasses.RoundedButton;
import customClasses.RoundedJTextField;
import customClasses.RoundedPanel;
import customClasses.SideBar;

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
		
		RoundedButton clientId = new RoundedButton("ID: #12345");
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

		CustomJComboBox clientFidelity = new CustomJComboBox();
		clientFidelity.setModel( new DefaultComboBoxModel( new String[] { "Ninguno", "Cliente frecuente", "Aficionado" }));
		clientFidelity.setBounds(520, 160, 250, 27);
		clientFidelity.setFont(fieldtxt);
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

}
