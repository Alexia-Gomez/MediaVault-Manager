package controllers;

import java.util.ArrayList;

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
}
