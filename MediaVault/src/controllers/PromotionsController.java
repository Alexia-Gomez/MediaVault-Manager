package controllers;

import models.Promotion;
import models.PromotionsModel;

public class PromotionsController {
	PromotionsModel model;

	public PromotionsController() {
		model = new PromotionsModel();
	}
	
	public Promotion get(int promo_id) {
		return model.getPromo(promo_id);
	}
}
