package controllers;

import views.OperationsView;

public class OperationsController {
	private OperationsView view;

	public OperationsController() {
		view = new OperationsView();
	}

	public void operations() {
		view.operations();
	}
}
