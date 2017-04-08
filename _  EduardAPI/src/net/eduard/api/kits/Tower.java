package net.eduard.api.kits;

import org.bukkit.Material;

import net.eduard.api.gui.Kit;
import net.eduard.api.gui.KitType;


public class Tower extends Kit{

	public Tower() {
		setIcon(Material.DIAMOND_BOOTS, "§fJunte a força do Stomper com o Worm");
		add(KitType.STOMPER);
		add(KitType.WORM);
	}
	
	
}
