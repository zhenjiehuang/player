package test;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class PopuMenu extends JFrame {// 弹出菜单的实现，弹出菜单是一个可弹出并显示一系列选项的小窗口
	JPopupMenu popupMenu;

	public PopuMenu() {
		super("右键弹出式菜单"); // 调用父类构造函数
		// 实例化弹出菜单
		popupMenu = new JPopupMenu();
		// 增加菜单项到菜单上
		popupMenu.add(new JMenuItem("菜单项"));

		myEvents();

		setSize(350, 300); // 设置窗口大小
		setLocation(400, 200);
		setVisible(true); // 设置窗口为可视
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序
	}

	private void myEvents() {
		// 窗口的鼠标事件处理
		addMouseListener(new MouseAdapter() {
			// 点击鼠标
			@Override
			public void mousePressed(MouseEvent event) {
				// 调用triggerEvent方法处理事件
				// triggerEvent(event);
			}

			// 释放鼠标
			@Override
			public void mouseReleased(MouseEvent event) {
				triggerEvent(event);
			}

			private void triggerEvent(MouseEvent event) { // 处理事件
				// isPopupTrigger():返回此鼠标事件是否为该平台的弹出菜单触发事件。
				if (event.isPopupTrigger())
					// 显示菜单
					popupMenu.show(event.getComponent(), event.getX(), event.getY());
			}
		});
	}

	public static void main(String args[]) {
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
		new PopuMenu();
	}
}
