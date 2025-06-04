package controllers;

import models.Game;
import models.GamesModel;
import models.MoviesModel;
import views.GamesView;

public class GamesController {
	private GamesView view;
	private GamesModel model;

	public GamesController() {
		view = new GamesView();
		model = new GamesModel();

	}

	public void games() {
		view.games();
	}
	
	public void viewGame() {
		view.viewGame(null);
	}
	
	public boolean addGame(Game game) {
		return model.add(game); 
	}
	
	public boolean updateGame(Game game, int product_id) {
		return model.update(game, product_id);
	}
	
	public boolean  deleteGame(int product_id) { 
		GamesModel model = new GamesModel();
		
		return model.delete(product_id);
		
	}
}
