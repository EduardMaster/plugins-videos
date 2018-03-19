package net.eduard.live;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

public class ComandoBauCair implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			FallingBlock bauCaindo = p.getWorld().spawnFallingBlock(p.getLocation(), Material.CHEST.getId(), (byte) 0);
			if (bauCaindo instanceof Chest) {
				Chest chest = (Chest) bauCaindo;
				Bukkit.broadcastMessage("Foi");
				
			}else {
				Bukkit.broadcastMessage("Nao Foi");
			}
		}
		return false;
	}

}
