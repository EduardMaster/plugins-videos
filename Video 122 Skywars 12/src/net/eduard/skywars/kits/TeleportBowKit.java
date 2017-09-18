package net.eduard.skywars.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

import net.eduard.skywars.manager.Kit;

public class TeleportBowKit extends Kit{
	
	public TeleportBowKit() {
		
		setName("TeleportBow");
		setTitle("§6Kit>> TeleportBow");
		setDescription(Arrays.asList("§bTeleporta utilizando o Arco",
				"§bPode usar para sempre",
				"§bNão possuir cooldown"));
		setHasAbility(true);
		setIcon(Material.BOW);
		setGlow(true);
		register();
		
	}
	@EventHandler
	public void event(ProjectileHitEvent e)
	{
		
		if (e.getEntity() instanceof Arrow){
			Arrow arrow = (Arrow) e.getEntity();
			if (arrow.getShooter() instanceof Player){
				Player p = (Player) arrow.getShooter();
				if (hasKit(p)){
					p.teleport(arrow.getLocation().setDirection(p.getLocation().getDirection()));
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT,2, 1);
					
				}
				
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
