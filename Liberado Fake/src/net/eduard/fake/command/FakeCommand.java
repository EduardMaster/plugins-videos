package net.eduard.fake.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.api.util.Cs;
import net.eduard.fake.FakeAPI;
import net.eduard.fake.Main;

public class FakeCommand extends CMD {
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
				String name = Cs.toText(args[0]);
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
