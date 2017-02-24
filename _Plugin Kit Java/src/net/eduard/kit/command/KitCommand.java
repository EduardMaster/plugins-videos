package net.eduard.kit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.manager.CMD;
import net.eduard.kit.Main;


public class KitCommand extends CMD {

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
		if (API.noConsole(sender)) {

			Player p = (Player) sender;
			if (command.getName().equalsIgnoreCase("kit")) {

				if (args.length == 0) {
					p.sendMessage("§c/kit <kit>");
				} else {
					PlayerInventory inv = p.getInventory();
					String name = args[0];
					Config kit = Main.getKit(name);
					if (p.hasPermission("kit.kits." + name)) {

						if (kit.existsConfig()) {
							if (kit.contains("cooldowns." + p.getName())) {
								long now = API.getTime();
								long before =
									kit.getLong("cooldowns." + p.getName());
								long cooldown = kit.getLong("cooldown") * 1000;
								long diference = now - before;
								long result = (cooldown - diference) / 1000L;
								if (diference <= cooldown) {
									p.sendMessage(Main.config.message("kit_in_cooldown")
										.replace("$seconds", "" + result));

									return true;
								} else {
									kit.set("cooldowns." + p.getName(),
											API.getTime());
									kit.saveConfig();
								}
							} else {
								kit.set("cooldowns." + p.getName(),
									API.getTime());
								kit.saveConfig();
							}
							if (Main.config.getBoolean("clear_inv")) {
								inv.clear();
							}
							for (int id = 0; id < 4 * 9; id++) {
								if (kit.contains("slot_" + id)) {
									inv.setItem(id, kit.getItem("slot_" + id));
								}
							}
							if (kit.contains("helmet")) {
								inv.setHelmet(kit.getItem("helmet"));
							}
							if (kit.contains("chestplate")) {
								inv.setChestplate(kit.getItem("chestplate"));
							}
							if (kit.contains("leggings")) {
								inv.setLeggings(kit.getItem("leggings"));
							}
							if (kit.contains("boots")) {
								inv.setBoots(kit.getItem("boots"));
							}
							p.sendMessage(
								Main.config.message("kit").replace("$kit", name));

						} else {
							p.sendMessage(
								Main.config.message("no_kit").replace("$kit", name));
						}
					} else {
						p.sendMessage(
							Main.config.message("no_perm_kit").replace("$kit", name));
					}
				}
			}

		}

		return true;
	}


}
