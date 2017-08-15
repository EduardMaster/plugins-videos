package net.eduard.api.config.serial;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

public class ChunkSerial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String world;
	private int x, z;
	public Chunk getChunk() {
		return Bukkit.getWorld(world).getChunkAt(x, z);
	}
	public void setChunk(Chunk chunk) {
		world = chunk.getWorld().getName();
		x = chunk.getX();
		z = chunk.getZ();
	}

	@Override
	public String toString() {
		return "ChunkSerial [world=" + world + ", x=" + x + ", z=" + z + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		result = prime * result + x;
		result = prime * result + z;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChunkSerial other = (ChunkSerial) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		if (x != other.x)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
	public String getWorld() {
		return world;
	}
	public void setWorld(String world) {
		this.world = world;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getZ() {
		return z;
	}
	public void setZ(int z) {
		this.z = z;
	}
	public ChunkSerial(Chunk chunk) {
		setChunk(chunk);
	}
	public ChunkSerial(String world, int x, int z) {
		super();
		this.world = world;
		this.x = x;
		this.z = z;
	}

}
