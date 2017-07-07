package net.eduard.template;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class RankUP {

	private static Map<UUID, String> players = new HashMap<>();

	private static Map<String, RankUP> ranks = new HashMap<>();

	private static String defaultRank = "membro";
	private static String commandAdd = "pex user %s group add %s";
	private static String commandRemove = "pex user %s group remove %s";

	public static Map<UUID, String> getPlayers() {
		return players;
	}

	public static void setPlayers(Map<UUID, String> players) {
		RankUP.players = players;
	}

	public static String getCommandAdd() {
		return commandAdd;
	}

	public static void setCommandAdd(String commandAdd) {
		RankUP.commandAdd = commandAdd;
	}

	public static String getCommandRemove() {
		return commandRemove;
	}

	public static void setCommandRemove(String commandRemove) {
		RankUP.commandRemove = commandRemove;
	}

	public static void rankUp(Player player) {
		UUID id = player.getUniqueId();
		RankUP rank = getRank(player);
		String name = player.getName();
		rank.getPrice();
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
				String.format(commandAdd, name, rank.getRankUp()));
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
				String.format(commandRemove, name, rank.getName()));

	}

	public static void setRank(OfflinePlayer player, String rank) {
		UUID id = player.getUniqueId();
		players.put(id, rank);

	}

	public static RankUP getRank(OfflinePlayer player) {
		UUID id = player.getUniqueId();
		if (!players.containsKey(id)) {
			players.put(id, defaultRank);
		}

		return ranks.get(players.get(id));

	}

	public static void savePlayers(File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config.set("", players);
		// for (Entry<UUID, String> map:players.entrySet()){
		// config.set(map.getKey(), map.getValue());
		// }
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadPlayers(File file) {
		players.clear();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection sec = config.getConfigurationSection("");
		if (sec == null)
			return;
		for (String name : sec.getKeys(false)) {
			players.put(UUID.fromString(name), sec.getString(name));
		}

	}

	public static void saveRanks(File file) {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		for (RankUP rank : ranks.values()) {
			ConfigurationSection sec = config
					.getConfigurationSection(rank.getName());
			if (sec == null) {
				sec = config.createSection(rank.getName());
			}
			sec.set("prefix", rank.getPrefix());
			sec.set("suffix", rank.getSuffix());
			sec.set("price", rank.getPrice());
			sec.set("next", rank.getRankUp());
		}
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadRanks(File file) {
		ranks.clear();
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		ConfigurationSection sec = config.getConfigurationSection("");
		if (sec == null)
			return;
		for (String name : sec.getKeys(false)) {
			RankUP rank = new RankUP();
			rank.setName(name);
			rank.setPrefix(sec.getString("prefix"));
			rank.setSuffix(sec.getString("suffix"));
			rank.setPrice(sec.getDouble("price"));
			rank.setRankUp(sec.getString("next"));

			ranks.put(name, rank);
		}
	}

	private String name;

	private String prefix;

	private String suffix;

	private String rankUp;
	private double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getRankUp() {
		return rankUp;
	}

	public void setRankUp(String rankUp) {
		this.rankUp = rankUp;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static Map<String, RankUP> getRanks() {
		return ranks;
	}

	public static void setRanks(Map<String, RankUP> ranks) {
		RankUP.ranks = ranks;
	}

	public static String getDefaultRank() {
		return defaultRank;
	}

	public static void setDefaultRank(String defaultRank) {
		RankUP.defaultRank = defaultRank;
	}

}
