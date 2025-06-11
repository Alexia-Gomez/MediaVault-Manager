package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OperationsModel {

	public ArrayList<Operation> operaciones = new ArrayList<>();
	
	
	public ArrayList<Operation> getOperations(){
		operaciones.clear();
		String query = """
	            SELECT
	              o.operation_id,
	              o.operation_type,
	              o.operation_date,
	              c.id_client,
	              c.client_name,
	              c.client_lastname,
	              c.client_birth_date,
	              c.email,
	              c.photo,
	              c.total_rentals,
				  c.total_purchases,
				  c.fidelity_level,
				  c.phone_number,
	              p.product_id,
	              p.title        AS product_title,
	              p.product_type,
	              p.studio,
	              p.platform,
	              p.genre,
	              p.classification,
	              p.release_date,
	              p.rent_stock,
	              p.sale_stock,
	              p.cover,
	              p.sale_price,
	              p.rent_price,
	              p.fk_promotion_id_product AS promotion_id,
	              o.unit_price,
	              o.total_amount
	            FROM Operations o
	            JOIN Clients c
	              ON c.id_client = o.fk_client_id_operation
	            JOIN Products p
	              ON p.product_id = o.fk_product_id_operation
	            ORDER BY o.operation_date DESC
	            """;
		Connection connection = ConexionBD.getConexion();
		Statement stmt = null;
        try {
        	stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                
                Client cli = new Client(
                rs.getInt("id_client"),
                rs.getString("client_name"),
                rs.getString("client_lastname"),
                rs.getString("email"),             
                rs.getString("phone_number"),      
                rs.getString("client_birth_date"),
                rs.getBytes("photo"),
                rs.getInt("total_rentals"),
                rs.getInt("total_purchases"),
                rs.getString("fidelity_level"));

                
                int    operation_id     = rs.getInt("operation_id");
                String operation_type   = rs.getString("operation_type");
                String operation_date = rs.getString("operation_date");
                double unitPrice   = rs.getDouble("unit_price");
                double totalAmount = rs.getDouble("total_amount");

                
                int    prodId       = rs.getInt("product_id");
                String prodTitle    = rs.getString("product_title");
                String prodType     = rs.getString("product_type");
                String studio       = rs.getString("studio");
                String platform     = rs.getString("platform");
                String genre        = rs.getString("genre");
                String classification = rs.getString("classification");
                String releaseDate  = rs.getString("release_date");
                int    rentStock    = rs.getInt("rent_stock");
                int    saleStock    = rs.getInt("sale_stock");
                byte[] cover         = rs.getBytes("cover");
                double salePrice    = rs.getDouble("sale_price");
                double rentPrice    = rs.getDouble("rent_price");
                int    promotionId  = rs.getInt("promotion_id");
                

                Operation op;
                if ("movie".equalsIgnoreCase(prodType)) {
                    Movie movie = new Movie(prodId,prodTitle,studio,classification,releaseDate,genre,rentStock,saleStock,cover,salePrice,rentPrice,promotionId);
                    op = new Operation(operation_id, cli, movie,operation_type, operation_date,unitPrice, totalAmount);
                } else {
                    Game game = new Game(prodId,prodTitle,studio,classification,releaseDate,genre,rentStock,saleStock,cover,salePrice,rentPrice,promotionId);
                    op = new Operation(operation_id, cli, game,operation_type, operation_date,unitPrice, totalAmount);
                }

                operaciones.add(op);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operaciones;
    }
	
	public boolean add(Operation operation) {
		String query = "INSERT INTO `Operations`(fk_client_id_operation,fk_product_id_operation,operation_type,operation_date,total_amount,unit_price) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
   
	    Connection connection = ConexionBD.getConexion();
	    PreparedStatement stmt = null;
	    
	    try {
	        stmt = connection.prepareStatement(query);
	        
	        stmt.setInt(1, operation.getCliente().getClient_id());
	        if(operation.getMovie()!=null) {
	        	stmt.setInt(2, operation.getMovie().getProduct_id());
	        }
	        else {
	        	stmt.setInt(2, operation.getGame().getProduct_id());
	        }
	        stmt.setString(3, operation.getOperation_type());
	        stmt.setString(4, operation.getOperation_date());
    		stmt.setDouble(5, operation.getPrecioFinal());        	
	        stmt.setDouble(6, operation.getPrecioUnitario());
	        
	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            return true;
	        }
	        else {
	        	throw new SQLException("No se agregó ningún cliente");
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
	
	public boolean delete(int operation_id) {
		String query = "DELETE FROM Operations WHERE operation_id = ? ";
		Connection connection = ConexionBD.getConexion();
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, operation_id);

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
	

	public ArrayList<Operation> getOperationsByClientId(int clientId) {
	    ArrayList<Operation> clientOperations = new ArrayList<>();
	    String query = """
	        SELECT
	          o.operation_id,
	          o.operation_type,
	          o.operation_date,
	          c.id_client,
	          c.client_name,
	          c.client_lastname,
	          c.client_birth_date,
	          c.email,
	          c.photo,
	          c.total_rentals,
	          c.total_purchases,
	          c.fidelity_level,
	          c.phone_number,
	          p.product_id,
	          p.title        AS product_title,
	          p.product_type,
	          p.studio,
	          p.platform,
	          p.genre,
	          p.classification,
	          p.release_date,
	          p.rent_stock,
	          p.sale_stock,
	          p.cover,
	          p.sale_price,
	          p.rent_price,
	          p.fk_promotion_id_product AS promotion_id,
	          o.unit_price,
	          o.total_amount
	        FROM Operations o
	        JOIN Clients c
	          ON c.id_client = o.fk_client_id_operation
	        JOIN Products p
	          ON p.product_id = o.fk_product_id_operation
	        WHERE o.fk_client_id_operation = ?  -- Filter by the specific client ID
	        ORDER BY o.operation_date DESC
	        """;
	    Connection connection = ConexionBD.getConexion();
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setInt(1, clientId); 

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Client cli = new Client(
	                    rs.getInt("id_client"),
	                    rs.getString("client_name"),
	                    rs.getString("client_lastname"),
	                    rs.getString("email"),             
	                    rs.getString("phone_number"),      
	                    rs.getString("client_birth_date"),
	                    rs.getBytes("photo"),
	                    rs.getInt("total_rentals"),
	                    rs.getInt("total_purchases"),
	                    rs.getString("fidelity_level"));

	                
	                int    operation_id     = rs.getInt("operation_id");
	                String operation_type   = rs.getString("operation_type");
	                String operation_date = rs.getString("operation_date");
	                double unitPrice   = rs.getDouble("unit_price");
	                double totalAmount = rs.getDouble("total_amount");

	                
	                int    prodId       = rs.getInt("product_id");
	                String prodTitle    = rs.getString("product_title");
	                String prodType     = rs.getString("product_type");
	                String studio       = rs.getString("studio");
	                String platform     = rs.getString("platform");
	                String genre        = rs.getString("genre");
	                String classification = rs.getString("classification");
	                String releaseDate  = rs.getString("release_date");
	                int    rentStock    = rs.getInt("rent_stock");
	                int    saleStock    = rs.getInt("sale_stock");
	                byte[] cover         = rs.getBytes("cover");
	                double salePrice    = rs.getDouble("sale_price");
	                double rentPrice    = rs.getDouble("rent_price");
	                int    promotionId  = rs.getInt("promotion_id");
	                

	                Operation op;
	                if ("movie".equalsIgnoreCase(prodType)) {
	                    Movie movie = new Movie(prodId,prodTitle,studio,classification,releaseDate,genre,rentStock,saleStock,cover,salePrice,rentPrice,promotionId);
	                    op = new Operation(operation_id, cli, movie,operation_type, operation_date,unitPrice, totalAmount);
	                } else { 
	                    Game game = new Game(prodId,prodTitle,studio,classification,releaseDate,genre,rentStock,saleStock,cover,salePrice,rentPrice,promotionId);
	                    op = new Operation(operation_id, cli, game,operation_type, operation_date,unitPrice, totalAmount);
	                }

	                clientOperations.add(op);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return clientOperations;
	}
	
	public int getTotalSales() {
	    String query = "SELECT COUNT(*) FROM Operations WHERE operation_type = 'sale'";
	    Connection connection = ConexionBD.getConexion();
	    try (Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}
	
	public int getTotalRents() {
	    String query = "SELECT COUNT(*) FROM Operations WHERE operation_type = 'rent'";
	    Connection connection = ConexionBD.getConexion();
	    try (Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {
	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}
	
	public Game getMostRentedGame() {
	    Game game = null;
	    String query = "SELECT p.product_id, p.title, p.platform, p.classification, p.release_date, p.genre, "
	                 + "p.rent_stock, p.sale_stock, p.cover, p.sale_price, p.rent_price, "
	                 + "COUNT(o.operation_id) AS total_rent_count "
	                 + "FROM Products p "
	                 + "JOIN Operations o ON p.product_id = o.fk_product_id_operation "
	                 + "WHERE p.product_type = 'videogame' AND o.operation_type = 'rent' "
	                 + "GROUP BY p.product_id "
	                 + "ORDER BY total_rent_count DESC "
	                 + "LIMIT 1";

	    try (Connection connection = ConexionBD.getConexion();
	         PreparedStatement stmt = connection.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        if (rs.next()) {
	            game = new Game(
	                rs.getInt("product_id"),
	                rs.getString("title"),
	                rs.getString("platform"),
	                rs.getString("classification"),
	                rs.getString("release_date"),
	                rs.getString("genre"),
	                rs.getInt("rent_stock"),
	                rs.getInt("sale_stock"),
	                rs.getBytes("cover"),
	                rs.getDouble("sale_price"),
	                rs.getDouble("rent_price")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return game;
	}

	public Game getMostPurchasedGame() {
	    Game game = null;
	    String query = "SELECT p.product_id, p.title, p.platform, p.classification, p.release_date, p.genre, "
	                 + "p.rent_stock, p.sale_stock, p.cover, p.sale_price, p.rent_price, "
	                 + "COUNT(o.operation_id) AS total_purchase_count "
	                 + "FROM Products p "
	                 + "JOIN Operations o ON p.product_id = o.fk_product_id_operation "
	                 + "WHERE p.product_type = 'videogame' AND o.operation_type = 'sale' "
	                 + "GROUP BY p.product_id "
	                 + "ORDER BY total_purchase_count DESC "
	                 + "LIMIT 1";

	    try (Connection connection = ConexionBD.getConexion();
	         PreparedStatement stmt = connection.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        if (rs.next()) {
	            game = new Game(
	                rs.getInt("product_id"),
	                rs.getString("title"),
	                rs.getString("platform"),
	                rs.getString("classification"),
	                rs.getString("release_date"),
	                rs.getString("genre"),
	                rs.getInt("rent_stock"),
	                rs.getInt("sale_stock"),
	                rs.getBytes("cover"),
	                rs.getDouble("sale_price"),
	                rs.getDouble("rent_price")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return game;
	}

	public Movie getMostRentedMovie() {
	    Movie movie = null;
	    String query = "SELECT p.product_id, p.title, p.platform, p.classification, p.release_date, p.genre, "
	                 + "p.rent_stock, p.sale_stock, p.cover, p.sale_price, p.rent_price, "
	                 + "COUNT(o.operation_id) AS total_rent_count "
	                 + "FROM Products p "
	                 + "JOIN Operations o ON p.product_id = o.fk_product_id_operation "
	                 + "WHERE p.product_type = 'movie' AND o.operation_type = 'rent' "
	                 + "GROUP BY p.product_id "
	                 + "ORDER BY total_rent_count DESC "
	                 + "LIMIT 1";

	    try (Connection connection = ConexionBD.getConexion();
	         PreparedStatement stmt = connection.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        if (rs.next()) {
	            movie = new Movie(
	                rs.getInt("product_id"),
	                rs.getString("title"),
	                rs.getString("platform"),
	                rs.getString("classification"),
	                rs.getString("release_date"),
	                rs.getString("genre"),
	                rs.getInt("rent_stock"),
	                rs.getInt("sale_stock"),
	                rs.getBytes("cover"),
	                rs.getDouble("sale_price"),
	                rs.getDouble("rent_price")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return movie;
	}

	public Movie getMostPurchasedMovie() {
	    Movie movie = null;
	    String query = "SELECT p.product_id, p.title, p.platform, p.classification, p.release_date, p.genre, "
	                 + "p.rent_stock, p.sale_stock, p.cover, p.sale_price, p.rent_price, "
	                 + "COUNT(o.operation_id) AS total_purchase_count "
	                 + "FROM Products p "
	                 + "JOIN Operations o ON p.product_id = o.fk_product_id_operation "
	                 + "WHERE p.product_type = 'movie' AND o.operation_type = 'sale' "
	                 + "GROUP BY p.product_id "
	                 + "ORDER BY total_purchase_count DESC "
	                 + "LIMIT 1";

	    try (Connection connection = ConexionBD.getConexion();
	         PreparedStatement stmt = connection.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        if (rs.next()) {
	            movie = new Movie(
	                rs.getInt("product_id"),
	                rs.getString("title"),
	                rs.getString("platform"),
	                rs.getString("classification"),
	                rs.getString("release_date"),
	                rs.getString("genre"),
	                rs.getInt("rent_stock"),
	                rs.getInt("sale_stock"),
	                rs.getBytes("cover"),
	                rs.getDouble("sale_price"),
	                rs.getDouble("rent_price")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return movie;
	}
}
