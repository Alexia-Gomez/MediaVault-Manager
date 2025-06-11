package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PromotionsModel {

	public Promotion getPromo(int promo_id) {
		Promotion promo=null;
		String query = "SELECT promotion_id, promo_discount " +
			    	   "FROM Promotions " +
			    	   "WHERE promotion_id = ?";
		Connection connection = ConexionBD.getConexion();
		PreparedStatement stmt = null;
		
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, promo_id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				int id= rs.getInt("promotion_id");
				double descuento_promo = rs.getDouble("promo_discount");
				promo = new Promotion(id,descuento_promo);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return promo;
	}
}
