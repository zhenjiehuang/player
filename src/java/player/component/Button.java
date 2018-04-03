package player.component;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

public class Button extends JButton {

	private static final long serialVersionUID = -8838359712508713601L;

	public Button(String text, String tip, Icon icon, Dimension size) {
		setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		setIcon(icon);
		setText(text);
		setToolTipText(tip);
		setPreferredSize(size);
	}
}
