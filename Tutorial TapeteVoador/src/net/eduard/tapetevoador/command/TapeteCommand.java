package net.eduard.tapetevoador.command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.tapetevoador.TapeteVoador;

public class TapeteCommand extends CMD {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			if (TapeteVoador.players.contains(p)) {
				TapeteVoador.players.remove(p);
				p.sendMessage(TapeteVoador.config.message("Disable"));
				for (Block block : TapeteVoador.getTapeteBlocks(p.getLocation())) {
					if (block.getType() == TapeteVoador.getMaterial()) {
						block.setType(Material.AIR);
					}
				}
			} else {
				TapeteVoador.players.add(p);
				p.sendMessage(TapeteVoador.config.message("Enable"));
			}
		}
		return true;
	}

}
