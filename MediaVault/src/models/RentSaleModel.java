package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class RentSaleModel {
	public ArrayList<Operation> getOperations() {
	    ArrayList<Operation> operations = new ArrayList<>();
	    operations.clear();

	    String query = "SELECT o.operation_id, o.operation_type, o.operation_date, " +
	                   "o.unit_price, o.total_amount, " +
	                   "c.id_client, c.client_name, c.client_lastname, c.client_birth_date, " +
	                   "c.email, c.photo, c.total_rentals, c.total_purchases, " +
	                   "c.fidelity_level, c.phone_number, " +
	                   "p.product_id, p.title, p.platform, p.type, p.category, p.director, " +
	                   "p.release_year, p.rating, p.price, p.stock " +
	                   "FROM Operations o " +
	                   "JOIN Clients c ON o.fk_client_id_operation = c.id_client " +
	                   "JOIN Products p ON o.fk_product_id_operation = p.product_id";

	    Connection connection = ConexionBD.getConexion();
	    Statement stmt = null;

	    try {
	        stmt = connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);

	        while (rs.next()) {
	            int client_id = rs.getInt("id_client");
	            String name = rs.getString("client_name");
	            String lastName = rs.getString("client_lastname");
	            String birthDate = rs.getString("client_birth_date");
	            String email = rs.getString("email");
	            byte[] photo = rs.getBytes("photo");
	            int rentals = rs.getInt("total_rentals");
	            int purchases = rs.getInt("total_purchases");
	            String fidelity = rs.getString("fidelity_level");
	            String phone = rs.getString("phone_number");

	            Client client = new Client(client_id, name, lastName, email, phone, birthDate, photo, rentals, purchases, fidelity);

	            int operationId = rs.getInt("operation_id");
	            String operationType = rs.getString("operation_type");
	            String operationDate = rs.getString("operation_date");
	            double unitPrice = rs.getDouble("unit_price");
	            double totalAmount = rs.getDouble("total_amount");

				int product_id = rs.getInt("product_id");
				String title = rs.getString("title");
				String studio = rs.getString("studio");
				String classification = rs.getString("classification");
				String release_date = rs.getString("release_date");
				String genre = rs.getString("genre");
				String platform = rs.getString("platform");
				String product_type = rs.getString("product_type");
				int rent_stock = rs.getInt("rent_stock");
				int sale_stock = rs.getInt("sale_stock");
				byte[] cover = rs.getBytes("cover");       
	            double sale_price = rs.getDouble("sale_price");
	            double rent_price = rs.getDouble("rent_price");
	            int releaseYear = rs.getInt("release_year");
	            double rating = rs.getDouble("rating");
	            double price = rs.getDouble("price");
	            int stock = rs.getInt("stock");

	            if (product_type.equalsIgnoreCase("movie")) {
	                Movie movie = new Movie(product_id, title, studio, classification, release_date, genre, rent_stock, sale_stock, cover, sale_price, rent_price);

	                Operation op = new Operation(operationId, client, movie, operationType, operationDate, unitPrice, totalAmount);
	                operations.add(op);
	            } else if (product_type.equalsIgnoreCase("videogame")) {
	                Game game = new Game(product_id, title, platform, classification, release_date, genre, rent_stock, sale_stock, cover, sale_price, rent_price);
	                
	                Operation op = new Operation(operationId, client, game, operationType, operationDate, unitPrice, totalAmount);
	                operations.add(op);
	            }
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

	    return operations;
	}



	
}
