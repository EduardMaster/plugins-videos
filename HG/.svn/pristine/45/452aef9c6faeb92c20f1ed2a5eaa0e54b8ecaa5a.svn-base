package zLuck.zHabilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Crops;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Habilidades implements Listener{
	
	private HashMap<Player, Item> C4 = new HashMap<Player, Item>();
	private ArrayList<Player> forcefield = new ArrayList<>();
	private HashMap<Player, Player> ajnin = new HashMap<Player, Player>();
	
	@EventHandler
	void KitAchilles(EntityDamageByEntityEvent e){
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			
			if (zLuck.estado == Estado.Jogo) {
				if (KitAPI.getKit(p).equalsIgnoreCase("Achilles")) {
					if (d.getItemInHand().getType() == Material.WOOD_SWORD || d.getItemInHand().getType() == Material.WOOD_PICKAXE || d.getItemInHand().getType() == Material.WOOD_AXE) {
						e.setDamage(6.0);
					}
					if (d.getItemInHand().getType() == Material.STONE_SWORD) {
						e.setDamage(2.0);
					}
					if (d.getItemInHand().getType() == Material.IRON_SWORD) {
						e.setDamage(3.0);
					}
					if (d.getItemInHand().getType() == Material.DIAMOND_SWORD) {
						e.setDamage(4.0);
					}
				}
			}
		}
	}
	@EventHandler
	void KitAnchor(EntityDamageByEntityEvent e){
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			
			if (zLuck.estado == Estado.Jogo) {
				if (KitAPI.getKit(p).equalsIgnoreCase("Anchor")) {
					e.setCancelled(true);
					p.damage(e.getDamage());
				} 
				if (KitAPI.getKit(d).equalsIgnoreCase("Anchor")) {
					e.setCancelled(true);
					p.damage(e.getDamage());
				}
			}
		}
	}
	@EventHandler
	void KitAJnin(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player d = (Player) e.getDamager();
			Player p = (Player) e.getEntity();
			  
			if (KitAPI.getKit(d).equalsIgnoreCase("Ajnin")) {
				if (zLuck.estado != Estado.Jogo) {
					return;
				}
				ajnin.put(d, p);
			}
		}
	}		  
	@EventHandler
	void KitAjnin(PlayerToggleSneakEvent e) {	
		Player p = e.getPlayer();
		
		if (KitAPI.getKit(p).equalsIgnoreCase("Ajnin") && p.isSneaking()) {
			if (ajnin.containsKey(p)) {
				if (zLuck.estado != Estado.Jogo) {
					return;
				}
				String nome = ajnin.get(p).getName();
				Player tp = Bukkit.getPlayer(nome);
				
				if (tp != null) {
					if (p.getLocation().distance(tp.getLocation()) <= 50) {
						if (KitAPI.cooldown.containsKey(p)) {
							p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
							return;
						}
		        		KitAPI.setarCooldown(p, "Ajnin", 5);
						tp.teleport(p);
			            p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.0F);			 		 
					} else {
						p.sendMessage(Uteis.prefix + " §7O jogador hitado esta muito longe");
					}
				} else {
					p.sendMessage(Uteis.prefix + " §7Hite alguém para teleporta-se");
				}
			}
		}
	}
	@EventHandler
	void KitAvatar(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction().name().contains("LEFT") && KitAPI.getKit(p).equalsIgnoreCase("Avatar")) {
			if (p.getItemInHand().getType() == Material.LAPIS_BLOCK) {			
		        ItemStack avatar = new ItemStack(Material.GRASS);
		        ItemMeta avatarM = avatar.getItemMeta();
		        avatarM.setDisplayName("§bAvatar §c- §2§lTERRA");
		        avatar.setItemMeta(avatarM);
		        p.setItemInHand(avatar);
			} else if (p.getItemInHand().getType() == Material.GRASS) {
		        ItemStack avatar = new ItemStack(Material.QUARTZ_BLOCK);
		        ItemMeta avatarM = avatar.getItemMeta();
		        avatarM.setDisplayName("§bAvatar §c- §f§lAR");
		        avatar.setItemMeta(avatarM);
		        p.setItemInHand(avatar);
			} else if (p.getItemInHand().getType() == Material.QUARTZ_BLOCK) {
		        ItemStack avatar = new ItemStack(Material.REDSTONE_BLOCK);
		        ItemMeta avatarM = avatar.getItemMeta();
		        avatarM.setDisplayName("§bAvatar §c- §4§lFOGO");
		        avatar.setItemMeta(avatarM);
		        p.setItemInHand(avatar);
			} else if (p.getItemInHand().getType() == Material.REDSTONE_BLOCK) {
		        ItemStack avatar = new ItemStack(Material.LAPIS_BLOCK);
		        ItemMeta avatarM = avatar.getItemMeta();
		        avatarM.setDisplayName("§bAvatar §c- §9§lAGUA");
		        avatar.setItemMeta(avatarM);
		        p.setItemInHand(avatar);
			}
		} else if (e.getAction().name().contains("RIGHT") && KitAPI.getKit(p).equalsIgnoreCase("Avatar")) {
			if (p.getItemInHand().getType() == Material.LAPIS_BLOCK) {
				if (zLuck.estado != Estado.Jogo) {
					p.sendMessage("§c§oEstado de jogo nao compativel");
					return;
				}
				if (KitAPI.cooldown.containsKey(p)) {
					p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
					return;
				}
				KitAPI.setarCooldown(p, "Avatar", 30);
				Vector v = p.getEyeLocation().getDirection().multiply(10);
				Fireball fireball = p.launchProjectile(Fireball.class);
				fireball.setIsIncendiary(true);
				fireball.setVelocity(v);
				BlockIterator bola = new BlockIterator(p.getEyeLocation(), 0, 30);
				while (bola.hasNext()) {
	                p.getWorld().playEffect(bola.next().getLocation(), Effect.STEP_SOUND, Material.LAPIS_BLOCK, 10);   
				}
			} else if (p.getItemInHand().getType() == Material.GRASS) {		
				if (zLuck.estado != Estado.Jogo) {
					p.sendMessage("§c§oEstado de jogo nao compativel");
					return;
				}
				if (KitAPI.cooldown.containsKey(p)) {
					p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
					return;
				}
				KitAPI.setarCooldown(p, "Avatar", 30);
				Vector v = p.getEyeLocation().getDirection().multiply(10);
				Fireball fireball = p.launchProjectile(Fireball.class);
				fireball.setIsIncendiary(true);
				fireball.setVelocity(v);
				BlockIterator bola = new BlockIterator(p.getEyeLocation(), 0, 30);
				while (bola.hasNext()) {
	                p.getWorld().playEffect(bola.next().getLocation(), Effect.STEP_SOUND, Material.GRASS, 10);   
				}
			} else if (p.getItemInHand().getType() == Material.QUARTZ_BLOCK) {
				if (zLuck.estado != Estado.Jogo) {
					p.sendMessage("§c§oEstado de jogo nao compativel");
					return;
				}
				if (KitAPI.cooldown.containsKey(p)) {
					p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
					return;
				}
				KitAPI.setarCooldown(p, "Avatar", 30);
				Vector v = p.getEyeLocation().getDirection().multiply(10);
				Fireball fireball = p.launchProjectile(Fireball.class);
				fireball.setIsIncendiary(true);
				fireball.setVelocity(v);
				BlockIterator bola = new BlockIterator(p.getEyeLocation(), 0, 30);
				while (bola.hasNext()) {
	                p.getWorld().playEffect(bola.next().getLocation(), Effect.STEP_SOUND, Material.QUARTZ_BLOCK, 10);   
				}
			} else if (p.getItemInHand().getType() == Material.REDSTONE_BLOCK) {
				if (zLuck.estado != Estado.Jogo) {
					p.sendMessage("§c§oEstado de jogo nao compativel");
					return;
				}
				if (KitAPI.cooldown.containsKey(p)) {
					p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
					return;
				}
				KitAPI.setarCooldown(p, "Avatar", 30);
				Vector v = p.getEyeLocation().getDirection().multiply(10);
				Fireball fireball = p.launchProjectile(Fireball.class);
				fireball.setIsIncendiary(true);
				fireball.setVelocity(v);
				BlockIterator bola = new BlockIterator(p.getEyeLocation(), 0, 30);
				while (bola.hasNext()) {
	                p.getWorld().playEffect(bola.next().getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK, 10);   
				}
			}
		}
	}
	@EventHandler
	void KitAvatar(BlockPlaceEvent e) {		
	    if (KitAPI.getKit(e.getPlayer()).equalsIgnoreCase("Avatar") && 
	  	    e.getBlock().getType() == Material.LAPIS_BLOCK || 
	  	    e.getBlock().getType() == Material.QUARTZ_BLOCK || 
	  	    e.getBlock().getType() == Material.REDSTONE_BLOCK || 
	  	    e.getBlock().getType() == Material.GRASS) {
	  	      e.setCancelled(true);
	    }
	}
	@EventHandler
	void KitBarbarian(PlayerDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			Player k = e.getEntity().getKiller();
			
			if (KitAPI.getKit(k).equalsIgnoreCase("Barbarian")) {
				if (k.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§oBarbarian 1")) {
					Uteis.setarItem(k, Material.STONE_SWORD, 1, 0, "§c§oBarbarian 2", "§7- Utilize para atacar outros jogadores", null, 0, 0);
					k.sendMessage(Uteis.prefix + " §4§lUPGRADE §7nivel §f1");
				} else if (k.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§oBarbarian 2")) {
					Uteis.setarItem(k, Material.IRON_SWORD, 1, 0, "§c§oBarbarian 3", "§7- Utilize para atacar outros jogadores", null, 0, 0);
					k.sendMessage(Uteis.prefix + " §4§lUPGRADE §7nivel §f2");
				} else if (k.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§oBarbarian 3")) {
					Uteis.setarItem(k, Material.IRON_SWORD, 1, 0, "§c§oBarbarian 4", "§7- Utilize para atacar outros jogadores", null, 0, 0);
					k.sendMessage(Uteis.prefix + " §4§lUPGRADE §7nivel §f3");
				} else if (k.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§oBarbarian 4")) {
					Uteis.setarItem(k, Material.IRON_SWORD, 1, 0, "§c§oBarbarian 5", "§7- Utilize para atacar outros jogadores", Enchantment.DAMAGE_ALL, 1, 0);
					k.sendMessage(Uteis.prefix + " §4§lUPGRADE §7nivel §f4");
				} else if (k.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§oBarbarian 5")) {
					Uteis.setarItem(k, Material.IRON_SWORD, 1, 0, "§c§oBarbarian 6", "§7- Utilize para atacar outros jogadores", Enchantment.DAMAGE_ALL, 2, 0);
					k.sendMessage(Uteis.prefix + " §4§lUPGRADE §7nivel §f5");
				} else if (k.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§oBarbarian 6")) {
					Uteis.setarItem(k, Material.IRON_SWORD, 1, 0, "§c§oBarbarian 7", "§7- Utilize para atacar outros jogadores", Enchantment.DAMAGE_ALL, 3, 0);
					k.sendMessage(Uteis.prefix + " §4§lUPGRADE §7nivel §f6");
				} else if (k.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§oBarbarian 7")) {
					Uteis.setarItem(k, Material.DIAMOND_SWORD, 1, 0, "§c§oBarbarian 8", "§7- Utilize para atacar outros jogadores", null, 0, 0);
					k.sendMessage(Uteis.prefix + " §4§lUPGRADE §7nivel §f7");
				} else if (k.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§oBarbarian 8")) {
					Uteis.setarItem(k, Material.DIAMOND_SWORD, 1, 0, "§c§oBarbarian 9", "§7- Utilize para atacar outros jogadores", Enchantment.DAMAGE_ALL, 1, 0);
					k.sendMessage(Uteis.prefix + " §4§lUPGRADE §7nivel §f8");
				} else if (k.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§c§oBarbarian 9")) {
					Uteis.setarItem(k, Material.DIAMOND_SWORD, 1, 0, "§c§oBarbarian 10", "§7- Utilize para atacar outros jogadores", Enchantment.DAMAGE_ALL, 2, 0);
					k.sendMessage(Uteis.prefix + " §4§lUPGRADE §7nivel §fMAXÍMO");
				}
			}
		}
	}
	@EventHandler
	void KitBerserker(PlayerDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			Player k = e.getEntity().getKiller();
			
			if (KitAPI.getKit(k).equalsIgnoreCase("Berserker")) {
		        k.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 0));
		        k.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 0));
		        k.sendMessage(Uteis.prefix + " §7Modo berserker §4§lON!");
		        k.playSound(k.getLocation(), Sound.AMBIENCE_THUNDER, 1.0F, 1.0F);
			}
		}
	}
	@EventHandler
	void KitBoxer(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			
			if (KitAPI.getKit(p).equalsIgnoreCase("Boxer")) {
				e.setDamage(e.getDamage() - 1.0D);
			} else if (KitAPI.getKit(d).equalsIgnoreCase("Boxer")) {
				e.setDamage(e.getDamage() + 1.0D);
			}
		}
	}
	@EventHandler
	void KitBurstMaster(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		
		if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.GOLD_HOE && KitAPI.getKit(p).equalsIgnoreCase("BurstMaster")) {
			e.setCancelled(true);
			if (zLuck.estado != Estado.Jogo) {
				p.sendMessage("§c§oEstado de jogo nao compativel");
				return;
			}
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
			KitAPI.setarCooldown(p, "BurstMaster", 30);
			final Snowball ball = p.launchProjectile(Snowball.class);
			ball.setFireTicks(90);
			ball.setVelocity(p.getEyeLocation().getDirection().multiply(2.0F));
            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
                public void run() {
                    p.getWorld().createExplosion(ball.getLocation(), 3.1f);
                }
            }, 9L);
            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
                public void run() {
                    ball.remove();
                }
            }, 10L);
			BlockIterator bola = new BlockIterator(p.getEyeLocation(), 0, 30);			
			while (bola.hasNext()) {
                p.getWorld().playEffect(bola.next().getLocation(), Effect.STEP_SOUND, Material.TNT, 50);
			}
		}
	}
    @EventHandler
    void KitButterfly(PlayerInteractEvent e){  	
    	final Player p = e.getPlayer();    
    	
        if(e.getAction() == Action.RIGHT_CLICK_AIR) {               	
            if(p.getItemInHand().getType() == Material.FEATHER && KitAPI.getKit(p).equalsIgnoreCase("Butterfly")) {
    			if (zLuck.estado != Estado.Jogo) {
    				p.sendMessage("§c§oEstado de jogo nao compativel");
    				return;
    			}
    			if (KitAPI.cooldown.containsKey(p)) {
    				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
    				return;
    			}
    			KitAPI.setarCooldown(p, "Butterfly", 30);
 			    Vector vector = p.getEyeLocation().getDirection();
  			    vector.multiply(2);
  			    
			    final Item item = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.WOOL));
                item.setVelocity(vector);
                item.setPassenger(p);
                item.setPickupDelay(Integer.MAX_VALUE);

                Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {                	
                    public void run() {
                        p.setPassenger(null);
                        item.remove();
                    }
                }, 3 * 20L);  
            }
        }
    } 
    @EventHandler
    void KitCactus(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player p = (Player)e.getEntity();
            Player d = (Player)e.getDamager();
            
            if (KitAPI.getKit(p).equalsIgnoreCase("Cactus")) {
                Random r = new Random();
                int rand = r.nextInt(100);
                
                if (rand <= 20) {              	
                    d.damage(e.getDamage());
                }
            }
        }
    }
	@EventHandler
	void KitCamel(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if ((e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SAND || e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SANDSTONE) && KitAPI.getKit(p).equalsIgnoreCase("Camel")) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 0));
		}
	}
	@EventHandler
	void KitCookieMonster(BlockBreakEvent e) {		
	    Player p = e.getPlayer();
	    
	     if (e.getBlock().getType() == Material.GRASS && KitAPI.getKit(p).equalsIgnoreCase("Cookiemonster")) {      
		     e.getBlock().getWorld().dropItem(new Location(p.getWorld(), e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ()), new ItemStack(Material.COOKIE));
		 }
	}
    @EventHandler
	void KitCookieMonster(PlayerInteractEvent e) {   	   	
	    Player p = e.getPlayer();	    
	      
	    if (p.getItemInHand().getType() == Material.COOKIE && KitAPI.getKit(p).equalsIgnoreCase("Cookiemonster")) {	 	    	
	        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
	        	p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
                p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                return;
	        }
	    }
    }
	@SuppressWarnings("deprecation")
	@EventHandler
	void KitCultivator(BlockPlaceEvent e) {		
		Player p = e.getPlayer();
		  
		if ((e.getBlock().getType() == Material.SAPLING || e.getBlock().getType() == Material.SEEDS) && (KitAPI.getKit(p).equalsIgnoreCase("Endermage") || KitAPI.getKit(p).equalsIgnoreCase("Endercult"))) {			
			if (e.getBlock().getType() == Material.SAPLING) {
				Location bloc = e.getBlock().getLocation();
				e.getBlock().setTypeId(17);
				bloc.getWorld().generateTree(bloc.add(0.0, -1.0, 0.0), TreeType.TREE);
			}
			if (e.getBlock().getType() == Material.CROPS) {
			    Crops c = (Crops) e.getBlock();
			    c.setState(CropState.GERMINATED);
			}
		}
	}
  	@EventHandler
    void KitCreeper(PlayerDeathEvent e) {		   	
    	if (e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {  	 
        	final Player p = e.getEntity();
    		
    	    if (KitAPI.getKit(p).equalsIgnoreCase("Creeper")) {	           
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {                     	
		        	public void run() {
		                p.getWorld().createExplosion(p.getLocation(), 5.0F);  	
		        	}          
                }, 5 * 20L); 
    	    }
    	}
  	}    
	@EventHandler
	void KitC4Dano(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if ((e.getCause() == DamageCause.BLOCK_EXPLOSION || e.getCause() == DamageCause.ENTITY_EXPLOSION) && KitAPI.getKit(p).equalsIgnoreCase("c4")) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	void KitC4(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction().name().contains("RIGHT")) {
			if (p.getItemInHand().getType() == Material.TNT && KitAPI.getKit(p).equalsIgnoreCase("c4")) {
				e.setCancelled(true);
    			if (zLuck.estado != Estado.Jogo) {
    				p.sendMessage("§c§oEstado de jogo nao compativel");
    				return;
    			}
				if (KitAPI.cooldown.containsKey(p)) {
					p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
					return;
				}
				Item c4 = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.TNT));
				c4.setVelocity(p.getEyeLocation().getDirection().multiply(1.0));
				c4.setPickupDelay(Integer.MAX_VALUE);
				C4.put(p, c4);
				p.sendMessage(Uteis.prefix + " §7A bomba foi §c§lPLANTADA");
				Uteis.setarItem(p, Material.SLIME_BALL, 1, 0, "§cDetonar", null, null, 0, 1);				
			} else if (p.getItemInHand().getType() == Material.SLIME_BALL && KitAPI.getKit(p).equalsIgnoreCase("c4")) {
				Item c4 = C4.get(p);
				p.getWorld().createExplosion(c4.getLocation(), 5.0F);
				c4.remove();
				C4.remove(p);
				p.sendMessage(Uteis.prefix + " §7A bomba foi §c§lDETONADA");
				KitAPI.setarCooldown(p, "C4", 30);
				Uteis.setarItem(p, Material.TNT, 1, 0, "§b§oC4", null, null, 0, 1);		
			}
		} else if (e.getAction().name().contains("LEFT")) {
			if (p.getItemInHand().getType() == Material.SLIME_BALL && KitAPI.getKit(p).equalsIgnoreCase("c4")) {
				if (C4.containsKey(p)) {
					Item c4 = C4.get(p);
					c4.remove();
					C4.remove(p);
					p.sendMessage(Uteis.prefix + " §7A bomba foi §c§lRETIRADA");
					Uteis.setarItem(p, Material.TNT, 1, 0, "§b§oC4", null, null, 0, 1);		
				}
			}
		}
	}
	@EventHandler
	void KitDrain(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		
		if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.NETHER_STAR && KitAPI.getKit(p).equalsIgnoreCase("Drain")) {
			if (zLuck.estado != Estado.Jogo) {
				p.sendMessage("§c§oEstado de jogo nao compativel");
				return;
			}
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
			KitAPI.setarCooldown(p, "Drain", 30);
			p.sendMessage(Uteis.prefix + " §7Voce esta drenando vida de seus inimigos");
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
					for (Entity ent : p.getNearbyEntities(5.0, 5.0, 5.0)) {
						if (ent instanceof Player) {
							Player perto = (Player) ent;
							Damageable pertohp = perto;
							
							perto.setHealth(pertohp.getHealth() - 1.0);
						}
					}
				}
			}, 1 * 20);
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
					for (Entity ent : p.getNearbyEntities(5.0, 5.0, 5.0)) {
						if (ent instanceof Player) {
							Player perto = (Player) ent;
							Damageable pertohp = perto;
							
							perto.setHealth(pertohp.getHealth() - 1.0);
						}
					}
				}
			}, 2 * 20);
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
					for (Entity ent : p.getNearbyEntities(5.0, 5.0, 5.0)) {
						if (ent instanceof Player) {
							Player perto = (Player) ent;
							Damageable pertohp = perto;
							
							perto.setHealth(pertohp.getHealth() - 1.0);
						}
					}
				}
			}, 3 * 20);
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
					for (Entity ent : p.getNearbyEntities(5.0, 5.0, 5.0)) {
						if (ent instanceof Player) {
							Player perto = (Player) ent;
							Damageable pertohp = perto;
							
							perto.setHealth(pertohp.getHealth() - 1.0);
						}
					}
				}
			}, 4 * 20);
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
					p.sendMessage(Uteis.prefix + " §7Voce parou de drenar a vida de seus inimigos");
				}
			}, 5 * 20);
		}
	}
	@EventHandler	
    void KitDigger(BlockPlaceEvent e) {
		Player p = e.getPlayer();
	    Block b = e.getBlock();
		  
		if (b.getType() == Material.DRAGON_EGG && KitAPI.getKit(p).equalsIgnoreCase("Digger")) {		
			if (zLuck.estado != Estado.Jogo) {
				p.sendMessage("§c§oVoce nao pode usa o kit agora!");
				 return;
			}
	        p.sendMessage("§4§lAVISO: §7Voce usou o digger");
	        b.setType(Material.AIR);

	        for (int bX = 0; bX <= 5; ++bX) {
	            for (int bZ = 0; bZ <= 5; ++bZ) {
	                for (int bY = -5; bY <= 5; ++bY) {
	                    Block block = (Block) b.getLocation(new Location(p.getWorld(), b.getX(),b.getY(),b.getZ())).clone().add(bX, bY, bZ).getBlock();
	                      
	                    block.breakNaturally();
	                    block.setType(Material.AIR);
	                }
	            }
	        }
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	void KitEndermage(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		final Block b = e.getClickedBlock();
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && (KitAPI.getKit(p).equalsIgnoreCase("Endermage") || KitAPI.getKit(p).equalsIgnoreCase("Endercult")) && p.getItemInHand().getType() == Material.ENDER_PORTAL) {
			e.setCancelled(true);
			if (zLuck.estado != Estado.Jogo) {
				p.sendMessage("§c§oEstado de jogo nao compativel");
				return;
			}
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
			KitAPI.setarCooldown(p, "Endermage", 5);
			b.setTypeId(120);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
				    for (Entity ent : p.getNearbyEntities(5.0D, 128.0D, 5.0D)) {
				    	if (ent instanceof Player) {
				    		Player perto = (Player) ent;
				    		if (b.getTypeId() == 120 && perto != p) {
				                Location tp = b.getLocation().clone().add(0.5D, 1.0D, 0.5D);
				                if (perto.getLocation().distance(tp) >= 4.0) {
				                	if (!KitAPI.getKit(perto).equalsIgnoreCase("Endermage") &&  !KitAPI.getKit(perto).equalsIgnoreCase("Endercult") && !KitAPI.getKit(perto).equalsIgnoreCase("Admin") && !Arrays.spec.contains(perto)) {
				                		p.sendMessage(Uteis.prefix + " §7Voce puxou os players");
				                        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 255));
				                        perto.sendMessage("§cVoce foi puxado pelo endermage " + p.getDisplayName());
				                        perto.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 255));
				                        perto.teleport(tp);
				                	}
				                }
				    		}
				            b.setTypeId(121);
				    	}
				    }
				}
			}, 20);
		}
	}
	@EventHandler
	void KitForcefield(PlayerInteractEvent e) {
		final Player p = e.getPlayer(); 
		
		if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.IRON_FENCE && KitAPI.getKit(p).equalsIgnoreCase("Forcefield")) {
			e.setCancelled(true);
			if (zLuck.estado != Estado.Jogo) {
				p.sendMessage("§c§oEstado de jogo nao compativel");
				return;
			}
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
			KitAPI.setarCooldown(p, "Forcefield", 30);
			p.sendMessage(Uteis.prefix + " §7Voce ativou o §c§lCAMPO DE FORCA");
			forcefield.add(p);
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
					forcefield.remove(p);
					p.sendMessage(Uteis.prefix + " §7O §c§lCAMPO DE FORCA §7foi desativado");
				}
			}, 5* 20);
		}
	}
	@EventHandler
	void KitForcefieldDano(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if (forcefield.contains(p)) {
			for (Entity ent : p.getNearbyEntities(5.0d, 5.0d, 5.0d)) {
				if (ent instanceof Player) {
					Player perto = (Player) ent;
					perto.damage(1.0);		
					perto.setVelocity(perto.getLocation().getDirection().multiply(-0.2F));
				}
			}
		}
	}
	@EventHandler
	void KitFireman(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if (KitAPI.getKit(p).equalsIgnoreCase("Fireman") && (e.getCause() == DamageCause.LAVA || e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK)) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	void KitFisherman(PlayerFishEvent e) {
		if (e.getCaught() instanceof Player) {
			Player p = e.getPlayer();
			Player f = (Player) e.getCaught();
			
			if (KitAPI.getKit(p).equalsIgnoreCase("Fisherman")) {
				if (f != null && f != e.getHook().getLocation().getBlock()) {
					if (zLuck.estado != Estado.Jogo) {
						p.sendMessage("§c§oEstado de jogo nao compativel");
						return;
					}
					f.teleport(p);
				}
			}
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	void KitFlash(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.REDSTONE_TORCH_ON && KitAPI.getKit(p).equalsIgnoreCase("Flash")) {
			e.setCancelled(true);
			if (zLuck.estado != Estado.Jogo) {
				p.sendMessage("§c§oEstado de jogo nao compativel");
				return;
			}
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
			Block b = p.getTargetBlock((HashSet<Byte>) null, 100).getRelative(BlockFace.UP); 
			p.teleport(b.getLocation()); 
			p.getWorld().strikeLightning(p.getLocation());
			KitAPI.setarCooldown(p, "Flash", 15);
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	void KitForger(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType() == Material.COAL && KitAPI.getKit(p).equalsIgnoreCase("Forger")) {
		    Block b = p.getTargetBlock((HashSet<Byte>) null, 100);      
		    Location loc = b.getLocation();
		    
		    if (b.getType() == Material.IRON_ORE) {
		        p.getWorld().dropItemNaturally(loc, new ItemStack(Material.IRON_INGOT));
		        b.setType(Material.AIR);
		        remove(p.getInventory(), Material.COAL, 1, (short)0);
		    }
		}
	}
	@EventHandler
	void KitFear(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player && e.getPlayer() instanceof Player) {
			Player p = e.getPlayer();
		    Player r = (Player) e.getRightClicked();	
			
			if (p.getItemInHand().getType() == Material.GOLD_HOE && KitAPI.getKit(p).equalsIgnoreCase("Fear")) {
				e.setCancelled(true);
				if (zLuck.estado != Estado.Jogo) {
					p.sendMessage("§c§oEstado de jogo nao compativel");
					return;
				}
				if (KitAPI.cooldown.containsKey(p)) {
					p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
					return;
				}
	 	        KitAPI.setarCooldown(p, "Fear", 20);	
	     		r.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2));
	     		r.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
	     		r.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2));
	     		p.sendMessage(Uteis.prefix + " §7Voce aterrorizou " + r.getDisplayName());
			}
		}
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	void KitGambler(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction().name().contains("RIGHT") && KitAPI.getKit(p).equalsIgnoreCase("Gambler") && p.getItemInHand().getTypeId() == 77) {
			e.setCancelled(true);
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
 	        KitAPI.setarCooldown(p, "Gambler", 20);	
		    Random random = new Random();
	        List<PotionEffectType> efeitos = java.util.Arrays.asList(PotionEffectType.INCREASE_DAMAGE, PotionEffectType.SLOW, PotionEffectType.CONFUSION, PotionEffectType.DAMAGE_RESISTANCE, PotionEffectType.SPEED, PotionEffectType.WEAKNESS, PotionEffectType.HEALTH_BOOST, PotionEffectType.HUNGER);
	        int index = random.nextInt(efeitos.size());
	        String efeito = String.valueOf(efeitos.get(index));
	        
	        if (efeitos.get(index) == PotionEffectType.INCREASE_DAMAGE) {
	        	efeito = " §cforça";
	        }
	        if (efeitos.get(index) == PotionEffectType.SLOW) {
	        	efeito = " §clentidao";
	        }
	        if (efeitos.get(index) == PotionEffectType.CONFUSION) {
	        	efeito = " §cnausea";
	        }
	        if (efeitos.get(index) == PotionEffectType.DAMAGE_RESISTANCE) {
	        	efeito = " §cresistencia";
	        }
	        if (efeitos.get(index) == PotionEffectType.SPEED) {
	        	efeito = " §cvelocidade";
	        }
	        if (efeitos.get(index) == PotionEffectType.WEAKNESS) {
	        	efeito = " §cwither";
	        }
	        if (efeitos.get(index) == PotionEffectType.HEALTH_BOOST) {
	        	efeito = " §ccorações";
	        }
	        if (efeitos.get(index) == PotionEffectType.HUNGER) {
	        	efeito = " §cfome";
	        }
	        
	        p.sendMessage(Uteis.prefix + " §7Voce recebeu o efeito" + efeito);
	        p.addPotionEffect(new PotionEffect(efeitos.get(index),100, 1));
		}
	}
	private int remove(Inventory inventory, Material mat, int amount, short damage) {
	    ItemStack[] contents = inventory.getContents();
	    int removed = 0;
	    for (int i = 0; i < contents.length; i++) {
	        ItemStack item = contents[i];

	        if (item == null || !item.getType().equals(mat)) {
	            continue;
	        }

	        if (damage != (short) -1 && item.getDurability() != damage) {
	            continue;
	        }

	        int remove = item.getAmount() - amount - removed;

	        if (removed > 0) { 
	            removed = 0;
	        }

	        if (remove <= 0) {
	            removed += Math.abs(remove);
	            contents[i] = null;
	        } else {
	            item.setAmount(remove);
	        }
	    }
	    return removed;
	}

}
