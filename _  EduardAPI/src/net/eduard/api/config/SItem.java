package net.eduard.api.config;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SItem extends Serz<ItemStack> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private List<String> lore;

	private int amount, data, id;

	private Map<Integer, Integer> enchants;

	@SuppressWarnings("deprecation")
	public void save(ItemStack value) {
		set(value);
		if (value.hasItemMeta()) {
			this.name = value.getItemMeta().getDisplayName();
			this.lore = value.getItemMeta().getLore();
		}
		this.data = value.getDurability();
		for (Entry<Enchantment, Integer> map : value.getEnchantments().entrySet()) {
			enchants.put(map.getKey().getId(), map.getValue());
		}

	}

	@SuppressWarnings("deprecation")
	public ItemStack reload() {
		if (isNull()) {
			set(new ItemStack(id, amount, (short) data));
			ItemMeta meta = get().getItemMeta();
			meta.setDisplayName(name);
			meta.setLore(lore);
			get().setItemMeta(meta);
			for (Entry<Integer, Integer> map:enchants.entrySet()) {
				get().addUnsafeEnchantment(Enchantment.getById(map.getKey()), map.getValue());
			}
		}
		return get();
	}

}
