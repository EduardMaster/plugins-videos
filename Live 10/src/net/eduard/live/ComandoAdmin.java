package net.eduard.live;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ComandoAdmin implements CommandExecutor {

	public static ArrayList<Player> admins = new ArrayList<>();

	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (admins.contains(p)) {
				admins.remove(p);
				for (Player jogador : Bukkit.getOnlinePlayers()) {
					if (jogador.equals(p))
						continue;
					jogador.showPlayer(p);
				}
				p.sendMessage("§aVoce saiu do modo admin");
				
				p.setGameMode(GameMode.SURVIVAL);
				
			} else {
				admins.add(p);
				p.getInventory().clear();
				p.setGameMode(GameMode.CREATIVE);
				ItemStack prender = new ItemStack(Material.BEDROCK);
				ItemMeta meta = prender.getItemMeta();
				meta.setDisplayName("§cPrender");
				prender.setItemMeta(meta);
				p.getInventory().setItem(5, prender);
				for (Player jogador : Bukkit.getOnlinePlayers()) {
					if (jogador.equals(p))
						continue;
					jogador.showPlayer(p);
				}
				p.sendMessage("§aVoce entrou do modo admin");
				
			}

		}
		return false;
	}

}
