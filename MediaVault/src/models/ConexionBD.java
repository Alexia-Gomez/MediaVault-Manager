package models;

import java.sql.*;

public class ConexionBD {
	Connection con = null;

    String base = "defaultdb"; 
    String url = "jdbc:mysql://avnadmin:AVNS_5Ef9Q8F8YLbAuc_MzPx@mediavault-manager-proyecto-mediavault-manager2.g.aivencloud.com:23828/defaultdb?ssl-mode=REQUIRED";
    String user = "avnadmin"; 
    String password = "AVNS_5Ef9Q8F8YLbAuc_MzPx"; 

	public Connection getConexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			return con;
		}
		catch(SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
		
	

}
