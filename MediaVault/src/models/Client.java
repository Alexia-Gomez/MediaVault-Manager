package models;

public class Client {
	
	public int client_id, total_rentals, total_purchases;
	public String name,last_name,email,phone,fidelity;
	public String birth_date;
	public byte[] photo;
	

	public Client(String name, String last_name, String email, String phone, String birth_date, byte[] photo) {
		this.name=name;
		this.last_name=last_name;
		this.email=email;
		this.phone=phone;
		this.birth_date=birth_date;
		this.photo=photo;
		this.total_purchases=0;
		this.total_rentals=0;
		this.fidelity="Ninguno";
	}
	
	public Client(int client_id,String name, String last_name, String email, String phone, String birth_date, byte[] photo, int total_rentals,int total_purchases, String fidelity) {
		this.client_id=client_id;
		this.name=name;
		this.last_name=last_name;
		this.email=email;
		this.phone=phone;
		this.birth_date=birth_date;
		this.photo=photo;
		this.total_purchases=total_purchases;
		this.total_rentals=total_rentals;
		this.fidelity=fidelity;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public int getTotal_rentals() {
		return total_rentals;
	}

	public void setTotal_rentals(int total_rentals) {
		this.total_rentals = total_rentals;
	}

	public int getTotal_purchases() {
		return total_purchases;
	}

	public void setTotal_purchases(int total_purchases) {
		this.total_purchases = total_purchases;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFidelity() {
		return fidelity;
	}

	public void setFidelity(String fidelity) {
		this.fidelity = fidelity;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	

}
