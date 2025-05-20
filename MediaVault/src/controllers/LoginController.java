package controllers;

import views.LoginView;

public class LoginController {
	private LoginView view;
	
	public LoginController() {
		view = new LoginView();
	}
	
	public void login() {
		view.login();
	}
}
