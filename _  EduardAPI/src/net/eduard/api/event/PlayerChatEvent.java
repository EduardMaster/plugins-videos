package net.eduard.api.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import net.eduard.api.API;
import net.eduard.api.chat.ChatChannel;

public class PlayerChatEvent extends PlayerEvent implements Cancellable {
	private ChatChannel channel = ChatChannel.LOCAL;
	private String format = "<channel> <player>: <message>";
	private Map<String, String> tags = new HashMap<>();
	
	public Map<String, String> getTags() {
		return tags;
	}

	public void setTagValue(String tag, String value) {
		tags.put(tag, value);
	}
	public String getTagValue(String tag) {
		return tags.get(tag);
	}
	private String message = "";
	private boolean cancelled;
	private List<Player> players = new ArrayList<>();
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	private static final HandlerList handlers = new HandlerList();
	public PlayerChatEvent(Player player, ChatChannel channel, String format,
			String message) {
		super(player);
		this.format = format;
		this.channel = channel;
		this.message = message;
		this.message = message.replace("<message>", message);
		this.players = API.getPlayers();
	}
	public void addReplacer(String text, String replacer) {
		this.format.replace(text, replacer);
	}

	public ChatChannel getChannel() {
		return channel;
	}

	public void setChannel(ChatChannel channel) {
		this.channel = channel;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;

	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
