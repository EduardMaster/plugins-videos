package net.eduard.api.factions;

import java.io.Serializable;

import org.bukkit.entity.Player;

import net.eduard.api.config.serial.PlayerSerial;
public class FactionMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PlayerSerial player;
	private Faction faction;
	private FactionRank rank = FactionRank.NEWBIE;
	private int kills;
	private int deaths;
	private double power;
	private double maxPower;

	public FactionMember(Player player) {
		setPlayer(new PlayerSerial(player));
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
	public PlayerSerial getPlayer() {
		return player;
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
	public void setPlayer(PlayerSerial player) {
		this.player = player;
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
	public FactionRel getRel(FactionMember member) {
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
			if (getRank().ordinal() < member.getRank().ordinal()) {
				return FactionRel.SUPERIOR;
			}
			if (getRank().ordinal() > member.getRank().ordinal()) {
				return FactionRel.SUBORDINATE;
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

	public FactionRank getRank() {
		return rank;
	}

	public void setRank(FactionRank rank) {
		this.rank = rank;
	}

}
