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

package player;

import static player.Application.application;

import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import player.event.ShutdownEvent;
import player.view.debug.DebugFrame;
import player.view.effects.EffectsFrame;
import player.view.main.ListMediaPane;
import player.view.main.MainFrame;
import player.view.messages.NativeLogFrame;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.log.NativeLog;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.streams.NativeStreams;

/**
 * Application entry-point.
 */
public class Start {

	private static final NativeStreams nativeStreams;

	// Redirect the native output streams to files, useful since VLC can
	// generate a lot of noisy native logs we don't care about
	// (on the other hand, if we don't look at the logs we might won't see
	// errors)
	static {
		// if (RuntimeUtil.isNix()) {
		// nativeStreams = new NativeStreams("stdout.log", "stderr.log");
		// }
		// else {
		nativeStreams = null;
		// }
	}

	private final JFrame mainFrame;

	private ListMediaPane listMediaFrame;

	@SuppressWarnings("unused")
	private final JFrame messagesFrame;

	@SuppressWarnings("unused")
	private final JFrame effectsFrame;

	@SuppressWarnings("unused")
	private final JFrame debugFrame;

	private final NativeLog nativeLog;

	public static void main(String[] args) throws InterruptedException {
		// This will locate LibVLC for the vast majority of cases
		new NativeDiscovery().discover();

		setLookAndFeel();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Start().start();
			}
		});
	}

	private static void setLookAndFeel() {
		String lookAndFeelClassName;
		if (RuntimeUtil.isNix()) {
			lookAndFeelClassName = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
		} else {
			lookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
		}
		try {
			UIManager.setLookAndFeel(lookAndFeelClassName);
		} catch (Exception e) {
			// Silently fail, it doesn't matter
		}
	}

	public Start() {
		EmbeddedMediaPlayerComponent mediaPlayerComponent = application().mediaPlayerComponent();
		listMediaFrame = new ListMediaPane();
		mainFrame = new MainFrame();
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.getMediaPlayer().stop();
				mediaPlayerComponent.release();
				if (nativeStreams != null) {
					nativeStreams.release();
				}
				application().post(ShutdownEvent.INSTANCE);
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}
		});

		mainFrame.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentMoved(ComponentEvent e) {
				e.getComponent().getLocationOnScreen();
			}
		});

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		EmbeddedMediaPlayer embeddedMediaPlayer = mediaPlayerComponent.getMediaPlayer();
		embeddedMediaPlayer.setFullScreenStrategy(new VlcjPlayerFullScreenStrategy(mainFrame));

		nativeLog = mediaPlayerComponent.getMediaPlayerFactory().newLog();

		messagesFrame = new NativeLogFrame(nativeLog);
		effectsFrame = new EffectsFrame();
		debugFrame = new DebugFrame();
	}

	private void setMediaLocation(Point point) {
		listMediaFrame.setLocation(point);
	}

	private void start() {
		mainFrame.setVisible(true);
		setMediaLocation(mainFrame.getLocationOnScreen());
		listMediaFrame.setVisible(true);
	}
}
