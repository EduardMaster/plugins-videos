package net.eduard.api.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import net.eduard.api.setup.StorageAPI.Copyable;
import net.eduard.api.setup.StorageAPI.Storable;
import net.eduard.api.setup.StorageAPI.Variable;

/**
 * Simples Esquema de Blocos denominado Arena
 * 
 * @author Eduard
 *
 */
public class Arena implements Storable, Copyable {
	private World world;
	private Location highPosition;
	private Location playerPosition;
	private Location lowPosition;
	private List<ArenaBlock> blocks = new ArrayList<>();

	public Arena() {

	}
	public Arena(Location location) {
		this.playerPosition = location;
	}
	public Arena(Location firstPosition, Location secondPosition) {
		this(firstPosition, secondPosition, null);
	}

	public Arena(Location firstPosition, Location secondPosition,
			Location playerPosition) {
		super();
		base(firstPosition, secondPosition, playerPosition);
	}

	public Arena base(Location firstPosition, Location secondPosition,
			Location playerPosition) {
		this.playerPosition = playerPosition;
		this.world = playerPosition.getWorld();
		setupHighLow(firstPosition, secondPosition);
		return this;
	}
	public Arena copy() {
		return copy(this);
	}
	public Arena clear() {
		setBlocks(Material.AIR);
		paste(true);
		return this;
	}
	public Arena setBlocks(Material type) {
		setBlocks(type, 0);
		return this;
	}
	public Arena setBlocks(Material type, int data) {
		for (ArenaBlock block : getBlocks()) {
			@SuppressWarnings("deprecation")
			int id = type.getId();
			block.setId(id);
			block.setData(data);
		}
		return this;
	}
	public Arena setupHighLow(Location loc1, Location loc2) {
		int x1 = (int) Math.max(loc1.getX(), loc2.getX());
		int y1 = (int) Math.max(loc1.getY(), loc2.getY());
		int z1 = (int) Math.max(loc1.getZ(), loc2.getZ());
		highPosition = new Location(world, x1, y1, z1);
		int x2 = (int) Math.min(loc1.getX(), loc2.getX());
		int y2 = (int) Math.min(loc1.getY(), loc2.getY());
		int z2 = (int) Math.min(loc1.getZ(), loc2.getZ());
		lowPosition = new Location(world, x2, y2, z2);
		return this;
	}

	public void setLow(Location low) {
		this.lowPosition = low;
	}
	public Arena copyFrom(World world) {
		return world(world).copy(false);
	}
	public Arena copy(boolean copyAir) {
		blocks.clear();
		int x1 = lowPosition.getBlockX();
		int y1 = lowPosition.getBlockY();
		int z1 = lowPosition.getBlockZ();
		int x2 = highPosition.getBlockX();
		int y2 = highPosition.getBlockY();
		int z2 = highPosition.getBlockZ();
		for (int x = x1; x <= x2; x++) {
			for (int y = y1; y <= y2; y++) {
				for (int z = z1; z <= z2; z++) {
					Block block = new Location(world, x, y, z).getBlock();
					if (!copyAir && block.getType() == Material.AIR)
						continue;
					blocks.add(new ArenaBlock(block));
				}
			}
		}
		return this;
	}
	public Arena move(Location playerPosition) {
		world(playerPosition.getWorld());
		int difX = this.playerPosition.getBlockX() - playerPosition.getBlockX();
		int difY = this.playerPosition.getBlockY() - playerPosition.getBlockY();
		int difZ = this.playerPosition.getBlockZ() - playerPosition.getBlockZ();
		for (ArenaBlock block : this.blocks) {
			int x = block.getX() - difX;
			int y = block.getY() - difY;
			int z = block.getZ() - difZ;
			block.setY(y);
			block.setZ(z);
			block.setX(x);
		}
		this.highPosition.setX(highPosition.getX() - difX);
		this.highPosition.setY(highPosition.getY() - difY);
		this.highPosition.setZ(highPosition.getZ() - difZ);
		this.lowPosition.setX(lowPosition.getX() - difX);
		this.lowPosition.setY(lowPosition.getY() - difY);
		this.lowPosition.setZ(lowPosition.getZ() - difZ);
		this.playerPosition = playerPosition;
		return this;
	}
	public Arena paste(boolean applyPhisics) {
		for (ArenaBlock block : blocks) {
			block.update(world, applyPhisics);
		}
		return this;
	}
	public Arena copyPaste(Location playerPosition) {
		return copy().move(playerPosition).paste(false);
	}
	public Arena copyPaste(World world) {
		return copy().world(world).paste(false);
	}
	public Arena world(World world) {
		this.world = world;
		this.highPosition.setWorld(world);
		this.lowPosition.setWorld(world);
		this.playerPosition.setWorld(world);
		return this;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public World getWorld() {
		return world;
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
	public List<ArenaBlock> getBlocks() {
		return blocks;
	}
	public void setBlocks(List<ArenaBlock> blocks) {
		this.blocks = blocks;
	}
	public Location getHighPosition() {
		return highPosition;
	}
	public void setHighPosition(Location highPosition) {
		this.highPosition = highPosition;
	}
	public Location getPlayerPosition() {
		return playerPosition;
	}
	public void setPlayerPosition(Location playerPosition) {
		this.playerPosition = playerPosition;
	}
	public Location getLowPosition() {
		return lowPosition;
	}
	public void setLowPosition(Location lowPosition) {
		this.lowPosition = lowPosition;
	}

	public static class ArenaBlock implements Variable {

		private int x, y, z;
		private int id, data;
		public ArenaBlock(Block block) {
			setBlock(block);
		}
		public ArenaBlock() {
			// TODO Auto-generated constructor stub
		}
		public Block getBlock(World world) {
			return world.getBlockAt(x, y, z);
		}
		@SuppressWarnings("deprecation")
		public void update(World world, boolean applyPhisics) {
			getBlock(world).setTypeIdAndData(id, (byte) data, applyPhisics);
		}
		public void setBlock(Location location) {
			setBlock(location.getBlock());
		}
		@SuppressWarnings("deprecation")
		public void setBlock(Block block) {
			this.x = block.getX();
			this.y = block.getY();
			this.z = block.getZ();
			this.id = block.getTypeId();
			this.data = block.getData();
		}
		public Location getLocation(World world) {
			return new Location(world, x, y, z);
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
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}

		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		@Override
		public Object get(Object object) {
			if (object instanceof String) {
				String string = (String) object;
				String[] spliter = string.split(";");
				ArenaBlock block = new ArenaBlock();
				block.setId(Mine.toInt(spliter[0]));
				block.setData(Mine.toInt(spliter[1]));
				block.setX(Mine.toInt(spliter[2]));
				block.setY(Mine.toInt(spliter[3]));
				block.setZ(Mine.toInt(spliter[4]));
				return block;

			}
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Object save(Object object) {
			// TODO Auto-generated method stub
			if (object instanceof ArenaBlock) {
				ArenaBlock arenaBlock = (ArenaBlock) object;
				return arenaBlock.getId() + ";" + arenaBlock.getData() + ";"
						+ arenaBlock.getX() + ";" + arenaBlock.getY() + ";"
						+ arenaBlock.getZ();
			}
			return null;
		}

	}
}