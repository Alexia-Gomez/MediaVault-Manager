package customClasses;

import java.awt.Font;
import java.io.InputStream;

public class Fuentes {
	 private Font font = null;
	    public String Golos = "GolosText-VariableFont_wght.ttf";
	    public String Lexend = "Lexend-VariableFont_wght.ttf";

	    public Font fuente( String fontName, float tamanio)
	    {
	         try {
	            InputStream is =  getClass().getResourceAsStream(fontName);
	            font = Font.createFont(Font.TRUETYPE_FONT, is);
	            
	        } catch (Exception ex) {
	            System.err.println(fontName + " No se cargo la fuente");
	            font = new Font("Arial", Font.PLAIN, 14);            
	        }
	         
	        Font tfont = font.deriveFont(tamanio);
	        return tfont;
	    }
}
