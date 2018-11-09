
package net.eduard.essentials;

import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.lib.config.Config;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.api.lib.storage.StorageAPI;
import net.eduard.api.server.EduardPlugin;
import net.eduard.api.server.Systems;

public class EssentialsPlugin extends EduardPlugin {
	private static EssentialsPlugin plugin;
	private Config commands;

	public static EssentialsPlugin getInstance() {
		return plugin;
	}



	@Override
	public void onEnable() {
		plugin = this;
		commands = new Config(this, "commands.yml");
		reload();
	}

	public void reloadCommmands() {

		commands.reloadConfig();

		for (CommandManager cmd : CommandManager.getCommandsRegistred().values()) {
			if (cmd.getPlugin().equals(this)) {
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
					cmd.registerCommand(this);
					cmd.register(this);
				}

			}
			StorageAPI.updateReferences();
			commands.saveConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void save() {

	}

	public void reload() {
		reloadCommmands();
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

}
