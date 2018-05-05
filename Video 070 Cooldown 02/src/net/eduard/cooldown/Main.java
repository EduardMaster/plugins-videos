
package net.eduard.cooldown;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.EduardPlugin;
import net.eduard.api.setup.manager.CooldownManager;

public class Main extends EduardPlugin implements Listener {
	private static Main plugin;
	public static Main getInstance() {
		return plugin;
	}
	public CooldownManager cooldown = new CooldownManager();
	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	@Override
	public void onEnable() {
		plugin = this;
		cooldown.setOnCooldownMessage("§aDigite mais devagar por favor!");
	}

	@Override
	public void onDisable() {
	}
	@EventHandler
	public void event(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (!cooldown.cooldown(p)) {
			e.setCancelled(true);
		}
	}

}
