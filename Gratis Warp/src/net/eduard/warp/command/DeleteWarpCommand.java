
package net.eduard.warp.command;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.eduard.warp.WarpPlugin;

public class DeleteWarpCommand implements CommandExecutor {

	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
		if (args.length == 0) {
			return false;
		}
		String name = args[0];
		if (!WarpPlugin.getWarps().containsKey(name.toLowerCase())) {
			sender.sendMessage("§cNão existe esse Warp §4" + name);
		} else {
			WarpPlugin.getWarps().remove(name.toLowerCase());
			new File(WarpPlugin.getInstance().getDataFolder(),"/Warps/"+name.toLowerCase()+".yml").delete();
			sender.sendMessage("§bWarp deletada §3"+name);
		}
		
		return true;
	}

}
