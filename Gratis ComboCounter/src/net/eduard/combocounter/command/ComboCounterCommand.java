
package net.eduard.combocounter.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.setup.manager.CommandManager;
import net.eduard.combocounter.Main;

public class ComboCounterCommand extends CommandManager {

	public ComboCounterCommand() {
		super("combocounter");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (Main.getInstance().getCombos().containsKey(p)) {
				p.sendMessage("§cVoce desativou o contador de Combos!");
			} else {
				Main.getInstance().getCombos().put(p, 0);
				p.sendMessage("§aVoce ativou o contador de Combos!");
			}

		}
		return true;
	}

}
