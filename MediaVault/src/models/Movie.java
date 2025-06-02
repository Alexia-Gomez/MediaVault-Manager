package models;

import java.io.ByteArrayInputStream;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Movie {
	
	public int product_id, rent_stock, sale_stock,fk_promotion_id;
	public String title, platform, genre, classification, product_type, studio;
	public String release_date;
	public double sale_price, rent_price; 
	public byte[] cover;
	

	public Movie(String title, String studio, String classification, String release_date, String genre, int rent_stock, int sale_stock, byte[] cover, double sale_price, double rent_price) {

		this.product_id = product_id;
		this.rent_stock = rent_stock ;
		this.sale_stock = sale_stock;
		this.title = title;
		this.platform = platform;
		this.genre = genre;
		this.classification = classification;
		this.product_type = "movie";
		this.cover = cover;
		this.studio = studio;
		this.release_date = release_date;
		this.sale_price = sale_price;
		this.rent_price = rent_price;
		this.fk_promotion_id=fk_promotion_id;
	}
	
	public Movie(int product_id, String title, String studio, String classification, String release_date, String genre, int rent_stock, int sale_stock, byte[] cover, double sale_price, double rent_price) {

		this.product_id = product_id;
		this.rent_stock = rent_stock ;
		this.sale_stock = sale_stock;
		this.title = title;
		this.platform = platform;
		this.genre = genre;
		this.classification = classification;
		this.product_type = "movie";
		this.cover = cover;
		this.studio = studio;
		this.release_date = release_date;
		this.sale_price = sale_price;
		this.rent_price = rent_price;
		this.fk_promotion_id=fk_promotion_id;
	}
	public Movie() {
		
	}

	public int getProduct_id() { return product_id; }
    public void setProduct_id(int product_id) { this.product_id = product_id;}
	public int getRent_stock() { return rent_stock; }
	public int getSale_stock() { return sale_stock; }
	public int get_fk_promotion_id() { return fk_promotion_id; }

	public void setRent_stock(int rent_stock) { this.rent_stock = rent_stock; }
	public void setSale_stock(int sale_stock) { this.sale_stock = sale_stock; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getPlatform() { return platform; }
	public void setPlatform(String platform) { this.platform = platform; }

	public String getGenre() { return genre; }
	public void setGenre(String genre) { this.genre = genre; }

	public String getClassification() { return classification; }
	public void setClassification(String classification) { this.classification = classification; }

	public String getProduct_type() { return product_type; }
	public void setProduct_type(String product_type) { this.product_type = product_type; }

	public byte[] getCover() { return cover; }
	public void setCover(byte[] cover) { this.cover = cover; }

	public String getStudio() { return studio; }
	public void setStudio(String studio) { this.studio = studio; }

	public String getRelease_date() { return release_date; }
	public void setRelease_date(String release_date) { this.release_date = release_date; }

	public double getSale_price() { return sale_price; }
	public void setSale_price(double sale_price) { this.sale_price = sale_price; }

	public double getRent_price() { return rent_price; }
	public void setRent_price(double rent_price) { this.rent_price = rent_price; }

	public ImageIcon getCoverAsIcon(int w, int h) {
	    if (cover != null) {
	        try {
	            BufferedImage img = ImageIO.read(new ByteArrayInputStream(cover));
	            Image scaledImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	            return new ImageIcon(scaledImg);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return null;
	}

	@Override
	public String toString() {
		return "Movie [product_id=" + product_id + ", rent_stock=" + rent_stock + ", sale_stock=" + sale_stock
				+ ", title=" + title + ", platform=" + platform + ", genre=" + genre + ", classification="
				+ classification + ", product_type=" + product_type + ", cover=" + cover + ", studio=" + studio
				+ ", release_date=" + release_date + ", sale_price=" + sale_price + ", rent_price=" + rent_price + "]";
	}
	
	

}
