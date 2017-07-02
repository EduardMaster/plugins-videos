package zLuck.zEventos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.piracraft.api.Main;
import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;
import zLuck.zMySQL.SQLStats;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Morrer implements Listener{

	@EventHandler
	void aoMorrer(PlayerRespawnEvent e) {		
		Player p = e.getPlayer();
				
		if (zLuck.estado == Estado.Iniciando) {
    	    double x = zLuck.mensagens.getDouble("Spawn.X");
    	    double y = zLuck.mensagens.getDouble("Spawn.Y");
    	    double z = zLuck.mensagens.getDouble("Spawn.Z");
        	
            e.setRespawnLocation(new Location(p.getWorld(), x, y, z));  
            Uteis.Hotbar(p);
			Arrays.treinopvp.remove(p);
		}
	    if (zLuck.estado == Estado.Jogo) {  		    
	        if (Arrays.tempoA3 <= 300) {
		        if (Main.isStaff(p) || Main.isVip(p)) {	
			        KitAPI.setarKit(p, "Nenhum");
			        e.setRespawnLocation(new Location(p.getWorld(), 200, p.getWorld().getHighestBlockYAt(200, -200), -200));
		            p.getInventory().setArmorContents(null);
		            p.getInventory().clear();
		            p.setFoodLevel(20);
		            p.getInventory().setItem(0, new ItemStack(Material.COMPASS));
			     	p.sendMessage(Uteis.prefix + " §cVoce perdeu a habilidade do seu KIT.");
			        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 999));
			    } else {				    	
		         	Arrays.jogador.remove(p);
		         	if (Main.isStaff(p) || Main.isVip(p)) {
		             	p.kickPlayer("§cVocê morreu.\n§7Ao chegar aos 5 minutos de partida voce poderá espectar.");
		         	} else {
		             	p.kickPlayer("§cVocê morreu.\n§7Adquira §a§lVIP §7em nossa loja para poder Espectar");
		         	}
			    }
			} else {
			    if (Main.isStaff(p) || Main.isVip(p)) {			    	 
	                e.setRespawnLocation(new Location(p.getWorld(), 200, p.getWorld().getHighestBlockYAt(200, -200), -200));
	                Uteis.EntrarSpec(p);
	            } else {
		         	Arrays.jogador.remove(p);
	             	p.kickPlayer("§cVocê morreu.\n§7Adquira §a§lVIP §7em nossa loja para poder Espectar");
	            }
			}
	    }
	}
	@EventHandler
	void MensagemMorrer(PlayerDeathEvent e) {
		Player p = e.getEntity();
		
		e.setDeathMessage(null);
		
	    new BukkitRunnable() {
	        public void run() {
		        p.spigot().respawn();
	        }
	    }.runTask(zLuck.getPlugin());
		
		if (zLuck.estado != Estado.Jogo) return;
		
		if (e.getEntity().getLastDamageCause() != null) {
	        String nome = "§7[§4§l卐§7] [§c§l"+p.getDisplayName()+"§7] §7(§c§l" + KitAPI.kit.get(p) + "§7)";
			if (e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent matador = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();
				if (matador.getDamager() instanceof Player) {
					Player k = (Player) matador.getDamager();
					String matou  = "§7[§4§l卐§7] §7[§c§l"+k.getDisplayName()+"§7] §7(§c§l" + KitAPI.kit.get(k) + "§7)";
					Bukkit.broadcastMessage(matou + " matou ➼ " + "[§c§l"+p.getDisplayName()+"§7] §7(§c§l" + KitAPI.kit.get(p) + "§7)" + " utilizando " + Uteis.nomes(k.getItemInHand()));
				} else if (matador.getDamager() instanceof Monster) {
					Bukkit.broadcastMessage(nome + " morreu para um monstro.");
				} else if (matador.getDamager() instanceof Arrow) {
					Bukkit.broadcastMessage(nome + " morreu para uma flecha");
				}
			} else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
				Bukkit.broadcastMessage(nome + " morreu de altura");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
				Bukkit.broadcastMessage(nome + " foi explodido em mil pedaços!");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.DROWNING) {
				Bukkit.broadcastMessage(nome + " não soube nadar e morreu afogado");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
				Bukkit.broadcastMessage(nome + " morreu de trovoada");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
				Bukkit.broadcastMessage(nome + " morreu esmagado");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.MAGIC) {
				Bukkit.broadcastMessage(nome + " morreu para a magia");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.POISON) {
				Bukkit.broadcastMessage(nome + " morreu entoxicado");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
				Bukkit.broadcastMessage(nome + " morreu para um projetil");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.STARVATION) {
				Bukkit.broadcastMessage(nome + " morreu de fome");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
				Bukkit.broadcastMessage(nome + " morreu drasticamente sufocado");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
				Bukkit.broadcastMessage(nome + " se suicidou");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.THORNS) {
				Bukkit.broadcastMessage(nome + " morreu para um espinho");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {
				Bukkit.broadcastMessage(nome + " caiu para fora do mundo");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.WITHER) {
				Bukkit.broadcastMessage(nome + " morreu de efeito wither");
            } else if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CONTACT) {
				Bukkit.broadcastMessage(nome + " morreu para um cacto");
            }
		}
	}
	@EventHandler
    void AdicionarMatou(PlayerDeathEvent e) {					
    	if (e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {    		
        	Player p = e.getEntity();
    	    Player k = p.getKiller();
    	    
    		if (zLuck.estado == Estado.Iniciando) {
    			e.getDrops().clear();
    			return;
    		}
    	    if (zLuck.estado == Estado.Jogo) {	
    	    	if (KitAPI.getKit(k).equalsIgnoreCase("Copycat")) {
    	    		KitAPI.setarKit(k, KitAPI.kit.get(p));
    	    		k.sendMessage(Uteis.prefix + " §7Voce ganhou o kit §c§l" +KitAPI.kit.get(p)+ " §7ao matar " + p.getDisplayName());
    	    	}
    	  	    Arrays.kills.put(k.getName(), Arrays.kills.get(k.getName()) + 1);
    	  	    SQLStats.addMatou(k.getUniqueId().toString(), 1);
    			if (SQLStats.getMatou(k.getName()) == 100) {
    				SQLStats.setRank(k.getName(), "Aluminiun");
    				k.sendMessage("§aVoce upou para o rank §e§o" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 200) {
    				SQLStats.setRank(k.getName(), "Prata");
    				k.sendMessage("§aVoce upou para o rank §8§l§o" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 400) {
    				SQLStats.setRank(k.getName(), "Ouro");
    				k.sendMessage("§aVoce upou para o rank §6§o" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 800) {
    				SQLStats.setRank(k.getName(), "Diamante");
    				k.sendMessage("§aVoce upou para o rank §b§o" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 1600) {
    				SQLStats.setRank(k.getName(), "Cobriun");
    				k.sendMessage("§aVoce upou para o rank §5§o" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 3200) {
    				SQLStats.setRank(k.getName(), "Esmerald");
    				k.sendMessage("§aVoce upou para o rank §a§o" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 6400) {
    				SQLStats.setRank(k.getName(), "Mercuriun");
    				k.sendMessage("§aVoce upou para o rank §9§o" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 12800) {
    				SQLStats.setRank(k.getName(), "Titaniun");
    				k.sendMessage("§aVoce upou para o rank §c§l§o" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 25600) {
    				SQLStats.setRank(k.getName(), "Platina");
    				k.sendMessage("§aVoce upou para o rank §9§l" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 51.200) {
    				SQLStats.setRank(k.getName(), "Mestre");
    				k.sendMessage("§aVoce upou para o rank §c§o" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 102400) {
    				SQLStats.setRank(k.getName(), "Centuriun");
    				k.sendMessage("§aVoce upou para o rank §5§o" + SQLStats.getRank(k.getName()));
    			}
    			if (SQLStats.getMatou(k.getName()) == 200000) {
    				SQLStats.setRank(k.getName(), "Desafiante");
    				k.sendMessage("§aVoce upou para o rank §4§o§l" + SQLStats.getRank(k.getName()));
    			}
    	    }
    	}
	}

}
