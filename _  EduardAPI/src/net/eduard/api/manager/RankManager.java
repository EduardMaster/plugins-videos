package net.eduard.api.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.game.Rank;
import net.eduard.api.setup.Mine.FakeOfflinePlayer;
import net.eduard.api.setup.StorageAPI.Storable;
import net.eduard.api.setup.VaultAPI;

/**
 * Controlador de Ranks
 * @author Eduard
 *
 */
public class RankManager implements Storable{

	private Map<String, Rank> ranks = new HashMap<>();
	
	private Map<UUID, String> players = new HashMap<>();
	
	private String first;
	
	private String last;
	
	
	public void promote(Player player) {
		Rank rank = getRank(player);
		Rank next = getRank(rank.getNextRank());
		players.put(player.getUniqueId(), next.getName().toLowerCase());
		
	}
	
	public void demote(Player player) {
		Rank rank = getRank(player);
		Rank next = getRank(rank.getPreviousRank());
		players.put(player.getUniqueId(), next.getName().toLowerCase());
	}
	
	public void updatePermissions() {
		for (Entry<String, Rank> map : ranks.entrySet()) {
			Rank rank = map.getValue();
			rank.updatePermissions();
		}
	}
	public Rank getFirstRank() {
		return ranks.get(first);
	}
	public Rank getLastRank() {
		return ranks.get(last);
	}
	
	
	public Map<UUID, String> getPlayers() {
		return players;
	}

	public void setPlayers(Map<UUID, String> players) {
		this.players = players;
	}

	public void setRanks(Map<String, Rank> ranks) {
		this.ranks = ranks;
	}

	public void updateGroups()
	{
		for (Entry<String, Rank> map : ranks.entrySet()) {
			Rank rank = map.getValue();
			for (Player p : API.getPlayers()) {
				VaultAPI.getPermission().playerRemoveGroup(p, rank.getName());
			}
		}
		for (Entry<UUID, String> map: players.entrySet()) {
			Rank rank = ranks.get(map.getValue());
			UUID id = map.getKey();
			FakeOfflinePlayer fake = new FakeOfflinePlayer(null,id);
			VaultAPI.getPermission().playerAddGroup(null,fake, rank.getName());
		}
	}
	
	public Map<String, Rank> getRanks() {
		return ranks;
	}
	public void setRanks(NavigableMap<String, Rank> ranks) {
		this.ranks = ranks;
	}

	public Rank getRank(Player player) {
		return ranks.getOrDefault(players.get(player.getUniqueId()),getFirstRank());
	}


	public void rankUp(Player p) {
		Rank rank = getRank(p);
		VaultAPI.getEconomy().withdrawPlayer(p, rank.getPrice());
	}
	public boolean canRankUp(Player p) {
		return VaultAPI.getEconomy().has(p, getRank(p).getPrice());

	}

	public int getPercent(Player p) {
		return 0;
	}

	public Rank getRank(String name) {
		return ranks.get(name.toLowerCase());
	}
	@Override
	public Object restore(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void store(Map<String, Object> map, Object object) {
		// TODO Auto-generated method stub
		
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

}
