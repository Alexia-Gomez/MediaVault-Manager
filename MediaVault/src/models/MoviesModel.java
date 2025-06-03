package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MoviesModel {

	private ArrayList<Movie> movies = new ArrayList<>();

	public MoviesModel() {}

	public ArrayList<Movie> get() {
		String query = "SELECT product_id, title, studio, classification, release_date, genre, rent_stock, sale_stock, cover, sale_price, rent_price FROM Products WHERE product_type = 'movie'";
		Connection connection = ConexionBD.getConexion();
		Statement stmt = null;

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int product_id = rs.getInt("product_id");
				String title = rs.getString("title");
				String studio = rs.getString("studio");
				String classification = rs.getString("classification");
				String release_date = rs.getString("release_date");
				String genre = rs.getString("genre");
				int rent_stock = rs.getInt("rent_stock");
				int sale_stock = rs.getInt("sale_stock");
				byte[] cover = rs.getBytes("cover");       
	            double sale_price = rs.getDouble("sale_price");
	            double rent_price = rs.getDouble("rent_price");
				

				Movie movie = new Movie(product_id, title, studio, classification, release_date, genre, rent_stock, sale_stock,cover,sale_price,rent_price);
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
	
	public boolean delete(int product_id) {
		String query = "DELETE FROM Products WHERE product_id = ? AND product_type = 'movie'";
		Connection connection = ConexionBD.getConexion();
		PreparedStatement stmt = null;

		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, product_id);

			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;

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
		return false;
	}
	
	public boolean add(Movie product) {
	    String query = "INSERT INTO `Products`(title, platform, genre, classification, release_date, product_type, rent_stock, sale_stock, cover, sale_price, rent_price, studio, fk_promotion_id_product) "
	                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    Connection connection = ConexionBD.getConexion();
	    PreparedStatement stmt = null;
	    
	    try {
	        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        
	        stmt.setString(1, product.getTitle());
	        stmt.setString(2, product.getPlatform());
	        stmt.setString(3, product.getGenre());
	        stmt.setString(4, product.getClassification());
	        stmt.setString(5, product.getRelease_date());
	        stmt.setString(6, product.getProduct_type());
	        stmt.setInt(7, product.getRent_stock());
	        stmt.setInt(8, product.getSale_stock());
	        
	        if (product.getCover() != null) {
	            stmt.setBytes(9, product.getCover());
	        } else {
	            stmt.setNull(9, java.sql.Types.BLOB);
	        }

	        stmt.setDouble(10, product.getSale_price());
	        stmt.setDouble(11, product.getRent_price());
	        stmt.setString(12, product.getStudio());
	        stmt.setNull(13, java.sql.Types.INTEGER);

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            ResultSet generatedKeys = stmt.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int id = generatedKeys.getInt(1);
	                product.setProduct_id(id); 
	                System.out.println("Nuevo producto insertado con ID: " + id);
	            }
	            return true;
	        }
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
	    return false;
	}

	public boolean update(Movie product, int product_id) {
	    String query = "UPDATE `Products` SET "
	        + "title = ?, "
	        + "platform = ?, "
	        + "genre = ?, "
	        + "classification = ?, "
	        + "release_date = ?, "
	        + "rent_stock = ?, "
	        + "sale_stock = ?, "
	        + "cover = ?, "
	        + "sale_price = ?, "
	        + "rent_price = ?, "
	        + "studio = ? "
	        + "WHERE product_id = ? AND product_type = 'movie'";
	    
	    Connection connection = ConexionBD.getConexion();
	    PreparedStatement stmt = null;
	    
	    try {
	        stmt = connection.prepareStatement(query);
	        
	        stmt.setString(1, product.getTitle());
	        stmt.setString(2, product.getPlatform());
	        stmt.setString(3, product.getGenre());
	        stmt.setString(4, product.getClassification());
	        stmt.setString(5, product.getRelease_date());
	        stmt.setInt(6, product.getRent_stock());
	        stmt.setInt(7, product.getSale_stock());
	        
	        if (product.getCover() != null) {
	            stmt.setBytes(8, product.getCover());
	        } else {
	            stmt.setNull(8, java.sql.Types.BLOB);
	        }
	        
	        stmt.setDouble(9, product.getSale_price());
	        stmt.setDouble(10, product.getRent_price());
	        stmt.setString(11, product.getStudio());
	        stmt.setInt(12, product_id); 
	        
	        int rowsAffected = stmt.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            return true;
	        }
	        
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
	    return false;
	}
	
	
}
