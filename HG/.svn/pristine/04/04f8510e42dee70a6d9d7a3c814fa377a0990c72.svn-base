package zLuck.zEventos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.piracraft.api.Main;
import zLuck.zAPIs.KitAPI;
import zLuck.zMain.zLuck;
import zLuck.zUteis.Arrays;
import zLuck.zUteis.Estado;
import zLuck.zUteis.Uteis;

public class Entrar implements Listener{
	
	@EventHandler
	void aoEntrar(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(null);

		if (zLuck.estado == Estado.Iniciando) {
			p.teleport(new Location(p.getWorld(), zLuck.mensagens.getDouble("Spawn.X"), zLuck.mensagens.getDouble("Spawn.Y"), zLuck.mensagens.getDouble("Spawn.Z")));
			Arrays.jogador.add(p);
			KitAPI.setarKit(p, "Nenhum");
			Uteis.Hotbar(p);
			Arrays.kills.put(p.getName(), 0);
		}
		if (zLuck.estado == Estado.Proteção) {
	        KitAPI.setarKit(p, "Nenhum");
	        p.teleport(new Location(p.getWorld(), 200, p.getWorld().getHighestBlockYAt(200, -200), -200));
            p.getInventory().setArmorContents(null);
            p.getInventory().clear();
            p.setFoodLevel(20);
            p.getInventory().setItem(0, new ItemStack(Material.COMPASS));
	     	p.sendMessage(Uteis.prefix + " §7Voce perdeu a habilidade do seu KIT.");
	        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 999));
			Arrays.jogador.add(p);
			Arrays.kills.put(p.getName(), 0);
		}
		if (zLuck.estado == Estado.Jogo) {
	        if (Arrays.tempoA3 <= 300) {
		        KitAPI.setarKit(p, "Nenhum");
		        p.teleport(new Location(p.getWorld(), 200, p.getWorld().getHighestBlockYAt(200, -200), -200));
	            p.getInventory().setArmorContents(null);
	            p.getInventory().clear();
	            p.setFoodLevel(20);
	            p.getInventory().setItem(0, new ItemStack(Material.COMPASS));
		     	p.sendMessage(Uteis.prefix + " §7Voce perdeu a habilidade do seu KIT.");
		        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 120, 999));
				Arrays.kills.put(p.getName(), 0);
				Arrays.jogador.add(p);
			} else {	    	 
                p.teleport(new Location(p.getWorld(), 200, p.getWorld().getHighestBlockYAt(200, -200), -200));
		        KitAPI.setarKit(p, "Nenhum");
				Arrays.kills.put(p.getName(), 0);
                Uteis.EntrarSpec(p);
			}
		}
	}
	@EventHandler
	void aoEntrar(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		
		if (zLuck.estado == Estado.Proteção) {
			if (Main.isStaff(p) || Main.isVip(p)) {
				e.allow();
			} else {
				e.disallow(Result.KICK_OTHER, Uteis.prefix + " §cTorneio em andamento!\n§cCompre §a§lVIP §cpara poder entrar!");
			}
		}
		if (zLuck.estado == Estado.Jogo) {
			if (Arrays.ganhou) {
                Player ganhador = Bukkit.getPlayer(Arrays.ganhador);
				e.disallow(Result.KICK_OTHER, Uteis.prefix + " §7Torneio ja acabou!\n§7O ganhador foi " + ganhador.getDisplayName());
				return;
			}
			if (Arrays.tempoA3 <= 300) {
				if (Main.isStaff(p) || Main.isVip(p)) {
					e.allow();
				} else {
					e.disallow(Result.KICK_OTHER, Uteis.prefix + " §cTorneio em andamento!\n§cCompre §a§lVIP §cpara poder entrar!");
				}
			} else {
				if (Main.isStaff(p) || Main.isVip(p)) {
					e.allow();
				} else {
					e.disallow(Result.KICK_OTHER, Uteis.prefix + " §cTorneio em andamento!\n§cCompre §a§lVIP §cpara poder entrar!");
				}
			}
		}
		if (e.getResult() == Result.KICK_WHITELIST) {
			e.setKickMessage("§cEstamos trabalhando para a sua diversão");
		}
		if (e.getResult() == Result.KICK_FULL) {
			e.setKickMessage("§cServidor cheio! Compre §a§lVIP §cpara poder entrar");
		}
	}

}
