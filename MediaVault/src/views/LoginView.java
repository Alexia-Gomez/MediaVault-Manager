package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.AdminController;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controllers.HomeController;
import customClasses.Fuentes;
import customClasses.RoundedButton;
import customClasses.RoundedJPasswordField;
import customClasses.RoundedJTextField;
import customClasses.RoundedPanel;
import customClasses.Validaciones;

public class LoginView {
	
	Color blueBack = new Color(28, 148, 244);
	Color blue = new Color(24, 130, 234);
	Color grayF = new Color(250, 250, 250);
	
	Fuentes tipoFuentes = new Fuentes();
	Font txt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 12f);
	Font btntxt = tipoFuentes.fuente("/fonts/GolosText-Regular.ttf", 15f);
	
	ImageIcon logo = new ImageIcon(LoginView.class.getResource("/images/logo.png"));
	ImageIcon iconoFrame = new ImageIcon(LoginView.class.getResource("/images/iconoPrincipal.PNG"));

	
	public LoginView() {
		
	}
	
	public void login() {
		
		// VENTANA
		JFrame frame  = new JFrame();
		frame.setBounds(100, 20, 1000, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("Iniciar sesión");
		frame.setIconImage(iconoFrame.getImage());


		// PANELES
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		RoundedPanel roundPane = new RoundedPanel(30, Color.white, 3);
		roundPane.setLocation(338, 125);
		roundPane.setSize(310,365);
		panel.add(roundPane);

		JPanel back = new JPanel();
		back.setBackground(blueBack);
		back.setBounds(0, 0, 986, 240);
		panel.add(back);
		roundPane.setLayout(null);

		//
		JLabel logoLabel = new JLabel("");
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		logoLabel.setIcon(new ImageIcon(logo.getImage().getScaledInstance(200,150, Image.SCALE_SMOOTH)));
		logoLabel.setBounds(35, 11, 240, 96);
		roundPane.add(logoLabel);

		JLabel intro = new JLabel("<html><center>Por favor, ingrese sus datos de inicio de<br>sesión</center></html>");
		intro.setHorizontalAlignment(SwingConstants.CENTER);
		intro.setFont(txt);
		intro.setBounds(37, 100, 236, 43);
		roundPane.add(intro);

		// DATOS
		JLabel userLabel = new JLabel("Usuario");
		userLabel.setBounds(33, 154, 49, 14);
		userLabel.setFont(txt);
		roundPane.add(userLabel);

		RoundedJTextField userField = new RoundedJTextField(20);
		userField.addKeyListener(Validaciones.letras());
		userField.addKeyListener(Validaciones.limite(10));
		userField.setBounds(35, 171, 240, 36);
		userField.setForeground(Color.BLACK);
		userField.setBackground(grayF);
		userField.setFont(txt);
		roundPane.add(userField);


		JLabel passLabel = new JLabel("Contraseña");
		passLabel.setBounds(33, 218, 70, 14);
		passLabel.setFont(txt);
		roundPane.add(passLabel);

		RoundedJPasswordField passField = new RoundedJPasswordField(20);
		passField.addKeyListener(Validaciones.enteros());
		passField.addKeyListener(Validaciones.limite(10));
		passField.setBounds(35, 237, 240, 36);
		passField.setForeground(Color.BLACK);
		passField.setBackground(grayF);
		passField.setFont(txt);
		roundPane.add(passField);

		// ERROR
		JLabel errorLabel = new JLabel("Usuario o contraseña incorrectos");
		errorLabel.setBounds(35, 275, 240, 14);
		errorLabel.setForeground(Color.RED);
		errorLabel.setFont(txt);
		errorLabel.setVisible(false);
		roundPane.add(errorLabel);

		//
		RoundedButton btn = new RoundedButton("Iniciar sesión");
		btn.setFont(btntxt);
		btn.setRadius(20);
		btn.setBackground(blue);
		btn.setForeground(new Color(255, 255, 255));
		btn.setLocation(35, 300);
		btn.setSize(240, 40);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String password = new String(passField.getPassword());
				String username = userField.getText();
				
				Boolean flag1=false, flag2=false;
				
				if(username.equals("")){
					userField.setBorderColor(Color.red);
				} else
					flag1=true;
				
				
				if(password.equals("")){
					passField.setBorderColor(Color.red);
				} else
					flag2=true;
				
				userField.requestFocus();
				
				if(flag1 && flag2) {
					
					AdminController controller = new AdminController();
			        boolean acceso = controller.autenticar(username, password);
			        
			        if(acceso) {
			        	
			        	frame.dispose();
	 					HomeController hc = new HomeController();
	 					hc.home();
		                  
			        }else {
	 					passField.setBorderColor(Color.red);
	 					userField.setBorderColor(Color.red);
	 					errorLabel.setVisible(true);
	 					
	 					passField.setText("");
	 					userField.requestFocus();
			        }
				}

			}

		});
		roundPane.add(btn);

		frame.revalidate();
		frame.repaint();
	}
}
