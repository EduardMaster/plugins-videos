
package net.eduard.doublejump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.config.Config;
import net.eduard.api.config.Section;
import net.eduard.api.dev.Jump;
import net.eduard.api.dev.Sounds;
import net.eduard.doublejump.command.DoubleJumpReloadCMD;
import net.eduard.doublejump.event.DoubleJumpEvent;

public class DoubleJump extends JavaPlugin {

	public static DoubleJump plugin;
	public static Config config;
	public static List<Player> players = new ArrayList<>();
	public static Map<World, Jump> jumps = new HashMap<>();
	public void onEnable() {

		plugin = this;
		config = new Config(this);
		new DoubleJumpReloadCMD();
		new DoubleJumpEvent();
		for (World world : Bukkit.getWorlds()) {
			Section sec = config.getSection(world.getName().toLowerCase());
			Sounds sound = Sounds.create(Sound.ENDERMAN_TELEPORT);
			Jump jump1 = new Jump(true, 0.5, 2.5, sound);
			sec.set("enable",true);
			config.add(world.getName(), jump1);
		}
		config.saveConfig();
		reload();
	}

	public static void reload() {
		jumps.clear();
		config.reloadConfig();
		for (World world : Bukkit.getWorlds()) {
			jumps.put(world, (Jump) config.get(world.getName()));
		}
	}
}
