package net.eduard.api.util;

import java.util.Collection;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;

public enum Cs {

	NORMAL("§A", "§2"), ERROR("§C", "§4"), SIMPLE("§7", "§8"), GAME("§E",
			"§6"), SETUP("§B", "§3"), BONUS("§9",
					"§1"), EFFECT("§D", "§5"), EXTRA("§F", "§0");

	private Cs(String light, String dark) {
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
		return "❑";
	}

	public static String getInterrogation() {
		return "➁";
	}

	public static String getRedHeart() {
		return ChatColor.RED + getHeart();
	}

	public static String getALlSimbols() {
		return "❤❥✔✖✗✘❂⋆✢✣✤✥✦✩✪✫✬✭✵✴✳✲✱★✰✯✮✶✷✸✹✺✻✼❄❅✽✡☆❋❊❉❈❇❆✾✿❀❁❃✌♼♽✂➣➢⬇➟⬆⬅➡✈✄➤➥➦➧➨➚➘➙➛➶➵➴➳➲➸➞➝➜➷➹➹➺➻➼➽Ⓜ⬛⬜ℹ☕▌▄▆▜▀▛█";
	}

	public static String getAllSimbols2() {
		return "™⚑⚐☃⚠⚔⚖⚒⚙⚜⚀⚁⚂⚃⚄⚅⚊⚋⚌⚍⚏⚎☰☱☲☳☴☵☶☷⚆⚇⚈⚉♿♩♪♫♬♭♮♯♠♡♢♗♖♕♔♧♛♦♥♤♣♘♙♚♛♜♝♞♟⚪➃➂➁➀➌➋➊➉➈➇➆➅➄☣☮☯⚫➌➋➊➉➈➇➆➅➄➍➎➏➐➑➒➓ⓐⓑⓚ";
	}

	public static String getAllSimbols3() {
		return "웃유♋♀♂❣¿⌚☑▲☠☢☿Ⓐ✍☤✉☒▼⌘⌛®©✎♒☁☼ツღ¡Σ☭✞℃℉ϟ☂¢£⌨⚛⌇☹☻☺☪½∞✆☎⌥⇧↩←→↑↓⚣⚢⌲♺☟☝☞☜➫❑❒◈◐◑«»‹›×±※⁂‽¶—⁄—–≈÷≠π†‡‡¥€‰●•·";
	}
	private String dark;

	private String light;

	public String getLightBold() {
		return light + "§l";
	}
	public String getDarkBold() {
		return dark + "§l";
	}

	public String getLight() {
		return light;
	}

	public void setLight(String light) {
		this.light = light;
	}

	public String getDark() {
		return dark;
	}

	public void setDark(String dark) {
		this.dark = dark;
	}
	public String toString() {
		return getDarkBold();
	}
	public static void chat(CommandSender sender, String message) {
		sender.sendMessage(API.SERVER_TAG + message);
	}

	public static void all(Object... objects) {

		broadcast(objects);
		console(objects);
	}
	public static void broadcast(Object... objects) {

		for (Player p : API.getPlayers()) {
			chat(p, getText(objects));
		}
	}
	public static String getText(Object... objects) {
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append(object);

		}
		return builder.toString();
	}

	public static String getText(String text, Player player) {
		for (Entry<String, Replacer> value : API.REPLACERS.entrySet()) {
			try {
				if (text.contains(value.getKey())){
					text = text.replace(value.getKey(),
							value.getValue().getText(player).toString());
					
				}
				
			} catch (Exception e) {
			}

		}
		return text;
	}
	public static void sendMessage(Collection<Player> players,Object... objects){
		String message = getText(objects);
		for (Player player:players){
			player.sendMessage(message);
		}
		
	}

	public static String getTime(int time) {

		return getTime(time, " segundo(s)", " minuto(s) ");

	}

	public static String getTime(int time, String second, String minute) {
		if (time >= 60) {
			int min = time / 60;
			int sec = time % 60;
			if (sec == 0) {
				return min + minute;
			} else {
				return min + minute + sec + second;
			}

		}
		return time + second;
	}

	public static String getTimeMid(int time) {

		return getTime(time, " seg", " min ");

	}

	public static String getTimeSmall(int time) {

		return getTime(time, "s", "m");

	}

	public static String[] toArray(Collection<String> list){
		return list.toArray(new String[0]);
	}
	public static boolean startWith(String message, String text) {
		return message.toLowerCase().startsWith(text.toLowerCase());
	}

	public static String toChatMessage(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static String toConfigMessage(String text) {
		return text.replace("§", "&");
	}

	public static String toDecimal(Object number) {
		return toDecimal(number, 2);
	}

	public static String toDecimal(Object number, int max) {
		String text = "" + number;
		if (text.contains(".")) {
			String[] split = text.replace(".", ",").split(",");
			if (split[1].length() >= max) {
				return split[0] + "." + split[1].substring(0, max);
			}
			return text;
		}
		return text;
	}

	

	public static String toText(Collection<String> message) {
		return message.toString().replace("[", "").replace("]", "");
	}

	public static String toText(int size, String text) {

		return text.length() > size ? text.substring(0, size) : text;
	}

	public static String toText(String... message) {

		return message.toString().replace("[", "").replace("]", "");
	}

	public static String toText(String text) {

		return toText(16, text);
	}

	public static String toTitle(String name) {
		char first = name.toUpperCase().charAt(0);
		name = name.toLowerCase();
		return first + name.substring(1, name.length());

	}

	public static String toTitle(String name, String replacer) {
		if (name.contains("_")) {
			String customName = "";
			for (String newName : name.split("_")) {
				if (newName != null) {
					customName += toTitle(newName) + replacer;
				}
			}
			return customName;
		}
		return toTitle(name);
	}
	public static boolean contains(String message, String text) {
		return message.toLowerCase().contains(text.toLowerCase());
	}
	public static void broadcastMessage(Object... objects) {

		for (Player p : API.getPlayers()) {
			p.sendMessage(getText(objects));
		}
		Bukkit.getConsoleSender().sendMessage(getText(objects));
	}
	public static void console(Object... objects) {

		Bukkit.getConsoleSender().sendMessage(API.SERVER_TAG + getText(objects));
	}
	public static void consoleMessage(Object... objects) {
		Bukkit.getConsoleSender().sendMessage(getText(objects));
	}

	public static void broadcast(String message, String permision) {
		for (Player p : API.getPlayers()) {
			if (p.hasPermission(permision)) {
				chat(p, message);
			}
		}
	}
	public static String getText(int init,String... args){
		StringBuilder text = new StringBuilder();
		int id = 0;
		for (String arg :args){
			if (id <init){
				id++;
				continue;
			}
			text.append(" "+toChatMessage(arg));
			id++;
		}
		return text.toString();
	}
	public static Double toDouble(Object object) {

		if (object == null) {
			return 0D;
		}
		if (object instanceof Double) {
			return (Double) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.doubleValue();
		}
		try {
			return Double.valueOf(object.toString());
		} catch (Exception e) {
			return 0D;
		}

	}

	public static Float toFloat(Object object) {

		if (object == null) {
			return 0F;
		}
		if (object instanceof Float) {
			return (Float) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.floatValue();
		}
		try {
			return Float.valueOf(object.toString());
		} catch (Exception e) {
			return 0F;
		}

	}

	public static Integer toInt(Object object) {

		if (object == null) {
			return 0;
		}
		if (object instanceof Integer) {
			return (Integer) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.intValue();
		}
		try {
			return Integer.valueOf(object.toString());
		} catch (Exception e) {
			return 0;
		}

	}

	public static Integer toInteger(Object object) {
		return toInt(object);
	}

	public static Long toLong(Object object) {

		if (object == null) {
			return 0L;
		}
		if (object instanceof Long) {
			return (Long) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.longValue();
		}
		try {
			return Long.valueOf(object.toString());
		} catch (Exception e) {
			return 0L;
		}
	}

	public static Short toShort(Object object) {

		if (object == null) {
			return 0;
		}
		if (object instanceof Short) {
			return (Short) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.shortValue();
		}
		try {
			return Short.valueOf(object.toString());
		} catch (Exception e) {
			return 0;
		}

	}
	public static Boolean toBoolean(Object obj) {

		if (obj == null) {
			return false;
		}
		if (obj instanceof Boolean) {
			return (Boolean) obj;
		}
		try {
			return Boolean.valueOf(obj.toString());
		} catch (Exception e) {
			return false;
		}
	}

	public static Byte toByte(Object object) {

		if (object == null) {
			return 0;
		}
		if (object instanceof Byte) {
			return (Byte) object;
		}
		if (object instanceof Number) {
			Number number = (Number) object;
			return number.byteValue();
		}
		try {
			return Byte.valueOf(object.toString());
		} catch (Exception e) {
			return 0;
		}

	}
	public static String toString(Object object) {

		return object == null ? "" : object.toString();
	}
}


