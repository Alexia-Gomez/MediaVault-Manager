package application;

import controllers.LoginController;

public class Main {
	
	public static void main(String[] args) {
		
		LoginController application = new LoginController();
		application.login();
	}
}
