
package net.eduard.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.game.Sounds;
import net.eduard.api.manager.EduardPlugin;
import net.eduard.spawn.command.SetSpawnCommand;
import net.eduard.spawn.command.SpawnCommand;
import net.eduard.spawn.event.SpawnEvent;


/**
 * Preço: 12
 * Comandos 2
 * Eventos 2
 * Config 6
 * Sistema 1.75
 * @author Eduard-PC
 *
 */
public class Main extends EduardPlugin {
	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}

	public void init() {
		Sounds sound = Sounds.create(Sound.ENDERMAN_TELEPORT);
		messages.add("Spawn", "&6Voce foi teleportado para spawn!");
		messages.add("DelaySpawn",
				"&bVoce vai ser teleportando em $time segundos!");
		messages.add("NoSpawn", "&cO spawn ainda nao foi setado!");
		messages.add("SetSpawn", "&bVoce setou o spawn!");
		messages.saveDefault();
		for (World world : Bukkit.getWorlds()) {
			String name = world.getName().toLowerCase();
			config.add("teleportOnRespawnInWorld." + name, false);
		}
		config.add("teleportOnlyOnFirstJoin", false);
		config.add("spawnWithDelay", false);
		config.add("delayInSeconds", 4);
		config.add("Sound.OnTeleport", sound);
		config.add("Sound.OnJoin", sound);
		config.add("Sound.OnRespawn", sound);
		config.saveConfig();
		new SpawnEvent().register(this);
		new SpawnCommand();
		new SetSpawnCommand();

	}

	public void onDisable() {

	}

}
