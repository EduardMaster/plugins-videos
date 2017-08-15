package net.eduard.api.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.config.ConfigSection;
/**
 * Representa o Controle de efeito de clicar na Tela<br>
 * Botão direito, esquerdo com um Item especifico ou não
 * @author Eduard-PC
 *
 */
public class Click extends Slot {
	
	

	private static final ClickEffect DEFAULT = new ClickEffect() {

		@Override
		public void effect(PlayerInteractEntityEvent e) {

		}

		@Override
		public void effect(PlayerInteractEvent e) {
		}
	};
	private ClickType type = ClickType.RIGHT;
	private ClickCheck check = ClickCheck.NORMAL;
	private transient ClickEffect click = DEFAULT;
	private String typeName;

	public Click() {
	}

	public Click(ItemStack item, ClickEffect clickEffect) {
		setItem(item);
		setClick(clickEffect);
	}
	public Click(Material type, ClickEffect effect) {
		setItem(type);
		setClick(effect);
	}
	public Click(ItemStack item, int slot) {
		setItem(item);
		setSlot(slot);
	}
	@Override
	public Click setItem(Material type) {
		setCheck(ClickCheck.TYPE);
		setItem(new ItemStack(type));
		return this;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void event(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		ItemStack item = getItem();
		ItemStack id = p.getItemInHand();
		if (id == null)
			id = new ItemStack(0);
	
		if (check != ClickCheck.ANY_ITEM) {
			if (check == ClickCheck.ALL && !id.equals(item))
				return;
			if (check == ClickCheck.NORMAL && !id.isSimilar(item))
				return;
			if (check == ClickCheck.TYPE && id.getType() != item.getType())
				return;
			if (check == ClickCheck.TYPE_NAME && !id.getType().name()
					.toLowerCase().contains(typeName.toLowerCase()))
				return;
			if (check == ClickCheck.NAME && !id.getItemMeta().getDisplayName()
					.equals(item.getItemMeta().getDisplayName()))
				return;
		}
		if (id.getType() != Material.AIR&&check != ClickCheck.ANY_ITEM)
			e.setCancelled(true);
		click.effect(e);
		effect(p);
	
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		ItemStack id = e.getItem();
		ItemStack item = getItem();
		String ty = e.getAction().name();
		String tr = type.name();
		if (id == null)
			id = new ItemStack(0);
		if (check != ClickCheck.ANY_ITEM) {
			if (check == ClickCheck.ALL && !id.equals(item))
				return;
			if (check == ClickCheck.NORMAL && !id.isSimilar(item))
				return;
			if (check == ClickCheck.TYPE && id.getType() != item.getType())
				return;
			if (check == ClickCheck.TYPE_NAME && !id.getType().name()
					.toLowerCase().contains(typeName.toLowerCase()))
				return;
			if (check == ClickCheck.NAME && !id.getItemMeta().getDisplayName()
					.equals(item.getItemMeta().getDisplayName()))
				return;
		}

		if (type == ClickType.ENTITY && id.getType() != Material.AIR) {
			e.setCancelled(true);
			return;
		}
		if (tr.contains("AIR") && !ty.contains("AIR")) {

			return;
		}
		if (tr.contains("BLOCK") && !ty.contains("BLOCK")) {

			return;
		}
		if (tr.contains("LEFT") && !ty.contains("LEFT")) {

			return;
		}
		if (tr.contains("RIGHT") && !ty.contains("RIGHT")) {
			return;
		}
		click.effect(e);
		effect(p);

		if (id.getType() != Material.AIR&&check != ClickCheck.ANY_ITEM) {
			e.setCancelled(true);
			return;
		}

	}

	@Override
	public Object get(ConfigSection section) {
		return null;
	}

	public ClickCheck getCheck() {
		return check;
	}

	public ClickEffect getClick() {
		return click;
	}

	public ClickType getType() {
		return type;
	}

	public String getTypeName() {
		return typeName;
	}

	@Override
	public void save(ConfigSection section, Object value) {

	}

	public Click setCheck(ClickCheck check) {
		this.check = check;
		return this;
	}
	public Click setClick(ClickEffect click) {
		this.click = click;
		return this;
	}
	public Click setType(ClickType type) {
		this.type = type;
		return this;
	}
	public Click setTypeName(String typeName) {
		this.typeName = typeName;
		return this;
	}

}
