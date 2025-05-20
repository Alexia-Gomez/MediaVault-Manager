package controllers;

import views.HomeView;

public class HomeController {
	private HomeView view;

	public HomeController() {
		view = new HomeView();
	}

	public void home() {
		view.home();
	}
}
