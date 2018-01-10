
package net.eduard.api.command.map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.CommandManager;

public class MapPasteCommand extends CommandManager {

	public MapPasteCommand() {
		super("paste", "colar");

	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (API.onlyPlayer(sender)) {
//			Player p = (Player) sender;
//			if (!API.MAPS.containsKey(p)) {
//				p.sendMessage(
//						"§bEduardAPI §2Primeiro copie um Mapa:§a /e copy");
//				return true;
//			}

//			Arena map = API.MAPS.get(p);
//			map.copyPaste(p.getLocation());
//			p.sendMessage("§bEduardAPI §6Mapa colado com sucesso! ($blocks)"
//					.replace("$blocks", "" + map.getBlocks().size()));
		}
		return true;
	}

}
