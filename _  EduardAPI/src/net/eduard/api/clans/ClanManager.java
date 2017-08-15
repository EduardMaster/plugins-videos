package net.eduard.api.clans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ClanManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Clan> clans = new HashMap<>();
	private Map<UUID, ClanMember> players = new HashMap<>();

	public boolean hasClan(Player player) {
		return getClan(player) != null;
	}
	private Clan getClan(Player player) {
		return getPlayer(player).getClan();
	}
	public ClanMember getPlayer(OfflinePlayer player) {
		if (players.containsKey(player.getUniqueId())) {
			return players.get(player.getUniqueId());
		}
		ClanMember member = new ClanMember(player);
		players.put(player.getUniqueId(), member);
		return member;
	}
	public Map<String, Clan> getClans() {
		return clans;
	}
	public void setClans(Map<String, Clan> clans) {
		this.clans = clans;
	}
	public Map<UUID, ClanMember> getPlayers() {
		return players;
	}
	public void setPlayers(Map<UUID, ClanMember> players) {
		this.players = players;
	}

}
