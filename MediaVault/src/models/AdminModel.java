package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminModel {

	public AdminModel() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean access(String username, String password) {
		Connection connection = ConexionBD.getConexion();
	    if (connection == null) return false;
	    
        String query = "SELECT * FROM Admin WHERE username = ? AND password = ?";

	    try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password); 

            ResultSet rs = stmt.executeQuery();
            return rs.next(); 

        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}

}
