package controllers;

import java.util.ArrayList;

import models.Operation;
import models.RentSaleModel;
import views.RentSaleView;

public class RentSaleController {
	private RentSaleView view;
	private RentSaleModel model;
	
	public RentSaleController() {
		view = new RentSaleView();
		model = new RentSaleModel();
	}
	
	public void rentSale() {
		view.rentSale();
	}
	
	public ArrayList<Operation>  getOperations() {
		return model.getOperations();
	}
}
