package com.hcp.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.hcp.Main;

public class Admin implements Listener{
	
	public static void isUsingAdmin(){
		new BukkitRunnable() {
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()){
					if(br.com.piracraft.api.command.Admin.player.contains(p.getName())){
						for(Player pp : Bukkit.getOnlinePlayers()){
							if(pp!=p){
								pp.hidePlayer(p);
							}
						}
					}else{
						for(Player pp : Bukkit.getOnlinePlayers()){
							if(pp!=p){
								pp.showPlayer(p);
							}
						}
					}
				}
			}
		}.runTaskTimer(Main.plugin, 0, 60);
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e){
		if(br.com.piracraft.api.Main.isStaff.containsKey(e.getPlayer().getName())){
			e.getPlayer().performCommand("admin");
			
			e.getPlayer().sendMessage("§4§lHardcore§f§l» §bVoce nao pode jogar como um player, cuidado!");
		}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent e){
		if(br.com.piracraft.api.command.Admin.player.contains(e.getPlayer().getName())){
			br.com.piracraft.api.command.Admin.player.remove(e.getPlayer().getName());
		}
	}
	
	@EventHandler
	public void damageentity(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
			Player d = (Player) e.getDamager();
			
			if(br.com.piracraft.api.command.Admin.player.contains(d.getName())){
				e.setCancelled(true);
			}else{
				e.setCancelled(false);
			}
		}
	}

}
