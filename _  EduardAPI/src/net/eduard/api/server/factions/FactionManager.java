package net.eduard.api.server.factions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.eduard.api.game.Rank;
import net.eduard.api.manager.RankManager;
import net.eduard.api.setup.Mine;
import net.eduard.api.setup.StorageAPI.Reference;
import net.eduard.api.setup.StorageAPI.Storable;

public class FactionManager implements Storable {

	private Map<String, Faction> factions = new HashMap<>();
	private Map<UUID, FactionPlayer> members = new HashMap<>();
	private RankManager ranks = new RankManager();
	@Reference
	private Faction warZone;
	@Reference
	private Faction protectedZone;
	@Reference
	private Faction freeZone;
	public FactionManager() {
		warZone = new Faction("Zona de Guerra", "§4Zona de Guerra");
		protectedZone = new Faction("Zona Protegida", "§6Zona Protegida!");
		freeZone = new Faction("Zona Livre", "§2Zona Livre");
		factions.put("zonadeguerra", warZone);
		factions.put("zonaprotegida", protectedZone);
		factions.put("zonalivre", freeZone);
		Rank leader = new Rank("lider", 1);
		leader.setPrefix("**");
		ranks.getRanks().put("leader", leader);
		leader.setPrefix("member");
		Rank member = new Rank("membro", 2);
		member.setPrefix("+");
		member.setNextRank("leader");
		ranks.getRanks().put("member", member);
		ranks.setFirst("member");
		ranks.setLast("leader");

	}

	public Faction getFaction(Player player) {
		return getMember(player).getFaction();
	}

	public Map<String, Faction> getFactions() {
		return factions;
	}
	public FactionPlayer getMember(Player player) {
		FactionPlayer member = members.get(player.getUniqueId());
		if (member == null) {
			member = new FactionPlayer(player);
			member.setRank(ranks.getFirstRank());
			members.put(player.getUniqueId(), member);
		}
		return member;

	}
	public FactionPlayer getPlayer(Player player) {
		return members.get(player.getUniqueId());
	}
	public Map<UUID, FactionPlayer> getMembers() {
		return members;
	}
	public boolean hasFaction(Player player) {
		return getMember(player).getFaction() != null;
	}
	public Faction getFaction(String name) {
		return factions.get(name.toLowerCase());
	}
	public boolean hasFaction(String name) {
		return factions.containsKey(name.toLowerCase());
	}
	public Faction getFaction(String name, String prefix) {
		Faction fac = getFaction(name);
		if (fac == null) {
			for (Faction faction : factions.values()) {
				if (faction.getPrefix().equalsIgnoreCase(prefix)) {
					return faction;
				}
			}
		}
		return fac;
	}
	public boolean hasFaction(String name, String prefix) {
		return getFaction(name, prefix) != null;
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
	public void factionCreate(FactionPlayer player,Faction faction) {
		faction.setLeader(player);
		player.setRank(getRanks().getLastRank());
		factionJoin(player, faction);
		
		Mine.broadcast(faction.getMembers().toString());
	}
	public FactionClaim getClaim(Location location) {
		return getClaim(location.getChunk());
	}
	public boolean isClaimed(Location location) {
		return getClaim(location).getFaction() != null;
	}
	public void factionJoin(FactionPlayer member, Faction faction) {
		faction.getMembers().add(member);
		member.setFaction(faction);
	}
	public void factionLeave(Player player) {
		FactionPlayer member = getMember(player);
		Faction fac = member.getFaction();
		member.getFaction().getMembers().remove(member);
		member.setFaction(null);
		member.setRank(ranks.getFirstRank());
		if (fac.getLeader().equals(member)) {
			deleteFaction(fac);
		}
	}
	
	public void factionClaim(Player player) {
		Faction fac = getFaction(player);
		FactionClaim claim = new FactionClaim(player.getLocation());
		claim.setFaction(fac);
		fac.getClaims().add(claim);
	}

	public Faction createFaction(String name, String tag) {
		Faction faction = new Faction(name, tag);
		factions.put(name.toLowerCase(), faction);
		return faction;
	}
	public void deleteFaction(Faction faction) {

		for (FactionPlayer member : faction.getMembers()) {
			member.setFaction(null);
			member.setRank(ranks.getFirstRank());
		}
		for (Faction fac : faction.getAllies()) {
			fac.getAllies().remove(faction);
		}
		for (Faction fac : faction.getRivals()) {
			fac.getRivals().remove(faction);
		}
		factions.remove(faction.getName().toLowerCase());
	}

	public void setFactions(Map<String, Faction> factions) {
		this.factions = factions;
	}

	public void setMembers(Map<UUID, FactionPlayer> members) {
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

	public RankManager getRanks() {
		return ranks;
	}

	public void setRanks(RankManager ranks) {
		this.ranks = ranks;
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

}
