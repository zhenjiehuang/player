/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2015 Caprica Software Limited.
 */

package player.view.main;

import static player.Application.application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.google.common.eventbus.Subscribe;

import net.miginfocom.swing.MigLayout;
import player.event.PausedEvent;
import player.event.PlayingEvent;
import player.event.ShowEffectsEvent;
import player.event.StoppedEvent;
import player.view.BasePanel;
import player.view.action.mediaplayer.MediaPlayerActions;
import uk.co.caprica.vlcj.binding.LibVlcConst;

final class ControlsPane extends BasePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Icon playIcon = newIcon("play");

	private final Icon pauseIcon = newIcon("pause");

	private final Icon previousIcon = newIcon("previous");

	private final Icon nextIcon = newIcon("next");

	private final Icon fullscreenIcon = newIcon("fullscreen");

	private final Icon extendedIcon = newIcon("extended");

	private final Icon snapshotIcon = newIcon("snapshot");

	private final Icon volumeHighIcon = newIcon("volume-high");

	private final Icon volumeMutedIcon = newIcon("volume-muted");

	private final JButton playPauseButton;

	private final JButton previousButton;

	private final JButton stopButton;

	private final JButton nextButton;

	private final JButton fullscreenButton;

	private final JButton extendedButton;

	private final JButton snapshotButton;

	private final JButton muteButton;

	private final JSlider volumeSlider;

	ControlsPane(MediaPlayerActions mediaPlayerActions) {
		playPauseButton = new BigButton();
		playPauseButton.setAction(mediaPlayerActions.playbackPlayAction());
		previousButton = new StandardButton();
		previousButton.setIcon(previousIcon);
		stopButton = new StandardButton();
		stopButton.setAction(mediaPlayerActions.playbackStopAction());
		nextButton = new StandardButton();
		nextButton.setIcon(nextIcon);
		fullscreenButton = new StandardButton();
		fullscreenButton.setIcon(fullscreenIcon);
		extendedButton = new StandardButton();
		extendedButton.setIcon(extendedIcon);
		snapshotButton = new StandardButton();
		snapshotButton.setAction(mediaPlayerActions.videoSnapshotAction());
		muteButton = new StandardButton();
		muteButton.setIcon(volumeHighIcon);
		volumeSlider = new JSlider();
		volumeSlider.setMinimum(LibVlcConst.MIN_VOLUME);
		volumeSlider.setMaximum(LibVlcConst.MAX_VOLUME);

		setLayout(new MigLayout("fill, insets 0 0 0 0", "[]12[]0[]0[]12[]0[]12[]push[]0[]", "[]"));

		add(playPauseButton);
		add(previousButton, "sg 1");
		add(stopButton, "sg 1");
		add(nextButton, "sg 1");

		add(fullscreenButton, "sg 1");
		add(extendedButton, "sg 1");

		add(snapshotButton, "sg 1");

		add(muteButton, "sg 1");
		add(volumeSlider, "wmax 100");

		volumeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				application().mediaPlayerComponent().getMediaPlayer().setVolume(volumeSlider.getValue());
				int value = volumeSlider.getValue();
				muteButton.setIcon(value == 0 ? volumeMutedIcon : volumeHighIcon);
			}
		});

		muteButton.addActionListener(new ActionListener() {
			int value = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				application().mediaPlayerComponent().getMediaPlayer().mute();

				if (muteButton.getIcon() == volumeMutedIcon) {
					volumeSlider.setValue(value);
				} else {
					value = volumeSlider.getValue();
					volumeSlider.setValue(0);
				}
			}
		});

		fullscreenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				application().mediaPlayerComponent().getMediaPlayer().toggleFullScreen();
			}
		});

		extendedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				application().post(ShowEffectsEvent.INSTANCE);
			}
		});
	}

	@Subscribe
	public void onPlaying(PlayingEvent event) {
		playPauseButton.setIcon(pauseIcon); // FIXME best way to do this? should
											// be via the action really?
	}

	@Subscribe
	public void onPaused(PausedEvent event) {
		playPauseButton.setIcon(playIcon); // FIXME best way to do this? should
											// be via the action really?
	}

	@Subscribe
	public void onStopped(StoppedEvent event) {
		playPauseButton.setIcon(playIcon); // FIXME best way to do this? should
											// be via the action really?
	}

	private class BigButton extends JButton {

		private static final long serialVersionUID = 1L;

		private BigButton() {
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			setHideActionText(true);
		}
	}

	private class StandardButton extends JButton {

		private static final long serialVersionUID = 1L;

		private StandardButton() {
			setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
			setHideActionText(true);
		}
	}

	private Icon newIcon(String name) {
		return new ImageIcon(getClass().getResource("/icons/buttons/" + name + ".png"));
	}
}
