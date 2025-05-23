package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controllers.ClientsController;
import controllers.GamesController;
import controllers.LoginController;
import controllers.MoviesController;
import controllers.RentSaleController;
import customClasses.Fuentes;
import customClasses.RoundedButton;
import customClasses.RoundedPanel;
import customClasses.SideBar;

public class HomeView {
	
	Color blue = new Color(24, 130, 234);
	Color lightGray = new Color(117, 117, 117);
	Color Gray = new Color(51, 51, 51);
	
	Fuentes tipoFuentes = new Fuentes();
	Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 17f);
	Font subtitles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 13f);
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 12f);
	Font btntxt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 15f);
	Font btnNum = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 18f);
	Font small = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 10f);
	Font extra = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 10f);
	
	ImageIcon logo = new ImageIcon(HomeView.class.getResource("/images/logo.png"));
	ImageIcon logOut = new ImageIcon(HomeView.class.getResource("/images/logOut.png"));
	ImageIcon adminIcon = new ImageIcon(HomeView.class.getResource("/images/admin.png"));
	

	GamesController gc = new GamesController();
	MoviesController mc = new MoviesController();
	RentSaleController rc = new RentSaleController();
	ClientsController cc = new ClientsController();

	public HomeView() {

	}

	public void home() {

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


		// PANEL CENTRO
		JPanel centro = new JPanel();
		centro.setBounds(0, 0, 809, 606);
		frame.getContentPane().add(centro);
		centro.setLayout(null);
		
			//BANNER
		RoundedPanel banner = new RoundedPanel(10, Color.white);
		banner.setBounds(140, 0, 654, 75);
		centro.add(banner);
		banner.setLayout(null);

		JLabel logoLabel = new JLabel("");
		logoLabel.setBounds(233, 5, 188, 64);
		logoLabel.setIcon(new ImageIcon(((ImageIcon) logo).getImage().getScaledInstance(200, 130, Image.SCALE_SMOOTH)));
		banner.add(logoLabel);


		JLabel logOutLabel = new JLabel("Cerrar Sesión");
		logOutLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		logOutLabel.setForeground(new Color(51, 51, 51));
		logOutLabel.setBounds(526, 47, 88, 14);
		logOutLabel.setFont(small);
		banner.add(logOutLabel);

		JLabel logOutIcon = new JLabel("");
		logOutIcon.setIcon(new ImageIcon(((ImageIcon) logOut).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		logOutIcon.setBounds(620, 44, 20, 20);
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
		adminPhoto.setBounds(494, 15, 49, 49);
		banner.add(adminPhoto);

		JLabel extralabel = new JLabel("Bienvenido");
		extralabel.setBounds(553, 15, 76, 14);
		extralabel.setFont(extra);
		banner.add(extralabel);

		JLabel adminName = new JLabel("Admin");
		adminName.setBounds(553, 29, 49, 14);
		adminName.setFont(extra);
		banner.add(adminName);
		
			//WIDGETS SUP
		//w1
		RoundedPanel widget1 = new RoundedPanel(15, Color.white, 3);
		widget1.setLocation(175, 112);
		widget1.setSize(170,160);
		centro.add(widget1);
		widget1.setLayout(null);
		
		JLabel w1title = new JLabel("Rentas");
		w1title.setHorizontalAlignment(SwingConstants.CENTER);
		w1title.setBounds(0, 23, 170, 15);
		w1title.setFont(titles);
		widget1.add(w1title);
		
		RoundedButton w1btn = new RoundedButton("Ver");
		w1btn.setForeground(new Color(255, 255, 255));
		w1btn.setLocation(45, 108);
		w1btn.setFont(btntxt);
		w1btn.setRadius(20);
		w1btn.setBackground(blue);
		w1btn.setSize(80, 30);
		w1btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				rc.rentSale();
			}
			
		});
		widget1.add(w1btn);
		
		
		JLabel w1num = new JLabel("127");
		w1num.setVerticalAlignment(SwingConstants.TOP);
		w1num.setHorizontalAlignment(SwingConstants.CENTER);
		w1num.setForeground(new Color(91, 161, 70));
		w1num.setBounds(0, 48, 170, 35);
		w1num.setFont(btnNum);
		widget1.add(w1num);
		
		JLabel w1date = new JLabel("06/06/2025");
		w1date.setHorizontalAlignment(SwingConstants.CENTER);
		w1date.setBounds(0, 83, 170, 14);
		w1date.setForeground(lightGray);
		w1date.setFont(small);
		widget1.add(w1date);
		
		
		//w2
		RoundedPanel widget2 = new RoundedPanel(15, Color.WHITE, 3);
		widget2.setBounds(384, 112, 170, 160);
		widget2.setLayout(null);
		centro.add(widget2);
		
		JLabel w2title = new JLabel("Compras");
		w2title.setHorizontalAlignment(SwingConstants.CENTER);
		w2title.setBounds(0, 23, 170, 15);
		w2title.setFont(titles);
		widget2.add(w2title);
		
		RoundedButton w2btn = new RoundedButton("Ver");
		w2btn.setBounds(46, 108, 80, 30);
		w2btn.setForeground(Color.WHITE);
		w2btn.setBackground(blue);
		w2btn.setFont(btntxt);
		w2btn.setRadius(20);
		w2btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				rc.rentSale();
			}
			
		});
		widget2.add(w2btn);
		
		JLabel w2num = new JLabel("249");
		w2num.setVerticalAlignment(SwingConstants.TOP);
		w2num.setHorizontalAlignment(SwingConstants.CENTER);
		w2num.setForeground(new Color(49, 113, 223));
		w2num.setBounds(0, 48, 170, 35);
		w2num.setFont(btnNum);
		widget2.add(w2num);
		
		JLabel w2date = new JLabel("06/06/2025");
		w2date.setHorizontalAlignment(SwingConstants.CENTER);
		w2date.setBounds(0, 83, 170, 14);
		w2date.setForeground(lightGray);
		w2date.setFont(small);
		widget2.add(w2date);
		
		
		//w3
		RoundedPanel widget3 = new RoundedPanel(15, Color.WHITE, 3);
		widget3.setBounds(590, 112, 170, 160);
		widget3.setLayout(null);
		centro.add(widget3);
		
		JLabel w3title = new JLabel("Clientes");
		w3title.setHorizontalAlignment(SwingConstants.CENTER);
		w3title.setBounds(0, 23, 170, 15);
		w3title.setFont(titles);
		widget3.add(w3title);
		
		RoundedButton w3btn = new RoundedButton("Ver");
		w3btn.setBounds(46, 108, 80, 30);
		w3btn.setForeground(Color.WHITE);
		w3btn.setBackground(blue);
		w3btn.setFont(btntxt);
		w3btn.setRadius(20);
		w3btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cc.clients();
			}
			
		});
		widget3.add(w3btn);
		
		JLabel w3num = new JLabel("5,308");
		w3num.setHorizontalAlignment(SwingConstants.CENTER);
		w3num.setVerticalAlignment(SwingConstants.TOP);
		w3num.setForeground(new Color(66, 194, 168));
		w3num.setBounds(0, 48, 170, 35);
		w3num.setFont(btnNum);
		widget3.add(w3num);
		
		JLabel w3date = new JLabel("06/06/2025");
		w3date.setHorizontalAlignment(SwingConstants.CENTER);
		w3date.setBounds(0, 83, 170, 14);
		w3date.setForeground(lightGray);
		w3date.setFont(small);
		widget3.add(w3date);
		
			//WIDGETS INF
		//games
		RoundedPanel gamePanel = new RoundedPanel(15, Color.white, 3);
		gamePanel.setBounds(162, 305, 290, 260);
		centro.add(gamePanel);
		gamePanel.setLayout(null);
		
		JLabel gPanelTitle = new JLabel("Videojuegos");
		gPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		gPanelTitle.setBounds(0, 25, 290, 25);
		gPanelTitle.setFont(titles);
		gamePanel.add(gPanelTitle);
		
		
		JLabel gameSubtitle1 = new JLabel("Más rentado");
		gameSubtitle1.setHorizontalAlignment(SwingConstants.CENTER);
		gameSubtitle1.setBounds(30, 65, 93, 14);
		gameSubtitle1.setForeground(Gray);
		gameSubtitle1.setFont(subtitles);
		gamePanel.add(gameSubtitle1);
		
		JLabel gameSubtitle2 = new JLabel("Más comprado");
		gameSubtitle2.setHorizontalAlignment(SwingConstants.CENTER);
		gameSubtitle2.setBounds(165, 65, 93, 14);
		gameSubtitle2.setForeground(Gray);
		gameSubtitle2.setFont(subtitles);
		gamePanel.add(gameSubtitle2);
		
		RoundedButton gameBtn1 = new RoundedButton("Ver");
		gameBtn1.setBounds(40, 210, 80, 30);
		gameBtn1.setForeground(Color.WHITE);
		gameBtn1.setBackground(blue);
		gameBtn1.setFont(btntxt);
		gameBtn1.setRadius(20);
		gameBtn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				gc.viewGame();
			}
			
		});
		gamePanel.add(gameBtn1);
		
		RoundedButton gameBtn2 = new RoundedButton("Ver");
		gameBtn2.setBounds(170, 210, 80, 30);
		gameBtn2.setForeground(Color.WHITE);
		gameBtn2.setBackground(blue);
		gameBtn2.setFont(btntxt);
		gameBtn2.setRadius(20);
		gameBtn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				gc.viewGame();
			}
			
		});
		gamePanel.add(gameBtn2);
		
		
		//movies
		RoundedPanel moviePanel = new RoundedPanel(15, Color.WHITE, 3);
		moviePanel.setBounds(480, 305, 290, 260);
		centro.add(moviePanel);
		moviePanel.setLayout(null);
		
		JLabel mPanelTitle = new JLabel("Películas");
		mPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		mPanelTitle.setBounds(0, 25, 290, 25);
		mPanelTitle.setFont(titles);
		moviePanel.add(mPanelTitle);
		
		JLabel movieSubtitle1 = new JLabel("Más rentado");
		movieSubtitle1.setHorizontalAlignment(SwingConstants.CENTER);
		movieSubtitle1.setBounds(30, 65, 95, 14);
		movieSubtitle1.setFont(subtitles);
		movieSubtitle1.setForeground(Gray);
		moviePanel.add(movieSubtitle1);
		
		JLabel movieSubtitle2 = new JLabel("Más comprado");
		movieSubtitle2.setHorizontalAlignment(SwingConstants.CENTER);
		movieSubtitle2.setBounds(165, 65, 95, 14);
		movieSubtitle2.setFont(subtitles);
		movieSubtitle2.setForeground(Gray);
		moviePanel.add(movieSubtitle2);
		
		RoundedButton movieBtn1 = new RoundedButton("Ver");
		movieBtn1.setBounds(43, 210, 80, 30);
		movieBtn1.setForeground(Color.WHITE);
		movieBtn1.setBackground(blue);
		movieBtn1.setFont(btntxt);
		movieBtn1.setRadius(20);
		movieBtn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				mc.viewMovies();
			}
			
		});
		moviePanel.add(movieBtn1);
		
		RoundedButton movieBtn2 = new RoundedButton("Ver");
		movieBtn2.setForeground(Color.WHITE);
		movieBtn2.setBounds(173, 210, 80, 30);
		movieBtn2.setBackground(blue);
		movieBtn2.setFont(btntxt);
		movieBtn2.setRadius(20);
		movieBtn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				mc.viewMovies();
			}
			
		});
		moviePanel.add(movieBtn2);
	}
}
