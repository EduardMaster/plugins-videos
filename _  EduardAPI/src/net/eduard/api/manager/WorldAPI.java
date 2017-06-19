package net.eduard.api.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.util.LocationEffect;
/**
 * Location , World
 *
 */
public final class WorldAPI {
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
		API.deleteFolder(new File(API.getWorldsFolder(), name.toLowerCase()));
	}
	public static void copyWorld(String worldName, String name) {
		World world = getWorld(worldName);
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
		return new File(API.getWorldsFolder(), name.toLowerCase());
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
			unloadWorld(getWorld(name));
		} catch (Exception ex) {
		}
	}
	public static World getWorld(String name) {
		return Bukkit.getWorld(name);

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
		int xR = API.getRandomInt(x-xVar, x+xVar);
		int zR = API.getRandomInt(z-zVar, z+zVar);
		int yR = API.getRandomInt(y-yVar, y+zVar);
		return new Location(location.getWorld(),xR,yR,zR);
	}
	public static Location getHighPosition(Location location){
		return location.getWorld().getHighestBlockAt(location).getLocation();
	}
}
