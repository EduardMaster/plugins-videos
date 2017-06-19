package net.eduard.template;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public final class RankManager {

	private static Map<UUID, String> players = new HashMap<>();
	private static Map<String, Rank> ranks = new HashMap<>();
	private static String defaultRank = "default";
	private static String commandAdd = "pex group $player group add $rank";
	private static String commandRemove = "pex group $player group remove $rank";

	public static Map<String, Rank> getRanks() {
		return ranks;
	}
	public static void setRanks(Map<String, Rank> ranks) {
		RankManager.ranks = ranks;
	}
	public static String getDefaultRank() {
		return defaultRank;
	}
	public static void setDefaultRank(String defaultRank) {
		RankManager.defaultRank = defaultRank;
	}
	public static String getCommandAdd() {
		return commandAdd;
	}
	public static void setCommandAdd(String commandAdd) {
		RankManager.commandAdd = commandAdd;
	}
	public static String getCommandRemove() {
		return commandRemove;
	}
	public static void setCommandRemove(String commandRemove) {
		RankManager.commandRemove = commandRemove;
	}
	public static Rank getRank(OfflinePlayer player) {
		UUID id = player.getUniqueId();
		if (!players.containsKey(id)) {
			players.put(id, defaultRank);
		}
		String rank = players.get(id);
		if (hasRank(rank)) {
			return getRank(rank);
		}
		return null;

	}
	public static String translate(String msg){
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static void loadRanks(File file) {
		ranks.clear();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection sec = config.getConfigurationSection("");
		if (sec==null){
			return;
		}
		for (String name:sec.getKeys(false)){
			Rank rank = new Rank(name, sec.getString("rank"));
			rank.setPrefix(translate(sec.getString("prefix")));
			rank.setSuffix(translate(sec.getString("suffix")));
			rank.setPrice(sec.getDouble("price"));
			ranks.put(name, rank);
		}

	}
	public static Rank getRank(String name) {
		return ranks.get(name.toLowerCase());
	}
	public static boolean hasRank(String name) {
		return ranks.containsKey(name.toLowerCase());
	}

	public static void rankUp(Player player) {
		Rank rank = getRank(player);
		String name = player.getName();
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandAdd
				.replace("$player", name).replace("$rank", rank.getRank()));
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandRemove
				.replace("$player", name).replace("$rank", rank.getName()));

	}

	public static boolean canRankUp(Player player) {

		return false;
	}

	public static Map<UUID, String> getPlayers() {
		return players;
	}
	public static void setPlayers(Map<UUID, String> players) {
		RankManager.players = players;
	}

}
