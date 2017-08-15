package net.eduard.api.manager;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;

public class Arena implements Cloneable {

	private List<BlockState> blocks = new ArrayList<>();
	private Location highPosition;
	private Location secondPosition;
	private Location playerPosition;
	private Location firstPosition;
	private Location lowPosition;
	public Arena() {

	}
	public Arena(Location firstPosition, Location secondPosition) {
		this(firstPosition, secondPosition, null);
	}

	public Arena(Location firstPosition, Location secondPosition,
			Location playerPosition) {
		super();
		this.firstPosition = firstPosition;
		this.secondPosition = secondPosition;
		this.playerPosition = playerPosition;
		setupHighLow();
	}
	public Arena setupHighLow() {
		int x1 = (int) Math.max(firstPosition.getX(), secondPosition.getX());
		int y1 = (int) Math.max(firstPosition.getY(), secondPosition.getY());
		int z1 = (int) Math.max(firstPosition.getZ(), secondPosition.getZ());
		highPosition = new Location(playerPosition.getWorld(), x1, y1, z1);
		int x2 = (int) Math.min(firstPosition.getX(), secondPosition.getX());
		int y2 = (int) Math.min(firstPosition.getY(), secondPosition.getY());
		int z2 = (int) Math.min(firstPosition.getZ(), secondPosition.getZ());
		lowPosition = new Location(playerPosition.getWorld(), x2, y2, z2);
		return this;
	}
	public Arena copy() {
		return copy(true);
	}
	public Arena copy(boolean copyAir) {
		blocks.clear();
		setupHighLow();
		int x1 = lowPosition.getBlockX();
		int y1 = lowPosition.getBlockY();
		int z1 = lowPosition.getBlockZ();
		int x2 = highPosition.getBlockX();
		int y2 = highPosition.getBlockY();
		int z2 = highPosition.getBlockZ();
		for (int x = x1; x < x2; x++) {
			for (int y = y1; y < y2; y++) {
				for (int z = z1; z < z2; z++) {
					BlockState block = new Location(playerPosition.getWorld(),
							x, y, z).getBlock().getState();
					if (!copyAir && block.getType() == Material.AIR)
						continue;
					blocks.add(block);
				}
			}
		}
		return this;
	}
	@SuppressWarnings("deprecation")
	public Arena move(Location playerPosition) {
		World world = playerPosition.getWorld();
		int difX = this.playerPosition.getBlockX() - playerPosition.getBlockX();
		int difY = this.playerPosition.getBlockY() - playerPosition.getBlockY();
		int difZ = this.playerPosition.getBlockZ() - playerPosition.getBlockZ();
		List<BlockState> blocks = new ArrayList<>();
		for (BlockState block : this.blocks) {
			int x = block.getX() - difX;
			int y = block.getY() - difY;
			int z = block.getZ() - difZ;
			BlockState newBlock = world.getBlockAt(x, y, z).getState();
			newBlock.setType(block.getType());
			newBlock.setRawData(block.getRawData());
			blocks.add(newBlock);
		}
		this.blocks = blocks;
		this.highPosition.setX(highPosition.getX() - difX);
		this.highPosition.setY(highPosition.getY() - difY);
		this.highPosition.setZ(highPosition.getZ() - difZ);
		this.lowPosition.setX(lowPosition.getX() - difX);
		this.lowPosition.setY(lowPosition.getY() - difY);
		this.lowPosition.setZ(lowPosition.getZ() - difZ);
		this.firstPosition.setX(firstPosition.getX() - difX);
		this.firstPosition.setY(firstPosition.getY() - difY);
		this.firstPosition.setZ(firstPosition.getZ() - difZ);
		this.secondPosition.setX(secondPosition.getX() - difX);
		this.secondPosition.setY(secondPosition.getY() - difY);
		this.secondPosition.setZ(secondPosition.getZ() - difZ);
		this.playerPosition = playerPosition;
		return this;
	}
	public Arena paste(boolean applyPhisics) {
		for (BlockState block : blocks) {
			block.update(true,applyPhisics);
		}
		return this;
	}
	public Arena paste(Location playerPosition) {
		return clone().move(playerPosition).paste(false);
	}

	@Override
	public Arena clone() {
		try {
			return (Arena) super.clone();
		} catch (Exception e) {
		}
		return null;
	}
	private String getLocation(Location location) {
		return location.getBlockX() + "," + location.getBlockY() + ","
				+ location.getBlockZ();
	}

	public Arena save(File file) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("world=" + playerPosition.getWorld().getName());
			builder.append("\nplayer=" + getLocation(playerPosition));
			builder.append("\nhigh=" + getLocation(highPosition));
			builder.append("\nlow=" + getLocation(lowPosition));
			builder.append("\npos1=" + getLocation(firstPosition));
			builder.append("\npos2=" + getLocation(secondPosition));
			builder.append("\nblocks:");
			for (BlockState block : blocks) {
				builder.append("\n- " + getBlock(block));
			}
			Files.write(file.toPath(), builder.toString().getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	private Location getLocation(World world, String[] text) {
		Integer x = Integer.valueOf(text[0]);
		Integer y = Integer.valueOf(text[1]);
		Integer z = Integer.valueOf(text[2]);
		return new Location(world, x, y, z);
	}
	@SuppressWarnings("deprecation")
	public Arena reload(File file) {
		try {
			List<String> lines = Files.readAllLines(file.toPath());
			World world = Bukkit.getWorlds().get(0);
			blocks.clear();
			for (String line : lines) {
				if (line.contains("world=")) {
					world = Bukkit.getWorld(line.split("=")[1]);
				}
				if (line.contains("player=")) {
					String[] text = line.split("=")[1].split(",");
					playerPosition = getLocation(world, text);
				}
				if (line.contains("high=")) {
					String[] text = line.split("=")[1].split(",");
					highPosition = getLocation(world, text);
				}
				if (line.contains("low=")) {
					String[] text = line.split("=")[1].split(",");
					lowPosition = getLocation(world, text);
				}
				if (line.contains("pos1=")) {
					String[] text = line.split("=")[1].split(",");
					firstPosition = getLocation(world, text);
				}
				if (line.contains("pos2=")) {
					String[] text = line.split("=")[1].split(",");
					secondPosition = getLocation(world, text);
				}
				if (line.startsWith("- ")) {
					String[] text = line.split("- ")[1].split(";");
					String[] info = text[0].split(",");
					Integer id = Integer.valueOf(info[0]);
					int data = Integer.valueOf(info[1]);
					Location loc = getLocation(world, text[1].split(","));
					BlockState state = loc.getBlock().getState();
					state.setTypeId(id);
					state.setRawData((byte) data);
					blocks.add(state);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	@SuppressWarnings("deprecation")
	private String getBlock(BlockState block) {
		return block.getTypeId() + "," + block.getRawData() + ";"
				+ getLocation(block.getLocation());
	}

	public static Arena load(File file) {
		return new Arena().reload(file);
	}

	public List<BlockState> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<BlockState> blocks) {
		this.blocks = blocks;
	}

	public Location getFirstPosition() {
		return secondPosition;
	}

	public void setFirstPosition(Location firstPosition) {
		this.secondPosition = firstPosition;
	}

	public Location getSecondPosition() {
		return secondPosition;
	}

	public void setSecondPosition(Location secondPosition) {
		this.secondPosition = secondPosition;
	}

	public Location getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(Location playerPosition) {
		this.playerPosition = playerPosition;
	}

	public Location getHighPosition() {
		return secondPosition;
	}

	public void setHighPosition(Location highPosition) {
		this.secondPosition = highPosition;
	}

	public Location getLowPosition() {
		return secondPosition;
	}

	public void setLowPosition(Location lowPosition) {
		this.secondPosition = lowPosition;
	}

}
