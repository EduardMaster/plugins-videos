package net.eduard.api.setup;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

public final class ExtraAPI {

	public static Random RANDOM = new Random();
	private static Map<String, Replacer> replacers = new HashMap<>();
	private static List<String> lineBreakers = new ArrayList<>();
	public static final float TNT = 4F;
	public static final float CREEPER = 3F;
	public static final float WALKING_VELOCITY = -0.08F;
	public static final int DAY_IN_HOUR = 24;
	public static final int DAY_IN_MINUTES = DAY_IN_HOUR * 60;
	public static final int DAY_IN_SECONDS = DAY_IN_MINUTES * 60;
	public static final long DAY_IN_TICKS = DAY_IN_SECONDS * 20;
	public static final long DAY_IN_LONG = DAY_IN_TICKS * 50;

	public static Sound getSound(Object object) {
		String str = object.toString().replace("_", "").trim();
		for (Sound sound : Sound.values()) {
			if (str.equals("" + sound.ordinal())) {
				return sound;
			}
			if (str.equalsIgnoreCase(sound.name().replace("_", ""))) {
				return sound;
			}

		}
		return null;
	}
	public static List<String> getLivingEntities(String argument) {
		List<String> list = new ArrayList<>();
		argument = argument.trim().replace("_", "");
		for (EntityType type : EntityType.values()) {
			if (type == EntityType.PLAYER)
				continue;
			if (type.isAlive() & type.isSpawnable()) {
				String text = ExtraAPI.toTitle(type.name(), "");
				String line = type.name().trim().replace("_", "");
				if (startWith(line, argument)) {
					list.add(text);
				}
			}
			
		}
		return list;
	}
	@SuppressWarnings("deprecation")
	public static EntityType getEntity(Object object) {
		String str = object.toString().replace("_", "").trim();
		for (EntityType type : EntityType.values()) {
			if (str.equals("" + type.getTypeId())) {
				return type;
			}
			if (str.equalsIgnoreCase("" + type.getName())) {
				return type;
			}
			if (str.equalsIgnoreCase(type.name().replace("_", ""))) {
				return type;
			}

		}
		return null;
	}
	public static List<String> getEnchants(String argument) {
		if (argument == null) {
			argument = "";
		}
		argument = argument.trim().replace("_", "");
		List<String> list = new ArrayList<>();

		for (Enchantment enchant : Enchantment.values()) {
			String text = ExtraAPI.toTitle(enchant.getName(), "");
			String line = enchant.getName().trim().replace("_", "");
			if (startWith(line, argument)) {
				list.add(text);
			}
		}
		return list;

	}
	public static List<String> getSounds(String argument) {
		if (argument == null) {
			argument = "";
		}
		argument = argument.trim().replace("_", "");
		List<String> list = new ArrayList<>();

		for (Sound enchant : Sound.values()) {
			String text = ExtraAPI.toTitle(enchant.name(), "");
			String line = enchant.name().trim().replace("_", "");
			if (startWith(line, argument)) {
				list.add(text);
			}
		}
		return list;

	}
	@SuppressWarnings("deprecation")
	public static Enchantment getEnchant(Object object) {
		String str = object.toString().replace("_", "").trim();
		for (Enchantment enchant : Enchantment.values()) {
			if (str.equals("" + enchant.getId())) {
				return enchant;
			}
			if (str.equalsIgnoreCase(enchant.getName().replace("_", ""))) {
				return enchant;
			}
		}
		return null;
	}

	public static boolean getChance(double chance) {

		return RANDOM.nextDouble() <= chance;
	}
	public static boolean hasPerm(CommandSender sender, String permission,
			int max, int min) {

		boolean has = false;
		for (int i = max; i >= min; i--) {
			if (sender.hasPermission(permission + "." + i)) {
				has = true;
			}
		}
		return has;

	}

	public static PluginCommand command(String commandName,
			CommandExecutor command, String permission,
			String permissionMessage) {

		PluginCommand cmd = Bukkit.getPluginCommand(commandName);
		cmd.setExecutor(command);
		cmd.setPermission(permission);
		cmd.setPermissionMessage(permissionMessage);
		return cmd;
	}
	public static boolean commandEquals(String message, String cmd) {
		String command = message;
		if (message.contains(" "))
			command = message.split(" ")[0];
		return command.equalsIgnoreCase(cmd);
	}
	public static boolean commandStartWith(String message, String cmd) {
		return startWith(message, "/" + cmd);
	}
	public static boolean hasPlugin(String plugin) {
		return Bukkit.getPluginManager().getPlugin(plugin) != null;
	}

	/**
	 * Retorna se (now < (seconds + before));
	 * 
	 * @param before
	 *            (Antes)
	 * @param seconds
	 *            (Cooldown)
	 * @return
	 */
	public static boolean inCooldown(long before, long seconds) {

		long now = System.currentTimeMillis();
		long cooldown = seconds * 1000;
		return now <= (cooldown + before);

	}
	public static long getCooldown(long before, long seconds) {

		long now = System.currentTimeMillis();
		long cooldown = seconds * 1000;

		// +5 - 19 + 15

		return +cooldown - now + before;

	}

	public static boolean hasAPI() {
		return hasPlugin("EduardAPI");
	}
	public static Scoreboard getMainScoreboard() {
		return Bukkit.getScoreboardManager().getMainScoreboard();
	}
	public static long getNow() {
		return System.currentTimeMillis();
	}
	@SafeVarargs
	public static <E> E getRandom(E... objects) {
		if (objects.length >= 1)
			return objects[(RANDOM.nextInt(objects.length - 1))];
		return null;
	}
	public static <E> E getRandom(List<E> objects) {
		if (objects.size() >= 1)
			return objects.get((RANDOM.nextInt(objects.size() - 1)));
		return null;
	}
	public static boolean isMultBy(int number1, int numer2) {

		return number1 % numer2 == 0;
	}
	public static void addPermission(String permission) {
		Bukkit.getPluginManager().addPermission(new Permission(permission));
	}
	public static boolean newExplosion(Location location, float power,
			boolean breakBlocks, boolean makeFire) {
		return location.getWorld().createExplosion(location.getX(),
				location.getY(), location.getZ(), power, breakBlocks, makeFire);
	}
	public static BukkitTask timer(Plugin plugin, long ticks, Runnable run) {
		if (run instanceof BukkitRunnable) {
			BukkitRunnable bukkitRunnable = (BukkitRunnable) run;
			return bukkitRunnable.runTaskTimer(plugin, ticks, ticks);
		}
		return Bukkit.getScheduler().runTaskTimer(plugin, run, ticks, ticks);
	}
	public static BukkitTask timers(Plugin plugin, long ticks, Runnable run) {
		if (run instanceof BukkitRunnable) {
			BukkitRunnable bukkitRunnable = (BukkitRunnable) run;
			return bukkitRunnable.runTaskTimerAsynchronously(plugin, ticks,
					ticks);
		}
		return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, run,
				ticks, ticks);
	}

	public static double getRandomDouble(double minValue, double maxValue) {

		double min = Math.min(minValue, maxValue),
				max = Math.max(minValue, maxValue);
		return min + (max - min) * RANDOM.nextDouble();
	}
	public static Firework newFirework(Location location, int high, Color color,
			Color fade, boolean trail, boolean flicker) {
		Firework firework = location.getWorld().spawn(location, Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		meta.setPower(high);
		meta.addEffect(FireworkEffect.builder().trail(trail).flicker(flicker)
				.withColor(color).withFade(fade).build());
		firework.setFireworkMeta(meta);
		return firework;
	}
	public static int getRandomInt(int minValue, int maxValue) {

		int min = Math.min(minValue, maxValue),
				max = Math.max(minValue, maxValue);
		return min + RANDOM.nextInt(max - min + 1);
	}

	public static void runCommand(String command) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}
	public static void makeCommand(String command) {
		runCommand(command);
	}

	public static boolean isIpProxy(String ip) {
		try {
			String url = "http://botscout.com/test/?ip=" + ip;
			Scanner scanner = new Scanner(new URL(url).openStream());
			if (scanner.findInLine("Y") != null) {
				scanner.close();
				return true;
			}
			scanner.close();

		} catch (Exception e) {
		}
		return false;
	}
	public static void callEvent(Event event) {
		Bukkit.getPluginManager().callEvent(event);
	}
	public static void event(Listener event, Plugin plugin) {
		registerEvents(event, plugin);
	}
	public static void registerEvents(Listener event, Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(event, plugin);
	}

	public static BukkitTask delay(Plugin plugin, long ticks, Runnable run) {
		return Bukkit.getScheduler().runTaskLater(plugin, run, ticks);
	}
	public static BukkitTask delays(Plugin plugin, long ticks, Runnable run) {
		return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, run,
				ticks);
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
		if (name == null)
			return "";
		char first = name.toUpperCase().charAt(0);
		name = name.toLowerCase();
		return first + name.substring(1, name.length());

	}

	public static String toTitle(String name, String replacer) {
		if (name.contains("_")) {
			String customName = "";
			int id = 0;
			for (String newName : name.split("_")) {
				if (id != 0) {
					customName += replacer;
				}
				id++;
				customName += toTitle(newName);
			}
			return customName;
		}
		return toTitle(name);
	}
	public static boolean contains(String message, String text) {
		return message.toLowerCase().contains(text.toLowerCase());
	}

	public static String getText(int init, String... args) {
		StringBuilder text = new StringBuilder();
		int id = 0;
		for (String arg : args) {
			if (id < init) {
				id++;
				continue;
			}
			text.append(" " + toChatMessage(arg));
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
	public static void sendActionBar(String message) {
		for (Player player : GameAPI.getPlayers()) {
			RexAPI.sendActionBar(player, message);
		}
	}
	public static void addReplacer(String key, Replacer value) {
		replacers.put(key, value);
	}
	public static Replacer getReplacer(String key) {
		return replacers.get(key);
	}

	public static String getWraps(String value) {
		for (String wrap : lineBreakers) {
			value = value.replaceAll(wrap, "\n");
		}
		return value;
	}

	public static void chatMessage(CommandSender sender, Object... objects) {
		sender.sendMessage(getMessage(objects));
	}
	public static String getMessage(Object... objects) {
		String message = getText(objects);
		message = message.replace("$>", ChatAPI.getArrowRight());
		return getWraps(message);
	}

	public static void consoleMessage(Object... objects) {
		chatMessage(Bukkit.getConsoleSender(), objects);
	}

	public static void broadcastMessage(Object... objects) {

		for (Player p : GameAPI.getPlayers()) {
			chatMessage(p, objects);
		}
	}

	public static String getText(Object... objects) {
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append(object);

		}
		return builder.toString();
	}
	public static List<String> toLines(String text, int size) {

		List<String> lista = new ArrayList<>();

		String x = text;

		int id = 1;
		while (x.length() >= size) {
			String cut = x.substring(0, size);
			x = text.substring(id * size);
			id++;
			lista.add(cut);
		}
		lista.add(x);
		return lista;

	}
	public static void newWrapper(String wrap) {
		if (!lineBreakers.contains(wrap))
			lineBreakers.add(wrap);

	}
	public static String getComment(String line) {

		String[] split = line.split("#");
		if (split.length > 0)
			return line.replaceFirst(split[0] + "#", "").replaceFirst(" ", "");
		return line.replaceFirst("#", "").replaceFirst(" ", "");

	}

	public static List<String> getConfigLines(Path path) throws Exception {
		return Files.readAllLines(path);
	}

	public static String getKey(String line, String space) {
		line = line.replaceFirst(space, "");
		return line.split(":")[0];

	}

	public static String getList(String line) {
		String[] split = line.split("-");
		if (split.length > 0)
			return line.replaceFirst(split[0] + "-", "").replaceFirst(" ", "");
		return line.replaceFirst("-", "");
	}

	public static String getPath(String path) {
		if (path.startsWith("#")) {
			path.replaceFirst("#", "$");
		}
		if (path.startsWith("-")) {
			path.replaceFirst("-", "$");
		}
		if (path.contains(":")) {
			path.replace(":", "$");
		}
		return path;
	}

	public static String getSpace(int id) {
		String space = "";
		for (int i = 0; i < id; i++) {
			space += "  ";
		}
		return space;
	}

	public static int getTime(String line) {
		int value = 0;
		while (line.startsWith("  ")) {
			line = line.replaceFirst("  ", "");
			value++;
		}
		return value;
	}

	public static String getValue(String line, String space) {
		line = line.replaceFirst(space, "");
		if (line.endsWith(":")) {
			return "";
		}
		String[] split = line.split(":");
		String result = line.replaceFirst(split[0] + ":", "").replaceFirst(" ",
				"");
		return result;

	}
	public static String removeQuotes(String message) {
		if (message.startsWith("'")) {
			message = message.replaceFirst("'", "");
		}
		if (message.startsWith("\"")) {
			message = message.replaceFirst("\"", "");
		}
		if (message.endsWith("'")) {
			message = message.substring(0, message.length() - 1);
		}
		if (message.endsWith("\"")) {
			message = message.substring(0, message.length() - 1);
		}
		return message;
	}
	public static void setConfigLines(Path path, List<String> lines)
			throws Exception {
		Files.write(path, lines);
	}
	public static boolean isComment(String line) {
		return line.trim().startsWith("#");
	}

	public static boolean isList(String line) {
		return line.trim().startsWith("-");
	}

	public static boolean isSection(String line) {
		return !isList(line) & !isComment(line) & line.contains(":");
	}

	public static String getReplacers(String text, Player player) {
		for (Entry<String, Replacer> value : replacers.entrySet()) {
			if (text.contains(value.getKey())) {
				try {
					text = text.replace(value.getKey(),
							"" + value.getValue().getText(player));

				} catch (Exception e) {
					consoleMessage(e.getMessage());
				}

			}

		}
		return text;
	}
	public static String[] wordWrap(String rawString, int lineLength) {
		if (rawString == null) {
			return new String[]{""};
		}

		if ((rawString.length() <= lineLength)
				&& (!(rawString.contains("\n")))) {
			return new String[]{rawString};
		}

		char[] rawChars = new StringBuilder().append(rawString).append(' ')
				.toString().toCharArray();
		StringBuilder word = new StringBuilder();
		StringBuilder line = new StringBuilder();
		List<String> lines = new LinkedList<>();
		int lineColorChars = 0;

		for (int i = 0; i < rawChars.length; ++i) {
			char c = rawChars[i];

			if (c == 167) {
				word.append(ChatColor.getByChar(rawChars[(i + 1)]));
				lineColorChars += 2;
				++i;
			} else if ((c == ' ') || (c == '\n')) {
				if ((line.length() == 0) && (word.length() > lineLength)) {
					for (String partialWord : word.toString()
							.split(new StringBuilder().append("(?<=\\G.{")
									.append(lineLength).append("})")
									.toString()))
						lines.add(partialWord);
				} else if (line.length() + word.length()
						- lineColorChars == lineLength) {
					line.append(word);
					lines.add(line.toString());
					line = new StringBuilder();
					lineColorChars = 0;
				} else if (line.length() + 1 + word.length()
						- lineColorChars > lineLength) {
					for (String partialWord : word.toString()
							.split(new StringBuilder().append("(?<=\\G.{")
									.append(lineLength).append("})")
									.toString())) {
						lines.add(line.toString());
						line = new StringBuilder(partialWord);
					}
					lineColorChars = 0;
				} else {
					if (line.length() > 0) {
						line.append(' ');
					}
					line.append(word);
				}
				word = new StringBuilder();

				if (c == '\n') {
					lines.add(line.toString());
					line = new StringBuilder();
				}
			} else {
				word.append(c);
			}
		}

		if (line.length() > 0) {
			lines.add(line.toString());
		}

		if ((lines.get(0).length() == 0) || (lines.get(0).charAt(0) != 167)) {
			lines.set(0, new StringBuilder().append(ChatColor.WHITE)
					.append(lines.get(0)).toString());
		}
		for (int i = 1; i < lines.size(); ++i) {
			String pLine = lines.get(i - 1);
			String subLine = lines.get(i);

			char color = pLine.charAt(pLine.lastIndexOf(167) + 1);
			if ((subLine.length() == 0) || (subLine.charAt(0) != 167)) {
				lines.set(i,
						new StringBuilder().append(ChatColor.getByChar(color))
								.append(subLine).toString());
			}
		}

		return (lines.toArray(new String[lines.size()]));
	}

}
