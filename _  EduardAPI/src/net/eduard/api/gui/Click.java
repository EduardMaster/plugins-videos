
package net.eduard.api.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.util.ClickCheck;
import net.eduard.api.util.ClickEffect;
import net.eduard.api.util.ClickType;

public class Click extends Slot{
	private static final ClickEffect DEFAULT = new ClickEffect() {
		
		public void effect(PlayerInteractEntityEvent e) {
		}
		
		public void effect(PlayerInteractEvent e) {
			
		}
	};
	private Player player;
	private String typeNamed;
	private ClickCheck check = ClickCheck.BY_TYPE;
	private ClickType type = ClickType.ALL_CLICK;
	private ClickEffect effect=DEFAULT;

	public Click() {
		this(Material.AIR);
	}

	public Click(String material) {
		this(Material.AIR);
		setTypeNamed(material);
		setCheck(ClickCheck.BY_TYPE_NAMED);
	}

	public String toString() {
		return "Click [player=" + player + ", typeNamed=" + typeNamed + ", check=" + check + ", type=" + type + "]";
	}

	public Click(Material material) {
		super(new ItemStack(material),0);
		API.CLICKS.add(this);
	}
	public Click(Material material, ClickEffect clickEffect) {
		this(material);
		setEffect(clickEffect);
	}

	public Click(ItemStack item, ClickType type, ClickCheck check) {
		super(item,0);
		this.type = type;
		this.check = check;
		API.CLICKS.add(this);
	}

	

	public ClickCheck getCheck() {

		return check;
	}


	public ClickType getType() {

		return type;
	}

	public Click setCheck(ClickCheck check) {

		this.check = check;
		return this;
	}

	public Click setType(ClickType type) {

		this.type = type;
		return this;
	}

	public String getTypeNamed() {
		return typeNamed;
	}

	public Click setTypeNamed(String typeNamed) {
		this.typeNamed = typeNamed;
		return this;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}


	public ClickEffect getEffect() {
		return effect;
	}

	public void setEffect(ClickEffect effect) {
		this.effect = effect;
	}

}
