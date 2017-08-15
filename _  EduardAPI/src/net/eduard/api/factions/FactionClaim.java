package net.eduard.api.factions;

import java.io.Serializable;

import org.bukkit.Chunk;
import org.bukkit.Location;

import net.eduard.api.config.serial.ChunkSerial;

public class FactionClaim implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Faction faction;
	
	private boolean onAttack;

	private ChunkSerial chunk;

	public FactionClaim(Faction faction, ChunkSerial chunk) {
		super();
		this.faction = faction;
		this.chunk = chunk;
	}

	public FactionClaim(Location location) {
		setChunk(new ChunkSerial(location.getChunk()));
	}

	public FactionClaim(Chunk chunk) {
		setChunk(new ChunkSerial(chunk));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FactionClaim other = (FactionClaim) obj;
		if (chunk == null) {
			if (other.chunk != null)
				return false;
		} else if (!chunk.equals(other.chunk))
			return false;
		return true;
	}

	public ChunkSerial getChunk() {
		return chunk;
	}

	public Faction getFaction() {
		return faction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chunk == null) ? 0 : chunk.hashCode());
		return result;
	}

	public void setChunk(ChunkSerial chunk) {
		this.chunk = chunk;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	@Override
	public String toString() {
		return "FactionClaim [chunk=" + chunk + "]";
	}


	public boolean isOnAttack() {
		return onAttack;
	}

	public void setOnAttack(boolean onAttack) {
		this.onAttack = onAttack;
	}
	
	
	

}
