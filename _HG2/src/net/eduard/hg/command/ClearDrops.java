
package net.eduard.hg.command;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;

import net.eduard.hg.manager.HGCommand;

public class ClearDrops extends HGCommand  {


	public void command(CommandSender sender, String cmd, String... args) {
		World world = Bukkit.getWorld("world");
		for (Item entity:world.getEntitiesByClass(Item.class)) {
			entity.remove();
		}
		sender.sendMessage("§6Os drops foram removidos!");
	}

}
