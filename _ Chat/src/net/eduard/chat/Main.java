package net.eduard.chat;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.api.lib.storage.StorageAPI;
import net.eduard.api.server.EduardPlugin;
import net.eduard.api.server.chat.ChatChannel;
import net.eduard.api.server.chat.ChatManager;
import net.eduard.chat.command.ChatReloadCommand;
import net.eduard.chat.command.ColorCommand;
import net.eduard.chat.command.TellCommand;
import net.eduard.chat.command.ToggleChatCommand;

public class Main extends EduardPlugin {

	private static Main plugin;
	private ChatManager chat;
	private Map<Player, Player> lastPrivateMessage = new HashMap<>();
	public static Main getInstance() {
		return plugin;
	}
	public void reload() {
		if (chat !=null) {
			chat.unregisterListener();
		}
		
		if (config.contains("Chat")) {
			chat = (ChatManager) config.get("Chat");
			StorageAPI.updateReferences();
		}else {
			chat = new ChatManager();
			chat.register(new ChatChannel("staff", "", "(&2STAFF)", "", "sc"));
			chat.register(this);
			save();
		}
		
	}
	public void save() {
		config.set("Chat", chat);
		config.saveConfig();
	}
	
	@Override
	public void onEnable() {
		plugin = this;
		
		reload();
		for (Class<?> claz : getClasses("net.eduard.chat.command")) {
			if (CommandManager.class.isAssignableFrom(claz)) {
				try {
					Mine.createCommand(plugin, (Command) claz.newInstance());
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
	public ChatManager getChat() {
		return chat;
	}
	public void setChat(ChatManager chat) {
		this.chat = chat;
	}
	public Map<Player, Player> getLastPrivateMessage() {
		return lastPrivateMessage;
	}
	public void setLastPrivateMessage(Map<Player, Player> lastPrivateMessage) {
		this.lastPrivateMessage = lastPrivateMessage;
	}

}
