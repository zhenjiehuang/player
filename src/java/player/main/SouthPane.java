package player.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import player.component.Button;

public class SouthPane extends JPanel {

	private static final long serialVersionUID = 1121506364804775678L;

	private Icon playIcon = newIcon("play");

	private Icon pauseIcon = newIcon("pause");

	private Icon highIcon = newIcon("volume-high");

	private Icon mutedIcon = newIcon("volume-muted");

	private JButton play = new Button("", "", playIcon, new Dimension(40, 40));

	private JButton pre = new Button("", "", newIcon("previous"), new Dimension(35, 25));

	private JButton stop = new Button("", "", newIcon("stop"), new Dimension(25, 25));

	private JButton next = new Button("", "", newIcon("next"), new Dimension(35, 25));

	private JButton muteButton = new Button("", "", newIcon("volume-high"), new Dimension(35, 25));;

	private JSlider volumeSlider = new JSlider(0, 100);

	private JSlider positionSlider = new JSlider(0, 100);

	public SouthPane() {
		init();
		initListener();
	}

	private void init() {
		setLayout(new BorderLayout());
		play.setIcon(playIcon);

		JPanel west = new JPanel();
		west.add(play);
		west.add(pre);
		west.add(stop);
		west.add(next);
		west.add(muteButton);
		west.add(volumeSlider);
		volumeSlider.setValue(100);
		add(west, BorderLayout.WEST);

		JPanel north = new JPanel(new BorderLayout());
		north.add(positionSlider, BorderLayout.CENTER);
		positionSlider.setValue(0);
		positionSlider.setPreferredSize(new Dimension(200, 15));

		add(north, BorderLayout.NORTH);
	}

	private void initListener() {
		play.addActionListener(new ActionListener() {
			boolean playB = true;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (playB = !playB) {
					play.setIcon(playIcon);
				} else {
					play.setIcon(pauseIcon);
				}
			}
		});

		muteButton.addActionListener(new ActionListener() {
			int value = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (muteButton.getIcon() == mutedIcon) {
					volumeSlider.setValue(value);
				} else {
					value = volumeSlider.getValue();
					volumeSlider.setValue(0);
				}
			}
		});

		volumeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = volumeSlider.getValue();
				muteButton.setIcon(value == 0 ? mutedIcon : highIcon);
			}
		});
	}

	private Icon newIcon(String name) {
		return new ImageIcon(getClass().getResource("/icons/buttons/" + name + ".png"));
	}
}
