package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClientsModel {

	public ArrayList<Client> getClients(){
		ArrayList<Client> clients = new ArrayList<>();
		clients.clear();
		String query = "SELECT id_client,client_name,client_lastname,client_birth_date,email,photo,total_rentals,total_purchases,fidelity_level,phone_number FROM Clients";
		Connection connection = ConexionBD.getConexion();
		Statement stmt = null;

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				int client_id = rs.getInt("id_client");
				String name = rs.getString("client_name");
				String last_name = rs.getString("client_lastname");
				String birth_date = rs.getString("client_birth_date");
				String email = rs.getString("email");
				byte[] photo = rs.getBytes("photo");
				int rentals = rs.getInt("total_rentals");
				int purchases = rs.getInt("total_purchases");
				String fidelity = rs.getString("fidelity_level");       
	            String phone = rs.getString("phone_number");
				

				Client client = new Client(client_id,name,last_name,email,phone,birth_date,photo,rentals,purchases,fidelity);
				clients.add(client);
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
		return clients;
	}
	
	public boolean add(Client client) {
		 String query = "INSERT INTO `Clients`(client_name,client_lastname,client_birth_date,email,photo,total_rentals,total_purchases,fidelity_level,phone_number) "
                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
	    Connection connection = ConexionBD.getConexion();
	    PreparedStatement stmt = null;
	    
	    try {
	        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        
	        stmt.setString(1, client.getName());
	        stmt.setString(2, client.getLast_name());
	        stmt.setString(3, client.getBirth_date());
	        stmt.setString(4, client.getEmail());
	        if (client.getPhoto()!=null) {
	        	stmt.setBytes(5, client.getPhoto());
	        }
	        else
	        	stmt.setNull(5, java.sql.Types.BLOB);
	        stmt.setInt(6, client.getTotal_rentals());
	        stmt.setInt(7, client.getTotal_purchases());
	        stmt.setString(8, client.getFidelity());
	        stmt.setString(9, client.getPhone());
	
	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            ResultSet generatedKeys = stmt.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int id = generatedKeys.getInt(1);
	                client.setClient_id(id); 
	                System.out.println("Nuevo producto insertado con ID: " + id);
	            }
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
	
	public boolean delete(int client_id) {
		String query = "DELETE FROM Clients WHERE id_client = ? ";
		Connection connection = ConexionBD.getConexion();
		PreparedStatement stmt = null;

		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, client_id);

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
	
	public boolean update(Client client) {
		String query = "UPDATE `Clients` SET "
		        + "client_name = ?, "
		        + "client_lastname = ?, "
		        + "client_birth_date = ?, "
		        + "email = ?, "
		        + "photo = ?, "
		        + "phone_number = ? "
		        + "WHERE id_client = ? ";
		    
		    Connection connection = ConexionBD.getConexion();
		    PreparedStatement stmt = null;
		    
		    try {
		        stmt = connection.prepareStatement(query);
		        
		        stmt.setString(1, client.getName());
		        stmt.setString(2, client.getLast_name());
		        stmt.setString(3, client.getBirth_date());
		        stmt.setString(4, client.getEmail());
		        stmt.setBytes(5, client.getPhoto());
		        stmt.setString(6, client.getPhone());
		        stmt.setInt(7, client.getClient_id());
		        
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

	public int getCantClients() {
	    
	    Connection connection = ConexionBD.getConexion();
	    if (connection == null) return 0;
	    

	    String query = "SELECT COUNT(*) FROM Clients"; 

	    try {
	        PreparedStatement stmt = connection.prepareStatement(query);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}
}
