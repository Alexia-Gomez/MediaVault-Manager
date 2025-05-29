package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controllers.HomeController;
import customClasses.Fuentes;
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
	Color field = new Color(250, 250, 250);

	Fuentes tipoFuentes = new Fuentes();
	Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 17f);
	Font btn = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 14f);
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 12f);

	ImageIcon lupa = new ImageIcon(MoviesView.class.getResource("/images/lupa.png"));
	ImageIcon mas = new ImageIcon(MoviesView.class.getResource("/images/mas.png"));
	ImageIcon filter = new ImageIcon(MoviesView.class.getResource("/images/filter.png"));
	ImageIcon arrow = new ImageIcon(MoviesView.class.getResource("/images/arrow.png"));
	
	public MoviesView() {

	}

	public void movies() {
		// VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 823, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);


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
		newMovie.setBounds(594, 11, 185, 43);
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

		//BARRA
		RoundedPanel barra = new RoundedPanel(30, new Color(255, 255, 255));
		barra.setBounds(151, 65, 626, 68);
		barra.setLayout(null);
		centro.add(barra);

		JLabel lupaIcon = new JLabel("");
		lupaIcon.setIcon(new ImageIcon(((ImageIcon) lupa).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		lupaIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lupaIcon.setBounds(20, 20, 30, 30);
		barra.add(lupaIcon);

		RoundedJTextField searchBar = new RoundedJTextField(20);
		searchBar.setBounds(65, 20, 348, 30);
		searchBar.setBackground(field);
		searchBar.setFont(txt);
		barra.add(searchBar);

		RoundedButton buscar = new RoundedButton("Buscar");
		buscar.setBounds(423, 20, 86, 30);
		buscar.setBackground(blue);
		buscar.setFont(btn);
		buscar.setRadius(20);
		barra.add(buscar);

		filterPanel(centro);
		
		RoundedButton filtrar = new RoundedButton("Filtrar");
		filtrar.setIcon(new ImageIcon(((ImageIcon) filter).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		filtrar.setHorizontalTextPosition(SwingConstants.LEFT);
		filtrar.setHorizontalAlignment(SwingConstants.CENTER);
		filtrar.setBounds(515, 20, 86, 30);
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
		tableTitles.setBounds(151, 144, 626, 29);
		tableTitles.setLayout(new GridLayout(0, 7, 0, 0));
		centro.add(tableTitles);

		JLabel lblNewLabel_1 = new JLabel("Título");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(lightGray);
		lblNewLabel_1.setFont(txt);
		tableTitles.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Estudio");
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
		/*JPanel tablePanel = new JPanel();
		tablePanel.setBounds(151, 184, 626, 322);
		tablePanel.setLayout(new BorderLayout(0, 0));
		tablePanel.setBackground(Color.white);
		centro.add(tablePanel);*/
		//TABLA
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(151, 184, 626, 322);
		tablePanel.setLayout(new GridLayout(0, 7, 0, 10)); // una fila por película
		tablePanel.setBackground(Color.white);
		centro.add(tablePanel);

		// Obtener datos de la BD
		MoviesModel model = new MoviesModel();
		ArrayList<Movie> movies = model.get();

		// Mostrar datos
		for (Movie movie : movies) {
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
		}

	}

	public void newMovie() {
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 823, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

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
	}

	public void filterPanel(JPanel centro) {
		filtro = new RoundedPanel(30, new Color(255, 255, 255),3);
		filtro.setBounds(510, 115, 265, 200);
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

	public void viewMovie() {
		//VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 823, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

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
				HomeController hc = new HomeController();
				hc.home();
			}

		});
		centro.add(titleButton);
	}
}
