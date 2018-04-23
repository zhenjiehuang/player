package player;

import javax.swing.UIManager;

import player.main.MainFrame;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class Start {
	public static void main(String[] args) {
		String lookAndFeelClassName;
		if (RuntimeUtil.isNix()) {
			lookAndFeelClassName = "javax.swing.plaf.metal.MetalLookAndFeel";
		} else {
			lookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
		}
		try {
			
			UIManager.setLookAndFeel(lookAndFeelClassName);
		} catch (Exception e) {

		}
		new MainFrame().setVisible(true);
	}
}
