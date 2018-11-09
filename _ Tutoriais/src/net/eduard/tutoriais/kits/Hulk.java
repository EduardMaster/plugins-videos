package net.eduard.tutoriais.kits;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.eduard.api.lib.click.PlayerClickEntity;
import net.eduard.api.lib.click.PlayerClickEntityEffect;
import net.eduard.api.lib.game.Jump;
import net.eduard.api.lib.game.KitAbility;
import net.eduard.api.lib.game.Sounds;

public class Hulk extends KitAbility {

	public Hulk() {
		setIcon(Material.DISPENSER, "§fLevante seus inimigos");
		setTime(15);
		jump(new Jump(Sounds.create("BURP"),
				new Vector(0, 2, 0)));
		setClick(new PlayerClickEntity(Material.AIR,new PlayerClickEntityEffect() {
			
			@Override
			public void onClickAtEntity(Player player, Entity entity, ItemStack item) {
				// TODO Auto-generated method stub
				if (hasKit(player)) {
					if (player.getPassenger() != null) {

						if (entity.equals(player.getPassenger())) {
							Entity target = entity;
							player.eject();
							jump(target);;
							player.sendMessage("§6Voce jogou alguem para Cima!");

						}
					} else {
						if (cooldown(player)) {
							player.setPassenger(entity);
							player.sendMessage(
									"§6Voce colocou alguem em suas costas!");
						}
					}

				}

			}
		}));
	}

}
