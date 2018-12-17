package net.eduard.tapetevoador.command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.tapetevoador.Main;

public class TapeteCommand extends CommandManager {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.noConsole(sender)) {
			Player p = (Player) sender;
			if (Main.players.contains(p)) {
				Main.players.remove(p);
				p.sendMessage(Main.config.message("Disable"));
				for (Block block : Main.getTapeteBlocks(p.getLocation())) {
					if (block.getType() == Main.getMaterial()) {
						block.setType(Material.AIR);
					}
				}
			} else {
				Main.players.add(p);
				p.sendMessage(Main.config.message("Enable"));
			}
		}
		return true;
	}

}
