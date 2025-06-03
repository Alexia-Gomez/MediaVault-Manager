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

				if (c == '$') {
					if (text.isEmpty()) {
						return;
					} else {
						e.consume();
						return;
					}
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
}
