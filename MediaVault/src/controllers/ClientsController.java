package controllers;

import views.ClientsView;

public class ClientsController {
	private ClientsView view;

	public ClientsController() {
		view = new ClientsView();
	}

	public void clients() {
		view.clients();
	}
}
