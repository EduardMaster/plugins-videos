package net.eduard.api.setup;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * API para utilização de metodos do Spigot com mais facilidade
 * 
 * @author Eduard-PC
 *
 */
public final class SpigotAPI {
	public static void sendMessage(Player player, String message, String hoverMessage, String clickCommand) {
		sendMessage(Arrays.asList(player), message, hoverMessage, clickCommand);
	}

	public static void sendMessage(Collection<Player> players, String message, String hoverMessage,
			String clickCommand) {
		sendMessage(players, message, Arrays.asList(hoverMessage), clickCommand);
	}

	public static void sendMessage(Collection<Player> players, String message, List<String> hoverMessages,
			String clickCommand) {
		sendMessage(players, message, hoverMessages, clickCommand, true);
	}

	public static void sendMessage(Collection<Player> players, String message, List<String> hoverMessages,
			String clickCommand, boolean runCommand) {

		String lastColor = "";
		String msg = message;
		ComponentBuilder builder = new ComponentBuilder("");
		for (String line : hoverMessages) {
			builder.append(line + "\n");
		}
		HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, builder.create());
		ClickEvent clickEvent = new ClickEvent(
				runCommand ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND, clickCommand);
		boolean stop = false;
		while (!stop) {
			String send = "";
			if (msg.length() > 55) {
				send = msg.substring(0, 55);
				String color = getLastColor(send);
				lastColor = color;
				msg = lastColor + msg.substring(55);
			} else {
				send = msg;
				stop = true;
			}
			TextComponent spigotMessage = new TextComponent(send);
			spigotMessage.setClickEvent(clickEvent);
			spigotMessage.setHoverEvent(hoverEvent);
			for (Player player : players) {
				player.spigot().sendMessage(spigotMessage);
			}

		}

	}

	public static String getLastColor(String text) {
		char[] array = text.toLowerCase().toCharArray();
		String lastColor = "";
		String lastFormat = "";
		char lastChar = 0;
		for (int i = 0; i < array.length; i++) {
			char c = array[i];
			if (lastChar == '§') {
				lastColor = "§" + c;
			}
			lastChar = c;
		}
		return lastColor + lastFormat;
	}
}
