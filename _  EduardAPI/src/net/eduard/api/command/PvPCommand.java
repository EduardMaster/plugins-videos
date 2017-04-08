
package net.eduard.api.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.Commands;

public class PvPCommand extends Commands {

	public String world = "world";
	public String messageOn = "§6O PvP foi ativado!";
	public String messageOff = "§6O PvP foi desativado!";
	public PvPCommand() {
		super("pvp");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		World world = Bukkit.getWorld(this.world);
		if (world.getPVP()) {
			world.setPVP(false);
			API.chat(sender,messageOff);
		} else {
			world.setPVP(true);
			API.chat(sender,messageOn);
		}
		return true;
	}

}
