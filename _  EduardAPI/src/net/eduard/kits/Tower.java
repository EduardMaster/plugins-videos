package net.eduard.kits;

import org.bukkit.Material;

import net.eduard.api.gui.Kit;
import net.eduard.api.util.KitType;


public class Tower extends Kit{

	public Tower() {
		setIcon(Material.DIAMOND_BOOTS, "Junte a força do Stomper com o Worm");
		add(KitType.STOMPER);
		add(KitType.WORM);
	}
}
