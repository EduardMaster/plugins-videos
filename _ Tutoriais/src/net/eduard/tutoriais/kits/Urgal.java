package net.eduard.tutoriais.kits;

import org.bukkit.Material;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import net.eduard.api.lib.game.KitAbility;


public class Urgal extends KitAbility{

	public Urgal() {
		setIcon(Material.POTION,8261, "§fGanhe poções que te dão muita força");
		add(new Potion(PotionType.STRENGTH,1).toItemStack(2));
	}
}
