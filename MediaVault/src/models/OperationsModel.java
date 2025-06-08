package models;

import java.sql.Connection;
import java.sql.ResultSet;
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
                // 1) Cliente
                Client cli = new Client(
                rs.getInt("id_client"),
                rs.getString("client_name"),
                rs.getString("client_lastname"),
                rs.getString("email"),             // si lo necesitas
                rs.getString("phone_number"),      // idem
                rs.getString("client_birth_date"), // idem
                rs.getBytes("photo"),
                rs.getInt("total_rentals"),
                rs.getInt("total_purchases"),
                rs.getString("fidelity_level"));

                // 2) Datos de operación
                int    operation_id     = rs.getInt("operation_id");
                String operation_type   = rs.getString("operation_type");
                String operation_date = rs.getString("operation_date");
                double unitPrice   = rs.getDouble("unit_price");
                double totalAmount = rs.getDouble("total_amount");

                // 3) Datos de producto
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

                // 4) Crea Operation usando el constructor adecuado
                Operation op;
                if ("movie".equalsIgnoreCase(prodType)) {
                    Movie movie = new Movie(prodId,prodTitle,studio,classification,releaseDate,genre,rentStock,saleStock,cover,salePrice,rentPrice);
                    op = new Operation(operation_id, cli, movie,operation_type, operation_date,unitPrice, totalAmount);
                } else {
                    Game game = new Game(prodId,prodTitle,studio,classification,releaseDate,genre,rentStock,saleStock,cover,salePrice,rentPrice);
                    op = new Operation(operation_id, cli, game,operation_type, operation_date,unitPrice, totalAmount);
                }

                operaciones.add(op);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return operaciones;
    }
	
}
