package net.eduard.tutoriais.kits;

import org.bukkit.Material;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import net.eduard.api.server.kits.KitAbility;


public class Scout extends KitAbility{
	public Scout() {
		setIcon(Material.POTION,8261, "§fGanhe poções que te dão muita força");
		add(new Potion(PotionType.SPEED,1).toItemStack(3));
	}
}
