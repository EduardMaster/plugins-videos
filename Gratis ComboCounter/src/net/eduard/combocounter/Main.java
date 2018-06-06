
package net.eduard.combocounter;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.server.EduardPlugin;
import net.eduard.combocounter.command.ComboCounterCommand;
import net.eduard.combocounter.event.ComboCounterEvents;

public class Main extends EduardPlugin  {
	private static Main plugin;
	private Map<Player, Integer> combos = new HashMap<>();
	public static Main getInstance() {
		return plugin;
	}
	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	@Override
	public void onEnable() {
		plugin = this;
		new ComboCounterEvents().register(this);
		new ComboCounterCommand().register();
	}

	@Override
	public void onDisable() {
	}
	public Map<Player, Integer> getCombos() {
		return combos;
	}
	public void setCombos(Map<Player, Integer> combos) {
		this.combos = combos;
	}

}
