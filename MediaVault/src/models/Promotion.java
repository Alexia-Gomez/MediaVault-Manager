package models;

public class Promotion {

	int promotion_id;
	String code;
	double promo_discount;
	
	
	public Promotion(int promotion_id,double promo_discount) {
		this.promo_discount=promo_discount;
		this.promotion_id=promotion_id;
	}
	
	public Promotion(int promotion_id) {
		this.promotion_id=promotion_id;
	}

	public int getPromotion_id() {
		return promotion_id;
	}

	public void setPromotion_id(int promotion_id) {
		this.promotion_id = promotion_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getPromo_discount() {
		return promo_discount;
	}

	public void setPromo_discount(double promo_discount) {
		this.promo_discount = promo_discount;
	}
	
	
}
