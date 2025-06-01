package controllers;

import models.Movie;
import models.MoviesModel;
import views.MoviesView;

public class MoviesController {
	private MoviesView view;
	private MoviesModel model;

	public MoviesController() {
		view = new MoviesView();
		model = new MoviesModel();
	}

	public void movies() {
		view.movies();
	}
	
	public void viewMovies() {
		view.viewMovie();
	}
	
	public boolean addMovie(Movie movie) {
		return model.add(movie);
	}
}
