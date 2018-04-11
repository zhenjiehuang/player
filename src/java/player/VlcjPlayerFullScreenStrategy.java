package player;

import static player.Application.application;

import java.awt.Window;

import player.event.AfterExitFullScreenEvent;
import player.event.BeforeEnterFullScreenEvent;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;

final class VlcjPlayerFullScreenStrategy extends DefaultAdaptiveRuntimeFullScreenStrategy {

	VlcjPlayerFullScreenStrategy(Window window) {
		super(window);
	}

	@Override
	protected void beforeEnterFullScreen() {
		application().post(BeforeEnterFullScreenEvent.INSTANCE);
	}

	@Override
	protected void afterExitFullScreen() {
		application().post(AfterExitFullScreenEvent.INSTANCE);
	}
}
