
package net.eduard.spawn;

import org.bukkit.Bukkit;
import org.bukkit.World;

import net.eduard.api.lib.game.SoundEffect;
import net.eduard.api.lib.game.Title;
import net.eduard.api.server.EduardPlugin;
import net.eduard.spawn.command.SetSpawnCommand;
import net.eduard.spawn.command.SpawnCommand;
import net.eduard.spawn.events.SpawnEvents;

public class SpawnPlugin extends EduardPlugin {
	private static SpawnPlugin instance;

	public static SpawnPlugin getInstance() {
		return instance;
	}
 
	@Override
	public void onEnable() {
		instance = this;
		free = true;

		messages.add("Spawn teleport", "&6Voce foi teleportado para spawn!");
		messages.add("Spawn delay teleport", "&bVoce vai ser teleportando em $time segundos!");
		messages.add("Spawn not setted", "&cO spawn ainda nao foi setado!");
		messages.add("Spawn setted", "&bVoce setou o spawn!");
		messages.saveConfig();
		for (World world : Bukkit.getWorlds()) {
			String name = world.getName().toLowerCase();
			config.add("Teleport on respawn in world." + name, false,
					"Teleportar quando voce resnacer no mundo " + name);
		}
		config.add("Teleport on respawn", true, "Teleportar quando renascer");
		config.add("Teleport on join", true, "Teleportar quando entrar");
		config.add("Teleport only on first join", false, "Teleportar apenas quando entrar pela primeira vez");
		config.add("Teleport delay", false, "Atraso ao teleportar");
		config.add("Teleport delay seconds", 4, "Tempo de atraso ao teleportar");

		config.add("Sound on teleport", SoundEffect.create("ENDERMAN_TELEPORT"), "Som ao teleportar");
		config.add("Sound on join", SoundEffect.create("ENDERMAN_TELEPORT"), "Som ao entrar ");
		config.add("Sound on respawn", SoundEffect.create("ENDERMAN_TELEPORT"),"Som ao renascer");

		config.add("Title on teleport enabled", true, "Enviar um titulo ao ir pro spawn");
		config.add("Title on teleport", new Title(20, 20 * 2, 20, "§6Inicio", "§eVoce foi para o Spawn!"),
				"Configure como vai ser o titulo ao ir para o spawn");

		config.saveConfig();
		new SpawnEvents().register(this);
		new SpawnCommand().register();
		new SetSpawnCommand().register();

	}

	@Override
	public void onDisable() {

	}

}
