package zLuck.zHabilidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Habilidade implements Listener{

	private List<Block> gladiatorbloco = new ArrayList<Block>();
	private HashMap<Block, Player> gladblock = new HashMap<Block, Player>();
	private HashMap<Player, Player> lutando = new HashMap<Player, Player>();
	private HashMap<Player, Location> lugar = new HashMap<Player, Location>();
	private int glad1;
	private int glad2;
    private ArrayList<Player> glace = new ArrayList<Player>();
	private ArrayList<Player> icelord = new ArrayList<Player>();
	private ArrayList<Player> kangaroo = new ArrayList<Player>();
	private ArrayList<Player> nerfkangaroo = new ArrayList<Player>();
	private ArrayList<Player> launcher = new ArrayList<Player>();
	private HashMap<Player, Player> ninja = new HashMap<Player, Player>();
	private ArrayList<Player> timelord = new ArrayList<Player>();
	
	@EventHandler
	void KitGladiator(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			final Player p = e.getPlayer();
			final Player r = (Player) e.getRightClicked();
			
			if (p.getItemInHand().getType() == Material.IRON_FENCE && KitAPI.getKit(p).equalsIgnoreCase("Gladiator")) {
				if (zLuck.estado != Estado.Jogo) {
					p.sendMessage("§c§oEstado de jogo nao compativel");
					return;
				}
				Location loc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 80, p.getLocation().getZ());
		        Location loc1 = new Location(p.getWorld(), p.getLocation().getBlockX() + 8, p.getLocation().getBlockY() + 82, p.getLocation().getBlockZ() + 8);
		        Location loc2 = new Location(p.getWorld(), p.getLocation().getBlockX() - 8, p.getLocation().getBlockY() + 82, p.getLocation().getBlockZ() - 8);
		        if (lutando.containsKey(p) || lutando.containsKey(r)) {
		        	p.sendMessage(Uteis.prefix + " §7Voce ja esta em combate");
		        	return;
		        }
		        List<Location> cuboid = new ArrayList<Location>();
	            for (int bX = -10; bX <= 10; bX++) {
		            for (int bZ = -10; bZ <= 10; bZ++) {
		                for (int bY = -1; bY <= 10; bY++) {		                	
			                Block b = loc.clone().add(bX, bY, bZ).getBlock();
			                if (!b.isEmpty()) {
			                	p.sendMessage(Uteis.prefix + " §cVoce nao pode criar arena aqui");
			                	return;
			                }	                  
			                if (bY == 10) {			                
			                    cuboid.add(loc.clone().add(bX, bY, bZ));
			                } else if (bY == -1) {
			                    cuboid.add(loc.clone().add(bX, bY, bZ));
			                } else if (bX == -10 || bZ == -10 || bX == 10 || bZ == 10) {
			                    cuboid.add(loc.clone().add(bX, bY, bZ));
			                }
		                }
		            }
	            }
	            for (Location loc3 : cuboid) {
	            	loc3.getBlock().setType(Material.GLASS);
	            	gladiatorbloco.add(loc3.getBlock());
	            	gladblock.put(loc3.getBlock(), p);
	            	gladblock.put(loc3.getBlock(), r);
	            }
	            lugar.put(p, p.getLocation());
	            lugar.put(r, r.getLocation());
	            p.teleport(loc1);
	            r.teleport(loc2);
	            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 110, 5));
	            r.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 110, 5));
	            lutando.put(p, r);
	            lutando.put(r, p);
	            glad1 = Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
					public void run() {
						if (lutando.containsKey(p) && lutando.containsKey(r)) {
		          	        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, 2));
		          	        r.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, 2));
						}
					}
				}, 240 * 20);
	            glad2 = Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
					public void run() {
						if (lutando.containsKey(p) && lutando.containsKey(r)) {
							lutando.remove(p);
							lutando.remove(r);
							p.teleport(lugar.get(p));
							r.teleport(lugar.get(r));
							for (Block glad : gladiatorbloco) {
								if (gladblock.get(glad) == r || gladblock.get(glad) == p) {
									if (glad.getType() == Material.GLASS) {
										glad.setType(Material.AIR);
									}
								}
							}
				    	    for (PotionEffect effect : p.getActivePotionEffects()) {
				      	        p.removePotionEffect(effect.getType());
				    	    }
				    	    for (PotionEffect effect : r.getActivePotionEffects()) {
				      	        r.removePotionEffect(effect.getType());
				    	    }
							lugar.remove(p);
							lugar.remove(r);
							gladblock.remove(p);
							gladblock.remove(r);
						}
					}
				}, 300 * 20);
			}
		}
	}
    @EventHandler
    void KitGladiatorInteragir(BlockBreakEvent e) {
    	Player p = e.getPlayer();
    	
        if (lutando.containsKey(p) && gladiatorbloco.contains(e.getBlock()) && p.getGameMode() != GameMode.CREATIVE) {      	
            e.setCancelled(true);
        }
    }
    @EventHandler
    void KitGladiatorBotar(BlockPlaceEvent e) {
    	Player p = e.getPlayer();
    	
        if (e.getBlockPlaced().getType() == Material.IRON_FENCE && KitAPI.getKit(p).equalsIgnoreCase("Gladiator")) {      	
            e.setCancelled(true);
        }
    }
    @EventHandler
    void KitGladiatorSair(PlayerQuitEvent e) {
    	Player p = e.getPlayer();
    	
    	if (lutando.containsKey(p)) {
    		String nome = lutando.get(p).getName();
    		Player q = Bukkit.getPlayer(nome);
    		
    		lutando.remove(p);
    		lutando.remove(q);
    		q.sendMessage(Uteis.prefix + " §7O player " + p.getDisplayName() + " §7deslogou do gladiator");
    		q.teleport(lugar.get(q));
    	    for (PotionEffect effect : p.getActivePotionEffects()) {
      	        p.removePotionEffect(effect.getType());
    	    }
    		Bukkit.getScheduler().cancelTask(glad1);
    		Bukkit.getScheduler().cancelTask(glad2);
			for (Block glad : gladiatorbloco) {
				if (gladblock.get(glad) == q || gladblock.get(glad) == p) {
					if (glad.getType() == Material.GLASS) {
						glad.setType(Material.AIR);
					}
				}
			}
			gladblock.remove(p);
			gladblock.remove(q);
    	}
    }
    @EventHandler
    void KitGladiatorMorrer(PlayerDeathEvent e) {
    	Player p = e.getEntity();
    	
    	if (lutando.containsKey(p)) {
    		String nome = lutando.get(p).getName();
    		Player m = Bukkit.getPlayer(nome);
    		
    		m.sendMessage(Uteis.prefix + " §7Voce venceu o desafio contra " + p.getDisplayName());
    		p.sendMessage(Uteis.prefix + " §7Voce perdeu o desafio contra " + m.getDisplayName());
    		lutando.remove(p);
    		lutando.remove(m);
    		m.teleport(lugar.get(m));
    	    for (PotionEffect effect : p.getActivePotionEffects()) {
      	        p.removePotionEffect(effect.getType());
      	    }
	        m.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
    		Bukkit.getScheduler().cancelTask(glad1);
    		Bukkit.getScheduler().cancelTask(glad2);
			for (Block glad : gladiatorbloco) {
				if (gladblock.get(glad) == m || gladblock.get(glad) == p) {
					if (glad.getType() == Material.GLASS) {
						glad.setType(Material.AIR);
					}
				}
			}
			gladblock.remove(p);
			gladblock.remove(m);
    	}
    }
    @EventHandler
    void KitGladiatorComando(PlayerCommandPreprocessEvent e) {
    	Player p = e.getPlayer();
    	
    	if (lutando.containsKey(p)) {
    		e.setCancelled(true);
    		p.sendMessage(Uteis.prefix + " §7Voce esta impossibilitado de usar comandos no gladiator");
    	}
    }
	@EventHandler
	void KitGlace(PlayerInteractEvent e) {			
		final Player p = e.getPlayer();
		
		if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.PACKED_ICE && !glace.contains(p) && KitAPI.getKit(p).equalsIgnoreCase("Glace")) {
			e.setCancelled(true);
			if (zLuck.estado != Estado.Jogo) {
				p.sendMessage("§c§oEstado de jogo nao compativel");
				return;
			}
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
 	        KitAPI.setarCooldown(p, "Fear", 40);	
 	        glace.add(p);
 	        p.sendMessage(Uteis.prefix + " §7Voce usou seu kit! Hite alguém!");
 	        
 	        Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {	        	
				public void run() {
					if (glace.contains(p)) {
						p.sendMessage(Uteis.prefix + " §7Voce não hitou ninguem!");
						glace.remove(p);
					}
				}
			}, 10 * 20L);	 
		}
	}
    @EventHandler
    void KitGlaceLis(EntityDamageByEntityEvent e) {
    	if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
    		Player p = (Player)e.getEntity();
    		Player d = (Player)e.getDamager();	
    		
    		if (KitAPI.getKit(d).equalsIgnoreCase("Glace") && glace.contains(d)) {
    			glace.remove(d);
    			d.sendMessage(Uteis.prefix + " §7Voce descarregou seu poder!");
    			
    			p.setVelocity(new Vector(5.0D, 0.0D, 0.0D));
    			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 300));
    		}
    	}
    }
	@EventHandler
	void KitHulk(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			Player p = e.getPlayer();
			Player r = (Player) e.getRightClicked();
			
			if (p.getItemInHand().getType() == Material.AIR && KitAPI.getKit(p).equalsIgnoreCase("Hulk")) {
				if (p.getPassenger() == null) {
					if (zLuck.estado != Estado.Jogo) {
						return;
					}
					if (KitAPI.cooldown.containsKey(p)) {
						p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
						return;
					}
	        		KitAPI.setarCooldown(p, "Hulk", 10);
					p.setPassenger(r);
		            r.sendMessage(Uteis.prefix + " §7Você foi pego por um §cHULK! §7aperte shift para sair");
				} else {
					p.sendMessage("§c§oVoce esta com player levantado");
				}
			}
		}
	}
    @EventHandler
	void KitIcelord(EntityDamageByEntityEvent e) {			
	    Entity ent = e.getEntity();
	    Entity damager = e.getDamager();
	    
	    if (ent instanceof Player) {
	        final Player hit = (Player)ent;
	        if (damager instanceof Snowball) {
	            Snowball snowball = (Snowball)damager;
	            if (snowball.getShooter() instanceof Player) {  	           	
	  		        Player shooter = (Player)snowball.getShooter();
	  		        
	                if (KitAPI.getKit(shooter).equalsIgnoreCase("IceLord")) {
	        			if (zLuck.estado != Estado.Jogo) {
	        				shooter.sendMessage("§c§oEstado de jogo nao compativel");
	        				return;
	        			}
	        			if (KitAPI.cooldown.containsKey(shooter)) {
	        				shooter.sendMessage("§c§oHabilidade em cooldown");
	        				return;
	        			}
	         	        KitAPI.setarCooldown(shooter, "Icelord", 40);	
	                    icelord.add(hit);
	                    shooter.sendMessage(Uteis.prefix + " §7Voce acertou o " + hit.getDisplayName());
	                    hit.sendMessage(Uteis.prefix + " §7O " + shooter.getDisplayName() + " acertou voce pelo kit icelord");
	                    hit.damage(6.0D);
	                    Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
							public void run() {
								if (icelord.contains(hit)) {
									icelord.remove(hit);
								}
							}
						}, 40L);
	                }
	            }
	        }
	    }
	}
	@EventHandler
	void KitIcelord(PlayerMoveEvent e) {		
		Player p = e.getPlayer();
		
		if (icelord.contains(p)) {
			p.teleport(p);
		}
	}
	@EventHandler
	void KitJackhammer(BlockBreakEvent e) {
	    Player p = e.getPlayer();
		Block b = e.getBlock();
		  
		if (KitAPI.getKit(p).equalsIgnoreCase("Jackhammer") && p.getItemInHand().getType() == Material.STONE_PICKAXE) {
            for (int bY = -50; bY <= 100; ++bY) {
                Location bloc = b.getLocation().clone().add(0,bY,0);
                Block block = bloc.getBlock();
                block.breakNaturally();
            }
		}
	}
	@EventHandler
	void KitKangaroo(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && 
			KitAPI.getKit(p).equalsIgnoreCase("Kangaroo") && p.getItemInHand().getType() == Material.FIREWORK) { 
			e.setCancelled(true);
			Vector v = p.getEyeLocation().getDirection();
			if (!kangaroo.contains(p)) {
				if (!nerfkangaroo.contains(p)) {
					if (p.isSneaking()) {
						v.multiply(1.3F);
						v.setY(0.6F);
						p.setVelocity(v);
					} else {
						v.multiply(0.6F);
						v.setY(1.3F);
						p.setVelocity(v);
					}
					kangaroo.add(p);
				} else {
					p.sendMessage(Uteis.prefix + " §7Voce §c§lNAO §7pode usar o kit em §b§lCOMBATE");
				}
			}
		}
	}
	@EventHandler
	void KitKangarooAPI(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Block bloco = p.getLocation().getBlock();
		
		if (kangaroo.contains(p) && (bloco.getType() != Material.AIR || bloco.getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
			kangaroo.remove(p);
		}
	}
	@EventHandler
	void KitKangarooNerf(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			final Player p = (Player) e.getEntity();
			if (KitAPI.getKit(p).equalsIgnoreCase("Kangaroo") && !nerfkangaroo.contains(p)) {
				nerfkangaroo.add(p);
				Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
					public void run() {
						if (nerfkangaroo.contains(p)) {
							nerfkangaroo.remove(p);
						}
					}
				}, 3 * 20);
			}
		}
	}
	@EventHandler
	void KitKangaroDano(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			
			if (e.getCause() == DamageCause.FALL) {
				if (KitAPI.getKit(p).equalsIgnoreCase("Kangaroo")) {
					if (e.getDamage() >= 4.0D) {
						e.setDamage(4.0D);
					}
				}
			}
		}
	}
	@EventHandler
	void KitLumberjack(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		if ((b.getType() == Material.LOG || b.getType() == Material.LOG_2) && KitAPI.getKit(p).equalsIgnoreCase("Lumberjack") && p.getItemInHand().getType() == Material.STONE_AXE) {
			Double y = b.getLocation().getY() + 1.0;
		    Location l = new Location(p.getWorld(), b.getLocation().getX(), y, b.getLocation().getZ());
		    
		    while (l.getBlock().getType() == Material.LOG || l.getBlock().getType() == Material.LOG_2) {
		    	l.getBlock().breakNaturally();
		    	y = y + 1.0;
		    	l.setY(y);
		    }
		}
	}
	@EventHandler
	void KitLauncher(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		   
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {	
			Vector v = p.getLocation().getDirection().multiply(0).setY(3.0f);
			p.setVelocity(v);
			   
			launcher.add(p);
			return;
		}
	}
	@EventHandler
	void KitLauncher(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			  
			if (launcher.contains(p)) {
				if (e.getCause() == DamageCause.FALL) {
					e.setCancelled(true);
					launcher.remove(p);
					return;
				}
			}
		}
	}
	@EventHandler
	void KitMagma(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			   
			if (KitAPI.getKit(d).equalsIgnoreCase("Magma")) {
				Random random = new Random();
				int percent = random.nextInt(100);
				
				if (percent <= 10) {
					p.setFireTicks(60);
					return;
				}
			}
		}
	}
	@EventHandler
	void KitMagma(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			   
			if (KitAPI.getKit(p).equalsIgnoreCase("Magma") && (e.getCause() == DamageCause.LAVA || e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK)) {
				e.setCancelled(true);
				return;
			}
		}
	}
	@EventHandler
	void KitMiner(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		   
		if (KitAPI.getKit(p).equalsIgnoreCase("Miner") && b.getType() == Material.IRON_ORE) {
		    for (int bY = -2; bY <= 2; ++bY) {    	
		        for (int bx = -2; bx <= 2; ++bx) {		        	
		        	for (int bz = -2; bz <= 2; ++bz) {	        		
		                Location bloc = b.getLocation().clone().add(bx,bY,bz);
		                
		                if (bloc.getBlock().getType() == Material.IRON_ORE) {                	
			                bloc.getBlock().breakNaturally();
					 	    bloc.getBlock().setType(Material.AIR);
		                }
		        	}
		        }
		    }
		}
	}
	@EventHandler
	void KitMonk(PlayerInteractEntityEvent e) {
	    Player p = e.getPlayer();
	
	    if (KitAPI.getKit(p).equalsIgnoreCase("monk") && p.getItemInHand().getType() == Material.BLAZE_ROD) {	
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
    		KitAPI.setarCooldown(p, "Monk", 10);
 	        Player r = (Player)e.getRightClicked();
            PlayerInventory inv = r.getInventory();
            int slot = new Random().nextInt(36);
            ItemStack replaced = inv.getItemInHand();
            ItemStack replacer = inv.getItem(slot);
            
            inv.setItemInHand(replacer);
            inv.setItem(slot, replaced);
            r.sendMessage(Uteis.prefix + " §7Voce foi monkeado");
            p.sendMessage(Uteis.prefix + " §7Voce monkeou §c§l" + r.getName());
	    }
	}
	@EventHandler
	void KitNinja(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player d = (Player) e.getDamager();
			Player p = (Player) e.getEntity();
			  
			if (KitAPI.getKit(d).equalsIgnoreCase("Ninja")) {
				if (zLuck.estado != Estado.Jogo) {
					return;
				}
				ninja.put(d, p);
			}
		}
	}		  
	@EventHandler
	void KitNinja(PlayerToggleSneakEvent e) {	
		Player p = e.getPlayer();
		
		if (KitAPI.getKit(p).equalsIgnoreCase("Ninja") && p.isSneaking()) {
			if (ninja.containsKey(p)) {
				if (zLuck.estado != Estado.Jogo) {
					return;
				}
				String nome = ninja.get(p).getName();
				Player tp = Bukkit.getPlayer(nome);
				
				if (tp != null) {
					if (p.getLocation().distance(tp.getLocation()) <= 20) {
						if (KitAPI.cooldown.containsKey(p)) {
							p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
							return;
						}
		        		KitAPI.setarCooldown(p, "Ninja", 5);
						p.teleport(tp);
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
	void KitPhantom(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		  
		if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.FEATHER && KitAPI.getKit(p).equalsIgnoreCase("Phantom")) {	
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
    		KitAPI.setarCooldown(p, "Phantom", 30);
			p.setAllowFlight(true);
			p.setFlying(true);
			p.sendMessage(Uteis.prefix + " §7Voce ganhou assas para voar!");
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {			
				public void run() {
					p.sendMessage(Uteis.prefix + " §7Suas assas nao aguentara mais que 5 segundos!");
				}
			}, 1 * 20L);
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
					p.sendMessage(Uteis.prefix + " §7Suas assas nao aguentara mais que 4 segundos!");
				}
			}, 2 * 20L);
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
					p.sendMessage(Uteis.prefix + " §7Suas assas nao aguentara mais que 3 segundos!");
				}
			}, 3 * 20L);
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
					p.sendMessage(Uteis.prefix + " §7Suas assas nao aguentara mais que 2 segundos!");
				}
			}, 4 * 20L);
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
				public void run() {
					p.setAllowFlight(false);
					p.setFlying(false);
					p.sendMessage(Uteis.prefix + " §7Suas assas ficaram cansadas!");
				}
			}, 5 * 20L);
		}
	}
	@EventHandler
	void KitPoseidon(PlayerMoveEvent e) {	
	    Player p = e.getPlayer();
		  
		if (KitAPI.getKit(p).equalsIgnoreCase("Poseidon")) {
		    if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER || e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.WATER) {    	
			    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
		    }
		}
	}
	@EventHandler
	void KitPyro(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		  
		if (e.getAction().name().contains("RIGHT") && KitAPI.getKit(p).equalsIgnoreCase("Pyro") && p.getItemInHand().getType() == Material.BLAZE_POWDER) {
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
    		KitAPI.setarCooldown(p, "Pyro", 10);
	        Vector velo = p.getLocation().getDirection().normalize().multiply(10);
	        Fireball boladenve = p.launchProjectile(Fireball.class);
	        boladenve.setIsIncendiary(true);
	        boladenve.setYield(0.0F);
	        boladenve.setVelocity(velo);
	        p.sendMessage(Uteis.prefix + " §7Voce soltou bola de fogo!");
		}
	}
	@EventHandler
	void KitReaper(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			  
			if (KitAPI.getKit(d).equalsIgnoreCase("Reaper") && d.getItemInHand().getType() == Material.GOLD_HOE) {
			    Random random = new Random();
				int porcento = random.nextInt(100);
				
				if (porcento <= 33) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 1));
					return;
				}
			}
		}
	}
	@EventHandler
	void KitSnail(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			  
			if (KitAPI.getKit(d).equalsIgnoreCase("Snail")) {
				Random random = new Random();
				int porcento = random.nextInt(100);
				
				if (porcento <= 33) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
					return;
				}
			}
		}
	}
	@EventHandler
	void KitStomper(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
		    Player p = (Player) e.getEntity();
		    
		    if (e.getCause() == DamageCause.FALL) { 	
     		    if (KitAPI.getKit(p).equalsIgnoreCase("Stomper") || KitAPI.getKit(p).equalsIgnoreCase("Tower")) {			    	
				    for(Entity ent : p.getNearbyEntities(5.0D, 5.0D, 5.0D)) {
				    	if (ent instanceof Player) {
					        Player perto = (Player)ent;
					        
					        if (perto.isSneaking()) {
						        perto.damage(4.0, p);
						        perto.sendMessage(Uteis.prefix + " §7Voce foi stompado, porém estava no §C§LSHIFT");
					        } else {
						        perto.damage(e.getDamage(), p);
						        perto.sendMessage(Uteis.prefix + " §7Voce foi stompado!");
					        }
				    	}
				    }
				    e.setDamage(4.0D);
					return;
			    } 						
		    }
		}
	}
    @EventHandler
    void KitSwitcher(EntityDamageByEntityEvent e) {   	
        Entity ent = e.getEntity();
        Entity damager = e.getDamager();
      
        if (ent instanceof Player) {
            Player hit = (Player)ent;
            if (damager instanceof Snowball) {        	                
            	Snowball snowball = (Snowball)damager;                        
                if (snowball.getShooter() instanceof Player) {                      	
        		    Player shooter = (Player)snowball.getShooter();      		    
                    if (KitAPI.getKit(shooter).equalsIgnoreCase("Switcher")) {
                        Location ploc = shooter.getLocation();
                        Location hitloc = hit.getLocation();
                        shooter.teleport(hitloc);
                        hit.teleport(ploc);
                    }
                }
            }
        }
    }
	@SuppressWarnings("deprecation")
	@EventHandler
    void KitThor(PlayerInteractEvent e) {
        Player p = e.getPlayer();
      
        if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.WOOD_AXE && KitAPI.getKit(p).equalsIgnoreCase("Thor")) {
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
    		KitAPI.setarCooldown(p, "Thor", 10);
	        Location thored = p.getTargetBlock((HashSet<Byte>) null, 100).getLocation();
	        p.getWorld().strikeLightning(thored); 
        }
    }
    @EventHandler
    void KitTimelord(PlayerInteractEvent e) {
    	Player p = e.getPlayer();
    	
    	if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.WATCH && KitAPI.getKit(p).equalsIgnoreCase("Timelord")) {
			if (KitAPI.cooldown.containsKey(p)) {
				p.sendMessage("§7Aguarde mais §c§l" + TimeUnit.MILLISECONDS.toSeconds(KitAPI.cooldown.get(p) - System.currentTimeMillis()) + " segundos §7para usar novamente");
				return;
			}
    		KitAPI.setarCooldown(p, "Timelord", 30);
    		for (Entity ent : p.getNearbyEntities(5.0, 5.0, 5.0)) {
    			if (ent instanceof Player) {
    				final Player perto = (Player) ent;
    				
    				timelord.add(perto);

    				Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
						public void run() {
							timelord.remove(perto);
						}
					}, 5 * 20);
    			}
    		}
    	}
    }
    @EventHandler
    void KitTimelord(PlayerMoveEvent e) {
    	Player p = e.getPlayer();
    	
    	if (timelord.contains(p)) {
    		p.teleport(p.getLocation());
    	}
    }
    @EventHandler
    void KitViper(EntityDamageByEntityEvent e) {
    	if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
    		Player p = (Player) e.getEntity();
    		Player d = (Player) e.getDamager();
    		
    		if (KitAPI.getKit(d).equalsIgnoreCase("Viper")) {
    			if (zLuck.estado != Estado.Jogo) {
    				return;
    			}
    			Random random = new Random();
    			int porcento = random.nextInt(100);
    			if (porcento <= 33) {
    				p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 1));
    				return;
    			}
    		}
    	}
    }		    	
    @EventHandler
    void KitWorm(BlockDamageEvent e) {
    	Player p = e.getPlayer();
    	Damageable hp = p;
    	
    	if ((KitAPI.getKit(p).equalsIgnoreCase("Worm") || KitAPI.getKit(p).equalsIgnoreCase("Tower")) && e.getBlock().getType() == Material.DIRT) {
    		e.getBlock().breakNaturally();
    		if (hp.getHealth() != hp.getMaxHealth()) {
        		p.setHealth(hp.getHealth() + 1.0D);
    		}
    		if (p.getFoodLevel() != 10) {
        		p.setFoodLevel(p.getFoodLevel() + 1/2);
    		}
    		return;
    	}
    }
    
}
