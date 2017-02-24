package net.eduard.kit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.manager.CMD;
import net.eduard.kit.Main;


public class SetKitCommand extends CMD{

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (API.noConsole(sender)) {

			Player p = (Player) sender;
			if (command.getName().equalsIgnoreCase("setkit")) {
				if (args.length == 0) {
					p.sendMessage("§c/setkit <kit> [seconds]");
				} else {

					String name = args[0];
					Config kit = Main.getKit(name);
					kit.deleteConfig();
					if (args.length >= 2) {
						kit.set("cooldown", API.toInt(args[1]));
					} else {
						kit.set("cooldown", 0);
					}
					PlayerInventory inv = p.getInventory();
					if (inv.getHelmet() != null) {
						kit.set("helmet", inv.getHelmet());
					}
					if (inv.getChestplate() != null) {
						kit.set("chestplate", inv.getChestplate());
					}
					if (inv.getLeggings() != null) {
						kit.set("leggings", inv.getLeggings());
					}
					if (inv.getBoots() != null) {
						kit.set("boots", inv.getBoots());
					}
					int id = 0;
					for (ItemStack item : p.getInventory().getContents()) {
						if (item != null) {
							kit.set("slot_" + id, item);
						}
						id++;
					}
					kit.saveConfig();
					p.sendMessage(Main.config.message("set_kit").replace("$kit", name));
				}
			}

		}

		return true;
	}
	
	

}
