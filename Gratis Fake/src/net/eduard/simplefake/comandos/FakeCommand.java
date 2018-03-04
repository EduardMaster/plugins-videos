package net.eduard.simplefake.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CommandManager;
import net.eduard.api.setup.Mine;
import net.eduard.simplefake.FakeAPI;
import net.eduard.simplefake.Main;

public class FakeCommand extends CommandManager {
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (FakeAPI.isFake(p)) {
					FakeAPI.reset(p);
				} else
					return false;
			} else {
				String name = Mine.removeBrackets(args[0]);
				if (FakeAPI.getData().containsValue(name)
						|| FakeAPI.getOriginal().containsValue(name)) {
					p.sendMessage(Main.config.message("name_exist_exeption")
							.replace("$name", name));
				} else {
					FakeAPI.fake(p, name);
					p.sendMessage(Main.config.message("name_change_sussefully")
							.replace("$name", name));
				}
			}
		}

		return true;
	}
}
