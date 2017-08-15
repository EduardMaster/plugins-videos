package net.eduard.api.factions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.eduard.api.config.serial.LocationSerial;

public class Faction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FactionManager manager;

	public static FactionManager getManager() {
		return manager;
	}

	public static void setManager(FactionManager manager) {
		Faction.manager = manager;

	}

	private String name;

	private String prefix;

	private String suffix;

	private double power;

	private double maxPower;
	private long lastTimeOnline;
	private LocationSerial base;
	private FactionMember leader;
	private List<FactionClaim> claims = new ArrayList<>();
	private List<FactionMember> members = new ArrayList<>();
	private List<FactionMember> invites = new ArrayList<>();
	private List<FactionMember> allies = new ArrayList<>();
	private List<FactionMember> rivals = new ArrayList<>();
	private int kills;
	private int deaths;
	public Faction(String name, String prefix) {
		this.name = name;
		this.prefix = prefix;
	}
	public void create(String name) {

	}
	public List<FactionMember> getAllies() {
		return allies;
	}
	public LocationSerial getBase() {
		return base;
	}
	public List<FactionClaim> getClaims() {
		return claims;
	}
	public int getDeaths() {
		return deaths;
	}

	public List<FactionMember> getInvites() {
		return invites;
	}

	public int getKills() {
		return kills;
	}

	public long getLastTimeOnline() {
		return lastTimeOnline;
	}

	public FactionMember getLeader() {
		return leader;
	}
	public double getMaxPower() {
		return maxPower;
	}
	public List<FactionMember> getMembers() {
		return members;
	}
	public String getName() {
		return name;
	}
	public double getPower() {
		return power;
	}
	public String getPrefix() {
		return prefix;
	}
	public List<FactionMember> getRivals() {
		return rivals;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setAllies(List<FactionMember> allies) {
		this.allies = allies;
	}
	public void setBase(LocationSerial base) {
		this.base = base;
	}

	public void setClaims(List<FactionClaim> claims) {
		this.claims = claims;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public void setInvites(List<FactionMember> invites) {
		this.invites = invites;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void setLastTimeOnline(long lastTimeOnline) {
		this.lastTimeOnline = lastTimeOnline;
	}

	public void setLeader(FactionMember leader) {
		this.leader = leader;
	}

	public void setMaxPower(double maxPower) {
		this.maxPower = maxPower;
	}

	public void setMembers(List<FactionMember> members) {
		this.members = members;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setRivals(List<FactionMember> rivals) {
		this.rivals = rivals;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public FactionRel getRel(FactionClaim claim) {
		Faction faction = claim.getFaction();
		if (faction == null) {
			return FactionRel.FREE_ZONE;
		}
		if (equals(faction)) {
			if (claim.isOnAttack()) {
				return FactionRel.WAR;
			}
			return FactionRel.MEMBER;
		}
		return getRel(claim.getFaction());
		
	}
	public FactionRel getRel(Faction faction) {
		if (faction == null) {
			return FactionRel.FREE_ZONE;
		}
		if (getAllies().contains(faction)) {
			return FactionRel.ALLY;
		}
		if (getRivals().contains(faction)) {
			return FactionRel.RIVAL;
		}
		if (faction.equals(manager.getWarZone())) {
			return FactionRel.WAR_ZONE;
		}
		if (faction.equals(manager.getProtectedZone())) {
			return FactionRel.PROTECTED_ZONE;
		}
		return FactionRel.NEUTRAL;

	}

}
