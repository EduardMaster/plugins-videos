package zLuck.zUteis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;

@SuppressWarnings("deprecation")
public class Schematic {
	
	public static List<Block> blocoscoliseu = new ArrayList<Block>();
	public static List<Block> blocos = new ArrayList<Block>();
	
    public Schematic() {   	
		Bukkit.getConsoleSender().sendMessage(Uteis.prefix + " §cSpawnando schematic...");
        File file = new File(zLuck.getPlugin().getDataFolder().getPath() + "/coliseu.schematic");
        try {
            Vector v = new Vector(0, 150, 0);
            BukkitWorld BWf = new BukkitWorld(Bukkit.getWorld("world"));
            EditSession es = new EditSession((LocalWorld)BWf, 9999999);
            CuboidClipboard c1 = SchematicFormat.MCEDIT.load(file);
            c1.place(es, v, false);
            for (int x = -c1.getWidth(); x <= c1.getWidth(); x++) {
               for (int y = -c1.getHeight(); y <= c1.getHeight(); y++) {
                   for (int z = -c1.getLength(); z <= c1.getLength(); z++) {

                    Location location = new Location(Bukkit.getWorld("world"), v.getBlockX(), v.getBlockY(), v.getBlockZ());
                    Block block = location.getBlock().getRelative(x, y, z);

                    blocoscoliseu.add(block);
                   }
               }
            }
        }
        catch (DataException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex2) {
            ex2.printStackTrace();
        }
        catch (MaxChangedBlocksException ex3) {
            ex3.printStackTrace();
        }
    }
    public static void SchematicArena() {   	
        File file = new File(zLuck.getPlugin().getDataFolder().getPath() + "/arena.schematic");
        try {
            Vector v = new Vector(0, 100, 0);
            BukkitWorld BWf = new BukkitWorld(Bukkit.getWorld("world"));
            EditSession es = new EditSession((LocalWorld)BWf, 9999999);
            CuboidClipboard c1 = SchematicFormat.MCEDIT.load(file);
            c1.place(es, v, false);
            for (int x = -c1.getWidth(); x <= c1.getWidth(); x++) {
                for (int y = -c1.getHeight(); y <= c1.getHeight(); y++) {
                   for (int z = -c1.getLength(); z <= c1.getLength(); z++) {

                    Location location = new Location(Bukkit.getWorld("world"), v.getBlockX(), v.getBlockY(), v.getBlockZ());
                    Block block = location.getBlock().getRelative(x, y, z);

                    blocos.add(block);
                   }
                }
            }
        }
        catch (DataException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex2) {
            ex2.printStackTrace();
        }
        catch (MaxChangedBlocksException ex3) {
            ex3.printStackTrace();
        }
    }
	public static void SchematicMiniFeast() {
    	Random random = new Random();
    	int minx = -450;
    	int maxx = 450;
    	int minz = -450;
    	int maxz = 450;
    	 
    	int x1 = random.nextInt(maxx - (minx) + 1) + (minx);
    	int z1 = random.nextInt(maxz - (minz) + 1) + (minz);
    	int y1 = Bukkit.getWorld("world").getHighestBlockAt(x1, z1).getY();
    	
        File file = new File(zLuck.getPlugin().getDataFolder().getPath() + "/minifeast.schematic");
        try {
            Vector v = new Vector(x1,y1,z1);
            BukkitWorld BWf = new BukkitWorld(Bukkit.getWorld("world"));
            EditSession es = new EditSession((LocalWorld)BWf, 9999999);
            CuboidClipboard c1 = SchematicFormat.MCEDIT.load(file);
            c1.place(es, v, false);
            for (int x = -c1.getWidth(); x <= c1.getWidth(); x++) {
                for (int y = -c1.getHeight(); y <= c1.getHeight(); y++) {
                   for (int z = -c1.getLength(); z <= c1.getLength(); z++) {

                    Location location = new Location(Bukkit.getWorld("world"), v.getBlockX(), v.getBlockY(), v.getBlockZ());
                    Block block = location.getBlock().getRelative(x, y, z);

                    blocos.add(block);
                   }
                }
            }
        }
        catch (DataException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		} catch (MaxChangedBlocksException e) {
			e.printStackTrace();
		}
        
        int x11 = x1 -80;
        int x2 = x1 +80;
        int z11 = z1 -80;
        int z2 = z1 +80;
        for (Block blocks : blocos) {
            if (blocks.getType() == Material.CHEST) {
            	CriarMiniFeast(blocks.getLocation());
        	}
        }
        for (Player all : Bukkit.getOnlinePlayers()) {
        	if (KitAPI.getKit(all).equalsIgnoreCase("Explorer")) {
        		all.sendMessage("§4§lAVISO: §7O mini-feast nasceu em §4X: " + x1 + " §7e §4Z: " + z1);
        	}   		
        }
        Bukkit.broadcastMessage(Uteis.prefix + " §cUm mini-feast spawnou entre [" + x11 + "], [" + x2 +"] e z: [" + z11 +"],  [" + z2 + " ]");
    }
    public static void SchematicFeast() {
    	Random random = new Random();
    	int minx = -450;
    	int maxx = 450;
    	int minz = -450;
    	int maxz = 450;
    	 
    	final int x1 = random.nextInt(maxx - (minx) + 1) + (minx);
    	final int z1 = random.nextInt(maxz - (minz) + 1) + (minz);
    	final int y1 = Bukkit.getWorld("world").getHighestBlockAt(x1, z1).getY();
        Bukkit.broadcastMessage(Uteis.prefix + " §cO feast ira spawnar em 5 minutos(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
        
    	Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
			public void run() {
		        Bukkit.broadcastMessage(Uteis.prefix + " §cO feast ira spawnar em 4 minutos(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
			}
		}, 60 * 20);
       	Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
    	    public void run() {
    	        Bukkit.broadcastMessage(Uteis.prefix + " §cO feast ira spawnar em 3 minutos(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
    	    }
        }, 120 * 20);
       	Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
    	    public void run() {
    	        Bukkit.broadcastMessage(Uteis.prefix + " §cO feast ira spawnar em 2 minutos(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
    	    }
        }, 180 * 20);
       	Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
    	    public void run() {
    	        Bukkit.broadcastMessage(Uteis.prefix + " §cO feast ira spawnar em 1 minutos(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
    	    }
        }, 240 * 20); 
       	Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
    	    public void run() {
    	        Bukkit.broadcastMessage(Uteis.prefix + " §cO feast ira spawnar em 5 segundos(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
    	    }
        }, 295 * 20);
       	Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
    	    public void run() {
    	    	Bukkit.broadcastMessage(Uteis.prefix + " §cO feast ira spawnar em 4 segundos(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
    	    }
        }, 296 * 20);
       	Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
    	    public void run() {
    	    	Bukkit.broadcastMessage(Uteis.prefix + " §cO feast ira spawnar em 3 segundos(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
    	    }
        }, 297 * 20); 
       	Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
    	    public void run() {
    	    	Bukkit.broadcastMessage(Uteis.prefix + " §cO feast ira spawnar em 2 segundos(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
    	    }
        }, 298 * 20);
       	Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
    	    public void run() {
    	    	Bukkit.broadcastMessage(Uteis.prefix + " §cO feast ira spawnar em 1 segundos(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
    	    }
        }, 299 * 20);     
       	Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
    	    public void run() {
		        Bukkit.broadcastMessage(Uteis.prefix + " §cO feast spawnou!(§4§lX: " + x1 + ", Z: " + z1 + "§c)");
		        File file = new File(zLuck.getPlugin().getDataFolder().getPath() + "/feast.schematic");
		        try {
		            Vector v = new Vector(x1,y1,z1);
		            BukkitWorld BWf = new BukkitWorld(Bukkit.getWorld("world"));
		            EditSession es = new EditSession((LocalWorld)BWf, 9999999);
		            CuboidClipboard c1 = SchematicFormat.MCEDIT.load(file);
		            c1.place(es, v, false);
		            for (int x = -c1.getWidth(); x <= c1.getWidth(); x++) {
		                for (int y = -c1.getHeight(); y <= c1.getHeight(); y++) {
		                   for (int z = -c1.getLength(); z <= c1.getLength(); z++) {

		                    Location location = new Location(Bukkit.getWorld("world"), v.getBlockX(), v.getBlockY(), v.getBlockZ());
		                    Block block = location.getBlock().getRelative(x, y, z);

		                    blocos.add(block);
		                   }
		                }
		            }
		        } catch (DataException ex) {
		            ex.printStackTrace();
		        } catch (IOException e) {
					e.printStackTrace();
				} catch (MaxChangedBlocksException e) {
					e.printStackTrace();
				}
		        for (Block blocks : blocos) {
		            if (blocks.getType() == Material.CHEST) {
		            	CriarFeast(blocks.getLocation());
		        	}
		        }
    	    }
        }, 300 * 20);  
    }
    
    public static void CriarFeast(Location loc) {
    	List<ItemStack> materials = Arrays.asList(new ItemStack(Material.IRON_SWORD),
    			                                  new ItemStack(Material.GOLD_INGOT, 4),
    			                                  new ItemStack(Material.DIAMOND),
    			                                  new ItemStack(Material.IRON_INGOT,2),
    			                                  new ItemStack(Material.POTION,1,(short)16458),
    			                                  new ItemStack(Material.POTION,1,(short)16425),
    			                                  new ItemStack(Material.POTION,1,(short)8226),
    			                                  new ItemStack(Material.BUCKET),
    			                                  new ItemStack(Material.FLINT_AND_STEEL),
    			                                  new ItemStack(Material.GOLDEN_APPLE,2),
    			                                  new ItemStack(Material.IRON_HELMET),
    			                                  new ItemStack(Material.EXP_BOTTLE,2),
    			                                  new ItemStack(Material.DIAMOND_HELMET),
    			                                  new ItemStack(Material.DIAMOND_CHESTPLATE),
    			                                  new ItemStack(Material.DIAMOND_LEGGINGS),
    			                                  new ItemStack(Material.DIAMOND_BOOTS),
    			                                  new ItemStack(Material.ENDER_PEARL,2));
    	Chest c = (Chest) loc.getBlock().getState();
    	Random random = new Random();

    	c.getInventory().clear();
    	for(int i = 0; i < 4; i++) {
        	c.getInventory().setItem(random.nextInt(27), materials.get(random.nextInt(materials.size())));
        	c.update();
    	}
    }
    public static void CriarMiniFeast(Location loc) {
    	List<ItemStack> materials = Arrays.asList(new ItemStack(Material.IRON_SWORD),
    			                                  new ItemStack(Material.GOLD_INGOT, 4),
    			                                  new ItemStack(Material.DIAMOND),
    			                                  new ItemStack(Material.DIAMOND),
    			                                  new ItemStack(Material.IRON_INGOT,2),
    			                                  new ItemStack(Material.POTION,1,(short)16458),
    			                                  new ItemStack(Material.POTION,1,(short)16425),
    			                                  new ItemStack(Material.POTION,1,(short)8226),
    			                                  new ItemStack(Material.BUCKET),
    			                                  new ItemStack(Material.FLINT_AND_STEEL),
    			                                  new ItemStack(Material.GOLDEN_APPLE,2),
    			                                  new ItemStack(Material.IRON_HELMET),
    			                                  new ItemStack(Material.EXP_BOTTLE,2),
    			                                  new ItemStack(Material.ENDER_PEARL));
    	Chest c = (Chest) loc.getBlock().getState();
    	Random random = new Random();

    	c.getInventory().clear();
    	for(int i = 0; i < 3; i++) {
        	c.getInventory().setItem(random.nextInt(27), materials.get(random.nextInt(materials.size())));
        	c.update();
    	}
    }

}
