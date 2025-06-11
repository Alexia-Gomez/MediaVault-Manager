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

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import controllers.ClientsController;
import controllers.GamesController;
import controllers.LoginController;
import controllers.MoviesController;
import controllers.OperationsController;
import customClasses.Fuentes;
import customClasses.RoundedButton;
import customClasses.RoundedPanel;
import customClasses.SideBar;
import models.Game;
import models.Movie;

public class HomeView {
	
	Color blue = new Color(24, 130, 234);
	Color lightGray = new Color(117, 117, 117);
	Color gray = new Color(51, 51, 51);
	
	Fuentes tipoFuentes = new Fuentes();
	Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 17f);
	Font subtitles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 13f);
	Font btntxt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 15f);
	Font btnNum = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 25f);
	Font small = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 10f);
	Font extra = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 10f);
	
	ImageIcon logo = new ImageIcon(HomeView.class.getResource("/images/logo.png"));
	ImageIcon logOut = new ImageIcon(HomeView.class.getResource("/images/logOut.png"));
	ImageIcon adminIcon = new ImageIcon(HomeView.class.getResource("/images/adminIcon.png"));
	ImageIcon iconoFrame = new ImageIcon(LoginView.class.getResource("/images/iconoPrincipal.PNG"));

	GamesController gc = new GamesController();
	MoviesController mc = new MoviesController();
	ClientsController cc = new ClientsController();
	OperationsController oc = new OperationsController();
	
	JLabel movieTitle2, movieTitle, gameTitle2, gameTitle;
	RoundedButton gameBtn1,gameBtn2, movieBtn1, movieBtn2; 
	
	private JLabel w1num, w3num, w2num;

	public HomeView() {

	}

	public void home() {

		// VENTANA
		JFrame frame = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Dashboard");
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


		// PANEL CENTRO
		JPanel centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);
		
			//BANNER
		RoundedPanel banner = new RoundedPanel(10, Color.white);
		banner.setBounds(140, 0, 830, 80);
		centro.add(banner);
		banner.setLayout(null);

		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(331, 5, 195, 65);
		logoLabel.setIcon(new ImageIcon(((ImageIcon) logo).getImage().getScaledInstance(200, 130, Image.SCALE_SMOOTH)));
		banner.add(logoLabel);


		JLabel logOutLabel = new JLabel("Cerrar Sesión");
		logOutLabel.setHorizontalAlignment(SwingConstants.LEFT);
		logOutLabel.setForeground(gray);
		logOutLabel.setBounds(724, 45, 77, 14);
		logOutLabel.setFont(small);
		banner.add(logOutLabel);

		JLabel logOutIcon = new JLabel("");
		logOutIcon.setIcon(new ImageIcon(((ImageIcon) logOut).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		logOutIcon.setBounds(795, 40, 20, 20);
		logOutIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				frame.dispose();
				LoginController lc = new LoginController();
				lc.login();
			}
		});
		banner.add(logOutIcon);

		JLabel adminPhoto = new JLabel("");
		adminPhoto.setIcon(new ImageIcon(((ImageIcon) adminIcon).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
		adminPhoto.setHorizontalAlignment(SwingConstants.CENTER);
		adminPhoto.setBounds(665, 15, 49, 49);
		banner.add(adminPhoto);

		JLabel extralabel = new JLabel("Bienvenido");
		extralabel.setBounds(725, 15, 76, 14);
		extralabel.setFont(extra);
		banner.add(extralabel);

		JLabel adminName = new JLabel("Admin");
		adminName.setBounds(725, 30, 49, 14);
		adminName.setFont(extra);
		banner.add(adminName);
		
			//WIDGETS SUP
		//w1
		RoundedPanel widget1 = new RoundedPanel(15, Color.white, 3);
		widget1.setBounds(210, 105, 200, 190);
		widget1.setLayout(null);
		centro.add(widget1);
		
		JLabel w1title = new JLabel("Rentas");
		w1title.setHorizontalAlignment(SwingConstants.CENTER);
		w1title.setBounds(15, 30, 170, 20);
		w1title.setFont(titles);
		widget1.add(w1title);
		
		RoundedButton w1btn = new RoundedButton("Ver");
		w1btn.setForeground(Color.white);
		w1btn.setBounds(60, 130, 90, 30);
		w1btn.setBackground(blue);
		w1btn.setFont(btntxt);
		w1btn.setRadius(20);
		widget1.add(w1btn);
		w1btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				oc.operations();
			}
			
		});
		
		
		w1num = new JLabel("");
		w1num.setVerticalAlignment(SwingConstants.TOP);
		w1num.setHorizontalAlignment(SwingConstants.CENTER);
		w1num.setForeground(new Color(91, 161, 70));
		w1num.setBounds(15,70, 170, 35);
		w1num.setFont(btnNum);
		widget1.add(w1num);
		
		
		//w2	
		RoundedPanel widget2 = new RoundedPanel(15, Color.WHITE, 3);
		widget2.setBounds(465, 105, 200, 190);
		widget2.setLayout(null);
		centro.add(widget2);
		
		JLabel w2title = new JLabel("Compras");
		w2title.setHorizontalAlignment(SwingConstants.CENTER);
		w2title.setBounds(15, 30, 170, 20);
		w2title.setFont(titles);
		widget2.add(w2title);
		
		RoundedButton w2btn = new RoundedButton("Ver");
		w2btn.setBounds(60, 130, 90, 30);
		w2btn.setForeground(Color.WHITE);
		w2btn.setBackground(blue);
		w2btn.setFont(btntxt);
		w2btn.setRadius(20);
		widget2.add(w2btn);
		w2btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				oc.operations();
			}
			
		});
		
		w2num = new JLabel("");
		w2num.setVerticalAlignment(SwingConstants.TOP);
		w2num.setHorizontalAlignment(SwingConstants.CENTER);
		w2num.setForeground(new Color(49, 113, 223));
		w2num.setBounds(15, 70, 170, 35);
		w2num.setFont(btnNum);
		widget2.add(w2num);
		
		
		//w3
		RoundedPanel widget3 = new RoundedPanel(15, Color.WHITE, 3);
		widget3.setBounds(710, 105, 200, 190);
		widget3.setLayout(null);
		centro.add(widget3);
		
		JLabel w3title = new JLabel("Clientes");
		w3title.setHorizontalAlignment(SwingConstants.CENTER);
		w3title.setBounds(15, 30, 170, 20);
		w3title.setFont(titles);
		widget3.add(w3title);
		
		RoundedButton w3btn = new RoundedButton("Ver");
		w3btn.setBounds(60, 130, 90, 30);
		w3btn.setForeground(Color.WHITE);
		w3btn.setBackground(blue);
		w3btn.setFont(btntxt);
		w3btn.setRadius(20);
		w3btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				cc.clients();
			}
			
		});
		widget3.add(w3btn);
		
		w3num = new JLabel("");
		w3num.setHorizontalAlignment(SwingConstants.CENTER);
		w3num.setVerticalAlignment(SwingConstants.TOP);
		w3num.setForeground(new Color(66, 194, 168));
		w3num.setBounds(15, 70, 170, 35);
		w3num.setFont(btnNum);
		widget3.add(w3num);
		
			//WIDGETS INF
		//games
		RoundedPanel gamePanel = new RoundedPanel(15, Color.white, 3);
		gamePanel.setBounds(180, 320, 360, 260);
		centro.add(gamePanel);
		gamePanel.setLayout(null);
		
		JLabel gPanelTitle = new JLabel("Videojuegos");
		gPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		gPanelTitle.setBounds(35, 25, 290, 25);
		gPanelTitle.setFont(titles);
		gamePanel.add(gPanelTitle);
		
		
		JLabel gameSubtitle1 = new JLabel("Más rentado");
		gameSubtitle1.setHorizontalAlignment(SwingConstants.CENTER);
		gameSubtitle1.setBounds(45, 65, 93, 15);
		gameSubtitle1.setForeground(gray);
		gameSubtitle1.setFont(subtitles);
		gamePanel.add(gameSubtitle1);
		
		gameTitle = new JLabel("");
		gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		gameTitle.setBounds(50, 110, 90, 50);
		gameTitle.setForeground(gray);
		gameTitle.setFont(subtitles);
		gamePanel.add(gameTitle);


		JLabel gameSubtitle2 = new JLabel("Más comprado");
		gameSubtitle2.setHorizontalAlignment(SwingConstants.CENTER);
		gameSubtitle2.setBounds(220, 65, 93, 15);
		gameSubtitle2.setForeground(gray);
		gameSubtitle2.setFont(subtitles);
		gamePanel.add(gameSubtitle2);
		
		gameBtn1 = new RoundedButton("Ver");
		gameBtn1.setBounds(45, 205, 90, 30);
		gameBtn1.setForeground(Color.WHITE);
		gameBtn1.setBackground(blue);
		gameBtn1.setFont(btntxt);
		gameBtn1.setRadius(20);
		gamePanel.add(gameBtn1);

		
		gameTitle2 = new JLabel("");
		gameTitle2.setHorizontalAlignment(SwingConstants.CENTER);
		gameTitle2.setBounds(230, 110, 90, 50);
		gameTitle2.setForeground(gray);
		gameTitle2.setFont(subtitles);
		gamePanel.add(gameTitle2);
		
		gameBtn2 = new RoundedButton("Ver");
		gameBtn2.setBounds(220, 205, 90, 30);
		gameBtn2.setForeground(Color.WHITE);
		gameBtn2.setBackground(blue);
		gameBtn2.setFont(btntxt);
		gameBtn2.setRadius(20);
		gamePanel.add(gameBtn2);
		
		
		//movies
		RoundedPanel moviePanel = new RoundedPanel(15, Color.WHITE, 3);
		moviePanel.setBounds(575, 320, 360, 260);
		centro.add(moviePanel);
		moviePanel.setLayout(null);
		
		JLabel mPanelTitle = new JLabel("Películas");
		mPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mPanelTitle.setBounds(35, 25, 290, 25);
		mPanelTitle.setFont(titles);
		moviePanel.add(mPanelTitle);

		
		JLabel movieSubtitle1 = new JLabel("Más rentado");
		movieSubtitle1.setHorizontalAlignment(SwingConstants.CENTER);
		movieSubtitle1.setBounds(45, 65, 93, 15);
		movieSubtitle1.setFont(subtitles);
		movieSubtitle1.setForeground(gray);
		moviePanel.add(movieSubtitle1);
		
		movieTitle = new JLabel("");
		movieTitle.setHorizontalAlignment(SwingConstants.CENTER);
		movieTitle.setBounds(45, 110, 90, 50);
		movieTitle.setForeground(gray);
		movieTitle.setFont(subtitles);
		moviePanel.add(movieTitle);
		
		
		JLabel movieSubtitle2 = new JLabel("Más comprado");
		movieSubtitle2.setHorizontalAlignment(SwingConstants.CENTER);
		movieSubtitle2.setBounds(220, 65, 95, 15);
		movieSubtitle2.setFont(subtitles);
		movieSubtitle2.setForeground(gray);
		moviePanel.add(movieSubtitle2);
		
		movieTitle2 = new JLabel("");
		movieTitle2.setHorizontalAlignment(SwingConstants.CENTER);
		movieTitle2.setBounds(220, 110, 90, 50);
		movieTitle2.setForeground(gray);
		movieTitle2.setFont(subtitles);
		moviePanel.add(movieTitle2);
		
		movieBtn1 = new RoundedButton("Ver");
		movieBtn1.setBounds(43, 205, 90, 30);
		movieBtn1.setForeground(Color.WHITE);
		movieBtn1.setBackground(blue);
		movieBtn1.setFont(btntxt);
		movieBtn1.setRadius(20);
		moviePanel.add(movieBtn1);
		
		movieBtn2 = new RoundedButton("Ver");
		movieBtn2.setForeground(Color.WHITE);
		movieBtn2.setBounds(220, 205, 90, 30);
		movieBtn2.setBackground(blue);
		movieBtn2.setFont(btntxt);
		movieBtn2.setRadius(20);
		moviePanel.add(movieBtn2);
		
		frame.setVisible(true);
	    
	    SwingUtilities.invokeLater(() -> {
	        JDialog dlg = new JDialog(frame, "Cargando...", true); // modal
	        dlg.setUndecorated(true);
	        JPanel p = new JPanel(new BorderLayout(5,5));
	        JLabel label = new JLabel("Cargando, espere unos segundos...", SwingConstants.CENTER);
	        label.setFont(titles);
	        p.add(label);
	        dlg.setContentPane(p);
	        dlg.pack();
	        dlg.setLocationRelativeTo(frame);

	        // Crear SwingWorker
	        SwingWorker<Void, Void> worker = new SwingWorker<>() {
	            Game juegoRentado, juegoComprado;
	            Movie peliculaRentada, peliculaComprada;
	            int totalRents, totalSales, cantClientes;

	            @Override
	            protected Void doInBackground() throws Exception {
	                // Operaciones pesadas acá, NO en EDT
	                juegoRentado = oc.getMostRentedGame();
	                juegoComprado = oc.getMostPurchasedGame();
	                peliculaRentada = oc.getMostRentedMovie();
	                peliculaComprada = oc.getMostPurchasedMovie();
	                totalRents = oc.getTotalRents();
	                totalSales = oc.getTotalSales();
	                cantClientes = cc.getCantClients();
	                return null;
	            }

	            @Override
	            protected void done() {
	                dlg.dispose();  // Cierra diálogo
	                datos(frame, juegoRentado, juegoComprado, peliculaRentada, peliculaComprada, totalRents, totalSales, cantClientes);
	            }
	        };

	        worker.execute();
	        dlg.setVisible(true);  // Esto bloqueará aquí hasta que worker termine y cierre el diálogo
	    });
	}
	
	public void actualizarCantClientes() {
		int cantidad = cc.getCantClients();
	    w3num.setText(String.format("%,d", cantidad));
	}
	
	public void actualizarCantCompras() {
		int totalSales = oc.getTotalSales();
	    w2num.setText(String.valueOf(totalSales));
	}
	
	public void actualizarCantRentas() {
		int totalRents = oc.getTotalRents();
	    w2num.setText(String.valueOf(totalRents));
	}

	public void datos(JFrame frame, Game juegoRentado, Game juegoComprado, Movie peliculaRentada, Movie peliculaComprada, int totalRents, int totalSales, int cantClientes) {
		
		w1num.setText(String.valueOf(totalRents));
		w2num.setText((String.valueOf(totalSales)));
		w3num.setText(String.format("%,d", cc.getCantClients()));
		gameTitle.setText("<html>" + juegoComprado.getTitle() + "</html>");
		gameTitle2.setText("<html>" + juegoRentado.getTitle() + "</html>");
		movieTitle.setText("<html>" + peliculaRentada.getTitle() + "</html>");
		movieTitle2.setText("<html>" + peliculaComprada.getTitle() + "</html>");
		
		/*JLabel lblCover = new JLabel();
		lblCover.setBounds(45, 85, 90, 95); 
		try {
			ImageIcon icon = juegoRentado.getCoverAsIcon(lblCover.getWidth(), lblCover.getHeight());
			lblCover.setIcon(icon);
		} catch (NullPointerException ex) {
			System.out.println("No hay imagen disponible: " + ex.getMessage());
		}
		gamePanel.add(lblCover);*/
		
		gameBtn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				gc.viewGame(juegoRentado);
			}
			
		});
		gameBtn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				gc.viewGame(juegoComprado);
			}
			
		});
		movieBtn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mc.viewMovies(peliculaRentada);
			}
			
		});
		movieBtn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				mc.viewMovies(peliculaComprada);
			}
			
		});
		
		frame.repaint();
		frame.revalidate();
		
	}
}
