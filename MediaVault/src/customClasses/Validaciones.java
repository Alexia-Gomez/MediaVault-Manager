package customClasses;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class Validaciones {

	public static KeyAdapter enteros() {
		return new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
					e.consume();
				}
			}
		};
	}

	public static KeyAdapter conDecimal() {
		return new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();

				if (c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
					return;
				}

				if (!Character.isDigit(c) && c != '.') {
					e.consume();
					return;
				}

				if (c == '.' && text.contains(".")) {
					e.consume();
					return;
				}

				if (text.isEmpty() && c == '.') {
					textField.setText("0.");
					e.consume();
					return;
				}

				if (text.contains(".")) {
					int index = text.indexOf(".");
					String decimal = text.substring(index + 1);
					if (decimal.length() >= 2) {
						e.consume();
					}
				}

			}
		};
	}

	public static KeyAdapter letras() {
		return new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
					e.consume();
				}
			}
		};
	}
	
	public static KeyAdapter fechas() {
		return new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();

				if (c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
					return;
				}
				
				if (!Character.isDigit(c) && c != '-') {
					e.consume();
					return;
				}
			}
		};
		
	}
	
 	public static KeyAdapter limite(int lim) {
		return new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				JTextField field = (JTextField) e.getSource();
				if(field.getText().length() >= lim) {
					e.consume();
				}
				
			}
		};
	}
 	
 	public static boolean validarCampoVacio(RoundedJTextField campo) {
 		boolean vacio = campo.getText().trim().isEmpty();
 		campo.setErrorBorder(vacio);
 		return !vacio;
 	}

 	public static boolean validarCombo(CustomJComboBox combo) {
 		boolean invalido = combo.getSelectedIndex() == 0;
 		combo.setErrorBorder(invalido);

 		return !invalido;
 	}


}
