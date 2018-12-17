
package net.eduard.antibot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.lib.manager.EventsManager;

public class BotDetector extends EventsManager {

	public BotDetector() {
		Main.time.asyncTimer(new BukkitRunnable() {
			
			@Override
			public void run() {
				test();
			}
		},20,20);
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
			Main.time.asyncDelay(new Runnable() {
				
				@Override
				public void run() {
					whitelist = false;
				}
			},10);
		}
		contagem = 0;
	}

}
