package controllers;

import views.GamesView;

public class GamesController {
	private GamesView view;

	public GamesController() {
		view = new GamesView();
	}

	public void games() {
		view.games();
	}
	
	public void viewGame() {
		view.viewGame();
	}
}
