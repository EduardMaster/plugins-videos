package net.eduard.api.setup;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public final class TextAPI {
	public static void sendClickable(Player player, String clickMessage,
			String hoverMessage, String command) {
		player.spigot().sendMessage(getClickable(clickMessage, hoverMessage, command));
	}
	public static TextComponent getClickable(String message, String hoverMessage,
			String command) {
		TextComponent text = new TextComponent(message);
		text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder(hoverMessage).create()));
		text.setClickEvent(new ClickEvent(Action.RUN_COMMAND, command));
		return text;
	}
}
