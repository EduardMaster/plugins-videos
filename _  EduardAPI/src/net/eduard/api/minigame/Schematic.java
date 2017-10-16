package net.eduard.api.minigame;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import net.eduard.api.setup.StorageAPI.Copyable;
import net.eduard.api.setup.StorageAPI.Storable;
/**
 * 
 * @author Eduard-PC
 *
 */
public class Schematic implements Storable ,Copyable{
	private World world;
	private Location firstLocation;
	private Location secondLocation;
	private Location highestLocation;
	private Location lowestLocation;
	private Location location;
	private transient List<Location> locations = new ArrayList<>();
	private transient byte[] blocks;
	private transient byte[] datas;
	private transient short length;
	private transient short height;
	private transient short width;
	public Schematic(Location location) {
		this.location = location;
	}
	
	public void save(File file) {
		
	}
	public Schematic(File file) {
		load(file);
		
	}
	public void load(File file) {
		
	}
	public Schematic() {
	}

	public Schematic(Location location, Location firstLocation,
			Location secondLocation) {
		select(location, firstLocation, secondLocation);
	}
	public Schematic select(Location firstLocation, Location secondLocation) {
		return select(location, firstLocation, secondLocation);
	}
	public Schematic select() {
		return select(location, firstLocation, secondLocation);
	}
	public Schematic setupHighAndLow(Location firstLocation,
			Location secondLocation) {
		this.world = firstLocation.getWorld();
		int xMin = Math.min(firstLocation.getBlockX(),
				secondLocation.getBlockX());
		int xMax = Math.max(firstLocation.getBlockX(),
				secondLocation.getBlockX());
		int yMin = Math.min(firstLocation.getBlockY(),
				secondLocation.getBlockY());
		int yMax = Math.max(firstLocation.getBlockY(),
				secondLocation.getBlockY());
		int zMin = Math.min(firstLocation.getBlockZ(),
				secondLocation.getBlockZ());
		int zMax = Math.max(firstLocation.getBlockZ(),
				secondLocation.getBlockZ());
		this.lowestLocation = new Location(world, xMin, yMin, zMin);
		this.highestLocation = new Location(world, xMax, yMax, zMax);
		
		return this;

	}
	public Schematic setupSize() {
		width = (short) (highestLocation.getBlockX()
				- lowestLocation.getBlockX());
		height = (short) (highestLocation.getBlockY()
				- lowestLocation.getBlockY());
		length = (short) (highestLocation.getBlockZ()
				- lowestLocation.getBlockZ());
		blocks = new byte[width * height * length];
		datas = new byte[width * height * length];
		return this;
	}
	@SuppressWarnings("deprecation")
	public Schematic select(Location location, Location firstLocation,
			Location secondLocation) {
		this.location = location;
		this.firstLocation = firstLocation;
		this.secondLocation = secondLocation;
		setupHighAndLow(firstLocation, secondLocation);
		setupSize();

		for (int x = highestLocation.getBlockX(); x <= lowestLocation
				.getBlockX(); x++) {
			for (int y = highestLocation.getBlockY(); y <= lowestLocation
					.getBlockY(); y++) {
				for (int z = highestLocation.getBlockZ(); z <= lowestLocation
						.getBlockZ(); z++) {
					int index = y * width * length + z * width + x;
					Location loc = new Location(world, x, y, z);
					locations.add(loc);
					blocks[index] = (byte) loc.getBlock().getTypeId();
					datas[index] = (byte) loc.getBlock().getData();
					// blocks =
				}
			}
		}
		return this;
	}

	public Schematic paste() {
		return paste(location);
	}

	@SuppressWarnings("deprecation")
	public Schematic paste(Location location) {
		// int x = location.getBlockX();
		// int y = location.getBlockY();
		// int z = location.getBlockZ();
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				for (int z = 0; z < length; z++) {
					int index = y * width * length + z * width + x;
					Block block = new Location(world, x + location.getX(),
							y + location.getY(), z + location.getZ())
									.getBlock();
					block.setTypeIdAndData(blocks[index], datas[index], true);
				}
		return this;
	}
	public Location getFirstLocation() {
		return firstLocation;
	}
	public void setFirstLocation(Location firstLocation) {
		this.firstLocation = firstLocation;
	}
	public Location getSecondLocation() {
		return secondLocation;
	}
	public void setSecondLocation(Location secondLocation) {
		this.secondLocation = secondLocation;
	}
	public Location getHighestLocation() {
		return highestLocation;
	}
	public void setHighestLocation(Location highestLocation) {
		this.highestLocation = highestLocation;
	}
	public Location getLowestLocation() {
		return lowestLocation;
	}
	public void setLowestLocation(Location lowestLocation) {
		this.lowestLocation = lowestLocation;
	}
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public byte[] getBlocks() {
		return blocks;
	}
	public void setBlocks(byte[] blocks) {
		this.blocks = blocks;
	}
	public byte[] getDatas() {
		return datas;
	}
	public void setDatas(byte[] datas) {
		this.datas = datas;
	}
	public short getLenght() {
		return length;
	}
	public void setLenght(short lenght) {
		this.length = lenght;
	}

	public short getWidth() {
		return width;
	}
	public void setWidth(short width) {
		this.width = width;
	}

	public Schematic world(World world) {
		this.world = world;
		this.location.setWorld(world);
		this.firstLocation.setWorld(world);
		this.secondLocation.setWorld(world);
		this.lowestLocation.setWorld(world);
		this.highestLocation.setWorld(world);
		return this;
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