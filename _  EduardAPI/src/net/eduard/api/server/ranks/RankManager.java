package net.eduard.api.server.ranks;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import net.eduard.api.setup.Mine.FakeOfflinePlayer;
import net.eduard.api.setup.StorageAPI.Reference;
import net.eduard.api.setup.StorageAPI.Storable;
import net.eduard.api.setup.VaultAPI;

/**
 * Controlador de Ranks
 * 
 * @author Eduard
 *
 */
public class RankManager implements Storable {
	private transient Map<Player, PermissionAttachment> permissions = new HashMap<>();

	private Map<String, Rank> ranks = new LinkedHashMap<>();

	private String first;

	private String last;

	private boolean perGroup = true;

	@Reference
	private Map<UUID, Rank> playersRanks = new HashMap<>();

	public void promote(Player player) {
		setRank(player, getPlayerRank(player).getNextRank());

	}

	public boolean isInLastRank(Player p) {
		return getLastRank().equals(getPlayerRank(p));
	}

	public void demote(Player player) {
		setRank(player, getPlayerRank(player).getPreviousRank());
	}

	public void updatePermissions() {
		if (perGroup) {
			for (Entry<String, Rank> map : ranks.entrySet()) {
				Rank rank = map.getValue();
				rank.removePermissions();
				rank.addPermissions();
			}
		}
	}

	public Rank getFirstRank() {
		return getRank(first);
	}

	public Rank getLastRank() {
		return getRank(last);
	}

	public Map<UUID, Rank> getPlayers() {
		return playersRanks;
	}

	public void setRanks(Map<String, Rank> ranks) {
		this.ranks = ranks;
	}

	public void updateGroups(Player p) {
		for (Entry<String, Rank> map : ranks.entrySet()) {
			Rank rank = map.getValue();
			VaultAPI.getPermission().playerRemoveGroup(p, rank.getName());
		}
		Rank rank = getPlayerRank(p);
		VaultAPI.getPermission().playerAddGroup(p, rank.getName());
	}

	public void updateGroups() {
		if (perGroup) {
			// for (Entry<String, Rank> map : ranks.entrySet()) {
			// Rank rank = map.getValue();
			// for (Player p : API.getPlayers()) {
			// VaultAPI.getPermission().playerRemoveGroup(p, rank.getName());
			// }
			// for (Entry<UUID, Rank> entry : playersRanks.entrySet()) {
			// UUID id = entry.getKey();
			// FakeOfflinePlayer fake = new FakeOfflinePlayer(null, id);
			// VaultAPI.getPermission().playerAddGroup(null, fake, rank.getName());
			// }
			// }
			for (Entry<UUID, Rank> map : playersRanks.entrySet()) {
				Rank rank = map.getValue();
				UUID id = map.getKey();
				FakeOfflinePlayer fake = new FakeOfflinePlayer(null, id);
				VaultAPI.getPermission().playerAddGroup(null, fake, rank.getName());
			}
		}
	}

	public Map<String, Rank> getRanks() {
		return ranks;
	}

	public void setRanks(NavigableMap<String, Rank> ranks) {
		this.ranks = ranks;
	}

	public Rank getPlayerRank(Player player) {
		return getPlayerRank(player.getUniqueId());
	}

	public Rank getPlayerRank(UUID playerId) {
		Rank rank = playersRanks.get(playerId);
		if (rank == null) {
			Player player = Bukkit.getPlayer(playerId);
			setRank(playerId, rank = getFirstRank());
			if (player != null) {
				addPermissions(player);
			}
			// rank = getFirstRank();
			// setRank(player.getUniqueId(), first);

		}
		return rank;
	}

	public void rankUp(Player p) {
		Rank rank = getPlayerRank(p);
		VaultAPI.getEconomy().withdrawPlayer(p, rank.getPrice());
		setRank(p, rank.getNextRank());
	}

	public void removePermissions(Player player) {
		if (perGroup) {
			getPlayerRank(player).removeGroup(player);
		} else {

			getPlayerRank(player).removePermissions(player);

		}

	}

	public void setRank(UUID playerId, Rank rank) {
		playersRanks.put(playerId, rank);
	}
	public void setFirstRank(UUID playerId) {
		setRank(playerId,getFirstRank());
	}
	public void setLastRank(UUID playerId) {
		setRank(playerId,getLastRank());
	}
	public void setRank(Player p, String rank) {

		removePermissions(p);
		setRank(p.getUniqueId(), getRank(rank));
		addPermissions(p);

	}

	public void addPermissions(Player p) {
		if (perGroup) {
			updateGroups(p);
		} else {
			getPlayerRank(p).addPermissions(p);
		}
	}

	public boolean canRankUp(Player p) {
		return VaultAPI.getEconomy().has(p, getPlayerRank(p).getPrice());

	}

	public double getPercent(Player p) {

		Rank rank = getPlayerRank(p);
		double dinheiroParaUpar = rank.getPrice();
		double dinheiroAtual = VaultAPI.getEconomy().getBalance(p);
		if (dinheiroAtual < 1 || dinheiroParaUpar < 1) {
			return 0;
		}
		double resultado = dinheiroAtual / dinheiroParaUpar;

		return resultado > 1 ? 1 : resultado;
	}

	public boolean existsRank(String name) {
		return getRank(name) != null;
	}

	public Rank getRank(String name) {
		for (Entry<String, Rank> entry : ranks.entrySet()) {
			String rankName = entry.getKey();
			Rank rank = entry.getValue();
			if (fix(name).equals(fix(rankName))) {
				return rank;
			}
			if (fix(name).equals(fix(rank.getName()))) {
				return rank;
			}

		}
		return null;
	}

	public static String fix(String text) {
		return text.replace(" ", "").trim().toLowerCase();
	}

	public RankManager() {
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

	public boolean isPerGroup() {
		return perGroup;
	}

	public void setPerGroup(boolean perGroup) {
		this.perGroup = perGroup;
	}

	public Map<Player, PermissionAttachment> getPermissions() {
		return permissions;
	}

	public void setPermissions(Map<Player, PermissionAttachment> permissions) {
		this.permissions = permissions;
	}

}
