
package net.eduard.eduardapi.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.config.Config;
import net.eduard.api.manager.CMD;

public class ConfigReloadAllCommand extends CMD {

	public ConfigReloadAllCommand() {
		super("all", "todas");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		Config.reloadConfigs();
		ConfigSection.chat(sender,
				"§aTodas configurações de todos plugins foram recarregadas!");
		return true;
	}

}
