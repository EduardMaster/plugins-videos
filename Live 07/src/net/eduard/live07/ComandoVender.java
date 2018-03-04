package net.eduard.live07;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ComandoVender implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			PlayerInventory inv = p.getInventory();
			
			double precoDaRestone = 5D;
			int contagem = 0;
			for (ItemStack item : inv.getContents()) {
				if (item == null)continue;
				if (item.getType() == Material.REDSTONE) {
					contagem = contagem + item.getAmount();
				}
				
			}
			inv.remove(Material.REDSTONE);
			double precoFinal = contagem * precoDaRestone;
			
			VaultAPI.getEconomy().depositPlayer(p, precoFinal);
			p.sendMessage("§aVoce vendeu "+ contagem+" de redtones por "+precoFinal);
			
			
		}
		
		return false;
	}

}
