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

import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import com.google.common.eventbus.EventBus;

import player.event.TickEvent;
import player.view.action.mediaplayer.MediaPlayerActions;
import player.view.main.ListMediaPane;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

/**
 * Global application state.
 */
public final class Application {

	private static final String RESOURCE_BUNDLE_BASE_NAME = "strings/player";

	private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME);

	private final EventBus eventBus;

	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

	private final ListMediaPane listMediaPane;

	private final MediaPlayerActions mediaPlayerActions;

	private final ScheduledExecutorService tickService = Executors.newSingleThreadScheduledExecutor();

	private static final class ApplicationHolder {
		private static final Application INSTANCE = new Application();
	}

	public static Application application() {
		return ApplicationHolder.INSTANCE;
	}

	public static ResourceBundle resources() {
		return resourceBundle;
	}

	private Application() {
		eventBus = new EventBus();
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent() {
			private static final long serialVersionUID = -1061659091221897666L;

			@Override
			protected String[] onGetMediaPlayerFactoryExtraArgs() {
				return new String[] { "--no-osd" }; // Disables the display of
													// the snapshot filename
													// (amongst other things)
			}
		};
		mediaPlayerActions = new MediaPlayerActions(mediaPlayerComponent.getMediaPlayer());
		tickService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				eventBus.post(TickEvent.INSTANCE);
			}
		}, 0, 1000, TimeUnit.MILLISECONDS);

		listMediaPane = new ListMediaPane();
	}

	public void subscribe(Object subscriber) {
		eventBus.register(subscriber);
	}

	public void post(Object event) {
		// Events are always posted and processed on the Swing Event Dispatch
		// thread
		if (SwingUtilities.isEventDispatchThread()) {
			eventBus.post(event);
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					eventBus.post(event);
				}
			});
		}
	}

	public EmbeddedMediaPlayerComponent mediaPlayerComponent() {
		return mediaPlayerComponent;
	}

	public MediaPlayerActions mediaPlayerActions() {
		return mediaPlayerActions;
	}

	public ListMediaPane listMediaPane() {
		return listMediaPane;
	}

}