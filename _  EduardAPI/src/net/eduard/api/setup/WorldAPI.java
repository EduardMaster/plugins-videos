package net.eduard.api.setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import net.eduard.api.setup.StorageAPI.Copyable;
import net.eduard.api.setup.StorageAPI.Storable;

/**
 * API de controle e manipulação de Mundos e Localizações e Cuboids (Uma expecie de Bloco retangular) 
 * @author Eduard
 *
 */
public final class WorldAPI {
	
	/**
	 * Efeito a fazer na localização
	 * @author Eduard
	 *
	 */
	public static interface LocationEffect {

		boolean effect(Location location);
	}
	/**
	 * Ponto de direção usado para fazer um RADAR
	 * @author Eduard
	 *
	 */
	public static enum Point
	{
	  N('N'), NE('/'), E('O'), SE('\\'), S('S'), SW('/'), W('L'), NW('\\');

	  public final char asciiChar;

	  private Point(char asciiChar) {
	    this.asciiChar = asciiChar;
	  }

	  @Override
	public String toString()
	  {
	    return String.valueOf(this.asciiChar);
	  }

	  public String toString(boolean isActive, ChatColor colorActive, String colorDefault) {
	    return (isActive ? colorActive : colorDefault) + String.valueOf(this.asciiChar);
	  }
	}
	public static boolean equals(Location location1, Location location2) {

		return getBlockLocation1(location1)
				.equals(getBlockLocation1(location2));
	}

	public static boolean equals2(Location location1, Location location2) {
		return location1.getBlock().getLocation()
				.equals(location2.getBlock().getLocation());
	}
	public static List<Location> getLocations(Location location1,
			Location location2) {
		return getLocations(location1, location2, new LocationEffect() {

			@Override
			public boolean effect(Location location) {
				return true;
			}
		});
	}
	public static Location getHighLocation(Location loc, double high,
			double size) {

		loc.add(size, high, size);
		return loc;
	}
	public static void copyWorldFolder(File source, File target) {
		try {
			List<String> ignore = new ArrayList<String>(
					Arrays.asList("uid.dat", "session.dat"));
			if (!ignore.contains(source.getName())) {
				if (source.isDirectory()) {
					if (!target.exists())
						target.mkdirs();
					String files[] = source.list();
					for (String file : files) {
						File srcFile = new File(source, file);
						File destFile = new File(target, file);
						copyWorldFolder(srcFile, destFile);
					}
				} else {
					InputStream in = new FileInputStream(source);
					OutputStream out = new FileOutputStream(target);
					byte[] buffer = new byte[1024];
					int length;
					while ((length = in.read(buffer)) > 0)
						out.write(buffer, 0, length);
					in.close();
					out.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public static void deleteWorld(String name) {
		World world = Bukkit.getWorld(name);
		if (world != null) {
			for (Player p : world.getPlayers()) {
				try {
					p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation()
							.setDirection(p.getLocation().getDirection()));

				} catch (Exception e) {
					p.kickPlayer("§cRestarting Server!");
				}
			}
		}
		Bukkit.unloadWorld(name, true);
		deleteFolder(new File(Bukkit.getWorldContainer(), name.toLowerCase()));
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
	public static void copyWorld(String worldName, String name) {
		World world = Bukkit.getWorld(worldName);
		world.save();
		copyWorldFolder(world.getWorldFolder(), getWorldFolder(name));
		WorldCreator copy = new WorldCreator(name);
		copy.copy(world);
		copy.createWorld();
	}
	public static World loadWorld(String name) {
		return new WorldCreator(name).createWorld();
	}
	public static File getWorldFolder(String name) {
		return new File(Bukkit.getWorldContainer(), name.toLowerCase());
	}

	public static Location getHighLocation(Location loc1, Location loc2) {

		double x = Math.max(loc1.getX(), loc2.getX());
		double y = Math.max(loc1.getY(), loc2.getY());
		double z = Math.max(loc1.getZ(), loc2.getZ());
		return new Location(loc1.getWorld(), x, y, z);
	}
	public static List<Location> getLocations(Location location1,
			Location location2, LocationEffect effect) {

		Location min = getLowLocation(location1, location2);
		Location max = getHighLocation(location1, location2);
		List<Location> locations = new ArrayList<>();
		for (double x = min.getX(); x <= max.getX(); x++) {
			for (double y = min.getY(); y <= max.getY(); y++) {
				for (double z = min.getZ(); z <= max.getZ(); z++) {
					Location loc = new Location(min.getWorld(), x, y, z);
					try {
						boolean r = effect.effect(loc);
						if (r) {
							try {
								locations.add(loc);
							} catch (Exception ex) {
							}
						}
					} catch (Exception ex) {
					}

				}
			}
		}
		return locations;

	}
	public static Location getLowLocation(Location loc, double low,
			double size) {

		loc.subtract(size, low, size);
		return loc;
	}

	public static Location getLowLocation(Location location1,
			Location location2) {
		double x = Math.min(location1.getX(), location2.getX());
		double y = Math.min(location1.getY(), location2.getY());
		double z = Math.min(location1.getZ(), location2.getZ());
		return new Location(location1.getWorld(), x, y, z);
	}
	public static Location getBlockLocation1(Location location) {

		return new Location(location.getWorld(), (int) location.getX(),
				(int) location.getY(), (int) location.getZ());
	}

	public static Location getBlockLocation2(Location location) {

		return location.getBlock().getLocation();
	}

	public static List<Location> getBox(Location playerLocation, double higher,
			double lower, double size, LocationEffect effect) {
		Location high = getHighLocation(playerLocation.clone(), higher, size);
		Location low = getLowLocation(playerLocation.clone(), lower, size);
		return getLocations(low, high, effect);
	}
	public static List<Location> setBox(Location playerLocation, double higher,
			double lower, double size, Material wall, Material up,
			Material down, boolean clearInside) {
		return getBox(playerLocation, higher, lower, size,
				new LocationEffect() {

					@Override
					public boolean effect(Location location) {

						if (location.getBlockY() == playerLocation.getBlockY()
								+ higher) {
							location.getBlock().setType(up);
							return true;
						}
						if (location.getBlockY() == playerLocation.getBlockY()
								- lower) {
							location.getBlock().setType(down);
							return true;
						}

						if (location.getBlockX() == playerLocation.getBlockX()
								+ size
								|| location.getBlockZ() == playerLocation
										.getBlockZ() + size
								|| location.getBlockX() == playerLocation
										.getBlockX() - size
								|| location.getBlockZ() == playerLocation
										.getBlockZ() - size) {
							location.getBlock().setType(wall);
							return true;
						}
						if (clearInside) {
							if (location.getBlock().getType() != Material.AIR)
								location.getBlock().setType(Material.AIR);
						}
						return false;
					}
				});
	}
	public static List<Location> getBox(Location playerLocation, double higher,
			double lower, double size) {
		return getBox(playerLocation, higher, lower, size,
				new LocationEffect() {

					@Override
					public boolean effect(Location location) {
						return true;
					}
				});
	}
	public static List<Location> getBox(Location playerLocation, double xHigh,
			double xLow, double zHigh, double zLow, double yLow, double yHigh) {
		Location low = playerLocation.clone().subtract(xLow, yLow, zLow);
		Location high = playerLocation.clone().add(xHigh, yHigh, zHigh);
		return getLocations(low, high);
	}
	public static Location getRandomPosition(Location location,int xVar,int zVar){
		return getHighPosition(getRandomLocation(location, xVar, 0, zVar));
		
	}
	public static void unloadWorld(String name) {
		try {
			unloadWorld(Bukkit.getWorld(name));
		} catch (Exception ex) {
		}
	}
	public static double distanceX(Location loc1, Location loc2) {
		return loc1.getX() - loc2.getX();
	}

	public static double distanceZ(Location loc1, Location loc2) {
		return loc1.getZ() - loc2.getZ();
	}
	public static void unloadWorld(World world) {
		try {
			Bukkit.getServer().unloadWorld(world, false);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	public static Location getRandomLocation(Location location,int xVar,int yVar,int zVar){
		int x = location.getBlockX();
		int z = location.getBlockZ();
		int y = location.getBlockY();
		int xR = ExtraAPI.getRandomInt(x-xVar, x+xVar);
		int zR = ExtraAPI.getRandomInt(z-zVar, z+zVar);
		int yR = ExtraAPI.getRandomInt(y-yVar, y+zVar);
		return new Location(location.getWorld(),xR,yR,zR);
	}
	public static Location getHighPosition(Location location){
		return location.getWorld().getHighestBlockAt(location).getLocation();
	}
	public static Location getSpawn() {
		return Bukkit.getWorlds().get(0).getSpawnLocation();
	}
	
	public static Point getCompassPointForDirection(double inDegrees)
	  {
	    double degrees = (inDegrees - 180.0D) % 360.0D;
	    if (degrees < 0.0D) {
	      degrees += 360.0D;
	    }

	    if ((0.0D <= degrees) && (degrees < 22.5D))
	      return Point.N;
	    if ((22.5D <= degrees) && (degrees < 67.5D))
	      return Point.NE;
	    if ((67.5D <= degrees) && (degrees < 112.5D))
	      return Point.E;
	    if ((112.5D <= degrees) && (degrees < 157.5D))
	      return Point.SE;
	    if ((157.5D <= degrees) && (degrees < 202.5D))
	      return Point.S;
	    if ((202.5D <= degrees) && (degrees < 247.5D))
	      return Point.SW;
	    if ((247.5D <= degrees) && (degrees < 292.5D))
	      return Point.W;
	    if ((292.5D <= degrees) && (degrees < 337.5D))
	      return Point.NW;
	    if ((337.5D <= degrees) && (degrees < 360.0D)) {
	      return Point.N;
	    }
	    return null;
	  }

	  public static ArrayList<String> getAsciiCompass(Point point, ChatColor colorActive, String colorDefault)
	  {
	    ArrayList<String> ret = new ArrayList<>();

	    String row = "";
	    row = row + Point.NW.toString(Point.NW == point, colorActive, colorDefault);
	    row = row + Point.N.toString(Point.N == point, colorActive, colorDefault);
	    row = row + Point.NE.toString(Point.NE == point, colorActive, colorDefault);
	    ret.add(row);

	    row = "";
	    row = row + Point.W.toString(Point.W == point, colorActive, colorDefault);
	    row = row + colorDefault + "+";
	    row = row + Point.E.toString(Point.E == point, colorActive, colorDefault);
	    ret.add(row);

	    row = "";
	    row = row + Point.SW.toString(Point.SW == point, colorActive, colorDefault);
	    row = row + Point.S.toString(Point.S == point, colorActive, colorDefault);
	    row = row + Point.SE.toString(Point.SE == point, colorActive, colorDefault);
	    ret.add(row);

	    return ret;
	  }

	  public static ArrayList<String> getAsciiCompass(double inDegrees, ChatColor colorActive, String colorDefault) {
	    return getAsciiCompass(getCompassPointForDirection(inDegrees), colorActive, colorDefault);
	  }

	  /**
	   * Gerador de Mundo Vasio
	   * @author Eduard
	   *
	   */
	  public static class EmptyWorldGenerator extends ChunkGenerator {

	  	@Override
	  	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ,
	  			ChunkGenerator.BiomeGrid biomeGrid) {
	  		byte[][] result = new byte[world.getMaxHeight() / 16][];
	  		return result;
	  	}

	  	@Override
	  	public Location getFixedSpawnLocation(World world, Random random) {
	  		return new Location(world, 100, 100, 100);
	  	}

	  	public void setBlock(byte[][] result, int x, int y, int z, byte blockID) {
	  		if (result[(y >> 4)] == null) {
	  			result[(y >> 4)] = new byte[4096];
	  		}
	  		result[(y >> 4)][((y & 0xF) << 8 | z << 4 | x)] = blockID;
	  	}

	  	@SuppressWarnings("deprecation")
	  	public byte getId(Material material) {
	  		return (byte) material.getId();
	  	}

	  	public byte getId(Material material, short data) {
	  		return 0;
	  	}

	  	public void setLayer(byte[][] result, int level, Material material) {
	  		int x, z;
	  		for (x = 0; x < 16; x++) {
	  			for (z = 0; z < 16; z++) {
	  				setBlock(result, x, level, z, getId(material));
	  			}
	  		}
	  	}

	  	public void setCorner(byte[][] result, int level, Material material) {
	  		int x, z;
	  		for (x = 0; x < 16; x++) {
	  			setBlock(result, x, level, 0, getId(material));
	  		}
	  		for (z = 0; z < 16; z++) {
	  			setBlock(result, 0, level, z, getId(material));
	  		}
	  	}


	  	public void setLayer(byte[][] result, int minLevel, int maxLevel, Material material) {
	  		int y;
	  		for (y = minLevel; y <= maxLevel; y++) {
	  			setLayer(result, y, material);
	  		}
	  	}
	  }


	  /**
	   * Gerador de Mundo Plano
	   * @author Eduard
	   *
	   */
	  public static class FlatWorldGenerator extends EmptyWorldGenerator {

	  	@Override
	  	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ,
	  			ChunkGenerator.BiomeGrid biomeGrid) {
	  		byte[][] result = new byte[world.getMaxHeight() / 16][];
	  		setLayer(result, 0, Material.BEDROCK);
	  		setLayer(result, 1, 3, Material.DIRT);
	  		setLayer(result, 4, Material.GRASS);
	  		setCorner(result, 8, Material.DIAMOND_BLOCK);
	  		return result;
	  	}

	  }
	  /**
	   * Simples Esquema de Blocos denominado Arena
	   * @author Eduard
	   *
	   */
	  public static class Arena implements Storable ,Copyable {

	  	private List<ArenaBlock> blocks = new ArrayList<>();
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
	  	public Arena copy() {
	  		return copy(this);
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
	  					Block block = new Location(playerPosition.getWorld(),
	  							x, y, z).getBlock();
	  					if (!copyAir && block.getType() == Material.AIR)
	  						continue;
	  					blocks.add(new ArenaBlock(block));
	  				}
	  			}
	  		}
	  		return this;
	  	}
	  	public Arena move(Location playerPosition) {
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
	  		for (ArenaBlock block : blocks) {
	  			block.update(applyPhisics);
	  		}
	  		return this;
	  	}
	  	public Arena paste(Location playerPosition) {
	  		return copy(this).move(playerPosition).paste(false);
	  	}

	  	public List<ArenaBlock> getBlocks() {
	  		return blocks;
	  	}
	  	public void setBlocks(List<ArenaBlock> blocks) {
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

	  public static class ArenaBlock implements Storable{

			private String world;
			private int x, y, z;
			private int id, data;
			public ArenaBlock(Block block) {
				setBlock(block);
			}public ArenaBlock() {
				// TODO Auto-generated constructor stub
			}
			public World getRealWorld() {
				return Bukkit.getWorld(world);
			}
			public Block getBlock() {
				return getRealWorld().getBlockAt(x, y, z);
			}
			@SuppressWarnings("deprecation")
			public void update(boolean applyPhisics) {
				getBlock().setTypeIdAndData(id, (byte) data, applyPhisics);
			}
			public void setBlock(Location location) {
				setBlock(location.getBlock());
			}
			@SuppressWarnings("deprecation")
			public void setBlock(Block block) {
				this.world = block.getWorld().getName();
				this.x = block.getX();
				this.z = block.getZ();
				this.y = block.getZ();
				this.id = block.getTypeId();
				this.data = block.getData();
			}
			public Location getLocation() {
				return new Location(getRealWorld(), x, y, z);
			}
			
			public BlockState getState() {
				return getBlock().getState();
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
			public Object restore(Map<String, Object> map) {
				// TODO Auto-generated method stub
				return null;
			}
			@Override
			public void store(Map<String, Object> map, Object object) {
				// TODO Auto-generated method stub
				
			}

		}

}
