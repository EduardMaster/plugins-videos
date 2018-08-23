package net.eduard.curso.tutoriais;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * API para utilização de metodos do Spigot com mais facilidade
 * @version 1.2
 * @since Lib v1.0
 * @author Eduard
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
	public static ChatColor SUCCESS = ChatColor.GREEN;
	public static ChatColor SUCCESS_ARGUMENT = ChatColor.DARK_GREEN;
	public static ChatColor ERROR = ChatColor.RED;
	public static ChatColor ERROR_ARGUMENT = ChatColor.DARK_RED;
	public static ChatColor MESSAGE = ChatColor.GOLD;
	public static ChatColor MESSAGE_ARGUMENT = ChatColor.YELLOW;
	public static ChatColor CHAT_CLEAR = ChatColor.WHITE;
	public static ChatColor CHAT_NORMAL = ChatColor.GRAY;
	public static ChatColor GUI_TITLE = ChatColor.BLACK;
	public static ChatColor GUI_TEXT = ChatColor.DARK_GRAY;
	public static ChatColor CONFIG = ChatColor.AQUA;
	public static ChatColor CONFIG_ARGUMENT = ChatColor.DARK_AQUA;
	public static ChatColor ITEM_TITLE = ChatColor.LIGHT_PURPLE;
	public static ChatColor ITEM_TEXT = ChatColor.DARK_PURPLE;
	public static ChatColor TITLE = ChatColor.DARK_BLUE;
	public static ChatColor TEXT = ChatColor.BLUE;

	
	/**
	 * API que controla o chat
	 * <br>Se chamava ChatAPI
	 * @author Eduard
	 * @version 1.1
	 * @since Lib v1.0
	 * 
	 */
	public static enum Chat {
		NORMAL("§A", "§2"), ERROR("§C", "§4"), SIMPLE("§7", "§8"), GAME("§E", "§6"), SETUP("§B", "§3"), BONUS("§9",
				"§1"), EFFECT("§D", "§5"), EXTRA("§F", "§0");

		private String dark;
		private String light;

		private Chat(String light, String dark) {
			setLight(light);
			setDark(dark);
		}

		public static String getHeart() {
			return "♥";
		}

		public static String getArrow() {
			return "➵";
		}

		public static String getArrowRight() {
			return "››";
		}

		public static String getArrowLeft() {
			return "‹‹";
		}

		public static String getSquared() {
			return "�?�";
		}

		public static String getInterrogation() {
			return "�?";
		}

		public static String getRedHeart() {
			return ChatColor.RED + getHeart();
		}

		public static String getAllSimbols() {
			return "�?��?�✔✖✗✘�?�⋆✢✣✤✥✦✩✪✫✬✭✵✴✳✲✱★✰✯✮✶✷✸✹✺✻✼�?��?�✽✡☆�?��?��?��?��?��?�✾✿�?��??�?�✌♼♽✂➣➢⬇➟⬆⬅➡✈✄➤➥➦➧➨➚➘➙➛➶➵➴➳➲➸➞�?➜➷➹➹➺➻➼➽Ⓜ⬛⬜ℹ☕▌▄▆▜▀▛█";
		}

		public static String getAllSimbols2() {
			return "™⚑�?☃⚠⚔⚖⚒⚙⚜⚀�?⚂⚃⚄⚅⚊⚋⚌�?�?⚎☰☱☲☳☴☵☶☷⚆⚇⚈⚉♿♩♪♫♬♭♮♯♠♡♢♗♖♕♔♧♛♦♥♤♣♘♙♚♛♜�?♞♟⚪➃➂�?➀➌➋➊➉➈➇➆➅➄☣☮☯⚫➌➋➊➉➈➇➆➅➄�?➎�?�?➑➒➓�?ⓑⓚ";
		}

		public static String getAllSimbols3() {
			return "웃유♋♀♂�?�¿⌚☑▲☠☢☿Ⓐ�?☤✉☒▼⌘⌛®©✎♒�?☼ツღ¡Σ☭✞℃℉ϟ☂¢£⌨⚛⌇☹☻☺☪½∞✆☎⌥⇧↩�?→↑↓⚣⚢⌲♺☟�?☞☜➫�?��?�◈�?◑«»‹›×±※�?�‽¶—�?�—–≈÷≠π†‡‡¥€‰�?•·";
		}

		public String getLightBold() {
			return this.light + "§l";
		}

		public String getDarkBold() {
			return this.dark + "§l";
		}

		public String getLight() {
			return this.light;
		}

		public void setLight(String light) {
			this.light = light;
		}

		public String getDark() {
			return this.dark;
		}

		public void setDark(String dark) {
			this.dark = dark;
		}

		public String toString() {
			return getDarkBold();
		}
	}
}
