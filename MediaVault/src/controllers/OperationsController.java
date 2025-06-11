package controllers;

import java.util.ArrayList;

import models.Game;
import models.Movie;
import models.Operation;
import models.OperationsModel;
import views.OperationsView;

public class OperationsController {
	private OperationsView view;
	private OperationsModel model;

	public OperationsController() {
		view = new OperationsView();
		model = new OperationsModel();
	}

	public void operations() {
		view.operations();
	}
	
	public ArrayList<Operation> get() {
		return model.getOperations();
	}
	
	public boolean add(Operation operation) {
		return model.add(operation);
	}
	
	public boolean delete(int operation_id) {
		return model.delete(operation_id);
	}
	
	public ArrayList<Operation> getOperationsByClientId(int clientId) {
		return model.getOperationsByClientId(clientId);
	}
	
	public int getTotalSales() {
        return model.getTotalSales();
    }
	
	public int getTotalRents() {
        return model.getTotalRents();
    }
	
	public Game getMostRentedGame() {
		return model.getMostPurchasedGame();
	}
	
	public Game getMostPurchasedGame() {
		return model.getMostPurchasedGame();
	}
	
	public Movie getMostRentedMovie() {
		return model.getMostPurchasedMovie();
	}
	
	public Movie getMostPurchasedMovie() {
		return model.getMostPurchasedMovie();
	}
}
