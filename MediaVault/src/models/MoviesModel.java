package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MoviesModel {

	private ArrayList<Movie> movies = new ArrayList<>();

	public MoviesModel() {}

	public ArrayList<Movie> get() {
		String query = "SELECT title, studio, classification, release_date, genre, rent_stock, sale_stock FROM Products WHERE product_type = 'movie'";
		Connection connection = ConexionBD.getConexion();
		Statement stmt = null;

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				String title = rs.getString("title");
				String studio = rs.getString("studio");
				String classification = rs.getString("classification");
				String release_date = rs.getString("release_date");
				String genre = rs.getString("genre");
				int rent_stock = rs.getInt("rent_stock");
				int sale_stock = rs.getInt("sale_stock");

				Movie movie = new Movie(title, studio, classification, release_date, genre, rent_stock, sale_stock);
				movies.add(movie);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (connection != null) connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return movies;
	}
}
