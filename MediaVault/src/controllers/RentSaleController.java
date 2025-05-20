package controllers;

import views.RentSaleView;

public class RentSaleController {
private RentSaleView view;
	
	public RentSaleController() {
		view = new RentSaleView();
	}
	
	public void rentSale() {
		view.rentSale();
	}
}
