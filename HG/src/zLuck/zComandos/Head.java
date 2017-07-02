package zLuck.zComandos;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.piracraft.api.Main;
import zLuck.zUteis.Uteis;

public class Head implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("head")) {
			if (Main.isStaff(p)) {
				if (args.length == 0) {
					p.sendMessage("§cUse /head [jogador]");
					return true;
				}
				if (args.length == 1) {
			        ItemStack cabeça = new ItemStack(Material.SKULL_ITEM);
			        cabeça.setDurability((short)3);
			        SkullMeta cabeçameta = (SkullMeta)cabeça.getItemMeta();
			        cabeçameta.setOwner(args[0]);
			        cabeça.setItemMeta(cabeçameta);
			        p.getInventory().addItem(cabeça);
			  	    p.sendMessage(Uteis.prefix + " §7Voce pegou a cabeça de §c§l" + args[0]);
				}
			} else {
				p.sendMessage(Uteis.sempermissão);
			}
		}
		return false;
	}
	
	

}
