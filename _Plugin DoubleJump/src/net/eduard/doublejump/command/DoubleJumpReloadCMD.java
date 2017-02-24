package net.eduard.doublejump.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.doublejump.DoubleJump;

public class DoubleJumpReloadCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		DoubleJump.reload();
		sender.sendMessage("§aDoubleJump foi recarregado!");
		return true;
	}

}
