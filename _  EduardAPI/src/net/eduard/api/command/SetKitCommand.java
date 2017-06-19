
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.api.util.Cs;

public class SetKitCommand extends CMD {

	public String message = "§6Seu inventario foi aplicado para todos jogadores!";
	public SetKitCommand() {
		super("setkit");
		register(getPlugin());
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			int range = 100;
			try {
				Integer.valueOf(args[0]);
			} catch (Exception e) {
			}
			Player p = (Player) sender;
			for (Player player : API.getPlayerAtRange(p.getLocation(), range)) {
				if (player != p) {
					player.getInventory().setArmorContents(
							p.getInventory().getArmorContents());
					player.getInventory()
							.setContents(p.getInventory().getContents());

				}
			}
			Cs.chat(p, message);
		}

		return true;
	}

}
