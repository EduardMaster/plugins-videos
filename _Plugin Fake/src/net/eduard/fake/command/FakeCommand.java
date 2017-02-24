package net.eduard.fake.command;

import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.api.manager.PlayerAPI;
import net.eduard.fake.Main;

public class FakeCommand extends CMD{
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (Main.fakes.containsKey(p.getName())) {
					p.sendMessage("§c/fake <name>");
				} else if (Main.fakes.containsValue(p.getName())) {
					for (Entry<String, String> fake : Main.fakes.entrySet()) {
						if (fake.getValue().equals(p.getName())) {
							p.sendMessage(Main.config.message("name_reset_to_original"));
							p.setDisplayName(fake.getKey());
							p.setPlayerListName(fake.getKey());
							Main.fakes.put(fake.getKey(), fake.getKey());
							PlayerAPI.changeName(p, fake.getKey());
						}
					}
					p.sendMessage("§c/fake <name>");
				} else {
					p.sendMessage("§c/fake <name>");
				}
			} else {
				String name = API.toText(args[0]);
				if (Main.fakes.containsKey(p.getName())) {
					if (Main.fakes.containsKey(name)) {
						p.sendMessage(Main.config.message("name_exist_exeption")
							.replace("$name", name));
					} else if (Main.fakes.containsValue(name)) {
						p.sendMessage(Main.config.message("name_exist_exeption")
							.replace("$name", name));
					} else {
						p.sendMessage(Main.config.message("name_change_sussefully")
							.replace("$name", name));
						p.setDisplayName(name);
						p.setPlayerListName(name);
						Main.fakes.put(p.getName(), name);
						PlayerAPI.changeName(p, name);
					}
				} else if (Main.fakes.containsValue(p.getName())) {
					for (Entry<String, String> fake : Main.fakes.entrySet()) {
						if (fake.getValue().equals(p.getName())) {
							if (Main.fakes.containsKey(name)) {
								p.sendMessage(Main.config.message("name_exist_exeption")
									.replace("<name>", name));
							} else if (Main.fakes.containsValue(name)) {
								p.sendMessage(Main.config.message("name_exist_exeption")
									.replace("<name>", name));
							} else {
								p.sendMessage(
									Main.config.message("name_change_sussefully")
										.replace("$name", name));
								p.setDisplayName(name);
								p.setPlayerListName(name);
								Main.fakes.put(fake.getKey(), name);
								PlayerAPI.changeName(p, name);
							}
						}
					}
				} else {
					p.sendMessage(Main.config.message("name_change_sussefully")
						.replace("$name", name));
					Main.fakes.put(p.getName(), name);
					p.setDisplayName(name);
					p.setPlayerListName(name);
					PlayerAPI.changeName(p, name);
				}
			}
		}

		return true;
	}
}
