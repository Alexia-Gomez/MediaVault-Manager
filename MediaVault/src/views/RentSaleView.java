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

import customClasses.Fuentes;
import customClasses.RoundedButton;
import customClasses.RoundedJTextField;
import customClasses.RoundedPanel;
import customClasses.SideBar;

public class RentSaleView {

	RoundedPanel filtro;
	
	Color blue = new Color(24, 130, 234);
	Color border = new Color(186, 186, 186);
	Color lightGray = new Color(117, 117, 117);
	Color field = new Color(250, 250, 250);

	Fuentes tipoFuentes = new Fuentes();
	Font btnNum = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 18f);
	Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 17f);
	Font btn = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 14f);
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 12f);
	Font table = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 11f);
	Font small = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 10f);
	
	
	ImageIcon lupa = new ImageIcon(ClientsView.class.getResource("/images/lupa.png"));
	ImageIcon mas = new ImageIcon(ClientsView.class.getResource("/images/mas.png"));
	ImageIcon filter = new ImageIcon(ClientsView.class.getResource("/images/filter.png"));
	ImageIcon iconoFrame = new ImageIcon(LoginView.class.getResource("/images/iconoPrincipal.PNG"));


	public RentSaleView() {

	}

	public void rentSale() {


		// VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Rentas y Compras");
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
		JPanel centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);

		RoundedPanel titlePanel = new RoundedPanel(30, new Color(255, 255, 255));
		titlePanel.setBounds(151, 11, 170, 43);
		centro.add(titlePanel);
		titlePanel.setLayout(new BorderLayout(0, 0));

		JLabel titleLabel = new JLabel("Rentas y compras");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(titles);
		titlePanel.add(titleLabel);
		
		//WIDGETS
			//w1
		RoundedPanel widget1 = new RoundedPanel(30, Color.WHITE);
		widget1.setBounds(324, 70, 120, 95);
		widget1.setLayout(null);
		centro.add(widget1);
		
		JLabel w1title = new JLabel("Rentas");
		w1title.setHorizontalAlignment(SwingConstants.CENTER);
		w1title.setBounds(0, 15, 120, 22);
		w1title.setFont(titles);
		widget1.add(w1title);
		
		JLabel w1num = new JLabel("127");
		w1num.setVerticalAlignment(SwingConstants.TOP);
		w1num.setHorizontalAlignment(SwingConstants.CENTER);
		w1num.setForeground(new Color(91, 161, 70));
		w1num.setBounds(0, 40, 120, 25);
		w1num.setFont(btnNum);
		widget1.add(w1num);
		
		JLabel w1date = new JLabel("06/06/2025");
		w1date.setHorizontalAlignment(SwingConstants.CENTER);
		w1date.setBounds(0, 65, 120, 14);
		w1date.setForeground(lightGray);
		w1date.setFont(small);
		widget1.add(w1date);
		
			//w2
		RoundedPanel widget2 = new RoundedPanel(30, Color.WHITE);
		widget2.setBounds(484, 70, 120, 95);
		widget2.setLayout(null);
		centro.add(widget2);
		
		JLabel w2title = new JLabel("Compras");
		w2title.setHorizontalAlignment(SwingConstants.CENTER);
		w2title.setBounds(0, 15, 120, 22);
		w2title.setFont(titles);
		widget2.add(w2title);
		
		JLabel w2num = new JLabel("249");
		w2num.setVerticalAlignment(SwingConstants.TOP);
		w2num.setHorizontalAlignment(SwingConstants.CENTER);
		w2num.setForeground(new Color(49, 113, 223));
		w2num.setBounds(0, 40, 120, 25);
		w2num.setFont(btnNum);
		widget2.add(w2num);
		
		JLabel w2date = new JLabel("06/06/2025");
		w2date.setHorizontalAlignment(SwingConstants.CENTER);
		w2date.setBounds(0, 65, 120, 14);
		w2date.setForeground(lightGray);
		w2date.setFont(small);
		widget2.add(w2date);
		
			//w3
		RoundedPanel widget3 = new RoundedPanel(30, Color.WHITE);
		widget3.setBounds(644, 70, 120, 95);
		widget3.setLayout(null);
		centro.add(widget3);
		
		JLabel w3title = new JLabel("Ingresos");
		w3title.setHorizontalAlignment(SwingConstants.CENTER);
		w3title.setBounds(0, 15, 120, 22);
		w3title.setFont(titles);
		widget3.add(w3title);
		
		JLabel w3num = new JLabel("$5,308");
		w3num.setVerticalAlignment(SwingConstants.TOP);
		w3num.setHorizontalAlignment(SwingConstants.CENTER);
		w3num.setForeground(new Color(87, 87, 87));
		w3num.setBounds(0, 40, 120, 25);
		w3num.setFont(btnNum);
		widget3.add(w3num);
		
		JLabel w3date = new JLabel("06/06/2025");
		w3date.setHorizontalAlignment(SwingConstants.CENTER);
		w3date.setBounds(0, 65, 120, 14);
		w3date.setForeground(lightGray);
		w3date.setFont(small);
		widget3.add(w3date);

		//BARRA
		RoundedPanel barra = new RoundedPanel(30, Color.WHITE);
		barra.setBounds(151, 180, 810, 50);
		barra.setLayout(null);
		centro.add(barra);

		JLabel lupaIcon = new JLabel("");
		lupaIcon.setIcon(new ImageIcon(((ImageIcon) lupa).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		lupaIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lupaIcon.setBounds(20, 10, 30, 30);
		barra.add(lupaIcon);

		RoundedJTextField searchBar = new RoundedJTextField(20);
		searchBar.setBounds(65, 10, 520, 30);
		searchBar.setBackground(field);
		searchBar.setFont(txt);
		barra.add(searchBar);

		RoundedButton buscar = new RoundedButton("Buscar");
		buscar.setBounds(600, 10, 86, 30);
		buscar.setBackground(blue);
		buscar.setFont(btn);
		buscar.setRadius(20);
		barra.add(buscar);

		filterPanel(centro);
		
		RoundedButton filtrar = new RoundedButton("Filtrar");
		filtrar.setIcon(new ImageIcon(((ImageIcon) filter).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		filtrar.setHorizontalTextPosition(SwingConstants.LEFT);
		filtrar.setHorizontalAlignment(SwingConstants.CENTER);
		filtrar.setBounds(700, 10, 86, 30);
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
		tableTitles.setBounds(151, 240, 810, 29);
		tableTitles.setLayout(new GridLayout(0, 6, 0, 0));
		centro.add(tableTitles);

		JLabel lblNewLabel_1 = new JLabel("ID operación");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(lightGray);
		lblNewLabel_1.setFont(table);
		tableTitles.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Cliente");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(lightGray);
		lblNewLabel_2.setFont(table);
		tableTitles.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Tipo");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setForeground(lightGray);
		lblNewLabel_3.setFont(table);
		tableTitles.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Producto");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setForeground(lightGray);
		lblNewLabel_4.setFont(table);
		tableTitles.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Fecha de operación");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setForeground(lightGray);
		lblNewLabel_5.setFont(table);
		tableTitles.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Monto");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setForeground(lightGray);
		lblNewLabel_6.setFont(table);
		tableTitles.add(lblNewLabel_6);

		//TABLA
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(151, 275, 810, 225);
		tablePanel.setLayout(new BorderLayout(0, 0));
		tablePanel.setBackground(Color.white);
		centro.add(tablePanel);
	}

	public void filterPanel(JPanel centro) {
		filtro = new RoundedPanel(30, new Color(255, 255, 255),3);
		filtro.setBounds(515, 220, 265, 200);
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
