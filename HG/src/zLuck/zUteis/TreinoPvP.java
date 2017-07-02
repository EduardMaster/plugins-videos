package zLuck.zUteis;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import zLuck.zMain.zLuck;

public class TreinoPvP implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	void aoClicar(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		
		if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.DIAMOND_SWORD) {
			if (zLuck.estado == Estado.Iniciando) {
				if (!Arrays.treinopvp.contains(p)) {
					if (Arrays.tempoA1 >= 10) {
				  	    p.setItemInHand(new ItemStack(Material.AIR));
						Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
							public void run() {
								Arrays.treinopvp.add(p);
						  	    double x = zLuck.mensagens.getDouble("TreinoPvP.X");
						  	    double y = zLuck.mensagens.getDouble("TreinoPvP.Y");
						  	    double z = zLuck.mensagens.getDouble("TreinoPvP.Z");				  
						  	    
								ItemStack espada = new ItemStack(Material.DIAMOND_SWORD);
								ItemMeta espadam = espada.getItemMeta();
								espadam.setDisplayName("§6Espada");
								espada.setItemMeta(espadam);
				    			espada.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);				
							
							    p.getInventory().clear();
						  	    p.teleport(new Location(p.getWorld(), x, y, z));
						  	    p.sendMessage(Uteis.prefix + " §aVoce foi para o TreinoPvP");
							    p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
							    p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
							    p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
							    p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));	
							    p.getInventory().setItem(0, espada);
					  		    p.getInventory().setItem(13, new ItemStack(Material.getMaterial(39), 64));
					  		    p.getInventory().setItem(14, new ItemStack(Material.getMaterial(40), 64));
					  		    p.getInventory().setItem(15, new ItemStack(Material.BOWL, 64));
						        for (int i = 0; i <= 36; i++) {    
						             ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
						             ItemMeta sopameta = sopa.getItemMeta();
						             sopameta.setDisplayName("§7Sopa de §cCogumelo");
						             sopa.setItemMeta(sopameta);
						  	         p.getInventory().addItem(sopa);        
						        }
							}
						}, 20);
					} else {
						p.sendMessage(Uteis.prefix + " §cFuncao indisponivel no momento");
					}
				}
			}
		}
	}

}
