
package net.eduard.antbot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.manager.EventAPI;

public class BotDetector extends EventAPI {

	public BotDetector() {
		Main.time.timer(20,new BukkitRunnable() {
			
			@Override
			public void run() {
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
			Main.time.delay(10,new Runnable() {
				
				@Override
				public void run() {
					whitelist = false;
				}
			});
		}
		contagem = 0;
	}

}
