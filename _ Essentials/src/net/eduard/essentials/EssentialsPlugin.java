
package net.eduard.essentials;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.eduard.api.lib.config.Config;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.api.lib.storage.StorageAPI;
import net.eduard.api.server.EduardPlugin;

public class EssentialsPlugin extends EduardPlugin {
	private static EssentialsPlugin plugin;
	private Config commands;
	private ArrayList<Player> slimeChunkActive = new ArrayList<>();
	public static EssentialsPlugin getInstance() {
		return plugin;
	}

	@Override
	public void onEnable() {
		plugin = this;
		commands = new Config(this, "commands.yml");
		reload();
	}

	public void save() {

	}

	public void reload() {
		commands.reloadConfig();

		for (CommandManager cmd : CommandManager.getCommandsRegistred().values()) {
			if (cmd.getPluginInstance().equals(this)) {
				cmd.unregisterCommand();
				cmd.unregisterListener();
			}
		}
		try {
			for (Class<?> claz : getClasses("net.eduard.essentials.command")) {
				if (CommandManager.class.isAssignableFrom(claz)) {
					CommandManager cmd = (CommandManager) claz.newInstance();
					String path = "command." + cmd.getClass().getSimpleName().toLowerCase();
					if (commands.contains(path)) {
						cmd = (CommandManager) commands.get(path);
					} else {
						commands.set(path, cmd);
					}
					config.add(path, true);
					if (config.getBoolean(path)) {
						cmd.registerCommand(this);
						cmd.register(this);
					}
				}

			}
			StorageAPI.updateReferences();
			commands.saveConfig();
			config.saveConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		save();
	}

	public Config getCommands() {
		return commands;
	}

	public void setCommands(Config commands) {
		this.commands = commands;
	}

	public ArrayList<Player> getSlimeChunkActive() {
		return slimeChunkActive;
	}

	public void setSlimeChunkActive(ArrayList<Player> slimeChunkActive) {
		this.slimeChunkActive = slimeChunkActive;
	}

}
