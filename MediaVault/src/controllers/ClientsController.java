package controllers;

import java.util.ArrayList;

import models.Client;
import models.ClientsModel;
import views.ClientsView;

public class ClientsController {
	private ClientsView view;
	private ClientsModel model;

	public ClientsController() {
		view = new ClientsView();
		model = new ClientsModel();
	}

	public void clients() {
		view.clients();
	}
	
	public ArrayList<Client>  get() {
		return model.getClients();
	}
	
	public boolean add(Client client) {
		return model.add(client);
	}
	
	public boolean update(Client client) {
		return model.update(client);
	}
	
	public boolean delete(int client_id) {
		return model.delete(client_id);
	}
	
	public int getCantClients() {
		return model.getCantClients();
		
	}
}
