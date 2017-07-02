package zLuck.zComandos;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.piracraft.api.Main;
import zLuck.zUteis.Uteis;

public class Skit implements CommandExecutor{
	
	private HashMap<Player, ItemStack[]> skit = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("skit")) {
			if (Main.isStaff(p)) {
				if (args.length == 0) {
					p.sendMessage("§7Use /skit <§c§lCRIAR§7/§c§lAPLICAR§7> <§c§lRAIO§7>");					
				}
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("criar")) {
						skit.put(p, p.getInventory().getContents());
						p.sendMessage("§7Agora use /skit aplicar <§c§lRAIO§7>");
					}
					if (args[0].equalsIgnoreCase("aplicar")) {
						if (skit.containsKey(p)) {
							p.sendMessage("§7Use /skit aplicar <§c§lRAIO§7>");
						} else {
							p.sendMessage("§7Use primeiramente /skit criar");
						}
					}
				}
				if (args.length == 2) {
					if (skit.containsKey(p)) {
						for (Entity ent : p.getNearbyEntities(Double.valueOf(args[1]), Double.valueOf(args[1]), Double.valueOf(args[1]))) {
							if (ent instanceof Player) {
								Player perto = (Player) ent;
								perto.getInventory().setContents(skit.get(p));
							}
						}
					} else {
						p.sendMessage("§7Use /skit criar");
					}
				}
			} else {
				p.sendMessage(Uteis.sempermissão);
			}
		}
		return false;
	}

}
