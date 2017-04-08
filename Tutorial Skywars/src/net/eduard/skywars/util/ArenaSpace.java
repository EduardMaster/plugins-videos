package net.eduard.skywars.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ArenaSpace implements Cloneable {

	private Location pos1, pos2;
	

	public Location getPos1() {
		return pos1;
	}
	public void setPos1(Location pos1) {
		this.pos1 = pos1;
	}
	public Location getPos2() {
		return pos2;
	}
	public void setPos2(Location pos2) {
		this.pos2 = pos2;
	}
	public void setBlocks(List<SimpleBlock> blocks) {
		this.blocks = blocks;
	}

	private List<SimpleBlock> blocks = new ArrayList<>();

	public List<Location> getBlocks() {
		return getLocations(pos1, pos2);
	}
	public void copy(boolean copyAir) {
		blocks.clear();
		for (Location loc : getLocations(pos1, pos2)) {
			Block block = loc.getBlock();
			if (block.getType() == Material.AIR && !copyAir)
				continue;
			blocks.add(new SimpleBlock(block));
		}
	}
	public ArenaSpace clone() {
		try {
			return (ArenaSpace) super.clone();
		} catch (Exception e) {
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	public void paste(World world) {
		for (SimpleBlock block : blocks) {
			Location loc = block.getLoc().clone();
			loc.setWorld(world);
			loc.getBlock().setTypeIdAndData(block.getId(),
					(byte) block.getData(), false);
		}
	}
	public static Location getLowLocation(Location location1,
			Location location2) {
		double x = Math.min(location1.getX(), location2.getX());
		double y = Math.min(location1.getY(), location2.getY());
		double z = Math.min(location1.getZ(), location2.getZ());
		return new Location(location1.getWorld(), x, y, z);
	}
	public static Location getHighLocation(Location loc1, Location loc2) {

		double x = Math.max(loc1.getX(), loc2.getX());
		double y = Math.max(loc1.getY(), loc2.getY());
		double z = Math.max(loc1.getZ(), loc2.getZ());
		return new Location(loc1.getWorld(), x, y, z);
	}
	public static List<Location> getLocations(Location location1,
			Location location2) {

		Location min = getLowLocation(location1, location2);
		Location max = getHighLocation(location1, location2);
		List<Location> locations = new ArrayList<>();
		for (double x = min.getX(); x <= max.getX(); x++) {
			for (double y = min.getY(); y <= max.getY(); y++) {
				for (double z = min.getZ(); z <= max.getZ(); z++) {
					Location loc = new Location(min.getWorld(), x, y, z);
					locations.add(loc);

				}
			}
		}
		return locations;

	}
	public class SimpleBlock {

		private Location loc;
		private int id, data;

		@SuppressWarnings("deprecation")
		public SimpleBlock(Block block) {
			loc = block.getLocation();
			id = block.getTypeId();
			data = block.getData();
		}
		public Location getLoc() {
			return loc;
		}
		public void setLoc(Location loc) {
			this.loc = loc;
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

	}
	public static World newEmptyWorld(String worldName) {
		World world = Bukkit.createWorld(
				new WorldCreator(worldName).generateStructures(false)
						.generator(new EmptyWorldGenerator()));
		world.getBlockAt(100, 100, 100).setType(Material.GLASS);
		world.setSpawnLocation(100, 101, 100);
		return world;
	}
	public static void deleteFolder(File file) {
		if (file.exists()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteFolder(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
	}

	public static void deleteWorld(String name) {
		World world = Bukkit.getWorld(name);
		if (world != null) {
			for (Player p : world.getPlayers()) {
				p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation()
						.setDirection(p.getLocation().getDirection()));
			}
			Bukkit.unloadWorld(world, false);
			
		}
		try {
			deleteFolder(world.getWorldFolder());
		} catch (Exception e) {
		}
	}
}
