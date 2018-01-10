package net.eduard.api.server.factions;

import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.Location;

import net.eduard.api.setup.StorageAPI.Reference;
import net.eduard.api.setup.StorageAPI.Storable;

public class FactionClaim implements Storable{

	@Reference
	private Faction faction;
	
	private boolean onAttack;
	private Chunk chunk;
	
	public FactionClaim(Chunk chunk) {
		setChunk(chunk);
	}
	public FactionClaim(Location location) {
		this(location.getChunk());
	}
	public boolean isSafeZone() {
		return faction.isSafeZone();
	}
	public boolean isFreeZone() {
		return !isDomined()||faction.isFreeZone();
	}
	public boolean isWarZone() {
		return faction.isWarZone();
	}
	public boolean isDomined() {
		return faction != null;
	}
	public boolean claimed() {
		return faction != null;
	}
		
	public Faction getFaction() {
		return faction;
	}
	public void setFaction(Faction faction) {
		this.faction = faction;
	}
	public boolean isOnAttack() {
		return onAttack;
	}
	public void setOnAttack(boolean onAttack) {
		this.onAttack = onAttack;
	}
	public Chunk getChunk() {
		return chunk;
	}
	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
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
