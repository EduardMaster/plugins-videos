	package zLuck.zComandos;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import br.com.piracraft.api.Main;
import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Centro;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Admin implements CommandExecutor, Listener{
	
	private HashMap<Player, ItemStack[]> inv = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("admin")) {
			if (args.length == 0) {
				if (zLuck.estado != Estado.Iniciando) {
					if (Main.isStaff(p)) {
						if (KitAPI.getKit(p).equalsIgnoreCase("Admin")) {
							KitAPI.setarKit(p, "Nenhum");
							Centro.sendCenteredMessage(p, "§7Voce saiu do modo §c§lADMIN");
							Centro.sendCenteredMessage(p, "§cAgora você está visivel para todos");
							p.setGameMode(GameMode.SURVIVAL);
							p.getInventory().clear();
							p.getInventory().setContents(inv.get(p));
							
							for (Player all : Bukkit.getOnlinePlayers()) {
								all.showPlayer(p);
							}
						} else {
							if (Arrays.jogador.contains(p)) {
								Arrays.jogador.remove(p);
							}
							if (Arrays.spec.contains(p)) {
								Arrays.spec.remove(p);
							}
							inv.put(p, p.getInventory().getArmorContents());
							Centro.sendCenteredMessage(p, "§7Voce entrou no modo §c§lADMIN");
							Centro.sendCenteredMessage(p, "§cAgora você esta visivel apenas para staffers");
							p.setGameMode(GameMode.CREATIVE);
							p.getInventory().clear();
							p.getInventory().setArmorContents(null);
						    Uteis.setarItem(p, Material.FEATHER, 1, 0, "§cTeste No-Fall", "§7- Clique com o botao direito no player", null, 0, 1);
						    Uteis.setarItem(p, Material.DIAMOND_SWORD, 1, 0, "§4PvP", "§7- Hite os hackers lixos", null, 0, 3);
						    Uteis.setarItem(p, Material.IRON_FENCE, 1, 0, "§cArena", "§7- Clique com o botao direito no player", null, 0, 4);
						    Uteis.setarItem(p, Material.BOWL, 1, 0, "§cTeste Auto-Soup", "§7- Clique com o botao direito no player", null, 0, 5);
						    Uteis.setarItem(p, Material.STICK, 1, 0, "§cTeste KnockBack", "§7- Clique com o botao direito no player", Enchantment.KNOCKBACK, 10, 7);
						    Uteis.setarItem(p, Material.MAGMA_CREAM, 1, 0, "§cTeste Kill-Aura", "§7- Clique com o botao direito no player", null, 0, 8);
							for (Player all : Bukkit.getOnlinePlayers()) {
								if (!Main.isStaff(p)) {
									all.hidePlayer(p);
								}
							}
							KitAPI.setarKit(p, "Admin");
						}
					} else {
						p.sendMessage(Uteis.sempermissão);
					}
				} else {
					p.sendMessage("§c§oEstado de jogo nao compativel");
				}
			}
		}
		if (label.equalsIgnoreCase("invsee")) {
			if (Main.isStaff(p)) {
				Player t = Bukkit.getPlayer(args[0]);
				
				if (t != null) {
					p.openInventory(t.getInventory());
				} else {
					p.sendMessage("§c§oJogador offline ou inexistente");
				}
			} else {
				p.sendMessage(Uteis.sempermissão);
			}
		}
		return false;
	}
	@EventHandler
	void AbrirInv(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			Player p = e.getPlayer();
			Player r = (Player) e.getRightClicked();
			
			if (KitAPI.getKit(p).equalsIgnoreCase("Admin") && p.getItemInHand().getType() == Material.AIR) {
				p.openInventory(r.getInventory());
			}
		}
	}
	@EventHandler
	void NoFall(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			final Player p = e.getPlayer();
			final Player r = (Player) e.getRightClicked();
			
			if (KitAPI.getKit(p).equalsIgnoreCase("Admin") && p.getItemInHand().getType() == Material.FEATHER) {
	          	final Damageable r1 = r;
	          	r.setHealth(r1.getMaxHealth());
	          	r.setVelocity(new Vector(0, 1, 0));
	            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
	                public void run() {
	                    String resultado = "Erro";
	                    if (r1.getHealth() != r1.getMaxHealth()) {
	                        r.setHealth(r1.getMaxHealth());
	                        resultado = "§c0%";
	                    } else {
	                        r.setHealth(r1.getMaxHealth());
	                        resultado = "§a100%";
	                    }
	                    p.sendMessage("§4§lRESULTADO: §7Probalidade de uso de cheating: §c" + resultado);
	                }
	            }, 40L);
			}
		}
	}
	@EventHandler
	void Arena(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			final Player p = e.getPlayer();
			final Player r = (Player) e.getRightClicked();
			
			if (KitAPI.getKit(p).equalsIgnoreCase("Admin") && p.getItemInHand().getType() == Material.IRON_FENCE) {
				final Location loc1 = new Location(p.getWorld(), r.getLocation().getX(), r.getLocation().getY() + 10, r.getLocation().getZ());
				final Location loc2 = new Location(p.getWorld(), r.getLocation().getX() -1, r.getLocation().getY() + 11, r.getLocation().getZ());
				final Location loc3 = new Location(p.getWorld(), r.getLocation().getX() +1, r.getLocation().getY() + 11, r.getLocation().getZ());
				final Location loc4 = new Location(p.getWorld(), r.getLocation().getX(), r.getLocation().getY() + 11, r.getLocation().getZ() -1);
				final Location loc5 = new Location(p.getWorld(), r.getLocation().getX(), r.getLocation().getY() + 11, r.getLocation().getZ() + 1);
				final Location loc6 = new Location(p.getWorld(), r.getLocation().getX(), r.getLocation().getY() + 13, r.getLocation().getZ());
				loc1.getBlock().setType(Material.BEDROCK);
				loc2.getBlock().setType(Material.BEDROCK);
				loc3.getBlock().setType(Material.BEDROCK);
				loc4.getBlock().setType(Material.BEDROCK);
				loc5.getBlock().setType(Material.BEDROCK);
				loc6.getBlock().setType(Material.BEDROCK);
				
				r.teleport(loc1.clone().add(0.5, 0.5, 0.0));
				Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
					public void run() {
						loc1.getBlock().setType(Material.AIR);
						loc2.getBlock().setType(Material.AIR);
						loc3.getBlock().setType(Material.AIR);
						loc4.getBlock().setType(Material.AIR);
						loc5.getBlock().setType(Material.AIR);
						loc6.getBlock().setType(Material.AIR);
					}
				}, 120 * 20);
			}
		}
	}
	@EventHandler
	void AutoSoup(PlayerInteractEntityEvent e) {
	    if (e.getRightClicked() instanceof Player) {			
		    final Player p = e.getPlayer();
		    final Player t = (Player) e.getRightClicked();
		    
			if (KitAPI.getKit(p).equalsIgnoreCase("Admin") && p.getItemInHand().getType() == Material.BOWL) {
	            final ItemStack autosoup = new ItemStack(Material.MUSHROOM_SOUP);
	            final ItemMeta autosoupf = autosoup.getItemMeta();
	            autosoupf.setDisplayName("§7Forjar é §4§lBAN");
	            autosoup.setItemMeta(autosoupf);
	                            
	            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {           	
	                public void run() {              
	                    inv.put(t, t.getInventory().getContents());
	                    t.getInventory().clear();
	                    t.setHealth(4.0);
	                    p.openInventory(t.getInventory());
	                }
	            },1l);
	            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
	                public void run() {
	                    t.getInventory().setItem(15, autosoup);
	                    t.setHealth(4.0);
	                }
	            }, 20L);
	            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
	                public void run() {
	                    t.getInventory().setItem(16, autosoup);
	                    t.setHealth(4.0);
	                }
	            }, 60L);
	            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
	                public void run() {
	                    t.getInventory().setItem(17, autosoup);
	                    t.setHealth(4.0);
	                }
	            }, 80L);
	            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
	                public void run() {
	                    t.getInventory().setItem(18, autosoup);
	                    t.setHealth(4.0);
	                }
	            }, 100L);
	            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
	                public void run() {
	                    t.getInventory().setItem(19, autosoup);
	                    t.setHealth(4.0);
	                }
	            }, 120L);
	            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
	                public void run() {
	                    p.sendMessage("§4§lRESULTADO §7O jogador tomou §a§l" + getAmount(t, Material.BOWL) + "§a§l/5 §7sopas!");
	                }
	            }, 135L);
	            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
	                @Override
	                public void run() {
	                    t.getInventory().setContents(inv.get(t.getName()));
	                }
	            }, 140L);
			}
	    }
	}
	@EventHandler
	void TesteKillAura(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		
		if (KitAPI.getKit(p).equalsIgnoreCase("Admin") && p.getItemInHand().getType() == Material.MAGMA_CREAM && e.getAction().name().contains("RIGHT")) {
		    for (Player all : Bukkit.getOnlinePlayers()) {
		    	all.showPlayer(p);
		    }
		    p.setGameMode(GameMode.SURVIVAL);
            Bukkit.getScheduler().scheduleSyncDelayedTask(zLuck.getPlugin(), new Runnable() {
                public void run() {                	
				    for (Player all : Bukkit.getOnlinePlayers()) {
				    	if (!Main.isStaff(p)) {
				    	     all.hidePlayer(p);
				    	}
				    }
				    p.setGameMode(GameMode.CREATIVE);
                }
            }, 15L);
		}
	}
    public int getAmount(final Player p, final Material m) {
        int amount = 0;
        for (int i = 0; i < p.getInventory().getContents().length; ++i) {
            ItemStack item = p.getInventory().getContents()[i];
            if (item != null && item.getType() == m && item.getAmount() > 0) {
                amount += item.getAmount();
            }
        }
        return amount;
    }

}
