
package net.eduard.api.command.config;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.manager.CommandManager;

public class ConfigReloadAllCommand extends CommandManager {

	public ConfigReloadAllCommand() {
		super("all", "todas");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		Config.reloadConfigs();
		API.chat(sender,
				"§aTodas configurações de todos plugins foram recarregadas!");
		return true;
	}

}
