package net.eduard.tutoriais.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.game.KitAbility;

public class Grandpa extends KitAbility{

	public Grandpa() {
		setIcon(Material.STICK, "§fJogue seus inimigos para longe");
		Mine.addEnchant(add(Material.STICK), Enchantment.KNOCKBACK, 2);
	}
}
