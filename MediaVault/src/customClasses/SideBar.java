package customClasses;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import controllers.ClientsController;
import controllers.GamesController;
import controllers.HomeController;
import controllers.MoviesController;
import controllers.OperationsController;
import controllers.RentSaleController;
import views.HomeView;

public class SideBar extends JButton {
	
	Color blue = new Color(24, 130, 234);
	Color hover = new Color(128, 186, 243);
	
	Fuentes tipoFuentes = new Fuentes();
	Font titles = tipoFuentes.fuente("/fonts/GolosText-SemiBold.ttf", 16f);
	
	static ImageIcon homeIcon = new ImageIcon(HomeView.class.getResource("/images/home.png"));
	static ImageIcon usersIcon = new ImageIcon(HomeView.class.getResource("/images/users.png"));
	static ImageIcon gamesIcon = new ImageIcon(HomeView.class.getResource("/images/games.png"));
	static ImageIcon moviesIcon = new ImageIcon(HomeView.class.getResource("/images/movies.png"));
	static ImageIcon opIcon = new ImageIcon(HomeView.class.getResource("/images/op.png"));
	static ImageIcon rentIcon = new ImageIcon(HomeView.class.getResource("/images/rent.png"));
	
	
	public SideBar(String text, Icon icon) {
		super("<html><center>" + text.replace(" ", "<br>") + "</center></html>", icon);
		setFont(titles);
		setIcon(new ImageIcon(((ImageIcon) icon).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setBackground(blue);
		setForeground(Color.white);
		setBorder(null);
		addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        setBackground(hover);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		        setBackground(blue);
		    }
		    
		    public void mousePressed(java.awt.event.MouseEvent evt) {
                setBackground(hover);
            }
		});
    }
	
	public static SideBar inicio(JFrame frame) {
		SideBar btn_inicio = new SideBar("Inicio", homeIcon);
		btn_inicio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				HomeController hc = new HomeController();
				hc.home();
			}
			
		});
		
		return btn_inicio;
	}
	
	public static SideBar clientes(JFrame frame) {
		SideBar btn_clientes = new SideBar("Clientes", usersIcon);
		btn_clientes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				ClientsController cc = new ClientsController();
				cc.clients();
			}
			
		});
		
		return btn_clientes;
	}
	
	public static SideBar nuevaOperacion(JFrame frame) {
		SideBar btn_nuevaOp = new SideBar("Nueva operación", opIcon );
		btn_nuevaOp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				OperationsController oc = new OperationsController();
				oc.operations();
			}
			
		});
		
		return btn_nuevaOp;
	}
	
	public static SideBar rentaCompra(JFrame frame) {
		SideBar btn_rentaCompra = new SideBar("Rentas\ny compras", rentIcon);
		btn_rentaCompra.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				RentSaleController rsc = new RentSaleController();
				rsc.rentSale();
			}
			
		});
		
		return btn_rentaCompra;
	}
	
	public static SideBar juegos(JFrame frame) {
		SideBar btn_juegos = new SideBar("Videojuegos", gamesIcon);
		btn_juegos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GamesController pc = new GamesController();
				pc.games();
			}
			
		});
		
		return btn_juegos;
	}
	
	public static SideBar peliculas(JFrame frame) {
		SideBar btn_peliculas = new SideBar("Películas", moviesIcon);
		btn_peliculas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MoviesController pc = new MoviesController();
				pc.movies();
			}
			
		});
		
		return btn_peliculas;
	}
}
