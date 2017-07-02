package zLuck.zEventos;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.help.HelpTopic;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffectType;

import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Eventos implements Listener{
	
	@EventHandler 
	void aoQuebrar(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if (zLuck.estado == Estado.Iniciando && p.getGameMode() == GameMode.SURVIVAL) {
			e.setCancelled(true);
		}
		if (Arrays.spec.contains(p)) {
			e.setCancelled(true);
		}
		if (Uteis.forcefield.contains(e.getBlock())) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	void DanoTnT(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getCause() == DamageCause.BLOCK_EXPLOSION || e.getCause() == DamageCause.ENTITY_EXPLOSION) {
				e.setDamage(10.0D);
			}
		}
	}
	@EventHandler 
	void aoBotar(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		if (zLuck.estado == Estado.Iniciando && p.getGameMode() == GameMode.SURVIVAL) {
			e.setCancelled(true);
		}
		if (Arrays.spec.contains(p)) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	void aoPegar(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		
		if (Arrays.spec.contains(p)) {
			e.setCancelled(true);
		}
		if (KitAPI.getKit(p).equalsIgnoreCase("Admin")) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	void Dano(EntityDamageEvent e) {
		if (zLuck.estado != Estado.Jogo) {
			e.setCancelled(true);
			return;
		}
		if (Arrays.ganhou) {
			e.setCancelled(true);
		}
	}
	@EventHandler(priority = EventPriority.MONITOR)
	void DanoTreinoPvP(EntityDamageEvent e) {
	    if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			
	    	if (zLuck.estado == Estado.Iniciando && Arrays.treinopvp.contains(p)) {	
	    		e.setCancelled(false);
	    	}
	    }
	}
	@EventHandler
	void aoAndarBaixo(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		if (zLuck.estado == Estado.Iniciando) {
			if (p.getLocation().getBlockY() <= 140) {
				p.teleport(new Location(p.getWorld(), zLuck.mensagens.getDouble("Spawn.X"), zLuck.mensagens.getDouble("Spawn.Y"), zLuck.mensagens.getDouble("Spawn.Z")));
			}
		}
	}
	@EventHandler
	void aoExplodir(EntityExplodeEvent e) {
		if (zLuck.estado != Estado.Jogo) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	void aoNascer(CreatureSpawnEvent e) {
		if (zLuck.estado != Estado.Jogo) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	void aoChuver(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	void aoSpecHitar(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			
			if (Arrays.spec.contains(p)) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	void aoSpecDaComando(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		
		if (Arrays.spec.contains(p)) {
			if (e.getMessage().equalsIgnoreCase("/kill") || e.getMessage().equalsIgnoreCase("/bukkit:kill") || e.getMessage().equalsIgnoreCase("/suicide")) {
				e.setCancelled(true);
				p.sendMessage(Uteis.prefix + " §7Espectador nao pode se matar!");
			}
		}
	} 
	@EventHandler
	void aoSentirFome(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}
	@EventHandler(priority = EventPriority.MONITOR)
	void NerfarDano(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player d = (Player) e.getDamager();
			Player p = (Player) e.getEntity();
			
  	        if (d.getItemInHand().getType() == Material.WOOD_SWORD) {
  	        	e.setDamage(2.0D);
  	        }
  	        if (d.getItemInHand().getType() == Material.STONE_SWORD) {
  	        	e.setDamage(4.0D);
  	        }
  	        if (d.getItemInHand().getType() == Material.IRON_SWORD) {
  	        	e.setDamage(6.0D);
  	        }
  	        if (d.getItemInHand().getType() == Material.DIAMOND_SWORD) {
  	        	e.setDamage(7.0D);
  	        }
  	        if (d.getItemInHand().getType() == Material.WOOD_AXE && !KitAPI.getKit(d).equalsIgnoreCase("Viking")) {
  	        	e.setDamage(1.0D);
  	        }
  	        if (d.getItemInHand().getType() == Material.STONE_AXE && !KitAPI.getKit(d).equalsIgnoreCase("Viking")) {
  	        	e.setDamage(2.0D);
  	        }
  	        if (d.getItemInHand().getType() == Material.IRON_AXE && !KitAPI.getKit(d).equalsIgnoreCase("Viking")) {
  	        	e.setDamage(3.0D);
  	        }
  	        if (d.getItemInHand().getType() == Material.DIAMOND_AXE && !KitAPI.getKit(d).equalsIgnoreCase("Viking")) {
  	        	e.setDamage(4.0D);
  	        }
			if (d.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) {
				e.setDamage(e.getDamage() + 1.0D);
			}
			if (d.getActivePotionEffects().contains(PotionEffectType.INCREASE_DAMAGE)) {
				e.setDamage(e.getDamage() + 2.0D);
			}
			if (!p.isDead() && !Arrays.spec.contains(d) && !KitAPI.getKit(p).equalsIgnoreCase("Admin")) {
			   /*Uteis.ActionBar(d, "§7Kit: §6" +KitAPI.kit.get(p)+ " §7Vida: §4" +(int)p1.getHealth() / 2+" ❤"); */
			}
		}
	}
	@EventHandler
	void ComandoInvalido(PlayerCommandPreprocessEvent e) {
	    Player player = e.getPlayer();
	    String cmd = e.getMessage().split(" ")[0];
	    HelpTopic topic = Bukkit.getHelpMap().getHelpTopic(cmd);
	    
	    if (topic == null) {
	        player.sendMessage("§cComando inexistente");
	        e.setCancelled(true);
	    }
	}
	@EventHandler
	void aoInteragirSpec(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (Arrays.spec.contains(p)) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	void SumirItem(final ItemSpawnEvent e) {
		if (zLuck.estado == Estado.Iniciando) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {				
				public void run() {					
					e.getEntity().remove();
					e.getEntity().getLocation().getWorld().playEffect(e.getEntity().getLocation(), Effect.SMOKE, 10);
				}
			}, 10);
		}
	}
	@EventHandler
	void TomarSopa(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Damageable hp = p;

		if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && p.getItemInHand().getType() == Material.MUSHROOM_SOUP) {
			if (hp.getHealth() != hp.getMaxHealth()) {
				p.setHealth((hp.getHealth() + 7.0D > hp.getMaxHealth()) ? hp.getMaxHealth() : (hp.getHealth() + 7.0D));
				p.getItemInHand().setType(Material.BOWL);
				return;
			}
			if (p.getFoodLevel() != 20) {
                p.setFoodLevel((p.getFoodLevel() + 7 > 20) ? 20 : (p.getFoodLevel() + 7));
				p.getItemInHand().setType(Material.BOWL);
                p.playSound(p.getLocation(), Sound.BURP, 1.0f, 1.0f);
			}
		}
	}
	@EventHandler
	void Bússola(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction().name().contains("RIGHT") && p.getItemInHand().getType() == Material.COMPASS) {
			boolean achou = false;
			for (Entity ent : p.getNearbyEntities(500.0, 128.0, 500.0)) {
				if (ent instanceof Player) {
					Player perto = (Player) ent;
					
					if (!KitAPI.getKit(perto).equalsIgnoreCase("Admin") && !Arrays.spec.contains(perto) && perto.getLocation().distance(p.getLocation()) >= 10) {
						p.setCompassTarget(perto.getLocation());
						p.sendMessage(Uteis.prefix + " §cJogador encontrado ➟ " + perto.getDisplayName());
						achou = true;
						return;
					}
				}
			}
			if (!achou) {
				p.sendMessage("§cNenhum jogador por perto!");
			}
		}
	}
	@EventHandler
	void Motd(ServerListPingEvent e) {
		if (zLuck.estado == Estado.Iniciando) {
			e.setMotd("§a§lPartida iniciando em " + Uteis.numeroComPontos(Arrays.tempoA1));
		}
		if (zLuck.estado == Estado.Proteção) {
			e.setMotd("§6§lPartida em proteção " + Uteis.numeroComPontos(Arrays.tempoA2));
		}
		if (zLuck.estado == Estado.Jogo) {
		    e.setMotd("§c§lPartida em andamento " + Uteis.numeroComPontos(Arrays.tempoA3));
			e.setMaxPlayers(Arrays.jogador.size());
		}
	}	
	@SuppressWarnings("deprecation")
	@EventHandler
	void EspectadorInventario(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (Arrays.spec.contains(p) && p.getItemInHand().getType() == Material.SKULL_ITEM && e.getAction().name().contains("RIGHT")) {
			Inventory inv = Bukkit.createInventory(p, 54, "§2§lJogadores:");
			for (Player all : Bukkit.getOnlinePlayers()) {
				if (!Arrays.spec.contains(all) && !KitAPI.getKit(all).equalsIgnoreCase("Admin") && Arrays.jogador.contains(all)) {
                    ItemStack cabeca = new ItemStack(Material.getMaterial(397), 1, (short)3);
                    SkullMeta cabecameta = (SkullMeta) cabeca.getItemMeta();
                    cabecameta.hasOwner();
                    cabecameta.setOwner(all.getName());
                    cabecameta.setDisplayName(all.getDisplayName());
                    cabecameta.setLore(java.util.Arrays.asList("§7Kit: §a" + KitAPI.kit.get(all)));
                    cabeca.setItemMeta(cabecameta);
                    inv.addItem(cabeca);
				}
				p.openInventory(inv);
			}
		}
	}
	@EventHandler
	void EspectadorTeleportar(InventoryClickEvent e) {	
        Player p = (Player) e.getWhoClicked();
		
        if (e.getInventory().getName().equalsIgnoreCase("§2§lJogadores:")) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null) {
                if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
                    e.setCancelled(true);
                    p.closeInventory();
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.getDisplayName().equals(e.getCurrentItem().getItemMeta().getDisplayName())) {
                            p.teleport(all);
                        }
                    }
                }
            }
        }
    }
	@EventHandler
	void aoDropar(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		
		if (zLuck.estado == Estado.Iniciando && p.getGameMode() == GameMode.SURVIVAL && !Arrays.treinopvp.contains(p)) {
			e.setCancelled(true);
		}
		if (Arrays.spec.contains(p)) {
			e.setCancelled(true);
		}
		if ((e.getItemDrop().getItemStack().getType() == Material.LAPIS_BLOCK || e.getItemDrop().getItemStack().getType() == Material.REDSTONE_BLOCK || e.getItemDrop().getItemStack().getType() == Material.QUARTZ_BLOCK || e.getItemDrop().getItemStack().getType() == Material.GRASS) && KitAPI.getKit(p).equalsIgnoreCase("butterfly")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.GOLD_HOE && KitAPI.getKit(p).equalsIgnoreCase("Burstmaster")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.FEATHER && KitAPI.getKit(p).equalsIgnoreCase("butterfly")) {
			e.setCancelled(true);
		}
		if ((e.getItemDrop().getItemStack().getType() == Material.TNT || e.getItemDrop().getItemStack().getType() == Material.SLIME_BALL) && KitAPI.getKit(p).equalsIgnoreCase("C4")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.NETHER_STAR && KitAPI.getKit(p).equalsIgnoreCase("drain")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.DRAGON_EGG && KitAPI.getKit(p).equalsIgnoreCase("digger")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.ENDER_PORTAL && KitAPI.getKit(p).equalsIgnoreCase("endermage")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.ENDER_PORTAL && KitAPI.getKit(p).equalsIgnoreCase("endercult")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.IRON_FENCE && KitAPI.getKit(p).equalsIgnoreCase("forcefield")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.FISHING_ROD && KitAPI.getKit(p).equalsIgnoreCase("fisherman")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.REDSTONE_TORCH_ON && KitAPI.getKit(p).equalsIgnoreCase("flash")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.GOLD_HOE && KitAPI.getKit(p).equalsIgnoreCase("fear")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.STONE_BUTTON && KitAPI.getKit(p).equalsIgnoreCase("gambler")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.IRON_FENCE && KitAPI.getKit(p).equalsIgnoreCase("gladiator")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.PACKED_ICE && KitAPI.getKit(p).equalsIgnoreCase("glace")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.STICK && KitAPI.getKit(p).equalsIgnoreCase("grandpa")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.LEASH && KitAPI.getKit(p).equalsIgnoreCase("grappler")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.SNOW_BALL && KitAPI.getKit(p).equalsIgnoreCase("icelord")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.FIREWORK && KitAPI.getKit(p).equalsIgnoreCase("kangaroo")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.BLAZE_ROD && KitAPI.getKit(p).equalsIgnoreCase("monk")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.FEATHER && KitAPI.getKit(p).equalsIgnoreCase("phantom")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.BLAZE_POWDER && KitAPI.getKit(p).equalsIgnoreCase("pyro")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.GOLD_HOE && KitAPI.getKit(p).equalsIgnoreCase("Reaper")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.SNOW_BALL && KitAPI.getKit(p).equalsIgnoreCase("Switcher")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.WOOD_AXE && KitAPI.getKit(p).equalsIgnoreCase("Thor")) {
			e.setCancelled(true);
		}
		if (e.getItemDrop().getItemStack().getType() == Material.WATCH && KitAPI.getKit(p).equalsIgnoreCase("Timelord")) {
			e.setCancelled(true);
		}
	}

}
