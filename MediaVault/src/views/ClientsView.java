package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
	Color field = new Color(250, 250, 250);

	Fuentes tipoFuentes = new Fuentes();
	Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 17f);
	Font btn = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 14f);
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 12f);
	
	ImageIcon lupa = new ImageIcon(ClientsView.class.getResource("/images/lupa.png"));
	ImageIcon mas = new ImageIcon(ClientsView.class.getResource("/images/mas.png"));
	ImageIcon filter = new ImageIcon(ClientsView.class.getResource("/images/filter.png"));
	ImageIcon arrow = new ImageIcon(ClientsView.class.getResource("/images/arrow.png"));
	ImageIcon iconoFrame = new ImageIcon(LoginView.class.getResource("/images/iconoPrincipal.PNG"));

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
