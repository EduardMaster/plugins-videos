
package net.eduard.api.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.eduard.api.manager.CMD;
import net.eduard.api.util.Cs;

public class ListCommand extends CMD {

	public String messageTarget = "§6O sender §e$target §6foi mutado por §a$sender §6motido: §c$reason";
	public String message = "§cVoce foi mutado";
	public ListCommand() {
		super("mute");
		hasEvents = true;
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		sender.sendMessage(" ");
		sender.sendMessage("   \u00A72* \u00A7aAtualmente tem");
		sender.sendMessage("   \u00A72* \u00A7a"
				+ Bukkit.getOnlinePlayers().length + " senderes online.");
		sender.sendMessage(" ");
		return true;
	}
	@EventHandler
	public void event(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (p.hasMetadata("muted")) {
			e.setCancelled(true);
			Cs.chat(p, message);

		}
	}
}
