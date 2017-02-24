
package net.eduard.spawn;

import net.eduard.api.config.Config;
import net.eduard.api.dev.Sounds;
import net.eduard.spawn.command.SetSpawnCMD;
import net.eduard.spawn.command.SpawnCMD;
import net.eduard.spawn.event.SpawnEvent;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public static Config config;

	public void onEnable() {

		config = new Config(this);
		Sounds sound = Sounds.create(Sound.ENDERMAN_TELEPORT);
		config.add("Spawn", "&6Voce foi teleportado para spawn!");
		config.add("DelaySpawn", "&bVoce vai ser teleportando em $time segundos!");
		config.add("NoSpawn", "&cO spawn ainda nao foi setado!");
		config.add("SetSpawn", "&bVoce setou o spawn!");
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
		new SpawnEvent();
		new SpawnCMD();
		new SetSpawnCMD();
	}

}
