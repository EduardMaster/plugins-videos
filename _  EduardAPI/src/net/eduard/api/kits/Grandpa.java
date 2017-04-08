package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import net.eduard.api.API;
import net.eduard.api.gui.Kit;

public class Grandpa extends Kit{

	public Grandpa() {
		setIcon(Material.STICK, "§fJogue seus inimigos para longe");
		API.add(add(Material.STICK), Enchantment.KNOCKBACK, 2);
	}
}
