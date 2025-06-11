package controllers;

import java.util.ArrayList;

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
		view.viewMovie(null);
	}
	
	public void viewMovies(Movie movie) {
		view.viewMovie(movie);
	}
	
	public boolean addMovie(Movie movie) {
		return model.add(movie); 
	}
	
	public boolean updateMovie(Movie movie, int product_id) {
		return model.update(movie, product_id);
	}
	
	public boolean  deleteMovie(int product_id) { 
		MoviesModel model = new MoviesModel();
		
		return model.delete(product_id);
		
	}
	
	public ArrayList<Movie> get(){
		return model.get();
		
	}

	
}
