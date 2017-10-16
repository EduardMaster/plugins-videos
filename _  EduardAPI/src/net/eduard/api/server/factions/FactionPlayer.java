package net.eduard.api.server.factions;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.eduard.api.game.Rank;
import net.eduard.api.setup.StorageAPI.Reference;
import net.eduard.api.setup.StorageAPI.Storable;
public class FactionPlayer implements Storable {

	@Reference
	private Faction faction;
	private UUID id;
	private String name;

	@Reference
	private Rank rank;
	private int kills;
	private int deaths;
	private double power;
	private double maxPower;
public FactionPlayer() {
	// TODO Auto-generated constructor stub
}
	public void setId(UUID id) {
		this.id = id;
	}
	public FactionPlayer(Player player) {
		this.id = player.getUniqueId();
		this.setName(player.getName());
	}
	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public double getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(double maxPower) {
		this.maxPower = maxPower;
	}

	public int getDeaths() {
		return deaths;
	}
	public Faction getFaction() {
		return faction;
	}
	public int getKills() {
		return kills;
	}
	public boolean hasFaction() {
		return this.faction != null;

	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}

	public FactionRel getRel(Faction faction) {

		if (faction == null) {
			return FactionRel.FREE_ZONE;
		}
		if (getFaction() == null) {
			return FactionRel.FREE_ZONE;
		}
		if (getFaction().equals(faction)) {
			return FactionRel.MEMBER;
		}
		return getFaction().getRel(faction);

	}
	public FactionRel getRel(FactionPlayer member) {
		if (member.getFaction() == null) {
			return FactionRel.NEUTRAL;
		}
		if (getFaction() == null) {
			return FactionRel.NEUTRAL;
		}
		if (getFaction().equals(member.getFaction())) {
			if (getFaction().getLeader().equals(member)) {
				return FactionRel.LEADER;
			}
			return FactionRel.MEMBER;
		}
		return getFaction().getRel(member.getFaction());
	}
	public FactionRel getRel(FactionClaim claim) {
		if (claim == null) {
			return FactionRel.FREE_ZONE;
		}
		if (getFaction() == null)
			return getRel(getFaction());
		return getFaction().getRel(claim);

	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public void sendMessage(String message) {
		getPlayer().sendMessage(message);
	}

	public boolean isOnline() {
		return getPlayer() != null;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(id);
	}

	public UUID getId() {
		return id;
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

	public double getKDR() {
		try {
			return kills / deaths;
		} catch (Exception e) {
			return 0D;
		}

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


}
