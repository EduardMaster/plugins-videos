package com.hcp.gift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.hcp.Main;
import com.hcp.Texts;

import br.com.piracraft.api.caixas.ItemAPI;

public class GiftUtils {
	
	public static List<Gift> getBoxes = new ArrayList<Gift>();
	
	public static void spawnBoxes(){
		if(Bukkit.getOnlinePlayers().size() >= 20){
			for(int x = 0; x < Bukkit.getOnlinePlayers().size()/20; x++){
				Gift g = new Gift();
				Block fb = getRandomLocation().add(0, 1, 0).getBlock();
				fb.setType(Material.CHEST);
				fb.getState().update();
				fb.getState().update(true);
				
				g.setBloco(fb);
				g.setItens(null);
				g.setLocalizacao(fb.getLocation());
				
				getBoxes.add(g);
			}
		}else{
			Gift g = new Gift();
			Block fb = getRandomLocation().add(0, 1, 0).getBlock();
			fb.setType(Material.CHEST);
			fb.getState().update();
			fb.getState().update(true);
			
			g.setBloco(fb);
			g.setItens(null);
			g.setLocalizacao(fb.getLocation());
			
			getBoxes.add(g);
		}
		
		putEffect();
		
		for(Player p : Bukkit.getOnlinePlayers()){
			Texts.sendTitle(p, "§b§lGift", "§aVarias caixas spawnadas para voce!", 10, 60, 20);
		}
	}
	
	private static void putEffect(){
		new BukkitRunnable() {
			public void run() {
				for(int g = 0; g < getBoxes.size(); g++){
					fall(getBoxes.get(g).getBloco().getLocation());
					alertThatHaveAGiftRightThere(getBoxes.get(g).getBloco().getLocation());
				}
				mountChest();
			}
		}.runTaskLater(Main.plugin, 22);
	}
	
	private static void mountChest(){
		List<Material> itens = Arrays.asList(Material.COOKED_BEEF,Material.IRON_SWORD,Material.APPLE,Material.STONE_SWORD,Material.DIAMOND,Material.CHAINMAIL_CHESTPLATE,
				Material.BOW,Material.MELON,Material.ARROW,Material.EGG,Material.OBSIDIAN,Material.LOG,Material.ENCHANTMENT_TABLE,Material.GOLD_AXE,Material.IRON_INGOT);
		
		for(int g = 0; g < getBoxes.size(); g++){
			Chest b = (Chest) getBoxes.get(g).getBloco().getState();
			int x = new Random().nextInt(100);
			if(x<4){
				for(int y = 0; y < b.getBlockInventory().getSize(); y++){
					if(y<=8 || y>=18){
						b.getBlockInventory().setItem(y, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 0, " ", false));
					}
					
					if(y>=9 && y<=17){
						b.getBlockInventory().setItem(y, ItemAPI.Criar(itens.get(new Random().nextInt(itens.size())), new Random().nextInt(20), 0, " ", false));
					}
					b.getBlockInventory().setItem(13, ItemAPI.Criar(Material.DIAMOND, 2, 0, " ", false));
				}
			}else{
				for(int y = 0; y < b.getBlockInventory().getSize(); y++){
					if(y<=8 || y>=18){
						b.getBlockInventory().setItem(y, ItemAPI.Criar(Material.STAINED_GLASS_PANE, 1, 0, " ", false));
					}
					
					if(y>=9 && y<=17){
						Material m = itens.get(new Random().nextInt(itens.size()));
						if(m!=Material.DIAMOND){
							b.getBlockInventory().setItem(y, ItemAPI.Criar(m, new Random().nextInt(7), 0, " ", false));
						}
					}
				}
			}
			
			getBoxes.remove(g);
		}
	}
	
	private static void fall(Location loc){
            new BukkitRunnable(){
                double t = Math.PI/4;
                public void run(){ 
                        t = t + 0.1*Math.PI;
                        for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32){
                                double x = t*Math.cos(theta);
                                double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
                                double z = t*Math.sin(theta);
                                loc.add(x,y,z);
                                ParticleEffect.LAVA.display(0, 0, 0, 0, 2, loc, 300);
                                loc.subtract(x,y,z);
                               
                                theta = theta + Math.PI/64;
                               
                                x = t*Math.cos(theta);
                                y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
                                z = t*Math.sin(theta);
                                loc.add(x,y,z);
                                ParticleEffect.SMOKE_NORMAL.display(0, 0, 0, 0, 2, loc, 300);
                                loc.subtract(x,y,z);
                        }
                        if (t > 20){
                                this.cancel();
                        }
            		}
                                       
        }.runTaskTimer(Main.plugin, 0, 1);
	}
	
	private static void alertThatHaveAGiftRightThere(Location loc){
        new BukkitRunnable(){
            double t = 0;
            double r = 1.5;
            
            double r2 = 1.5;
            Location loc2 = loc;
            public void run(){
                    
                    t = t + Math.PI/4;
                    
                    double x = r*Math.cos(t);
                    double y = r;
                    double z = r*Math.sin(t);
                    
                    loc.add(x, y, z);
                    ParticleEffect.VILLAGER_HAPPY.display(0, 0, 0, 0, 2, loc, 300);
                    loc.subtract(x, y, z);
                    
                    double x1 = r2*Math.cos(t);
                    double y1 = r2;
                    double z1 = r2*Math.sin(t);
                    
                    loc2.add(x1, y1, z1);
                    ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 0, 2, loc2, 300);
                    loc2.subtract(x1, y1, z1);
        	}                 
    }.runTaskTimer(Main.plugin, 0, 1);
	}
	
	public static Location getRandomLocation() {
		Location teleportLocation = null;
		int x = new Random().nextInt(400) + 1;
		int y = 150;
		int z = new Random().nextInt(400) + 1;
		boolean terra = false;
		while (terra == false) {

			teleportLocation = new Location(Bukkit.getWorld("world"), x, y, z);

			if (teleportLocation.getBlock().getType() != Material.AIR && teleportLocation.getBlock().getType() != Material.WATER && teleportLocation.getBlock().getType() != Material.STATIONARY_WATER
					&& teleportLocation.getBlock().getType() != Material.STATIONARY_LAVA && teleportLocation.getBlock().getType() != Material.LAVA) {
				terra = true;
			} else
				y--;
		}
		return teleportLocation;
	}

}
