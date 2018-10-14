
package net.eduard.essentials.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import net.eduard.api.lib.manager.CommandManager;

public class GodCommand extends CommandManager {

	public static ArrayList<Player> gods = new ArrayList<>();

	public GodCommand() {
		super("god");
	}

	@Override

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				if (gods.contains(p)) {

					gods.remove(p);
				} else {
					gods.add(p);

				}
			} else {
				String sub = args[0];
				if (sub.equalsIgnoreCase("on")) {

					if (gods.contains(p)) {

					} else {
						gods.add(p);
					}
				} else if (sub.equalsIgnoreCase("off")) {
					if (gods.contains(p)) {
						gods.remove(p);
					} else {

					}

				} else {
					sendUsage(sender);
				}
			}
		}
		return true;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (gods.contains(p)) {
				e.setCancelled(true);
			}
		}
	}

}
