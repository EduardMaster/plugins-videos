package net.eduard.api.factions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FactionManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Faction> factions = new HashMap<>();
	private Map<UUID, FactionMember> members = new HashMap<>();

	private Faction warZone;
	private Faction protectedZone;
	private Faction freeZone;
	public FactionManager() {
		warZone = new Faction("zonadeguerra", "§4Zona de Guerra");
		protectedZone = new Faction("zonaprotegida", "§6Zona Protegida!");
		freeZone = new Faction("zonalivre", "§2Zona Livre");
	}

	public void claim(Player player) {
		Faction fac = getFaction(player);
		FactionClaim claim = new FactionClaim(player.getLocation());
		claim.setFaction(fac);
		fac.getClaims().add(claim);
	}

	public Faction create(String name, String tag) {
		Faction fac = new Faction(name, tag);
		factions.put(name.toLowerCase(), fac);
		return fac;
	}

	public Faction getFaction(Player player) {
		return getMember(player).getFaction();
	}

	public Map<String, Faction> getFactions() {
		return factions;
	}
	public FactionMember getMember(Player player) {
		if (!members.containsKey(player.getUniqueId())) {
			FactionMember member = new FactionMember(player);
			members.put(player.getUniqueId(), member);
		}

		return members.get(player.getUniqueId());

	}
	public Map<UUID, FactionMember> getMembers() {
		return members;
	}
	public boolean hasFaction(Player player) {
		return getMember(player).getFaction() != null;
	}
	public boolean hasFaction(String name) {
		return factions.containsKey(name.toLowerCase());
	}

	public FactionClaim getClaim(Chunk chunk) {
		FactionClaim claim = new FactionClaim(chunk);
		for (Faction fac : factions.values()) {
			for (FactionClaim loopClaim : fac.getClaims()) {
				if (loopClaim.equals(claim)) {
					return loopClaim;	
				}
				
			}
		}
		return claim;
	}
	public FactionClaim getClaim(Location location) {
		return getClaim(location.getChunk());
	}
	public boolean isClaimed(Location location) {
		return getClaim(location).getFaction() != null;
	}
	public void join(Player player, Faction faction) {

	}
	public void leave(Player player) {

	}
	public void invite(Player player, Faction faction) {

	}
	public void setFactions(Map<String, Faction> factions) {
		this.factions = factions;
	}

	public void setMembers(Map<UUID, FactionMember> members) {
		this.members = members;
	}

	public Faction getProtectedZone() {
		return protectedZone;
	}

	public void setProtectedZone(Faction protectedZone) {
		this.protectedZone = protectedZone;
	}

	public Faction getWarZone() {
		return warZone;
	}

	public void setWarZone(Faction warZone) {
		this.warZone = warZone;
	}

	public Faction getFreeZone() {
		return freeZone;
	}

	public void setFreeZone(Faction freeZone) {
		this.freeZone = freeZone;
	}

}
