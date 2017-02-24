
package net.eduard.antbot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import net.eduard.api.dev.Game;
import net.eduard.api.manager.EventAPI;
import net.eduard.api.util.SimpleEffect;

public class BotDetector extends EventAPI {

	public BotDetector() {
		new Game(1).timer(new SimpleEffect() {

			public void effect() {
				test();
			}
		});
	}

	private int contagem = 0;

	private boolean whitelist = false;

	@EventHandler
	private void onBotTryToJoin(AsyncPlayerPreLoginEvent e) {

		if (Main.ips_proxy.contains(e.getAddress().getHostAddress())) {
			e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Main.config.message("Bot").replace("$ender", "/n"));
		}
		if (whitelist) {
			e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Main.config.message("Bot").replace("$enter", "/n"));
			return;
		}

		++contagem;
	}

	public void test() {
		if (contagem > 6) {
			whitelist = true;
			new Game(10).delay(new SimpleEffect() {

				public void effect() {
					whitelist = false;
				}
			});
		}
		contagem = 0;
	}

}
