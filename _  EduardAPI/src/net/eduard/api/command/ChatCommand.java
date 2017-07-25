package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.chat.ChatChannel;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.config.Config;
import net.eduard.api.event.PlayerChatEvent;
import net.eduard.api.manager.CMD;
import net.eduard.eduardapi.EduardAPI;

public class ChatCommand extends CMD {

	public Config config = new Config("group.yml");
	public String global = "§6(G) §f";
	public String getGroup(Player player) {
		return config.getString(player.getUniqueId().toString());
	}
	public ChatCommand() {
		super("global");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length == 0) {
			sendUsage(sender);
		} else {
			String message = ConfigSection.getText(0, args);
			if (API.onlyPlayer(sender)) {
				Player p = (Player) sender;
				PlayerChatEvent event = new PlayerChatEvent(p, 
						ChatChannel.LOCAL, API.CHAT_FORMAT,
						message.replaceFirst(" ", ""));

				EduardAPI.sendChat(event);
			}
		}
		return true;
	}
	// @EventHandler
	// public void event(AsyncPlayerChatEvent e) {
	// Player p = e.getPlayer();
	// String group = VaultAPI.getPermission().getPrimaryGroup(p);
	// String tag = VaultAPI.getChat().getGroupPrefix("null", group);
	// String a = format.replace("$group", tag).replace("$player",
	// p.getName());
	//
	// if (p.hasPermission(chatColorPerm)) {
	// a = a.replace("$message", CS.toChatMessage(e.getMessage()));
	// } else
	// a = a.replace("$message", e.getMessage());
	// a = a.replace("$channel", normal);
	// e.setFormat(a);
	// }
}
