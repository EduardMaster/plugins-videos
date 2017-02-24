
package net.eduard.hg.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.hg.manager.HGCommand;

public class Kit extends HGCommand {

	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			ArrayList<String> list = new ArrayList<>();
			if (args.length == 1) {
//				for (KitType kit : KitType.values()) {
//					try {
//						list.add(KitAPI.getKit(kit).getName());
//					} catch (Exception ex) {
//						continue;
//					}
//					
//				}
			}

			return list;
		}

		return null;
	}

	public void command(CommandSender sender, String cmd, String... args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage("§c/kit <name>");
			} else {
				String name = args[0];
				try {
//					for (KitType kitType: KitType.values()) {
//						if (kitType.toString().replace("_", "").equalsIgnoreCase(name)) {
//							if (p.hasPermission("kit." + name.toLowerCase())) {
//								KitAPI.selectKit(p,KitAPI.getKit(kitType));
//							} else {
//								p.sendMessage("§cVoce não tem esse Kit §7" + name);
//								p.sendMessage("§aCompre ele na Loja de Kits!");
//							}
//							return;
//						}
//					}
					
				} catch (Exception ex) {
					
				}
				p.sendMessage("§cEsse Kit " + name + " não existe!");
			}
			

		}
	}

}
