package controllers;

import views.MoviesView;

public class MoviesController {
	private MoviesView view;

	public MoviesController() {
		view = new MoviesView();
	}

	public void movies() {
		view.movies();
	}
	
	public void viewMovies() {
		view.viewMovie();
	}
}
