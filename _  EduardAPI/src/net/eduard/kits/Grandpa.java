package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import net.eduard.api.API;
import net.eduard.api.gui.Kit;

public class Grandpa extends Kit{

	public Grandpa() {
		setIcon(Material.STICK, "Jogue seus inimigos para longe");
		API.add(add(Material.STICK), Enchantment.KNOCKBACK, 2);
	}
}
